package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.collections.Collections;
import org.apache.commons.lang3.function.TriConsumer;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Coordinate2d;
import soliloquy.specs.common.valueobjects.Coordinate3d;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.gamestate.entities.shared.GameZoneTerrain;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import static inaugural.soliloquy.tools.collections.Collections.*;
import static inaugural.soliloquy.tools.valueobjects.Coordinate2d.addOffsets2d;
import static inaugural.soliloquy.tools.valueobjects.Pair.pairOf;
import static soliloquy.specs.gamestate.entities.WallSegmentOrientation.*;

public class GameZoneImpl extends HasDeletionInvariants implements GameZone {
    private final String ID;
    private final Coordinate2d MAX_COORDINATES;
    private final Map<Integer, Map<Integer, Map<Integer, Tile>>> TILES;
    private final Map<WallSegmentOrientation, Map<Integer, Map<Integer, Map<Integer, WallSegment>>>>
            SEGMENTS;
    @SuppressWarnings("rawtypes")
    private final List<Action> ENTRY_ACTIONS;
    @SuppressWarnings("rawtypes")
    private final List<Action> EXIT_ACTIONS;
    private final VariableCache DATA;
    private final Map<UUID, Character> CHARACTERS_IN_GAME_ZONE;
    private final TriConsumer<GameZoneTerrain, GameZone, Coordinate3d> ASSIGN_LOC_AFTER_PLACE;

    private String name;

    public GameZoneImpl(String id, Coordinate2d maxCoordinates, VariableCache data,
                        Consumer<Character> addToEndOfRoundManager,
                        Consumer<Character> removeFromRoundManager,
                        TriConsumer<GameZoneTerrain, GameZone, Coordinate3d> assignLocationAfterPlacement) {
        ID = Check.ifNullOrEmpty(id, "id");
        CHARACTERS_IN_GAME_ZONE = mapOf();

        Check.ifNull(maxCoordinates, "maxCoordinates");
        Check.throwOnLteZero(maxCoordinates.X, "maxCoordinates.X");
        Check.throwOnLteZero(maxCoordinates.Y, "maxCoordinates.Y");
        Check.ifNull(addToEndOfRoundManager, "addToEndOfRoundManager");
        Check.ifNull(removeFromRoundManager, "removeFromRoundManager");
        ASSIGN_LOC_AFTER_PLACE =
                Check.ifNull(assignLocationAfterPlacement, "assignLocationAfterPlacement");

        MAX_COORDINATES = maxCoordinates;
        TILES = mapOf();
        SEGMENTS = mapOf();
        SEGMENTS.put(HORIZONTAL, mapOf());
        SEGMENTS.put(CORNER, mapOf());
        SEGMENTS.put(VERTICAL, mapOf());
        ENTRY_ACTIONS = listOf();
        EXIT_ACTIONS = listOf();
        DATA = Check.ifNull(data, "data");
    }

    @Override
    public Coordinate2d maxCoordinates() {
        return MAX_COORDINATES;
    }

    @Override
    public Tile tile(Coordinate3d loc)
            throws IllegalArgumentException, EntityDeletedException {
        return retrieveTerrain(loc, TILES, Map::get);
    }

    @Override
    public Tile removeTile(Coordinate3d loc)
            throws IllegalArgumentException, EntityDeletedException {
        return retrieveTerrain(loc, TILES, Map::remove);
    }

    @Override
    public Set<Tile> tiles() throws EntityDeletedException {
        var tiles = Collections.<Tile>setOf();
        TILES.values().forEach(yz -> yz.values().forEach(z -> tiles.addAll(z.values())));
        return tiles;
    }

    @Override
    public Set<Tile> tiles(Coordinate2d loc)
            throws IllegalArgumentException, EntityDeletedException {
        checkIfLocationValid(loc, false);
        return setOf(retrieveAt2dLoc(loc, TILES, Map::get).values());
    }

    @Override
    public Set<Tile> removeTiles(Coordinate2d loc)
            throws IllegalArgumentException, EntityDeletedException {
        var tiles = setOf(retrieveAt2dLoc(loc, TILES, Map::get).values());
        clearEmptyInnerMapsAfterRemoval(TILES, loc);
        return tiles;
    }

    @Override
    public Tile putTile(Tile tile, Coordinate3d location)
            throws IllegalArgumentException, EntityDeletedException {
        Check.ifNull(tile, "tile");
        checkIfLocationValid(location, false);

        Tile prev = null;

        var tilesByX = TILES.get(location.X);
        if (tilesByX == null) {
            TILES.put(location.X, mapOf(pairOf(location.Y, mapOf(pairOf(location.Z, tile)))));
        }
        else {
            var tilesByXY = tilesByX.get(location.Y);
            if (tilesByXY == null) {
                tilesByX.put(location.Y, mapOf(pairOf(location.Z, tile)));
            }
            else {
                prev = tilesByXY.get(location.Z);
                tilesByXY.put(location.Z, tile);
            }
        }

        ASSIGN_LOC_AFTER_PLACE.accept(tile, this, location);

        return prev;
    }

    @Override
    public Set<WallSegment> segments(Coordinate2d loc, WallSegmentOrientation orientation)
            throws IllegalArgumentException {
        if (SEGMENTS.get(orientation).containsKey(loc.X) &&
                SEGMENTS.get(orientation).get(loc.X).containsKey(loc.Y) &&
                SEGMENTS.get(orientation).get(loc.X).get(loc.Y) != null) {
            return setOf(SEGMENTS.get(orientation).get(loc.X).get(loc.Y).values());
        }
        else {
            return setOf();
        }
    }

    @Override
    public Map<WallSegmentOrientation, Map<Coordinate3d, WallSegment>> segments(
            Coordinate2d location) throws IllegalArgumentException, EntityDeletedException {
        checkIfLocationValid(location, true);

        Map<WallSegmentOrientation, Map<Coordinate3d, WallSegment>> segments =
                mapOf(pairOf(HORIZONTAL, mapOf()), pairOf(VERTICAL, mapOf()),
                        pairOf(CORNER, mapOf()));

        var locNeAndE = addOffsets2d(location, 1, 0);
        var locSwAndS = addOffsets2d(location, 0, 1);
        var locSe = addOffsets2d(location, 1, 1);
        // NW
        retrieveAt2dLoc(location, SEGMENTS.get(CORNER), Map::get).forEach((z, seg) ->
                segments.get(CORNER).put(location.to3d(z), seg));
        // N
        retrieveAt2dLoc(location, SEGMENTS.get(HORIZONTAL), Map::get).forEach((z, seg) ->
                segments.get(HORIZONTAL).put(location.to3d(z), seg));
        // NE
        retrieveAt2dLoc(locNeAndE, SEGMENTS.get(CORNER), Map::get).forEach((z, seg) ->
                segments.get(CORNER).put(locNeAndE.to3d(z), seg));
        // W
        retrieveAt2dLoc(location, SEGMENTS.get(VERTICAL), Map::get).forEach((z, seg) ->
                segments.get(VERTICAL).put(location.to3d(z), seg));
        // E
        retrieveAt2dLoc(locNeAndE, SEGMENTS.get(VERTICAL), Map::get).forEach((z, seg) ->
                segments.get(VERTICAL).put(locNeAndE.to3d(z), seg));
        // SW
        retrieveAt2dLoc(locSwAndS, SEGMENTS.get(CORNER), Map::get).forEach((z, seg) ->
                segments.get(CORNER).put(locSwAndS.to3d(z), seg));
        // S
        retrieveAt2dLoc(locSwAndS, SEGMENTS.get(HORIZONTAL), Map::get).forEach((z, seg) ->
                segments.get(HORIZONTAL).put(locSwAndS.to3d(z), seg));
        // SE
        retrieveAt2dLoc(locSe, SEGMENTS.get(CORNER), Map::get).forEach((z, seg) ->
                segments.get(CORNER).put(locSe.to3d(z), seg));

        return segments;
    }

    @Override
    public WallSegment segment(WallSegmentOrientation orientation, Coordinate3d loc)
            throws IllegalArgumentException, EntityDeletedException {
        if (SEGMENTS.get(orientation).containsKey(loc.X) &&
                SEGMENTS.get(orientation).get(loc.X).containsKey(loc.Y)) {
            return SEGMENTS.get(orientation).get(loc.X).get(loc.Y).get(loc.Z);
        }
        return null;
    }

    @Override
    public WallSegment putSegment(Coordinate3d location, WallSegment segment)
            throws IllegalArgumentException, EntityDeletedException {
        Check.ifNull(segment, "segment");
        Check.ifNull(segment.getType(), "segment.getType()");
        Check.ifNull(segment.getType().orientation(), "segment.getType().orientation()");
        checkIfLocationValid(location, true);

        WallSegment prev = null;

        var orientation = segment.getType().orientation();
        if (SEGMENTS.get(orientation).containsKey(location.X)) {
            var byX = SEGMENTS.get(orientation).get(location.X);
            if (byX.containsKey(location.Y)) {
                var byXY = byX.get(location.Y);
                prev = byXY.get(location.Z);
                byXY.put(location.Z, segment);
            }
            else {
                byX.put(location.Y, mapOf(pairOf(location.Z, segment)));
            }
        }
        else {
            SEGMENTS.get(orientation)
                    .put(location.X, mapOf(pairOf(location.Y, mapOf(pairOf(location.Z, segment)))));
        }

        ASSIGN_LOC_AFTER_PLACE.accept(segment, this, location);

        return prev;
    }

    @Override
    public WallSegment removeSegment(Coordinate3d loc, WallSegmentOrientation orientation)
            throws IllegalArgumentException, EntityDeletedException {
        Check.ifNull(loc, "loc");
        Check.ifNull(orientation, "orientation");
        checkIfLocationValid(loc, true);

        var byX = SEGMENTS.get(orientation).get(loc.X);
        if (byX != null && byX.containsKey(loc.Y)) {
            var byXY = byX.get(loc.Y);
            if (byXY != null) {
                var segment = byXY.remove(loc.Z);
                if (byXY.isEmpty()) {
                    byX.remove(loc.Y);
                }
                return segment;
            }
        }
        return null;
    }

    @Override
    public void removeAllSegments(Coordinate2d location, WallSegmentOrientation orientation)
            throws IllegalArgumentException, EntityDeletedException {
        checkIfLocationValid(location, true);
        Check.ifNull(orientation, "orientation");
        var byX = SEGMENTS.get(orientation).get(location.X);
        if (byX != null && byX.containsKey(location.Y)) {
            var byXY = byX.get(location.Y);
            if (byXY != null) {
                byXY.clear();
            }
        }
        clearEmptyInnerMapsAfterRemoval(SEGMENTS.get(orientation), location);
    }

    private <T> T retrieveTerrain(Coordinate3d loc,
                                  Map<Integer, Map<Integer, Map<Integer, T>>> terrains,
                                  BiFunction<Map<Integer, T>, Integer, T> retrieveFunc) {
        checkIfLocationValid(loc, false);
        if (terrains.containsKey(loc.X) && terrains.get(loc.X).containsKey(loc.Y)) {
            var terrain = retrieveFunc.apply(terrains.get(loc.X).get(loc.Y), loc.Z);
            if (terrains.get(loc.X).get(loc.Y).isEmpty()) {
                terrains.get(loc.X).remove(loc.Y);
            }
            if (terrains.get(loc.X).isEmpty()) {
                terrains.remove(loc.X);
            }
            return terrain;
        }
        return null;
    }

    private <T extends GameZoneTerrain> Map<Integer, T> retrieveAt2dLoc(
            Coordinate2d loc,
            Map<Integer, Map<Integer, Map<Integer, T>>> terrains,
            BiFunction<Map<Integer, Map<Integer, T>>, Integer, Map<Integer, T>> retrieve) {
        if (terrains.containsKey(loc.X) && terrains.get(loc.X).containsKey(loc.Y)) {
            return retrieve.apply(terrains.get(loc.X), loc.Y);
        }
        else {
            return mapOf();
        }
    }

    private <T> void clearEmptyInnerMapsAfterRemoval(
            Map<Integer, Map<Integer, Map<Integer, T>>> map, Coordinate2d loc2d) {
        if (map.get(loc2d.X).get(loc2d.Y).isEmpty()) {
            map.get(loc2d.X).remove(loc2d.Y);
        }
        if (map.get(loc2d.X).isEmpty()) {
            map.remove(loc2d.X);
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<Action> onEntry() {
        return ENTRY_ACTIONS;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<Action> onExit() {
        return EXIT_ACTIONS;
    }

    @Override
    public Map<UUID, Character> charactersRepresentation() {
        return mapOf(CHARACTERS_IN_GAME_ZONE);
    }

    @Override
    public String id() throws IllegalStateException {
        return ID;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        Check.ifNullOrEmpty(name, "name");
        this.name = name;
    }

    @Override
    public VariableCache data() throws IllegalStateException {
        return DATA;
    }

    @Override
    public String getInterfaceName() {
        return GameZone.class.getCanonicalName();
    }

    @Override
    protected String containingClassName() {
        return null;
    }

    @Override
    protected Deletable getContainingObject() {
        return null;
    }

    @Override
    public void afterDeleted() throws IllegalStateException {
    }

    private void checkIfLocationValid(Coordinate2d location, boolean forSegment) {
        Check.ifNull(location, "location");
        checkIfLocationValid(location.to3d(0), forSegment);
    }

    private void checkIfLocationValid(Coordinate3d location, boolean forSegment) {
        Check.ifNull(location, "location");
        Check.ifNonNegative(location.X, "location.X");
        Check.ifNonNegative(location.Y, "location.Y");
        var segmentAddend = forSegment ? 1 : 0;
        Check.throwOnGtValue(location.X, MAX_COORDINATES.X + segmentAddend, "location.X");
        Check.throwOnGtValue(location.Y, MAX_COORDINATES.Y + segmentAddend, "location.Y");
    }
}

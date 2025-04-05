package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.collections.Collections;
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
    public final Map<UUID, Character> CHARACTERS_IN_GAME_ZONE;

    private String name;

    public GameZoneImpl(String id, Coordinate2d maxCoordinates, VariableCache data,
                        Consumer<Character> addToEndOfRoundManager,
                        Consumer<Character> removeFromRoundManager) {
        ID = Check.ifNullOrEmpty(id, "id");
        CHARACTERS_IN_GAME_ZONE = mapOf();

        Check.ifNull(maxCoordinates, "maxCoordinates");
        Check.throwOnLteZero(maxCoordinates.X, "maxCoordinates.X");
        Check.throwOnLteZero(maxCoordinates.Y, "maxCoordinates.Y");
        Check.ifNull(addToEndOfRoundManager, "addToEndOfRoundManager");
        Check.ifNull(removeFromRoundManager, "removeFromRoundManager");

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
    public Tile tile(Coordinate3d location)
            throws IllegalArgumentException, EntityDeletedException {
        checkIfLocationValid(location, false);
        if (TILES.containsKey(location.X) && TILES.get(location.X).containsKey(location.Y)) {
            return TILES.get(location.X).get(location.Y).get(location.Z);
        }
        return null;
    }

    @Override
    public Set<Tile> tiles() throws EntityDeletedException {
        var tiles = Collections.<Tile>setOf();
        TILES.values().forEach(yz -> yz.values().forEach(z -> tiles.addAll(z.values())));
        return tiles;
    }

    @Override
    public Set<Tile> tiles(Coordinate2d location)
            throws IllegalArgumentException, EntityDeletedException {
        checkIfLocationValid(location, false);
        return setOf(terrains(location, TILES).values());
    }

    @Override
    public Tile addTile(Tile tile, Coordinate3d location)
            throws IllegalArgumentException, EntityDeletedException {
        Check.ifNull(tile, "tile");
        checkIfLocationValid(location, false);

        Tile prevTileAtLoc = null;

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
                prevTileAtLoc = tilesByXY.get(location.Z);
                tilesByXY.put(location.Z, tile);
            }
        }

        tile.assignGameZoneAfterAddedToGameZone(this, location);
        return prevTileAtLoc;
    }

    @Override
    public Tile removeTile(Coordinate3d coordinate3d)
            throws IllegalArgumentException, EntityDeletedException {

        return null;
    }

    @Override
    public Set<Tile> removeTiles(Coordinate2d coordinate2d)
            throws IllegalArgumentException, EntityDeletedException {
        return null;
    }

    @Override
    public Map<WallSegmentOrientation, Map<Coordinate3d, WallSegment>> getSegments(
            Coordinate2d location) throws IllegalArgumentException, EntityDeletedException {
        checkIfLocationValid(location, true);

        Map<WallSegmentOrientation, Map<Coordinate3d, WallSegment>> segments =
                mapOf(pairOf(HORIZONTAL, mapOf()), pairOf(VERTICAL, mapOf()),
                        pairOf(CORNER, mapOf()));

        var locNeAndE = addOffsets2d(location, 1, 0);
        var locSwAndS = addOffsets2d(location, 0, 1);
        var locSe = addOffsets2d(location, 1, 1);
        // NW
        terrains(location, SEGMENTS.get(CORNER)).forEach((z, seg) ->
                segments.get(CORNER).put(location.to3d(z), seg));
        // N
        terrains(location, SEGMENTS.get(HORIZONTAL)).forEach((z, seg) ->
                segments.get(HORIZONTAL).put(location.to3d(z), seg));
        // NE
        terrains(locNeAndE, SEGMENTS.get(CORNER)).forEach((z, seg) ->
                segments.get(CORNER).put(locNeAndE.to3d(z), seg));
        // W
        terrains(location, SEGMENTS.get(VERTICAL)).forEach((z, seg) ->
                segments.get(VERTICAL).put(location.to3d(z), seg));
        // E
        terrains(locNeAndE, SEGMENTS.get(VERTICAL)).forEach((z, seg) ->
                segments.get(VERTICAL).put(locNeAndE.to3d(z), seg));
        // SW
        terrains(locSwAndS, SEGMENTS.get(CORNER)).forEach((z, seg) ->
                segments.get(CORNER).put(locSwAndS.to3d(z), seg));
        // S
        terrains(locSwAndS, SEGMENTS.get(HORIZONTAL)).forEach((z, seg) ->
                segments.get(HORIZONTAL).put(locSwAndS.to3d(z), seg));
        // SE
        terrains(locSe, SEGMENTS.get(CORNER)).forEach((z, seg) ->
                segments.get(CORNER).put(locSe.to3d(z), seg));

        return segments;
    }

    @Override
    public WallSegment getSegment(WallSegmentOrientation orientation, Coordinate3d loc)
            throws IllegalArgumentException, EntityDeletedException {
        if (SEGMENTS.get(orientation).containsKey(loc.X) &&
                SEGMENTS.get(orientation).get(loc.X).containsKey(loc.Y)) {
            return SEGMENTS.get(orientation).get(loc.X).get(loc.Y).get(loc.Z);
        }
        return null;
    }

    @Override
    public WallSegment setSegment(Coordinate3d location, WallSegment segment)
            throws IllegalArgumentException, EntityDeletedException {
        Check.ifNull(segment, "segment");
        Check.ifNull(segment.getType(), "segment.getType()");
        Check.ifNull(segment.getType().orientation(), "segment.getType().orientation()");
        checkIfLocationValid(location, true);

        var orientation = segment.getType().orientation();
        if (SEGMENTS.get(orientation).containsKey(location.X)) {
            var byX = SEGMENTS.get(orientation).get(location.X);
            if (byX.containsKey(location.Y)) {
                var byXY = byX.get(location.Y);
                var prev = byXY.get(location.Z);
                byXY.put(location.Z, segment);
                return prev;
            }
            else {
                byX.put(location.Y, mapOf(pairOf(location.Z, segment)));
            }
        }
        else {
            SEGMENTS.get(orientation)
                    .put(location.X, mapOf(pairOf(location.Y, mapOf(pairOf(location.Z, segment)))));
        }

        return null;
    }

    @Override
    public WallSegment removeSegment(Coordinate3d location, WallSegmentOrientation orientation)
            throws IllegalArgumentException, EntityDeletedException {
        Check.ifNull(location, "location");
        Check.ifNull(orientation, "orientation");
        checkIfLocationValid(location, true);

        var byX = SEGMENTS.get(orientation).get(location.X);
        if (byX != null && byX.containsKey(location.Y)) {
            var byXY = byX.get(location.Y);
            if (byXY != null) {
                return byXY.remove(location.Z);
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
    }

    private <T extends GameZoneTerrain> Map<Integer, T> terrains(Coordinate2d location,
                                                                 Map<Integer,
                                                                         Map<Integer, Map<Integer
                                                                                 , T>>> terrains)
            throws IllegalArgumentException, EntityDeletedException {
        if (terrains.containsKey(location.X) && terrains.get(location.X).containsKey(location.Y)) {
            return terrains.get(location.X).get(location.Y);
        }
        else {
            return mapOf();
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

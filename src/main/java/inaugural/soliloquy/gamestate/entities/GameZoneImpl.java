package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.collections.Collections;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Coordinate2d;
import soliloquy.specs.common.valueobjects.Coordinate3d;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static inaugural.soliloquy.tools.collections.Collections.*;
import static inaugural.soliloquy.tools.valueobjects.Pair.pairOf;
import static soliloquy.specs.gamestate.entities.WallSegmentOrientation.*;

public class GameZoneImpl extends HasDeletionInvariants implements GameZone {
    private final String ID;
    private final String TYPE;
    private final Coordinate2d MAX_COORDINATES;
    private final Tile[][] TILES;
    private final Map<WallSegmentOrientation, Map<Coordinate2d, Map<Integer, WallSegment>>> SEGMENTS;
    @SuppressWarnings("rawtypes")
    private final List<Action> ENTRY_ACTIONS;
    @SuppressWarnings("rawtypes")
    private final List<Action> EXIT_ACTIONS;
    private final VariableCache DATA;
    public final Map<UUID, Character> CHARACTERS_IN_GAME_ZONE;

    private String name;

    public GameZoneImpl(String id, String type, Tile[][] tiles,
                        VariableCache data,
                        Consumer<Character> addToEndOfRoundManager,
                        Consumer<Character> removeFromRoundManager) {
        ID = Check.ifNullOrEmpty(id, "id");
        TYPE = Check.ifNullOrEmpty(type, "type");
        CHARACTERS_IN_GAME_ZONE = mapOf();

        Check.ifNull(tiles, "tiles");
        Check.throwOnLteZero(tiles.length, "tiles.length");
        Check.throwOnLteZero(tiles[0].length, "tiles[0].length");
        Check.ifNull(addToEndOfRoundManager, "addToEndOfRoundManager");
        Check.ifNull(removeFromRoundManager, "removeFromRoundManager");

        var tilesWidth = tiles.length;
        var tilesHeight = tiles[0].length;
        TILES = new Tile[tilesWidth][tilesHeight];
        SEGMENTS = mapOf();
        SEGMENTS.put(HORIZONTAL, mapOf());
        SEGMENTS.put(CORNER, mapOf());
        SEGMENTS.put(VERTICAL, mapOf());
        for (var x = 0; x < tilesWidth; x++) {
            for (var y = 0; y < tilesHeight; y++) {
                if (tiles[x][y] == null) {
                    throw new IllegalArgumentException("GameZoneImpl: tiles has null tile at (" +
                            x + "," + y + ")");
                }
                if (tiles[x][y].gameZone() != null) {
                    throw new IllegalArgumentException("GameZoneImpl: tiles has assigned " +
                            "GameZone at (" + x + "," + y + ")");
                }
                if (tiles[x][y].location().X != x || tiles[x][y].location().Y != y) {
                    throw new IllegalArgumentException("GameZoneImpl: tile at Coordinate2d (" + x +
                            "," + y + ") found at different Coordinate2d on insertion, (" + x +
                            "," + y + ")");
                }
                TILES[x][y] = tiles[x][y];
                tiles[x][y].assignGameZoneAfterAddedToGameZone(this);
                tiles[x][y].characters().initializeActionAfterAdding(c -> {
                    CHARACTERS_IN_GAME_ZONE.put(c.uuid(), c);
                    addToEndOfRoundManager.accept(c);
                });
                tiles[x][y].characters().initializeActionAfterRemoving(c -> {
                    CHARACTERS_IN_GAME_ZONE.remove(c.uuid());
                    removeFromRoundManager.accept(c);
                });
                tiles[x][y].characters().forEach(
                        c -> CHARACTERS_IN_GAME_ZONE.put(c.item1().uuid(), c.item1()));
            }
        }
        MAX_COORDINATES = Coordinate2d.of(tilesWidth - 1, tilesHeight - 1);
        ENTRY_ACTIONS = listOf();
        EXIT_ACTIONS = listOf();
        DATA = Check.ifNull(data, "data");
    }

    @Override
    public String type() {
        return TYPE;
    }

    @Override
    public Coordinate2d maxCoordinates() {
        return MAX_COORDINATES;
    }

    @Override
    public Tile tile(Coordinate2d location) throws IllegalArgumentException {
        checkIfLocationValid(location, false);
        return TILES[location.X][location.Y];
    }

    @Override
    public Map<WallSegmentOrientation, Map<Coordinate3d, WallSegment>> getSegments(
            Coordinate2d location) throws IllegalArgumentException {
        checkIfLocationValid(location, true);

        Map<WallSegmentOrientation, Map<Coordinate3d, WallSegment>> segmentLocations = mapOf();
        segmentLocations.put(HORIZONTAL, mapOf());
        segmentLocations.put(CORNER, mapOf());
        segmentLocations.put(VERTICAL, mapOf());

        var locationToWest = Coordinate2d.of(location.X + 1, location.Y);
        var locationToSouth = Coordinate2d.of(location.X, location.Y + 1);
        var locationToSouthwest = Coordinate2d.of(location.X + 1, location.Y + 1);
        var adjacentSegments = setOf(
                pairOf(CORNER, location),
                pairOf(HORIZONTAL, location),
                pairOf(CORNER, locationToWest),
                pairOf(VERTICAL, location),
                pairOf(VERTICAL, locationToWest),
                pairOf(CORNER, locationToSouth),
                pairOf(HORIZONTAL, locationToSouth),
                pairOf(CORNER, locationToSouthwest)
        );
        adjacentSegments.forEach(segmentsToGet -> segmentLocations.get(segmentsToGet.item1())
                .putAll(getOrDefaultAndAdd(SEGMENTS.get(segmentsToGet.item1()),
                        segmentsToGet.item2(), Collections::mapOf).entrySet().stream().collect(
                        Collectors.toMap(kv -> segmentsToGet.item2().to3d(kv.getKey()),
                                Map.Entry::getValue))));

        return segmentLocations;
    }

    @Override
    public void setSegment(Coordinate3d location, WallSegment segment)
            throws IllegalArgumentException {
        Check.ifNull(segment, "segment");
        Check.ifNull(segment.getType(), "segment.getType()");
        Check.ifNull(segment.getType().direction(), "segment.getType().direction()");
        checkIfLocationValid(location.to2d(), true);

        getOrDefaultAndAdd(SEGMENTS.get(segment.getType().direction()), location.to2d(),
                Collections::mapOf).put(location.Z, segment);
    }

    @Override
    public boolean removeSegment(Coordinate3d location, WallSegmentOrientation direction)
            throws IllegalArgumentException {
        Check.ifNull(location, "location");
        Check.ifNull(direction, "direction");
        checkIfLocationValid(location.to2d(), true);
        return removeChildMapKeyAndChildIfEmpty(SEGMENTS.get(direction), location.to2d(),
                location.Z);
    }

    @Override
    public void removeAllSegments(Coordinate2d location, WallSegmentOrientation direction)
            throws IllegalArgumentException {
        Check.ifNull(location, "location");
        Check.ifNull(direction, "direction");
        checkIfLocationValid(location, true);
        SEGMENTS.get(direction).remove(location);
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
        for (var x = 0; x <= MAX_COORDINATES.X; x++) {
            for (var y = 0; y <= MAX_COORDINATES.Y; y++) {
                TILES[x][y].delete();
            }
        }
    }

    private void checkIfLocationValid(Coordinate2d location, boolean forSegment) {
        Check.ifNull(location, "location");
        Check.ifNonNegative(location.X, "location.X");
        Check.ifNonNegative(location.Y, "location.Y");
        var segmentAddend = forSegment ? 1 : 0;
        Check.throwOnGtValue(location.X, MAX_COORDINATES.X + segmentAddend, "location.X");
        Check.throwOnGtValue(location.Y, MAX_COORDINATES.Y + segmentAddend, "location.Y");
    }
}

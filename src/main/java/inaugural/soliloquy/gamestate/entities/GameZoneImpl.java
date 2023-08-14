package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.Character;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.collections.Collections.mapOf;

public class GameZoneImpl extends HasDeletionInvariants implements GameZone {
    private final String ID;
    private final String TYPE;
    private final Coordinate MAX_COORDINATES;
    private final Tile[][] TILES;
    private final Map<Integer, WallSegment>[][] NORTH_SEGMENTS;
    private final Map<Integer, WallSegment>[][] NORTHWEST_SEGMENTS;
    private final Map<Integer, WallSegment>[][] WEST_SEGMENTS;
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
        //noinspection unchecked
        NORTH_SEGMENTS = new Map[tilesWidth + 1][tilesHeight];
        //noinspection unchecked
        NORTHWEST_SEGMENTS = new Map[tilesWidth + 1][tilesHeight];
        //noinspection unchecked
        WEST_SEGMENTS = new Map[tilesWidth + 1][tilesHeight];
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
                if (tiles[x][y].location().x() != x || tiles[x][y].location().y() != y) {
                    throw new IllegalArgumentException("GameZoneImpl: tile at coordinate (" + x +
                            "," + y + ") found at different coordinate on insertion, (" + x +
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
                        c -> CHARACTERS_IN_GAME_ZONE.put(c.getItem1().uuid(), c.getItem1()));
            }
        }
        MAX_COORDINATES = Coordinate.of(tilesWidth - 1, tilesHeight - 1);
        ENTRY_ACTIONS = listOf();
        EXIT_ACTIONS = listOf();
        DATA = Check.ifNull(data, "data");
    }

    @Override
    public String type() {
        return TYPE;
    }

    @Override
    public Coordinate maxCoordinates() {
        return MAX_COORDINATES;
    }

    @Override
    public Tile tile(Coordinate location) throws IllegalArgumentException {
        checkIfLocationValid(location, false);
        return TILES[location.x()][location.y()];
    }

    @Override
    public Map<Integer, WallSegment> getSegments(Coordinate location,
                                                 WallSegmentDirection direction)
            throws IllegalArgumentException {
        checkIfLocationValid(location, true);
        Check.ifNull(direction, "direction");

        Map<Integer, WallSegment>[][] segmentsOfDirection;
        switch(direction){
            case NORTH -> segmentsOfDirection = NORTH_SEGMENTS;
            case NORTHWEST -> segmentsOfDirection = NORTHWEST_SEGMENTS;
            case WEST -> segmentsOfDirection = WEST_SEGMENTS;
            default -> throw new IllegalArgumentException("GameZoneImpl.getSegmentsAtLocation: invalid direction");
        }

        var segmentsAtLocation = segmentsOfDirection[location.x()][location.y()];
        if (segmentsAtLocation == null) {
            segmentsAtLocation = segmentsOfDirection[location.x()][location.y()] = mapOf();
        }

        return segmentsAtLocation;
    }

    @Override
    public void setSegment(Coordinate location, int z, WallSegment segment)
            throws IllegalArgumentException {
        Check.ifNull(segment, "segment");
        Check.ifNull(segment.getType(), "segment.getType()");
        Check.ifNull(segment.getType().direction(), "segment.getType().direction()");

        var segmentsAtLocation = getSegments(location, segment.getType().direction());

        segmentsAtLocation.put(z, segment);
    }

    @Override
    public boolean removeSegment(Coordinate location, int z, WallSegmentDirection direction)
            throws IllegalArgumentException {
        var segmentsAtLocation = getSegments(location, direction);
        if (segmentsAtLocation == null) {
            return false;
        }
        if (!segmentsAtLocation.containsKey(z)) {
            return false;
        }
        segmentsAtLocation.remove(z);
        return true;
    }

    @Override
    public void removeAllSegments(Coordinate location, WallSegmentDirection direction)
            throws IllegalArgumentException {
        getSegments(location, direction).clear();
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
        if (name == null) {
            throw new IllegalArgumentException("GameZoneImpl.setName: name cannot be null");
        }
        if (name.equals("")) {
            throw new IllegalArgumentException("GameZoneImpl.setName: name cannot be empty");
        }
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
        for (var x = 0; x <= MAX_COORDINATES.x(); x++) {
            for (var y = 0; y <= MAX_COORDINATES.y(); y++) {
                TILES[x][y].delete();
            }
        }
    }

    private void checkIfLocationValid(Coordinate location, boolean forSegment) {
        Check.ifNull(location, "location");
        Check.ifNonNegative(location.x(), "location.x()");
        Check.ifNonNegative(location.y(), "location.y()");
        var segmentAddend = forSegment ? 1 : 0;
        Check.throwOnGtValue(location.x(), MAX_COORDINATES.x() + segmentAddend, "location.x()");
        Check.throwOnGtValue(location.y(), MAX_COORDINATES.y() + segmentAddend, "location.y()");
    }
}

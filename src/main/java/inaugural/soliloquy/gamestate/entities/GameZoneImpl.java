package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GameZoneImpl extends HasDeletionInvariants implements GameZone {
    private final String ID;
    private final String TYPE;
    private final Coordinate MAX_COORDINATES;
    private final Tile[][] TILES;
    @SuppressWarnings("rawtypes")
    private final List<Action> ENTRY_ACTIONS;
    @SuppressWarnings("rawtypes")
    private final List<Action> EXIT_ACTIONS;
    private final VariableCache DATA;
    public final List<Character> CHARACTERS_IN_GAME_ZONE;

    private String _name;

    public GameZoneImpl(String id, String type, Tile[][] tiles,
                        VariableCache data,
                        Consumer<Character> addToEndOfRoundManager,
                        Consumer<Character> removeFromRoundManager) {
        ID = Check.ifNullOrEmpty(id, "id");
        TYPE = Check.ifNullOrEmpty(type, "type");
        CHARACTERS_IN_GAME_ZONE = new ArrayList<>();

        Check.ifNull(tiles, "tiles");
        Check.throwOnLteZero(tiles.length, "tiles.length");
        Check.throwOnLteZero(tiles[0].length, "tiles[0].length");
        Check.ifNull(addToEndOfRoundManager, "addToEndOfRoundManager");
        Check.ifNull(removeFromRoundManager, "removeFromRoundManager");

        TILES = new Tile[tiles.length][tiles[0].length];
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
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
                    CHARACTERS_IN_GAME_ZONE.add(c);
                    addToEndOfRoundManager.accept(c);
                });
                tiles[x][y].characters().initializeActionAfterRemoving(c -> {
                    CHARACTERS_IN_GAME_ZONE.remove(c);
                    removeFromRoundManager.accept(c);
                });
                tiles[x][y].characters().forEach(c -> CHARACTERS_IN_GAME_ZONE.add(c.getItem1()));
            }
        }
        MAX_COORDINATES = Coordinate.of(tiles.length - 1, tiles[0].length - 1);
        ENTRY_ACTIONS = new ArrayList<>();
        EXIT_ACTIONS = new ArrayList<>();
        DATA = Check.ifNull(data, "data");
    }

    @Override
    public String type() {
        return TYPE;
    }

    // TODO: Ensure that this is a clone
    @Override
    public Coordinate maxCoordinates() {
        return MAX_COORDINATES;
    }

    @Override
    public Tile tile(int x, int y) throws IllegalArgumentException {
        if (x < 0) {
            throw new IllegalArgumentException("GameZoneImpl.tile: x cannot be negative");
        }
        if (y < 0) {
            throw new IllegalArgumentException("GameZoneImpl.tile: y cannot be negative");
        }
        if (x > MAX_COORDINATES.x()) {
            throw new IllegalArgumentException("GameZoneImpl.tile: x is beyond max x coordinate");
        }
        if (y > MAX_COORDINATES.y()) {
            throw new IllegalArgumentException("GameZoneImpl.tile: y is beyond max y coordinate");
        }
        return TILES[x][y];
    }

    // TODO: Ensure this object is a clone
    @SuppressWarnings("rawtypes")
    @Override
    public List<Action> onEntry() {
        return new ArrayList<>(ENTRY_ACTIONS);
    }

    // TODO: Ensure this object is a clone
    @SuppressWarnings("rawtypes")
    @Override
    public List<Action> onExit() {
        return new ArrayList<>(EXIT_ACTIONS);
    }

    // TODO: Ensure this object is a clone
    @Override
    public List<Character> charactersRepresentation() {
        return new ArrayList<>(CHARACTERS_IN_GAME_ZONE);
    }

    @Override
    public String id() throws IllegalStateException {
        return ID;
    }

    @Override
    public String getName() {
        return _name;
    }

    @Override
    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("GameZoneImpl.setName: name cannot be null");
        }
        if (name.equals("")) {
            throw new IllegalArgumentException("GameZoneImpl.setName: name cannot be empty");
        }
        _name = name;
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
        for (int x = 0; x <= MAX_COORDINATES.x(); x++) {
            for (int y = 0; y <= MAX_COORDINATES.y(); y++) {
                TILES[x][y].delete();
            }
        }
    }
}

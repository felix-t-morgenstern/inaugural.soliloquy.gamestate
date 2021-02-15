package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.CharacterArchetype;
import inaugural.soliloquy.gamestate.archetypes.VoidActionArchetype;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;

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

    @SuppressWarnings("rawtypes")
    private final static Action ACTION_ARCHETYPE = new VoidActionArchetype();
    private final static Character CHARACTER_ARCHETYPE = new CharacterArchetype();

    private String _name;

    @SuppressWarnings("ConstantConditions")
    public GameZoneImpl(String id, String type, Tile[][] tiles,
                        CoordinateFactory coordinateFactory, ListFactory listFactory,
                        VariableCache data, Consumer<Character> addToEndOfRoundManager,
                        Consumer<Character> removeFromRoundManager) {
        ID = Check.ifNullOrEmpty(id, "id");
        TYPE = Check.ifNullOrEmpty(type, "type");
        CHARACTERS_IN_GAME_ZONE = Check.ifNull(listFactory, "listFactory")
                .make(CHARACTER_ARCHETYPE);

        Check.ifNull(tiles, "tiles");
        Check.throwOnLteZero(tiles.length, "tiles.length");
        Check.throwOnLteZero(tiles[0].length, "tiles[0].length");
        Check.ifNull(addToEndOfRoundManager, "addToEndOfRoundManager");
        Check.ifNull(removeFromRoundManager, "removeFromRoundManager");

        TILES = new Tile[tiles.length][tiles[0].length];
        for(int x = 0; x < tiles.length; x++) {
            for(int y = 0; y < tiles[0].length; y++) {
                if (tiles[x][y] == null) {
                    throw new IllegalArgumentException("GameZoneImpl: tiles has null tile at (" +
                            x + "," + y + ")");
                }
                if (tiles[x][y].gameZone() != null) {
                    throw new IllegalArgumentException("GameZoneImpl: tiles has assigned " +
                            "GameZone at (" + x + "," + y + ")");
                }
                if (tiles[x][y].location().getX() != x || tiles[x][y].location().getY() != y) {
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
        if (coordinateFactory == null) {
            throw new IllegalArgumentException("GameZoneImpl: coordinateFactory cannot be null");
        }
        MAX_COORDINATES = coordinateFactory.make(tiles.length - 1,tiles[0].length - 1);
        ENTRY_ACTIONS = listFactory.make(ACTION_ARCHETYPE);
        EXIT_ACTIONS = listFactory.make(ACTION_ARCHETYPE);
        DATA = Check.ifNull(data, "data");
    }

    @Override
    public String type() {
        return TYPE;
    }

    // TODO: Ensure that this is a clone
    @Override
    public Coordinate maxCoordinates() {
        return MAX_COORDINATES.makeClone();
    }

    @Override
    public Tile tile(int x, int y) throws IllegalArgumentException {
        if (x < 0) {
            throw new IllegalArgumentException("GameZoneImpl.tile: x cannot be negative");
        }
        if (y < 0) {
            throw new IllegalArgumentException("GameZoneImpl.tile: y cannot be negative");
        }
        if (x > MAX_COORDINATES.getX()) {
            throw new IllegalArgumentException("GameZoneImpl.tile: x is beyond max x coordinate");
        }
        if (y > MAX_COORDINATES.getY()) {
            throw new IllegalArgumentException("GameZoneImpl.tile: y is beyond max y coordinate");
        }
        return TILES[x][y];
    }

    // TODO: Ensure this object is a clone
    @SuppressWarnings("rawtypes")
    @Override
    public List<Action> onEntry() {
        return ENTRY_ACTIONS.makeClone();
    }

    // TODO: Ensure this object is a clone
    @SuppressWarnings("rawtypes")
    @Override
    public List<Action> onExit() {
        return EXIT_ACTIONS.makeClone();
    }

    // TODO: Ensure this object is a clone
    @Override
    public List<Character> charactersRepresentation() {
        return CHARACTERS_IN_GAME_ZONE.makeClone();
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
    protected String className() {
        return "GameZoneImpl";
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
        for(int x = 0; x <= MAX_COORDINATES.getX(); x++) {
            for(int y = 0; y <= MAX_COORDINATES.getY(); y++) {
                TILES[x][y].delete();
            }
        }
    }
}

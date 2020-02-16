package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.CharacterArchetype;
import inaugural.soliloquy.gamestate.archetypes.VoidActionArchetype;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;

public class GameZoneImpl extends HasDeletionInvariants implements GameZone {
    private final String ID;
    private final String TYPE;
    private final ReadableCoordinate MAX_COORDINATES;
    private final Tile[][] TILES;
    @SuppressWarnings("rawtypes")
    private final Collection<Action> ENTRY_ACTIONS;
    @SuppressWarnings("rawtypes")
    private final Collection<Action> EXIT_ACTIONS;
    private final VariableCache DATA;
    public final Collection<Character> CHARACTERS_IN_GAME_ZONE;

    @SuppressWarnings("rawtypes")
    private final static Action ACTION_ARCHETYPE = new VoidActionArchetype();
    private final static Character CHARACTER_ARCHETYPE = new CharacterArchetype();

    private String _name;

    @SuppressWarnings("ConstantConditions")
    public GameZoneImpl(String id, String type, Tile[][] tiles,
                        CoordinateFactory coordinateFactory, CollectionFactory collectionFactory,
                        VariableCache data) {
        if (id == null) {
            throw new IllegalArgumentException("GameZoneImpl: id cannot be null");
        }
        if (id.equals("")) {
            throw new IllegalArgumentException("GameZoneImpl: id cannot be empty");
        }
        ID = id;
        if (type == null) {
            throw new IllegalArgumentException("GameZoneImpl: type cannot be null");
        }
        if (type.equals("")) {
            throw new IllegalArgumentException("GameZoneImpl: type cannot be empty");
        }
        TYPE = type;
        if (collectionFactory == null) {
            throw new IllegalArgumentException("GameZoneImpl: tileFactory cannot be null");
        }
        CHARACTERS_IN_GAME_ZONE = collectionFactory.make(CHARACTER_ARCHETYPE);
        if (tiles == null) {
            throw new IllegalArgumentException("GameZoneImpl: tiles is null");
        }
        if (tiles.length == 0) {
            throw new IllegalArgumentException("GameZoneImpl: tiles has x length of 0");
        }
        if (tiles[0].length == 0) {
            throw new IllegalArgumentException("GameZoneImpl: tiles has y length of 0");
        }
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
                tiles[x][y].characters().assignAddToGameZoneActionAfterAddingToGameZone(
                        CHARACTERS_IN_GAME_ZONE::add);
                tiles[x][y].characters().assignRemoveFromGameZoneActionAfterAddingToGameZone(
                        CHARACTERS_IN_GAME_ZONE::remove);
                tiles[x][y].characters().forEach(c -> CHARACTERS_IN_GAME_ZONE.add(c.getItem1()));
            }
        }
        if (coordinateFactory == null) {
            throw new IllegalArgumentException("GameZoneImpl: coordinateFactory cannot be null");
        }
        MAX_COORDINATES = coordinateFactory.make(tiles.length - 1,tiles[0].length - 1);
        ENTRY_ACTIONS = collectionFactory.make(ACTION_ARCHETYPE);
        EXIT_ACTIONS = collectionFactory.make(ACTION_ARCHETYPE);
        if (data == null) {
            throw new IllegalArgumentException("GameZoneImpl: data cannot be null");
        }
        DATA = data;
    }

    @Override
    public String type() {
        return TYPE;
    }

    @Override
    public ReadableCoordinate maxCoordinates() {
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
        if (x > MAX_COORDINATES.getX()) {
            throw new IllegalArgumentException("GameZoneImpl.tile: x is beyond max x coordinate");
        }
        if (y > MAX_COORDINATES.getY()) {
            throw new IllegalArgumentException("GameZoneImpl.tile: y is beyond max y coordinate");
        }
        return TILES[x][y];
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Collection<Action> onEntry() {
        return ENTRY_ACTIONS;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Collection<Action> onExit() {
        return EXIT_ACTIONS;
    }

    @Override
    public ReadableCollection<Character> charactersRepresentation() {
        return CHARACTERS_IN_GAME_ZONE.representation();
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

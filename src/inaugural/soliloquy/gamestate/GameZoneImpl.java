package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.VoidActionArchetype;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.TileFactory;

public class GameZoneImpl extends HasDeletionInvariants implements GameZone {
    private final String ID;
    private final String TYPE;
    private final ReadableCoordinate MAX_COORDINATES;
    private final Tile[][] TILES;
    private final Collection<Action<Void>> ENTRY_ACTIONS;
    private final Collection<Action<Void>> EXIT_ACTIONS;
    private final GenericParamsSet DATA;

    private final static Action<Void> ACTION_ARCHETYPE = new VoidActionArchetype();

    private String _name;

    // TODO: Have this use the PersistentTileHandler!
    @SuppressWarnings("ConstantConditions")
    public GameZoneImpl(String id, String name, String type, ReadableCoordinate maxCoordinates,
                        TileFactory tileFactory, CoordinateFactory coordinateFactory,
                        CollectionFactory collectionFactory,
                        GenericParamsSet data) {
        if (id == null) {
            throw new IllegalArgumentException("GameZoneImpl: id cannot be null");
        }
        if (id.equals("")) {
            throw new IllegalArgumentException("GameZoneImpl: id cannot be empty");
        }
        ID = id;
        if (name == null) {
            throw new IllegalArgumentException("GameZoneImpl: name cannot be null");
        }
        if (name.equals("")) {
            throw new IllegalArgumentException("GameZoneImpl: name cannot be empty");
        }
        _name = name;
        TYPE = type;
        if (maxCoordinates == null) {
            throw new IllegalArgumentException("GameZoneImpl: maxCoordinates cannot be null");
        }
        MAX_COORDINATES = maxCoordinates;
        if (tileFactory == null) {
            throw new IllegalArgumentException("GameZoneImpl: tileFactory cannot be null");
        }
        if (coordinateFactory == null) {
            throw new IllegalArgumentException("GameZoneImpl: coordinateFactory cannot be null");
        }
        if (collectionFactory == null) {
            throw new IllegalArgumentException("GameZoneImpl: tileFactory cannot be null");
        }
        TILES = new Tile[maxCoordinates.getX()+1][maxCoordinates.getY()+1];
        for (int x = 0; x <= maxCoordinates.getX(); x++) {
            for (int y = 0; y <= maxCoordinates.getY(); y++) {
                // TODO: Have this use the PersistentTileHandler!
                TILES[x][y] = tileFactory.make(this, coordinateFactory.make(x,y), null);
            }
        }
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
    public ReadableCoordinate getMaxCoordinates() {
        return MAX_COORDINATES;
    }

    @Override
    public Tile tile(ReadableCoordinate readableCoordinate) throws IllegalArgumentException {
        // TODO: Throw on invalid params
        return TILES[readableCoordinate.getX()][readableCoordinate.getY()];
    }

    @Override
    public Collection<Action<Void>> onEntry() {
        return ENTRY_ACTIONS;
    }

    @Override
    public Collection<Action<Void>> onExit() {
        return EXIT_ACTIONS;
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
    public GenericParamsSet data() throws IllegalStateException {
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

package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.GenericParamsSetFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameEvent;
import soliloquy.specs.gamestate.factories.TileCharactersFactory;
import soliloquy.specs.gamestate.factories.TileFixturesFactory;
import soliloquy.specs.gamestate.factories.TileItemsFactory;
import soliloquy.specs.gamestate.factories.TileWallSegmentsFactory;
import soliloquy.specs.ruleset.entities.GroundType;
import soliloquy.specs.sprites.entities.Sprite;

public class TileImpl extends HasDeletionInvariants implements Tile {
    private final GameZone GAME_ZONE;
    private final ReadableCoordinate LOCATION;
    private final TileCharacters TILE_CHARACTERS;
    private final TileItems TILE_ITEMS;
    private final TileFixtures TILE_FIXTURES;
    private final TileWallSegments TILE_WALL_SEGMENTS;
    private final Map<Integer, Collection<Sprite>> SPRITES;
    private final GenericParamsSet DATA;

    private int _height;
    private GroundType _groundType;

    public TileImpl(GameZone gameZone, ReadableCoordinate location,
                    TileCharactersFactory tileCharactersFactory, TileItemsFactory tileItemsFactory,
                    TileFixturesFactory tileFixturesFactory,
                    TileWallSegmentsFactory tileWallSegmentsFactory, MapFactory mapFactory,
                    CollectionFactory collectionFactory, Sprite spriteArchetype,
                    GenericParamsSetFactory genericParamsSetFactory) {
        if (gameZone == null) {
            throw new IllegalArgumentException("TileImpl: gameZone cannot be null");
        }
        GAME_ZONE = gameZone;
        if (location == null) {
            throw new IllegalArgumentException("TileImpl: location cannot be null");
        }
        LOCATION = location.makeClone().readOnlyRepresentation();
        if (tileCharactersFactory == null) {
            throw new IllegalArgumentException("TileImpl: tileCharactersFactory cannot be null");
        }
        TILE_CHARACTERS = tileCharactersFactory.make(this);
        if (tileItemsFactory == null) {
            throw new IllegalArgumentException("TileImpl: tileItemsFactory cannot be null");
        }
        TILE_ITEMS = tileItemsFactory.make(this);
        if (tileFixturesFactory == null) {
            throw new IllegalArgumentException("TileImpl: tileFixturesFactory cannot be null");
        }
        TILE_FIXTURES = tileFixturesFactory.make(this);
        if (tileWallSegmentsFactory == null) {
            throw new IllegalArgumentException("TileImpl: tileWallSegmentsFactory cannot be null");
        }
        TILE_WALL_SEGMENTS = tileWallSegmentsFactory.make(this);
        if (mapFactory == null) {
            throw new IllegalArgumentException("TileImpl: mapFactory cannot be null");
        }
        if (collectionFactory == null) {
            throw new IllegalArgumentException("TileImpl: collectionFactory cannot be null");
        }
        if (spriteArchetype == null) {
            throw new IllegalArgumentException("TileImpl: spriteArchetype cannot be null");
        }
        SPRITES = mapFactory.make(0, collectionFactory.make(spriteArchetype));
        if (genericParamsSetFactory == null) {
            throw new IllegalArgumentException("TileImpl: genericParamsSetFactory cannot be null");
        }
        DATA = genericParamsSetFactory.make();
    }

    @Override
    public GameZone gameZone() throws IllegalStateException {
        return GAME_ZONE;
    }

    @Override
    public ReadableCoordinate location() throws IllegalStateException {
        return LOCATION;
    }

    @Override
    public int getHeight() throws IllegalStateException {
        return _height;
    }

    @Override
    public void setHeight(int height) throws IllegalStateException {
        _height = height;
    }

    @Override
    public GroundType getGroundType() throws IllegalStateException {
        return _groundType;
    }

    @Override
    public void setGroundType(GroundType groundType) throws IllegalStateException {
        _groundType = groundType;
    }

    @Override
    public TileCharacters characters() {
        return TILE_CHARACTERS;
    }

    @Override
    public TileItems items() {
        return TILE_ITEMS;
    }

    @Override
    public TileFixtures fixtures() throws IllegalStateException {
        return TILE_FIXTURES;
    }

    @Override
    public TileWallSegments wallSegments() throws IllegalStateException {
        return TILE_WALL_SEGMENTS;
    }

    @Override
    public Map<Integer, Collection<Sprite>> sprites() throws IllegalStateException {
        return SPRITES;
    }

    @Override
    public GenericParamsSet data() throws IllegalStateException {
        return DATA;
    }

    @Override
    protected String className() {
        return "TileImpl";
    }

    @Override
    protected String containingClassName() {
        return "GameZone";
    }

    @Override
    protected boolean containingObjectIsDeleted() {
        return false;
    }

    @Override
    public void delete() throws IllegalStateException {

    }

    @Override
    public boolean isDeleted() {
        return false;
    }

    @Override
    public void deleteAfterDeletingContainingGameZone() throws IllegalStateException {

    }

    @Override
    public Collection<GameEvent> events() throws IllegalStateException {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

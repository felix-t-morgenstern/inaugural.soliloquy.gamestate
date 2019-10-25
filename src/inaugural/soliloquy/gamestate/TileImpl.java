package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.GenericParamsSetFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.gamestate.factories.TileCharactersFactory;
import soliloquy.specs.gamestate.factories.TileFixturesFactory;
import soliloquy.specs.gamestate.factories.TileItemsFactory;
import soliloquy.specs.gamestate.factories.TileWallSegmentsFactory;
import soliloquy.specs.ruleset.entities.GroundType;
import soliloquy.specs.sprites.entities.Sprite;

public class TileImpl extends GameEventTargetEntityImpl implements Tile {
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
    private boolean _isDeleted;

    @SuppressWarnings("ConstantConditions")
    public TileImpl(GameZone gameZone, ReadableCoordinate location,
                    TileCharactersFactory tileCharactersFactory, TileItemsFactory tileItemsFactory,
                    TileFixturesFactory tileFixturesFactory,
                    TileWallSegmentsFactory tileWallSegmentsFactory, MapFactory mapFactory,
                    CollectionFactory collectionFactory, Sprite spriteArchetype,
                    GenericParamsSetFactory genericParamsSetFactory) {
        super(collectionFactory);
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
        enforceDeletionInvariant("gameZone");
        enforceLocationCorrespondenceInvariant("gameZone");
        return GAME_ZONE;
    }

    @Override
    public ReadableCoordinate location() throws IllegalStateException {
        enforceDeletionInvariant("location");
        enforceLocationCorrespondenceInvariant("location");
        return LOCATION;
    }

    @Override
    public int getHeight() throws IllegalStateException {
        enforceDeletionInvariant("getHeight");
        enforceLocationCorrespondenceInvariant("getHeight");
        return _height;
    }

    @Override
    public void setHeight(int height) throws IllegalStateException {
        enforceDeletionInvariant("setHeight");
        enforceLocationCorrespondenceInvariant("setHeight");
        _height = height;
    }

    @Override
    public GroundType getGroundType() throws IllegalStateException {
        enforceDeletionInvariant("getGroundType");
        enforceLocationCorrespondenceInvariant("getGroundType");
        return _groundType;
    }

    @Override
    public void setGroundType(GroundType groundType) throws IllegalStateException {
        enforceDeletionInvariant("setGroundType");
        enforceLocationCorrespondenceInvariant("setGroundType");
        _groundType = groundType;
    }

    @Override
    public TileCharacters characters() {
        enforceDeletionInvariant("characters");
        enforceLocationCorrespondenceInvariant("characters");
        return TILE_CHARACTERS;
    }

    @Override
    public TileItems items() {
        enforceDeletionInvariant("items");
        enforceLocationCorrespondenceInvariant("items");
        return TILE_ITEMS;
    }

    @Override
    public TileFixtures fixtures() throws IllegalStateException {
        enforceDeletionInvariant("fixtures");
        enforceLocationCorrespondenceInvariant("fixtures");
        return TILE_FIXTURES;
    }

    @Override
    public TileWallSegments wallSegments() throws IllegalStateException {
        enforceDeletionInvariant("wallSegments");
        enforceLocationCorrespondenceInvariant("wallSegments");
        return TILE_WALL_SEGMENTS;
    }

    @Override
    public Map<Integer, Collection<Sprite>> sprites() throws IllegalStateException {
        enforceDeletionInvariant("sprites");
        enforceLocationCorrespondenceInvariant("sprites");
        return SPRITES;
    }

    @Override
    public GenericParamsSet data() throws IllegalStateException {
        enforceDeletionInvariant("data");
        enforceLocationCorrespondenceInvariant("data");
        return DATA;
    }

    @Override
    public boolean isDeleted() {
        return _isDeleted;
    }

    @Override
    public void deleteAfterDeletingContainingGameZone() throws IllegalStateException {
        if (!GAME_ZONE.isDeleted()) {
            throw new IllegalStateException("TileImpl.deleteAfterDeletingContainingGameZone: " +
                    "containing GameZone has not been deleted");
        }
        _isDeleted = true;
        TILE_CHARACTERS.delete();
        TILE_FIXTURES.delete();
        TILE_ITEMS.delete();
        TILE_WALL_SEGMENTS.delete();
    }

    private void enforceDeletionInvariant(String methodName) {
        if (_isDeleted) {
            throw new IllegalStateException("TileImpl." + methodName + ": Tile is deleted");
        }
    }

    @Override
    public GameEventTarget makeGameEventTarget() throws IllegalStateException {
        Tile tile = this;
        return new GameEventTarget() {
            @Override
            public Tile tile() {
                return tile;
            }

            @Override
            public TileFixture tileFixture() {
                return null;
            }

            @Override
            public String getInterfaceName() {
                return GameEventTarget.class.getCanonicalName();
            }
        };
    }

    @Override
    public String getInterfaceName() {
        return Tile.class.getCanonicalName();
    }

    private void enforceLocationCorrespondenceInvariant(String methodName) {
        if (GAME_ZONE.tile(LOCATION) != this) {
            throw new IllegalStateException("TileImpl." + methodName + ": This Tile is not " +
                    "present at its stated location (" + LOCATION.getX() + "," + LOCATION.getY() +
                    ") in its containing GameZone");
        }
    }

    @Override
    void enforceInvariants(String methodName) {
        enforceDeletionInvariant(methodName);
        enforceLocationCorrespondenceInvariant(methodName);
    }
}

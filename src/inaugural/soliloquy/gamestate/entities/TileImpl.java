package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.gamestate.archetypes.CharacterArchetype;
import inaugural.soliloquy.gamestate.archetypes.ItemArchetype;
import inaugural.soliloquy.gamestate.archetypes.TileFixtureArchetype;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.gamestate.factories.TileEntitiesFactory;
import soliloquy.specs.gamestate.factories.TileWallSegmentsFactory;
import soliloquy.specs.graphics.assets.Sprite;
import soliloquy.specs.ruleset.entities.GroundType;

import java.util.HashMap;
import java.util.Map;

public class TileImpl extends AbstractGameEventTargetEntity implements Tile {
    private final Coordinate LOCATION;
    private final TileEntities<Character> TILE_CHARACTERS;
    private final TileEntities<Item> TILE_ITEMS;
    private final TileEntities<TileFixture> TILE_FIXTURES;
    private final TileWallSegments TILE_WALL_SEGMENTS;
    private final Map<Sprite, Integer> SPRITES;
    private final VariableCache DATA;

    private GameZone _gameZone;
    private int _height;
    private GroundType _groundType;

    private final static Character CHARACTER_ARCHETYPE = new CharacterArchetype();
    private final static Item ITEM_ARCHETYPE = new ItemArchetype();
    private final static TileFixture TILE_FIXTURE_ARCHETYPE = new TileFixtureArchetype();

    @SuppressWarnings("ConstantConditions")
    public TileImpl(int x, int y,
                    CoordinateFactory coordinateFactory,
                    TileEntitiesFactory tileEntitiesFactory,
                    TileWallSegmentsFactory tileWallSegmentsFactory,
                    VariableCache data) {
        super();
        if (coordinateFactory == null) {
            throw new IllegalArgumentException("TileImpl: coordinateFactory cannot be null");
        }
        LOCATION = coordinateFactory.make(x, y);
        if (tileEntitiesFactory == null) {
            throw new IllegalArgumentException("TileImpl: tileEntitiesFactory cannot be null");
        }
        // TODO: Test and implement whether add and remove from gameZone works
        TILE_CHARACTERS = tileEntitiesFactory.make(this, CHARACTER_ARCHETYPE);
        TILE_ITEMS = tileEntitiesFactory.make(this, ITEM_ARCHETYPE);
        TILE_FIXTURES = tileEntitiesFactory.make(this, TILE_FIXTURE_ARCHETYPE);
        if (tileWallSegmentsFactory == null) {
            throw new IllegalArgumentException("TileImpl: tileWallSegmentsFactory cannot be null");
        }
        TILE_WALL_SEGMENTS = tileWallSegmentsFactory.make(this);
        SPRITES = new HashMap<>();
        if (data == null) {
            throw new IllegalArgumentException("TileImpl: data cannot be null");
        }
        DATA = data;
    }

    @Override
    public GameZone gameZone() throws IllegalStateException {
        enforceDeletionInvariants();
        enforceLocationCorrespondenceInvariant("gameZone");
        return _gameZone;
    }

    // TODO: Ensure that clone is made
    @Override
    public Coordinate location() throws IllegalStateException {
        enforceDeletionInvariants();
        enforceLocationCorrespondenceInvariant("location");
        return LOCATION.makeClone();
    }

    @Override
    public int getHeight() throws IllegalStateException {
        enforceDeletionInvariants();
        enforceLocationCorrespondenceInvariant("getHeight");
        return _height;
    }

    @Override
    public void setHeight(int height) throws IllegalStateException {
        enforceDeletionInvariants();
        enforceLocationCorrespondenceInvariant("setHeight");
        _height = height;
    }

    @Override
    public GroundType getGroundType() throws IllegalStateException {
        enforceDeletionInvariants();
        enforceLocationCorrespondenceInvariant("getGroundType");
        return _groundType;
    }

    @Override
    public void setGroundType(GroundType groundType) throws IllegalStateException {
        enforceDeletionInvariants();
        enforceLocationCorrespondenceInvariant("setGroundType");
        _groundType = groundType;
    }

    @Override
    public TileEntities<Character> characters() {
        enforceDeletionInvariants();
        enforceLocationCorrespondenceInvariant("characters");
        return TILE_CHARACTERS;
    }

    @Override
    public TileEntities<Item> items() {
        enforceDeletionInvariants();
        enforceLocationCorrespondenceInvariant("items");
        return TILE_ITEMS;
    }

    @Override
    public TileEntities<TileFixture> fixtures() throws IllegalStateException {
        enforceDeletionInvariants();
        enforceLocationCorrespondenceInvariant("fixtures");
        return TILE_FIXTURES;
    }

    @Override
    public TileWallSegments wallSegments() throws IllegalStateException {
        enforceDeletionInvariants();
        enforceLocationCorrespondenceInvariant("wallSegments");
        return TILE_WALL_SEGMENTS;
    }

    @Override
    public Map<Sprite, Integer> sprites() throws IllegalStateException {
        enforceDeletionInvariants();
        enforceLocationCorrespondenceInvariant("sprites");
        return SPRITES;
    }

    @Override
    public void assignGameZoneAfterAddedToGameZone(GameZone gameZone)
            throws IllegalArgumentException, IllegalStateException {
        if (gameZone == null) {
            throw new IllegalArgumentException(
                    "TileImpl.assignGameZoneAfterAddedToGameZone: gameZone cannot be null");
        }
        if (_gameZone != null) {
            throw new IllegalArgumentException(
                    "TileImpl.assignGameZoneAfterAddedToGameZone: gameZone is already assigned");
        }
        _gameZone = gameZone;
    }

    @Override
    public VariableCache data() throws IllegalStateException {
        enforceDeletionInvariants();
        enforceLocationCorrespondenceInvariant("data");
        return DATA;
    }

    @Override
    protected String containingClassName() {
        return "GameZone";
    }

    @Override
    protected Deletable getContainingObject() {
        return _gameZone;
    }

    @Override
    public void afterDeleted() throws IllegalStateException {
        if (!_gameZone.isDeleted()) {
            throw new IllegalStateException("TileImpl.deleteAfterDeletingContainingGameZone: " +
                    "containing GameZone has not been deleted");
        }
        TILE_CHARACTERS.delete();
        TILE_FIXTURES.delete();
        TILE_ITEMS.delete();
        TILE_WALL_SEGMENTS.delete();
    }

    @Override
    public void delete() {
        if (!_gameZone.isDeleted()) {
            throw new IllegalStateException(
                    "TileImpl.delete: cannot delete before deleting containing GameZone");
        }
        super.delete();
    }

    // TODO: Test enforcement of deletion invariants
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
            public TileWallSegment tileWallSegment() {
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
        if (_gameZone != null && _gameZone.tile(LOCATION.getX(), LOCATION.getY()) != this) {
            throw new IllegalStateException("TileImpl." + methodName + ": This Tile is not " +
                    "present at its stated location (" + LOCATION.getX() + "," + LOCATION.getY() +
                    ") in its containing GameZone");
        }
    }

    @Override
    void enforceInvariants(String methodName) {
        enforceDeletionInvariants();
        enforceLocationCorrespondenceInvariant(methodName);
    }
}

package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.gamestate.archetypes.*;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.gamestate.factories.TileEntitiesFactory;
import soliloquy.specs.gamestate.factories.TileWallSegmentsFactory;
import soliloquy.specs.graphics.assets.Sprite;
import soliloquy.specs.ruleset.entities.GroundType;

public class TileImpl extends AbstractGameEventTargetEntity implements Tile {
    private final Coordinate LOCATION;
    private final TileEntities<Character> TILE_CHARACTERS;
    private final TileEntities<Item> TILE_ITEMS;
    private final TileEntities<TileFixture> TILE_FIXTURES;
    private final TileWallSegments TILE_WALL_SEGMENTS;
    private final List<GameMovementEvent> MOVEMENT_EVENTS;
    private final List<GameAbilityEvent> ABILITY_EVENTS;
    private final Map<Sprite, Integer> SPRITES;
    private final VariableCache DATA;

    private GameZone _gameZone;
    private int _height;
    private GroundType _groundType;

    private final static Character CHARACTER_ARCHETYPE = new CharacterArchetype();
    private final static Item ITEM_ARCHETYPE = new ItemArchetype();
    private final static TileFixture TILE_FIXTURE_ARCHETYPE = new TileFixtureArchetype();
    private final static GameMovementEvent MOVEMENT_EVENT_ARCHETYPE =
            new GameMovementEventArchetype();
    private final static GameAbilityEvent ABILITY_EVENT_ARCHETYPE =
            new GameAbilityEventArchetype();
    private final static Sprite SPRITE_ARCHETYPE = new SpriteArchetype();

    @SuppressWarnings("ConstantConditions")
    public TileImpl(int x, int y,
                    CoordinateFactory coordinateFactory,
                    TileEntitiesFactory tileEntitiesFactory,
                    TileWallSegmentsFactory tileWallSegmentsFactory,
                    ListFactory listFactory,
                    MapFactory mapFactory,
                    VariableCache data) {
        super(listFactory);
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
        if (listFactory == null) {
            throw new IllegalArgumentException("TileImpl: listFactory cannot be null");
        }
        MOVEMENT_EVENTS = listFactory.make(MOVEMENT_EVENT_ARCHETYPE);
        ABILITY_EVENTS = listFactory.make(ABILITY_EVENT_ARCHETYPE);
        if (mapFactory == null) {
            throw new IllegalArgumentException("TileImpl: mapFactory cannot be null");
        }
        SPRITES = mapFactory.make(SPRITE_ARCHETYPE, 0);
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
    public List<GameMovementEvent> movementEvents() throws IllegalStateException {
        enforceDeletionInvariants();
        enforceLocationCorrespondenceInvariant("movementEvents");
        return MOVEMENT_EVENTS;
    }

    @Override
    public List<GameAbilityEvent> abilityEvents() throws IllegalStateException {
        enforceDeletionInvariants();
        enforceLocationCorrespondenceInvariant("abilityEvents");
        return ABILITY_EVENTS;
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

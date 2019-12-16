package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.*;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.gamestate.factories.TileEntitiesFactory;
import soliloquy.specs.gamestate.factories.TileWallSegmentsFactory;
import soliloquy.specs.ruleset.entities.GroundType;
import soliloquy.specs.sprites.entities.Sprite;

public class TileImpl extends GameEventTargetEntityAbstract implements Tile {
    private final GameZone GAME_ZONE;
    private final ReadableCoordinate LOCATION;
    private final TileEntities<Character> TILE_CHARACTERS;
    private final TileEntities<Item> TILE_ITEMS;
    private final TileEntities<TileFixture> TILE_FIXTURES;
    private final TileWallSegments TILE_WALL_SEGMENTS;
    private final Collection<GameMovementEvent> MOVEMENT_EVENTS;
    private final Collection<GameAbilityEvent> ABILITY_EVENTS;
    private final Collection<Sprite> SPRITES;
    private final GenericParamsSet DATA;

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
    public TileImpl(GameZone gameZone, ReadableCoordinate location,
                    TileEntitiesFactory tileEntitiesFactory,
                    TileWallSegmentsFactory tileWallSegmentsFactory,
                    CollectionFactory collectionFactory,
                    GenericParamsSet data) {
        super(collectionFactory);
        if (gameZone == null) {
            throw new IllegalArgumentException("TileImpl: gameZone cannot be null");
        }
        GAME_ZONE = gameZone;
        if (location == null) {
            throw new IllegalArgumentException("TileImpl: location cannot be null");
        }
        LOCATION = location.makeClone().readOnlyRepresentation();
        if (tileEntitiesFactory == null) {
            throw new IllegalArgumentException("TileImpl: tileEntitiesFactory cannot be null");
        }
        TILE_CHARACTERS = tileEntitiesFactory.make(this, CHARACTER_ARCHETYPE);
        TILE_ITEMS = tileEntitiesFactory.make(this, ITEM_ARCHETYPE);
        TILE_FIXTURES = tileEntitiesFactory.make(this, TILE_FIXTURE_ARCHETYPE);
        if (tileWallSegmentsFactory == null) {
            throw new IllegalArgumentException("TileImpl: tileWallSegmentsFactory cannot be null");
        }
        TILE_WALL_SEGMENTS = tileWallSegmentsFactory.make(this);
        if (collectionFactory == null) {
            throw new IllegalArgumentException("TileImpl: collectionFactory cannot be null");
        }
        MOVEMENT_EVENTS = collectionFactory.make(MOVEMENT_EVENT_ARCHETYPE);
        ABILITY_EVENTS = collectionFactory.make(ABILITY_EVENT_ARCHETYPE);
        SPRITES = collectionFactory.make(SPRITE_ARCHETYPE);
        if (data == null) {
            throw new IllegalArgumentException("TileImpl: data cannot be null");
        }
        DATA = data;
    }

    @Override
    public GameZone gameZone() throws IllegalStateException {
        enforceDeletionInvariants("gameZone");
        enforceLocationCorrespondenceInvariant("gameZone");
        return GAME_ZONE;
    }

    @Override
    public ReadableCoordinate location() throws IllegalStateException {
        enforceDeletionInvariants("location");
        enforceLocationCorrespondenceInvariant("location");
        return LOCATION;
    }

    @Override
    public int getHeight() throws IllegalStateException {
        enforceDeletionInvariants("getHeight");
        enforceLocationCorrespondenceInvariant("getHeight");
        return _height;
    }

    @Override
    public void setHeight(int height) throws IllegalStateException {
        enforceDeletionInvariants("setHeight");
        enforceLocationCorrespondenceInvariant("setHeight");
        _height = height;
    }

    @Override
    public GroundType getGroundType() throws IllegalStateException {
        enforceDeletionInvariants("getGroundType");
        enforceLocationCorrespondenceInvariant("getGroundType");
        return _groundType;
    }

    @Override
    public void setGroundType(GroundType groundType) throws IllegalStateException {
        enforceDeletionInvariants("setGroundType");
        enforceLocationCorrespondenceInvariant("setGroundType");
        _groundType = groundType;
    }

    @Override
    public TileEntities<Character> characters() {
        enforceDeletionInvariants("characters");
        enforceLocationCorrespondenceInvariant("characters");
        return TILE_CHARACTERS;
    }

    @Override
    public TileEntities<Item> items() {
        enforceDeletionInvariants("items");
        enforceLocationCorrespondenceInvariant("items");
        return TILE_ITEMS;
    }

    @Override
    public TileEntities<TileFixture> fixtures() throws IllegalStateException {
        enforceDeletionInvariants("fixtures");
        enforceLocationCorrespondenceInvariant("fixtures");
        return TILE_FIXTURES;
    }

    @Override
    public TileWallSegments wallSegments() throws IllegalStateException {
        enforceDeletionInvariants("wallSegments");
        enforceLocationCorrespondenceInvariant("wallSegments");
        return TILE_WALL_SEGMENTS;
    }

    @Override
    public Collection<GameMovementEvent> movementEvents() throws IllegalStateException {
        enforceDeletionInvariants("movementEvents");
        enforceLocationCorrespondenceInvariant("movementEvents");
        return MOVEMENT_EVENTS;
    }

    @Override
    public Collection<GameAbilityEvent> abilityEvents() throws IllegalStateException {
        enforceDeletionInvariants("abilityEvents");
        enforceLocationCorrespondenceInvariant("abilityEvents");
        return ABILITY_EVENTS;
    }

    @Override
    public Collection<Sprite> sprites() throws IllegalStateException {
        enforceDeletionInvariants("sprites");
        enforceLocationCorrespondenceInvariant("sprites");
        return SPRITES;
    }

    @Override
    public GenericParamsSet data() throws IllegalStateException {
        enforceDeletionInvariants("data");
        enforceLocationCorrespondenceInvariant("data");
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
    protected Deletable getContainingObject() {
        return GAME_ZONE;
    }

    @Override
    public void afterDeleted() throws IllegalStateException {
        if (!GAME_ZONE.isDeleted()) {
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
        if (!GAME_ZONE.isDeleted()) {
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
        if (GAME_ZONE.tile(LOCATION) != this) {
            throw new IllegalStateException("TileImpl." + methodName + ": This Tile is not " +
                    "present at its stated location (" + LOCATION.getX() + "," + LOCATION.getY() +
                    ") in its containing GameZone");
        }
    }

    @Override
    void enforceInvariants(String methodName) {
        enforceDeletionInvariants(methodName);
        enforceLocationCorrespondenceInvariant(methodName);
    }
}

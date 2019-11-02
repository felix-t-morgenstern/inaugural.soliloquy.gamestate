package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.GameAbilityEventArchetype;
import inaugural.soliloquy.gamestate.archetypes.GameMovementEventArchetype;
import inaugural.soliloquy.gamestate.archetypes.SpriteArchetype;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.GenericParamsSetFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
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
    private final Collection<GameMovementEvent> MOVEMENT_EVENTS;
    private final Collection<GameAbilityEvent> ABILITY_EVENTS;
    private final Map<Integer, Collection<Sprite>> SPRITES;
    private final GenericParamsSet DATA;

    private int _height;
    private GroundType _groundType;

    private final static GameMovementEvent MOVEMENT_EVENT_ARCHETYPE =
            new GameMovementEventArchetype();
    private final static GameAbilityEvent ABILITY_EVENT_ARCHETYPE =
            new GameAbilityEventArchetype();
    private final static Sprite SPRITE_ARCHETYPE = new SpriteArchetype();

    @SuppressWarnings("ConstantConditions")
    public TileImpl(GameZone gameZone, ReadableCoordinate location,
                    TileCharactersFactory tileCharactersFactory, TileItemsFactory tileItemsFactory,
                    TileFixturesFactory tileFixturesFactory,
                    TileWallSegmentsFactory tileWallSegmentsFactory, MapFactory mapFactory,
                    CollectionFactory collectionFactory,
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
        MOVEMENT_EVENTS = collectionFactory.make(MOVEMENT_EVENT_ARCHETYPE);
        ABILITY_EVENTS = collectionFactory.make(ABILITY_EVENT_ARCHETYPE);
        SPRITES = mapFactory.make(0, collectionFactory.make(SPRITE_ARCHETYPE));
        if (genericParamsSetFactory == null) {
            throw new IllegalArgumentException("TileImpl: genericParamsSetFactory cannot be null");
        }
        DATA = genericParamsSetFactory.make();
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
    public TileCharacters characters() {
        enforceDeletionInvariants("characters");
        enforceLocationCorrespondenceInvariant("characters");
        return TILE_CHARACTERS;
    }

    @Override
    public TileItems items() {
        enforceDeletionInvariants("items");
        enforceLocationCorrespondenceInvariant("items");
        return TILE_ITEMS;
    }

    @Override
    public TileFixtures fixtures() throws IllegalStateException {
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
    public Map<Integer, Collection<Sprite>> sprites() throws IllegalStateException {
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
    void enforceInvariantsForEventsCollections(String methodName) {
        enforceDeletionInvariants(methodName);
        enforceLocationCorrespondenceInvariant(methodName);
    }
}

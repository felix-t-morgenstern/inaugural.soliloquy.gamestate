package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Coordinate2d;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.gamestate.factories.TileEntitiesFactory;
import soliloquy.specs.graphics.assets.Sprite;
import soliloquy.specs.ruleset.entities.GroundType;

import java.util.Map;

import static inaugural.soliloquy.tools.collections.Collections.mapOf;
import static inaugural.soliloquy.tools.generic.Archetypes.generateSimpleArchetype;

public class TileImpl extends AbstractGameEventTargetEntity implements Tile {
    private final Coordinate2d LOCATION;
    private final TileEntities<Character> TILE_CHARACTERS;
    private final TileEntities<Item> TILE_ITEMS;
    private final TileEntities<TileFixture> TILE_FIXTURES;
    private final Map<Sprite, Integer> SPRITES;
    private final VariableCache DATA;

    private GameZone gameZone;
    private int height;
    private GroundType groundType;

    public TileImpl(int x, int y,
                    TileEntitiesFactory tileEntitiesFactory,
                    VariableCache data) {
        super();
        LOCATION = Coordinate2d.of(x, y);
        Check.ifNull(tileEntitiesFactory, "tileEntitiesFactory");
        // TODO: Test and implement whether add and remove from gameZone works
        TILE_CHARACTERS = tileEntitiesFactory.make(this, generateSimpleArchetype(Character.class));
        TILE_ITEMS = tileEntitiesFactory.make(this, generateSimpleArchetype(Item.class));
        TILE_FIXTURES = tileEntitiesFactory.make(this, generateSimpleArchetype(TileFixture.class));
        SPRITES = mapOf();
        DATA = Check.ifNull(data, "data");
    }

    @Override
    public GameZone gameZone() throws IllegalStateException {
        enforceDeletionInvariants();
        enforceLocationCorrespondenceInvariant("gameZone");
        return gameZone;
    }

    // TODO: Ensure that clone is made
    @Override
    public Coordinate2d location() throws IllegalStateException {
        enforceDeletionInvariants();
        enforceLocationCorrespondenceInvariant("location");
        return LOCATION;
    }

    @Override
    public int getHeight() throws IllegalStateException {
        enforceDeletionInvariants();
        enforceLocationCorrespondenceInvariant("getHeight");
        return height;
    }

    @Override
    public void setHeight(int height) throws IllegalStateException {
        enforceDeletionInvariants();
        enforceLocationCorrespondenceInvariant("setHeight");
        this.height = height;
    }

    @Override
    public GroundType getGroundType() throws IllegalStateException {
        enforceDeletionInvariants();
        enforceLocationCorrespondenceInvariant("getGroundType");
        return groundType;
    }

    @Override
    public void setGroundType(GroundType groundType) throws IllegalStateException {
        enforceDeletionInvariants();
        enforceLocationCorrespondenceInvariant("setGroundType");
        this.groundType = groundType;
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
    public Map<Sprite, Integer> sprites() throws IllegalStateException {
        enforceDeletionInvariants();
        enforceLocationCorrespondenceInvariant("sprites");
        return SPRITES;
    }

    @Override
    public void assignGameZoneAfterAddedToGameZone(GameZone gameZone)
            throws IllegalArgumentException, IllegalStateException {
        Check.ifNull(gameZone, "gameZone");
        if (this.gameZone != null) {
            throw new IllegalArgumentException(
                    "TileImpl.assignGameZoneAfterAddedToGameZone: gameZone is already assigned");
        }
        this.gameZone = gameZone;
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
        return gameZone;
    }

    @Override
    public void afterDeleted() throws IllegalStateException {
        if (!gameZone.isDeleted()) {
            throw new IllegalStateException("TileImpl.deleteAfterDeletingContainingGameZone: " +
                    "containing GameZone has not been deleted");
        }
        TILE_CHARACTERS.delete();
        TILE_FIXTURES.delete();
        TILE_ITEMS.delete();
    }

    @Override
    public void delete() {
        if (!gameZone.isDeleted()) {
            throw new IllegalStateException(
                    "TileImpl.delete: cannot delete before deleting containing GameZone");
        }
        super.delete();
    }

    // TODO: Test enforcement of deletion invariants
    @Override
    public GameEventTarget makeGameEventTarget() throws IllegalStateException {
        var tile = this;
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
            public WallSegment tileWallSegment() {
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
        if (gameZone != null && gameZone.tile(LOCATION) != this) {
            throw new IllegalStateException("TileImpl." + methodName + ": This Tile is not " +
                    "present at its stated location (" + LOCATION.X + "," + LOCATION.Y +
                    ") in its containing GameZone");
        }
    }

    @Override
    void enforceInvariants(String methodName) {
        enforceDeletionInvariants();
        enforceLocationCorrespondenceInvariant(methodName);
    }
}

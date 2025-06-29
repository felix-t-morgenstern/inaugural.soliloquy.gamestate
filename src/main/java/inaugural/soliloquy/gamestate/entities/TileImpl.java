package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.io.graphics.assets.Sprite;
import soliloquy.specs.ruleset.entities.GroundType;

import java.util.Map;
import java.util.function.Function;

import static inaugural.soliloquy.tools.collections.Collections.mapOf;

public class TileImpl extends AbstractGameZoneTerrain implements Tile {
    private final TileEntities<Character> TILE_CHARACTERS;
    private final TileEntities<Item> TILE_ITEMS;
    private final TileEntities<TileFixture> TILE_FIXTURES;
    private final Map<Sprite, Integer> SPRITES;
    private final Map<String, Object> DATA;

    private GroundType groundType;

    public TileImpl(@SuppressWarnings("rawtypes") Function<Tile, TileEntities> tileEntitiesFactory,
                    Map<String, Object> data) {
        super();
        Check.ifNull(tileEntitiesFactory, "tileEntitiesFactory");
        // TODO: Test and implement whether add and remove from gameZone works
        //noinspection unchecked
        TILE_CHARACTERS = tileEntitiesFactory.apply(this);
        //noinspection unchecked
        TILE_ITEMS = tileEntitiesFactory.apply(this);
        //noinspection unchecked
        TILE_FIXTURES = tileEntitiesFactory.apply(this);
        SPRITES = mapOf();
        DATA = Check.ifNull(data, "data");
    }

    @Override
    public GroundType getGroundType() throws IllegalStateException {
        enforceInvariants("getGroundType");
        return groundType;
    }

    @Override
    public void setGroundType(GroundType groundType) throws IllegalStateException {
        enforceInvariants("setGroundType");
        this.groundType = groundType;
    }

    @Override
    public TileEntities<Character> characters() {
        enforceInvariants("characters");
        return TILE_CHARACTERS;
    }

    @Override
    public TileEntities<Item> items() {
        enforceInvariants("items");
        return TILE_ITEMS;
    }

    @Override
    public TileEntities<TileFixture> fixtures() throws IllegalStateException {
        enforceInvariants("fixtures");
        return TILE_FIXTURES;
    }

    @Override
    public Map<Sprite, Integer> sprites() throws IllegalStateException {
        enforceInvariants("sprites");
        return SPRITES;
    }

    @Override
    public Map<String, Object> data() throws IllegalStateException {
        enforceInvariants("data");
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
        if (gameZone != null && !gameZone.isDeleted()) {
            throw new IllegalStateException("TileImpl.deleteAfterDeletingContainingGameZone: " +
                    "containing GameZone has not been deleted");
        }
        TILE_CHARACTERS.delete();
        TILE_FIXTURES.delete();
        TILE_ITEMS.delete();
    }

    @Override
    public void delete() {
        if (gameZone != null && !gameZone.isDeleted()) {
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
        };
    }

    private void enforceLocationCorrespondenceInvariant(String methodName) {
        if (gameZone != null && gameZone.tile(location) != this) {
            throw new IllegalStateException("TileImpl." + methodName + ": This Tile is not " +
                    "present at its stated location (" + location.X + "," + location.Y +
                    ") in its containing GameZone");
        }
    }

    @Override
    void enforceInvariants(String methodName) {
        enforceDeletionInvariants();
        enforceLocationCorrespondenceInvariant(methodName);
    }
}

package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Vertex;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.gamestate.factories.TileFixtureItemsFactory;
import soliloquy.specs.ruleset.entities.FixtureType;

import java.util.UUID;

public class TileFixtureImpl extends AbstractTileEntity<TileFixture> implements TileFixture {
    private final UUID UUID;
    private final FixtureType TYPE;
    private final TileFixtureItems TILE_FIXTURE_ITEMS;
    private final VariableCache DATA;

    private String name;
    private Vertex tileOffset;

    public TileFixtureImpl(UUID uuid,
                           FixtureType fixtureType,
                           TileFixtureItemsFactory tileFixtureItemsFactory,
                           VariableCache data) {
        super();
        UUID = Check.ifNull(uuid, "uuid");
        TYPE = Check.ifNull(fixtureType, "fixtureType");
        tileOffset = TYPE.defaultTileOffset();
        TILE_FIXTURE_ITEMS = Check.ifNull(tileFixtureItemsFactory, "tileFixtureItemsFactory")
                .make(this);
        DATA = Check.ifNull(data, "data");
    }

    @Override
    public UUID uuid() {
        return UUID;
    }

    @Override
    public Tile tile() throws IllegalStateException {
        enforceDeletionInvariants();
        enforceCorrectTileInvariant("tile");
        return tile;
    }

    @Override
    public FixtureType type() throws IllegalStateException {
        enforceDeletionInvariants();
        enforceCorrectTileInvariant("fixtureType");
        return TYPE;
    }

    @Override
    public TileFixtureItems items() throws IllegalStateException {
        enforceDeletionInvariants();
        enforceCorrectTileInvariant("containedItems");
        return TILE_FIXTURE_ITEMS;
    }

    @Override
    public void assignTileAfterAddedToTileEntitiesOfType(Tile tile)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants();
        enforceCorrectTileInvariant("assignTileAfterAddedToTileEntitiesOfType");
        this.tile = tile;
        enforceCorrectTileInvariant("assignTileAfterAddedToTileEntitiesOfType");
    }

    @Override
    public VariableCache data() throws IllegalStateException {
        enforceInvariants("data");
        return DATA;
    }

    @Override
    public GameEventTarget makeGameEventTarget() throws IllegalStateException {
        enforceInvariants("makeGameEventTarget");
        var tileFixture = this;
        return new GameEventTarget() {
            @Override
            public Tile tile() {
                return null;
            }

            @Override
            public TileFixture tileFixture() {
                return tileFixture;
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
    public void afterDeleted() throws IllegalStateException {
        enforceCorrectTileInvariant("delete");
        TILE_FIXTURE_ITEMS.delete();
        if (tile != null) {
            var tileFixtures = tile.fixtures();
            tile = null;
            tileFixtures.remove(this);
        }
    }

    @Override
    public String getName() {
        enforceInvariants("getName");
        return name;
    }

    @Override
    public void setName(String name) {
        enforceInvariants("setName");
        this.name = name;
    }

    @Override
    public String getInterfaceName() {
        return TileFixture.class.getCanonicalName();
    }

    @Override
    protected String className() {
        return "TileFixtureImpl";
    }

    @Override
    protected TileEntities<TileFixture> getTileAggregation() {
        return tile.fixtures();
    }

    @Override
    public Vertex getTileOffset() throws IllegalStateException, EntityDeletedException {
        enforceInvariants("getTileOffset");
        return tileOffset;
    }

    @Override
    public void setTileOffset(Vertex tileOffset)
            throws IllegalArgumentException, IllegalStateException, EntityDeletedException {
        enforceInvariants("setTileOffset");
        this.tileOffset = Check.ifNull(tileOffset, "tileOffset");
    }
}

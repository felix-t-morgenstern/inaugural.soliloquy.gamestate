package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.infrastructure.VariableCache;
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

    private String _name;
    private float _xTileWidthOffset;
    private float _yTileHeightOffset;

    @SuppressWarnings("ConstantConditions")
    public TileFixtureImpl(UUID uuid,
                           FixtureType fixtureType,
                           TileFixtureItemsFactory tileFixtureItemsFactory,
                           VariableCache data) {
        super();
        UUID = Check.ifNull(uuid, "uuid");
        TYPE = Check.ifNull(fixtureType, "fixtureType");
        _xTileWidthOffset = TYPE.defaultXTileWidthOffset();
        _yTileHeightOffset = TYPE.defaultYTileHeightOffset();
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
        return _tile;
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
        _tile = tile;
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
        TileFixture tileFixture = this;
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
    public void afterDeleted() throws IllegalStateException {
        enforceCorrectTileInvariant("delete");
        TILE_FIXTURE_ITEMS.delete();
        if (_tile != null) {
            TileEntities<TileFixture> tileFixtures = _tile.fixtures();
            _tile = null;
            tileFixtures.remove(this);
        }
    }

    @Override
    public String getName() {
        enforceInvariants("getName");
        return _name;
    }

    @Override
    public void setName(String name) {
        enforceInvariants("setName");
        _name = name;
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
        return _tile.fixtures();
    }

    @Override
    public float getXTileWidthOffset() throws IllegalStateException, EntityDeletedException {
        enforceInvariants("getXTileWidthOffset");
        return _xTileWidthOffset;
    }

    @Override
    public float getYTileHeightOffset() throws IllegalStateException, EntityDeletedException {
        enforceInvariants("getYTileHeightOffset");
        return _yTileHeightOffset;
    }

    @Override
    public void setXTileWidthOffset(float xTileWidthOffset)
            throws IllegalStateException, EntityDeletedException {
        enforceInvariants("setXTileWidthOffset");
        _xTileWidthOffset = xTileWidthOffset;
    }

    @Override
    public void setYTileHeightOffset(float yTileHeightOffset)
            throws IllegalStateException, EntityDeletedException {
        enforceInvariants("setYTileHeightOffset");
        _yTileHeightOffset = yTileHeightOffset;
    }
}

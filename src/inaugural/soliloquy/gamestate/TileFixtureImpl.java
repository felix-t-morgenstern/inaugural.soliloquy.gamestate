package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.gamestate.factories.TileFixtureItemsFactory;
import soliloquy.specs.ruleset.entities.FixtureType;

public class TileFixtureImpl extends TileEntityAbstract<TileFixture> implements TileFixture {
    private final EntityUuid ID;
    private final FixtureType TYPE;
    private final Coordinate PIXEL_OFFSET;
    private final TileFixtureItems TILE_FIXTURE_ITEMS;
    private final VariableCache DATA;

    private String _name;

    public TileFixtureImpl(EntityUuid id,
                           FixtureType fixtureType,
                           CoordinateFactory coordinateFactory,
                           CollectionFactory collectionFactory,
                           TileFixtureItemsFactory tileFixtureItemsFactory,
                           VariableCache data) {
        super(collectionFactory);
        ID = id;
        TYPE = fixtureType;
        PIXEL_OFFSET = coordinateFactory.make(0,0);
        TILE_FIXTURE_ITEMS = tileFixtureItemsFactory.make(this);
        DATA = data;
    }

    @Override
    public EntityUuid id() {
        return ID;
    }

    @Override
    public Tile tile() throws IllegalStateException {
        enforceDeletionInvariants("tile");
        enforceCorrectTileInvariant("tile");
        return _tile;
    }

    @Override
    public FixtureType type() throws IllegalStateException {
        enforceDeletionInvariants("fixtureType");
        enforceCorrectTileInvariant("fixtureType");
        return TYPE;
    }

    @Override
    public Coordinate pixelOffset() throws IllegalStateException {
        enforceDeletionInvariants("pixelOffset");
        enforceCorrectTileInvariant("pixelOffset");
        return PIXEL_OFFSET;
    }

    @Override
    public TileFixtureItems items() throws IllegalStateException {
        enforceDeletionInvariants("containedItems");
        enforceCorrectTileInvariant("containedItems");
        return TILE_FIXTURE_ITEMS;
    }

    @Override
    public void assignTileAfterAddedToTileEntitiesOfType(Tile tile)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("assignTileAfterAddedToTileEntitiesOfType");
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
}

package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.gamestate.factories.TileFixtureItemsFactory;
import soliloquy.specs.ruleset.entities.FixtureType;

public class TileFixtureImpl extends GameEventTargetEntityImpl implements TileFixture {
    private final EntityUuid ID;
    private final FixtureType TYPE;
    private final Coordinate PIXEL_OFFSET;
    private final TileFixtureItems TILE_FIXTURE_ITEMS;
    private final GenericParamsSet DATA;

    private Tile _tile;
    private String _name;

    public TileFixtureImpl(EntityUuid id,
                           FixtureType fixtureType,
                           CoordinateFactory coordinateFactory,
                           CollectionFactory collectionFactory,
                           TileFixtureItemsFactory tileFixtureItemsFactory,
                           GenericParamsSet data) {
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
    public void assignTileFixtureToTileAfterAddingToTileFixtures(Tile tile)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("assignTileFixtureToTileAfterAddingToTileFixtures");
        enforceCorrectTileInvariant("assignTileFixtureToTileAfterAddingToTileFixtures");
        _tile = tile;
        enforceCorrectTileInvariant("assignTileFixtureToTileAfterAddingToTileFixtures");
    }

    @Override
    public GenericParamsSet data() throws IllegalStateException {
        enforceDeletionInvariants("data");
        enforceCorrectTileInvariant("data");
        return DATA;
    }

    @Override
    public GameEventTarget makeGameEventTarget() throws IllegalStateException {
        enforceDeletionInvariants("makeGameEventTarget");
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
            TileFixtures tileFixtures = _tile.fixtures();
            _tile = null;
            tileFixtures.remove(this);
        }
    }

    @Override
    public String getName() {
        enforceDeletionInvariants("getName");
        enforceCorrectTileInvariant("getName");
        return _name;
    }

    @Override
    public void setName(String name) {
        enforceDeletionInvariants("setName");
        enforceCorrectTileInvariant("setName");
        _name = name;
    }

    @Override
    public String getInterfaceName() {
        return TileFixture.class.getCanonicalName();
    }

    private void enforceCorrectTileInvariant(String methodName) {
        if (_tile != null && !_tile.fixtures().contains(this)) {
            throw new IllegalStateException("TileFixture." + methodName +
                    ": TileFixture is not present on its specified Tile");
        }
    }

    @Override
    protected String className() {
        return "TileFixtureImpl";
    }

    @Override
    protected String containingClassName() {
        return "Tile";
    }

    @Override
    protected Deletable getContainingObject() {
        return _tile;
    }

    @Override
    void enforceInvariantsForEventsCollections(String methodName) {
        enforceCorrectTileInvariant(methodName);
        enforceDeletionInvariants(methodName);
    }
}

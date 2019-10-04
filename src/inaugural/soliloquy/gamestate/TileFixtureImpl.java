package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.GameEventArchetype;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtureItems;
import soliloquy.specs.gamestate.entities.gameevents.GameEvent;
import soliloquy.specs.gamestate.factories.TileFixtureItemsFactory;
import soliloquy.specs.ruleset.entities.FixtureType;

public class TileFixtureImpl implements TileFixture {
    private final FixtureType FIXTURE_TYPE;
    private final Coordinate PIXEL_OFFSET;
    private final Collection<GameEvent> EVENTS;
    private final TileFixtureItems TILE_FIXTURE_ITEMS;
    private final GenericParamsSet DATA;

    private Tile _tile;
    private String _name;
    private boolean _deleted;

    public TileFixtureImpl(FixtureType fixtureType,
                           Coordinate pixelOffset,
                           CollectionFactory collectionFactory,
                           TileFixtureItemsFactory tileFixtureItemsFactory,
                           GenericParamsSet data) {
        FIXTURE_TYPE = fixtureType;
        PIXEL_OFFSET = pixelOffset;
        EVENTS = collectionFactory.make(new GameEventArchetype());
        TILE_FIXTURE_ITEMS = tileFixtureItemsFactory.make(this);
        DATA = data;
    }

    @Override
    public Tile tile() throws IllegalStateException {
        enforceInvariant("tile", true);
        return _tile;
    }

    @Override
    public FixtureType fixtureType() throws IllegalStateException {
        enforceInvariant("fixtureType", true);
        return FIXTURE_TYPE;
    }

    @Override
    public Coordinate pixelOffset() throws IllegalStateException {
        enforceInvariant("pixelOffset", true);
        return PIXEL_OFFSET;
    }

    @Override
    public TileFixtureItems items() throws IllegalStateException {
        enforceInvariant("containedItems", true);
        return TILE_FIXTURE_ITEMS;
    }

    @Override
    public void assignTileFixtureToTileAfterAddingToTileFixtures(Tile tile)
            throws IllegalArgumentException, IllegalStateException {
        enforceInvariant("assignTileFixtureToTileAfterAddingToTileFixtures", true);
        _tile = tile;
        enforceInvariant("assignTileFixtureToTileAfterAddingToTileFixtures", true);
    }

    @Override
    public GenericParamsSet data() throws IllegalStateException {
        enforceInvariant("data", true);
        return DATA;
    }

    @Override
    public Collection<GameEvent> events() throws IllegalStateException {
        enforceInvariant("events", true);
        return EVENTS;
    }

    @Override
    public void delete() throws IllegalStateException {
        enforceInvariant("delete", true);
        TILE_FIXTURE_ITEMS.delete();
        _deleted = true;
    }

    @Override
    public boolean isDeleted() {
        enforceInvariant("isDeleted", false);
        return _deleted;
    }

    @Override
    public String getName() {
        enforceInvariant("getName", true);
        return _name;
    }

    @Override
    public void setName(String name) {
        enforceInvariant("setName", true);
        _name = name;
    }

    @Override
    public String getInterfaceName() {
        enforceInvariant("getInterfaceName", true);
        return TileFixture.class.getCanonicalName();
    }

    private void enforceInvariant(String methodName, boolean cannotBeDeleted) {
        if (cannotBeDeleted && _deleted) {
            throw new IllegalStateException(
                    "TileFixture." + methodName + ": TileFixture is deleted");
        }
        if (_tile != null && !_tile.fixtures().contains(this)) {
            throw new IllegalStateException("TileFixture." + methodName +
                    ": TileFixture is not present on its specified Tile");
        }
    }
}

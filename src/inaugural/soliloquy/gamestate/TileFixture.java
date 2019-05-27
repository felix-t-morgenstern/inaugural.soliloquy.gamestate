package inaugural.soliloquy.gamestate;

import soliloquy.common.specs.ICoordinate;
import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.common.specs.IMap;
import soliloquy.gamestate.specs.*;
import soliloquy.ruleset.gameentities.abilities.specs.IActiveAbility;
import soliloquy.ruleset.gameentities.abilities.specs.IReactiveAbility;
import soliloquy.ruleset.gameentities.specs.IFixtureType;

public class TileFixture implements ITileFixture {
    private final IFixtureType FIXTURE_TYPE;
    private final ICoordinate PIXEL_OFFSET;
    private final IMap<String, IActiveAbility> ACTIVE_ABILITIES;
    private final IMap<String, IReactiveAbility> REACTIVE_ABILITIES;
    private final ITileFixtureItems TILE_FIXTURE_ITEMS;
    private final IGenericParamsSet DATA;

    private ITile _tile;
    private String _name;
    private boolean _deleted;

    public TileFixture(IFixtureType fixtureType,
                       ICoordinate pixelOffset,
                       IMap<String, IActiveAbility> activeAbilities,
                       IMap<String, IReactiveAbility> reactiveAbilities,
                       ITileFixtureItemsFactory tileFixtureItemsFactory,
                       IGenericParamsSet data) {
        FIXTURE_TYPE = fixtureType;
        PIXEL_OFFSET = pixelOffset;
        ACTIVE_ABILITIES = activeAbilities;
        REACTIVE_ABILITIES = reactiveAbilities;
        TILE_FIXTURE_ITEMS = tileFixtureItemsFactory.make(this);
        DATA = data;
    }

    @Override
    public ITile tile() throws IllegalStateException {
        enforceInvariant("tile", true);
        return _tile;
    }

    @Override
    public IFixtureType fixtureType() throws IllegalStateException {
        enforceInvariant("fixtureType", true);
        return FIXTURE_TYPE;
    }

    @Override
    public ICoordinate pixelOffset() throws IllegalStateException {
        enforceInvariant("pixelOffset", true);
        return PIXEL_OFFSET;
    }

    @Override
    public IMap<String, IActiveAbility> activeAbilities() throws IllegalStateException {
        enforceInvariant("activeAbilities", true);
        return ACTIVE_ABILITIES;
    }

    @Override
    public IMap<String, IReactiveAbility> reactiveAbilities() throws IllegalStateException {
        enforceInvariant("reactiveAbilities", true);
        return REACTIVE_ABILITIES;
    }

    @Override
    public ITileFixtureItems containedItems() throws IllegalStateException {
        enforceInvariant("containedItems", true);
        return TILE_FIXTURE_ITEMS;
    }

    @Override
    public void assignTileFixtureToTile(ITile tile) throws IllegalArgumentException, IllegalStateException {
        enforceInvariant("assignTileFixtureToTile", true);
        _tile = tile;
    }

    @Override
    public IGenericParamsSet data() throws IllegalStateException {
        enforceInvariant("data", true);
        return DATA;
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
        return ITileFixture.class.getCanonicalName();
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

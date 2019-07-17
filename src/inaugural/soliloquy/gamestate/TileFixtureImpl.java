package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtureItems;
import soliloquy.specs.gamestate.factories.TileFixtureItemsFactory;
import soliloquy.specs.ruleset.entities.FixtureType;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbility;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbility;

public class TileFixtureImpl implements TileFixture {
    private final FixtureType FIXTURE_TYPE;
    private final Coordinate PIXEL_OFFSET;
    private final Map<String, ActiveAbility> ACTIVE_ABILITIES;
    private final Map<String, ReactiveAbility> REACTIVE_ABILITIES;
    private final TileFixtureItems TILE_FIXTURE_ITEMS;
    private final GenericParamsSet DATA;

    private Tile _tile;
    private String _name;
    private boolean _deleted;

    public TileFixtureImpl(FixtureType fixtureType,
                           Coordinate pixelOffset,
                           Map<String, ActiveAbility> activeAbilities,
                           Map<String, ReactiveAbility> reactiveAbilities,
                           TileFixtureItemsFactory tileFixtureItemsFactory,
                           GenericParamsSet data) {
        FIXTURE_TYPE = fixtureType;
        PIXEL_OFFSET = pixelOffset;
        ACTIVE_ABILITIES = activeAbilities;
        REACTIVE_ABILITIES = reactiveAbilities;
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
    public Map<String, ActiveAbility> activeAbilities() throws IllegalStateException {
        enforceInvariant("activeAbilities", true);
        return ACTIVE_ABILITIES;
    }

    @Override
    public Map<String, ReactiveAbility> reactiveAbilities() throws IllegalStateException {
        enforceInvariant("reactiveAbilities", true);
        return REACTIVE_ABILITIES;
    }

    @Override
    public TileFixtureItems containedItems() throws IllegalStateException {
        enforceInvariant("containedItems", true);
        return TILE_FIXTURE_ITEMS;
    }

    @Override
    public void assignTileFixtureToTile(Tile tile) throws IllegalArgumentException, IllegalStateException {
        enforceInvariant("assignTileFixtureToTile", true);
        _tile = tile;
    }

    @Override
    public GenericParamsSet data() throws IllegalStateException {
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

package inaugural.soliloquy.gamestate;

import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.ICoordinate;
import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.common.specs.IMap;
import soliloquy.gamestate.specs.IGameZone;
import soliloquy.gamestate.specs.IItem;
import soliloquy.gamestate.specs.ITileFixture;
import soliloquy.ruleset.gameentities.abilities.specs.IActiveAbility;
import soliloquy.ruleset.gameentities.abilities.specs.IReactiveAbility;
import soliloquy.ruleset.gameentities.specs.IFixtureType;

public class TileFixture implements ITileFixture {
    private final IFixtureType FIXTURE_TYPE;
    private final ICollection<IItem> CONTAINED_ITEMS;
    private final ICoordinate PIXEL_OFFSET;
    private final IMap<String, IActiveAbility> ACTIVE_ABILITIES;
    private final IMap<String, IReactiveAbility> REACTIVE_ABILITIES;
    private final IGameZone GAME_ZONE;
    private final IGenericParamsSet DATA;

    private String _name;
    private boolean _deleted;

    public TileFixture(IFixtureType fixtureType,
                       ICollection<IItem> containedItems,
                       ICoordinate pixelOffset,
                       IMap<String, IActiveAbility> activeAbilities,
                       IMap<String, IReactiveAbility> reactiveAbilities,
                       IGameZone gameZone,
                       IGenericParamsSet data) {
        FIXTURE_TYPE = fixtureType;
        CONTAINED_ITEMS = containedItems;
        PIXEL_OFFSET = pixelOffset;
        ACTIVE_ABILITIES = activeAbilities;
        REACTIVE_ABILITIES = reactiveAbilities;
        GAME_ZONE = gameZone;
        DATA = data;
    }

    @Override
    public IFixtureType fixtureType() throws IllegalStateException {
        return FIXTURE_TYPE;
    }

    @Override
    public ICollection<IItem> containedItems() throws UnsupportedOperationException, IllegalStateException {
        return CONTAINED_ITEMS;
    }

    @Override
    public ICoordinate pixelOffset() throws IllegalStateException {
        return PIXEL_OFFSET;
    }

    @Override
    public IMap<String, IActiveAbility> activeAbilities() throws IllegalStateException {
        return ACTIVE_ABILITIES;
    }

    @Override
    public IMap<String, IReactiveAbility> reactiveAbilities() throws IllegalStateException {
        return REACTIVE_ABILITIES;
    }

    @Override
    public IGameZone gameZone() throws IllegalStateException {
        return GAME_ZONE;
    }

    @Override
    public IGenericParamsSet data() throws IllegalStateException {
        return DATA;
    }

    @Override
    public void delete() throws IllegalStateException {
        // TODO: Implement and test deletion of Items within the Fixture
        _deleted = true;
    }

    @Override
    public boolean isDeleted() {
        return _deleted;
    }

    @Override
    public String getName() {
        return _name;
    }

    @Override
    public void setName(String name) {
        _name = name;
    }

    @Override
    public void read(String data, boolean overridePreviousData) throws IllegalArgumentException {

    }

    @Override
    public String write() throws IllegalArgumentException {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return "soliloquy.gamestate.specs.ITileFixture";
    }
}

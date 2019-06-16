package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.valueobjects.ICoordinate;
import soliloquy.specs.common.valueobjects.IGenericParamsSet;
import soliloquy.specs.common.valueobjects.IMap;
import soliloquy.specs.gamestate.entities.ITile;
import soliloquy.specs.gamestate.entities.ITileFixture;
import soliloquy.specs.gamestate.entities.ITileFixtureItems;
import soliloquy.specs.ruleset.entities.IFixtureType;
import soliloquy.specs.ruleset.entities.abilities.IActiveAbility;
import soliloquy.specs.ruleset.entities.abilities.IReactiveAbility;

public class TileFixtureStub implements ITileFixture {
    private boolean _isDeleted;

    @Override
    public ITile tile() throws IllegalStateException {
        return null;
    }

    @Override
    public IFixtureType fixtureType() throws IllegalStateException {
        return null;
    }

    @Override
    public ICoordinate pixelOffset() throws IllegalStateException {
        return null;
    }

    @Override
    public IMap<String, IActiveAbility> activeAbilities() throws IllegalStateException {
        return null;
    }

    @Override
    public IMap<String, IReactiveAbility> reactiveAbilities() throws IllegalStateException {
        return null;
    }

    @Override
    public ITileFixtureItems containedItems() throws IllegalStateException {
        return null;
    }

    @Override
    public void assignTileFixtureToTile(ITile iTile) throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public IGenericParamsSet data() throws IllegalStateException {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String s) {

    }

    @Override
    public void delete() throws IllegalStateException {
        _isDeleted = true;
    }

    @Override
    public boolean isDeleted() {
        return _isDeleted;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

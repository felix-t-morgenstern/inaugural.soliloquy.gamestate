package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.infrastructure.IGenericParamsSet;
import soliloquy.specs.common.infrastructure.IMap;
import soliloquy.specs.common.valueobjects.ICoordinate;
import soliloquy.specs.gamestate.entities.ITile;
import soliloquy.specs.gamestate.entities.ITileFixture;
import soliloquy.specs.gamestate.entities.ITileFixtureItems;
import soliloquy.specs.ruleset.entities.IFixtureType;
import soliloquy.specs.ruleset.entities.abilities.IActiveAbility;
import soliloquy.specs.ruleset.entities.abilities.IReactiveAbility;

public class TileFixtureArchetype implements ITileFixture {
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

    }

    @Override
    public boolean isDeleted() {
        return false;
    }

    @Override
    public String getInterfaceName() {
        return ITileFixture.class.getCanonicalName();
    }
}

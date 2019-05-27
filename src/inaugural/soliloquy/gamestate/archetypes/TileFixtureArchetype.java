package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.common.specs.ICoordinate;
import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.common.specs.IMap;
import soliloquy.gamestate.specs.ITile;
import soliloquy.gamestate.specs.ITileFixture;
import soliloquy.gamestate.specs.ITileFixtureItems;
import soliloquy.ruleset.gameentities.abilities.specs.IActiveAbility;
import soliloquy.ruleset.gameentities.abilities.specs.IReactiveAbility;
import soliloquy.ruleset.gameentities.specs.IFixtureType;

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

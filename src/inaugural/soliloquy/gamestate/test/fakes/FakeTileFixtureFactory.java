package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.factories.TileFixtureFactory;
import soliloquy.specs.ruleset.entities.FixtureType;

public class FakeTileFixtureFactory implements TileFixtureFactory {
    @Override
    public TileFixture make(FixtureType fixtureType, VariableCache data) throws IllegalArgumentException {
        return null;
    }

    @Override
    public TileFixture make(FixtureType fixtureType, VariableCache data, EntityUuid id) throws IllegalArgumentException {
        return new FakeTileFixture(id, fixtureType, data);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

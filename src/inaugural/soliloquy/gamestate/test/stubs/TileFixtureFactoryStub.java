package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.factories.TileFixtureFactory;
import soliloquy.specs.ruleset.entities.FixtureType;

public class TileFixtureFactoryStub implements TileFixtureFactory {
    @Override
    public TileFixture make(FixtureType fixtureType, GenericParamsSet genericParamsSet) throws IllegalArgumentException {
        return null;
    }

    @Override
    public TileFixture make(FixtureType fixtureType, GenericParamsSet data, EntityUuid id) throws IllegalArgumentException {
        return new TileFixtureStub(id, fixtureType, data);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
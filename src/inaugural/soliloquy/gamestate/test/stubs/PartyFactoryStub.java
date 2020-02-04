package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Party;
import soliloquy.specs.gamestate.factories.PartyFactory;

public class PartyFactoryStub implements PartyFactory {
    @Override
    public Party make(VariableCache attributes) throws IllegalArgumentException {
        return new PartyStub(attributes);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

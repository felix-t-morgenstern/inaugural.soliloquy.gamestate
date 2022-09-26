package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Party;
import soliloquy.specs.gamestate.factories.PartyFactory;

public class FakePartyFactory implements PartyFactory {
    @Override
    public Party make(VariableCache attributes) throws IllegalArgumentException {
        return new FakeParty(attributes);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

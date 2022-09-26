package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.PartyImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Party;
import soliloquy.specs.gamestate.factories.PartyFactory;

public class PartyFactoryImpl implements PartyFactory {
    @Override
    public Party make(VariableCache data) throws IllegalArgumentException {
        return new PartyImpl(Check.ifNull(data, "data"));
    }

    @Override
    public String getInterfaceName() {
        return PartyFactory.class.getCanonicalName();
    }
}

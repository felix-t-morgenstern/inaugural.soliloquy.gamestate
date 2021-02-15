package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Party;
import soliloquy.specs.gamestate.factories.PartyFactory;

public class PartyFactoryImpl implements PartyFactory {
    private final ListFactory LIST_FACTORY;

    public PartyFactoryImpl(ListFactory listFactory) {
        LIST_FACTORY = Check.ifNull(listFactory, "listFactory");
    }

    @Override
    public Party make(VariableCache data) throws IllegalArgumentException {
        return new PartyImpl(LIST_FACTORY, data);
    }

    @Override
    public String getInterfaceName() {
        return PartyFactory.class.getCanonicalName();
    }
}

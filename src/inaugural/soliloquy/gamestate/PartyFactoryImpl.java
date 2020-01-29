package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Party;
import soliloquy.specs.gamestate.factories.PartyFactory;

public class PartyFactoryImpl implements PartyFactory {
    private final CollectionFactory COLLECTION_FACTORY;

    @SuppressWarnings("ConstantConditions")
    public PartyFactoryImpl(CollectionFactory collectionFactory) {
        if (collectionFactory == null) {
            throw new IllegalArgumentException(
                    "PartyFactoryImpl: collectionFactory cannot be null");
        }
        COLLECTION_FACTORY = collectionFactory;
    }

    @Override
    public Party make(VariableCache data) throws IllegalArgumentException {
        return new PartyImpl(COLLECTION_FACTORY, data);
    }

    @Override
    public String getInterfaceName() {
        return PartyFactory.class.getCanonicalName();
    }
}

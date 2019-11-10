package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.gamestate.entities.KeyBindingContext;
import soliloquy.specs.gamestate.factories.KeyBindingContextFactory;

public class KeyBindingContextFactoryImpl implements KeyBindingContextFactory {
    private final CollectionFactory COLLECTION_FACTORY;

    @SuppressWarnings("ConstantConditions")
    public KeyBindingContextFactoryImpl(CollectionFactory collectionFactory) {
        if (collectionFactory == null) {
            throw new IllegalArgumentException(
                    "KeyBindingContextFactoryImpl: collectionFactory cannot be null");
        }
        COLLECTION_FACTORY = collectionFactory;
    }

    @Override
    public KeyBindingContext make() {
        return new KeyBindingContextImpl(COLLECTION_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return KeyBindingContextFactory.class.getCanonicalName();
    }
}

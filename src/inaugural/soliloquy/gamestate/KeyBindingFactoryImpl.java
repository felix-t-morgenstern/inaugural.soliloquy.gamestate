package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.gamestate.entities.KeyBinding;
import soliloquy.specs.gamestate.factories.KeyBindingFactory;

public class KeyBindingFactoryImpl implements KeyBindingFactory {
    private final CollectionFactory COLLECTION_FACTORY;

    @SuppressWarnings("ConstantConditions")
    public KeyBindingFactoryImpl(CollectionFactory collectionFactory) {
        if (collectionFactory == null) {
            throw new IllegalArgumentException(
                    "KeyBindingFactoryImpl: collectionFactory cannot be null");
        }
        COLLECTION_FACTORY = collectionFactory;
    }

    @Override
    public KeyBinding make() {
        return new KeyBindingImpl(COLLECTION_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return KeyBindingFactory.class.getCanonicalName();
    }
}

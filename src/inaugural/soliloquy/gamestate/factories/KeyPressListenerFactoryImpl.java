package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.KeyPressListenerImpl;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.gamestate.entities.KeyPressListener;
import soliloquy.specs.gamestate.factories.KeyPressListenerFactory;

public class KeyPressListenerFactoryImpl implements KeyPressListenerFactory {
    private final MapFactory MAP_FACTORY;

    @SuppressWarnings("ConstantConditions")
    public KeyPressListenerFactoryImpl(MapFactory mapFactory) {
        if (mapFactory == null) {
            throw new IllegalArgumentException(
                    "KeyPressListenerFactoryImpl: mapFactory cannot be null");
        }
        MAP_FACTORY = mapFactory;
    }

    @Override
    public KeyPressListener make() {
        return new KeyPressListenerImpl(MAP_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return KeyPressListenerFactory.class.getCanonicalName();
    }
}

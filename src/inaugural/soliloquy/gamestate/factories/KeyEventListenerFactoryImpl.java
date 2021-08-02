package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.KeyEventListenerImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.gamestate.entities.KeyEventListener;
import soliloquy.specs.gamestate.factories.KeyEventListenerFactory;

public class KeyEventListenerFactoryImpl implements KeyEventListenerFactory {
    private final ListFactory LIST_FACTORY;
    private final MapFactory MAP_FACTORY;

    public KeyEventListenerFactoryImpl(ListFactory listFactory, MapFactory mapFactory) {
        LIST_FACTORY = Check.ifNull(listFactory, "listFactory");
        MAP_FACTORY = Check.ifNull(mapFactory, "mapFactory");
    }

    @Override
    public KeyEventListener make(Long mostRecentTimestamp) {
        return new KeyEventListenerImpl(LIST_FACTORY, MAP_FACTORY, mostRecentTimestamp);
    }

    @Override
    public String getInterfaceName() {
        return KeyEventListenerFactory.class.getCanonicalName();
    }
}

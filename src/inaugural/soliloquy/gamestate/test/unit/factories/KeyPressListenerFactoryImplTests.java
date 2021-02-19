package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.KeyPressListenerFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeMapFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.gamestate.factories.KeyPressListenerFactory;

import static org.junit.jupiter.api.Assertions.*;

class KeyPressListenerFactoryImplTests {
    private final MapFactory MAP_FACTORY = new FakeMapFactory();

    private KeyPressListenerFactory _keyPressListenerFactory;

    @BeforeEach
    void setUp() {
        _keyPressListenerFactory = new KeyPressListenerFactoryImpl(MAP_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new KeyPressListenerFactoryImpl(null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(KeyPressListenerFactory.class.getCanonicalName(),
                _keyPressListenerFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        assertNotNull(_keyPressListenerFactory.make());
    }
}

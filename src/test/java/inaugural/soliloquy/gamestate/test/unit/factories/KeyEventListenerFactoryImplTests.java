package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.KeyEventListenerFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.factories.KeyEventListenerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class KeyEventListenerFactoryImplTests {
    private KeyEventListenerFactory _keyEventListenerFactory;

    @BeforeEach
    void setUp() {
        _keyEventListenerFactory = new KeyEventListenerFactoryImpl();
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(KeyEventListenerFactory.class.getCanonicalName(),
                _keyEventListenerFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        assertNotNull(_keyEventListenerFactory.make(null));
    }
}

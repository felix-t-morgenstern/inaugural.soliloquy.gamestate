package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.KeyBindingContextFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.factories.KeyBindingContextFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class KeyBindingContextFactoryImplTests {
    private KeyBindingContextFactory _keyBindingContextFactory;

    @BeforeEach
    void setUp() {
        _keyBindingContextFactory = new KeyBindingContextFactoryImpl();
    }
    @Test
    void testGetInterfaceName() {
        assertEquals(KeyBindingContextFactory.class.getCanonicalName(),
                _keyBindingContextFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        assertNotNull(_keyBindingContextFactory.make());
    }
}

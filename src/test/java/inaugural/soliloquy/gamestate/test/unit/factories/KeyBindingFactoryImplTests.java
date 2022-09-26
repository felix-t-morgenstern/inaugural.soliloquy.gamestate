package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.KeyBindingFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.entities.KeyBinding;
import soliloquy.specs.gamestate.factories.KeyBindingFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class KeyBindingFactoryImplTests {
    private KeyBindingFactory _keyBindingFactory;

    @BeforeEach
    void setUp() {
        _keyBindingFactory = new KeyBindingFactoryImpl();
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(KeyBindingFactory.class.getCanonicalName(),
                _keyBindingFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        KeyBinding keyBinding = _keyBindingFactory.make();

        assertNotNull(keyBinding);
    }
}

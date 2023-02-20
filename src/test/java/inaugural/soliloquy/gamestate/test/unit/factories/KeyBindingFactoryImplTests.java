package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.KeyBindingFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.factories.KeyBindingFactory;

import static inaugural.soliloquy.tools.random.Random.randomChar;
import static org.junit.jupiter.api.Assertions.*;

class KeyBindingFactoryImplTests {
    private final char[] CHARACTERS = new char[]{randomChar(), randomChar(), randomChar()};

    private KeyBindingFactory keyBindingFactory;

    @BeforeEach
    void setUp() {
        keyBindingFactory = new KeyBindingFactoryImpl();
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(KeyBindingFactory.class.getCanonicalName(),
                keyBindingFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        var output = keyBindingFactory.make(CHARACTERS);

        assertNotNull(output);
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> keyBindingFactory.make(null));
    }
}

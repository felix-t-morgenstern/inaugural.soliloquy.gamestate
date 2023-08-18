package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.KeyBindingFactoryImpl;
import org.junit.Before;
import org.junit.Test;
import soliloquy.specs.gamestate.factories.KeyBindingFactory;

import static inaugural.soliloquy.tools.random.Random.randomChar;
import static org.junit.Assert.*;

public class KeyBindingFactoryImplTests {
    private final char[] CHARACTERS = new char[]{randomChar(), randomChar(), randomChar()};

    private KeyBindingFactory keyBindingFactory;

    @Before
    public void setUp() {
        keyBindingFactory = new KeyBindingFactoryImpl();
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(KeyBindingFactory.class.getCanonicalName(),
                keyBindingFactory.getInterfaceName());
    }

    @Test
    public void testMake() {
        var output = keyBindingFactory.make(CHARACTERS);

        assertNotNull(output);
    }

    @Test
    public void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> keyBindingFactory.make(null));
    }
}

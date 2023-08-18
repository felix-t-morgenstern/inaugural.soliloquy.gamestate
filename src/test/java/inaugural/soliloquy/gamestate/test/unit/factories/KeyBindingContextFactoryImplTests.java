package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.KeyBindingContextFactoryImpl;
import org.junit.Before;
import org.junit.Test;
import soliloquy.specs.gamestate.factories.KeyBindingContextFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class KeyBindingContextFactoryImplTests {
    private KeyBindingContextFactory keyBindingContextFactory;

    @Before
    public void setUp() {
        keyBindingContextFactory = new KeyBindingContextFactoryImpl();
    }
    @Test
    public void testGetInterfaceName() {
        assertEquals(KeyBindingContextFactory.class.getCanonicalName(),
                keyBindingContextFactory.getInterfaceName());
    }

    @Test
    public void testMake() {
        assertNotNull(keyBindingContextFactory.make());
    }
}

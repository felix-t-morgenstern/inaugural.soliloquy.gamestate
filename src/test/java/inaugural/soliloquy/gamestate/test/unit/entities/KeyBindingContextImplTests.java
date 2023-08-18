package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.KeyBindingContextImpl;
import org.junit.Before;
import org.junit.Test;
import soliloquy.specs.gamestate.entities.KeyBindingContext;

import static org.junit.Assert.*;

public class KeyBindingContextImplTests {
    private KeyBindingContext keyBindingContext;

    @Before
    public void setUp() {
        keyBindingContext = new KeyBindingContextImpl();
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(KeyBindingContext.class.getCanonicalName(),
                keyBindingContext.getInterfaceName());
    }

    @Test
    public void testBindings() {
        assertNotNull(keyBindingContext.bindings());
    }

    @Test
    public void testSetAndGetBlocksAllLowerBindings() {
        keyBindingContext.setBlocksAllLowerBindings(true);

        assertTrue(keyBindingContext.getBlocksAllLowerBindings());
    }
}

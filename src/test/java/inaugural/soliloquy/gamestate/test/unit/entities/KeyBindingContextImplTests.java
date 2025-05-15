package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.KeyBindingContextImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.entities.KeyBindingContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KeyBindingContextImplTests {
    private KeyBindingContext keyBindingContext;

    @BeforeEach
    public void setUp() {
        keyBindingContext = new KeyBindingContextImpl();
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

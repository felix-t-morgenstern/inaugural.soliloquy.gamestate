package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.KeyBindingContextImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.entities.KeyBindingContext;

import static org.junit.jupiter.api.Assertions.*;

class KeyBindingContextImplTests {
    private KeyBindingContext _keyBindingContext;

    @BeforeEach
    void setUp() {
        _keyBindingContext = new KeyBindingContextImpl();
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(KeyBindingContext.class.getCanonicalName(),
                _keyBindingContext.getInterfaceName());
    }

    @Test
    void testBindings() {
        assertNotNull(_keyBindingContext.bindings());
    }

    @Test
    void testSetAndGetBlocksAllLowerBindings() {
        _keyBindingContext.setBlocksAllLowerBindings(true);

        assertTrue(_keyBindingContext.getBlocksAllLowerBindings());
    }
}

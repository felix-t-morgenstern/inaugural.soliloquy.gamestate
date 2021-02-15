package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.KeyBindingContextImpl;
import inaugural.soliloquy.gamestate.KeyBindingFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeListFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.gamestate.entities.KeyBindingContext;

import static org.junit.jupiter.api.Assertions.*;

class KeyBindingContextImplTests {
    private final ListFactory LIST_FACTORY = new FakeListFactory();

    private KeyBindingContext _keyBindingContext;

    @BeforeEach
    void setUp() {
        _keyBindingContext = new KeyBindingContextImpl(LIST_FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new KeyBindingFactoryImpl(null));
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

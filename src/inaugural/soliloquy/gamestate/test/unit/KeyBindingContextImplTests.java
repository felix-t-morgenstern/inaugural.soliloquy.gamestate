package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.KeyBindingContextImpl;
import inaugural.soliloquy.gamestate.KeyBindingFactoryImpl;
import inaugural.soliloquy.gamestate.test.stubs.CollectionFactoryStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.gamestate.entities.KeyBindingContext;

import static org.junit.jupiter.api.Assertions.*;

class KeyBindingContextImplTests {
    private final CollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();

    private KeyBindingContext _keyBindingContext;

    @BeforeEach
    void setUp() {
        _keyBindingContext = new KeyBindingContextImpl(COLLECTION_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
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

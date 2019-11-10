package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.KeyBindingFactoryImpl;
import inaugural.soliloquy.gamestate.test.stubs.CollectionFactoryStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.gamestate.entities.KeyBinding;
import soliloquy.specs.gamestate.factories.KeyBindingFactory;

import static org.junit.jupiter.api.Assertions.*;

class KeyBindingFactoryImplTests {
    private final CollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();

    private KeyBindingFactory _keyBindingFactory;

    @BeforeEach
    void setUp() {
        _keyBindingFactory = new KeyBindingFactoryImpl(COLLECTION_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new KeyBindingFactoryImpl(null));
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

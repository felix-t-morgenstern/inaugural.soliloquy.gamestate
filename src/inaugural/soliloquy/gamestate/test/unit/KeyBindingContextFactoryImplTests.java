package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.KeyBindingContextFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeCollectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.gamestate.factories.KeyBindingContextFactory;

import static org.junit.jupiter.api.Assertions.*;

class KeyBindingContextFactoryImplTests {
    private final CollectionFactory COLLECTION_FACTORY = new FakeCollectionFactory();

    private KeyBindingContextFactory _keyBindingContextFactory;

    @BeforeEach
    void setUp() {
        _keyBindingContextFactory = new KeyBindingContextFactoryImpl(COLLECTION_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new KeyBindingContextFactoryImpl(null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(KeyBindingContextFactory.class.getCanonicalName(),
                _keyBindingContextFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        assertNotNull(_keyBindingContextFactory.make());
    }
}

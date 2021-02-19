package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.KeyBindingContextFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeListFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.gamestate.factories.KeyBindingContextFactory;

import static org.junit.jupiter.api.Assertions.*;

class KeyBindingContextFactoryImplTests {
    private final ListFactory LIST_FACTORY = new FakeListFactory();

    private KeyBindingContextFactory _keyBindingContextFactory;

    @BeforeEach
    void setUp() {
        _keyBindingContextFactory = new KeyBindingContextFactoryImpl(LIST_FACTORY);
    }

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

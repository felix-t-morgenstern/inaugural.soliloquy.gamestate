package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.KeyEventListenerFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeListFactory;
import inaugural.soliloquy.gamestate.test.fakes.FakeMapFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.gamestate.factories.KeyEventListenerFactory;

import static org.junit.jupiter.api.Assertions.*;

class KeyEventListenerFactoryImplTests {
    private final ListFactory LIST_FACTORY = new FakeListFactory();
    private final MapFactory MAP_FACTORY = new FakeMapFactory();

    private KeyEventListenerFactory _keyEventListenerFactory;

    @BeforeEach
    void setUp() {
        _keyEventListenerFactory = new KeyEventListenerFactoryImpl(LIST_FACTORY, MAP_FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                new KeyEventListenerFactoryImpl(null, MAP_FACTORY));
        assertThrows(IllegalArgumentException.class, () ->
                new KeyEventListenerFactoryImpl(LIST_FACTORY, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(KeyEventListenerFactory.class.getCanonicalName(),
                _keyEventListenerFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        assertNotNull(_keyEventListenerFactory.make(null));
    }
}

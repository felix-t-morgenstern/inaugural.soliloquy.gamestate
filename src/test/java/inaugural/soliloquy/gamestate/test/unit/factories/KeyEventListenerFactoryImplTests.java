package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.KeyEventListenerFactoryImpl;
import org.junit.Before;
import org.junit.Test;
import soliloquy.specs.gamestate.factories.KeyEventListenerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class KeyEventListenerFactoryImplTests {
    private KeyEventListenerFactory keyEventListenerFactory;

    @Before
    public void setUp() {
        keyEventListenerFactory = new KeyEventListenerFactoryImpl();
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(KeyEventListenerFactory.class.getCanonicalName(),
                keyEventListenerFactory.getInterfaceName());
    }

    @Test
    public void testMake() {
        assertNotNull(keyEventListenerFactory.make(null));
    }
}

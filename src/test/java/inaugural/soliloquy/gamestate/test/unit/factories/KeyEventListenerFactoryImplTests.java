package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.KeyEventListenerFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.factories.KeyEventListenerFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class KeyEventListenerFactoryImplTests {
    private KeyEventListenerFactory keyEventListenerFactory;

    @BeforeEach
    public void setUp() {
        keyEventListenerFactory = new KeyEventListenerFactoryImpl();
    }

    @Test
    public void testMake() {
        assertNotNull(keyEventListenerFactory.make(null));
    }
}

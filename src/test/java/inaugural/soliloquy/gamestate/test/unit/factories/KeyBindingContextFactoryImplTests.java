package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.KeyBindingContextFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.factories.KeyBindingContextFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class KeyBindingContextFactoryImplTests {
    private KeyBindingContextFactory keyBindingContextFactory;

    @BeforeEach
    public void setUp() {
        keyBindingContextFactory = new KeyBindingContextFactoryImpl();
    }

    @Test
    public void testMake() {
        assertNotNull(keyBindingContextFactory.make());
    }
}

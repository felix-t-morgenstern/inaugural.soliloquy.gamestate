package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.KeyBindingFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.factories.KeyBindingFactory;

import static inaugural.soliloquy.tools.random.Random.randomChar;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class KeyBindingFactoryImplTests {
    private final char[] CHARACTERS = new char[]{randomChar(), randomChar(), randomChar()};

    private KeyBindingFactory keyBindingFactory;

    @BeforeEach
    public void setUp() {
        keyBindingFactory = new KeyBindingFactoryImpl();
    }

    @Test
    public void testMake() {
        var output = keyBindingFactory.make(CHARACTERS);

        assertNotNull(output);
    }

    @Test
    public void testMakeWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> keyBindingFactory.make(null));
    }
}

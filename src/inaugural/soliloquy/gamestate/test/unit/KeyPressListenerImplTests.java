package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.KeyPressListenerImpl;
import inaugural.soliloquy.gamestate.test.stubs.KeyBindingContextStub;
import inaugural.soliloquy.gamestate.test.stubs.KeyBindingStub;
import inaugural.soliloquy.gamestate.test.stubs.MapFactoryStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.gamestate.entities.KeyBindingContext;
import soliloquy.specs.gamestate.entities.KeyPressListener;

import java.awt.*;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

class KeyPressListenerImplTests {
    private final MapFactory MAP_FACTORY = new MapFactoryStub();
    private final Panel SOURCE = new Panel();

    private KeyPressListener _keyPressListener;

    @BeforeEach
    void setUp() {
        _keyPressListener = new KeyPressListenerImpl(MAP_FACTORY);
        SOURCE.addKeyListener(_keyPressListener);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new KeyPressListenerImpl(null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(KeyPressListener.class.getCanonicalName(),
                _keyPressListener.getInterfaceName());
    }

    @Test
    void testKeyBindingContexts() {
        assertNotNull(_keyPressListener.contexts());
        assertNotNull(_keyPressListener.contexts().getFirstArchetype());
        assertNotNull(_keyPressListener.contexts().getSecondArchetype());
    }

    @Test
    void testKeyTyped() throws InterruptedException {
        KeyBindingStub keyBinding = new KeyBindingStub();
        keyBinding.boundCharacters().add('a');

        KeyBindingContext keyBindingContext = new KeyBindingContextStub();
        keyBindingContext.bindings().add(keyBinding);

        _keyPressListener.contexts().put(0,keyBindingContext);

        KeyEvent key = new KeyEvent(SOURCE, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
                KeyEvent.VK_A, 'a');
        _keyPressListener.keyPressed(key);

        Thread.sleep(10);

        assertTrue(keyBinding._pressed);
    }

    @Test
    void testKeyReleased() throws InterruptedException {
        KeyBindingStub keyBinding = new KeyBindingStub();
        keyBinding.boundCharacters().add('a');

        KeyBindingContext keyBindingContext = new KeyBindingContextStub();
        keyBindingContext.bindings().add(keyBinding);

        _keyPressListener.contexts().put(0,keyBindingContext);

        KeyEvent key = new KeyEvent(SOURCE, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0,
                KeyEvent.VK_A, 'a');
        _keyPressListener.keyReleased(key);

        Thread.sleep(10);

        assertTrue(keyBinding._released);
    }
}

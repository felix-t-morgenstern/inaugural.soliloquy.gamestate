package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.KeyPressListenerImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeKeyBindingContext;
import inaugural.soliloquy.gamestate.test.fakes.FakeKeyBinding;
import inaugural.soliloquy.gamestate.test.fakes.FakeMapFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.gamestate.entities.KeyBindingContext;
import soliloquy.specs.gamestate.entities.KeyPressListener;

import java.awt.*;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

class KeyPressListenerImplTests {
    private final MapFactory MAP_FACTORY = new FakeMapFactory();
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
    void testKeyPressed() throws InterruptedException {
        FakeKeyBinding keyBinding = new FakeKeyBinding();
        keyBinding.boundCharacters().add('a');

        KeyBindingContext keyBindingContext = new FakeKeyBindingContext();
        keyBindingContext.bindings().add(keyBinding);

        _keyPressListener.contexts().put(0,keyBindingContext);

        KeyEvent key = new KeyEvent(SOURCE, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
                KeyEvent.VK_UNDEFINED, 'a');

        _keyPressListener.keyPressed(key);

        Thread.sleep(10);

        assertTrue(keyBinding._pressed);
    }

    @Test
    void testKeyReleased() throws InterruptedException {
        FakeKeyBinding keyBinding = new FakeKeyBinding();
        keyBinding.boundCharacters().add('a');

        KeyBindingContext keyBindingContext = new FakeKeyBindingContext();
        keyBindingContext.bindings().add(keyBinding);

        _keyPressListener.contexts().put(0,keyBindingContext);

        KeyEvent key = new KeyEvent(SOURCE, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0,
                KeyEvent.VK_UNDEFINED, 'a');

        _keyPressListener.keyReleased(key);

        Thread.sleep(10);

        assertTrue(keyBinding._released);
    }

    @Test
    void testKeyTyped() throws InterruptedException {
        FakeKeyBinding keyBinding = new FakeKeyBinding();
        keyBinding.boundCharacters().add('a');

        KeyBindingContext keyBindingContext = new FakeKeyBindingContext();
        keyBindingContext.bindings().add(keyBinding);

        _keyPressListener.contexts().put(0,keyBindingContext);

        KeyEvent key = new KeyEvent(SOURCE, KeyEvent.KEY_TYPED, System.currentTimeMillis(), 0,
                KeyEvent.VK_UNDEFINED, 'a');

        _keyPressListener.keyTyped(key);

        Thread.sleep(10);

        assertTrue(keyBinding._typed);
    }

    @Test
    void testContextBlocksLowerContextEvents() throws InterruptedException {
        FakeKeyBinding lowerKeyBinding = new FakeKeyBinding();
        lowerKeyBinding.boundCharacters().add('a');

        KeyBindingContext lowerKeyBindingContext = new FakeKeyBindingContext();
        lowerKeyBindingContext.bindings().add(lowerKeyBinding);

        FakeKeyBinding upperKeyBinding = new FakeKeyBinding();
        upperKeyBinding.boundCharacters().add('a');

        KeyBindingContext upperKeyBindingContext = new FakeKeyBindingContext();
        upperKeyBindingContext.bindings().add(upperKeyBinding);

        upperKeyBindingContext.setBlocksAllLowerBindings(true);

        _keyPressListener.contexts().put(0,upperKeyBindingContext);
        _keyPressListener.contexts().put(1,lowerKeyBindingContext);

        KeyEvent keyPress = new KeyEvent(SOURCE, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
                KeyEvent.VK_UNDEFINED, 'a');

        _keyPressListener.keyPressed(keyPress);

        Thread.sleep(10);

        assertTrue(upperKeyBinding._pressed);
        assertFalse(lowerKeyBinding._pressed);

        KeyEvent keyRelease = new KeyEvent(SOURCE, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0,
                KeyEvent.VK_UNDEFINED, 'a');

        _keyPressListener.keyReleased(keyRelease);

        Thread.sleep(10);

        assertTrue(upperKeyBinding._released);
        assertFalse(lowerKeyBinding._released);

        KeyEvent keyType= new KeyEvent(SOURCE, KeyEvent.KEY_TYPED, System.currentTimeMillis(), 0,
                KeyEvent.VK_UNDEFINED, 'a');

        _keyPressListener.keyTyped(keyRelease);

        Thread.sleep(10);

        assertTrue(upperKeyBinding._typed);
        assertFalse(lowerKeyBinding._typed);
    }

    @Test
    void testBindingBlocksLowerBindingEvents() throws InterruptedException {
        FakeKeyBinding lowerKeyBinding = new FakeKeyBinding();
        lowerKeyBinding.boundCharacters().add('a');

        KeyBindingContext lowerKeyBindingContext = new FakeKeyBindingContext();
        lowerKeyBindingContext.bindings().add(lowerKeyBinding);

        FakeKeyBinding upperKeyBinding = new FakeKeyBinding();
        upperKeyBinding.boundCharacters().add('a');

        KeyBindingContext upperKeyBindingContext = new FakeKeyBindingContext();
        upperKeyBindingContext.bindings().add(upperKeyBinding);

        upperKeyBinding.setBlocksLowerBindings(true);

        _keyPressListener.contexts().put(0,upperKeyBindingContext);
        _keyPressListener.contexts().put(1,lowerKeyBindingContext);

        KeyEvent keyPress = new KeyEvent(SOURCE, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
                KeyEvent.VK_UNDEFINED, 'a');

        _keyPressListener.keyPressed(keyPress);

        Thread.sleep(10);

        assertTrue(upperKeyBinding._pressed);
        assertFalse(lowerKeyBinding._pressed);

        KeyEvent keyRelease = new KeyEvent(SOURCE, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0,
                KeyEvent.VK_UNDEFINED, 'a');

        _keyPressListener.keyReleased(keyRelease);

        Thread.sleep(10);

        assertTrue(upperKeyBinding._released);
        assertFalse(lowerKeyBinding._released);

        KeyEvent keyType= new KeyEvent(SOURCE, KeyEvent.KEY_TYPED, System.currentTimeMillis(), 0,
                KeyEvent.VK_UNDEFINED, 'a');

        _keyPressListener.keyTyped(keyRelease);

        Thread.sleep(10);

        assertTrue(upperKeyBinding._typed);
        assertFalse(lowerKeyBinding._typed);
    }
}

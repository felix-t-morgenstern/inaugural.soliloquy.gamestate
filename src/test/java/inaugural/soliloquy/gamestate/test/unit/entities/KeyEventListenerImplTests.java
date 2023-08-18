package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.KeyEventListenerImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeKeyBinding;
import inaugural.soliloquy.gamestate.test.fakes.FakeKeyBindingContext;
import org.junit.Before;
import org.junit.Test;
import soliloquy.specs.gamestate.entities.KeyBindingContext;
import soliloquy.specs.gamestate.entities.KeyEventListener;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

// TODO: Awful suite.
public class KeyEventListenerImplTests {
    private final Long MOST_RECENT_TIMESTAMP = 123123L;
    private final char CHAR = 'a';

    private KeyEventListener keyEventListener;

    @Before
    public void setUp() {
        keyEventListener =
                new KeyEventListenerImpl(MOST_RECENT_TIMESTAMP);
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(KeyEventListener.class.getCanonicalName(),
                keyEventListener.getInterfaceName());
    }

    @Test
    public void testAddAndRemoveContextAndContextsRepresentation() {
        FakeKeyBindingContext keyBindingContext1 = new FakeKeyBindingContext();
        FakeKeyBindingContext keyBindingContext2 = new FakeKeyBindingContext();
        FakeKeyBindingContext keyBindingContext3 = new FakeKeyBindingContext();
        int priority1 = 123;
        int priority2 = 456;

        keyEventListener.addContext(keyBindingContext1, priority1);
        keyEventListener.addContext(keyBindingContext2, priority2);
        keyEventListener.addContext(keyBindingContext3, priority1);

        Map<Integer, List<KeyBindingContext>> contextsRepresentation =
                keyEventListener.contextsRepresentation();
        Map<Integer, List<KeyBindingContext>> contextsRepresentation2 =
                keyEventListener.contextsRepresentation();

        assertNotNull(contextsRepresentation);
        assertNotSame(contextsRepresentation, contextsRepresentation2);
        assertEquals(2, contextsRepresentation.size());
        assertEquals(2, contextsRepresentation.get(priority1).size());
        assertTrue(contextsRepresentation.get(priority1).contains(keyBindingContext1));
        assertTrue(contextsRepresentation.get(priority1).contains(keyBindingContext3));
        assertEquals(1, contextsRepresentation.get(priority2).size());
        assertTrue(contextsRepresentation.get(priority2).contains(keyBindingContext2));

        keyEventListener.removeContext(keyBindingContext2);
        Map<Integer, List<KeyBindingContext>> contextsRepresentationUpdated =
                keyEventListener.contextsRepresentation();

        assertEquals(1, contextsRepresentationUpdated.size());
        assertEquals(2, contextsRepresentationUpdated.get(priority1).size());
        assertTrue(contextsRepresentationUpdated.get(priority1).contains(keyBindingContext1));
        assertTrue(contextsRepresentationUpdated.get(priority1).contains(keyBindingContext3));
    }

    @Test
    public void testAddContextUpdatesPriority() {
        FakeKeyBindingContext keyBindingContext1 = new FakeKeyBindingContext();
        FakeKeyBindingContext keyBindingContext2 = new FakeKeyBindingContext();
        FakeKeyBindingContext keyBindingContext3 = new FakeKeyBindingContext();
        int priority1 = 123;
        int priority2 = 456;

        keyEventListener.addContext(keyBindingContext1, priority1);
        keyEventListener.addContext(keyBindingContext2, priority2);
        keyEventListener.addContext(keyBindingContext3, priority1);

        Map<Integer, List<KeyBindingContext>> contextsRepresentation =
                keyEventListener.contextsRepresentation();
        Map<Integer, List<KeyBindingContext>> contextsRepresentation2 =
                keyEventListener.contextsRepresentation();

        assertNotNull(contextsRepresentation);
        assertNotSame(contextsRepresentation, contextsRepresentation2);
        assertEquals(2, contextsRepresentation.size());
        assertEquals(2, contextsRepresentation.get(priority1).size());
        assertTrue(contextsRepresentation.get(priority1).contains(keyBindingContext1));
        assertTrue(contextsRepresentation.get(priority1).contains(keyBindingContext3));
        assertEquals(1, contextsRepresentation.get(priority2).size());
        assertTrue(contextsRepresentation.get(priority2).contains(keyBindingContext2));

        keyEventListener.addContext(keyBindingContext3, priority2);
        Map<Integer, List<KeyBindingContext>> contextsRepresentationUpdated =
                keyEventListener.contextsRepresentation();

        assertEquals(2, contextsRepresentationUpdated.size());
        assertEquals(1, contextsRepresentationUpdated.get(priority1).size());
        assertTrue(contextsRepresentationUpdated.get(priority1).contains(keyBindingContext1));
        assertEquals(2, contextsRepresentationUpdated.get(priority2).size());
        assertTrue(contextsRepresentationUpdated.get(priority2).contains(keyBindingContext2));
        assertTrue(contextsRepresentationUpdated.get(priority2).contains(keyBindingContext3));
    }

    @Test
    public void testAddAndRemoveContextWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> keyEventListener.addContext(null, 0));
        assertThrows(IllegalArgumentException.class, () -> keyEventListener.removeContext(null));
    }

    @Test
    public void testActiveKeysRepresentation() {
        FakeKeyBindingContext keyBindingContext1 = new FakeKeyBindingContext();
        FakeKeyBindingContext keyBindingContext2 = new FakeKeyBindingContext();
        FakeKeyBindingContext keyBindingContext3 = new FakeKeyBindingContext();

        FakeKeyBinding keyBindingContext1Binding1 = new FakeKeyBinding();
        keyBindingContext1Binding1.boundCharacters().add('a');
        keyBindingContext1Binding1.boundCharacters().add('b');
        keyBindingContext1Binding1.setBlocksLowerBindings(true);
        keyBindingContext1.bindings().add(keyBindingContext1Binding1);
        FakeKeyBinding keyBindingContext1Binding2 = new FakeKeyBinding();
        keyBindingContext1Binding2.boundCharacters().add('c');
        keyBindingContext1.bindings().add(keyBindingContext1Binding2);

        FakeKeyBinding keyBindingContext2Binding1 = new FakeKeyBinding();
        keyBindingContext2Binding1.boundCharacters().add('d');
        FakeKeyBinding keyBindingContext2Binding2 = new FakeKeyBinding();
        keyBindingContext2Binding2.boundCharacters().add('e');
        keyBindingContext2.bindings().add(keyBindingContext2Binding1);
        keyBindingContext2.bindings().add(keyBindingContext2Binding2);
        keyBindingContext2.setBlocksAllLowerBindings(true);

        FakeKeyBinding keyBindingContext3Binding1 = new FakeKeyBinding();
        keyBindingContext3Binding1.boundCharacters().add('f');
        keyBindingContext3.bindings().add(keyBindingContext3Binding1);

        keyEventListener.addContext(keyBindingContext1, 1);
        keyEventListener.addContext(keyBindingContext2, 2);
        keyEventListener.addContext(keyBindingContext3, 3);

        java.util.List<java.lang.Character> activeKeysRepresentation =
                keyEventListener.activeKeysRepresentation();
        java.util.List<java.lang.Character> activeKeysRepresentation2 =
                keyEventListener.activeKeysRepresentation();

        assertNotNull(activeKeysRepresentation);
        assertNotSame(activeKeysRepresentation, activeKeysRepresentation2);
        assertEquals(4, activeKeysRepresentation.size());
        assertTrue(activeKeysRepresentation.contains('a'));
        assertTrue(activeKeysRepresentation.contains('b'));
        assertTrue(activeKeysRepresentation.contains('d'));
        assertTrue(activeKeysRepresentation.contains('e'));
    }

    @Test
    public void testKeyPressed() {
        FakeKeyBinding keyBinding = new FakeKeyBinding();
        keyBinding.boundCharacters().add(CHAR);

        KeyBindingContext keyBindingContext = new FakeKeyBindingContext();
        keyBindingContext.bindings().add(keyBinding);

        keyEventListener.addContext(keyBindingContext, 0);

        keyEventListener.press(CHAR, MOST_RECENT_TIMESTAMP);

        assertEquals(MOST_RECENT_TIMESTAMP, keyBinding._pressed);
    }

    @Test
    public void testKeyReleased() {
        FakeKeyBinding keyBinding = new FakeKeyBinding();
        keyBinding.boundCharacters().add(CHAR);

        KeyBindingContext keyBindingContext = new FakeKeyBindingContext();
        keyBindingContext.bindings().add(keyBinding);

        keyEventListener.addContext(keyBindingContext, 0);

        keyEventListener.release(CHAR, MOST_RECENT_TIMESTAMP);

        assertEquals(MOST_RECENT_TIMESTAMP, keyBinding._released);
    }

    @Test
    public void testContextBlocksLowerContextEvents() {
        FakeKeyBinding lowerKeyBinding = new FakeKeyBinding();
        lowerKeyBinding.boundCharacters().add(CHAR);

        KeyBindingContext lowerKeyBindingContext = new FakeKeyBindingContext();
        lowerKeyBindingContext.bindings().add(lowerKeyBinding);

        FakeKeyBinding upperKeyBinding = new FakeKeyBinding();
        upperKeyBinding.boundCharacters().add(CHAR);

        KeyBindingContext upperKeyBindingContext = new FakeKeyBindingContext();
        upperKeyBindingContext.bindings().add(upperKeyBinding);

        upperKeyBindingContext.setBlocksAllLowerBindings(true);

        keyEventListener.addContext(upperKeyBindingContext, 0);
        keyEventListener.addContext(lowerKeyBindingContext, 1);

        keyEventListener.press(CHAR, MOST_RECENT_TIMESTAMP);

        assertEquals(MOST_RECENT_TIMESTAMP, upperKeyBinding._pressed);
        assertNull(lowerKeyBinding._pressed);

        keyEventListener.release(CHAR, MOST_RECENT_TIMESTAMP);

        assertEquals(MOST_RECENT_TIMESTAMP, upperKeyBinding._released);
        assertNull(lowerKeyBinding._released);
    }

    @Test
    public void testBindingBlocksLowerBindingEvents() {
        FakeKeyBinding lowerKeyBinding = new FakeKeyBinding();
        lowerKeyBinding.boundCharacters().add('a');

        KeyBindingContext lowerKeyBindingContext = new FakeKeyBindingContext();
        lowerKeyBindingContext.bindings().add(lowerKeyBinding);

        FakeKeyBinding upperKeyBinding = new FakeKeyBinding();
        upperKeyBinding.boundCharacters().add(CHAR);

        KeyBindingContext upperKeyBindingContext = new FakeKeyBindingContext();
        upperKeyBindingContext.bindings().add(upperKeyBinding);

        upperKeyBinding.setBlocksLowerBindings(true);

        keyEventListener.addContext(upperKeyBindingContext, 0);
        keyEventListener.addContext(lowerKeyBindingContext, 1);

        keyEventListener.press(CHAR, MOST_RECENT_TIMESTAMP);

        assertEquals(MOST_RECENT_TIMESTAMP, upperKeyBinding._pressed);
        assertEquals(MOST_RECENT_TIMESTAMP, lowerKeyBinding._pressed);

        keyEventListener.release(CHAR, MOST_RECENT_TIMESTAMP);

        assertEquals(MOST_RECENT_TIMESTAMP, upperKeyBinding._released);
        assertEquals(MOST_RECENT_TIMESTAMP, lowerKeyBinding._released);
    }

    @Test
    public void testPressOrReleaseAtInvalidTimestamp() {
        assertThrows(IllegalArgumentException.class, () ->
                keyEventListener.press(CHAR, MOST_RECENT_TIMESTAMP - 1));
        assertThrows(IllegalArgumentException.class, () ->
                keyEventListener.release(CHAR, MOST_RECENT_TIMESTAMP - 1));
    }
}

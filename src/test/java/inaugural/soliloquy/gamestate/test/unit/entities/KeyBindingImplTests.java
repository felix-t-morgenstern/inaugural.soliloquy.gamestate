package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.KeyBindingImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.entities.KeyBinding;

import static inaugural.soliloquy.tools.random.Random.*;
import static org.junit.jupiter.api.Assertions.*;

class KeyBindingImplTests {
    private final String KEY_PRESS_ACTION_ID = randomString();
    private final String KEY_RELEASE_ACTION_ID = randomString();
    private final FakeAction<Long> KEY_PRESS_ACTION = new FakeAction<>(KEY_PRESS_ACTION_ID);
    private final FakeAction<Long> KEY_RELEASE_ACTION = new FakeAction<>(KEY_RELEASE_ACTION_ID);
    private final long TIMESTAMP = randomLong();
    private final char[] CHARACTERS = new char[]{randomChar(), randomChar(), randomChar()};

    private KeyBinding keyBinding;

    @BeforeEach
    void setUp() {
        keyBinding = new KeyBindingImpl(CHARACTERS);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new KeyBindingImpl(null));
    }

    @Test
    void testBoundCharacters() {
        var boundCharacters = keyBinding.boundCharacters();

        assertNotNull(boundCharacters);
        assertNotSame(keyBinding.boundCharacters(), boundCharacters);
        assertEquals(CHARACTERS.length, boundCharacters.size());
        for (var c : CHARACTERS) {
            assertTrue(boundCharacters.contains(c));
        }
    }

    @Test
    void testSetOnPressAndOnPressActionIdAndPress() {
        assertNull(keyBinding.onPressActionId());

        keyBinding.setOnPress(KEY_PRESS_ACTION);

        keyBinding.press(TIMESTAMP);

        assertEquals(KEY_PRESS_ACTION_ID, keyBinding.onPressActionId());
        assertEquals(TIMESTAMP, KEY_PRESS_ACTION._mostRecentInput);
    }

    @Test
    void testSetOnReleaseAndOnReleaseActionIdAndRelease() {
        assertNull(keyBinding.onReleaseActionId());

        keyBinding.setOnRelease(KEY_RELEASE_ACTION);

        keyBinding.release(TIMESTAMP);

        assertEquals(KEY_RELEASE_ACTION_ID, keyBinding.onReleaseActionId());
        assertEquals(TIMESTAMP, KEY_RELEASE_ACTION._mostRecentInput);
    }

    @Test
    void testSetAndGetBlocksLowerBindings() {
        keyBinding.setBlocksLowerBindings(true);

        assertTrue(keyBinding.getBlocksLowerBindings());
    }

    @Test
    void testPressAndReleaseWithInvalidParams() {
        keyBinding.press(TIMESTAMP);
        keyBinding.release(TIMESTAMP);

        assertThrows(IllegalArgumentException.class, () -> keyBinding.press(TIMESTAMP - 1));
        assertThrows(IllegalArgumentException.class, () -> keyBinding.release(TIMESTAMP - 1));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(KeyBinding.class.getCanonicalName(), keyBinding.getInterfaceName());
    }
}

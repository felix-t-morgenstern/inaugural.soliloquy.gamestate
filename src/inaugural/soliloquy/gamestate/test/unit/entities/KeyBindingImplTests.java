package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.KeyBindingImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.entities.KeyBinding;

import static org.junit.jupiter.api.Assertions.*;

class KeyBindingImplTests {
    private final String KEY_PRESS_ACTION_ID = "keyPressActionId";
    private final String KEY_RELEASE_ACTION_ID = "keyReleaseActionId";
    private final FakeAction<Long> KEY_PRESS_ACTION = new FakeAction<>(KEY_PRESS_ACTION_ID);
    private final FakeAction<Long> KEY_RELEASE_ACTION = new FakeAction<>(KEY_RELEASE_ACTION_ID);
    private final long TIMESTAMP = 123123L;

    private KeyBinding _keyBinding;

    @BeforeEach
    void setUp() {
        _keyBinding = new KeyBindingImpl();
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(KeyBinding.class.getCanonicalName(), _keyBinding.getInterfaceName());
    }

    @Test
    void testBoundCharacters() {
        assertNotNull(_keyBinding.boundCharacters());
    }

    @Test
    void testSetOnPressAndOnPressActionIdAndPress() {
        assertNull(_keyBinding.onPressActionId());

        _keyBinding.setOnPress(KEY_PRESS_ACTION);

        _keyBinding.press(TIMESTAMP);

        assertEquals(KEY_PRESS_ACTION_ID, _keyBinding.onPressActionId());
        assertEquals(TIMESTAMP, KEY_PRESS_ACTION._mostRecentInput);
    }

    @Test
    void testSetOnReleaseAndOnReleaseActionIdAndRelease() {
        assertNull(_keyBinding.onReleaseActionId());

        _keyBinding.setOnRelease(KEY_RELEASE_ACTION);

        _keyBinding.release(TIMESTAMP);

        assertEquals(KEY_RELEASE_ACTION_ID, _keyBinding.onReleaseActionId());
        assertEquals(TIMESTAMP, KEY_RELEASE_ACTION._mostRecentInput);
    }

    @Test
    void testSetAndGetBlocksLowerBindings() {
        _keyBinding.setBlocksLowerBindings(true);

        assertTrue(_keyBinding.getBlocksLowerBindings());
    }

    @Test
    void testPressAndReleaseWithInvalidParams() {
        _keyBinding.press(TIMESTAMP);
        _keyBinding.release(TIMESTAMP);

        assertThrows(IllegalArgumentException.class, () -> _keyBinding.press(TIMESTAMP - 1));
        assertThrows(IllegalArgumentException.class, () -> _keyBinding.release(TIMESTAMP - 1));
    }
}

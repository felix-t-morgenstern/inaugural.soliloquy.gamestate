package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.KeyBindingImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeAction;
import inaugural.soliloquy.gamestate.test.fakes.FakeListFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.gamestate.entities.KeyBinding;

import static org.junit.jupiter.api.Assertions.*;

class KeyBindingImplTests {
    private final ListFactory LIST_FACTORY = new FakeListFactory();
    private final Action<Long> KEY_PRESS_ACTION = new FakeAction<>();
    private final Action<Long> KEY_RELEASE_ACTION = new FakeAction<>();

    private KeyBinding _keyBinding;

    @BeforeEach
    void setUp() {
        _keyBinding = new KeyBindingImpl(LIST_FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new KeyBindingImpl(null));
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
    void testSetOnPressAndOnPressActionId() {
        _keyBinding.setOnPress(KEY_PRESS_ACTION);

        //assertSame(KEY_PRESS_ACTION, _keyBinding.getOnPress());
        fail("Complete this test");
    }

    @Test
    void testSetOnReleaseAndOnReleaseActionId() {
        _keyBinding.setOnRelease(KEY_RELEASE_ACTION);

        //assertSame(KEY_RELEASE_ACTION, _keyBinding.getOnRelease());
        fail("Complete this test");
    }

    @Test
    void testPress() {
        fail("Complete this test");
    }

    @Test
    void testRelease() {
        fail("Complete this test");
    }

    @Test
    void testSetAndGetBlocksLowerBindings() {
        _keyBinding.setBlocksLowerBindings(true);

        assertTrue(_keyBinding.getBlocksLowerBindings());
    }
}

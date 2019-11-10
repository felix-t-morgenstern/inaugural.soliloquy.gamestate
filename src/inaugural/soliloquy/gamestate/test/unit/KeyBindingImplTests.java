package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.KeyBindingImpl;
import inaugural.soliloquy.gamestate.test.stubs.ActionStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.KeyBinding;

import static org.junit.jupiter.api.Assertions.*;

class KeyBindingImplTests {
    private final Action<Void> KEY_PRESS_ACTION = new ActionStub<>();
    private final Action<Void> KEY_RELEASE_ACTION = new ActionStub<>();

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
    void testSetAndGetOnPress() {
        _keyBinding.setOnPress(KEY_PRESS_ACTION);

        assertSame(KEY_PRESS_ACTION, _keyBinding.getOnPress());
    }

    @Test
    void testSetAndGetOnRelease() {
        _keyBinding.setOnRelease(KEY_RELEASE_ACTION);

        assertSame(KEY_RELEASE_ACTION, _keyBinding.getOnRelease());
    }

    @Test
    void testSetAndGetBlocksLowerBindings() {
        _keyBinding.setBlocksLowerBindings(true);

        assertTrue(_keyBinding.getBlocksLowerBindings());
    }
}

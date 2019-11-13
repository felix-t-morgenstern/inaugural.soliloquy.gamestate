package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.KeyBindingImpl;
import inaugural.soliloquy.gamestate.test.stubs.ActionStub;
import inaugural.soliloquy.gamestate.test.stubs.CollectionFactoryStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.gamestate.entities.KeyBinding;

import static org.junit.jupiter.api.Assertions.*;

class KeyBindingImplTests {
    private final CollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    private final Action<Void> KEY_PRESS_ACTION = new ActionStub<>();
    private final Action<Void> KEY_RELEASE_ACTION = new ActionStub<>();
    private final Action<Void> KEY_TYPE_ACTION = new ActionStub<>();

    private KeyBinding _keyBinding;

    @BeforeEach
    void setUp() {
        _keyBinding = new KeyBindingImpl(COLLECTION_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
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
    void testSetAndGetOnType() {
        _keyBinding.setOnType(KEY_TYPE_ACTION);

        assertSame(KEY_TYPE_ACTION, _keyBinding.getOnType());
    }

    @Test
    void testSetAndGetBlocksLowerBindings() {
        _keyBinding.setBlocksLowerBindings(true);

        assertTrue(_keyBinding.getBlocksLowerBindings());
    }
}

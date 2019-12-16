package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterInventoryFactoryImpl;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStub;
import inaugural.soliloquy.gamestate.test.stubs.CollectionFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.ItemStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.factories.CharacterInventoryFactory;

import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class CharacterInventoryFactoryImplTests {
    private final Character CHARACTER = new CharacterStub();
    private final CollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();

    private CharacterInventoryFactory _characterInventoryFactory;

    @BeforeEach
    void setUp() {
        _characterInventoryFactory = new CharacterInventoryFactoryImpl(COLLECTION_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithNullInput() {
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterInventoryFactoryImpl(null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(CharacterInventoryFactory.class.getCanonicalName(),
                _characterInventoryFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        assertNotNull(_characterInventoryFactory.make(CHARACTER));
    }

    @Test
    void testMakeWithNullInput() {
        assertThrows(IllegalArgumentException.class, () -> _characterInventoryFactory.make(null));
    }
}

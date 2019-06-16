package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterInventoryFactory;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStub;
import inaugural.soliloquy.gamestate.test.stubs.CollectionFactoryStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.ICollectionFactory;
import soliloquy.specs.gamestate.entities.ICharacter;
import soliloquy.specs.gamestate.factories.ICharacterInventoryFactory;

import static org.junit.jupiter.api.Assertions.*;

class CharacterInventoryFactoryTests {
    private final ICharacter CHARACTER = new CharacterStub();
    private final ICollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();

    private ICharacterInventoryFactory _characterInventoryFactory;

    @BeforeEach
    void setUp() {
        _characterInventoryFactory = new CharacterInventoryFactory(COLLECTION_FACTORY);
    }

    @Test
    void testConstructorWithNullInput() {
        assertThrows(IllegalArgumentException.class, () -> new CharacterInventoryFactory(null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(ICharacterInventoryFactory.class.getCanonicalName(),
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

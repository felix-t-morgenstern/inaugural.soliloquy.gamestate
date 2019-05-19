package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterInventoryFactory;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.gamestate.specs.ICharacter;
import soliloquy.gamestate.specs.ICharacterInventoryFactory;

import static org.junit.jupiter.api.Assertions.*;

class CharacterInventoryFactoryTests {
    private final ICharacter CHARACTER = new CharacterStub();

    private ICharacterInventoryFactory _characterInventoryFactory;

    @BeforeEach
    void setUp() {
        _characterInventoryFactory = new CharacterInventoryFactory();
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

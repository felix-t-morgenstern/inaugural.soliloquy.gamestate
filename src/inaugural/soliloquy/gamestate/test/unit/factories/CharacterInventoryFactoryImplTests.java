package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.CharacterInventoryFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeCharacter;
import inaugural.soliloquy.gamestate.test.fakes.FakeListFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.factories.CharacterInventoryFactory;

import static org.junit.jupiter.api.Assertions.*;

class CharacterInventoryFactoryImplTests {
    private final Character CHARACTER = new FakeCharacter();
    private final ListFactory LIST_FACTORY = new FakeListFactory();

    private CharacterInventoryFactory _characterInventoryFactory;

    @BeforeEach
    void setUp() {
        _characterInventoryFactory = new CharacterInventoryFactoryImpl(LIST_FACTORY);
    }

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

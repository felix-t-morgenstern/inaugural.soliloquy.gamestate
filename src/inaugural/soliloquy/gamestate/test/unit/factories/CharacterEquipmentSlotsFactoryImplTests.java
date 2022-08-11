package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.CharacterEquipmentSlotsFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeCharacter;
import inaugural.soliloquy.gamestate.test.fakes.FakeMapFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.factories.CharacterEquipmentSlotsFactory;

import static org.junit.jupiter.api.Assertions.*;

class CharacterEquipmentSlotsFactoryImplTests {
    private final Character CHARACTER = new FakeCharacter();
    private final MapFactory MAP_FACTORY = new FakeMapFactory();

    private CharacterEquipmentSlotsFactory _characterEquipmentSlotsFactory;

    @BeforeEach
    void setUp() {
        _characterEquipmentSlotsFactory = new CharacterEquipmentSlotsFactoryImpl(MAP_FACTORY);
    }

    @Test
    void testConstructorWithNullInput() {
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterEquipmentSlotsFactoryImpl(null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(CharacterEquipmentSlotsFactory.class.getCanonicalName(),
                _characterEquipmentSlotsFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        assertNotNull(_characterEquipmentSlotsFactory.make(CHARACTER));
    }

    @Test
    void testMakeWithNull() {
        assertThrows(IllegalArgumentException.class,
                () -> _characterEquipmentSlotsFactory.make(null));
    }
}

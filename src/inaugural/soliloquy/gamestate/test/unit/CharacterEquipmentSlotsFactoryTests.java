package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterEquipmentSlotsFactory;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.gamestate.specs.ICharacter;
import soliloquy.gamestate.specs.ICharacterEquipmentSlotsFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CharacterEquipmentSlotsFactoryTests {
    private final ICharacter CHARACTER = new CharacterStub();

    private ICharacterEquipmentSlotsFactory _characterEquipmentSlotsFactory;

    @BeforeEach
    void setUp() {
        _characterEquipmentSlotsFactory = new CharacterEquipmentSlotsFactory();
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(ICharacterEquipmentSlotsFactory.class.getCanonicalName(),
                _characterEquipmentSlotsFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        assertNotNull(_characterEquipmentSlotsFactory.make(CHARACTER));
    }
}

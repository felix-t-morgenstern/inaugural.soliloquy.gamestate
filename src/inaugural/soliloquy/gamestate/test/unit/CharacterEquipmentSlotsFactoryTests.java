package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterEquipmentSlotsFactory;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStub;
import inaugural.soliloquy.gamestate.test.stubs.MapFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.PairFactoryStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.common.specs.IMapFactory;
import soliloquy.common.specs.IPairFactory;
import soliloquy.gamestate.specs.ICharacter;
import soliloquy.gamestate.specs.ICharacterEquipmentSlotsFactory;

import static org.junit.jupiter.api.Assertions.*;

class CharacterEquipmentSlotsFactoryTests {
    private final ICharacter CHARACTER = new CharacterStub();
    private final IPairFactory PAIR_FACTORY = new PairFactoryStub();
    private final IMapFactory MAP_FACTORY = new MapFactoryStub();

    private ICharacterEquipmentSlotsFactory _characterEquipmentSlotsFactory;

    @BeforeEach
    void setUp() {
        _characterEquipmentSlotsFactory = new CharacterEquipmentSlotsFactory(PAIR_FACTORY,
                MAP_FACTORY);
    }

    @Test
    void testConstructorWithNullInput() {
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterEquipmentSlotsFactory(null, MAP_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterEquipmentSlotsFactory(PAIR_FACTORY, null));
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

    @Test
    void testMakeWithNull() {
        assertThrows(IllegalArgumentException.class,
                () -> _characterEquipmentSlotsFactory.make(null));
    }
}

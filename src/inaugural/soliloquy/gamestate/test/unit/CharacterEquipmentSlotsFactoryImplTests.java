package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterEquipmentSlotsFactoryImpl;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStub;
import inaugural.soliloquy.gamestate.test.stubs.MapFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.PairFactoryStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.factories.CharacterEquipmentSlotsFactory;

import static org.junit.jupiter.api.Assertions.*;

class CharacterEquipmentSlotsFactoryImplTests {
    private final Character CHARACTER = new CharacterStub();
    private final PairFactory PAIR_FACTORY = new PairFactoryStub();
    private final MapFactory MAP_FACTORY = new MapFactoryStub();

    private CharacterEquipmentSlotsFactory _characterEquipmentSlotsFactory;

    @BeforeEach
    void setUp() {
        _characterEquipmentSlotsFactory = new CharacterEquipmentSlotsFactoryImpl(PAIR_FACTORY,
                MAP_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithNullInput() {
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterEquipmentSlotsFactoryImpl(null, MAP_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterEquipmentSlotsFactoryImpl(PAIR_FACTORY, null));
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
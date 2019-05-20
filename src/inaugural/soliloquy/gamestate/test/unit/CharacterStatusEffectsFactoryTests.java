package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterStatusEffectsFactory;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStub;
import inaugural.soliloquy.gamestate.test.stubs.MapFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.MapStub;
import inaugural.soliloquy.gamestate.test.stubs.ResistanceCalculationStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.common.specs.IMap;
import soliloquy.common.specs.IMapFactory;
import soliloquy.gamestate.specs.ICharacter;
import soliloquy.gamestate.specs.ICharacterStatusEffectsFactory;
import soliloquy.ruleset.gameconcepts.specs.IResistanceCalculation;
import soliloquy.ruleset.gameentities.specs.IStatusEffectType;

import static org.junit.jupiter.api.Assertions.*;

class CharacterStatusEffectsFactoryTests {
    private final ICharacter CHARACTER = new CharacterStub();
    private final IMap<String, IStatusEffectType> STATUS_EFFECT_TYPES = new MapStub<>();
    private final IMapFactory MAP_FACTORY = new MapFactoryStub();
    private final IResistanceCalculation RESISTANCE_CALCULATION = new ResistanceCalculationStub();

    private ICharacterStatusEffectsFactory _characterStatusEffectsFactory;

    @BeforeEach
    void setUp() {
        _characterStatusEffectsFactory = new CharacterStatusEffectsFactory(
                STATUS_EFFECT_TYPES,
                MAP_FACTORY,
                RESISTANCE_CALCULATION);
    }

    @Test
    void testConstructorWithNullParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterStatusEffectsFactory(null, MAP_FACTORY, RESISTANCE_CALCULATION));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterStatusEffectsFactory(STATUS_EFFECT_TYPES, null, RESISTANCE_CALCULATION));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterStatusEffectsFactory(STATUS_EFFECT_TYPES, MAP_FACTORY, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(ICharacterStatusEffectsFactory.class.getCanonicalName(),
                _characterStatusEffectsFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        assertNotNull(_characterStatusEffectsFactory.make(CHARACTER));
    }

    @Test
    void testMakeWithNullInput() {
        assertThrows(IllegalArgumentException.class,
                () -> _characterStatusEffectsFactory.make(null));
    }
}

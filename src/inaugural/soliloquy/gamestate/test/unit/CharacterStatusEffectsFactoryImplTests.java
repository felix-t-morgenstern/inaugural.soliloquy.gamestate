package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterStatusEffectsFactoryImpl;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStub;
import inaugural.soliloquy.gamestate.test.stubs.MapFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.MapStub;
import inaugural.soliloquy.gamestate.test.stubs.StatusEffectResistanceCalculationStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.factories.CharacterStatusEffectsFactory;
import soliloquy.specs.ruleset.entities.StatusEffectType;
import soliloquy.specs.ruleset.gameconcepts.StatusEffectResistanceCalculation;

import static org.junit.jupiter.api.Assertions.*;

class CharacterStatusEffectsFactoryImplTests {
    private final Character CHARACTER = new CharacterStub();
    private final Map<String, StatusEffectType> STATUS_EFFECT_TYPES = new MapStub<>();
    private final MapFactory MAP_FACTORY = new MapFactoryStub();
    private final StatusEffectResistanceCalculation RESISTANCE_CALCULATION =
            new StatusEffectResistanceCalculationStub();

    private CharacterStatusEffectsFactory _characterStatusEffectsFactory;

    @BeforeEach
    void setUp() {
        _characterStatusEffectsFactory = new CharacterStatusEffectsFactoryImpl(
                STATUS_EFFECT_TYPES,
                MAP_FACTORY,
                RESISTANCE_CALCULATION);
    }

    @Test
    void testConstructorWithNullParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterStatusEffectsFactoryImpl(null, MAP_FACTORY, RESISTANCE_CALCULATION));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterStatusEffectsFactoryImpl(STATUS_EFFECT_TYPES, null, RESISTANCE_CALCULATION));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterStatusEffectsFactoryImpl(STATUS_EFFECT_TYPES, MAP_FACTORY, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(CharacterStatusEffectsFactory.class.getCanonicalName(),
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

package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.CharacterStatusEffectsFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeCharacter;
import inaugural.soliloquy.gamestate.test.fakes.FakeMapFactory;
import inaugural.soliloquy.gamestate.test.spydoubles.StatusEffectResistanceCalculationSpyDouble;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.factories.CharacterStatusEffectsFactory;
import soliloquy.specs.ruleset.gameconcepts.StatusEffectResistanceCalculation;

import static org.junit.jupiter.api.Assertions.*;

class CharacterStatusEffectsFactoryImplTests {
    private final Character CHARACTER = new FakeCharacter();
    private final MapFactory MAP_FACTORY = new FakeMapFactory();
    private final StatusEffectResistanceCalculation RESISTANCE_CALCULATION =
            new StatusEffectResistanceCalculationSpyDouble();

    private CharacterStatusEffectsFactory _characterStatusEffectsFactory;

    @BeforeEach
    void setUp() {
        _characterStatusEffectsFactory = new CharacterStatusEffectsFactoryImpl(
                MAP_FACTORY,
                RESISTANCE_CALCULATION);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithNullParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterStatusEffectsFactoryImpl(null, RESISTANCE_CALCULATION));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterStatusEffectsFactoryImpl(MAP_FACTORY, null));
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

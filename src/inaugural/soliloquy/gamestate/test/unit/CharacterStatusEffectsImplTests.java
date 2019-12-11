package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterStatusEffectsImpl;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterStatusEffects;
import soliloquy.specs.ruleset.entities.Element;
import soliloquy.specs.ruleset.entities.StatusEffectType;
import soliloquy.specs.ruleset.entities.abilities.AbilitySource;
import soliloquy.specs.ruleset.gameconcepts.StatusEffectResistanceCalculation;

import static org.junit.jupiter.api.Assertions.*;

class CharacterStatusEffectsImplTests {
    private CharacterStatusEffects _characterStatusEffects;

    private final StatusEffectType STATUS_EFFECT_TYPE_1 = new StatusEffectTypeStub();
    private final StatusEffectType STATUS_EFFECT_TYPE_2 = new StatusEffectTypeStub();

    private Character _character;
    private StatusEffectResistanceCalculation _statusEffectResistanceCalculation;
    private Element _element;
    private AbilitySource _abilitySource;

    @BeforeEach
    void setUp() {
        _character = new CharacterStub();
        _statusEffectResistanceCalculation = new StatusEffectResistanceCalculationStub();
        _element = new ElementStub();
        _abilitySource = new AbilitySourceStub();

        _characterStatusEffects = new CharacterStatusEffectsImpl(_character,
                new MapFactoryStub(), _statusEffectResistanceCalculation);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(CharacterStatusEffects.class.getCanonicalName(),
                _characterStatusEffects.getInterfaceName());
    }

    @Test
    void testGetAndSetStatusEffectLevel() {
        assertEquals(0, (int) _characterStatusEffects.getStatusEffectLevel(STATUS_EFFECT_TYPE_1));
        _characterStatusEffects.setStatusEffectLevel(STATUS_EFFECT_TYPE_1, 10);
        assertEquals(10, (int) _characterStatusEffects.getStatusEffectLevel(STATUS_EFFECT_TYPE_1));
    }

    @Test
    void testGetAllStatusEffects() {
        _characterStatusEffects.setStatusEffectLevel(STATUS_EFFECT_TYPE_1, 123);
        _characterStatusEffects.setStatusEffectLevel(STATUS_EFFECT_TYPE_2, 456);

        ReadableMap<StatusEffectType,Integer> statusEffectLevels =
                _characterStatusEffects.representation();

        assertEquals(2, statusEffectLevels.size());
        assertEquals(123, (int) statusEffectLevels.get(STATUS_EFFECT_TYPE_1));
        assertEquals(456, (int) statusEffectLevels.get(STATUS_EFFECT_TYPE_2));
    }

    @Test
    void testClearStatusEffects() {
        _characterStatusEffects.setStatusEffectLevel(STATUS_EFFECT_TYPE_1, 123);
        _characterStatusEffects.setStatusEffectLevel(STATUS_EFFECT_TYPE_2, 456);
        ReadableMap<StatusEffectType,Integer> statusEffectLevels =
                _characterStatusEffects.representation();
        assertEquals(2, statusEffectLevels.size());

        _characterStatusEffects.clearStatusEffects();
        statusEffectLevels = _characterStatusEffects.representation();

        assertEquals(0, statusEffectLevels.size());
    }

    @Test
    void testGetAndSetInvalidStatusEffectType() {
        assertThrows(IllegalArgumentException.class,
                () -> _characterStatusEffects.getStatusEffectLevel(null));
    }

    @Test
    void testClearGetAndSetNullOrDeletedCharacter() {
        _character.delete();
        assertThrows(IllegalStateException.class, () -> _characterStatusEffects.getStatusEffectLevel(STATUS_EFFECT_TYPE_1));
        assertThrows(IllegalStateException.class, () -> _characterStatusEffects.setStatusEffectLevel(STATUS_EFFECT_TYPE_1, 0));
        assertThrows(IllegalStateException.class, _characterStatusEffects::clearStatusEffects);

        CharacterStatusEffects characterStatusEffects =
                new CharacterStatusEffectsImpl(null, new MapFactoryStub(),
                        _statusEffectResistanceCalculation);
        assertThrows(IllegalStateException.class, () -> characterStatusEffects.getStatusEffectLevel(STATUS_EFFECT_TYPE_1));
        assertThrows(IllegalStateException.class, () -> characterStatusEffects.setStatusEffectLevel(STATUS_EFFECT_TYPE_1, 0));
        assertThrows(IllegalStateException.class, characterStatusEffects::clearStatusEffects);
    }

    @Test
    void testAlterStatusEffect() {
        _characterStatusEffects.alterStatusEffect(STATUS_EFFECT_TYPE_1, 111, true, _element,
                _abilitySource);

        assertEquals((int) _characterStatusEffects.getStatusEffectLevel(STATUS_EFFECT_TYPE_1),
                StatusEffectResistanceCalculationStub.STATUS_EFFECT_TYPE_RESULT);
        assertSame(((StatusEffectResistanceCalculationStub) _statusEffectResistanceCalculation)._statusEffectType,
                STATUS_EFFECT_TYPE_1);
        assertSame(((StatusEffectResistanceCalculationStub) _statusEffectResistanceCalculation)._character, _character);
        assertEquals(111, ((StatusEffectResistanceCalculationStub) _statusEffectResistanceCalculation)._baseAmount);
        assertTrue(((StatusEffectResistanceCalculationStub) _statusEffectResistanceCalculation)._stopAtZero);
        assertSame(((StatusEffectResistanceCalculationStub) _statusEffectResistanceCalculation)._element, _element);
        assertSame(((StatusEffectResistanceCalculationStub) _statusEffectResistanceCalculation)._abilitySource,
                _abilitySource);
    }

    @Test
    void testAlterStatusEffectInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _characterStatusEffects.alterStatusEffect(null, 111, true, _element,
                        _abilitySource));
        assertThrows(IllegalArgumentException.class,
                () -> _characterStatusEffects.alterStatusEffect(STATUS_EFFECT_TYPE_1, 111, true,
                        null, _abilitySource));

        _character.delete();
        assertThrows(IllegalStateException.class,
                () -> _characterStatusEffects.alterStatusEffect(STATUS_EFFECT_TYPE_1, 111, true,
                        _element, _abilitySource));
    }

    @Test
    void testEnforceDeletionInvariant() {
        _characterStatusEffects.delete();

        assertThrows(IllegalStateException.class,
                () -> _characterStatusEffects.getStatusEffectLevel(STATUS_EFFECT_TYPE_1));
        assertThrows(IllegalStateException.class,
                () -> _characterStatusEffects.representation());
        assertThrows(IllegalStateException.class,
                () -> _characterStatusEffects.alterStatusEffect(STATUS_EFFECT_TYPE_1, 0, false,
                        _element, _abilitySource));
        assertThrows(IllegalStateException.class,
                () -> _characterStatusEffects.setStatusEffectLevel(STATUS_EFFECT_TYPE_1, 0));
        assertThrows(IllegalStateException.class,
                () -> _characterStatusEffects.clearStatusEffects());
        assertThrows(IllegalStateException.class,
                () -> _characterStatusEffects.getInterfaceName());
    }
}

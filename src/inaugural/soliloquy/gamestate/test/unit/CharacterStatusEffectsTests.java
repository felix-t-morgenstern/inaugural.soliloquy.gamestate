package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterStatusEffects;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.common.specs.IMap;
import soliloquy.gamestate.specs.ICharacter;
import soliloquy.gamestate.specs.ICharacterStatusEffects;
import soliloquy.ruleset.gameconcepts.specs.IResistanceCalculation;
import soliloquy.ruleset.gameentities.abilities.specs.IAbilitySource;
import soliloquy.ruleset.gameentities.specs.IElement;
import soliloquy.ruleset.gameentities.specs.IStatusEffectType;

import static org.junit.jupiter.api.Assertions.*;

class CharacterStatusEffectsTests {
    private ICharacterStatusEffects _characterStatusEffects;

    private final String STATUS_EFFECT_TYPE_1_ID = "StatusEffectType1Id";
    private final String STATUS_EFFECT_TYPE_2_ID = "StatusEffectType2Id";

    private IMap<String, IStatusEffectType> _statusEffectTypes;
    private ICharacter _character;
    private IResistanceCalculation _resistanceCalculation;
    private IElement _element;
    private IAbilitySource _abilitySource;

    @BeforeEach
    void setUp() {
        _statusEffectTypes = new MapStub<>();
        IStatusEffectType _statusEffectType1 = new StatusEffectTypeStub();
        IStatusEffectType _statusEffectType2 = new StatusEffectTypeStub();
        _statusEffectTypes.put(STATUS_EFFECT_TYPE_1_ID, _statusEffectType1);
        _statusEffectTypes.put(STATUS_EFFECT_TYPE_2_ID, _statusEffectType2);

        _character = new CharacterStub();
        _resistanceCalculation = new ResistanceCalculationStub();
        _element = new ElementStub();
        _abilitySource = new AbilitySourceStub();

        _characterStatusEffects = new CharacterStatusEffects(_character, _statusEffectTypes,
                new MapFactoryStub(), _resistanceCalculation);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(ICharacterStatusEffects.class.getCanonicalName(),
                _characterStatusEffects.getInterfaceName());
    }

    @Test
    void testGetAndSetStatusEffectLevel() {
        assertEquals(0, (int) _characterStatusEffects.getStatusEffectLevel(STATUS_EFFECT_TYPE_1_ID));
        _characterStatusEffects.setStatusEffectLevel(STATUS_EFFECT_TYPE_1_ID, 10);
        assertEquals(10, (int) _characterStatusEffects.getStatusEffectLevel(STATUS_EFFECT_TYPE_1_ID));
    }

    @Test
    void testGetAllStatusEffects() {
        _characterStatusEffects.setStatusEffectLevel(STATUS_EFFECT_TYPE_1_ID, 123);
        _characterStatusEffects.setStatusEffectLevel(STATUS_EFFECT_TYPE_2_ID, 456);

        IMap<String,Integer> statusEffectLevels = _characterStatusEffects.getAllStatusEffects();

        assertEquals(2, statusEffectLevels.size());
        assertEquals(123, (int) statusEffectLevels.get(STATUS_EFFECT_TYPE_1_ID));
        assertEquals(456, (int) statusEffectLevels.get(STATUS_EFFECT_TYPE_2_ID));
    }

    @Test
    void testClearStatusEffects() {
        _characterStatusEffects.setStatusEffectLevel(STATUS_EFFECT_TYPE_1_ID, 123);
        _characterStatusEffects.setStatusEffectLevel(STATUS_EFFECT_TYPE_2_ID, 456);
        IMap<String,Integer> statusEffectLevels = _characterStatusEffects.getAllStatusEffects();
        assertEquals(2, statusEffectLevels.size());

        _characterStatusEffects.clearStatusEffects();
        statusEffectLevels = _characterStatusEffects.getAllStatusEffects();

        assertEquals(0, statusEffectLevels.size());
    }

    @Test
    void testGetAndSetInvalidStatusEffectTypeId() {
        assertThrows(IllegalArgumentException.class, () -> _characterStatusEffects.getStatusEffectLevel("Invalid Id"));
        assertThrows(IllegalArgumentException.class, () -> _characterStatusEffects.setStatusEffectLevel("Invalid Id", 0));
    }

    @Test
    void testClearGetAndSetNullDeadOrDeletedCharacter() {
        _character.setDead(true);
        assertThrows(IllegalStateException.class, () -> _characterStatusEffects.getStatusEffectLevel(STATUS_EFFECT_TYPE_1_ID));
        assertThrows(IllegalStateException.class, () -> _characterStatusEffects.setStatusEffectLevel(STATUS_EFFECT_TYPE_1_ID, 0));
        assertThrows(IllegalStateException.class, _characterStatusEffects::clearStatusEffects);

        _character = new CharacterStub();
        _character.delete();
        assertThrows(IllegalStateException.class, () -> _characterStatusEffects.getStatusEffectLevel(STATUS_EFFECT_TYPE_1_ID));
        assertThrows(IllegalStateException.class, () -> _characterStatusEffects.setStatusEffectLevel(STATUS_EFFECT_TYPE_1_ID, 0));
        assertThrows(IllegalStateException.class, _characterStatusEffects::clearStatusEffects);

        ICharacterStatusEffects characterStatusEffects =
                new CharacterStatusEffects(null, _statusEffectTypes, new MapFactoryStub(),
                        _resistanceCalculation);
        assertThrows(IllegalStateException.class, () -> characterStatusEffects.getStatusEffectLevel(STATUS_EFFECT_TYPE_1_ID));
        assertThrows(IllegalStateException.class, () -> characterStatusEffects.setStatusEffectLevel(STATUS_EFFECT_TYPE_1_ID, 0));
        assertThrows(IllegalStateException.class, characterStatusEffects::clearStatusEffects);
    }

    @Test
    void testAlterStatusEffect() {
        _characterStatusEffects.alterStatusEffect(STATUS_EFFECT_TYPE_1_ID, 111, true, _element, _abilitySource);

        assertTrue(_characterStatusEffects.getStatusEffectLevel(STATUS_EFFECT_TYPE_1_ID)
                .equals(ResistanceCalculationStub.STATUS_EFFECT_TYPE_RESULT));
        assertTrue(((ResistanceCalculationStub) _resistanceCalculation)._statusEffectType ==
                _statusEffectTypes.get(STATUS_EFFECT_TYPE_1_ID));
        assertTrue(((ResistanceCalculationStub) _resistanceCalculation)._character == _character);
        assertTrue(((ResistanceCalculationStub) _resistanceCalculation)._baseAmount == 111);
        assertTrue(((ResistanceCalculationStub) _resistanceCalculation)._stopAtZero == true);
        assertTrue(((ResistanceCalculationStub) _resistanceCalculation)._element == _element);
        assertTrue(((ResistanceCalculationStub) _resistanceCalculation)._abilitySource == _abilitySource);
    }

    @Test
    void testAlterStatusEffectInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _characterStatusEffects.alterStatusEffect(null, 111, true, _element,
                        _abilitySource));
        assertThrows(IllegalArgumentException.class,
                () -> _characterStatusEffects.alterStatusEffect(STATUS_EFFECT_TYPE_1_ID, 111, true,
                        null, _abilitySource));

        _character.setDead(true);
        assertThrows(IllegalStateException.class,
                () -> _characterStatusEffects.alterStatusEffect(STATUS_EFFECT_TYPE_1_ID, 111, true,
                        _element, _abilitySource));

        _character = new CharacterStub();
        _character.delete();
        assertThrows(IllegalStateException.class,
                () -> _characterStatusEffects.alterStatusEffect(STATUS_EFFECT_TYPE_1_ID, 111, true,
                        _element, _abilitySource));
    }
}

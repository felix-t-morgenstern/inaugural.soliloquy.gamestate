package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterStatusEffectsImpl;
import inaugural.soliloquy.gamestate.test.fakes.*;
import inaugural.soliloquy.gamestate.test.stubs.AbilitySourceStub;
import inaugural.soliloquy.gamestate.test.stubs.ElementStub;
import inaugural.soliloquy.gamestate.test.spydoubles.StatusEffectResistanceCalculationSpyDouble;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterStatusEffects;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.ruleset.entities.Element;
import soliloquy.specs.ruleset.entities.StatusEffectType;
import soliloquy.specs.ruleset.entities.abilities.AbilitySource;

import static org.junit.jupiter.api.Assertions.*;

class CharacterStatusEffectsImplTests {
    private CharacterStatusEffects _characterStatusEffects;

    private final StatusEffectType STATUS_EFFECT_TYPE_1 = new FakeStatusEffectType();
    private final StatusEffectType STATUS_EFFECT_TYPE_2 = new FakeStatusEffectType();
    private final StatusEffectResistanceCalculationSpyDouble STATUS_EFFECT_RESISTANCE_CALCULATION =
            new StatusEffectResistanceCalculationSpyDouble();
    private final Character CHARACTER = new FakeCharacter();
    private final Element ELEMENT = new ElementStub();
    private final AbilitySource ABILITY_SOURCE = new AbilitySourceStub();

    @BeforeEach
    void setUp() {
        _characterStatusEffects = new CharacterStatusEffectsImpl(CHARACTER,
                new FakeMapFactory(), STATUS_EFFECT_RESISTANCE_CALCULATION);
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
        assertThrows(IllegalArgumentException.class,
                () -> _characterStatusEffects.setStatusEffectLevel(null, 0));
    }

    @Test
    void testClearGetAndSetNullOrDeletedCharacter() {
        CHARACTER.delete();
        assertThrows(IllegalStateException.class,
                () -> _characterStatusEffects.getStatusEffectLevel(STATUS_EFFECT_TYPE_1));
        assertThrows(IllegalStateException.class,
                () -> _characterStatusEffects.setStatusEffectLevel(STATUS_EFFECT_TYPE_1, 0));
        assertThrows(IllegalStateException.class, _characterStatusEffects::clearStatusEffects);

        CharacterStatusEffects characterStatusEffects =
                new CharacterStatusEffectsImpl(null, new FakeMapFactory(),
                        STATUS_EFFECT_RESISTANCE_CALCULATION);
        assertThrows(IllegalStateException.class,
                () -> characterStatusEffects.getStatusEffectLevel(STATUS_EFFECT_TYPE_1));
        assertThrows(IllegalStateException.class,
                () -> characterStatusEffects.setStatusEffectLevel(STATUS_EFFECT_TYPE_1, 0));
        assertThrows(IllegalStateException.class, characterStatusEffects::clearStatusEffects);
    }

    @Test
    void testAlterStatusEffect() {
        final int amountAltered = 111;

        _characterStatusEffects.alterStatusEffect(STATUS_EFFECT_TYPE_1, amountAltered, true, ELEMENT,
                ABILITY_SOURCE);

        assertEquals((int) _characterStatusEffects.getStatusEffectLevel(STATUS_EFFECT_TYPE_1),
                STATUS_EFFECT_RESISTANCE_CALCULATION.StatusEffectTypeResult);
        assertSame(STATUS_EFFECT_RESISTANCE_CALCULATION._statusEffectType, STATUS_EFFECT_TYPE_1);
        assertSame(STATUS_EFFECT_RESISTANCE_CALCULATION._character, CHARACTER);
        assertEquals(amountAltered, STATUS_EFFECT_RESISTANCE_CALCULATION._baseAmount);
        assertTrue(STATUS_EFFECT_RESISTANCE_CALCULATION._stopAtZero);
        assertSame(STATUS_EFFECT_RESISTANCE_CALCULATION._element, ELEMENT);
        assertSame(STATUS_EFFECT_RESISTANCE_CALCULATION._abilitySource, ABILITY_SOURCE);
    }

    @Test
    void testStatusEffectIsRemovedWhenSetToZero() {
        _characterStatusEffects.setStatusEffectLevel(STATUS_EFFECT_TYPE_1, 1);

        assertEquals(1, _characterStatusEffects.representation().size());

        _characterStatusEffects.setStatusEffectLevel(STATUS_EFFECT_TYPE_1, 0);

        assertEquals(0, _characterStatusEffects.representation().size());
    }

    @Test
    void testStatusEffectIsRemovedWhenAlteredToZero() {
        _characterStatusEffects.alterStatusEffect(STATUS_EFFECT_TYPE_1, 0, true, ELEMENT,
                ABILITY_SOURCE);

        assertEquals(1, _characterStatusEffects.representation().size());

        STATUS_EFFECT_RESISTANCE_CALCULATION.StatusEffectTypeResult =
                -STATUS_EFFECT_RESISTANCE_CALCULATION.StatusEffectTypeResult;
        _characterStatusEffects.alterStatusEffect(STATUS_EFFECT_TYPE_1, 0, true, ELEMENT,
                ABILITY_SOURCE);

        assertEquals(0, _characterStatusEffects.representation().size());
    }

    @Test
    void testAlterStatusEffectInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _characterStatusEffects.alterStatusEffect(null, 111, true, ELEMENT,
                        ABILITY_SOURCE));
        assertThrows(IllegalArgumentException.class,
                () -> _characterStatusEffects.alterStatusEffect(STATUS_EFFECT_TYPE_1, 111, true,
                        null, ABILITY_SOURCE));

        CHARACTER.delete();
        assertThrows(IllegalStateException.class,
                () -> _characterStatusEffects.alterStatusEffect(STATUS_EFFECT_TYPE_1, 111, true,
                        ELEMENT, ABILITY_SOURCE));
    }

    @Test
    void testEnforceDeletionInvariant() {
        _characterStatusEffects.delete();

        assertThrows(EntityDeletedException.class,
                () -> _characterStatusEffects.getStatusEffectLevel(STATUS_EFFECT_TYPE_1));
        assertThrows(EntityDeletedException.class,
                () -> _characterStatusEffects.representation());
        assertThrows(EntityDeletedException.class,
                () -> _characterStatusEffects.alterStatusEffect(STATUS_EFFECT_TYPE_1, 0, false,
                        ELEMENT, ABILITY_SOURCE));
        assertThrows(EntityDeletedException.class,
                () -> _characterStatusEffects.setStatusEffectLevel(STATUS_EFFECT_TYPE_1, 0));
        assertThrows(EntityDeletedException.class,
                () -> _characterStatusEffects.clearStatusEffects());
        assertThrows(EntityDeletedException.class,
                () -> _characterStatusEffects.getInterfaceName());
    }
}

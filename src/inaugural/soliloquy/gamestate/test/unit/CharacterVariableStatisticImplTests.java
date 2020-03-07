package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterVariableStatisticImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeCharacter;
import inaugural.soliloquy.gamestate.test.spydoubles.CharacterStatisticCalculationSpyDouble;
import inaugural.soliloquy.gamestate.test.fakes.FakeCharacterVariableStatisticType;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistic;
import soliloquy.specs.ruleset.entities.CharacterVariableStatisticType;
import soliloquy.specs.ruleset.gameconcepts.CharacterStatisticCalculation;

import static org.junit.jupiter.api.Assertions.*;

class CharacterVariableStatisticImplTests {
    private CharacterVariableStatistic _characterVariableStatistic;

    private final Character CHARACTER = new FakeCharacter();
    private final CharacterVariableStatisticType CHARACTER_VARIABLE_STATISTIC_TYPE =
            new FakeCharacterVariableStatisticType("VariableStatisticType");
    private final VariableCache DATA = new VariableCacheStub();
    private final CharacterStatisticCalculation CHARACTER_STATISTIC_CALCULATION =
            new CharacterStatisticCalculationSpyDouble();

    @BeforeEach
    void setUp() {
        _characterVariableStatistic = new CharacterVariableStatisticImpl(CHARACTER,
                CHARACTER_VARIABLE_STATISTIC_TYPE, DATA, CHARACTER_STATISTIC_CALCULATION);
    }

    // TODO: Test constructor with invalid params

    @Test
    void testGetInterfaceName() {
        assertEquals(CharacterVariableStatistic.class.getCanonicalName(),
                _characterVariableStatistic.getInterfaceName());
    }

    @Test
    void testType() {
        assertSame(CHARACTER_VARIABLE_STATISTIC_TYPE, _characterVariableStatistic.type());
    }

    @Test
    void testData() {
        assertSame(DATA, _characterVariableStatistic.data());
    }

    @Test
    void testSetAndGetCurrentValue() {
        _characterVariableStatistic.setCurrentValue(123);

        assertEquals(123, _characterVariableStatistic.getCurrentValue());
    }

    @Test
    void testCalculate() {
        _characterVariableStatistic.calculate();

        assertSame(CHARACTER, CharacterStatisticCalculationSpyDouble._character);
        assertSame(CHARACTER_VARIABLE_STATISTIC_TYPE,
                CharacterStatisticCalculationSpyDouble._statisticType);
        assertEquals(CharacterStatisticCalculationSpyDouble.VALUE,
                _characterVariableStatistic.totalValue());
        ReadableMap<String,Integer> representation =
                _characterVariableStatistic.representation();
        assertEquals(CharacterStatisticCalculationSpyDouble.MODIFIERS.size(), representation.size());
        CharacterStatisticCalculationSpyDouble.MODIFIERS.forEach(p -> {
            assertTrue(representation.containsKey(p.getItem1()));
            assertTrue(representation.containsValue(p.getItem2()));
        });
    }

    @Test
    void testThrowsIllegalStateExceptionForDeadCharacter() {
        CHARACTER.delete();

        assertThrows(IllegalStateException.class, () -> _characterVariableStatistic.getCurrentValue());
        assertThrows(IllegalStateException.class, () -> _characterVariableStatistic.setCurrentValue(0));
        assertThrows(IllegalStateException.class, () -> _characterVariableStatistic.setCurrentValue(0));
        assertThrows(IllegalStateException.class, () -> _characterVariableStatistic.type());
        assertThrows(IllegalStateException.class, () -> _characterVariableStatistic.data());
        assertThrows(IllegalStateException.class, () -> _characterVariableStatistic.calculate());
        assertThrows(IllegalStateException.class, () -> _characterVariableStatistic.representation());
        assertThrows(IllegalStateException.class, () -> _characterVariableStatistic.totalValue());
    }

    @Test
    void testEnforceDeletionInvariant() {
        _characterVariableStatistic.delete();

        assertThrows(IllegalStateException.class, () -> _characterVariableStatistic.getCurrentValue());
        assertThrows(IllegalStateException.class, () -> _characterVariableStatistic.setCurrentValue(0));
        assertThrows(IllegalStateException.class, () -> _characterVariableStatistic.setCurrentValue(0));
        assertThrows(IllegalStateException.class, () -> _characterVariableStatistic.type());
        assertThrows(IllegalStateException.class, () -> _characterVariableStatistic.data());
        assertThrows(IllegalStateException.class, () -> _characterVariableStatistic.calculate());
        assertThrows(IllegalStateException.class, () -> _characterVariableStatistic.representation());
        assertThrows(IllegalStateException.class, () -> _characterVariableStatistic.totalValue());
    }
}

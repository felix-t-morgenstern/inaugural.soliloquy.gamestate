package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.CharacterVariableStatisticImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeCharacter;
import inaugural.soliloquy.gamestate.test.spydoubles.CharacterStatisticCalculationSpyDouble;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistic;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.ruleset.entities.CharacterVariableStatisticType;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CharacterVariableStatisticImplTests {
    private final Character CHARACTER = new FakeCharacter();
    private final VariableCache DATA = new VariableCacheStub();
    private final CharacterStatisticCalculationSpyDouble CHARACTER_STATISTIC_CALCULATION =
            new CharacterStatisticCalculationSpyDouble();

    @Mock private CharacterVariableStatisticType mockStatType;

    private CharacterVariableStatistic characterVariableStatistic;

    @BeforeEach
    void setUp() {
        mockStatType = mock(CharacterVariableStatisticType.class);

        characterVariableStatistic = new CharacterVariableStatisticImpl(CHARACTER,
                mockStatType, DATA, CHARACTER_STATISTIC_CALCULATION);
    }

    // TODO: Test constructor with invalid params

    @Test
    void testGetInterfaceName() {
        assertEquals(CharacterVariableStatistic.class.getCanonicalName(),
                characterVariableStatistic.getInterfaceName());
    }

    @Test
    void testType() {
        assertSame(mockStatType, characterVariableStatistic.type());
    }

    @Test
    void testData() {
        assertSame(DATA, characterVariableStatistic.data());
    }

    @Test
    void testSetAndGetCurrentValue() {
        characterVariableStatistic.setCurrentValue(123);

        assertEquals(123, characterVariableStatistic.getCurrentValue());
    }

    @Test
    void testCalculate() {
        characterVariableStatistic.calculate();

        assertSame(CHARACTER, CHARACTER_STATISTIC_CALCULATION._character);
        assertSame(mockStatType,
                CHARACTER_STATISTIC_CALCULATION._statisticType);
        assertEquals(CharacterStatisticCalculationSpyDouble.VALUE,
                characterVariableStatistic.totalValue());
        Map<String, Integer> representation =
                characterVariableStatistic.representation();
        assertEquals(CharacterStatisticCalculationSpyDouble.MODIFIERS.size(),
                representation.size());
        CharacterStatisticCalculationSpyDouble.MODIFIERS.forEach((modifierType, value) ->
                assertEquals(value, representation.get(modifierType)));
    }

    @Test
    void testThrowsIllegalStateExceptionForDeadCharacter() {
        CHARACTER.delete();

        assertThrows(IllegalStateException.class,
                () -> characterVariableStatistic.getCurrentValue());
        assertThrows(IllegalStateException.class,
                () -> characterVariableStatistic.setCurrentValue(0));
        assertThrows(IllegalStateException.class,
                () -> characterVariableStatistic.setCurrentValue(0));
        assertThrows(IllegalStateException.class, () -> characterVariableStatistic.type());
        assertThrows(IllegalStateException.class, () -> characterVariableStatistic.data());
        assertThrows(IllegalStateException.class, () -> characterVariableStatistic.calculate());
        assertThrows(IllegalStateException.class,
                () -> characterVariableStatistic.representation());
        assertThrows(IllegalStateException.class, () -> characterVariableStatistic.totalValue());
    }

    @Test
    void testEnforceDeletionInvariant() {
        characterVariableStatistic.delete();

        assertThrows(EntityDeletedException.class,
                () -> characterVariableStatistic.getCurrentValue());
        assertThrows(EntityDeletedException.class,
                () -> characterVariableStatistic.setCurrentValue(0));
        assertThrows(EntityDeletedException.class,
                () -> characterVariableStatistic.setCurrentValue(0));
        assertThrows(EntityDeletedException.class, () -> characterVariableStatistic.type());
        assertThrows(EntityDeletedException.class, () -> characterVariableStatistic.data());
        assertThrows(EntityDeletedException.class, () -> characterVariableStatistic.calculate());
        assertThrows(EntityDeletedException.class,
                () -> characterVariableStatistic.representation());
        assertThrows(EntityDeletedException.class, () -> characterVariableStatistic.totalValue());
    }
}

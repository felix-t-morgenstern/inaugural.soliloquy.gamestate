package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterDepletableStatisticImpl;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStatisticCalculationStub;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStub;
import inaugural.soliloquy.gamestate.test.stubs.VitalAttributeTypeStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterDepletableStatistic;
import soliloquy.specs.ruleset.entities.CharacterDepletableStatisticType;
import soliloquy.specs.ruleset.gameconcepts.CharacterStatisticCalculation;

import static org.junit.jupiter.api.Assertions.*;

class CharacterDepletableStatisticImplTests {
    private CharacterDepletableStatistic _characterDepletableStatistic;

    private final Character CHARACTER = new CharacterStub();
    private final CharacterDepletableStatisticType CHARACTER_DEPLETABLE_STATISTIC_TYPE =
            new VitalAttributeTypeStub();
    private final CharacterStatisticCalculation CHARACTER_STATISTIC_CALCULATION =
            new CharacterStatisticCalculationStub();

    @BeforeEach
    void setUp() {
        _characterDepletableStatistic = new CharacterDepletableStatisticImpl(CHARACTER,
                CHARACTER_DEPLETABLE_STATISTIC_TYPE, CHARACTER_STATISTIC_CALCULATION);
    }

    // TODO: Test constructor with invalid params

    @Test
    void testGetInterfaceName() {
        assertEquals(CharacterDepletableStatistic.class.getCanonicalName(),
                _characterDepletableStatistic.getInterfaceName());
    }

    @Test
    void testVitalAttributeType() {
        assertSame(CHARACTER_DEPLETABLE_STATISTIC_TYPE, _characterDepletableStatistic.type());
    }

    @Test
    void testSetAndGetCurrentValue() {
        _characterDepletableStatistic.setCurrentValue(123);

        assertEquals(123, _characterDepletableStatistic.getCurrentValue());
    }

    @Test
    void testCalculateValue() {
        _characterDepletableStatistic.calculate();

        assertSame(CHARACTER, CharacterStatisticCalculationStub._character);
        assertSame(CHARACTER_DEPLETABLE_STATISTIC_TYPE,
                CharacterStatisticCalculationStub._statisticType);
        assertEquals(CharacterStatisticCalculationStub.VALUE,
                _characterDepletableStatistic.totalValue());
        ReadableMap<String,Integer> representation =
                _characterDepletableStatistic.representation();
        assertEquals(CharacterStatisticCalculationStub.MODIFIERS.size(), representation.size());
        CharacterStatisticCalculationStub.MODIFIERS.forEach(p -> {
            assertTrue(representation.containsKey(p.getItem1()));
            assertTrue(representation.containsValue(p.getItem2()));
        });
    }

    @Test
    void testThrowsIllegalStateExceptionForDeadCharacter() {
        CHARACTER.delete();

        assertThrows(IllegalStateException.class, () -> _characterDepletableStatistic.getCurrentValue());
        assertThrows(IllegalStateException.class, () -> _characterDepletableStatistic.setCurrentValue(0));
        assertThrows(IllegalStateException.class, () -> _characterDepletableStatistic.setCurrentValue(0));
        assertThrows(IllegalStateException.class, () -> _characterDepletableStatistic.type());
        assertThrows(IllegalStateException.class, () -> _characterDepletableStatistic.getInterfaceName());
        assertThrows(IllegalStateException.class, () -> _characterDepletableStatistic.calculate());
        assertThrows(IllegalStateException.class, () -> _characterDepletableStatistic.representation());
        assertThrows(IllegalStateException.class, () -> _characterDepletableStatistic.totalValue());
    }

    @Test
    void testEnforceDeletionInvariant() {
        _characterDepletableStatistic.delete();

        assertThrows(IllegalStateException.class, () -> _characterDepletableStatistic.getCurrentValue());
        assertThrows(IllegalStateException.class, () -> _characterDepletableStatistic.setCurrentValue(0));
        assertThrows(IllegalStateException.class, () -> _characterDepletableStatistic.setCurrentValue(0));
        assertThrows(IllegalStateException.class, () -> _characterDepletableStatistic.type());
        assertThrows(IllegalStateException.class, () -> _characterDepletableStatistic.getInterfaceName());
        assertThrows(IllegalStateException.class, () -> _characterDepletableStatistic.calculate());
        assertThrows(IllegalStateException.class, () -> _characterDepletableStatistic.representation());
        assertThrows(IllegalStateException.class, () -> _characterDepletableStatistic.totalValue());
    }
}

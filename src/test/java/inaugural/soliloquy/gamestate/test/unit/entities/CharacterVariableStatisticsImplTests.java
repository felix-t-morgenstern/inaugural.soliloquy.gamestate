package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.CharacterVariableStatisticsImpl;
import inaugural.soliloquy.gamestate.test.fakes.*;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistic;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistics;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.ruleset.entities.CharacterVariableStatisticType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static inaugural.soliloquy.tools.random.Random.randomInt;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CharacterVariableStatisticsImplTests {
    private final Character CHARACTER = new FakeCharacter();
    private final FakeVariableCacheFactory DATA_FACTORY = new FakeVariableCacheFactory();
    private final FakeCharacterVariableStatisticFactory FACTORY =
            new FakeCharacterVariableStatisticFactory();

    @Mock private CharacterVariableStatisticType mockStatType1;
    @Mock private CharacterVariableStatisticType mockStatType2;
    @Mock private CharacterVariableStatisticType mockStatType3;

    private CharacterVariableStatistics variableStats;

    @BeforeEach
    void setUp() {
        mockStatType1 = mock(CharacterVariableStatisticType.class);
        mockStatType2 = mock(CharacterVariableStatisticType.class);
        mockStatType3 = mock(CharacterVariableStatisticType.class);

        variableStats = new CharacterVariableStatisticsImpl(CHARACTER, FACTORY, DATA_FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterVariableStatisticsImpl(null, FACTORY, DATA_FACTORY));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterVariableStatisticsImpl(CHARACTER, null, DATA_FACTORY));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterVariableStatisticsImpl(CHARACTER, FACTORY, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(CharacterVariableStatistics.class.getCanonicalName(),
                variableStats.getInterfaceName());
    }

    @Test
    void testAddAndGet() {
        assertNull(variableStats.get(mockStatType1));

        variableStats.add(mockStatType1);

        assertNotNull(variableStats.get(mockStatType1));
        assertSame(CHARACTER,
                ((FakeCharacterVariableStatistic) variableStats.get(mockStatType1))._character);
        assertSame(DATA_FACTORY.Created.get(0), variableStats.get(mockStatType1).data());
    }

    @Test
    void testAddWithData() {
        VariableCache data = new VariableCacheStub();

        assertNull(variableStats.get(mockStatType1));

        variableStats.add(mockStatType1, data);

        assertNotNull(variableStats.get(mockStatType1));
        assertSame(CHARACTER,
                ((FakeCharacterVariableStatistic) variableStats.get(mockStatType1))._character);
        assertSame(data, variableStats.get(mockStatType1).data());
    }

    @Test
    void testAddExistingTypeIsNondestructive() {
        variableStats.add(mockStatType1);

        CharacterVariableStatistic entity = variableStats.get(mockStatType1);

        variableStats.add(mockStatType1);

        CharacterVariableStatistic entityAfterSecondAdd = variableStats.get(mockStatType1);

        assertSame(entity, entityAfterSecondAdd);
    }

    @Test
    void testSize() {
        variableStats.add(mockStatType1);
        variableStats.add(mockStatType2);
        variableStats.add(mockStatType3);

        assertEquals(3, variableStats.size());
    }

    @Test
    void testContains() {
        assertFalse(variableStats.contains(mockStatType1));

        variableStats.add(mockStatType1);

        assertTrue(variableStats.contains(mockStatType1));
    }

    @Test
    void testRemove() {
        assertFalse(variableStats.remove(mockStatType1));

        variableStats.add(mockStatType1);

        assertTrue(variableStats.remove(mockStatType1));
        assertFalse(variableStats.remove(mockStatType1));
    }

    @Test
    void testMethodsWithNullType() {
        assertThrows(IllegalArgumentException.class, () -> variableStats.add(null));
        assertThrows(IllegalArgumentException.class, () -> variableStats.get(null));
        assertThrows(IllegalArgumentException.class, () -> variableStats.contains(null));
        assertThrows(IllegalArgumentException.class, () -> variableStats.remove(null));
    }

    @Test
    void testClear() {
        variableStats.add(mockStatType1);
        variableStats.add(mockStatType2);
        variableStats.add(mockStatType3);

        variableStats.clear();

        assertEquals(0, variableStats.size());
    }

    @Test
    void testGetCurrentValues() {
        variableStats.add(mockStatType1);
        variableStats.add(mockStatType2);
        variableStats.add(mockStatType3);

        variableStats.get(mockStatType1).setCurrentValue(123);
        variableStats.get(mockStatType2).setCurrentValue(456);
        variableStats.get(mockStatType3).setCurrentValue(789);

        Map<CharacterVariableStatisticType, Integer> currentValues = variableStats.currentValues();

        assertNotNull(currentValues);
        assertEquals(3, currentValues.size());
        assertEquals(123, currentValues.get(mockStatType1));
        assertEquals(456, currentValues.get(mockStatType2));
        assertEquals(789, currentValues.get(mockStatType3));
    }

    @Test
    void testGetMaxValues() {
        int maxValue1 = randomInt();
        int maxValue2 = randomInt();
        int maxValue3 = randomInt();

        variableStats.add(mockStatType1);
        variableStats.add(mockStatType2);
        variableStats.add(mockStatType3);

        ((FakeCharacterVariableStatistic) variableStats.get(mockStatType1))._maxValue = maxValue1;
        ((FakeCharacterVariableStatistic) variableStats.get(mockStatType2))._maxValue = maxValue2;
        ((FakeCharacterVariableStatistic) variableStats.get(mockStatType3))._maxValue = maxValue3;

        Map<CharacterVariableStatisticType, Integer> maxValues = variableStats.maxValues();

        assertNotNull(maxValues);
        assertEquals(3, maxValues.size());
        assertEquals(maxValue1, maxValues.get(mockStatType1));
        assertEquals(maxValue2, maxValues.get(mockStatType2));
        assertEquals(maxValue3, maxValues.get(mockStatType3));
        assertTrue(
                ((FakeCharacterVariableStatistic) variableStats.get(mockStatType1))._isCalculated);
        assertTrue(
                ((FakeCharacterVariableStatistic) variableStats.get(mockStatType2))._isCalculated);
        assertTrue(
                ((FakeCharacterVariableStatistic) variableStats.get(mockStatType3))._isCalculated);
    }

    @Test
    void testRepresentation() {
        variableStats.add(mockStatType1);
        variableStats.add(mockStatType2);
        variableStats.add(mockStatType3);

        List<CharacterVariableStatistic> representation = variableStats.representation();

        assertNotNull(representation);
        assertEquals(3, representation.size());
        Arrays.stream(
                new CharacterVariableStatisticType[]{mockStatType1, mockStatType2, mockStatType3})
                .forEach(t -> {
                    for (CharacterVariableStatistic variableStat : representation) {
                        if (variableStat.type() == t) {
                            return;
                        }
                    }
                    fail();
                });
    }

    @Test
    void testIterator() {
        variableStats.add(mockStatType1);
        variableStats.add(mockStatType2);
        variableStats.add(mockStatType3);

        List<CharacterVariableStatistic> representation = variableStats.representation();

        ArrayList<CharacterVariableStatistic> fromIterator = new ArrayList<>();

        variableStats.forEach(fromIterator::add);

        assertEquals(3, fromIterator.size());
        representation.forEach(e -> assertTrue(fromIterator.contains(e)));
    }

    @Test
    void testDelete() {
        variableStats.add(mockStatType1);
        variableStats.add(mockStatType2);
        variableStats.add(mockStatType3);

        List<CharacterVariableStatistic> representation = variableStats.representation();

        variableStats.delete();

        assertTrue(variableStats.isDeleted());
        representation.forEach(e -> assertTrue(e.isDeleted()));
    }

    @Test
    void testDeletionInvariant() {
        variableStats.delete();

        assertThrows(EntityDeletedException.class, () -> variableStats.add(mockStatType1));
        assertThrows(EntityDeletedException.class, () -> variableStats.get(mockStatType1));
        assertThrows(EntityDeletedException.class, () -> variableStats.contains(mockStatType1));
        assertThrows(EntityDeletedException.class, () -> variableStats.remove(mockStatType1));
        assertThrows(EntityDeletedException.class, () -> variableStats.size());
        assertThrows(EntityDeletedException.class, () -> variableStats.clear());
        assertThrows(EntityDeletedException.class, () -> variableStats.representation());
        assertThrows(EntityDeletedException.class, () -> variableStats.iterator());
        assertThrows(EntityDeletedException.class, () -> variableStats.currentValues());
        assertThrows(EntityDeletedException.class, () -> variableStats.maxValues());
    }

    @Test
    void testCharacterDeletionInvariant() {
        CHARACTER.delete();

        assertThrows(IllegalStateException.class, () -> variableStats.add(mockStatType1));
        assertThrows(IllegalStateException.class, () -> variableStats.get(mockStatType1));
        assertThrows(IllegalStateException.class, () -> variableStats.contains(mockStatType1));
        assertThrows(IllegalStateException.class, () -> variableStats.remove(mockStatType1));
        assertThrows(IllegalStateException.class, () -> variableStats.size());
        assertThrows(IllegalStateException.class, () -> variableStats.clear());
        assertThrows(IllegalStateException.class, () -> variableStats.representation());
        assertThrows(IllegalStateException.class, () -> variableStats.iterator());
        assertThrows(IllegalStateException.class, () -> variableStats.currentValues());
        assertThrows(IllegalStateException.class, () -> variableStats.maxValues());
    }
}

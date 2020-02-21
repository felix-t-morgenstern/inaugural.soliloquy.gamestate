package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterVariableStatisticsImpl;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistic;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistics;
import soliloquy.specs.ruleset.entities.CharacterVariableStatisticType;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CharacterVariableStatisticsImplTests {
    private final Character CHARACTER = new CharacterStub();
    private final CollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    private final MapFactory MAP_FACTORY = new MapFactoryStub();
    private final CharacterVariableStatisticFactoryStub FACTORY =
            new CharacterVariableStatisticFactoryStub();

    private CharacterVariableStatistics _variableStats;

    @BeforeEach
    void setUp() {
        _variableStats = new CharacterVariableStatisticsImpl(CHARACTER, MAP_FACTORY,
                COLLECTION_FACTORY, FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterVariableStatisticsImpl(null, MAP_FACTORY, COLLECTION_FACTORY,
                        FACTORY));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterVariableStatisticsImpl(CHARACTER, null, COLLECTION_FACTORY,
                        FACTORY));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterVariableStatisticsImpl(CHARACTER, MAP_FACTORY, null,
                        FACTORY));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterVariableStatisticsImpl(CHARACTER, MAP_FACTORY, COLLECTION_FACTORY,
                        null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(CharacterVariableStatistics.class.getCanonicalName(),
                _variableStats.getInterfaceName());
    }

    @Test
    void testAddAndGet() {
        CharacterVariableStatisticType type = new CharacterVariableStatisticTypeStub("");

        assertNull(_variableStats.get(type));

        _variableStats.add(type);

        assertNotNull(_variableStats.get(type));
        assertSame(CHARACTER,
                ((CharacterVariableStatisticStub) _variableStats.get(type))._character);
    }

    @Test
    void testAddExistingTypeIsNondestructive() {
        CharacterVariableStatisticType type = new CharacterVariableStatisticTypeStub("");

        _variableStats.add(type);

        CharacterVariableStatistic entity = _variableStats.get(type);

        _variableStats.add(type);

        CharacterVariableStatistic entityAfterSecondAdd = _variableStats.get(type);

        assertSame(entity, entityAfterSecondAdd);
    }

    @Test
    void testSize() {
        _variableStats.add(new CharacterVariableStatisticTypeStub("id1"));
        _variableStats.add(new CharacterVariableStatisticTypeStub("id2"));
        _variableStats.add(new CharacterVariableStatisticTypeStub("id3"));

        assertEquals(3, _variableStats.size());
    }

    @Test
    void testContains() {
        CharacterVariableStatisticType type = new CharacterVariableStatisticTypeStub("");

        assertFalse(_variableStats.contains(type));

        _variableStats.add(type);

        assertTrue(_variableStats.contains(type));
    }

    @Test
    void testRemove() {
        CharacterVariableStatisticType type = new CharacterVariableStatisticTypeStub("");

        assertFalse(_variableStats.remove(type));

        _variableStats.add(type);

        assertTrue(_variableStats.remove(type));
        assertFalse(_variableStats.remove(type));
    }

    @Test
    void testMethodsWithNullType() {
        assertThrows(IllegalArgumentException.class, () -> _variableStats.add(null));
        assertThrows(IllegalArgumentException.class, () -> _variableStats.get(null));
        assertThrows(IllegalArgumentException.class, () -> _variableStats.contains(null));
        assertThrows(IllegalArgumentException.class, () -> _variableStats.remove(null));
    }

    @Test
    void testClear() {
        CharacterVariableStatisticType type1 =
                new CharacterVariableStatisticTypeStub("id1");
        CharacterVariableStatisticType type2 =
                new CharacterVariableStatisticTypeStub("id2");
        CharacterVariableStatisticType type3 =
                new CharacterVariableStatisticTypeStub("id3");

        _variableStats.add(type1);
        _variableStats.add(type2);
        _variableStats.add(type3);

        _variableStats.clear();

        assertEquals(0, _variableStats.size());
    }

    @Test
    void testGetCurrentValues() {
        CharacterVariableStatisticType type1 =
                new CharacterVariableStatisticTypeStub("id1");
        CharacterVariableStatisticType type2 =
                new CharacterVariableStatisticTypeStub("id2");
        CharacterVariableStatisticType type3 =
                new CharacterVariableStatisticTypeStub("id3");

        _variableStats.add(type1);
        _variableStats.add(type2);
        _variableStats.add(type3);

        _variableStats.get(type1).setCurrentValue(123);
        _variableStats.get(type2).setCurrentValue(456);
        _variableStats.get(type3).setCurrentValue(789);

        ReadableMap<CharacterVariableStatisticType,Integer> currentValues =
                _variableStats.currentValues();

        assertNotNull(currentValues);
        assertEquals(3, currentValues.size());
        assertEquals(123, currentValues.get(type1));
        assertEquals(456, currentValues.get(type2));
        assertEquals(789, currentValues.get(type3));
    }

    @Test
    void testGetMaxValues() {
        CharacterVariableStatisticType type1 =
                new CharacterVariableStatisticTypeStub("id1");
        CharacterVariableStatisticType type2 =
                new CharacterVariableStatisticTypeStub("id2");
        CharacterVariableStatisticType type3 =
                new CharacterVariableStatisticTypeStub("id3");

        _variableStats.add(type1);
        _variableStats.add(type2);
        _variableStats.add(type3);

        ((CharacterVariableStatisticStub) _variableStats.get(type1))._maxValue = 123;
        ((CharacterVariableStatisticStub) _variableStats.get(type2))._maxValue = 456;
        ((CharacterVariableStatisticStub) _variableStats.get(type3))._maxValue = 789;

        ReadableMap<CharacterVariableStatisticType,Integer> maxValues =
                _variableStats.maxValues();

        assertNotNull(maxValues);
        assertEquals(3, maxValues.size());
        assertEquals(123, maxValues.get(type1));
        assertEquals(456, maxValues.get(type2));
        assertEquals(789, maxValues.get(type3));
        assertTrue(((CharacterVariableStatisticStub) _variableStats.get(type1))._isCalculated);
        assertTrue(((CharacterVariableStatisticStub) _variableStats.get(type2))._isCalculated);
        assertTrue(((CharacterVariableStatisticStub) _variableStats.get(type3))._isCalculated);
    }

    @Test
    void testRepresentation() {
        CharacterVariableStatisticType type1 =
                new CharacterVariableStatisticTypeStub("id1");
        CharacterVariableStatisticType type2 =
                new CharacterVariableStatisticTypeStub("id2");
        CharacterVariableStatisticType type3 =
                new CharacterVariableStatisticTypeStub("id3");

        _variableStats.add(type1);
        _variableStats.add(type2);
        _variableStats.add(type3);

        ReadableCollection<CharacterVariableStatistic> representation =
                _variableStats.representation();

        assertNotNull(representation);
        assertEquals(3, representation.size());
        Arrays.stream(new CharacterVariableStatisticType[] { type1, type2, type3 })
            .forEach(t -> {
                for(CharacterVariableStatistic variableStat : representation) {
                    if (variableStat.type() == t) {
                        return;
                    }
                }
                fail();
            });
    }

    @Test
    void testIterator() {
        CharacterVariableStatisticType type1 =
                new CharacterVariableStatisticTypeStub("id1");
        CharacterVariableStatisticType type2 =
                new CharacterVariableStatisticTypeStub("id2");
        CharacterVariableStatisticType type3 =
                new CharacterVariableStatisticTypeStub("id3");

        _variableStats.add(type1);
        _variableStats.add(type2);
        _variableStats.add(type3);

        ReadableCollection<CharacterVariableStatistic> representation =
                _variableStats.representation();

        ArrayList<CharacterVariableStatistic> fromIterator = new ArrayList<>();

        _variableStats.forEach(fromIterator::add);

        assertEquals(3, fromIterator.size());
        representation.forEach(e -> assertTrue(fromIterator.contains(e)));
    }

    @Test
    void testDelete() {
        CharacterVariableStatisticType type1 =
                new CharacterVariableStatisticTypeStub("id1");
        CharacterVariableStatisticType type2 =
                new CharacterVariableStatisticTypeStub("id2");
        CharacterVariableStatisticType type3 =
                new CharacterVariableStatisticTypeStub("id3");

        _variableStats.add(type1);
        _variableStats.add(type2);
        _variableStats.add(type3);

        ReadableCollection<CharacterVariableStatistic> representation =
                _variableStats.representation();

        _variableStats.delete();

        assertTrue(_variableStats.isDeleted());
        representation.forEach(e -> assertTrue(e.isDeleted()));
    }

    @Test
    void testDeletionInvariant() {
        CharacterVariableStatisticType type = new CharacterVariableStatisticTypeStub("");

        _variableStats.delete();

        assertThrows(IllegalStateException.class, () -> _variableStats.add(type));
        assertThrows(IllegalStateException.class, () -> _variableStats.get(type));
        assertThrows(IllegalStateException.class, () -> _variableStats.contains(type));
        assertThrows(IllegalStateException.class, () -> _variableStats.remove(type));
        assertThrows(IllegalStateException.class, () -> _variableStats.size());
        assertThrows(IllegalStateException.class, () -> _variableStats.clear());
        assertThrows(IllegalStateException.class, () -> _variableStats.representation());
        assertThrows(IllegalStateException.class, () -> _variableStats.iterator());
        assertThrows(IllegalStateException.class, () -> _variableStats.currentValues());
        assertThrows(IllegalStateException.class, () -> _variableStats.maxValues());
    }

    @Test
    void testCharacterDeletionInvariant() {
        CharacterVariableStatisticType type = new CharacterVariableStatisticTypeStub("");

        CHARACTER.delete();

        assertThrows(IllegalStateException.class, () -> _variableStats.add(type));
        assertThrows(IllegalStateException.class, () -> _variableStats.get(type));
        assertThrows(IllegalStateException.class, () -> _variableStats.contains(type));
        assertThrows(IllegalStateException.class, () -> _variableStats.remove(type));
        assertThrows(IllegalStateException.class, () -> _variableStats.size());
        assertThrows(IllegalStateException.class, () -> _variableStats.clear());
        assertThrows(IllegalStateException.class, () -> _variableStats.representation());
        assertThrows(IllegalStateException.class, () -> _variableStats.iterator());
        assertThrows(IllegalStateException.class, () -> _variableStats.currentValues());
        assertThrows(IllegalStateException.class, () -> _variableStats.maxValues());
    }
}

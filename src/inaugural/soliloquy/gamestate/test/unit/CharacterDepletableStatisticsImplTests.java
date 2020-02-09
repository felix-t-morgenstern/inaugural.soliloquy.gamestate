package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterDepletableStatisticsImpl;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterDepletableStatistic;
import soliloquy.specs.gamestate.entities.CharacterDepletableStatistics;
import soliloquy.specs.ruleset.entities.CharacterDepletableStatisticType;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CharacterDepletableStatisticsImplTests {
    private final Character CHARACTER = new CharacterStub();
    private final CollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    private final MapFactory MAP_FACTORY = new MapFactoryStub();
    private final CharacterDepletableStatisticFactoryStub FACTORY =
            new CharacterDepletableStatisticFactoryStub();

    private CharacterDepletableStatistics _depletableStats;

    @BeforeEach
    void setUp() {
        _depletableStats = new CharacterDepletableStatisticsImpl(CHARACTER, MAP_FACTORY,
                COLLECTION_FACTORY, FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterDepletableStatisticsImpl(null, MAP_FACTORY, COLLECTION_FACTORY,
                        FACTORY));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterDepletableStatisticsImpl(CHARACTER, null, COLLECTION_FACTORY,
                        FACTORY));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterDepletableStatisticsImpl(CHARACTER, MAP_FACTORY, null,
                        FACTORY));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterDepletableStatisticsImpl(CHARACTER, MAP_FACTORY, COLLECTION_FACTORY,
                        null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(CharacterDepletableStatistics.class.getCanonicalName(),
                _depletableStats.getInterfaceName());
    }

    @Test
    void testAddAndGet() {
        CharacterDepletableStatisticType type = new CharacterDepletableStatisticTypeStub("");

        assertNull(_depletableStats.get(type));

        _depletableStats.add(type);

        assertNotNull(_depletableStats.get(type));
        assertSame(CHARACTER,
                ((CharacterDepletableStatisticStub)_depletableStats.get(type))._character);
    }

    @Test
    void testAddExistingTypeIsNondestructive() {
        CharacterDepletableStatisticType type = new CharacterDepletableStatisticTypeStub("");

        _depletableStats.add(type);

        CharacterDepletableStatistic entity = _depletableStats.get(type);

        _depletableStats.add(type);

        CharacterDepletableStatistic entityAfterSecondAdd = _depletableStats.get(type);

        assertSame(entity, entityAfterSecondAdd);
    }

    @Test
    void testSize() {
        _depletableStats.add(new CharacterDepletableStatisticTypeStub("id1"));
        _depletableStats.add(new CharacterDepletableStatisticTypeStub("id2"));
        _depletableStats.add(new CharacterDepletableStatisticTypeStub("id3"));

        assertEquals(3, _depletableStats.size());
    }

    @Test
    void testContains() {
        CharacterDepletableStatisticType type = new CharacterDepletableStatisticTypeStub("");

        assertFalse(_depletableStats.contains(type));

        _depletableStats.add(type);

        assertTrue(_depletableStats.contains(type));
    }

    @Test
    void testRemove() {
        CharacterDepletableStatisticType type = new CharacterDepletableStatisticTypeStub("");

        assertFalse(_depletableStats.remove(type));

        _depletableStats.add(type);

        assertTrue(_depletableStats.remove(type));
        assertFalse(_depletableStats.remove(type));
    }

    @Test
    void testMethodsWithNullType() {
        assertThrows(IllegalArgumentException.class, () -> _depletableStats.add(null));
        assertThrows(IllegalArgumentException.class, () -> _depletableStats.get(null));
        assertThrows(IllegalArgumentException.class, () -> _depletableStats.contains(null));
        assertThrows(IllegalArgumentException.class, () -> _depletableStats.remove(null));
    }

    @Test
    void testClear() {
        CharacterDepletableStatisticType type1 =
                new CharacterDepletableStatisticTypeStub("id1");
        CharacterDepletableStatisticType type2 =
                new CharacterDepletableStatisticTypeStub("id2");
        CharacterDepletableStatisticType type3 =
                new CharacterDepletableStatisticTypeStub("id3");

        _depletableStats.add(type1);
        _depletableStats.add(type2);
        _depletableStats.add(type3);

        _depletableStats.clear();

        assertEquals(0, _depletableStats.size());
    }

    @Test
    void testGetCurrentValues() {
        CharacterDepletableStatisticType type1 =
                new CharacterDepletableStatisticTypeStub("id1");
        CharacterDepletableStatisticType type2 =
                new CharacterDepletableStatisticTypeStub("id2");
        CharacterDepletableStatisticType type3 =
                new CharacterDepletableStatisticTypeStub("id3");

        _depletableStats.add(type1);
        _depletableStats.add(type2);
        _depletableStats.add(type3);

        _depletableStats.get(type1).setCurrentValue(123);
        _depletableStats.get(type2).setCurrentValue(456);
        _depletableStats.get(type3).setCurrentValue(789);

        ReadableMap<CharacterDepletableStatisticType,Integer> currentValues =
                _depletableStats.currentValues();

        assertNotNull(currentValues);
        assertEquals(3, currentValues.size());
        assertEquals(123, currentValues.get(type1));
        assertEquals(456, currentValues.get(type2));
        assertEquals(789, currentValues.get(type3));
    }

    @Test
    void testGetMaxValues() {
        CharacterDepletableStatisticType type1 =
                new CharacterDepletableStatisticTypeStub("id1");
        CharacterDepletableStatisticType type2 =
                new CharacterDepletableStatisticTypeStub("id2");
        CharacterDepletableStatisticType type3 =
                new CharacterDepletableStatisticTypeStub("id3");

        _depletableStats.add(type1);
        _depletableStats.add(type2);
        _depletableStats.add(type3);

        ((CharacterDepletableStatisticStub)_depletableStats.get(type1))._maxValue = 123;
        ((CharacterDepletableStatisticStub)_depletableStats.get(type2))._maxValue = 456;
        ((CharacterDepletableStatisticStub)_depletableStats.get(type3))._maxValue = 789;

        ReadableMap<CharacterDepletableStatisticType,Integer> maxValues =
                _depletableStats.maxValues();

        assertNotNull(maxValues);
        assertEquals(3, maxValues.size());
        assertEquals(123, maxValues.get(type1));
        assertEquals(456, maxValues.get(type2));
        assertEquals(789, maxValues.get(type3));
        assertTrue(((CharacterDepletableStatisticStub)_depletableStats.get(type1))._isCalculated);
        assertTrue(((CharacterDepletableStatisticStub)_depletableStats.get(type2))._isCalculated);
        assertTrue(((CharacterDepletableStatisticStub)_depletableStats.get(type3))._isCalculated);
    }

    @Test
    void testRepresentation() {
        CharacterDepletableStatisticType type1 =
                new CharacterDepletableStatisticTypeStub("id1");
        CharacterDepletableStatisticType type2 =
                new CharacterDepletableStatisticTypeStub("id2");
        CharacterDepletableStatisticType type3 =
                new CharacterDepletableStatisticTypeStub("id3");

        _depletableStats.add(type1);
        _depletableStats.add(type2);
        _depletableStats.add(type3);

        ReadableCollection<CharacterDepletableStatistic> representation =
                _depletableStats.representation();

        assertNotNull(representation);
        assertEquals(3, representation.size());
        Arrays.stream(new CharacterDepletableStatisticType[] { type1, type2, type3 })
            .forEach(t -> {
                for(CharacterDepletableStatistic depletableStat : representation) {
                    if (depletableStat.type() == t) {
                        return;
                    }
                }
                fail();
            });
    }

    @Test
    void testIterator() {
        CharacterDepletableStatisticType type1 =
                new CharacterDepletableStatisticTypeStub("id1");
        CharacterDepletableStatisticType type2 =
                new CharacterDepletableStatisticTypeStub("id2");
        CharacterDepletableStatisticType type3 =
                new CharacterDepletableStatisticTypeStub("id3");

        _depletableStats.add(type1);
        _depletableStats.add(type2);
        _depletableStats.add(type3);

        ReadableCollection<CharacterDepletableStatistic> representation =
                _depletableStats.representation();

        ArrayList<CharacterDepletableStatistic> fromIterator = new ArrayList<>();

        _depletableStats.forEach(fromIterator::add);

        assertEquals(3, fromIterator.size());
        representation.forEach(e -> assertTrue(fromIterator.contains(e)));
    }

    @Test
    void testDelete() {
        CharacterDepletableStatisticType type1 =
                new CharacterDepletableStatisticTypeStub("id1");
        CharacterDepletableStatisticType type2 =
                new CharacterDepletableStatisticTypeStub("id2");
        CharacterDepletableStatisticType type3 =
                new CharacterDepletableStatisticTypeStub("id3");

        _depletableStats.add(type1);
        _depletableStats.add(type2);
        _depletableStats.add(type3);

        ReadableCollection<CharacterDepletableStatistic> representation =
                _depletableStats.representation();

        _depletableStats.delete();

        assertTrue(_depletableStats.isDeleted());
        representation.forEach(e -> assertTrue(e.isDeleted()));
    }

    @Test
    void testDeletionInvariant() {
        CharacterDepletableStatisticType type = new CharacterDepletableStatisticTypeStub("");

        _depletableStats.delete();

        assertThrows(IllegalStateException.class, () -> _depletableStats.add(type));
        assertThrows(IllegalStateException.class, () -> _depletableStats.get(type));
        assertThrows(IllegalStateException.class, () -> _depletableStats.contains(type));
        assertThrows(IllegalStateException.class, () -> _depletableStats.remove(type));
        assertThrows(IllegalStateException.class, () -> _depletableStats.size());
        assertThrows(IllegalStateException.class, () -> _depletableStats.clear());
        assertThrows(IllegalStateException.class, () -> _depletableStats.representation());
        assertThrows(IllegalStateException.class, () -> _depletableStats.iterator());
        assertThrows(IllegalStateException.class, () -> _depletableStats.currentValues());
        assertThrows(IllegalStateException.class, () -> _depletableStats.maxValues());
    }

    @Test
    void testCharacterDeletionInvariant() {
        CharacterDepletableStatisticType type = new CharacterDepletableStatisticTypeStub("");

        CHARACTER.delete();

        assertThrows(IllegalStateException.class, () -> _depletableStats.add(type));
        assertThrows(IllegalStateException.class, () -> _depletableStats.get(type));
        assertThrows(IllegalStateException.class, () -> _depletableStats.contains(type));
        assertThrows(IllegalStateException.class, () -> _depletableStats.remove(type));
        assertThrows(IllegalStateException.class, () -> _depletableStats.size());
        assertThrows(IllegalStateException.class, () -> _depletableStats.clear());
        assertThrows(IllegalStateException.class, () -> _depletableStats.representation());
        assertThrows(IllegalStateException.class, () -> _depletableStats.iterator());
        assertThrows(IllegalStateException.class, () -> _depletableStats.currentValues());
        assertThrows(IllegalStateException.class, () -> _depletableStats.maxValues());
    }
}

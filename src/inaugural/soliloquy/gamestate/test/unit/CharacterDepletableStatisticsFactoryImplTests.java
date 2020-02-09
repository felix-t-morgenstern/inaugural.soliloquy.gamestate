package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterDepletableStatisticsFactoryImpl;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterDepletableStatistic;
import soliloquy.specs.gamestate.entities.CharacterDepletableStatistics;
import soliloquy.specs.gamestate.factories.CharacterDepletableStatisticsFactory;
import soliloquy.specs.ruleset.entities.CharacterDepletableStatisticType;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class CharacterDepletableStatisticsFactoryImplTests {
    private final MapFactory MAP_FACTORY = new MapFactoryStub();
    private final CollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    private final CharacterDepletableStatisticFactoryStub FACTORY =
            new CharacterDepletableStatisticFactoryStub();

    private CharacterDepletableStatisticsFactory _characterDepletableStatisticsFactory;

    @BeforeEach
    void setUp() {
        _characterDepletableStatisticsFactory =
                new CharacterDepletableStatisticsFactoryImpl(MAP_FACTORY, COLLECTION_FACTORY,
                        FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterDepletableStatisticsFactoryImpl(null,
                        COLLECTION_FACTORY, FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterDepletableStatisticsFactoryImpl(MAP_FACTORY,
                        null, FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterDepletableStatisticsFactoryImpl(MAP_FACTORY,
                        COLLECTION_FACTORY, null));
    }

    @Test
    void testMake() {
        Character character = new CharacterStub();

        CharacterDepletableStatistics characterDepletableStatistics =
                _characterDepletableStatisticsFactory.make(character);

        assertNotNull(characterDepletableStatistics);

        String typeId = "typeId";
        CharacterDepletableStatisticType type = new CharacterDepletableStatisticTypeStub(typeId);
        characterDepletableStatistics.add(type);

        CharacterDepletableStatistic characterDepletableStatistic =
                characterDepletableStatistics.get(type);

        assertNotNull(characterDepletableStatistic);
        assertSame(character,
                ((CharacterDepletableStatisticStub)characterDepletableStatistic)._character);
        assertEquals(type,
                ((CharacterDepletableStatisticStub)characterDepletableStatistic)._type);
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _characterDepletableStatisticsFactory.make(null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(CharacterDepletableStatisticsFactory.class.getCanonicalName(),
                _characterDepletableStatisticsFactory.getInterfaceName());
    }
}

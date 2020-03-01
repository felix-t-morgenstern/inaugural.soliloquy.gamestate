package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterVariableStatisticsFactoryImpl;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistic;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistics;
import soliloquy.specs.gamestate.factories.CharacterVariableStatisticsFactory;
import soliloquy.specs.ruleset.entities.CharacterVariableStatisticType;

import static org.junit.jupiter.api.Assertions.*;

class CharacterVariableStatisticsFactoryImplTests {
    private final MapFactory MAP_FACTORY = new MapFactoryStub();
    private final CollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    private final VariableCacheFactory DATA_FACTORY = new VariableCacheFactoryStub();
    private final CharacterVariableStatisticFactoryStub ENTITY_FACTORY =
            new CharacterVariableStatisticFactoryStub();

    private CharacterVariableStatisticsFactory _characterVariableStatisticsFactory;

    @BeforeEach
    void setUp() {
        _characterVariableStatisticsFactory =
                new CharacterVariableStatisticsFactoryImpl(MAP_FACTORY, COLLECTION_FACTORY,
                        DATA_FACTORY, ENTITY_FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterVariableStatisticsFactoryImpl(null,
                        COLLECTION_FACTORY, DATA_FACTORY, ENTITY_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterVariableStatisticsFactoryImpl(MAP_FACTORY,
                        null, DATA_FACTORY, ENTITY_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterVariableStatisticsFactoryImpl(MAP_FACTORY,
                        COLLECTION_FACTORY, null, ENTITY_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterVariableStatisticsFactoryImpl(MAP_FACTORY,
                        COLLECTION_FACTORY, DATA_FACTORY, null));
    }

    @Test
    void testMake() {
        Character character = new CharacterStub();

        CharacterVariableStatistics characterVariableStatistics =
                _characterVariableStatisticsFactory.make(character);

        assertNotNull(characterVariableStatistics);

        String typeId = "typeId";
        CharacterVariableStatisticType type = new CharacterVariableStatisticTypeStub(typeId);
        characterVariableStatistics.add(type);

        CharacterVariableStatistic characterVariableStatistic =
                characterVariableStatistics.get(type);

        assertNotNull(characterVariableStatistic);
        assertSame(character,
                ((CharacterVariableStatisticStub)characterVariableStatistic)._character);
        assertEquals(type,
                ((CharacterVariableStatisticStub)characterVariableStatistic)._type);
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _characterVariableStatisticsFactory.make(null));
        // TODO: Test with data null
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(CharacterVariableStatisticsFactory.class.getCanonicalName(),
                _characterVariableStatisticsFactory.getInterfaceName());
    }
}

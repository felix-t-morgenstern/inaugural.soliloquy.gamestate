package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.CharacterVariableStatisticsFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistic;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistics;
import soliloquy.specs.gamestate.factories.CharacterVariableStatisticsFactory;
import soliloquy.specs.ruleset.entities.CharacterVariableStatisticType;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CharacterVariableStatisticsFactoryImplTests {
    private final VariableCacheFactory DATA_FACTORY = new FakeVariableCacheFactory();
    private final FakeCharacterVariableStatisticFactory ENTITY_FACTORY =
            new FakeCharacterVariableStatisticFactory();

    private CharacterVariableStatisticsFactory factory;

    @BeforeEach
    void setUp() {
        factory = new CharacterVariableStatisticsFactoryImpl(DATA_FACTORY, ENTITY_FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterVariableStatisticsFactoryImpl(null, ENTITY_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterVariableStatisticsFactoryImpl(DATA_FACTORY, null));
    }

    @Test
    void testMake() {
        Character character = new FakeCharacter();

        CharacterVariableStatistics characterVariableStatistics = factory.make(character);

        assertNotNull(characterVariableStatistics);

        CharacterVariableStatisticType type = mock(CharacterVariableStatisticType.class);
        characterVariableStatistics.add(type);

        CharacterVariableStatistic characterVariableStatistic =
                characterVariableStatistics.get(type);

        assertNotNull(characterVariableStatistic);
        assertSame(character,
                ((FakeCharacterVariableStatistic) characterVariableStatistic)._character);
        assertEquals(type,
                ((FakeCharacterVariableStatistic) characterVariableStatistic)._type);
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> factory.make(null));
        // TODO: Test with data null
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(CharacterVariableStatisticsFactory.class.getCanonicalName(),
                factory.getInterfaceName());
    }
}

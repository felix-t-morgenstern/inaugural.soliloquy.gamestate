package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.CharacterVariableStatisticFactory;
import inaugural.soliloquy.gamestate.test.fakes.FakeCharacter;
import inaugural.soliloquy.gamestate.test.fakes.FakeVariableCacheFactory;
import inaugural.soliloquy.gamestate.test.spydoubles.CharacterStatisticCalculationSpyDouble;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistic;
import soliloquy.specs.gamestate.factories.EntityMemberOfTypeFactory;
import soliloquy.specs.ruleset.entities.CharacterVariableStatisticType;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CharacterVariableStatisticFactoryTests {
    private final FakeVariableCacheFactory DATA_FACTORY = new FakeVariableCacheFactory();
    private final CharacterStatisticCalculationSpyDouble CALCULATION =
            new CharacterStatisticCalculationSpyDouble();
    private final Character CHARACTER = new FakeCharacter();

    @Mock private CharacterVariableStatisticType mockStatType;

    private CharacterVariableStatisticFactory factory;

    @BeforeEach
    void setUp() {
        mockStatType = mock(CharacterVariableStatisticType.class);

        factory = new CharacterVariableStatisticFactory(DATA_FACTORY, CALCULATION);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterVariableStatisticFactory(null, CALCULATION));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterVariableStatisticFactory(DATA_FACTORY, null));
    }

    @Test
    void testMake() {
        CharacterVariableStatistic characterVariableStat =
                factory.make(CHARACTER, mockStatType);

        assertNotNull(characterVariableStat);
        assertSame(mockStatType, characterVariableStat.type());
        assertSame(DATA_FACTORY.Created.get(0), characterVariableStat.data());
        // TODO: Consider some tests of Character assignment via FakeCharacter.delete
    }

    @Test
    void testMakeWithData() {
        VariableCache data = new VariableCacheStub();
        CharacterVariableStatistic characterVariableStat =
                factory.make(CHARACTER, mockStatType, data);

        assertNotNull(characterVariableStat);
        assertSame(mockStatType, characterVariableStat.type());
        assertSame(data, characterVariableStat.data());
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> factory.make(null, mockStatType));
        assertThrows(IllegalArgumentException.class,
                () -> factory.make(CHARACTER, null));
    }

    @Test
    void testCharacterAssignment() {
        CharacterVariableStatistic characterVariableStat =
                factory.make(CHARACTER, mockStatType);

        characterVariableStat.calculate();

        assertSame(CHARACTER, CALCULATION._character);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(EntityMemberOfTypeFactory.class.getCanonicalName() + "<" +
                        CharacterVariableStatisticType.class.getCanonicalName() + "," +
                        CharacterVariableStatistic.class.getCanonicalName() + ">",
                factory.getInterfaceName());
    }
}

package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.CharacterStaticStatisticFactory;
import inaugural.soliloquy.gamestate.test.fakes.FakeCharacter;
import inaugural.soliloquy.gamestate.test.fakes.FakeVariableCacheFactory;
import inaugural.soliloquy.gamestate.test.spydoubles.CharacterStatisticCalculationSpyDouble;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterStatistic;
import soliloquy.specs.gamestate.factories.EntityMemberOfTypeFactory;
import soliloquy.specs.ruleset.entities.CharacterStaticStatisticType;
import soliloquy.specs.ruleset.gameconcepts.CharacterStatisticCalculation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CharacterStaticStatisticFactoryTests {
    private final Character CHARACTER = new FakeCharacter();
    private final FakeVariableCacheFactory DATA_FACTORY = new FakeVariableCacheFactory();
    private final CharacterStatisticCalculation CALCULATION =
            new CharacterStatisticCalculationSpyDouble();

    @Mock private CharacterStaticStatisticType mockStatType;

    private EntityMemberOfTypeFactory<CharacterStaticStatisticType,
            CharacterStatistic<CharacterStaticStatisticType>, Character> factory;

    @BeforeEach
    void setUp() {
        mockStatType = mock(CharacterStaticStatisticType.class);

        factory = new CharacterStaticStatisticFactory(DATA_FACTORY, CALCULATION);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterStaticStatisticFactory(null, CALCULATION));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterStaticStatisticFactory(DATA_FACTORY, null));
    }

    @Test
    void testMake() {
        CharacterStatistic<CharacterStaticStatisticType> staticStat =
                factory.make(CHARACTER, mockStatType);

        assertNotNull(staticStat);
        assertSame(mockStatType, staticStat.type());
        assertSame(DATA_FACTORY.Created.get(0), staticStat.data());
    }

    @Test
    void testMakeWithData() {
        VariableCache data = new VariableCacheStub();
        CharacterStatistic<CharacterStaticStatisticType> staticStat =
                factory.make(CHARACTER, mockStatType, data);

        assertNotNull(staticStat);
        assertSame(mockStatType, staticStat.type());
        assertSame(data, staticStat.data());
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> factory.make(null, mockStatType));
        assertThrows(IllegalArgumentException.class, () -> factory.make(CHARACTER, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(EntityMemberOfTypeFactory.class.getCanonicalName() + "<" +
                        CharacterStaticStatisticType.class.getCanonicalName() + "," +
                        CharacterStatistic.class.getCanonicalName() + "<" +
                        CharacterStaticStatisticType.class.getCanonicalName() + ">>",
                factory.getInterfaceName());
    }
}

package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.CharacterStaticStatisticFactory;
import inaugural.soliloquy.gamestate.test.fakes.FakeCharacter;
import inaugural.soliloquy.gamestate.test.fakes.FakeCharacterStaticStatisticType;
import inaugural.soliloquy.gamestate.test.fakes.FakeVariableCacheFactory;
import inaugural.soliloquy.gamestate.test.spydoubles.CharacterStatisticCalculationSpyDouble;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterStatistic;
import soliloquy.specs.gamestate.factories.EntityMemberOfTypeFactory;
import soliloquy.specs.ruleset.entities.CharacterStaticStatisticType;
import soliloquy.specs.ruleset.gameconcepts.CharacterStatisticCalculation;

import static org.junit.jupiter.api.Assertions.*;

class CharacterStaticStatisticFactoryTests {
    private final Character CHARACTER = new FakeCharacter();
    private final CharacterStaticStatisticType TYPE = new FakeCharacterStaticStatisticType();
    private final FakeVariableCacheFactory DATA_FACTORY = new FakeVariableCacheFactory();
    private final CharacterStatisticCalculation CALCULATION =
            new CharacterStatisticCalculationSpyDouble();

    private EntityMemberOfTypeFactory<CharacterStaticStatisticType,
            CharacterStatistic<CharacterStaticStatisticType>, Character>
            _characterStaticStatisticFactory;

    @BeforeEach
    void setUp() {
        _characterStaticStatisticFactory = new CharacterStaticStatisticFactory(DATA_FACTORY,
                CALCULATION);
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
                _characterStaticStatisticFactory.make(CHARACTER, TYPE);

        assertNotNull(staticStat);
        assertSame(TYPE, staticStat.type());
        assertSame(DATA_FACTORY.Created.get(0), staticStat.data());
    }

    @Test
    void testMakeWithData() {
        VariableCache data = new VariableCacheStub();
        CharacterStatistic<CharacterStaticStatisticType> staticStat =
                _characterStaticStatisticFactory.make(CHARACTER, TYPE, data);

        assertNotNull(staticStat);
        assertSame(TYPE, staticStat.type());
        assertSame(data, staticStat.data());
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _characterStaticStatisticFactory.make(null, TYPE));
        assertThrows(IllegalArgumentException.class,
                () -> _characterStaticStatisticFactory.make(CHARACTER, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(EntityMemberOfTypeFactory.class.getCanonicalName() + "<" +
                        CharacterStaticStatisticType.class.getCanonicalName() + "," +
                        CharacterStatistic.class.getCanonicalName() + "<" +
                        CharacterStaticStatisticType.class.getCanonicalName() + ">>",
                _characterStaticStatisticFactory.getInterfaceName());
    }
}

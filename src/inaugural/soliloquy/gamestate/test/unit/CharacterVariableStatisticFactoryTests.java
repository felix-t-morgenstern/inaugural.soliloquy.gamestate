package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterVariableStatisticFactory;
import inaugural.soliloquy.gamestate.test.fakes.*;
import inaugural.soliloquy.gamestate.test.spydoubles.CharacterStatisticCalculationSpyDouble;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistic;
import soliloquy.specs.gamestate.factories.CharacterEntityOfTypeFactory;
import soliloquy.specs.ruleset.entities.CharacterVariableStatisticType;
import soliloquy.specs.ruleset.gameconcepts.CharacterStatisticCalculation;

import static org.junit.jupiter.api.Assertions.*;

class CharacterVariableStatisticFactoryTests {
    private final FakeVariableCacheFactory DATA_FACTORY = new FakeVariableCacheFactory();
    private final CharacterStatisticCalculationSpyDouble CALCULATION =
            new CharacterStatisticCalculationSpyDouble();
    private final Character CHARACTER = new FakeCharacter();
    private final CharacterVariableStatisticType TYPE =
            new FakeCharacterVariableStatisticType("type");

    private CharacterVariableStatisticFactory _characterVariableStatisticFactory;

    @BeforeEach
    void setUp() {
        _characterVariableStatisticFactory =
                new CharacterVariableStatisticFactory(DATA_FACTORY, CALCULATION);
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
                _characterVariableStatisticFactory.make(CHARACTER, TYPE);

        assertNotNull(characterVariableStat);
        assertSame(TYPE, characterVariableStat.type());
        assertSame(DATA_FACTORY.Created.get(0), characterVariableStat.data());
        // TODO: Consider some tests of Character assignment via FakeCharacter.delete
    }

    @Test
    void testMakeWithData() {
        VariableCache data = new VariableCacheStub();
        CharacterVariableStatistic characterVariableStat =
                _characterVariableStatisticFactory.make(CHARACTER, TYPE, data);

        assertNotNull(characterVariableStat);
        assertSame(TYPE, characterVariableStat.type());
        assertSame(data, characterVariableStat.data());
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _characterVariableStatisticFactory.make(null, TYPE));
        assertThrows(IllegalArgumentException.class,
                () -> _characterVariableStatisticFactory.make(CHARACTER, null));
    }

    @Test
    void testCharacterAssignment() {
        CharacterVariableStatistic characterVariableStat =
                _characterVariableStatisticFactory.make(CHARACTER, TYPE);

        characterVariableStat.calculate();

        assertSame(CHARACTER, CALCULATION._character);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(CharacterEntityOfTypeFactory.class.getCanonicalName() + "<" +
                CharacterVariableStatisticType.class.getCanonicalName() + "," +
                CharacterVariableStatistic.class.getCanonicalName() + ">",
                _characterVariableStatisticFactory.getInterfaceName());
    }
}

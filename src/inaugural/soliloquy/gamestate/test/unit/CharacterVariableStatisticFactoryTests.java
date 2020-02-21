package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterVariableStatisticFactory;
import inaugural.soliloquy.gamestate.test.stubs.CharacterVariableStatisticTypeStub;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStatisticCalculationStub;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistic;
import soliloquy.specs.gamestate.factories.CharacterEntityOfTypeFactory;
import soliloquy.specs.ruleset.entities.CharacterVariableStatisticType;
import soliloquy.specs.ruleset.gameconcepts.CharacterStatisticCalculation;

import static org.junit.jupiter.api.Assertions.*;

class CharacterVariableStatisticFactoryTests {
    private final CharacterStatisticCalculation CALCULATION =
            new CharacterStatisticCalculationStub();
    private final Character CHARACTER = new CharacterStub();
    private final CharacterVariableStatisticType TYPE =
            new CharacterVariableStatisticTypeStub("type");

    private CharacterVariableStatisticFactory _characterVariableStatisticFactory;

    @BeforeEach
    void setUp() {
        _characterVariableStatisticFactory =
                new CharacterVariableStatisticFactory(CALCULATION);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterVariableStatisticFactory(null));
    }

    @Test
    void testMake() {
        CharacterVariableStatistic characterVariableStat =
                _characterVariableStatisticFactory.make(CHARACTER, TYPE);

        assertNotNull(characterVariableStat);
        assertSame(TYPE, characterVariableStat.type());
        // TODO: Consider some tests of Character assignment via CharacterStub.delete
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _characterVariableStatisticFactory.make(null, TYPE));
        assertThrows(IllegalArgumentException.class,
                () -> _characterVariableStatisticFactory.make(CHARACTER, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(CharacterEntityOfTypeFactory.class.getCanonicalName() + "<" +
                CharacterVariableStatisticType.class.getCanonicalName() + "," +
                CharacterVariableStatistic.class.getCanonicalName() + ">",
                _characterVariableStatisticFactory.getInterfaceName());
    }
}

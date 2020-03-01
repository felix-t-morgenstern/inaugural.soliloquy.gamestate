package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterVariableStatisticFactory;
import inaugural.soliloquy.gamestate.test.stubs.*;
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
    private final VariableCacheFactoryStub DATA_FACTORY = new VariableCacheFactoryStub();
    private final CharacterStatisticCalculation CALCULATION =
            new CharacterStatisticCalculationStub();
    private final Character CHARACTER = new CharacterStub();
    private final CharacterVariableStatisticType TYPE =
            new CharacterVariableStatisticTypeStub("type");

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
        // TODO: Consider some tests of Character assignment via CharacterStub.delete
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
    void testGetInterfaceName() {
        assertEquals(CharacterEntityOfTypeFactory.class.getCanonicalName() + "<" +
                CharacterVariableStatisticType.class.getCanonicalName() + "," +
                CharacterVariableStatistic.class.getCanonicalName() + ">",
                _characterVariableStatisticFactory.getInterfaceName());
    }
}

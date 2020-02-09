package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterDepletableStatisticFactory;
import inaugural.soliloquy.gamestate.test.stubs.CharacterDepletableStatisticTypeStub;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStatisticCalculationStub;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterDepletableStatistic;
import soliloquy.specs.gamestate.factories.CharacterEntityOfTypeFactory;
import soliloquy.specs.ruleset.entities.CharacterDepletableStatisticType;
import soliloquy.specs.ruleset.gameconcepts.CharacterStatisticCalculation;

import static org.junit.jupiter.api.Assertions.*;

class CharacterDepletableStatisticFactoryTests {
    private final CharacterStatisticCalculation CALCULATION =
            new CharacterStatisticCalculationStub();
    private final Character CHARACTER = new CharacterStub();
    private final CharacterDepletableStatisticType TYPE =
            new CharacterDepletableStatisticTypeStub("type");

    private CharacterDepletableStatisticFactory _characterDepletableStatisticFactory;

    @BeforeEach
    void setUp() {
        _characterDepletableStatisticFactory =
                new CharacterDepletableStatisticFactory(CALCULATION);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterDepletableStatisticFactory(null));
    }

    @Test
    void testMake() {
        CharacterDepletableStatistic characterDepletableStat =
                _characterDepletableStatisticFactory.make(CHARACTER, TYPE);

        assertNotNull(characterDepletableStat);
        assertSame(TYPE, characterDepletableStat.type());
        // TODO: Consider some tests of Character assignment via CharacterStub.delete
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _characterDepletableStatisticFactory.make(null, TYPE));
        assertThrows(IllegalArgumentException.class,
                () -> _characterDepletableStatisticFactory.make(CHARACTER, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(CharacterEntityOfTypeFactory.class.getCanonicalName() + "<" +
                CharacterDepletableStatisticType.class.getCanonicalName() + "," +
                CharacterDepletableStatistic.class.getCanonicalName() + ">",
                _characterDepletableStatisticFactory.getInterfaceName());
    }
}

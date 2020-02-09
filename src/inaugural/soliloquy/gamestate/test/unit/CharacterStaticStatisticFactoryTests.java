package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterStaticStatisticFactory;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStaticStatisticTypeStub;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStatisticCalculationStub;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterStatistic;
import soliloquy.specs.gamestate.factories.CharacterEntityOfTypeFactory;
import soliloquy.specs.ruleset.entities.CharacterStaticStatisticType;
import soliloquy.specs.ruleset.gameconcepts.CharacterStatisticCalculation;

import static org.junit.jupiter.api.Assertions.*;

class CharacterStaticStatisticFactoryTests {
    private final Character CHARACTER = new CharacterStub();
    private final CharacterStaticStatisticType TYPE = new CharacterStaticStatisticTypeStub();
    private final CharacterStatisticCalculation CALCULATION =
            new CharacterStatisticCalculationStub();

    private CharacterEntityOfTypeFactory<CharacterStaticStatisticType,
            CharacterStatistic<CharacterStaticStatisticType>>
        _characterStaticStatisticFactory;

    @BeforeEach
    void setUp() {
        _characterStaticStatisticFactory = new CharacterStaticStatisticFactory(CALCULATION);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterStaticStatisticFactory(null));
    }

    @Test
    void testMake() {
        CharacterStatistic<CharacterStaticStatisticType> staticStat =
                _characterStaticStatisticFactory.make(CHARACTER, TYPE);

        assertNotNull(staticStat);
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
        assertEquals(CharacterEntityOfTypeFactory.class.getCanonicalName() + "<" +
                CharacterStaticStatisticType.class.getCanonicalName() + "," +
                CharacterStatistic.class.getCanonicalName() + "<" +
                CharacterStaticStatisticType.class.getCanonicalName() + ">>",
                _characterStaticStatisticFactory.getInterfaceName());
    }
}

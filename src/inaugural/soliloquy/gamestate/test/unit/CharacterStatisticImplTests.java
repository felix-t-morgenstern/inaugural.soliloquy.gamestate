package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterStatisticImpl;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStatisticCalculationStub;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStaticStatisticTypeStub;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterStatistic;
import soliloquy.specs.ruleset.entities.CharacterStatisticType;
import soliloquy.specs.ruleset.gameconcepts.CharacterStatisticCalculation;

import static org.junit.jupiter.api.Assertions.*;

class CharacterStatisticImplTests {
    private final Character CHARACTER = new CharacterStub();
    private final CharacterStatisticType ATTRIBUTE_TYPE = new CharacterStaticStatisticTypeStub();
    private final CharacterStatisticCalculation CHARACTER_ATTRIBUTE_CALCULATION =
            new CharacterStatisticCalculationStub();

    private CharacterStatistic _characterStatistic;

    @BeforeEach
    void setUp() {
        _characterStatistic = new CharacterStatisticImpl(CHARACTER, ATTRIBUTE_TYPE,
                CHARACTER_ATTRIBUTE_CALCULATION);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new CharacterStatisticImpl(null,
                ATTRIBUTE_TYPE, CHARACTER_ATTRIBUTE_CALCULATION));
        assertThrows(IllegalArgumentException.class, () -> new CharacterStatisticImpl(CHARACTER,
                null, CHARACTER_ATTRIBUTE_CALCULATION));
        assertThrows(IllegalArgumentException.class, () -> new CharacterStatisticImpl(CHARACTER,
                ATTRIBUTE_TYPE, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(CharacterStatistic.class.getCanonicalName(),
                _characterStatistic.getInterfaceName());
    }

    @Test
    void testAptitudeType() {
        assertEquals(ATTRIBUTE_TYPE, _characterStatistic.type());
    }

    @Test
    void testCalculateValue() {
        _characterStatistic.calculateValue();

        assertSame(CHARACTER, CharacterStatisticCalculationStub._character);
        assertSame(ATTRIBUTE_TYPE, CharacterStatisticCalculationStub._statisticType);
        assertEquals(CharacterStatisticCalculationStub.VALUE, _characterStatistic.totalValue());
        assertEquals(CharacterStatisticCalculationStub.MODIFIERS,
                _characterStatistic.modifiersRepresentation());
        assertEquals(CharacterStatisticCalculationStub.MODIFIERS.size(),
                _characterStatistic.modifiersRepresentation().size());
        CharacterStatisticCalculationStub.MODIFIERS.forEach(
                _characterStatistic.modifiersRepresentation()::contains);
    }

    @Test
    void testThrowsIllegalStateExceptionForDeadCharacter() {
        CHARACTER.delete();

        assertThrows(IllegalStateException.class, () -> _characterStatistic.type());
        assertThrows(IllegalStateException.class, () -> _characterStatistic.getInterfaceName());
        assertThrows(IllegalStateException.class, () -> _characterStatistic.calculateValue());
        assertThrows(IllegalStateException.class, () -> _characterStatistic.modifiersRepresentation());
        assertThrows(IllegalStateException.class, () -> _characterStatistic.totalValue());
    }

    @Test
    void testEnforceDeletionInvariant() {
        _characterStatistic.delete();

        assertThrows(IllegalStateException.class, () -> _characterStatistic.type());
        assertThrows(IllegalStateException.class, () -> _characterStatistic.getInterfaceName());
        assertThrows(IllegalStateException.class, () -> _characterStatistic.calculateValue());
        assertThrows(IllegalStateException.class, () -> _characterStatistic.modifiersRepresentation());
        assertThrows(IllegalStateException.class, () -> _characterStatistic.totalValue());
    }
}

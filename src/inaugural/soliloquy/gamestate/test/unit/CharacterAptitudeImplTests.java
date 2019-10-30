package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterAptitudeImpl;
import inaugural.soliloquy.gamestate.test.stubs.AptitudeCalculationStub;
import inaugural.soliloquy.gamestate.test.stubs.AptitudeTypeStub;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterAptitude;
import soliloquy.specs.ruleset.entities.AptitudeType;
import soliloquy.specs.ruleset.gameconcepts.CharacterStatisticCalculation;

import static org.junit.jupiter.api.Assertions.*;

class CharacterAptitudeImplTests {
    private final Character CHARACTER = new CharacterStub();
    private final AptitudeType APTITUDE_TYPE = new AptitudeTypeStub();
    private final CharacterStatisticCalculation<AptitudeType> CHARACTER_APTITUDE_CALCULATION =
            new AptitudeCalculationStub();

    private CharacterAptitude _characterAptitude;

    @BeforeEach
    void setUp() {
        _characterAptitude = new CharacterAptitudeImpl(CHARACTER, APTITUDE_TYPE,
                CHARACTER_APTITUDE_CALCULATION);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new CharacterAptitudeImpl(null,
                APTITUDE_TYPE, CHARACTER_APTITUDE_CALCULATION));
        assertThrows(IllegalArgumentException.class, () -> new CharacterAptitudeImpl(CHARACTER,
                null, CHARACTER_APTITUDE_CALCULATION));
        assertThrows(IllegalArgumentException.class, () -> new CharacterAptitudeImpl(CHARACTER,
                APTITUDE_TYPE, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(CharacterAptitude.class.getCanonicalName(),
                _characterAptitude.getInterfaceName());
    }

    @Test
    void testAptitudeType() {
        assertEquals(APTITUDE_TYPE, _characterAptitude.aptitudeType());
    }

    @Test
    void testCalculateValue() {
        _characterAptitude.calculateValue();

        assertSame(CHARACTER, AptitudeCalculationStub._character);
        assertSame(APTITUDE_TYPE, AptitudeCalculationStub._aptitudeType);
        assertEquals(AptitudeCalculationStub.VALUE, _characterAptitude.totalValue());
        assertEquals(AptitudeCalculationStub.MODIFIERS,
                _characterAptitude.modifiersRepresentation());
        assertEquals(AptitudeCalculationStub.MODIFIERS.size(),
                _characterAptitude.modifiersRepresentation().size());
        AptitudeCalculationStub.MODIFIERS.forEach(
                _characterAptitude.modifiersRepresentation()::contains);
    }

    @Test
    void testThrowsIllegalStateExceptionForDeadCharacter() {
        CHARACTER.delete();

        assertThrows(IllegalStateException.class, () -> _characterAptitude.aptitudeType());
        assertThrows(IllegalStateException.class, () -> _characterAptitude.getInterfaceName());
        assertThrows(IllegalStateException.class, () -> _characterAptitude.calculateValue());
        assertThrows(IllegalStateException.class, () -> _characterAptitude.modifiersRepresentation());
        assertThrows(IllegalStateException.class, () -> _characterAptitude.totalValue());
        assertThrows(IllegalStateException.class, () -> _characterAptitude.delete());
    }

    @Test
    void testEnforceDeletionInvariant() {
        _characterAptitude.delete();

        assertThrows(IllegalStateException.class, () -> _characterAptitude.aptitudeType());
        assertThrows(IllegalStateException.class, () -> _characterAptitude.getInterfaceName());
        assertThrows(IllegalStateException.class, () -> _characterAptitude.calculateValue());
        assertThrows(IllegalStateException.class, () -> _characterAptitude.modifiersRepresentation());
        assertThrows(IllegalStateException.class, () -> _characterAptitude.totalValue());
        assertThrows(IllegalStateException.class, () -> _characterAptitude.delete());
    }
}

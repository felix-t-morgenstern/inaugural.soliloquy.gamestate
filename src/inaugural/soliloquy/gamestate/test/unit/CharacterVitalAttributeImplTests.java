package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterVitalAttributeImpl;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStub;
import inaugural.soliloquy.gamestate.test.stubs.VitalAttributeCalculationStub;
import inaugural.soliloquy.gamestate.test.stubs.VitalAttributeTypeStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterVitalAttribute;
import soliloquy.specs.ruleset.entities.VitalAttributeType;
import soliloquy.specs.ruleset.gameconcepts.CharacterStatisticCalculation;

import static org.junit.jupiter.api.Assertions.*;

class CharacterVitalAttributeImplTests {
    private CharacterVitalAttribute _characterVitalAttribute;

    private final Character CHARACTER = new CharacterStub();
    private final VitalAttributeType VITAL_ATTRIBUTE_TYPE = new VitalAttributeTypeStub();
    private final CharacterStatisticCalculation<VitalAttributeType> VITAL_ATTRIBUTE_CALCULATION =
            new VitalAttributeCalculationStub();

    @BeforeEach
    void setUp() {
        _characterVitalAttribute = new CharacterVitalAttributeImpl(CHARACTER, VITAL_ATTRIBUTE_TYPE,
                VITAL_ATTRIBUTE_CALCULATION);
    }

    // TODO: Test constructor with invalid params

    @Test
    void testGetInterfaceName() {
        assertEquals(CharacterVitalAttribute.class.getCanonicalName(),
                _characterVitalAttribute.getInterfaceName());
    }

    @Test
    void testVitalAttributeType() {
        assertSame(VITAL_ATTRIBUTE_TYPE, _characterVitalAttribute.vitalAttributeType());
    }

    @Test
    void testSetAndGetCurrentValue() {
        _characterVitalAttribute.setCurrentValue(123);

        assertEquals(123, _characterVitalAttribute.getCurrentValue());
    }

    @Test
    void testCalculateValue() {
        _characterVitalAttribute.calculateValue();

        assertSame(CHARACTER, VitalAttributeCalculationStub._character);
        assertSame(VITAL_ATTRIBUTE_TYPE, VitalAttributeCalculationStub._vitalAttributeType);
        assertEquals(VitalAttributeCalculationStub.VALUE, _characterVitalAttribute.totalValue());
        assertEquals(VitalAttributeCalculationStub.MODIFIERS,
                _characterVitalAttribute.modifiersRepresentation());
        assertEquals(VitalAttributeCalculationStub.MODIFIERS.size(),
                _characterVitalAttribute.modifiersRepresentation().size());
        VitalAttributeCalculationStub.MODIFIERS.forEach(
                _characterVitalAttribute.modifiersRepresentation()::contains);
    }

    @Test
    void testThrowsIllegalStateExceptionForDeadCharacter() {
        CHARACTER.delete();

        assertThrows(IllegalStateException.class, () -> _characterVitalAttribute.getCurrentValue());
        assertThrows(IllegalStateException.class, () -> _characterVitalAttribute.setCurrentValue(0));
        assertThrows(IllegalStateException.class, () -> _characterVitalAttribute.setCurrentValue(0));
        assertThrows(IllegalStateException.class, () -> _characterVitalAttribute.vitalAttributeType());
        assertThrows(IllegalStateException.class, () -> _characterVitalAttribute.getInterfaceName());
        assertThrows(IllegalStateException.class, () -> _characterVitalAttribute.calculateValue());
        assertThrows(IllegalStateException.class, () -> _characterVitalAttribute.modifiersRepresentation());
        assertThrows(IllegalStateException.class, () -> _characterVitalAttribute.totalValue());
        assertThrows(IllegalStateException.class, () -> _characterVitalAttribute.delete());
    }

    @Test
    void testEnforceDeletionInvariant() {
        _characterVitalAttribute.delete();

        assertThrows(IllegalStateException.class, () -> _characterVitalAttribute.getCurrentValue());
        assertThrows(IllegalStateException.class, () -> _characterVitalAttribute.setCurrentValue(0));
        assertThrows(IllegalStateException.class, () -> _characterVitalAttribute.setCurrentValue(0));
        assertThrows(IllegalStateException.class, () -> _characterVitalAttribute.vitalAttributeType());
        assertThrows(IllegalStateException.class, () -> _characterVitalAttribute.getInterfaceName());
        assertThrows(IllegalStateException.class, () -> _characterVitalAttribute.calculateValue());
        assertThrows(IllegalStateException.class, () -> _characterVitalAttribute.modifiersRepresentation());
        assertThrows(IllegalStateException.class, () -> _characterVitalAttribute.totalValue());
        assertThrows(IllegalStateException.class, () -> _characterVitalAttribute.delete());
    }
}

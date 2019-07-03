package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterVitalAttribute;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStub;
import inaugural.soliloquy.gamestate.test.stubs.VitalAttributeCalculationStub;
import inaugural.soliloquy.gamestate.test.stubs.VitalAttributeTypeStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.entities.ICharacter;
import soliloquy.specs.gamestate.entities.ICharacterVitalAttribute;
import soliloquy.specs.ruleset.entities.IVitalAttributeType;
import soliloquy.specs.ruleset.gameconcepts.IVitalAttributeCalculation;

import static org.junit.jupiter.api.Assertions.*;

class CharacterVitalAttributeTests {
    private ICharacterVitalAttribute _characterVitalAttribute;

    private final ICharacter CHARACTER = new CharacterStub();
    private final IVitalAttributeType VITAL_ATTRIBUTE_TYPE = new VitalAttributeTypeStub();
    private final IVitalAttributeCalculation VITAL_ATTRIBUTE_CALCULATION =
            new VitalAttributeCalculationStub();

    @BeforeEach
    void setUp() {
        _characterVitalAttribute = new CharacterVitalAttribute(CHARACTER, VITAL_ATTRIBUTE_TYPE,
                VITAL_ATTRIBUTE_CALCULATION);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(ICharacterVitalAttribute.class.getCanonicalName(),
                _characterVitalAttribute.getInterfaceName());
    }

    @Test
    void testVitalAttributeType() {
        assertSame(VITAL_ATTRIBUTE_TYPE, _characterVitalAttribute.vitalAttributeType());
    }

    @Test
    void testCharacter() {
        assertSame(CHARACTER, _characterVitalAttribute.character());
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
        // TODO: Ensure that this test is rigorous enough, perhaps by guaranteeing map contents
        assertEquals(VitalAttributeCalculationStub.MODIFIERS,
                _characterVitalAttribute.modifiersRepresentation());
    }

    @Test
    void testThrowsIllegalStateExceptionForDeadCharacter() {
        CHARACTER.delete();

        assertThrows(IllegalStateException.class, () -> _characterVitalAttribute.character());
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

        assertThrows(IllegalStateException.class, () -> _characterVitalAttribute.character());
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

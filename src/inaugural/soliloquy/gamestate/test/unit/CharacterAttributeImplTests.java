package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterAttributeImpl;
import inaugural.soliloquy.gamestate.test.stubs.AttributeCalculationStub;
import inaugural.soliloquy.gamestate.test.stubs.AttributeTypeStub;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterAttribute;
import soliloquy.specs.ruleset.entities.AttributeType;
import soliloquy.specs.ruleset.gameconcepts.CharacterStatisticCalculation;

import static org.junit.jupiter.api.Assertions.*;

class CharacterAttributeImplTests {
    private final Character CHARACTER = new CharacterStub();
    private final AttributeType ATTRIBUTE_TYPE = new AttributeTypeStub();
    private final CharacterStatisticCalculation<AttributeType> CHARACTER_ATTRIBUTE_CALCULATION =
            new AttributeCalculationStub();

    private CharacterAttribute _characterAttribute;

    @BeforeEach
    void setUp() {
        _characterAttribute = new CharacterAttributeImpl(CHARACTER, ATTRIBUTE_TYPE,
                CHARACTER_ATTRIBUTE_CALCULATION);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new CharacterAttributeImpl(null,
                ATTRIBUTE_TYPE, CHARACTER_ATTRIBUTE_CALCULATION));
        assertThrows(IllegalArgumentException.class, () -> new CharacterAttributeImpl(CHARACTER,
                null, CHARACTER_ATTRIBUTE_CALCULATION));
        assertThrows(IllegalArgumentException.class, () -> new CharacterAttributeImpl(CHARACTER,
                ATTRIBUTE_TYPE, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(CharacterAttribute.class.getCanonicalName(),
                _characterAttribute.getInterfaceName());
    }

    @Test
    void testAptitudeType() {
        assertEquals(ATTRIBUTE_TYPE, _characterAttribute.attributeType());
    }

    @Test
    void testCalculateValue() {
        _characterAttribute.calculateValue();

        assertSame(CHARACTER, AttributeCalculationStub._character);
        assertSame(ATTRIBUTE_TYPE, AttributeCalculationStub._attributeType);
        assertEquals(AttributeCalculationStub.VALUE, _characterAttribute.totalValue());
        assertEquals(AttributeCalculationStub.MODIFIERS,
                _characterAttribute.modifiersRepresentation());
        assertEquals(AttributeCalculationStub.MODIFIERS.size(),
                _characterAttribute.modifiersRepresentation().size());
        AttributeCalculationStub.MODIFIERS.forEach(
                _characterAttribute.modifiersRepresentation()::contains);
    }

    @Test
    void testThrowsIllegalStateExceptionForDeadCharacter() {
        CHARACTER.delete();

        assertThrows(IllegalStateException.class, () -> _characterAttribute.attributeType());
        assertThrows(IllegalStateException.class, () -> _characterAttribute.getInterfaceName());
        assertThrows(IllegalStateException.class, () -> _characterAttribute.calculateValue());
        assertThrows(IllegalStateException.class, () -> _characterAttribute.modifiersRepresentation());
        assertThrows(IllegalStateException.class, () -> _characterAttribute.totalValue());
        assertThrows(IllegalStateException.class, () -> _characterAttribute.delete());
    }

    @Test
    void testEnforceDeletionInvariant() {
        _characterAttribute.delete();

        assertThrows(IllegalStateException.class, () -> _characterAttribute.attributeType());
        assertThrows(IllegalStateException.class, () -> _characterAttribute.getInterfaceName());
        assertThrows(IllegalStateException.class, () -> _characterAttribute.calculateValue());
        assertThrows(IllegalStateException.class, () -> _characterAttribute.modifiersRepresentation());
        assertThrows(IllegalStateException.class, () -> _characterAttribute.totalValue());
        assertThrows(IllegalStateException.class, () -> _characterAttribute.delete());
    }
}

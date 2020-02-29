package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterAbilityImpl;
import inaugural.soliloquy.gamestate.test.stubs.AbilityTypeStub;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterAbility;
import soliloquy.specs.ruleset.entities.abilities.AbilityType;

import static org.junit.jupiter.api.Assertions.*;

class CharacterAbilityImplTests {
    private CharacterAbility<AbilityTypeStub> _characterAbility;
    private final AbilityTypeStub ABILITY_TYPE = new AbilityTypeStub();

    private final Character CHARACTER = new CharacterStub();

    @BeforeEach
    void setUp() {
        _characterAbility = new CharacterAbilityImpl<>(CHARACTER, ABILITY_TYPE);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new CharacterAbilityImpl<>(null,
                ABILITY_TYPE));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterAbilityImpl<AbilityTypeStub>(CHARACTER, null));
    }

    @Test
    void testSetAndGetIsHidden() {
        _characterAbility.setIsHidden(true);
        assertTrue(_characterAbility.getIsHidden());
        _characterAbility.setIsHidden(false);
        assertFalse(_characterAbility.getIsHidden());
    }

    @Test
    void testSetAndGetIsDisabled() {
        _characterAbility.setIsDisabled(true);
        assertTrue(_characterAbility.getIsDisabled());
        _characterAbility.setIsDisabled(false);
        assertFalse(_characterAbility.getIsDisabled());
    }

    @Test
    void testAbilityType() {
        assertSame(_characterAbility.type(), ABILITY_TYPE);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(CharacterAbility.class.getCanonicalName() + "<" +
                AbilityType.class.getCanonicalName() + ">",
                    _characterAbility.getInterfaceName());
    }

    @Test
    void testCharacterDeletedInvariant() {
        CHARACTER.delete();

        assertThrows(IllegalStateException.class, () -> _characterAbility.getIsHidden());
        assertThrows(IllegalStateException.class, () -> _characterAbility.setIsHidden(false));
        assertThrows(IllegalStateException.class, () -> _characterAbility.getIsDisabled());
        assertThrows(IllegalStateException.class, () -> _characterAbility.setIsDisabled(false));
        assertThrows(IllegalStateException.class, () -> _characterAbility.type());
        assertThrows(IllegalStateException.class, () -> _characterAbility.getInterfaceName());
    }
}

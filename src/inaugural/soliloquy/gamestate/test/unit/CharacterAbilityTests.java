package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterAbility;
import inaugural.soliloquy.gamestate.test.stubs.AbilityTypeStub;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.entities.ICharacter;
import soliloquy.specs.gamestate.entities.ICharacterAbility;
import soliloquy.specs.ruleset.entities.abilities.IAbilityType;

import static org.junit.jupiter.api.Assertions.*;

class CharacterAbilityTests {
    private ICharacterAbility _characterAbility;
    private IAbilityType _abilityType;

    private final ICharacter CHARACTER = new CharacterStub();

    @BeforeEach
    void setUp() {
        _abilityType = new AbilityTypeStub();
        _characterAbility = new CharacterAbility(false, false, CHARACTER, _abilityType);
    }

    @Test
    void testSetAndGetIsHidden() {
        _characterAbility.setIsHidden(true);
        assertTrue(_characterAbility.getIsHidden());
        _characterAbility.setIsHidden(false);
        assertTrue(!_characterAbility.getIsHidden());
    }

    @Test
    void testSetAndGetIsDisabled() {
        _characterAbility.setIsDisabled(true);
        assertTrue(_characterAbility.getIsDisabled());
        _characterAbility.setIsDisabled(false);
        assertTrue(!_characterAbility.getIsDisabled());
    }

    @Test
    void testAbilityType() {
        assertSame(_characterAbility.abilityType(), _abilityType);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(ICharacterAbility.class.getCanonicalName(), _characterAbility.getInterfaceName());
    }

    @Test
    void testCharacterDeletedInvariant() {
        CHARACTER.delete();

        assertThrows(IllegalStateException.class, () -> _characterAbility.getIsHidden());
        assertThrows(IllegalStateException.class, () -> _characterAbility.setIsHidden(false));
        assertThrows(IllegalStateException.class, () -> _characterAbility.getIsDisabled());
        assertThrows(IllegalStateException.class, () -> _characterAbility.setIsDisabled(false));
        assertThrows(IllegalStateException.class, () -> _characterAbility.abilityType());
        assertThrows(IllegalStateException.class, () -> _characterAbility.getInterfaceName());
    }
}

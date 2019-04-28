package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterAbility;
import inaugural.soliloquy.gamestate.test.stubs.AbilityTypeStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.gamestate.specs.ICharacterAbility;
import soliloquy.ruleset.gameentities.abilities.specs.IAbilityType;

import static org.junit.jupiter.api.Assertions.*;

class CharacterAbilityTests {
    private ICharacterAbility _characterAbility;
    private IAbilityType _abilityType;

    @BeforeEach
    void setUp() {
        _abilityType = new AbilityTypeStub();
        _characterAbility = new CharacterAbility(false, false, _abilityType);
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
}

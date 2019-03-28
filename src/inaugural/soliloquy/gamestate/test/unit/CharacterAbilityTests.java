package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterAbility;
import inaugural.soliloquy.gamestate.test.stubs.AbilityTypeStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.gamestate.specs.ICharacterAbility;
import soliloquy.ruleset.gameentities.abilities.specs.IAbilityType;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CharacterAbilityTests {
    private ICharacterAbility _characterAbility;
    private IAbilityType _abilityType;

    @BeforeEach
    public void setUp() {
        _abilityType = new AbilityTypeStub();
        _characterAbility = new CharacterAbility(false, false, _abilityType);
    }

    @Test
    public void testSetAndGetIsHidden() {
        _characterAbility.setIsHidden(true);
        assertTrue(_characterAbility.getIsHidden());
        _characterAbility.setIsHidden(false);
        assertTrue(!_characterAbility.getIsHidden());
    }

    @Test
    public void testSetAndGetIsDisabled() {
        _characterAbility.setIsDisabled(true);
        assertTrue(_characterAbility.getIsDisabled());
        _characterAbility.setIsDisabled(false);
        assertTrue(!_characterAbility.getIsDisabled());
    }

    @Test
    public void testAbilityType() {
        assertTrue(_characterAbility.abilityType() == _abilityType);
    }

    @Test
    public void testGetInterfaceName() {
        assertTrue(_characterAbility.getInterfaceName().equals("soliloquy.gamestate.specs.ICharacterAbility"));
    }
}

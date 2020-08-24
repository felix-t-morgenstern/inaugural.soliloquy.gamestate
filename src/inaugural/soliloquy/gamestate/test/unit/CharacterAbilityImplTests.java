package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterAbilityImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeCharacter;
import inaugural.soliloquy.gamestate.test.stubs.AbilityTypeStub;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEntityOfType;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.ruleset.entities.abilities.AbilityType;

import static org.junit.jupiter.api.Assertions.*;

class CharacterAbilityImplTests {
    private CharacterEntityOfType<AbilityTypeStub> _characterAbility;
    private final AbilityTypeStub ABILITY_TYPE = new AbilityTypeStub();
    private final VariableCache DATA = new VariableCacheStub();

    private final Character CHARACTER = new FakeCharacter();

    @BeforeEach
    void setUp() {
        _characterAbility = new CharacterAbilityImpl<>(CHARACTER, ABILITY_TYPE, DATA);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterAbilityImpl<>(null, ABILITY_TYPE, DATA));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterAbilityImpl<AbilityTypeStub>(CHARACTER, null, DATA));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterAbilityImpl<>(CHARACTER, ABILITY_TYPE, null));
    }

    @Test
    void testData() {
        assertSame(DATA, _characterAbility.data());
    }

    @Test
    void testAbilityType() {
        assertSame(_characterAbility.type(), ABILITY_TYPE);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(CharacterEntityOfType.class.getCanonicalName() + "<" +
                AbilityType.class.getCanonicalName() + ">",
                    _characterAbility.getInterfaceName());
    }

    @Test
    void testDeletionInvariant() {
        _characterAbility.delete();

        assertThrows(EntityDeletedException.class, () -> _characterAbility.type());
        assertThrows(EntityDeletedException.class, () -> _characterAbility.data());
        assertThrows(EntityDeletedException.class, () -> _characterAbility.getInterfaceName());
    }

    @Test
    void testCharacterDeletedInvariant() {
        CHARACTER.delete();

        assertThrows(IllegalStateException.class, () -> _characterAbility.data());
        assertThrows(IllegalStateException.class, () -> _characterAbility.type());
        assertThrows(IllegalStateException.class, () -> _characterAbility.getInterfaceName());
    }
}

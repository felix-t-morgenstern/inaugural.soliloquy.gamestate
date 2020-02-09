package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterAbilityFactory;
import inaugural.soliloquy.gamestate.archetypes.ActiveAbilityTypeArchetype;
import inaugural.soliloquy.gamestate.test.stubs.ActiveAbilityTypeStub;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterAbility;
import soliloquy.specs.gamestate.factories.CharacterEntityOfTypeFactory;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbilityType;

import static org.junit.jupiter.api.Assertions.*;

class CharacterAbilityFactoryTests {
    private final Character CHARACTER = new CharacterStub();
    private final ActiveAbilityType TYPE = new ActiveAbilityTypeStub("type");
    private final ActiveAbilityType ARCHETYPE = new ActiveAbilityTypeArchetype();
    private CharacterEntityOfTypeFactory<ActiveAbilityType, CharacterAbility<ActiveAbilityType>>
            _characterAbilityFactory;

    @BeforeEach
    void setUp() {
        _characterAbilityFactory = new CharacterAbilityFactory<>(ARCHETYPE);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new CharacterAbilityFactory<>(null));
    }

    @Test
    void testMake() {
        CharacterAbility<ActiveAbilityType> characterActiveAbility =
                _characterAbilityFactory.make(CHARACTER, TYPE);

        assertNotNull(characterActiveAbility);
        assertSame(TYPE, characterActiveAbility.type());
        // TODO: Consider testing Character assignment via CharacterStub.delete
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _characterAbilityFactory.make(null, TYPE));
        assertThrows(IllegalArgumentException.class,
                () -> _characterAbilityFactory.make(CHARACTER, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(CharacterEntityOfTypeFactory.class.getCanonicalName() + "<" +
                ActiveAbilityType.class.getCanonicalName() + "," +
                CharacterAbility.class.getCanonicalName() + "<" +
                ActiveAbilityType.class.getCanonicalName() + ">>",
                _characterAbilityFactory.getInterfaceName());
    }
}

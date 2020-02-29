package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterAbilityFactory;
import inaugural.soliloquy.gamestate.archetypes.ActiveAbilityTypeArchetype;
import inaugural.soliloquy.gamestate.test.stubs.ActiveAbilityTypeStub;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStub;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheFactoryStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEntityOfType;
import soliloquy.specs.gamestate.factories.CharacterEntityOfTypeFactory;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbilityType;

import static org.junit.jupiter.api.Assertions.*;

class CharacterAbilityFactoryTests {
    private final Character CHARACTER = new CharacterStub();
    private final ActiveAbilityType TYPE = new ActiveAbilityTypeStub("type");
    private final ActiveAbilityType ARCHETYPE = new ActiveAbilityTypeArchetype();
    private final VariableCacheFactory DATA_FACTORY = new VariableCacheFactoryStub();
    private CharacterEntityOfTypeFactory<ActiveAbilityType,
            CharacterEntityOfType<ActiveAbilityType>> _characterAbilityFactory;

    @BeforeEach
    void setUp() {
        _characterAbilityFactory = new CharacterAbilityFactory<>(ARCHETYPE, DATA_FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterAbilityFactory<>(null, DATA_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterAbilityFactory<>(ARCHETYPE, null));
    }

    @Test
    void testMake() {
        CharacterEntityOfType<ActiveAbilityType> characterActiveAbility =
                _characterAbilityFactory.make(CHARACTER, TYPE);

        assertNotNull(characterActiveAbility);
        assertSame(TYPE, characterActiveAbility.type());
        // TODO: Consider testing Character assignment via CharacterStub.delete
    }

    @Test
    void testMakeWithData() {
        fail();
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
                        CharacterEntityOfType.class.getCanonicalName() + "<" +
                        ActiveAbilityType.class.getCanonicalName() + ">>",
                _characterAbilityFactory.getInterfaceName());
    }
}

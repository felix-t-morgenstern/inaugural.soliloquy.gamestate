package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterAbilityFactory;
import inaugural.soliloquy.gamestate.archetypes.ActiveAbilityTypeArchetype;
import inaugural.soliloquy.gamestate.test.fakes.FakeActiveAbilityType;
import inaugural.soliloquy.gamestate.test.fakes.FakeCharacter;
import inaugural.soliloquy.gamestate.test.fakes.FakeVariableCacheFactory;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEntityOfType;
import soliloquy.specs.gamestate.factories.CharacterEntityOfTypeFactory;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbilityType;

import static org.junit.jupiter.api.Assertions.*;

class CharacterAbilityFactoryTests {
    private final Character CHARACTER = new FakeCharacter();
    private final ActiveAbilityType TYPE = new FakeActiveAbilityType("type");
    private final ActiveAbilityType ARCHETYPE = new ActiveAbilityTypeArchetype();
    private final FakeVariableCacheFactory DATA_FACTORY = new FakeVariableCacheFactory();
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
        assertSame(DATA_FACTORY.Created.get(0), characterActiveAbility.data());
        // TODO: Consider testing Character assignment via FakeCharacter.delete
    }

    @Test
    void testMakeWithData() {
        VariableCache data = new VariableCacheStub();
        CharacterEntityOfType<ActiveAbilityType> characterActiveAbility =
                _characterAbilityFactory.make(CHARACTER, TYPE, data);

        assertNotNull(characterActiveAbility);
        assertSame(TYPE, characterActiveAbility.type());
        assertSame(data, characterActiveAbility.data());
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

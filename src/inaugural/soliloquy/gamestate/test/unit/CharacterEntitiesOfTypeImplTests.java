package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.common.test.stubs.HasIdAndNameStub;
import inaugural.soliloquy.gamestate.CharacterEntitiesOfTypeImpl;
import inaugural.soliloquy.gamestate.archetypes.ActiveAbilityTypeArchetype;
import inaugural.soliloquy.gamestate.test.stubs.CharacterAbilityStub;
import inaugural.soliloquy.gamestate.test.stubs.CharacterEntityStub;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStub;
import inaugural.soliloquy.gamestate.test.stubs.CollectionFactoryStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.common.shared.HasId;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterAbility;
import soliloquy.specs.gamestate.entities.CharacterEntitiesOfType;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbilityType;

import java.util.ArrayList;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class CharacterEntitiesOfTypeImplTests {
    private final Character CHARACTER = new CharacterStub();
    private final ArrayList<HasId> TYPES_ADDED = new ArrayList<>();
    private final ArrayList<CharacterEntityStub> ENTITIES_ADDED = new ArrayList<>();
    private final Function<HasId, Function<Character, CharacterEntityStub>> FACTORY = t -> c -> {
        _characterPassedIntoFactory = c;
        TYPES_ADDED.add(t);
        CharacterEntityStub entity = new CharacterEntityStub(c,t);
        ENTITIES_ADDED.add(entity);
        return entity;
    };
    private final CollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    private final CharacterEntityStub ARCHETYPE = new CharacterEntityStub(null,
            new HasIdAndNameStub("id", "name"));

    private Character _characterPassedIntoFactory;
    private CharacterEntitiesOfType<HasId, CharacterEntityStub> _entitiesOfType;

    @BeforeEach
    void setUp() {
        _characterPassedIntoFactory = null;
        _entitiesOfType = new CharacterEntitiesOfTypeImpl<>(CHARACTER, FACTORY,
                COLLECTION_FACTORY, ARCHETYPE);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterEntitiesOfTypeImpl<>(null, FACTORY, COLLECTION_FACTORY,
                        ARCHETYPE));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterEntitiesOfTypeImpl<>(CHARACTER, null, COLLECTION_FACTORY,
                        ARCHETYPE));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterEntitiesOfTypeImpl<>(CHARACTER, FACTORY, null, ARCHETYPE));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterEntitiesOfTypeImpl<>(CHARACTER, FACTORY, COLLECTION_FACTORY,
                        null));
    }

    @SuppressWarnings("unchecked")
    @Test
    void testGetInterfaceName() {
        Function<ActiveAbilityType, Function<Character, CharacterAbility<ActiveAbilityType>>>
                factory = t -> c -> null;
        CharacterAbility<ActiveAbilityType> archetype =
                new CharacterAbilityStub<ActiveAbilityType>(null,
                        new ActiveAbilityTypeArchetype());
        assertEquals(CharacterEntitiesOfType.class.getCanonicalName() + "<" +
                CharacterAbility.class.getCanonicalName() + "<" +
                ActiveAbilityType.class.getCanonicalName() + ">>",
                new CharacterEntitiesOfTypeImpl<>(CHARACTER, factory, new CollectionFactoryStub(),
                        archetype).getInterfaceName());
    }

    @Test
    void testAddAndGet() {
        HasId type = new HasIdAndNameStub("id", "name");

        assertNull(_entitiesOfType.get(type));

        _entitiesOfType.add(type);

        assertEquals(1, TYPES_ADDED.size());
        assertTrue(TYPES_ADDED.contains(type));
        assertNotNull(_entitiesOfType.get(type));
        assertSame(CHARACTER, _characterPassedIntoFactory);
    }

    @Test
    void testAddExistingTypeIsNondestructive() {
        HasId type = new HasIdAndNameStub("id", "name");

        _entitiesOfType.add(type);

        CharacterEntityStub entity = _entitiesOfType.get(type);

        _entitiesOfType.add(type);

        CharacterEntityStub entityAfterSecondAdd = _entitiesOfType.get(type);

        assertSame(entity, entityAfterSecondAdd);
    }

    @Test
    void testSize() {
        _entitiesOfType.add(new HasIdAndNameStub("id1", "name1"));
        _entitiesOfType.add(new HasIdAndNameStub("id2", "name2"));
        _entitiesOfType.add(new HasIdAndNameStub("id3", "name3"));

        assertEquals(3, _entitiesOfType.size());
    }

    @Test
    void testContains() {
        HasId type = new HasIdAndNameStub("id", "name");

        assertFalse(_entitiesOfType.contains(type));

        _entitiesOfType.add(type);

        assertTrue(_entitiesOfType.contains(type));
    }

    @Test
    void testRemove() {
        HasId type = new HasIdAndNameStub("id", "name");

        assertFalse(_entitiesOfType.remove(type));

        _entitiesOfType.add(type);

        assertTrue(_entitiesOfType.remove(type));
        assertFalse(_entitiesOfType.remove(type));
    }

    @Test
    void testMethodsWithNullType() {
        assertThrows(IllegalArgumentException.class, () -> _entitiesOfType.add(null));
        assertThrows(IllegalArgumentException.class, () -> _entitiesOfType.get(null));
        assertThrows(IllegalArgumentException.class, () -> _entitiesOfType.contains(null));
        assertThrows(IllegalArgumentException.class, () -> _entitiesOfType.remove(null));
    }

    @Test
    void testClear() {
        HasId type1 = new HasIdAndNameStub("id1", "name1");
        HasId type2 = new HasIdAndNameStub("id2", "name2");
        HasId type3 = new HasIdAndNameStub("id3", "name3");

        _entitiesOfType.add(type1);
        _entitiesOfType.add(type2);
        _entitiesOfType.add(type3);

        _entitiesOfType.clear();

        assertEquals(0, _entitiesOfType.size());
    }

    @Test
    void testRepresentation() {
        HasId type1 = new HasIdAndNameStub("id1", "name1");
        HasId type2 = new HasIdAndNameStub("id2", "name2");
        HasId type3 = new HasIdAndNameStub("id3", "name3");

        _entitiesOfType.add(type1);
        _entitiesOfType.add(type2);
        _entitiesOfType.add(type3);

        ReadableCollection<CharacterEntityStub> representation =
                _entitiesOfType.representation();

        assertNotNull(representation);
        assertEquals(3, representation.size());
        ENTITIES_ADDED.forEach(e -> assertTrue(representation.contains(e)));
    }

    @Test
    void testIterator() {
        HasId type1 = new HasIdAndNameStub("id1", "name1");
        HasId type2 = new HasIdAndNameStub("id2", "name2");
        HasId type3 = new HasIdAndNameStub("id3", "name3");

        _entitiesOfType.add(type1);
        _entitiesOfType.add(type2);
        _entitiesOfType.add(type3);

        ArrayList<CharacterEntityStub> fromIterator = new ArrayList<>();

        _entitiesOfType.forEach(fromIterator::add);

        assertEquals(3, fromIterator.size());
        ENTITIES_ADDED.forEach(e -> assertTrue(fromIterator.contains(e)));
    }

    @Test
    void testDelete() {
        HasId type1 = new HasIdAndNameStub("id1", "name1");
        HasId type2 = new HasIdAndNameStub("id2", "name2");
        HasId type3 = new HasIdAndNameStub("id3", "name3");

        _entitiesOfType.add(type1);
        _entitiesOfType.add(type2);
        _entitiesOfType.add(type3);

        _entitiesOfType.delete();

        assertTrue(_entitiesOfType.isDeleted());
        ENTITIES_ADDED.forEach(e -> assertTrue(e.isDeleted()));
    }

    @Test
    void testDeletionInvariant() {
        HasId type = new HasIdAndNameStub("id", "name");

        _entitiesOfType.delete();

        assertThrows(IllegalStateException.class, () -> _entitiesOfType.add(type));
        assertThrows(IllegalStateException.class, () -> _entitiesOfType.get(type));
        assertThrows(IllegalStateException.class, () -> _entitiesOfType.contains(type));
        assertThrows(IllegalStateException.class, () -> _entitiesOfType.remove(type));
        assertThrows(IllegalStateException.class, () -> _entitiesOfType.size());
        assertThrows(IllegalStateException.class, () -> _entitiesOfType.clear());
        assertThrows(IllegalStateException.class, () -> _entitiesOfType.representation());
        assertThrows(IllegalStateException.class, () -> _entitiesOfType.iterator());
    }

    @Test
    void testCharacterDeletionInvariant() {
        HasId type = new HasIdAndNameStub("id", "name");

        CHARACTER.delete();

        assertThrows(IllegalStateException.class, () -> _entitiesOfType.add(type));
        assertThrows(IllegalStateException.class, () -> _entitiesOfType.get(type));
        assertThrows(IllegalStateException.class, () -> _entitiesOfType.contains(type));
        assertThrows(IllegalStateException.class, () -> _entitiesOfType.remove(type));
        assertThrows(IllegalStateException.class, () -> _entitiesOfType.size());
        assertThrows(IllegalStateException.class, () -> _entitiesOfType.clear());
        assertThrows(IllegalStateException.class, () -> _entitiesOfType.representation());
        assertThrows(IllegalStateException.class, () -> _entitiesOfType.iterator());
    }
}

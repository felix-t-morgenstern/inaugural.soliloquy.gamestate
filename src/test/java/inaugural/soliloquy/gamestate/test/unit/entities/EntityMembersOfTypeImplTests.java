package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.EntityMembersOfTypeImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeDeletable;
import inaugural.soliloquy.gamestate.test.fakes.FakeEntityMemberOfType;
import inaugural.soliloquy.gamestate.test.fakes.FakeHasIdAndName;
import inaugural.soliloquy.gamestate.test.fakes.FakeVariableCacheFactory;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.shared.HasId;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.gamestate.entities.EntityMemberOfType;
import soliloquy.specs.gamestate.entities.EntityMembersOfType;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class EntityMembersOfTypeImplTests {
    private final FakeDeletable CONTAINING_ENTITY = new FakeDeletable();
    private final ArrayList<HasId> TYPES_ADDED = new ArrayList<>();
    private final ArrayList<FakeEntityMemberOfType> ENTITIES_ADDED = new ArrayList<>();
    private final Function<Deletable, Function<HasId, Function<VariableCache,
            FakeEntityMemberOfType>>>
            FACTORY = e -> t -> d -> {
        _entityPassedIntoFactory = e;
        _typePassedIntoFactory = t;
        _dataPassedIntoFactory = d;
        TYPES_ADDED.add(t);
        FakeEntityMemberOfType entity = new FakeEntityMemberOfType(t);
        ENTITIES_ADDED.add(entity);
        return entity;
    };
    private final FakeVariableCacheFactory DATA_FACTORY = new FakeVariableCacheFactory();

    private final HasIdArchetype ENTITY_MEMBER_TYPE_ARCHETYPE = new HasIdArchetype();
    private final FakeEntityMemberOfType ENTITY_MEMBER_ARCHETYPE =
            new FakeEntityMemberOfType(new FakeHasIdAndName("id", "name"));

    private Deletable _entityPassedIntoFactory;
    private HasId _typePassedIntoFactory;
    private VariableCache _dataPassedIntoFactory;
    private EntityMembersOfType<HasId, FakeEntityMemberOfType, Deletable> _entityMembersOfType;

    @BeforeEach
    void setUp() {
        _entityPassedIntoFactory = null;
        _entityMembersOfType =
                new EntityMembersOfTypeImpl<>(CONTAINING_ENTITY, FACTORY, DATA_FACTORY,
                        ENTITY_MEMBER_TYPE_ARCHETYPE, ENTITY_MEMBER_ARCHETYPE);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new EntityMembersOfTypeImpl<>(null, FACTORY,
                        DATA_FACTORY, ENTITY_MEMBER_TYPE_ARCHETYPE, ENTITY_MEMBER_ARCHETYPE));
        assertThrows(IllegalArgumentException.class,
                () -> new EntityMembersOfTypeImpl<>(CONTAINING_ENTITY, null,
                        DATA_FACTORY, ENTITY_MEMBER_TYPE_ARCHETYPE, ENTITY_MEMBER_ARCHETYPE));
        assertThrows(IllegalArgumentException.class,
                () -> new EntityMembersOfTypeImpl<>(CONTAINING_ENTITY, FACTORY,
                        null, ENTITY_MEMBER_TYPE_ARCHETYPE, ENTITY_MEMBER_ARCHETYPE));
        assertThrows(IllegalArgumentException.class,
                () -> new EntityMembersOfTypeImpl<>(CONTAINING_ENTITY, FACTORY,
                        DATA_FACTORY, null, ENTITY_MEMBER_ARCHETYPE));
        assertThrows(IllegalArgumentException.class,
                () -> new EntityMembersOfTypeImpl<>(CONTAINING_ENTITY, FACTORY,
                        DATA_FACTORY, ENTITY_MEMBER_TYPE_ARCHETYPE, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(EntityMembersOfType.class.getCanonicalName() + "<" +
                        HasId.class.getCanonicalName() + "," +
                        EntityMemberOfType.class.getCanonicalName() + "<" +
                        HasId.class.getCanonicalName() + ">" + "," +
                        Deletable.class.getCanonicalName() + ">",
                _entityMembersOfType.getInterfaceName());
    }

    @Test
    void testAddAndGet() {
        HasId type = new FakeHasIdAndName("id", "name");

        assertNull(_entityMembersOfType.get(type));

        _entityMembersOfType.add(type);

        assertEquals(1, TYPES_ADDED.size());
        assertTrue(TYPES_ADDED.contains(type));
        assertNotNull(_entityMembersOfType.get(type));
        assertSame(CONTAINING_ENTITY, _entityPassedIntoFactory);
        assertSame(type, _typePassedIntoFactory);
        assertSame(DATA_FACTORY.Created.get(0), _dataPassedIntoFactory);
    }

    @Test
    void testAddWithData() {
        HasId type = new FakeHasIdAndName("id", "name");
        VariableCache data = new VariableCacheStub();

        assertNull(_entityMembersOfType.get(type));

        _entityMembersOfType.add(type, data);

        assertEquals(1, TYPES_ADDED.size());
        assertTrue(TYPES_ADDED.contains(type));
        assertNotNull(_entityMembersOfType.get(type));
        assertSame(CONTAINING_ENTITY, _entityPassedIntoFactory);
        assertSame(type, _typePassedIntoFactory);
        assertSame(data, _dataPassedIntoFactory);
    }

    @Test
    void testAddExistingTypeIsNondestructive() {
        HasId type = new FakeHasIdAndName("id", "name");

        _entityMembersOfType.add(type);

        FakeEntityMemberOfType entity = _entityMembersOfType.get(type);

        _entityMembersOfType.add(type);

        FakeEntityMemberOfType entityAfterSecondAdd = _entityMembersOfType.get(type);

        assertSame(entity, entityAfterSecondAdd);
    }

    @Test
    void testSize() {
        _entityMembersOfType.add(new FakeHasIdAndName("id1", "name1"));
        _entityMembersOfType.add(new FakeHasIdAndName("id2", "name2"));
        _entityMembersOfType.add(new FakeHasIdAndName("id3", "name3"));

        assertEquals(3, _entityMembersOfType.size());
    }

    @Test
    void testContains() {
        HasId type = new FakeHasIdAndName("id", "name");

        assertFalse(_entityMembersOfType.contains(type));

        _entityMembersOfType.add(type);

        assertTrue(_entityMembersOfType.contains(type));
    }

    @Test
    void testRemove() {
        HasId type = new FakeHasIdAndName("id", "name");

        assertFalse(_entityMembersOfType.remove(type));

        _entityMembersOfType.add(type);

        assertTrue(_entityMembersOfType.remove(type));
        assertFalse(_entityMembersOfType.remove(type));
    }

    @Test
    void testMethodsWithNullType() {
        assertThrows(IllegalArgumentException.class, () -> _entityMembersOfType.add(null));
        assertThrows(IllegalArgumentException.class, () -> _entityMembersOfType.get(null));
        assertThrows(IllegalArgumentException.class, () -> _entityMembersOfType.contains(null));
        assertThrows(IllegalArgumentException.class, () -> _entityMembersOfType.remove(null));
    }

    @Test
    void testClear() {
        HasId type1 = new FakeHasIdAndName("id1", "name1");
        HasId type2 = new FakeHasIdAndName("id2", "name2");
        HasId type3 = new FakeHasIdAndName("id3", "name3");

        _entityMembersOfType.add(type1);
        _entityMembersOfType.add(type2);
        _entityMembersOfType.add(type3);

        _entityMembersOfType.clear();

        assertEquals(0, _entityMembersOfType.size());
    }

    @Test
    void testRepresentation() {
        HasId type1 = new FakeHasIdAndName("id1", "name1");
        HasId type2 = new FakeHasIdAndName("id2", "name2");
        HasId type3 = new FakeHasIdAndName("id3", "name3");

        _entityMembersOfType.add(type1);
        _entityMembersOfType.add(type2);
        _entityMembersOfType.add(type3);

        List<FakeEntityMemberOfType> representation = _entityMembersOfType.representation();

        assertNotNull(representation);
        assertEquals(3, representation.size());
        ENTITIES_ADDED.forEach(e -> assertTrue(representation.contains(e)));
    }

    @Test
    void testIterator() {
        HasId type1 = new FakeHasIdAndName("id1", "name1");
        HasId type2 = new FakeHasIdAndName("id2", "name2");
        HasId type3 = new FakeHasIdAndName("id3", "name3");

        _entityMembersOfType.add(type1);
        _entityMembersOfType.add(type2);
        _entityMembersOfType.add(type3);

        ArrayList<FakeEntityMemberOfType> fromIterator = new ArrayList<>();

        _entityMembersOfType.forEach(fromIterator::add);

        assertEquals(3, fromIterator.size());
        ENTITIES_ADDED.forEach(e -> assertTrue(fromIterator.contains(e)));
    }

    @Test
    void testDelete() {
        HasId type1 = new FakeHasIdAndName("id1", "name1");
        HasId type2 = new FakeHasIdAndName("id2", "name2");
        HasId type3 = new FakeHasIdAndName("id3", "name3");

        _entityMembersOfType.add(type1);
        _entityMembersOfType.add(type2);
        _entityMembersOfType.add(type3);

        _entityMembersOfType.delete();

        assertTrue(_entityMembersOfType.isDeleted());
        ENTITIES_ADDED.forEach(e -> assertTrue(e.isDeleted()));
    }

    @Test
    void testDeletionInvariant() {
        HasId type = new FakeHasIdAndName("id", "name");

        _entityMembersOfType.delete();

        assertThrows(EntityDeletedException.class, () -> _entityMembersOfType.add(type));
        assertThrows(EntityDeletedException.class, () -> _entityMembersOfType.get(type));
        assertThrows(EntityDeletedException.class, () -> _entityMembersOfType.contains(type));
        assertThrows(EntityDeletedException.class, () -> _entityMembersOfType.remove(type));
        assertThrows(EntityDeletedException.class, () -> _entityMembersOfType.size());
        assertThrows(EntityDeletedException.class, () -> _entityMembersOfType.clear());
        assertThrows(EntityDeletedException.class, () -> _entityMembersOfType.representation());
        assertThrows(EntityDeletedException.class, () -> _entityMembersOfType.iterator());
    }

    @Test
    void testCharacterDeletionInvariant() {
        HasId type = new FakeHasIdAndName("id", "name");

        CONTAINING_ENTITY.delete();

        assertThrows(IllegalStateException.class, () -> _entityMembersOfType.add(type));
        assertThrows(IllegalStateException.class, () -> _entityMembersOfType.get(type));
        assertThrows(IllegalStateException.class, () -> _entityMembersOfType.contains(type));
        assertThrows(IllegalStateException.class, () -> _entityMembersOfType.remove(type));
        assertThrows(IllegalStateException.class, () -> _entityMembersOfType.size());
        assertThrows(IllegalStateException.class, () -> _entityMembersOfType.clear());
        assertThrows(IllegalStateException.class, () -> _entityMembersOfType.representation());
        assertThrows(IllegalStateException.class, () -> _entityMembersOfType.iterator());
    }

    private static class HasIdArchetype implements HasId {

        @Override
        public String id() throws IllegalStateException {
            return null;
        }

        @Override
        public String getInterfaceName() {
            return HasId.class.getCanonicalName();
        }
    }
}

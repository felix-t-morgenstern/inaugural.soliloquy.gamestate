package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.EntityMembersOfTypeFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeDeletable;
import inaugural.soliloquy.gamestate.test.fakes.FakeEntityMemberOfType;
import inaugural.soliloquy.gamestate.test.fakes.FakeHasIdAndName;
import inaugural.soliloquy.gamestate.test.fakes.FakeVariableCacheFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.shared.HasId;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.gamestate.entities.EntityMembersOfType;
import soliloquy.specs.gamestate.factories.EntityMembersOfTypeFactory;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class EntityMembersOfTypeFactoryImplTests {
    private final FakeVariableCacheFactory DATA_FACTORY = new FakeVariableCacheFactory();
    private final FakeEntityMemberOfType FACTORY_OUTPUT = new FakeEntityMemberOfType(null);
    // TODO: Determine whether and where factory output should be checked
    private final Function<Deletable, Function<HasId, Function<VariableCache,
            FakeEntityMemberOfType>>>
            FACTORY = e -> t -> d -> {
        _entityPassedIntoFactory = e;
        _typePassedIntoFactory = t;
        _dataPassedIntoFactory = d;
        return FACTORY_OUTPUT;
    };
    private final FakeDeletable CONTAINING_ENTITY = new FakeDeletable();

    private final DeletableArchetype ENTITY_ARCHETYPE = new DeletableArchetype();
    private final HasIdArchetype ENTITY_MEMBER_TYPE_ARCHETYPE = new HasIdArchetype();
    private final FakeEntityMemberOfType ENTITY_MEMBER_ARCHETYPE = new FakeEntityMemberOfType(
            new FakeHasIdAndName("", ""));

    private Deletable _entityPassedIntoFactory;
    private HasId _typePassedIntoFactory;
    private VariableCache _dataPassedIntoFactory;
    private EntityMembersOfTypeFactory _factory;

    @BeforeEach
    void setUp() {
        _entityPassedIntoFactory = null;
        _factory = new EntityMembersOfTypeFactoryImpl(DATA_FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new EntityMembersOfTypeFactoryImpl(null));
    }

    @Test
    void testMakeWithoutRegisteredFactory() {
        assertThrows(IllegalArgumentException.class, () ->
                _factory.make(CONTAINING_ENTITY, ENTITY_MEMBER_TYPE_ARCHETYPE,
                        ENTITY_MEMBER_ARCHETYPE));
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _factory.make(null, ENTITY_MEMBER_TYPE_ARCHETYPE, ENTITY_MEMBER_ARCHETYPE));
        assertThrows(IllegalArgumentException.class, () ->
                _factory.make(CONTAINING_ENTITY, null, ENTITY_MEMBER_ARCHETYPE));
        assertThrows(IllegalArgumentException.class, () ->
                _factory.make(CONTAINING_ENTITY, ENTITY_MEMBER_TYPE_ARCHETYPE, null));
    }

    @Test
    void testRegisterFactoryAndMake() {
        _factory.registerFactory(FACTORY, ENTITY_MEMBER_TYPE_ARCHETYPE, ENTITY_MEMBER_ARCHETYPE,
                ENTITY_ARCHETYPE);
        HasId type = new FakeHasIdAndName("", "");

        EntityMembersOfType<HasId, FakeEntityMemberOfType, Deletable> entities =
                _factory.make(CONTAINING_ENTITY, ENTITY_MEMBER_TYPE_ARCHETYPE,
                        ENTITY_MEMBER_ARCHETYPE);

        entities.add(type);

        FakeEntityMemberOfType fromEntities = entities.get(type);

        assertSame(FACTORY_OUTPUT, fromEntities);
        assertSame(CONTAINING_ENTITY, _entityPassedIntoFactory);
        assertSame(type, _typePassedIntoFactory);
        assertSame(DATA_FACTORY.Created.get(0), _dataPassedIntoFactory);
    }

    @Test
    void testRegisterFactoryWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _factory.registerFactory(null, ENTITY_MEMBER_TYPE_ARCHETYPE,
                        ENTITY_MEMBER_ARCHETYPE, ENTITY_ARCHETYPE));
        assertThrows(IllegalArgumentException.class, () ->
                _factory.registerFactory(FACTORY, null,
                        ENTITY_MEMBER_ARCHETYPE, ENTITY_ARCHETYPE));
        assertThrows(IllegalArgumentException.class, () ->
                _factory.registerFactory(FACTORY, ENTITY_MEMBER_TYPE_ARCHETYPE,
                        null, ENTITY_ARCHETYPE));
        assertThrows(IllegalArgumentException.class, () ->
                _factory.registerFactory(FACTORY, ENTITY_MEMBER_TYPE_ARCHETYPE,
                        ENTITY_MEMBER_ARCHETYPE, null));
    }

    @Test
    void testGetInterface() {
        assertEquals(EntityMembersOfTypeFactory.class.getCanonicalName(),
                _factory.getInterfaceName());
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

    private static class DeletableArchetype implements Deletable {

        @Override
        public void delete() throws IllegalStateException {

        }

        @Override
        public boolean isDeleted() {
            return false;
        }

        @Override
        public String getInterfaceName() {
            return Deletable.class.getCanonicalName();
        }
    }
}

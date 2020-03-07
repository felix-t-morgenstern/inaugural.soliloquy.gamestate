package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.common.test.stubs.HasIdAndNameStub;
import inaugural.soliloquy.gamestate.CharacterEntitiesOfTypeFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeCharacter;
import inaugural.soliloquy.gamestate.test.fakes.FakeCharacterEntity;
import inaugural.soliloquy.gamestate.test.fakes.FakeCollectionFactory;
import inaugural.soliloquy.gamestate.test.fakes.FakeVariableCacheFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.shared.HasId;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEntitiesOfType;
import soliloquy.specs.gamestate.factories.CharacterEntitiesOfTypeFactory;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class CharacterEntitiesOfTypeFactoryImplTests {
    private final CollectionFactory COLLECTION_FACTORY = new FakeCollectionFactory();
    private final FakeVariableCacheFactory DATA_FACTORY = new FakeVariableCacheFactory();
    private final FakeCharacterEntity FACTORY_OUTPUT = new FakeCharacterEntity(null, null);
    private final Function<Character,Function<HasId,Function<VariableCache, FakeCharacterEntity>>>
            FACTORY = c -> t -> d -> {
                _characterPassedIntoFactory = c;
                _typePassedIntoFactory = t;
                _dataPassedIntoFactory = d;
                return FACTORY_OUTPUT;
            };
    private final Character CHARACTER = new FakeCharacter();
    private final FakeCharacterEntity ARCHETYPE =
            new FakeCharacterEntity(null, new HasIdAndNameStub("", ""));

    private Character _characterPassedIntoFactory;
    private HasId _typePassedIntoFactory;
    private VariableCache _dataPassedIntoFactory;
    private CharacterEntitiesOfTypeFactory _factory;

    @BeforeEach
    void setUp() {
        _characterPassedIntoFactory = null;
        _factory = new CharacterEntitiesOfTypeFactoryImpl(COLLECTION_FACTORY, DATA_FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterEntitiesOfTypeFactoryImpl(null, DATA_FACTORY));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterEntitiesOfTypeFactoryImpl(COLLECTION_FACTORY, null));
    }

    @Test
    void testMakeWithoutRegisteredFactory() {
        assertThrows(IllegalArgumentException.class, () ->
                _factory.make(CHARACTER, ARCHETYPE));
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _factory.make(null, ARCHETYPE));
        assertThrows(IllegalArgumentException.class, () -> _factory.make(CHARACTER, null));
    }

    @Test
    void testRegisterFactoryAndMake() {
        _factory.registerFactory(ARCHETYPE, FACTORY);
        HasId type = new HasIdAndNameStub("", "");

        @SuppressWarnings("unchecked") CharacterEntitiesOfType<HasId, FakeCharacterEntity>
                entities = _factory.make(CHARACTER, ARCHETYPE);

        entities.add(type);

        FakeCharacterEntity fromEntities = entities.get(type);

        assertSame(FACTORY_OUTPUT, fromEntities);
        assertSame(CHARACTER, _characterPassedIntoFactory);
        assertSame(type, _typePassedIntoFactory);
        assertSame(DATA_FACTORY.Created.get(0), _dataPassedIntoFactory);
    }

    @Test
    void testRegisterFactoryWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _factory.registerFactory(null,
                FACTORY));
        assertThrows(IllegalArgumentException.class, () -> _factory.registerFactory(ARCHETYPE,
                null));
    }

    @Test
    void testGetInterface() {
        assertEquals(CharacterEntitiesOfTypeFactory.class.getCanonicalName(),
                _factory.getInterfaceName());
    }
}

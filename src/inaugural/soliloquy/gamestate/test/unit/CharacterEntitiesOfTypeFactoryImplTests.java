package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.common.test.stubs.HasIdAndNameStub;
import inaugural.soliloquy.gamestate.CharacterEntitiesOfTypeFactoryImpl;
import inaugural.soliloquy.gamestate.CharacterEntitiesOfTypeImpl;
import inaugural.soliloquy.gamestate.test.stubs.CharacterEntityStub;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStub;
import inaugural.soliloquy.gamestate.test.stubs.CollectionFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheFactoryStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.shared.HasId;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEntitiesOfType;
import soliloquy.specs.gamestate.factories.CharacterEntitiesOfTypeFactory;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class CharacterEntitiesOfTypeFactoryImplTests {
    private final CollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    private final VariableCacheFactory DATA_FACTORY = new VariableCacheFactoryStub();
    private final CharacterEntityStub FACTORY_OUTPUT = new CharacterEntityStub(null, null);
    private final Function<Character,Function<HasId,Function<VariableCache,CharacterEntityStub>>>
            FACTORY = c -> t -> d -> {
                _characterPassedIntoFactory = c;
                _typePassedIntoFactory = t;
                _dataPassedIntoFactory = d;
                return FACTORY_OUTPUT;
            };
    private final Character CHARACTER = new CharacterStub();
    private final CharacterEntityStub ARCHETYPE =
            new CharacterEntityStub(null, new HasIdAndNameStub("", ""));

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

        CharacterEntitiesOfType<HasId, CharacterEntityStub> entities =
                _factory.make(CHARACTER, ARCHETYPE);

        entities.add(type);

        CharacterEntityStub fromEntities = entities.get(type);

        assertSame(FACTORY_OUTPUT, fromEntities);
        assertSame(CHARACTER, _characterPassedIntoFactory);
        // TODO: Test other parameters passed in
        fail();
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

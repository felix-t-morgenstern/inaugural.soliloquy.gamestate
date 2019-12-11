package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.common.test.stubs.HasIdAndNameStub;
import inaugural.soliloquy.gamestate.CharacterEntitiesOfTypeFactoryImpl;
import inaugural.soliloquy.gamestate.CharacterEntitiesOfTypeImpl;
import inaugural.soliloquy.gamestate.test.stubs.CharacterEntityStub;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStub;
import inaugural.soliloquy.gamestate.test.stubs.CollectionFactoryStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.shared.HasId;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEntitiesOfType;
import soliloquy.specs.gamestate.factories.CharacterEntitiesOfTypeFactory;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class CharacterEntitiesOfTypeFactoryImplTests {
    private final CollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    private final CharacterEntityStub FACTORY_OUTPUT = new CharacterEntityStub(null, null);
    private final Function<HasId, Function<Character, CharacterEntityStub>> FACTORY =
            t -> c -> {
                _characterPassedIntoFactory = c;
        return FACTORY_OUTPUT;
            };
    private final Character CHARACTER = new CharacterStub();
    private final CharacterEntityStub ARCHETYPE =
            new CharacterEntityStub(null, new HasIdAndNameStub("", ""));

    private Character _characterPassedIntoFactory;
    private CharacterEntitiesOfTypeFactory _factory;

    @BeforeEach
    void setUp() {
        _characterPassedIntoFactory = null;
        _factory = new CharacterEntitiesOfTypeFactoryImpl(COLLECTION_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterEntitiesOfTypeFactoryImpl(null));
    }

    @Test
    void testMakeWithoutRegisteredFactory() {
        assertThrows(IllegalArgumentException.class, () ->
                _factory.make(CHARACTER, ARCHETYPE));
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
    }

    @Test
    void testGetInterface() {
        assertEquals(CharacterEntitiesOfTypeFactory.class.getCanonicalName(),
                _factory.getInterfaceName());
    }
}

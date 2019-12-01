package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterEventsImpl;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStub;
import inaugural.soliloquy.gamestate.test.stubs.CollectionFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.MapFactoryStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEvents;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CharacterEventsImplTests {
    private final Character CHARACTER = new CharacterStub();
    private final CollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    private final MapFactory MAP_FACTORY = new MapFactoryStub();

    private CharacterEvents _characterEvents;

    @BeforeEach
    void setUp() {
        _characterEvents = new CharacterEventsImpl(CHARACTER, COLLECTION_FACTORY, MAP_FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterEventsImpl(null, COLLECTION_FACTORY, MAP_FACTORY));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterEventsImpl(CHARACTER, null, MAP_FACTORY));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterEventsImpl(CHARACTER, COLLECTION_FACTORY, null));
    }
}

package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterEventsFactoryImpl;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStub;
import inaugural.soliloquy.gamestate.test.stubs.CollectionFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.GameCharacterEventStub;
import inaugural.soliloquy.gamestate.test.stubs.MapFactoryStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEvents;
import soliloquy.specs.gamestate.factories.CharacterEventsFactory;

import static org.junit.jupiter.api.Assertions.*;


class CharacterEventsFactoryImplTests {
    private final CollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    private final MapFactory MAP_FACTORY = new MapFactoryStub();

    private CharacterEventsFactory _characterEventsFactory;

    @BeforeEach
    void setUp() {
        _characterEventsFactory = new CharacterEventsFactoryImpl(COLLECTION_FACTORY, MAP_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterEventsFactoryImpl(null, MAP_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterEventsFactoryImpl(COLLECTION_FACTORY, null));
    }

    @Test
    void testMake() {
        Character character = new CharacterStub();
        String eventStubId = "eventStubId";
        GameCharacterEventStub eventStub = new GameCharacterEventStub(eventStubId);
        String trigger = "trigger";

        CharacterEvents characterEvents = _characterEventsFactory.make(character);
        assertNotNull(characterEvents);
        characterEvents.addEvent(trigger, eventStub);
        eventStub.fire(character);
        assertSame(character, eventStub._characterFired);
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _characterEventsFactory.make(null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(CharacterEventsFactory.class.getCanonicalName(),
                _characterEventsFactory.getInterfaceName());
    }
}

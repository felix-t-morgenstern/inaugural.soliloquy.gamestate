package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.CharacterEventsFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeCharacter;
import inaugural.soliloquy.gamestate.test.fakes.FakeGameCharacterEvent;
import inaugural.soliloquy.gamestate.test.fakes.FakeListFactory;
import inaugural.soliloquy.gamestate.test.fakes.FakeMapFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEvents;
import soliloquy.specs.gamestate.factories.CharacterEventsFactory;

import static org.junit.jupiter.api.Assertions.*;


class CharacterEventsFactoryImplTests {
    private final ListFactory LIST_FACTORY = new FakeListFactory();
    private final MapFactory MAP_FACTORY = new FakeMapFactory();

    private CharacterEventsFactory _characterEventsFactory;

    @BeforeEach
    void setUp() {
        _characterEventsFactory = new CharacterEventsFactoryImpl(LIST_FACTORY, MAP_FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterEventsFactoryImpl(null, MAP_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterEventsFactoryImpl(LIST_FACTORY, null));
    }

    @Test
    void testMake() {
        Character character = new FakeCharacter();
        String eventStubId = "eventStubId";
        FakeGameCharacterEvent eventStub = new FakeGameCharacterEvent(eventStubId);
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

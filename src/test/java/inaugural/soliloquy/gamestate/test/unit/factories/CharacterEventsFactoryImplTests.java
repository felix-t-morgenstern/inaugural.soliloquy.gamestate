package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.CharacterEventsFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeCharacter;
import inaugural.soliloquy.gamestate.test.fakes.FakeGameCharacterEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEvents;
import soliloquy.specs.gamestate.factories.CharacterEventsFactory;

import static org.junit.jupiter.api.Assertions.*;

class CharacterEventsFactoryImplTests {
    private CharacterEventsFactory _characterEventsFactory;

    @BeforeEach
    void setUp() {
        _characterEventsFactory = new CharacterEventsFactoryImpl();
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

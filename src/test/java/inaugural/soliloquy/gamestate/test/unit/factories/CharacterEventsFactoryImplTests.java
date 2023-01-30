package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.entities.CharacterEventsImpl;
import inaugural.soliloquy.gamestate.factories.CharacterEventsFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.factories.CharacterEventsFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CharacterEventsFactoryImplTests {
    private CharacterEventsFactory characterEventsFactory;

    @BeforeEach
    void setUp() {
        characterEventsFactory = new CharacterEventsFactoryImpl();
    }

    @Test
    void testMake() {
        var character = mock(Character.class);

        var characterEvents = characterEventsFactory.make(character);

        assertNotNull(characterEvents);
        assertTrue(characterEvents instanceof CharacterEventsImpl);
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> characterEventsFactory.make(null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(CharacterEventsFactory.class.getCanonicalName(),
                characterEventsFactory.getInterfaceName());
    }
}

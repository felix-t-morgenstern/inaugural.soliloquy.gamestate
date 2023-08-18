package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.entities.CharacterEventsImpl;
import inaugural.soliloquy.gamestate.factories.CharacterEventsFactoryImpl;
import org.junit.Before;
import org.junit.Test;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.factories.CharacterEventsFactory;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class CharacterEventsFactoryImplTests {
    private CharacterEventsFactory characterEventsFactory;

    @Before
    public void setUp() {
        characterEventsFactory = new CharacterEventsFactoryImpl();
    }

    @Test
    public void testMake() {
        var character = mock(Character.class);

        var characterEvents = characterEventsFactory.make(character);

        assertNotNull(characterEvents);
        assertTrue(characterEvents instanceof CharacterEventsImpl);
    }

    @Test
    public void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> characterEventsFactory.make(null));
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(CharacterEventsFactory.class.getCanonicalName(),
                characterEventsFactory.getInterfaceName());
    }
}

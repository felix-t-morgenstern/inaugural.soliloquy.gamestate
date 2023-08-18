package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.entities.CharacterStatusEffectsImpl;
import inaugural.soliloquy.gamestate.factories.CharacterStatusEffectsFactoryImpl;
import org.junit.Before;
import org.junit.Test;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.factories.CharacterStatusEffectsFactory;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class CharacterStatusEffectsFactoryImplTests {
    private CharacterStatusEffectsFactory factory;

    @Before
    public void setUp() {
        factory = new CharacterStatusEffectsFactoryImpl();
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(CharacterStatusEffectsFactory.class.getCanonicalName(),
                factory.getInterfaceName());
    }

    @Test
    public void testMake() {
        var mockCharacter = mock(Character.class);

        var characterStatusEffects = factory.make(mockCharacter);

        assertNotNull(characterStatusEffects);
        assertTrue(characterStatusEffects instanceof CharacterStatusEffectsImpl);
    }

    @Test
    public void testMakeWithNullInput() {
        assertThrows(IllegalArgumentException.class,
                () -> factory.make(null));
    }
}

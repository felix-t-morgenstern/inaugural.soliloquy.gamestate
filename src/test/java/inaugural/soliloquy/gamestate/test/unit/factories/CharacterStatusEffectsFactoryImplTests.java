package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.entities.CharacterStatusEffectsImpl;
import inaugural.soliloquy.gamestate.factories.CharacterStatusEffectsFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.factories.CharacterStatusEffectsFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CharacterStatusEffectsFactoryImplTests {
    private CharacterStatusEffectsFactory characterStatusEffectsFactory;

    @BeforeEach
    void setUp() {
        characterStatusEffectsFactory = new CharacterStatusEffectsFactoryImpl();
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(CharacterStatusEffectsFactory.class.getCanonicalName(),
                characterStatusEffectsFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        var mockCharacter = mock(Character.class);

        var characterStatusEffects = characterStatusEffectsFactory.make(mockCharacter);

        assertNotNull(characterStatusEffects);
        assertTrue(characterStatusEffects instanceof CharacterStatusEffectsImpl);
    }

    @Test
    void testMakeWithNullInput() {
        assertThrows(IllegalArgumentException.class,
                () -> characterStatusEffectsFactory.make(null));
    }
}

package inaugural.soliloquy.gamestate.test.archetypes;

import inaugural.soliloquy.gamestate.archetypes.*;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.graphics.assets.Sprite;
import soliloquy.specs.ruleset.entities.CharacterAIType;
import soliloquy.specs.ruleset.entities.CharacterStaticStatisticType;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArchetypesTests {
    @Test
    void testCharacterAITypeArchetype() {
        assertEquals(CharacterAIType.class.getCanonicalName(),
                new CharacterAITypeArchetype().getInterfaceName());
    }

    @Test
    void testCharacterArchetype() {
        assertEquals(Character.class.getCanonicalName(),
                new CharacterArchetype().getInterfaceName());
    }

    @Test
    void testCharacterStaticStatisticArchetype() {
        assertEquals(CharacterStatistic.class.getCanonicalName() + "<" +
                        CharacterStaticStatisticType.class.getCanonicalName() + ">",
                new CharacterStaticStatisticArchetype().getInterfaceName());
    }

    @Test
    void testGameAbilityEventArchetype() {
        assertEquals(GameAbilityEvent.class.getCanonicalName(),
                new GameAbilityEventArchetype().getInterfaceName());
    }

    @Test
    void testGameMovementEventArchetype() {
        assertEquals(GameMovementEvent.class.getCanonicalName(),
                new GameMovementEventArchetype().getInterfaceName());
    }

    @Test
    void testKeyBindingArchetype() {
        assertEquals(KeyBinding.class.getCanonicalName(),
                new KeyBindingArchetype().getInterfaceName());
    }

    @Test
    void testSpriteArchetype() {
        assertEquals(Sprite.class.getCanonicalName(), new SpriteArchetype().getInterfaceName());
    }
}

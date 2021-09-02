package inaugural.soliloquy.gamestate.test.archetypes;

import inaugural.soliloquy.gamestate.archetypes.*;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.gamestate.GameState;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameCharacterEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.gamestate.entities.timers.OneTimeTurnBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RecurringTurnBasedTimer;
import soliloquy.specs.graphics.assets.Sprite;
import soliloquy.specs.ruleset.entities.CharacterAIType;
import soliloquy.specs.ruleset.entities.CharacterStaticStatisticType;
import soliloquy.specs.ruleset.entities.CharacterVariableStatisticType;
import soliloquy.specs.ruleset.entities.StatusEffectType;

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
    void testCharacterQueueEntryArchetype() {
        assertEquals(Pair.class.getCanonicalName() + "<" + Character.class.getCanonicalName() +
                "," + VariableCache.class.getCanonicalName() + ">",
                    new CharacterQueueEntryArchetype().getInterfaceName());
    }

    @Test
    void testCharacterStaticStatisticArchetype() {
        assertEquals(CharacterStatistic.class.getCanonicalName() + "<" +
                        CharacterStaticStatisticType.class.getCanonicalName() + ">",
                new CharacterStaticStatisticArchetype().getInterfaceName());
    }

    @Test
    void testCharacterVariableStatisticArchetype() {
        assertEquals(CharacterVariableStatistic.class.getCanonicalName(),
                new CharacterVariableStatisticArchetype().getInterfaceName());
    }

    @Test
    void testCharacterVariableStatisticTypeArchetype() {
        assertEquals(CharacterVariableStatisticType.class.getCanonicalName(),
                new CharacterVariableStatisticTypeArchetype().getInterfaceName());
    }

    @Test
    void testCoordinateArchetype() {
        assertEquals(Coordinate.class.getCanonicalName(),
                new CoordinateArchetype().getInterfaceName());
    }

    @Test
    void testGameAbilityEventArchetype() {
        assertEquals(GameAbilityEvent.class.getCanonicalName(),
                new GameAbilityEventArchetype().getInterfaceName());
    }

    @Test
    void testGameCharacterEventArchetype() {
        assertEquals(GameCharacterEvent.class.getCanonicalName(),
                new GameCharacterEventArchetype().getInterfaceName());
    }

    @Test
    void testGameCharacterEventReadableCollectionArchetype() {
        assertEquals(List.class.getCanonicalName() + "<" +
                GameCharacterEvent.class.getCanonicalName() + ">",
                new GameCharacterEventReadableCollectionArchetype().getInterfaceName());
    }

    @Test
    void testGameMovementEventArchetype() {
        assertEquals(GameMovementEvent.class.getCanonicalName(),
                new GameMovementEventArchetype().getInterfaceName());
    }

    @Test
    void testGameStateArchetype() {
        assertEquals(GameState.class.getCanonicalName(),
                new GameStateArchetype().getInterfaceName());
    }

    @Test
    void testGameZoneArchetype() {
        assertEquals(GameZone.class.getCanonicalName(),
                new GameZoneArchetype().getInterfaceName());
    }

    @Test
    void testItemArchetype() {
        assertEquals(Item.class.getCanonicalName(), new ItemArchetype().getInterfaceName());
    }

    @Test
    void testKeyBindingArchetype() {
        assertEquals(KeyBinding.class.getCanonicalName(),
                new KeyBindingArchetype().getInterfaceName());
    }

    @Test
    void testKeyBindingContextArchetype() {
        assertEquals(KeyBindingContext.class.getCanonicalName(),
                new KeyBindingContextArchetype().getInterfaceName());
    }

    @Test
    void testOneTimeTurnBasedTimerArchetype() {
        assertEquals(OneTimeTurnBasedTimer.class.getCanonicalName(),
                new OneTimeTurnBasedTimerArchetype().getInterfaceName());
    }

    @Test
    void testRecurringTurnBasedTimerArchetype() {
        assertEquals(RecurringTurnBasedTimer.class.getCanonicalName(),
                new RecurringTurnBasedTimerArchetype().getInterfaceName());
    }

    @Test
    void testSpriteArchetype() {
        assertEquals(Sprite.class.getCanonicalName(), new SpriteArchetype().getInterfaceName());
    }

    @Test
    void testStatusEffectTypeArchetype() {
        assertEquals(StatusEffectType.class.getCanonicalName(),
                new StatusEffectTypeArchetype().getInterfaceName());
    }

    @Test
    void testTileArchetype() {
        assertEquals(Tile.class.getCanonicalName(), new TileArchetype().getInterfaceName());
    }

    @Test
    void testTileFixtureArchetype() {
        assertEquals(TileFixture.class.getCanonicalName(),
                new TileFixtureArchetype().getInterfaceName());
    }

    @Test
    void testTileWallSegmentArchetype() {
        assertEquals(TileWallSegment.class.getCanonicalName(),
                new TileWallSegmentArchetype().getInterfaceName());
    }

    @Test
    void testVoidActionArchetype() {
        assertEquals(Action.class.getCanonicalName() + "<" + Void.class.getCanonicalName() + ">",
                new VoidActionArchetype().getInterfaceName());
    }
}

package inaugural.soliloquy.gamestate.test.archetypes;

import inaugural.soliloquy.gamestate.archetypes.*;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameCharacterEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.ruleset.entities.CharacterAIType;
import soliloquy.specs.ruleset.entities.CharacterDepletableStatisticType;
import soliloquy.specs.ruleset.entities.CharacterStaticStatisticType;
import soliloquy.specs.ruleset.entities.StatusEffectType;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbilityType;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbilityType;
import soliloquy.specs.ruleset.valueobjects.CharacterClassification;
import soliloquy.specs.sprites.entities.Sprite;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArchetypesTests {
    @Test
    void testActiveAbilityTypeArchetype() {
        assertEquals(ActiveAbilityType.class.getCanonicalName(),
                new ActiveAbilityTypeArchetype().getInterfaceName());
    }

    @Test
    void testCharacterActiveAbilityArchetype() {
        assertEquals(CharacterAbility.class.getCanonicalName() + "<" +
                ActiveAbilityType.class.getCanonicalName() + ">",
                new CharacterActiveAbilityArchetype().getInterfaceName());
    }

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
    void testCharacterClassificationArchetype() {
        assertEquals(CharacterClassification.class.getCanonicalName(),
                new CharacterClassificationArchetype().getInterfaceName());
    }

    @Test
    void testCharacterQueueEntryArchetype() {
        assertEquals(Pair.class.getCanonicalName() + "<" + Character.class.getCanonicalName() +
                "," + VariableCache.class.getCanonicalName() + ">",
                    new CharacterQueueEntryArchetype().getInterfaceName());
    }

    @Test
    void testCharacterReactiveAbilityArchetype() {
        assertEquals(CharacterAbility.class.getCanonicalName() + "<" +
                        ReactiveAbilityType.class.getCanonicalName() + ">",
                new CharacterReactiveAbilityArchetype().getInterfaceName());
    }

    @Test
    void testCharacterDepletableStatisticArchetype() {
        assertEquals(CharacterDepletableStatistic.class.getCanonicalName(),
                new CharacterDepletableStatisticArchetype().getInterfaceName());
    }

    @Test
    void testCharacterDepletableStatisticTypeArchetype() {
        assertEquals(CharacterDepletableStatisticType.class.getCanonicalName(),
                new CharacterDepletableStatisticTypeArchetype().getInterfaceName());
    }

    @Test
    void testCharacterStaticStatisticArchetype() {
        assertEquals(CharacterStaticStatistic.class.getCanonicalName(),
                new CharacterStaticStatisticArchetype().getInterfaceName());
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
        assertEquals(ReadableCollection.class.getCanonicalName() + "<" +
                GameCharacterEvent.class.getCanonicalName() + ">",
                new GameCharacterEventReadableCollectionArchetype().getInterfaceName());
    }

    @Test
    void testGameMovementEventArchetype() {
        assertEquals(GameMovementEvent.class.getCanonicalName(),
                new GameMovementEventArchetype().getInterfaceName());
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
    void testOneTimeTimerArchetype() {
        assertEquals(OneTimeTimer.class.getCanonicalName(),
                new OneTimeTimerArchetype().getInterfaceName());
    }

    @Test
    void testReactiveAbilityTypeArchetype() {
        assertEquals(ReactiveAbilityType.class.getCanonicalName(),
                new ReactiveAbilityTypeArchetype().getInterfaceName());
    }

    @Test
    void testRecurringTimerArchetype() {
        assertEquals(RecurringTimer.class.getCanonicalName(),
                new RecurringTimerArchetype().getInterfaceName());
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

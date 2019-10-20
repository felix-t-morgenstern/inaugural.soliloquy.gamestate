package inaugural.soliloquy.gamestate.test.archetypes;

import inaugural.soliloquy.gamestate.archetypes.*;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.gameevents.GameEvent;
import soliloquy.specs.ruleset.entities.CharacterAIType;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbilityType;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbilityType;
import soliloquy.specs.ruleset.valueobjects.CharacterClassification;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArchetypesTests {
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
    void testCharacterAptitudeArchetype() {
        assertEquals(CharacterAptitude.class.getCanonicalName(),
                new CharacterAptitudeArchetype().getInterfaceName());
    }

    @Test
    void testCharacterArchetype() {
        assertEquals(Character.class.getCanonicalName(),
                new CharacterArchetype().getInterfaceName());
    }

    @Test
    void testCharacterAttributeArchetype() {
        assertEquals(CharacterAttribute.class.getCanonicalName(),
                new CharacterAttributeArchetype().getInterfaceName());
    }

    @Test
    void testCharacterClassificationArchetype() {
        assertEquals(CharacterClassification.class.getCanonicalName(),
                new CharacterClassificationArchetype().getInterfaceName());
    }

    @Test
    void testCharacterReactiveAbilityArchetype() {
        assertEquals(CharacterAbility.class.getCanonicalName() + "<" +
                        ReactiveAbilityType.class.getCanonicalName() + ">",
                new CharacterReactiveAbilityArchetype().getInterfaceName());
    }

    @Test
    void testCharacterVitalAttributeArchetype() {
        assertEquals(CharacterVitalAttribute.class.getCanonicalName(),
                new CharacterVitalAttributeArchetype().getInterfaceName());
    }

    @Test
    void testCoordinateArchetype() {
        assertEquals(Coordinate.class.getCanonicalName(),
                new CoordinateArchetype().getInterfaceName());
    }

    @Test
    void testGameEventArchetype() {
        assertEquals(GameEvent.class.getCanonicalName(),
                new GameEventArchetype().getInterfaceName());
    }

    @Test
    void testItemArchetype() {
        assertEquals(Item.class.getCanonicalName(), new ItemArchetype().getInterfaceName());
    }

    @Test
    void testKeyBindingContextArchetype() {
        assertEquals(KeyBindingContext.class.getCanonicalName(),
                new KeyBindingContextArchetype().getInterfaceName());
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
}

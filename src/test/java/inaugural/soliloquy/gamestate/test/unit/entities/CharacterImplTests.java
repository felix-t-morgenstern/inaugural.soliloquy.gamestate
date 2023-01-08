package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.CharacterImpl;
import inaugural.soliloquy.gamestate.test.fakes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.gamestate.factories.*;
import soliloquy.specs.ruleset.entities.CharacterAIType;
import soliloquy.specs.ruleset.entities.CharacterType;

import java.util.UUID;

import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static soliloquy.specs.common.shared.Direction.SOUTHWEST;

class CharacterImplTests {
    private final UUID UUID = java.util.UUID.randomUUID();
    private final CharacterType CHARACTER_TYPE = new FakeCharacterType();
    private final CharacterEventsFactory CHARACTER_EVENTS_FACTORY =
            new FakeCharacterEventsFactory();
    private final CharacterEquipmentSlotsFactory EQUIPMENT_SLOTS_FACTORY =
            new FakeCharacterEquipmentSlotsFactory();
    private final CharacterInventoryFactory INVENTORY_FACTORY = new FakeCharacterInventoryFactory();
    private final EntityMembersOfTypeFactory ENTITIES_OF_TYPE_FACTORY =
            new FakeEntityMembersOfTypeFactory();
    private final CharacterVariableStatisticsFactory VARIABLE_STATS_FACTORY =
            new FakeCharacterVariableStatisticsFactory();
    private final CharacterStatusEffectsFactory STATUS_EFFECTS_FACTORY =
            new FakeCharacterStatusEffectsFactory();

    @Mock private VariableCache mockData;

    private Character character;

    @BeforeEach
    void setUp() {
        mockData = mock(VariableCache.class);

        character = new CharacterImpl(
                UUID,
                CHARACTER_TYPE,
                CHARACTER_EVENTS_FACTORY,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                VARIABLE_STATS_FACTORY,
                ENTITIES_OF_TYPE_FACTORY,
                STATUS_EFFECTS_FACTORY,
                mockData);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new CharacterImpl(
                null,
                CHARACTER_TYPE,
                CHARACTER_EVENTS_FACTORY,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                VARIABLE_STATS_FACTORY,
                ENTITIES_OF_TYPE_FACTORY,
                STATUS_EFFECTS_FACTORY,
                mockData));
        assertThrows(IllegalArgumentException.class, () -> new CharacterImpl(
                UUID,
                null,
                CHARACTER_EVENTS_FACTORY,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                VARIABLE_STATS_FACTORY,
                ENTITIES_OF_TYPE_FACTORY,
                STATUS_EFFECTS_FACTORY,
                mockData));
        assertThrows(IllegalArgumentException.class, () -> new CharacterImpl(
                UUID,
                CHARACTER_TYPE,
                null,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                VARIABLE_STATS_FACTORY,
                ENTITIES_OF_TYPE_FACTORY,
                STATUS_EFFECTS_FACTORY,
                mockData));
        assertThrows(IllegalArgumentException.class, () -> new CharacterImpl(
                UUID,
                CHARACTER_TYPE,
                CHARACTER_EVENTS_FACTORY,
                null,
                INVENTORY_FACTORY,
                VARIABLE_STATS_FACTORY,
                ENTITIES_OF_TYPE_FACTORY,
                STATUS_EFFECTS_FACTORY,
                mockData));
        assertThrows(IllegalArgumentException.class, () -> new CharacterImpl(
                UUID,
                CHARACTER_TYPE,
                CHARACTER_EVENTS_FACTORY,
                EQUIPMENT_SLOTS_FACTORY,
                null,
                VARIABLE_STATS_FACTORY,
                ENTITIES_OF_TYPE_FACTORY,
                STATUS_EFFECTS_FACTORY,
                mockData));
        assertThrows(IllegalArgumentException.class, () -> new CharacterImpl(
                UUID,
                CHARACTER_TYPE,
                CHARACTER_EVENTS_FACTORY,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                null,
                ENTITIES_OF_TYPE_FACTORY,
                STATUS_EFFECTS_FACTORY,
                mockData));
        assertThrows(IllegalArgumentException.class, () -> new CharacterImpl(
                UUID,
                CHARACTER_TYPE,
                CHARACTER_EVENTS_FACTORY,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                VARIABLE_STATS_FACTORY,
                null,
                STATUS_EFFECTS_FACTORY,
                mockData));
        assertThrows(IllegalArgumentException.class, () -> new CharacterImpl(
                UUID,
                CHARACTER_TYPE,
                CHARACTER_EVENTS_FACTORY,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                VARIABLE_STATS_FACTORY,
                ENTITIES_OF_TYPE_FACTORY,
                null,
                mockData));
        assertThrows(IllegalArgumentException.class, () -> new CharacterImpl(
                UUID,
                CHARACTER_TYPE,
                CHARACTER_EVENTS_FACTORY,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                VARIABLE_STATS_FACTORY,
                ENTITIES_OF_TYPE_FACTORY,
                STATUS_EFFECTS_FACTORY,
                null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(Character.class.getCanonicalName(), character.getInterfaceName());
    }

    @Test
    void testEquals() {
        var character2 = new CharacterImpl(
                UUID,
                CHARACTER_TYPE,
                CHARACTER_EVENTS_FACTORY,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                VARIABLE_STATS_FACTORY,
                ENTITIES_OF_TYPE_FACTORY,
                STATUS_EFFECTS_FACTORY,
                mockData);

        assertEquals(character, character2);
    }

    @Test
    void testCharacterType() {
        assertSame(CHARACTER_TYPE, character.type());
    }

    @Test
    void testClassifications() {
        assertNotNull(character.classifications());
    }

    @Test
    void testPronouns() {
        assertNotNull(character.pronouns());
    }

    @Test
    void testSetAndGetStance() {
        final var stance = randomString();

        character.setStance(stance);

        assertEquals(stance, character.getStance());
    }

    @Test
    void testSetAndGetDirection() {
        var direction = SOUTHWEST;

        character.setDirection(direction);

        assertEquals(direction, character.getDirection());
    }

    @Test
    void testSetAndGetSpriteSet() {
        var imageAssetSet = new FakeImageAssetSet();

        character.setImageAssetSet(imageAssetSet);

        assertEquals(imageAssetSet, character.getImageAssetSet());
    }

    @Test
    void testSetAndGetAIType() {
        var mockCharacterAIType = mock(CharacterAIType.class);

        assertNull(character.getAIType());

        character.setAIType(mockCharacterAIType);

        assertSame(mockCharacterAIType, character.getAIType());
    }

    @Test
    void testSetNullAIType() {
        assertThrows(IllegalArgumentException.class, () -> character.setAIType(null));
    }

    @Test
    void testCharacterEvents() {
        assertNotNull(character.events());
    }

    @Test
    void testEquipment() {
        assertSame(FakeCharacterEquipmentSlotsFactory.CHARACTER_EQUIPMENT_SLOTS,
                character.equipmentSlots());
    }

    @Test
    void testInventory() {
        assertNotNull(character.inventory());
    }

    @Test
    void testVariableStatistics() {
        assertNotNull(character.variableStatistics());
    }

    @Test
    void testStaticStatistics() {
        assertNotNull(character.staticStatistics());
    }

    @Test
    void testStatusEffects() {
        assertNotNull(character.statusEffects());
    }

    // TODO: Consider testing the ability methods a touch more robustly
    @Test
    void testPassiveAbilities() {
        assertNotNull(character.passiveAbilities());
    }

    @Test
    void testActiveAbilities() {
        assertNotNull(character.activeAbilities());
    }

    @Test
    void testReactiveAbilities() {
        assertNotNull(character.reactiveAbilities());
    }

    @Test
    void testSetAndGetPlayerControlled() {
        character.setPlayerControlled(true);
        assertTrue(character.getPlayerControlled());

        character.setPlayerControlled(false);
        assertFalse(character.getPlayerControlled());
    }

    @Test
    void testData() {
        assertSame(mockData, character.data());
    }

    @Test
    void testSetAndGetName() {
        var name = randomString();

        character.setName(name);

        assertEquals(name, character.getName());
    }

    @Test
    void testUuid() {
        assertSame(UUID, character.uuid());
    }

    @Test
    void testAssignCharacterToTile() {
        var tile = new FakeTile();
        assertNull(character.tile());

        // NB: Character.TILE should NOT be exposed, and calling Character.assignCharacterToTile
        // violates the invariant condition; therefore, FakeTileEntities calls
        // Character.assignCharacterToTile indirectly, as it should be in production code
        tile.characters().add(character);

        assertSame(tile, character.tile());
    }

    @SuppressWarnings({"rawtypes"})
    @Test
    void testDelete() {
        var tile = new FakeTile();

        tile.characters().add(character);

        var equipmentSlots = (FakeCharacterEquipmentSlots) character.equipmentSlots();

        var inventory = (FakeCharacterInventory) character.inventory();

        var variableStats = character.variableStatistics();

        var staticStats = character.staticStatistics();

        var statusEffects = character.statusEffects();

        assertFalse(character.isDeleted());
        assertFalse(((FakeTileEntities) tile.characters()).REMOVED_ENTITIES.contains(character));

        character.delete();

        assertTrue(character.isDeleted());
        var removedCharacter = (Character) ((FakeTileEntities) tile.characters()).REMOVED_ENTITIES.get(0);
        assertSame(character, removedCharacter);
        assertTrue(equipmentSlots._isDeleted);
        assertTrue(inventory._isDeleted);
        assertTrue(variableStats.isDeleted());
        assertTrue(staticStats.isDeleted());
        assertTrue(statusEffects.isDeleted());
    }

    @Test
    void testThrowsIllegalStateExceptionWhenDeleted() {
        character.delete();

        assertThrows(EntityDeletedException.class, () -> character.type());
        assertThrows(EntityDeletedException.class, () -> character.classifications());
        assertThrows(EntityDeletedException.class, () -> character.pronouns());
        assertThrows(EntityDeletedException.class, () -> character.tile());
        assertThrows(EntityDeletedException.class, () -> character.getStance());
        assertThrows(EntityDeletedException.class, () -> character.setStance(""));
        assertThrows(EntityDeletedException.class, () -> character.getDirection());
        assertThrows(EntityDeletedException.class, () -> character.setDirection(SOUTHWEST));
        assertThrows(EntityDeletedException.class, () -> character.getImageAssetSet());
        assertThrows(EntityDeletedException.class,
                () -> character.setImageAssetSet(new FakeImageAssetSet()));
        assertThrows(EntityDeletedException.class, () -> character.getAIType());
        assertThrows(EntityDeletedException.class, () -> character.setAIType(null));
        assertThrows(EntityDeletedException.class, () -> character.events());
        assertThrows(EntityDeletedException.class, () -> character.equipmentSlots());
        assertThrows(EntityDeletedException.class, () -> character.inventory());
        assertThrows(EntityDeletedException.class, () -> character.variableStatistics());
        assertThrows(EntityDeletedException.class, () -> character.staticStatistics());
        assertThrows(EntityDeletedException.class, () -> character.statusEffects());
        assertThrows(EntityDeletedException.class, () -> character.passiveAbilities());
        assertThrows(EntityDeletedException.class, () -> character.activeAbilities());
        assertThrows(EntityDeletedException.class, () -> character.reactiveAbilities());
        assertThrows(EntityDeletedException.class, () -> character.getPlayerControlled());
        assertThrows(EntityDeletedException.class, () -> character.setPlayerControlled(true));
        assertThrows(EntityDeletedException.class, () -> character.data());
        assertThrows(EntityDeletedException.class,
                () -> character.assignTileAfterAddedToTileEntitiesOfType(null));
        assertThrows(EntityDeletedException.class, () -> character.getName());
        assertThrows(EntityDeletedException.class, () -> character.setName(""));
        assertThrows(EntityDeletedException.class, () -> character.getInterfaceName());
    }

    @SuppressWarnings("rawtypes")
    @Test
    void testEnforceTileInvariant() {
        var tile = new FakeTile();
        tile.characters().add(character);
        ((FakeTileEntities) tile.characters()).ENTITIES.remove(character);

        assertThrows(IllegalStateException.class, () -> character.type());
        assertThrows(IllegalStateException.class, () -> character.classifications());
        assertThrows(IllegalStateException.class, () -> character.pronouns());
        assertThrows(IllegalStateException.class, () -> character.tile());
        assertThrows(IllegalStateException.class, () -> character.getStance());
        assertThrows(IllegalStateException.class, () -> character.setStance(""));
        assertThrows(IllegalStateException.class, () -> character.getDirection());
        assertThrows(IllegalStateException.class, () -> character.setDirection(SOUTHWEST));
        assertThrows(IllegalStateException.class, () -> character.getImageAssetSet());
        assertThrows(IllegalStateException.class,
                () -> character.setImageAssetSet(new FakeImageAssetSet()));
        assertThrows(IllegalStateException.class, () -> character.getAIType());
        assertThrows(IllegalStateException.class, () -> character.setAIType(null));
        assertThrows(IllegalStateException.class, () -> character.events());
        assertThrows(IllegalStateException.class, () -> character.equipmentSlots());
        assertThrows(IllegalStateException.class, () -> character.inventory());
        assertThrows(IllegalStateException.class, () -> character.variableStatistics());
        assertThrows(IllegalStateException.class, () -> character.staticStatistics());
        assertThrows(IllegalStateException.class, () -> character.statusEffects());
        assertThrows(IllegalStateException.class, () -> character.passiveAbilities());
        assertThrows(IllegalStateException.class, () -> character.activeAbilities());
        assertThrows(IllegalStateException.class, () -> character.reactiveAbilities());
        assertThrows(IllegalStateException.class, () -> character.getPlayerControlled());
        assertThrows(IllegalStateException.class, () -> character.setPlayerControlled(true));
        assertThrows(IllegalStateException.class, () -> character.data());
        assertThrows(IllegalStateException.class, () -> character.delete());
        assertThrows(IllegalStateException.class, () -> character.isDeleted());
        assertThrows(IllegalStateException.class,
                () -> character.assignTileAfterAddedToTileEntitiesOfType(null));
        assertThrows(IllegalStateException.class, () -> character.getName());
        assertThrows(IllegalStateException.class, () -> character.setName(""));
        assertThrows(IllegalStateException.class, () -> character.getInterfaceName());
    }
}

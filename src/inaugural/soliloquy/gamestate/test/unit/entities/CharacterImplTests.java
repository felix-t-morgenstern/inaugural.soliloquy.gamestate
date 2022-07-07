package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.CharacterImpl;
import inaugural.soliloquy.gamestate.test.fakes.*;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.gamestate.factories.*;
import soliloquy.specs.ruleset.entities.CharacterAIType;
import soliloquy.specs.ruleset.entities.CharacterStaticStatisticType;
import soliloquy.specs.ruleset.entities.CharacterType;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CharacterImplTests {
    private Character _character;

    private final UUID UUID = java.util.UUID.randomUUID();
    private final CharacterType CHARACTER_TYPE = new FakeCharacterType();
    private final CharacterAIType AI_TYPE = new FakeCharacterAIType();
    private final CharacterEventsFactory CHARACTER_EVENTS_FACTORY = new FakeCharacterEventsFactory();
    private final CharacterEquipmentSlotsFactory EQUIPMENT_SLOTS_FACTORY = new FakeCharacterEquipmentSlotsFactory();
    private final CharacterInventoryFactory INVENTORY_FACTORY = new FakeCharacterInventoryFactory();
    private final EntityMembersOfTypeFactory ENTITIES_OF_TYPE_FACTORY = new FakeEntityMembersOfTypeFactory();
    private final CharacterVariableStatisticsFactory VARIABLE_STATS_FACTORY = new FakeCharacterVariableStatisticsFactory();
    private final CharacterStatusEffectsFactory STATUS_EFFECTS_FACTORY = new FakeCharacterStatusEffectsFactory();
    private final VariableCache DATA = new VariableCacheStub();

    @BeforeEach
    void setUp() {

        _character = new CharacterImpl(
                UUID,
                CHARACTER_TYPE,
                CHARACTER_EVENTS_FACTORY,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                VARIABLE_STATS_FACTORY,
                ENTITIES_OF_TYPE_FACTORY,
                STATUS_EFFECTS_FACTORY,
                DATA);
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
                DATA));
        assertThrows(IllegalArgumentException.class, () -> new CharacterImpl(
                UUID,
                null,
                CHARACTER_EVENTS_FACTORY,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                VARIABLE_STATS_FACTORY,
                ENTITIES_OF_TYPE_FACTORY,
                STATUS_EFFECTS_FACTORY,
                DATA));
        assertThrows(IllegalArgumentException.class, () -> new CharacterImpl(
                UUID,
                CHARACTER_TYPE,
                null,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                VARIABLE_STATS_FACTORY,
                ENTITIES_OF_TYPE_FACTORY,
                STATUS_EFFECTS_FACTORY,
                DATA));
        assertThrows(IllegalArgumentException.class, () -> new CharacterImpl(
                UUID,
                CHARACTER_TYPE,
                CHARACTER_EVENTS_FACTORY,
                null,
                INVENTORY_FACTORY,
                VARIABLE_STATS_FACTORY,
                ENTITIES_OF_TYPE_FACTORY,
                STATUS_EFFECTS_FACTORY,
                DATA));
        assertThrows(IllegalArgumentException.class, () -> new CharacterImpl(
                UUID,
                CHARACTER_TYPE,
                CHARACTER_EVENTS_FACTORY,
                EQUIPMENT_SLOTS_FACTORY,
                null,
                VARIABLE_STATS_FACTORY,
                ENTITIES_OF_TYPE_FACTORY,
                STATUS_EFFECTS_FACTORY,
                DATA));
        assertThrows(IllegalArgumentException.class, () -> new CharacterImpl(
                UUID,
                CHARACTER_TYPE,
                CHARACTER_EVENTS_FACTORY,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                null,
                ENTITIES_OF_TYPE_FACTORY,
                STATUS_EFFECTS_FACTORY,
                DATA));
        assertThrows(IllegalArgumentException.class, () -> new CharacterImpl(
                UUID,
                CHARACTER_TYPE,
                CHARACTER_EVENTS_FACTORY,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                VARIABLE_STATS_FACTORY,
                null,
                STATUS_EFFECTS_FACTORY,
                DATA));
        assertThrows(IllegalArgumentException.class, () -> new CharacterImpl(
                UUID,
                CHARACTER_TYPE,
                CHARACTER_EVENTS_FACTORY,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                VARIABLE_STATS_FACTORY,
                ENTITIES_OF_TYPE_FACTORY,
                null,
                DATA));
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
        assertEquals(Character.class.getCanonicalName(), _character.getInterfaceName());
    }

    @Test

    void testEquals() {
        Character character2 = new CharacterImpl(
                UUID,
                CHARACTER_TYPE,
                CHARACTER_EVENTS_FACTORY,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                VARIABLE_STATS_FACTORY,
                ENTITIES_OF_TYPE_FACTORY,
                STATUS_EFFECTS_FACTORY,
                DATA);
        assertEquals(_character, character2);
    }

    @Test
    void testCharacterType() {
        assertSame(CHARACTER_TYPE, _character.type());
    }

    @Test
    void testClassifications() {
        assertNotNull(_character.classifications());
    }

    @Test
    void testPronouns() {
        assertNotNull(_character.pronouns());
    }

    @Test
    void testSetAndGetStance() {
        final String stance = "CharacterStance";
        _character.setStance(stance);

        assertEquals(stance, _character.getStance());
    }

    @Test
    void testSetAndGetDirection() {
        String direction = "CharacterDirection";
        _character.setDirection(direction);

        assertEquals(direction, _character.getDirection());
    }

    @Test
    void testSetAndGetSpriteSet() {
        FakeImageAssetSet imageAssetSet = new FakeImageAssetSet();
        _character.setImageAssetSet(imageAssetSet);

        assertEquals(imageAssetSet, _character.getImageAssetSet());
    }

    @Test
    void testSetAndGetAIType() {
        assertNull(_character.getAIType());

        _character.setAIType(AI_TYPE);

        assertSame(AI_TYPE, _character.getAIType());
    }

    @Test
    void testSetNullAIType() {
        assertThrows(IllegalArgumentException.class, () -> _character.setAIType(null));
    }

    @Test
    void testCharacterEvents() {
        assertNotNull(_character.events());
    }

    @Test
    void testEquipment() {
        assertSame(FakeCharacterEquipmentSlotsFactory.CHARACTER_EQUIPMENT_SLOTS,
                _character.equipmentSlots());
    }

    @Test
    void testInventory() {
        assertNotNull(_character.inventory());
    }

    @Test
    void testVariableStatistics() {
        assertNotNull(_character.variableStatistics());
    }

    @Test
    void testStaticStatistics() {
        assertNotNull(_character.staticStatistics());
    }

    @Test
    void testStatusEffects() {
        assertNotNull(_character.statusEffects());
    }

    // TODO: Consider testing the ability methods a touch more robustly
    @Test
    void testPassiveAbilities() {
        assertNotNull(_character.passiveAbilities());
    }

    @Test
    void testActiveAbilities() {
        assertNotNull(_character.activeAbilities());
    }

    @Test
    void testReactiveAbilities() {
        assertNotNull(_character.reactiveAbilities());
    }

    @Test
    void testSetAndGetPlayerControlled() {
        _character.setPlayerControlled(true);
        assertTrue(_character.getPlayerControlled());

        _character.setPlayerControlled(false);
        assertFalse(_character.getPlayerControlled());
    }

    @Test
    void testData() {
        assertSame(DATA, _character.data());
    }

    @Test
    void testSetAndGetName() {
        String CHARACTER_NAME = "CharacterName";
        _character.setName(CHARACTER_NAME);

        assertEquals(CHARACTER_NAME, _character.getName());
    }

    @Test
    void testUuid() {
        assertSame(UUID, _character.uuid());
    }

    @Test
    void testAssignCharacterToTile() {
        Tile tile = new FakeTile();
        assertNull(_character.tile());

        // NB: Character.TILE should NOT be exposed, and calling Character.assignCharacterToTile
        // violates the invariant condition; therefore, FakeTileEntities calls
        // Character.assignCharacterToTile indirectly, as it should be in production code
        tile.characters().add(_character);

        assertSame(tile, _character.tile());
    }

    @SuppressWarnings({"rawtypes"})
    @Test
    void testDelete() {
        Tile tile = new FakeTile();

        tile.characters().add(_character);

        FakeCharacterEquipmentSlots equipmentSlots =
                (FakeCharacterEquipmentSlots) _character.equipmentSlots();

        FakeCharacterInventory inventory = (FakeCharacterInventory) _character.inventory();

        CharacterVariableStatistics variableStats = _character.variableStatistics();

        EntityMembersOfType<CharacterStaticStatisticType,
                CharacterStatistic<CharacterStaticStatisticType>, Character>
                staticStats = _character.staticStatistics();

        CharacterStatusEffects statusEffects = _character.statusEffects();

        assertFalse(_character.isDeleted());
        assertFalse(((FakeTileEntities)tile.characters()).REMOVED_ENTITIES
                .contains(_character));

        _character.delete();

        assertTrue(_character.isDeleted());
        Character removedCharacter =
                (Character) ((FakeTileEntities)tile.characters()).REMOVED_ENTITIES.get(0);
        assertSame(_character, removedCharacter);
        assertTrue(equipmentSlots._isDeleted);
        assertTrue(inventory._isDeleted);
        assertTrue(variableStats.isDeleted());
        assertTrue(staticStats.isDeleted());
        assertTrue(statusEffects.isDeleted());
    }

    @Test
    void testThrowsIllegalStateExceptionWhenDeleted() {
        _character.delete();

        assertThrows(EntityDeletedException.class, () -> _character.type());
        assertThrows(EntityDeletedException.class, () -> _character.classifications());
        assertThrows(EntityDeletedException.class, () -> _character.pronouns());
        assertThrows(EntityDeletedException.class, () -> _character.tile());
        assertThrows(EntityDeletedException.class, () -> _character.getStance());
        assertThrows(EntityDeletedException.class, () -> _character.setStance(""));
        assertThrows(EntityDeletedException.class, () -> _character.getDirection());
        assertThrows(EntityDeletedException.class, () -> _character.setDirection(""));
        assertThrows(EntityDeletedException.class, () -> _character.getImageAssetSet());
        assertThrows(EntityDeletedException.class, () -> _character.setImageAssetSet(new FakeImageAssetSet()));
        assertThrows(EntityDeletedException.class, () -> _character.getAIType());
        assertThrows(EntityDeletedException.class, () -> _character.setAIType(null));
        assertThrows(EntityDeletedException.class, () -> _character.events());
        assertThrows(EntityDeletedException.class, () -> _character.equipmentSlots());
        assertThrows(EntityDeletedException.class, () -> _character.inventory());
        assertThrows(EntityDeletedException.class, () -> _character.variableStatistics());
        assertThrows(EntityDeletedException.class, () -> _character.staticStatistics());
        assertThrows(EntityDeletedException.class, () -> _character.statusEffects());
        assertThrows(EntityDeletedException.class, () -> _character.passiveAbilities());
        assertThrows(EntityDeletedException.class, () -> _character.activeAbilities());
        assertThrows(EntityDeletedException.class, () -> _character.reactiveAbilities());
        assertThrows(EntityDeletedException.class, () -> _character.getPlayerControlled());
        assertThrows(EntityDeletedException.class, () -> _character.setPlayerControlled(true));
        assertThrows(EntityDeletedException.class, () -> _character.data());
        assertThrows(EntityDeletedException.class, () -> _character.assignTileAfterAddedToTileEntitiesOfType(null));
        assertThrows(EntityDeletedException.class, () -> _character.getName());
        assertThrows(EntityDeletedException.class, () -> _character.setName(""));
        assertThrows(EntityDeletedException.class, () -> _character.getInterfaceName());
    }

    @SuppressWarnings("rawtypes")
    @Test
    void testEnforceTileInvariant() {
        FakeTile tile = new FakeTile();
        tile.characters().add(_character);
        ((FakeTileEntities)tile.characters()).ENTITIES.remove(_character);

        assertThrows(IllegalStateException.class, () -> _character.type());
        assertThrows(IllegalStateException.class, () -> _character.classifications());
        assertThrows(IllegalStateException.class, () -> _character.pronouns());
        assertThrows(IllegalStateException.class, () -> _character.tile());
        assertThrows(IllegalStateException.class, () -> _character.getStance());
        assertThrows(IllegalStateException.class, () -> _character.setStance(""));
        assertThrows(IllegalStateException.class, () -> _character.getDirection());
        assertThrows(IllegalStateException.class, () -> _character.setDirection(""));
        assertThrows(IllegalStateException.class, () -> _character.getImageAssetSet());
        assertThrows(IllegalStateException.class, () -> _character.setImageAssetSet(new FakeImageAssetSet()));
        assertThrows(IllegalStateException.class, () -> _character.getAIType());
        assertThrows(IllegalStateException.class, () -> _character.setAIType(null));
        assertThrows(IllegalStateException.class, () -> _character.events());
        assertThrows(IllegalStateException.class, () -> _character.equipmentSlots());
        assertThrows(IllegalStateException.class, () -> _character.inventory());
        assertThrows(IllegalStateException.class, () -> _character.variableStatistics());
        assertThrows(IllegalStateException.class, () -> _character.staticStatistics());
        assertThrows(IllegalStateException.class, () -> _character.statusEffects());
        assertThrows(IllegalStateException.class, () -> _character.passiveAbilities());
        assertThrows(IllegalStateException.class, () -> _character.activeAbilities());
        assertThrows(IllegalStateException.class, () -> _character.reactiveAbilities());
        assertThrows(IllegalStateException.class, () -> _character.getPlayerControlled());
        assertThrows(IllegalStateException.class, () -> _character.setPlayerControlled(true));
        assertThrows(IllegalStateException.class, () -> _character.data());
        assertThrows(IllegalStateException.class, () -> _character.delete());
        assertThrows(IllegalStateException.class, () -> _character.isDeleted());
        assertThrows(IllegalStateException.class, () -> _character.assignTileAfterAddedToTileEntitiesOfType(null));
        assertThrows(IllegalStateException.class, () -> _character.getName());
        assertThrows(IllegalStateException.class, () -> _character.setName(""));
        assertThrows(IllegalStateException.class, () -> _character.getInterfaceName());
    }
}

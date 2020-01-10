package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterImpl;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.factories.*;
import soliloquy.specs.ruleset.entities.CharacterAIType;
import soliloquy.specs.ruleset.entities.CharacterStaticStatisticType;
import soliloquy.specs.ruleset.entities.CharacterType;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbilityType;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbilityType;
import soliloquy.specs.sprites.entities.SpriteSet;

import static org.junit.jupiter.api.Assertions.*;

class CharacterImplTests {
    private Character _character;

    private final EntityUuid ID = new EntityUuidStub();
    private final CharacterType CHARACTER_TYPE = new CharacterTypeStub();
    private final CharacterAIType AI_TYPE = new CharacterAITypeStub();
    private final CharacterEventsFactory CHARACTER_EVENTS_FACTORY = new CharacterEventsFactoryStub();
    private final CharacterEquipmentSlotsFactory EQUIPMENT_SLOTS_FACTORY = new CharacterEquipmentSlotsFactoryStub();
    private final CharacterInventoryFactory INVENTORY_FACTORY = new CharacterInventoryFactoryStub();
    private final CharacterEntitiesOfTypeFactory ENTITIES_OF_TYPE_FACTORY = new CharacterEntitiesOfTypeFactoryStub();
    private final CharacterDepletableStatisticsFactory DEPLETABLE_STATS_FACTORY = new CharacterDepletableStatisticsFactoryStub();
    private final CharacterStatusEffectsFactory STATUS_EFFECTS_FACTORY = new CharacterStatusEffectsFactoryStub();
    private final GenericParamsSet GENERIC_PARAMS_SET = new GenericParamsSetStub();

    @BeforeEach
    void setUp() {

        _character = new CharacterImpl(
                ID,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                CHARACTER_EVENTS_FACTORY,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                DEPLETABLE_STATS_FACTORY,
                ENTITIES_OF_TYPE_FACTORY,
                STATUS_EFFECTS_FACTORY,
                GENERIC_PARAMS_SET);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new CharacterImpl(
                null,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                CHARACTER_EVENTS_FACTORY,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                DEPLETABLE_STATS_FACTORY,
                ENTITIES_OF_TYPE_FACTORY,
                STATUS_EFFECTS_FACTORY,
                GENERIC_PARAMS_SET));
        assertThrows(IllegalArgumentException.class, () -> new CharacterImpl(
                ID,
                null,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                CHARACTER_EVENTS_FACTORY,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                DEPLETABLE_STATS_FACTORY,
                ENTITIES_OF_TYPE_FACTORY,
                STATUS_EFFECTS_FACTORY,
                GENERIC_PARAMS_SET));
        assertThrows(IllegalArgumentException.class, () -> new CharacterImpl(
                ID,
                CHARACTER_TYPE,
                null,
                new MapFactoryStub(),
                CHARACTER_EVENTS_FACTORY,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                DEPLETABLE_STATS_FACTORY,
                ENTITIES_OF_TYPE_FACTORY,
                STATUS_EFFECTS_FACTORY,
                GENERIC_PARAMS_SET));
        assertThrows(IllegalArgumentException.class, () -> new CharacterImpl(
                ID,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                null,
                CHARACTER_EVENTS_FACTORY,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                DEPLETABLE_STATS_FACTORY,
                ENTITIES_OF_TYPE_FACTORY,
                STATUS_EFFECTS_FACTORY,
                GENERIC_PARAMS_SET));
        assertThrows(IllegalArgumentException.class, () -> new CharacterImpl(
                ID,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                null,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                DEPLETABLE_STATS_FACTORY,
                ENTITIES_OF_TYPE_FACTORY,
                STATUS_EFFECTS_FACTORY,
                GENERIC_PARAMS_SET));
        assertThrows(IllegalArgumentException.class, () -> new CharacterImpl(
                ID,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                CHARACTER_EVENTS_FACTORY,
                null,
                INVENTORY_FACTORY,
                DEPLETABLE_STATS_FACTORY,
                ENTITIES_OF_TYPE_FACTORY,
                STATUS_EFFECTS_FACTORY,
                GENERIC_PARAMS_SET));
        assertThrows(IllegalArgumentException.class, () -> new CharacterImpl(
                ID,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                CHARACTER_EVENTS_FACTORY,
                EQUIPMENT_SLOTS_FACTORY,
                null,
                DEPLETABLE_STATS_FACTORY,
                ENTITIES_OF_TYPE_FACTORY,
                STATUS_EFFECTS_FACTORY,
                GENERIC_PARAMS_SET));
        assertThrows(IllegalArgumentException.class, () -> new CharacterImpl(
                ID,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                CHARACTER_EVENTS_FACTORY,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                null,
                ENTITIES_OF_TYPE_FACTORY,
                STATUS_EFFECTS_FACTORY,
                GENERIC_PARAMS_SET));
        assertThrows(IllegalArgumentException.class, () -> new CharacterImpl(
                ID,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                CHARACTER_EVENTS_FACTORY,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                DEPLETABLE_STATS_FACTORY,
                null,
                STATUS_EFFECTS_FACTORY,
                GENERIC_PARAMS_SET));
        assertThrows(IllegalArgumentException.class, () -> new CharacterImpl(
                ID,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                CHARACTER_EVENTS_FACTORY,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                DEPLETABLE_STATS_FACTORY,
                ENTITIES_OF_TYPE_FACTORY,
                null,
                GENERIC_PARAMS_SET));
        assertThrows(IllegalArgumentException.class, () -> new CharacterImpl(
                ID,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                CHARACTER_EVENTS_FACTORY,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                DEPLETABLE_STATS_FACTORY,
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
                ID,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                CHARACTER_EVENTS_FACTORY,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                DEPLETABLE_STATS_FACTORY,
                ENTITIES_OF_TYPE_FACTORY,
                STATUS_EFFECTS_FACTORY,
                GENERIC_PARAMS_SET);
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
        SpriteSet spriteSet = new SpriteSetStub();
        _character.setSpriteSet(spriteSet);

        assertEquals(spriteSet, _character.getSpriteSet());
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
        assertSame(CharacterEquipmentSlotsFactoryStub.CHARACTER_EQUIPMENT_SLOTS,
                _character.equipmentSlots());
    }

    @Test
    void testInventory() {
        assertNotNull(_character.inventory());
    }

    @Test
    void testDepletableStatistics() {
        assertNotNull(_character.depletableStatistics());
    }

    @Test
    void testStaticStatistics() {
        assertNotNull(_character.staticStatistics());
    }

    @Test
    void testStatusEffects() {
        assertNotNull(_character.statusEffects());
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
        assertSame(GENERIC_PARAMS_SET, _character.data());
    }

    @Test
    void testSetAndGetName() {
        String CHARACTER_NAME = "CharacterName";
        _character.setName(CHARACTER_NAME);

        assertEquals(CHARACTER_NAME, _character.getName());
    }

    @Test
    void testId() {
        assertEquals(ID, _character.id());
    }

    @Test
    void testAssignCharacterToTile() {
        Tile tile = new TileStub();
        assertNull(_character.tile());

        // NB: Character.TILE should NOT be exposed, and calling Character.assignCharacterToTile
        // violates the invariant condition; therefore, TileEntitiesStub calls
        // Character.assignCharacterToTile indirectly, as it should be in production code
        tile.characters().add(_character);

        assertSame(tile, _character.tile());
    }

    @SuppressWarnings("unchecked")
    @Test
    void testDelete() {
        Tile tile = new TileStub();

        tile.characters().add(_character);

        CharacterEquipmentSlotsStub equipmentSlots =
                (CharacterEquipmentSlotsStub) _character.equipmentSlots();

        CharacterInventoryStub inventory = (CharacterInventoryStub) _character.inventory();

        CharacterDepletableStatistics depletableStats = _character.depletableStatistics();

        CharacterEntitiesOfType<CharacterStaticStatisticType, CharacterStaticStatistic>
                staticStats = _character.staticStatistics();

        CharacterStatusEffects statusEffects = _character.statusEffects();

        CharacterEntitiesOfType<ActiveAbilityType, CharacterAbility<ActiveAbilityType>>
                activeAbilities = _character.activeAbilities();

        CharacterEntitiesOfType<ReactiveAbilityType, CharacterAbility<ReactiveAbilityType>>
                reactiveAbilities = _character.reactiveAbilities();

        assertFalse(_character.isDeleted());
        assertFalse(((TileEntitiesStub)tile.characters()).REMOVED_ENTITIES
                .contains(_character));

        _character.delete();

        assertTrue(_character.isDeleted());
        Character removedCharacter =
                (Character) ((TileEntitiesStub)tile.characters()).REMOVED_ENTITIES.get(0);
        assertSame(_character, removedCharacter);
        assertTrue(equipmentSlots._isDeleted);
        assertTrue(inventory._isDeleted);
        assertTrue(depletableStats.isDeleted());
        assertTrue(staticStats.isDeleted());
        assertTrue(statusEffects.isDeleted());
        assertTrue(activeAbilities.isDeleted());
        assertTrue(reactiveAbilities.isDeleted());
    }

    @Test
    void testThrowsIllegalStateExceptionWhenDeleted() {
        _character.delete();

        assertThrows(IllegalStateException.class, () -> _character.type());
        assertThrows(IllegalStateException.class, () -> _character.classifications());
        assertThrows(IllegalStateException.class, () -> _character.pronouns());
        assertThrows(IllegalStateException.class, () -> _character.tile());
        assertThrows(IllegalStateException.class, () -> _character.getStance());
        assertThrows(IllegalStateException.class, () -> _character.setStance(""));
        assertThrows(IllegalStateException.class, () -> _character.getDirection());
        assertThrows(IllegalStateException.class, () -> _character.setDirection(""));
        assertThrows(IllegalStateException.class, () -> _character.getSpriteSet());
        assertThrows(IllegalStateException.class, () -> _character.setSpriteSet(new SpriteSetStub()));
        assertThrows(IllegalStateException.class, () -> _character.getAIType());
        assertThrows(IllegalStateException.class, () -> _character.setAIType(null));
        assertThrows(IllegalStateException.class, () -> _character.events());
        assertThrows(IllegalStateException.class, () -> _character.equipmentSlots());
        assertThrows(IllegalStateException.class, () -> _character.inventory());
        assertThrows(IllegalStateException.class, () -> _character.depletableStatistics());
        assertThrows(IllegalStateException.class, () -> _character.staticStatistics());
        assertThrows(IllegalStateException.class, () -> _character.statusEffects());
        assertThrows(IllegalStateException.class, () -> _character.activeAbilities());
        assertThrows(IllegalStateException.class, () -> _character.reactiveAbilities());
        assertThrows(IllegalStateException.class, () -> _character.getPlayerControlled());
        assertThrows(IllegalStateException.class, () -> _character.setPlayerControlled(true));
        assertThrows(IllegalStateException.class, () -> _character.data());
        assertThrows(IllegalStateException.class, () -> _character.assignTileAfterAddedToTileEntitiesOfType(null));
        assertThrows(IllegalStateException.class, () -> _character.getName());
        assertThrows(IllegalStateException.class, () -> _character.setName(""));
        assertThrows(IllegalStateException.class, () -> _character.getInterfaceName());
    }

    @Test
    void testEnforceTileInvariant() {
        TileStub tile = new TileStub();
        tile.characters().add(_character);
        ((TileEntitiesStub)tile.characters()).ENTITIES.remove(_character);

        assertThrows(IllegalStateException.class, () -> _character.type());
        assertThrows(IllegalStateException.class, () -> _character.classifications());
        assertThrows(IllegalStateException.class, () -> _character.pronouns());
        assertThrows(IllegalStateException.class, () -> _character.tile());
        assertThrows(IllegalStateException.class, () -> _character.getStance());
        assertThrows(IllegalStateException.class, () -> _character.setStance(""));
        assertThrows(IllegalStateException.class, () -> _character.getDirection());
        assertThrows(IllegalStateException.class, () -> _character.setDirection(""));
        assertThrows(IllegalStateException.class, () -> _character.getSpriteSet());
        assertThrows(IllegalStateException.class, () -> _character.setSpriteSet(new SpriteSetStub()));
        assertThrows(IllegalStateException.class, () -> _character.getAIType());
        assertThrows(IllegalStateException.class, () -> _character.setAIType(null));
        assertThrows(IllegalStateException.class, () -> _character.events());
        assertThrows(IllegalStateException.class, () -> _character.equipmentSlots());
        assertThrows(IllegalStateException.class, () -> _character.inventory());
        assertThrows(IllegalStateException.class, () -> _character.depletableStatistics());
        assertThrows(IllegalStateException.class, () -> _character.staticStatistics());
        assertThrows(IllegalStateException.class, () -> _character.statusEffects());
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

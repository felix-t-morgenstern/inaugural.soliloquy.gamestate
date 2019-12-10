package inaugural.soliloquy.gamestate.test.unit.persistenttypehandlers;

import inaugural.soliloquy.gamestate.persistentvaluetypehandlers.PersistentCharacterHandler;
import inaugural.soliloquy.gamestate.test.stubs.*;
import inaugural.soliloquy.gamestate.test.stubs.persistenttypehandlers.PersistentEntityUuidHandlerStub;
import inaugural.soliloquy.gamestate.test.stubs.persistenttypehandlers.PersistentGenericParamsSetHandlerStub;
import inaugural.soliloquy.gamestate.test.stubs.persistenttypehandlers.PersistentItemHandlerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterDepletableStatistic;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.gameevents.GameCharacterEvent;
import soliloquy.specs.gamestate.factories.CharacterFactory;
import soliloquy.specs.ruleset.entities.*;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbilityType;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbilityType;
import soliloquy.specs.ruleset.valueobjects.CharacterClassification;
import soliloquy.specs.sprites.entities.SpriteSet;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PersistentCharacterHandlerTests {
    private final CharacterFactory CHARACTER_FACTORY = new CharacterFactoryStub();

    private final PersistentValueTypeHandler<EntityUuid> ID_HANDLER = new PersistentEntityUuidHandlerStub();

    private final Map<String, CharacterType> CHARACTER_TYPES = new HashMap<>();
    private final String CHARACTER_TYPE_ID = "characterTypeId";
    private final CharacterType CHARACTER_TYPE = new CharacterTypeStub(CHARACTER_TYPE_ID);

    private final String CASE1 = "nominative";
    private final String CASE2 = "oblique";
    private final String CASE3 = "genitive";

    private final String ARTICLE1 = "they";
    private final String ARTICLE2 = "them";
    private final String ARTICLE3 = "theirs";

    private final String STANCE = "stance";

    private final String DIRECTION = "direction";

    private final Map<String, CharacterClassification> CLASSIFICATIONS = new HashMap<>();
    private final String CLASSIFICATION_ID = "classificationId";
    private final CharacterClassification CLASSIFICATION = new CharacterClassificationStub(CLASSIFICATION_ID);

    private final Map<String, SpriteSet> SPRITE_SETS = new HashMap<>();
    private final String SPRITE_SET_ID = "spriteSetId";
    private final SpriteSet SPRITE_SET = new SpriteSetStub(SPRITE_SET_ID);

    private final Map<String, CharacterAIType> AI_TYPES = new HashMap<>();
    private final String AI_TYPE_ID = "aiTypeId";
    private final CharacterAIType AI_TYPE = new CharacterAITypeStub(AI_TYPE_ID);

    private final Map<String, GameCharacterEvent> EVENTS = new HashMap<>();
    private final String EVENT_ID = "eventId";
    private final GameCharacterEvent EVENT = new GameCharacterEventStub(EVENT_ID);
    private final String TRIGGER = "trigger";

    private final String EQUIPMENT_SLOT_1 = "equipmentSlot1";
    private final String EQUIPMENT_SLOT_2 = "equipmentSlot2";

    private final Map<String, CharacterStatisticType> STAT_TYPES = new HashMap<>();
    private final String STAT_TYPE_ID = "statTypeId";
    private final CharacterStaticStatisticType STATIC_STAT_TYPE = new CharacterStaticStatisticTypeStub(STAT_TYPE_ID);

    private final Map<String, CharacterDepletableStatisticType> DEPLETABLE_STAT_TYPES = new HashMap<>();
    private final String DEPLETABLE_STAT_TYPE_ID = "depletableStatTypeId";
    private final CharacterDepletableStatisticType DEPLETABLE_STAT_TYPE = new CharacterDepletableStatisticTypeStub(DEPLETABLE_STAT_TYPE_ID);

    private final Map<String, StatusEffectType> STAT_EFFECT_TYPES = new HashMap<>();
    private final String STAT_EFFECT_TYPE_ID = "statEffectTypeId";
    private final StatusEffectType STAT_EFFECT_TYPE = new StatusEffectTypeStub(STAT_EFFECT_TYPE_ID);

    private final Map<String, ActiveAbilityType> ACTIVE_ABILITY_TYPES = new HashMap<>();
    private final String ACTIVE_ABILITY_TYPE_ID = "activeAbilityTypeId";
    private final ActiveAbilityType ACTIVE_ABILITY_TYPE = new ActiveAbilityTypeStub(ACTIVE_ABILITY_TYPE_ID);

    private final Map<String, ReactiveAbilityType> REACTIVE_ABILITY_TYPES = new HashMap<>();
    private final String REACTIVE_ABILITY_TYPE_ID = "reactiveAbilityTypeId";
    private final ReactiveAbilityType REACTIVE_ABILITY_TYPE = new ReactiveAbilityTypeStub(REACTIVE_ABILITY_TYPE_ID);

    private final PersistentValueTypeHandler<GenericParamsSet> DATA_HANDLER = new PersistentGenericParamsSetHandlerStub();
    private final PersistentValueTypeHandler<Item> ITEM_HANDLER = new PersistentItemHandlerStub();

    private final String WRITTEN_VALUE = "{\"id\":\"EntityUuid0\",\"characterTypeId\":\"characterTypeId\",\"pronouns\":[{\"key\":\"oblique\",\"val\":\"them\"},{\"key\":\"genitive\",\"val\":\"theirs\"},{\"key\":\"nominative\",\"val\":\"they\"}],\"stance\":\"stance\",\"direction\":\"direction\",\"spriteSetId\":\"spriteSetId\",\"aiTypeId\":\"aiTypeId\",\"events\":[{\"trigger\":\"trigger\",\"events\":[\"eventId\"]}],\"equipmentSlots\":[{\"key\":\"equipmentSlot2\"},{\"key\":\"equipmentSlot1\",\"val\":\"Item0\"}],\"inventoryItems\":[\"Item1\"],\"depletableStats\":[{\"typeId\":\"depletableStatTypeId\",\"value\":135}],\"stats\":[\"statTypeId\"],\"statusEffects\":[{\"typeId\":\"statEffectTypeId\",\"value\":246}],\"activeAbilities\":[{\"typeId\":\"activeAbilityTypeId\",\"isHidden\":false}],\"reactiveAbilities\":[{\"typeId\":\"reactiveAbilityTypeId\",\"isHidden\":false}],\"isPlayerControlled\":true,\"data\":\"GenericParamsSet0\"}";

    private PersistentValueTypeHandler<Character> _characterHandler;

    @BeforeEach
    void setUp() {
        CHARACTER_TYPES.put(CHARACTER_TYPE.id(), CHARACTER_TYPE);
        CLASSIFICATIONS.put(CLASSIFICATION.id(), CLASSIFICATION);
        SPRITE_SETS.put(SPRITE_SET.id(), SPRITE_SET);
        AI_TYPES.put(AI_TYPE.id(), AI_TYPE);
        EVENTS.put(EVENT_ID, EVENT);
        STAT_TYPES.put(STATIC_STAT_TYPE.id(), STATIC_STAT_TYPE);
        DEPLETABLE_STAT_TYPES.put(DEPLETABLE_STAT_TYPE.id(), DEPLETABLE_STAT_TYPE);
        STAT_EFFECT_TYPES.put(STAT_EFFECT_TYPE.id(), STAT_EFFECT_TYPE);
        ACTIVE_ABILITY_TYPES.put(ACTIVE_ABILITY_TYPE.id(), ACTIVE_ABILITY_TYPE);
        REACTIVE_ABILITY_TYPES.put(REACTIVE_ABILITY_TYPE.id(), REACTIVE_ABILITY_TYPE);

        _characterHandler = new PersistentCharacterHandler(CHARACTER_FACTORY, ID_HANDLER,
                CHARACTER_TYPES::get, CLASSIFICATIONS::get, SPRITE_SETS::get, AI_TYPES::get,
                EVENTS::get, STAT_TYPES::get, DEPLETABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                ITEM_HANDLER);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(null, ID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, SPRITE_SETS::get, AI_TYPES::get, EVENTS::get,
                        STAT_TYPES::get, DEPLETABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(CHARACTER_FACTORY, null, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, SPRITE_SETS::get, AI_TYPES::get, EVENTS::get,
                        STAT_TYPES::get, DEPLETABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(CHARACTER_FACTORY, ID_HANDLER, null,
                        CLASSIFICATIONS::get, SPRITE_SETS::get, AI_TYPES::get, EVENTS::get,
                        STAT_TYPES::get, DEPLETABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(CHARACTER_FACTORY, ID_HANDLER, CHARACTER_TYPES::get,
                        null, SPRITE_SETS::get, AI_TYPES::get, EVENTS::get,
                        STAT_TYPES::get, DEPLETABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(CHARACTER_FACTORY, ID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, null, AI_TYPES::get, EVENTS::get,
                        STAT_TYPES::get, DEPLETABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(CHARACTER_FACTORY, ID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, SPRITE_SETS::get, null, EVENTS::get,
                        STAT_TYPES::get, DEPLETABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(CHARACTER_FACTORY, ID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, SPRITE_SETS::get, AI_TYPES::get, null,
                        STAT_TYPES::get, DEPLETABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(CHARACTER_FACTORY, ID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, SPRITE_SETS::get, AI_TYPES::get, EVENTS::get,
                        STAT_TYPES::get, null, STAT_EFFECT_TYPES::get,
                        ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(CHARACTER_FACTORY, ID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, SPRITE_SETS::get, AI_TYPES::get, EVENTS::get,
                        null, DEPLETABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(CHARACTER_FACTORY, ID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, SPRITE_SETS::get, AI_TYPES::get, EVENTS::get,
                        STAT_TYPES::get, DEPLETABLE_STAT_TYPES::get, null,
                        ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(CHARACTER_FACTORY, ID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, SPRITE_SETS::get, AI_TYPES::get, EVENTS::get,
                        STAT_TYPES::get, DEPLETABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        null, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(CHARACTER_FACTORY, ID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, SPRITE_SETS::get, AI_TYPES::get, EVENTS::get,
                        STAT_TYPES::get, DEPLETABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        ACTIVE_ABILITY_TYPES::get, null, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(CHARACTER_FACTORY, ID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, SPRITE_SETS::get, AI_TYPES::get, EVENTS::get,
                        STAT_TYPES::get, DEPLETABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, null,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(CHARACTER_FACTORY, ID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, SPRITE_SETS::get, AI_TYPES::get, EVENTS::get,
                        STAT_TYPES::get, DEPLETABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                        null));
    }

    @SuppressWarnings("unchecked")
    @Test
    void testWrite() {
        EntityUuid id = new EntityUuidStub();
        Character character = new CharacterStub(id, CHARACTER_TYPE);
        character.classifications().add(CLASSIFICATION);
        character.pronouns().put(CASE1, ARTICLE1);
        character.pronouns().put(CASE2, ARTICLE2);
        character.pronouns().put(CASE3, ARTICLE3);
        character.setStance(STANCE);
        character.setDirection(DIRECTION);
        character.setSpriteSet(SPRITE_SET);
        character.setAIType(AI_TYPE);
        character.events().addEvent("trigger", EVENT);
        CharacterEquipmentSlotsStub.EQUIPMENT_SLOTS.clear();
        Item equipmentSlotItem = new ItemStub();
        character.equipmentSlots().addCharacterEquipmentSlot(EQUIPMENT_SLOT_1);
        character.equipmentSlots().addCharacterEquipmentSlot(EQUIPMENT_SLOT_2);
        character.equipmentSlots().equipItemToSlot(EQUIPMENT_SLOT_1, equipmentSlotItem);
        Item inventoryItem = new ItemStub();
        character.inventory().add(inventoryItem);
        character.depletableStatistics().add(DEPLETABLE_STAT_TYPE);
        CharacterDepletableStatistic depletableStat =
                character.depletableStatistics().get(DEPLETABLE_STAT_TYPE);
        depletableStat.setCurrentValue(135);
        character.staticStatistics().add(STATIC_STAT_TYPE);
        ((CharacterStatusEffectsStub)character.statusEffects())._representation.clear();
        ((CharacterStatusEffectsStub)character.statusEffects())._representation
                .put(STAT_EFFECT_TYPE, 246);
        character.activeAbilities().add(ACTIVE_ABILITY_TYPE);
        character.reactiveAbilities().add(REACTIVE_ABILITY_TYPE);
        character.setPlayerControlled(true);

        String writtenValue = _characterHandler.write(character);

        assertEquals(WRITTEN_VALUE, writtenValue);
        assertSame(id, ((PersistentEntityUuidHandlerStub)ID_HANDLER).WRITE_INPUTS.get(0));
        assertSame(character.data(),
                ((PersistentGenericParamsSetHandlerStub)DATA_HANDLER).WRITE_INPUTS.get(0));
    }

    @Test
    void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _characterHandler.write(null));
    }

    @Test
    void testRead() {
        CharacterEquipmentSlotsStub.EQUIPMENT_SLOTS.clear();
        Character readCharacter = _characterHandler.read(WRITTEN_VALUE);

        assertNotNull(readCharacter);
        assertEquals("EntityUuid0",
                ((PersistentEntityUuidHandlerStub)ID_HANDLER).READ_INPUTS.get(0));
        assertSame(((PersistentEntityUuidHandlerStub)ID_HANDLER).READ_OUTPUTS.get(0),
                readCharacter.id());
        assertSame(CHARACTER_TYPE, readCharacter.type());
        assertEquals(3, readCharacter.pronouns().size());
        assertEquals(ARTICLE1, readCharacter.pronouns().get(CASE1));
        assertEquals(ARTICLE2, readCharacter.pronouns().get(CASE2));
        assertEquals(ARTICLE3, readCharacter.pronouns().get(CASE3));
        assertEquals(STANCE, readCharacter.getStance());
        assertEquals(DIRECTION, readCharacter.getDirection());
        assertSame(SPRITE_SET, readCharacter.getSpriteSet());
        assertSame(AI_TYPE, readCharacter.getAIType());
        assertEquals(1, readCharacter.events().representation().size());
        assertEquals(1, readCharacter.events().representation().get(TRIGGER).size());
        assertSame(EVENT, readCharacter.events().representation().get(TRIGGER).get(0));
        assertEquals(2, readCharacter.equipmentSlots().representation().size());
        assertTrue(((PersistentItemHandlerStub)ITEM_HANDLER).READ_OUTPUTS.contains(
                readCharacter.equipmentSlots().itemInSlot(EQUIPMENT_SLOT_1)));
        assertNull(readCharacter.equipmentSlots().itemInSlot(EQUIPMENT_SLOT_2));
        assertEquals(1, readCharacter.inventory().representation().size());
        assertTrue(((PersistentItemHandlerStub)ITEM_HANDLER).READ_OUTPUTS.contains(
                readCharacter.inventory().representation().get(0)));
    }
}

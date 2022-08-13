package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.CharacterHandler;
import inaugural.soliloquy.gamestate.test.fakes.*;
import inaugural.soliloquy.gamestate.test.fakes.persistence.FakeItemHandler;
import inaugural.soliloquy.gamestate.test.fakes.persistence.FakeUuidHandler;
import inaugural.soliloquy.gamestate.test.fakes.persistence.FakeVariableCacheHandler;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistic;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.gameevents.GameCharacterEvent;
import soliloquy.specs.gamestate.factories.CharacterFactory;
import soliloquy.specs.graphics.assets.ImageAssetSet;
import soliloquy.specs.ruleset.entities.*;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbility;
import soliloquy.specs.ruleset.entities.abilities.PassiveAbility;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbility;
import soliloquy.specs.ruleset.valueobjects.CharacterClassification;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CharacterHandlerTests {
    private final CharacterFactory CHARACTER_FACTORY = new FakeCharacterFactory();

    private final TypeHandler<UUID> UUID_HANDLER = new FakeUuidHandler();

    private final Map<String, CharacterType> CHARACTER_TYPES = new HashMap<>();
    private final String CHARACTER_TYPE_ID = "characterTypeId";
    private final CharacterType CHARACTER_TYPE = new FakeCharacterType(CHARACTER_TYPE_ID);

    private final String CASE_1 = "nominative";
    private final String CASE_2 = "oblique";
    private final String CASE_3 = "genitive";
    private final String CASE_4 = "reflexive";

    private final String ARTICLE_1 = "they";
    private final String ARTICLE_2 = "them";
    private final String ARTICLE_3 = "theirs";
    private final String ARTICLE_4 = "themselves";

    private final String STANCE = "stance";

    private final String DIRECTION = "direction";

    private final Map<String, CharacterClassification> CLASSIFICATIONS = new HashMap<>();
    private final String CLASSIFICATION_ID = "classificationId";
    private final CharacterClassification CLASSIFICATION =
            new FakeCharacterClassification(CLASSIFICATION_ID);

    private final Map<String, ImageAssetSet> IMAGE_ASSET_SETS = new HashMap<>();
    private final String IMAGE_ASSET_SET_ID = "imageAssetSetId";
    private final FakeImageAssetSet IMAGE_ASSET_SET = new FakeImageAssetSet(IMAGE_ASSET_SET_ID);

    private final Map<String, CharacterAIType> AI_TYPES = new HashMap<>();
    private final String AI_TYPE_ID = "aiTypeId";
    private final CharacterAIType AI_TYPE = new FakeCharacterAIType(AI_TYPE_ID);

    private final Map<String, GameCharacterEvent> EVENTS = new HashMap<>();
    private final String EVENT_ID = "eventId";
    private final GameCharacterEvent EVENT = new FakeGameCharacterEvent(EVENT_ID);
    private final String TRIGGER = "trigger";

    private final String EQUIPMENT_SLOT_1 = "equipmentSlot1";
    private final String EQUIPMENT_SLOT_2 = "equipmentSlot2";

    private final Map<String, CharacterStaticStatisticType> STATIC_STAT_TYPES = new HashMap<>();
    private final String STATIC_STAT_TYPE_ID = "staticStatTypeId";
    private final CharacterStaticStatisticType STATIC_STAT_TYPE =
            new FakeCharacterStaticStatisticType(STATIC_STAT_TYPE_ID);

    private final Map<String, CharacterVariableStatisticType> VARIABLE_STAT_TYPES =
            new HashMap<>();
    private final String VARIABLE_STAT_TYPE_ID = "variableStatTypeId";
    private final CharacterVariableStatisticType VARIABLE_STAT_TYPE =
            new FakeCharacterVariableStatisticType(VARIABLE_STAT_TYPE_ID);

    private final Map<String, StatusEffectType> STAT_EFFECT_TYPES = new HashMap<>();
    private final String STAT_EFFECT_TYPE_ID = "statEffectTypeId";
    private final StatusEffectType STAT_EFFECT_TYPE =
            new FakeStatusEffectType(STAT_EFFECT_TYPE_ID);

    private final Map<String, PassiveAbility> PASSIVE_ABILITIES = new HashMap<>();
    private final String PASSIVE_ABILITY_ID = "passiveAbilityId";
    private final PassiveAbility PASSIVE_ABILITY = new FakePassiveAbility(PASSIVE_ABILITY_ID);

    private final Map<String, ActiveAbility> ACTIVE_ABILITIES = new HashMap<>();
    private final String ACTIVE_ABILITY_ID = "activeAbilityId";
    private final ActiveAbility ACTIVE_ABILITY = new FakeActiveAbility(ACTIVE_ABILITY_ID);

    private final Map<String, ReactiveAbility> REACTIVE_ABILITIES = new HashMap<>();
    private final String REACTIVE_ABILITY_ID = "reactiveAbilityId";
    private final ReactiveAbility REACTIVE_ABILITY = new FakeReactiveAbility(REACTIVE_ABILITY_ID);

    private final VariableCache DATA = new VariableCacheStub();

    private final String NAME = "charName";

    private final TypeHandler<VariableCache> DATA_HANDLER =
            new FakeVariableCacheHandler();
    private final TypeHandler<Item> ITEM_HANDLER = new FakeItemHandler();

    private final String WRITTEN_VALUE =
            "{\"uuid\":\"UUID0\",\"characterTypeId\":\"characterTypeId\"," +
                    "\"classifications\":[\"classificationId\"]," +
                    "\"pronouns\":[{\"key\":\"oblique\",\"val\":\"them\"},{\"key\":\"reflexive\"," +
                    "\"val\":\"themselves\"},{\"key\":\"genitive\",\"val\":\"theirs\"}," +
                    "{\"key\":\"nominative\",\"val\":\"they\"}],\"stance\":\"stance\"," +
                    "\"direction\":\"direction\",\"assetSetId\":\"imageAssetSetId\"," +
                    "\"aiTypeId\":\"aiTypeId\",\"events\":[{\"trigger\":\"trigger\"," +
                    "\"events\":[\"eventId\"]}],\"equipmentSlots\":[{\"key\":\"equipmentSlot2\"}," +
                    "{\"key\":\"equipmentSlot1\",\"val\":\"Item0\"}]," +
                    "\"inventoryItems\":[\"Item1\"],\"variableStats\":[{\"current\":135," +
                    "\"type\":\"variableStatTypeId\",\"data\":\"VariableCache0\"}]," +
                    "\"staticStats\":[{\"type\":\"staticStatTypeId\"," +
                    "\"data\":\"VariableCache1\"}]," +
                    "\"statusEffects\":[{\"type\":\"statEffectTypeId\",\"value\":246}]," +
                    "\"passiveAbilityIds\":[\"passiveAbilityId\"]," +
                    "\"activeAbilityIds\":[\"activeAbilityId\"]," +
                    "\"reactiveAbilityIds\":[\"reactiveAbilityId\"],\"isPlayerControlled\":true," +
                    "\"data\":\"VariableCache2\",\"name\":\"charName\"}";

    private TypeHandler<Character> _characterHandler;

    @BeforeEach
    void setUp() {
        CHARACTER_TYPES.put(CHARACTER_TYPE.id(), CHARACTER_TYPE);
        CLASSIFICATIONS.put(CLASSIFICATION.id(), CLASSIFICATION);
        IMAGE_ASSET_SETS.put(IMAGE_ASSET_SET.id(), IMAGE_ASSET_SET);
        AI_TYPES.put(AI_TYPE.id(), AI_TYPE);
        EVENTS.put(EVENT_ID, EVENT);
        STATIC_STAT_TYPES.put(STATIC_STAT_TYPE.id(), STATIC_STAT_TYPE);
        VARIABLE_STAT_TYPES.put(VARIABLE_STAT_TYPE.id(), VARIABLE_STAT_TYPE);
        STAT_EFFECT_TYPES.put(STAT_EFFECT_TYPE.id(), STAT_EFFECT_TYPE);
        PASSIVE_ABILITIES.put(PASSIVE_ABILITY.id(), PASSIVE_ABILITY);
        ACTIVE_ABILITIES.put(ACTIVE_ABILITY.id(), ACTIVE_ABILITY);
        REACTIVE_ABILITIES.put(REACTIVE_ABILITY.id(), REACTIVE_ABILITY);

        _characterHandler = new CharacterHandler(CHARACTER_FACTORY, UUID_HANDLER,
                CHARACTER_TYPES::get, CLASSIFICATIONS::get, IMAGE_ASSET_SETS::get, AI_TYPES::get,
                EVENTS::get, STATIC_STAT_TYPES::get, VARIABLE_STAT_TYPES::get,
                STAT_EFFECT_TYPES::get, PASSIVE_ABILITIES::get, ACTIVE_ABILITIES::get,
                REACTIVE_ABILITIES::get, DATA_HANDLER, ITEM_HANDLER);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterHandler(null, UUID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, IMAGE_ASSET_SETS::get, AI_TYPES::get, EVENTS::get,
                        STATIC_STAT_TYPES::get, VARIABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        PASSIVE_ABILITIES::get, ACTIVE_ABILITIES::get, REACTIVE_ABILITIES::get,
                        DATA_HANDLER, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterHandler(CHARACTER_FACTORY, null, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, IMAGE_ASSET_SETS::get, AI_TYPES::get, EVENTS::get,
                        STATIC_STAT_TYPES::get, VARIABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        PASSIVE_ABILITIES::get, ACTIVE_ABILITIES::get, REACTIVE_ABILITIES::get,
                        DATA_HANDLER, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterHandler(CHARACTER_FACTORY, UUID_HANDLER, null,
                        CLASSIFICATIONS::get, IMAGE_ASSET_SETS::get, AI_TYPES::get, EVENTS::get,
                        STATIC_STAT_TYPES::get, VARIABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        PASSIVE_ABILITIES::get, ACTIVE_ABILITIES::get, REACTIVE_ABILITIES::get,
                        DATA_HANDLER, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterHandler(CHARACTER_FACTORY, UUID_HANDLER, CHARACTER_TYPES::get,
                        null, IMAGE_ASSET_SETS::get, AI_TYPES::get, EVENTS::get,
                        STATIC_STAT_TYPES::get, VARIABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        PASSIVE_ABILITIES::get, ACTIVE_ABILITIES::get, REACTIVE_ABILITIES::get,
                        DATA_HANDLER, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterHandler(CHARACTER_FACTORY, UUID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, null, AI_TYPES::get, EVENTS::get,
                        STATIC_STAT_TYPES::get, VARIABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        PASSIVE_ABILITIES::get, ACTIVE_ABILITIES::get, REACTIVE_ABILITIES::get,
                        DATA_HANDLER, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterHandler(CHARACTER_FACTORY, UUID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, IMAGE_ASSET_SETS::get, null, EVENTS::get,
                        STATIC_STAT_TYPES::get, VARIABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        PASSIVE_ABILITIES::get, ACTIVE_ABILITIES::get, REACTIVE_ABILITIES::get,
                        DATA_HANDLER, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterHandler(CHARACTER_FACTORY, UUID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, IMAGE_ASSET_SETS::get, AI_TYPES::get, null,
                        STATIC_STAT_TYPES::get, VARIABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        PASSIVE_ABILITIES::get, ACTIVE_ABILITIES::get, REACTIVE_ABILITIES::get,
                        DATA_HANDLER, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterHandler(CHARACTER_FACTORY, UUID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, IMAGE_ASSET_SETS::get, AI_TYPES::get, EVENTS::get,
                        STATIC_STAT_TYPES::get, null, STAT_EFFECT_TYPES::get,
                        PASSIVE_ABILITIES::get, ACTIVE_ABILITIES::get, REACTIVE_ABILITIES::get,
                        DATA_HANDLER, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterHandler(CHARACTER_FACTORY, UUID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, IMAGE_ASSET_SETS::get, AI_TYPES::get, EVENTS::get,
                        null, VARIABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        PASSIVE_ABILITIES::get, ACTIVE_ABILITIES::get, REACTIVE_ABILITIES::get,
                        DATA_HANDLER, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterHandler(CHARACTER_FACTORY, UUID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, IMAGE_ASSET_SETS::get, AI_TYPES::get, EVENTS::get,
                        STATIC_STAT_TYPES::get, VARIABLE_STAT_TYPES::get, null,
                        PASSIVE_ABILITIES::get, ACTIVE_ABILITIES::get, REACTIVE_ABILITIES::get,
                        DATA_HANDLER, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterHandler(CHARACTER_FACTORY, UUID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, IMAGE_ASSET_SETS::get, AI_TYPES::get, EVENTS::get,
                        STATIC_STAT_TYPES::get, VARIABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        null, ACTIVE_ABILITIES::get, REACTIVE_ABILITIES::get,
                        DATA_HANDLER, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterHandler(CHARACTER_FACTORY, UUID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, IMAGE_ASSET_SETS::get, AI_TYPES::get, EVENTS::get,
                        STATIC_STAT_TYPES::get, VARIABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        PASSIVE_ABILITIES::get, null, REACTIVE_ABILITIES::get,
                        DATA_HANDLER, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterHandler(CHARACTER_FACTORY, UUID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, IMAGE_ASSET_SETS::get, AI_TYPES::get, EVENTS::get,
                        STATIC_STAT_TYPES::get, VARIABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        PASSIVE_ABILITIES::get, ACTIVE_ABILITIES::get, null,
                        DATA_HANDLER, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterHandler(CHARACTER_FACTORY, UUID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, IMAGE_ASSET_SETS::get, AI_TYPES::get, EVENTS::get,
                        STATIC_STAT_TYPES::get, VARIABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        PASSIVE_ABILITIES::get, ACTIVE_ABILITIES::get, REACTIVE_ABILITIES::get,
                        null, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterHandler(CHARACTER_FACTORY, UUID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, IMAGE_ASSET_SETS::get, AI_TYPES::get, EVENTS::get,
                        STATIC_STAT_TYPES::get, VARIABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        PASSIVE_ABILITIES::get, ACTIVE_ABILITIES::get, REACTIVE_ABILITIES::get,
                        DATA_HANDLER, null));
    }

    @Test
    void testWrite() {
        UUID uuid = UUID.randomUUID();
        Character character = new FakeCharacter(uuid, CHARACTER_TYPE, DATA);
        character.classifications().add(CLASSIFICATION);
        character.pronouns().put(CASE_1, ARTICLE_1);
        character.pronouns().put(CASE_2, ARTICLE_2);
        character.pronouns().put(CASE_3, ARTICLE_3);
        character.pronouns().put(CASE_4, ARTICLE_4);
        character.setStance(STANCE);
        character.setDirection(DIRECTION);
        character.setImageAssetSet(IMAGE_ASSET_SET);
        character.setAIType(AI_TYPE);
        character.events().addEvent(TRIGGER, EVENT);
        FakeCharacterEquipmentSlots.EQUIPMENT_SLOTS.clear();
        Item equipmentSlotItem = new FakeItem();
        character.equipmentSlots().addCharacterEquipmentSlot(EQUIPMENT_SLOT_1);
        character.equipmentSlots().addCharacterEquipmentSlot(EQUIPMENT_SLOT_2);
        character.equipmentSlots().equipItemToSlot(EQUIPMENT_SLOT_1, equipmentSlotItem);
        Item inventoryItem = new FakeItem();
        character.inventory().add(inventoryItem);
        character.variableStatistics().add(VARIABLE_STAT_TYPE);
        CharacterVariableStatistic variableStat =
                character.variableStatistics().get(VARIABLE_STAT_TYPE);
        variableStat.setCurrentValue(135);
        character.staticStatistics().add(STATIC_STAT_TYPE);
        ((FakeCharacterStatusEffects) character.statusEffects())._representation.clear();
        ((FakeCharacterStatusEffects) character.statusEffects())._representation
                .put(STAT_EFFECT_TYPE, 246);
        character.passiveAbilities().add(PASSIVE_ABILITY);
        character.activeAbilities().add(ACTIVE_ABILITY);
        character.reactiveAbilities().add(REACTIVE_ABILITY);
        character.setPlayerControlled(true);
        character.setName(NAME);

        String writtenValue = _characterHandler.write(character);

        assertEquals(WRITTEN_VALUE, writtenValue);
        assertSame(uuid, ((FakeUuidHandler) UUID_HANDLER).WRITE_INPUTS.get(0));
        assertSame(character.variableStatistics().get(VARIABLE_STAT_TYPE).data(),
                ((FakeVariableCacheHandler) DATA_HANDLER).WRITE_INPUTS.get(0));
        assertSame(character.staticStatistics().get(STATIC_STAT_TYPE).data(),
                ((FakeVariableCacheHandler) DATA_HANDLER).WRITE_INPUTS.get(1));
        assertSame(character.data(),
                ((FakeVariableCacheHandler) DATA_HANDLER).WRITE_INPUTS.get(2));
    }

    @Test
    void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _characterHandler.write(null));
    }

    @Test
    void testRead() {
        FakeCharacterEquipmentSlots.EQUIPMENT_SLOTS.clear();
        Character readCharacter = _characterHandler.read(WRITTEN_VALUE);

        assertNotNull(readCharacter);
        assertEquals("UUID0",
                ((FakeUuidHandler) UUID_HANDLER).READ_INPUTS.get(0));
        assertSame(((FakeUuidHandler) UUID_HANDLER).READ_OUTPUTS.get(0),
                readCharacter.uuid());
        assertSame(CHARACTER_TYPE, readCharacter.type());
        assertEquals(1, readCharacter.classifications().size());
        assertTrue(readCharacter.classifications().contains(CLASSIFICATION));
        assertEquals(4, readCharacter.pronouns().size());
        assertEquals(ARTICLE_1, readCharacter.pronouns().get(CASE_1));
        assertEquals(ARTICLE_2, readCharacter.pronouns().get(CASE_2));
        assertEquals(ARTICLE_3, readCharacter.pronouns().get(CASE_3));
        assertEquals(ARTICLE_4, readCharacter.pronouns().get(CASE_4));
        assertEquals(STANCE, readCharacter.getStance());
        assertEquals(DIRECTION, readCharacter.getDirection());
        assertSame(IMAGE_ASSET_SET, readCharacter.getImageAssetSet());
        assertSame(AI_TYPE, readCharacter.getAIType());
        assertEquals(1, readCharacter.events().representation().size());
        assertEquals(1, readCharacter.events().representation().get(TRIGGER).size());
        assertSame(EVENT, readCharacter.events().representation().get(TRIGGER).get(0));

        assertEquals(2, readCharacter.equipmentSlots().representation().size());
        assertTrue(((FakeItemHandler) ITEM_HANDLER).READ_OUTPUTS.contains(
                readCharacter.equipmentSlots().itemInSlot(EQUIPMENT_SLOT_1)));
        assertNull(readCharacter.equipmentSlots().itemInSlot(EQUIPMENT_SLOT_2));
        assertEquals(1, readCharacter.inventory().representation().size());
        assertTrue(((FakeItemHandler) ITEM_HANDLER).READ_OUTPUTS.contains(
                readCharacter.inventory().representation().get(0)));

        assertEquals(1, readCharacter.variableStatistics().representation().size());
        assertEquals(135,
                readCharacter.variableStatistics().get(VARIABLE_STAT_TYPE).getCurrentValue());
        assertEquals("VariableCache0",
                ((FakeVariableCacheHandler) DATA_HANDLER).READ_INPUTS.get(1));
        assertSame(((FakeVariableCacheHandler) DATA_HANDLER).READ_OUTPUTS.get(1),
                readCharacter.variableStatistics().get(VARIABLE_STAT_TYPE).data());

        assertEquals(1, readCharacter.staticStatistics().representation().size());
        assertSame(STATIC_STAT_TYPE,
                readCharacter.staticStatistics().representation().get(0).type());
        assertEquals("VariableCache1",
                ((FakeVariableCacheHandler) DATA_HANDLER).READ_INPUTS.get(2));
        assertSame(((FakeVariableCacheHandler) DATA_HANDLER).READ_OUTPUTS.get(2),
                readCharacter.staticStatistics().get(STATIC_STAT_TYPE).data());

        assertEquals(1, readCharacter.statusEffects().representation().size());
        assertEquals(246,
                readCharacter.statusEffects().getStatusEffectLevel(STAT_EFFECT_TYPE));

        assertEquals(1, readCharacter.passiveAbilities().size());
        assertSame(PASSIVE_ABILITY, readCharacter.passiveAbilities().get(0));

        assertEquals(1, readCharacter.activeAbilities().size());
        assertSame(ACTIVE_ABILITY, readCharacter.activeAbilities().get(0));

        assertEquals(1, readCharacter.reactiveAbilities().size());
        assertSame(REACTIVE_ABILITY, readCharacter.reactiveAbilities().get(0));

        assertTrue(readCharacter.getPlayerControlled());
        assertEquals("VariableCache2",
                ((FakeVariableCacheHandler) DATA_HANDLER).READ_INPUTS.get(0));
        assertSame(((FakeVariableCacheHandler) DATA_HANDLER).READ_OUTPUTS.get(0),
                readCharacter.data());
        assertEquals(NAME, readCharacter.getName());
    }

    @Test
    void testArchetype() {
        assertNotNull(_characterHandler.getArchetype());
        assertEquals(Character.class.getCanonicalName(),
                _characterHandler.getArchetype().getInterfaceName());
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        Character.class.getCanonicalName() + ">",
                _characterHandler.getInterfaceName());
    }
}

package inaugural.soliloquy.gamestate.test.unit.persistenttypehandlers;

import inaugural.soliloquy.gamestate.persistentvaluetypehandlers.PersistentCharacterHandler;
import inaugural.soliloquy.gamestate.test.fakes.*;
import inaugural.soliloquy.gamestate.test.fakes.persistenttypehandlers.FakePersistentEntityUuidHandler;
import inaugural.soliloquy.gamestate.test.fakes.persistenttypehandlers.FakePersistentItemHandler;
import inaugural.soliloquy.gamestate.test.fakes.persistenttypehandlers.FakePersistentVariableCacheHandler;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistic;
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
    private final CharacterFactory CHARACTER_FACTORY = new FakeCharacterFactory();

    private final PersistentValueTypeHandler<EntityUuid> ID_HANDLER = new FakePersistentEntityUuidHandler();

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
    private final CharacterClassification CLASSIFICATION = new FakeCharacterClassification(CLASSIFICATION_ID);

    private final Map<String, SpriteSet> SPRITE_SETS = new HashMap<>();
    private final String SPRITE_SET_ID = "spriteSetId";
    private final SpriteSet SPRITE_SET = new FakeSpriteSet(SPRITE_SET_ID);

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
    private final CharacterStaticStatisticType STATIC_STAT_TYPE = new FakeCharacterStaticStatisticType(STATIC_STAT_TYPE_ID);

    private final Map<String, CharacterVariableStatisticType> VARIABLE_STAT_TYPES = new HashMap<>();
    private final String VARIABLE_STAT_TYPE_ID = "variableStatTypeId";
    private final CharacterVariableStatisticType VARIABLE_STAT_TYPE = new FakeCharacterVariableStatisticType(VARIABLE_STAT_TYPE_ID);

    private final Map<String, StatusEffectType> STAT_EFFECT_TYPES = new HashMap<>();
    private final String STAT_EFFECT_TYPE_ID = "statEffectTypeId";
    private final StatusEffectType STAT_EFFECT_TYPE = new FakeStatusEffectType(STAT_EFFECT_TYPE_ID);

    private final Map<String, ActiveAbilityType> ACTIVE_ABILITY_TYPES = new HashMap<>();
    private final String ACTIVE_ABILITY_TYPE_ID = "activeAbilityTypeId";
    private final ActiveAbilityType ACTIVE_ABILITY_TYPE = new FakeActiveAbilityType(ACTIVE_ABILITY_TYPE_ID);

    private final Map<String, ReactiveAbilityType> REACTIVE_ABILITY_TYPES = new HashMap<>();
    private final String REACTIVE_ABILITY_TYPE_ID = "reactiveAbilityTypeId";
    private final ReactiveAbilityType REACTIVE_ABILITY_TYPE = new FakeReactiveAbilityType(REACTIVE_ABILITY_TYPE_ID);

    private final VariableCache DATA = new VariableCacheStub();

    private final String NAME = "charName";

    private final PersistentValueTypeHandler<VariableCache> DATA_HANDLER =
            new FakePersistentVariableCacheHandler();
    private final PersistentValueTypeHandler<Item> ITEM_HANDLER = new FakePersistentItemHandler();

    private final String WRITTEN_VALUE = "{\"id\":\"EntityUuid0\",\"characterTypeId\":\"characterTypeId\",\"classifications\":[\"classificationId\"],\"pronouns\":[{\"key\":\"oblique\",\"val\":\"them\"},{\"key\":\"reflexive\",\"val\":\"themselves\"},{\"key\":\"genitive\",\"val\":\"theirs\"},{\"key\":\"nominative\",\"val\":\"they\"}],\"stance\":\"stance\",\"direction\":\"direction\",\"spriteSetId\":\"spriteSetId\",\"aiTypeId\":\"aiTypeId\",\"events\":[{\"trigger\":\"trigger\",\"events\":[\"eventId\"]}],\"equipmentSlots\":[{\"key\":\"equipmentSlot2\"},{\"key\":\"equipmentSlot1\",\"val\":\"Item0\"}],\"inventoryItems\":[\"Item1\"],\"variableStats\":[{\"current\":135,\"type\":\"variableStatTypeId\",\"data\":\"VariableCache0\"}],\"staticStats\":[{\"type\":\"staticStatTypeId\",\"data\":\"VariableCache1\"}],\"statusEffects\":[{\"type\":\"statEffectTypeId\",\"value\":246}],\"activeAbilities\":[{\"type\":\"activeAbilityTypeId\",\"data\":\"VariableCache2\"}],\"reactiveAbilities\":[{\"type\":\"reactiveAbilityTypeId\",\"data\":\"VariableCache3\"}],\"isPlayerControlled\":true,\"data\":\"VariableCache4\",\"name\":\"charName\"}";

    private PersistentValueTypeHandler<Character> _characterHandler;

    @BeforeEach
    void setUp() {
        CHARACTER_TYPES.put(CHARACTER_TYPE.id(), CHARACTER_TYPE);
        CLASSIFICATIONS.put(CLASSIFICATION.id(), CLASSIFICATION);
        SPRITE_SETS.put(SPRITE_SET.id(), SPRITE_SET);
        AI_TYPES.put(AI_TYPE.id(), AI_TYPE);
        EVENTS.put(EVENT_ID, EVENT);
        STATIC_STAT_TYPES.put(STATIC_STAT_TYPE.id(), STATIC_STAT_TYPE);
        VARIABLE_STAT_TYPES.put(VARIABLE_STAT_TYPE.id(), VARIABLE_STAT_TYPE);
        STAT_EFFECT_TYPES.put(STAT_EFFECT_TYPE.id(), STAT_EFFECT_TYPE);
        ACTIVE_ABILITY_TYPES.put(ACTIVE_ABILITY_TYPE.id(), ACTIVE_ABILITY_TYPE);
        REACTIVE_ABILITY_TYPES.put(REACTIVE_ABILITY_TYPE.id(), REACTIVE_ABILITY_TYPE);

        _characterHandler = new PersistentCharacterHandler(CHARACTER_FACTORY, ID_HANDLER,
                CHARACTER_TYPES::get, CLASSIFICATIONS::get, SPRITE_SETS::get, AI_TYPES::get,
                EVENTS::get, STATIC_STAT_TYPES::get, VARIABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                ITEM_HANDLER);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(null, ID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, SPRITE_SETS::get, AI_TYPES::get, EVENTS::get,
                        STATIC_STAT_TYPES::get, VARIABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(CHARACTER_FACTORY, null, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, SPRITE_SETS::get, AI_TYPES::get, EVENTS::get,
                        STATIC_STAT_TYPES::get, VARIABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(CHARACTER_FACTORY, ID_HANDLER, null,
                        CLASSIFICATIONS::get, SPRITE_SETS::get, AI_TYPES::get, EVENTS::get,
                        STATIC_STAT_TYPES::get, VARIABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(CHARACTER_FACTORY, ID_HANDLER, CHARACTER_TYPES::get,
                        null, SPRITE_SETS::get, AI_TYPES::get, EVENTS::get,
                        STATIC_STAT_TYPES::get, VARIABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(CHARACTER_FACTORY, ID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, null, AI_TYPES::get, EVENTS::get,
                        STATIC_STAT_TYPES::get, VARIABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(CHARACTER_FACTORY, ID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, SPRITE_SETS::get, null, EVENTS::get,
                        STATIC_STAT_TYPES::get, VARIABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(CHARACTER_FACTORY, ID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, SPRITE_SETS::get, AI_TYPES::get, null,
                        STATIC_STAT_TYPES::get, VARIABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(CHARACTER_FACTORY, ID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, SPRITE_SETS::get, AI_TYPES::get, EVENTS::get,
                        STATIC_STAT_TYPES::get, null, STAT_EFFECT_TYPES::get,
                        ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(CHARACTER_FACTORY, ID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, SPRITE_SETS::get, AI_TYPES::get, EVENTS::get,
                        null, VARIABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(CHARACTER_FACTORY, ID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, SPRITE_SETS::get, AI_TYPES::get, EVENTS::get,
                        STATIC_STAT_TYPES::get, VARIABLE_STAT_TYPES::get, null,
                        ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(CHARACTER_FACTORY, ID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, SPRITE_SETS::get, AI_TYPES::get, EVENTS::get,
                        STATIC_STAT_TYPES::get, VARIABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        null, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(CHARACTER_FACTORY, ID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, SPRITE_SETS::get, AI_TYPES::get, EVENTS::get,
                        STATIC_STAT_TYPES::get, VARIABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        ACTIVE_ABILITY_TYPES::get, null, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(CHARACTER_FACTORY, ID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, SPRITE_SETS::get, AI_TYPES::get, EVENTS::get,
                        STATIC_STAT_TYPES::get, VARIABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, null,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(CHARACTER_FACTORY, ID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, SPRITE_SETS::get, AI_TYPES::get, EVENTS::get,
                        STATIC_STAT_TYPES::get, VARIABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                        null));
    }

    @Test
    void testWrite() {
        EntityUuid id = new FakeEntityUuid();
        Character character = new FakeCharacter(id, CHARACTER_TYPE, DATA);
        character.classifications().add(CLASSIFICATION);
        character.pronouns().put(CASE_1, ARTICLE_1);
        character.pronouns().put(CASE_2, ARTICLE_2);
        character.pronouns().put(CASE_3, ARTICLE_3);
        character.pronouns().put(CASE_4, ARTICLE_4);
        character.setStance(STANCE);
        character.setDirection(DIRECTION);
        character.setSpriteSet(SPRITE_SET);
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
        ((FakeCharacterStatusEffects)character.statusEffects())._representation.clear();
        ((FakeCharacterStatusEffects)character.statusEffects())._representation
                .put(STAT_EFFECT_TYPE, 246);
        character.activeAbilities().add(ACTIVE_ABILITY_TYPE);
        //noinspection rawtypes
        ((FakeCharacterAbility)character.activeAbilities().get(ACTIVE_ABILITY_TYPE))._data =
                new VariableCacheStub();
        character.reactiveAbilities().add(REACTIVE_ABILITY_TYPE);
        //noinspection rawtypes
        ((FakeCharacterAbility)character.reactiveAbilities().get(REACTIVE_ABILITY_TYPE))._data =
                new VariableCacheStub();
        character.setPlayerControlled(true);
        character.setName(NAME);

        String writtenValue = _characterHandler.write(character);

        assertEquals(WRITTEN_VALUE, writtenValue);
        assertSame(id, ((FakePersistentEntityUuidHandler)ID_HANDLER).WRITE_INPUTS.get(0));
        assertSame(character.variableStatistics().get(VARIABLE_STAT_TYPE).data(),
                ((FakePersistentVariableCacheHandler)DATA_HANDLER).WRITE_INPUTS.get(0));
        assertSame(character.staticStatistics().get(STATIC_STAT_TYPE).data(),
                ((FakePersistentVariableCacheHandler)DATA_HANDLER).WRITE_INPUTS.get(1));
        assertSame(character.activeAbilities().get(ACTIVE_ABILITY_TYPE).data(),
                ((FakePersistentVariableCacheHandler)DATA_HANDLER).WRITE_INPUTS.get(2));
        assertSame(character.reactiveAbilities().get(REACTIVE_ABILITY_TYPE).data(),
                ((FakePersistentVariableCacheHandler)DATA_HANDLER).WRITE_INPUTS.get(3));
        assertSame(character.data(),
                ((FakePersistentVariableCacheHandler)DATA_HANDLER).WRITE_INPUTS.get(4));
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
        assertEquals("EntityUuid0",
                ((FakePersistentEntityUuidHandler)ID_HANDLER).READ_INPUTS.get(0));
        assertSame(((FakePersistentEntityUuidHandler)ID_HANDLER).READ_OUTPUTS.get(0),
                readCharacter.id());
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
        assertSame(SPRITE_SET, readCharacter.getSpriteSet());
        assertSame(AI_TYPE, readCharacter.getAIType());
        assertEquals(1, readCharacter.events().representation().size());
        assertEquals(1, readCharacter.events().representation().get(TRIGGER).size());
        assertSame(EVENT, readCharacter.events().representation().get(TRIGGER).get(0));

        assertEquals(2, readCharacter.equipmentSlots().representation().size());
        assertTrue(((FakePersistentItemHandler)ITEM_HANDLER).READ_OUTPUTS.contains(
                readCharacter.equipmentSlots().itemInSlot(EQUIPMENT_SLOT_1)));
        assertNull(readCharacter.equipmentSlots().itemInSlot(EQUIPMENT_SLOT_2));
        assertEquals(1, readCharacter.inventory().representation().size());
        assertTrue(((FakePersistentItemHandler)ITEM_HANDLER).READ_OUTPUTS.contains(
                readCharacter.inventory().representation().get(0)));

        assertEquals(1, readCharacter.variableStatistics().representation().size());
        assertEquals(135,
                readCharacter.variableStatistics().get(VARIABLE_STAT_TYPE).getCurrentValue());
        assertEquals("VariableCache0",
                ((FakePersistentVariableCacheHandler)DATA_HANDLER).READ_INPUTS.get(1));
        assertSame(((FakePersistentVariableCacheHandler)DATA_HANDLER).READ_OUTPUTS.get(1),
                readCharacter.variableStatistics().get(VARIABLE_STAT_TYPE).data());

        assertEquals(1, readCharacter.staticStatistics().representation().size());
        assertSame(STATIC_STAT_TYPE,
                readCharacter.staticStatistics().representation().get(0).type());
        assertEquals("VariableCache1",
                ((FakePersistentVariableCacheHandler)DATA_HANDLER).READ_INPUTS.get(2));
        assertSame(((FakePersistentVariableCacheHandler)DATA_HANDLER).READ_OUTPUTS.get(2),
                readCharacter.staticStatistics().get(STATIC_STAT_TYPE).data());

        assertEquals(1, readCharacter.statusEffects().representation().size());
        assertEquals(246,
                readCharacter.statusEffects().getStatusEffectLevel(STAT_EFFECT_TYPE));

        assertEquals(1, readCharacter.activeAbilities().size());
        assertEquals("VariableCache2",
                ((FakePersistentVariableCacheHandler)DATA_HANDLER).READ_INPUTS.get(3));
        assertSame(((FakePersistentVariableCacheHandler)DATA_HANDLER).READ_OUTPUTS.get(3),
                readCharacter.activeAbilities().get(ACTIVE_ABILITY_TYPE).data());

        assertEquals(1, readCharacter.reactiveAbilities().size());
        assertEquals("VariableCache3",
                ((FakePersistentVariableCacheHandler)DATA_HANDLER).READ_INPUTS.get(4));
        assertSame(((FakePersistentVariableCacheHandler)DATA_HANDLER).READ_OUTPUTS.get(4),
                readCharacter.reactiveAbilities().get(REACTIVE_ABILITY_TYPE).data());

        assertTrue(readCharacter.getPlayerControlled());
        assertEquals("VariableCache4",
                ((FakePersistentVariableCacheHandler)DATA_HANDLER).READ_INPUTS.get(0));
        assertSame(((FakePersistentVariableCacheHandler)DATA_HANDLER).READ_OUTPUTS.get(0),
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
        assertEquals(PersistentValueTypeHandler.class.getCanonicalName() + "<" +
                        Character.class.getCanonicalName() + ">",
                _characterHandler.getInterfaceName());
    }
}

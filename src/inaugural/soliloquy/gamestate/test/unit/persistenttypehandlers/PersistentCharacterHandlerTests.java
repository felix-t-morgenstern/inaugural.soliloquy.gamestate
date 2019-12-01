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
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.gameevents.GameCharacterEvent;
import soliloquy.specs.ruleset.entities.*;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbilityType;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbilityType;
import soliloquy.specs.ruleset.valueobjects.CharacterClassification;
import soliloquy.specs.sprites.entities.SpriteSet;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PersistentCharacterHandlerTests {
    private final PersistentValueTypeHandler<EntityUuid> ID_HANDLER = new PersistentEntityUuidHandlerStub();

    private final Map<String, CharacterType> CHARACTER_TYPES = new HashMap<>();
    private final String CHARACTER_TYPE_ID = "characterTypeId";
    private final CharacterType CHARACTER_TYPE = new CharacterTypeStub(CHARACTER_TYPE_ID);

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

    private final Map<String, CharacterStatisticType> STAT_TYPES = new HashMap<>();
    private final String STAT_TYPE_ID = "statTypeId";
    private final CharacterStatisticType STAT_TYPE = new CharacterStatisticTypeStub(STAT_TYPE_ID);

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

    private final String WRITTEN_VALUE = "";

    private PersistentValueTypeHandler<Character> _characterHandler;

    @BeforeEach
    void setUp() {
        CHARACTER_TYPES.put(CHARACTER_TYPE.id(), CHARACTER_TYPE);
        CLASSIFICATIONS.put(CLASSIFICATION.id(), CLASSIFICATION);
        SPRITE_SETS.put(SPRITE_SET.id(), SPRITE_SET);
        AI_TYPES.put(AI_TYPE.id(), AI_TYPE);
        STAT_TYPES.put(STAT_TYPE.id(), STAT_TYPE);
        DEPLETABLE_STAT_TYPES.put(DEPLETABLE_STAT_TYPE.id(), DEPLETABLE_STAT_TYPE);
        STAT_EFFECT_TYPES.put(STAT_EFFECT_TYPE.id(), STAT_EFFECT_TYPE);
        ACTIVE_ABILITY_TYPES.put(ACTIVE_ABILITY_TYPE.id(), ACTIVE_ABILITY_TYPE);
        REACTIVE_ABILITY_TYPES.put(REACTIVE_ABILITY_TYPE.id(), REACTIVE_ABILITY_TYPE);

        _characterHandler = new PersistentCharacterHandler(ID_HANDLER, CHARACTER_TYPES::get,
                CLASSIFICATIONS::get, SPRITE_SETS::get, AI_TYPES::get, STAT_TYPES::get,
                DEPLETABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get, ACTIVE_ABILITY_TYPES::get,
                REACTIVE_ABILITY_TYPES::get, DATA_HANDLER, ITEM_HANDLER);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(null, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, SPRITE_SETS::get, AI_TYPES::get, STAT_TYPES::get,
                        DEPLETABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(ID_HANDLER, null,
                        CLASSIFICATIONS::get, SPRITE_SETS::get, AI_TYPES::get, STAT_TYPES::get,
                        DEPLETABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(ID_HANDLER, CHARACTER_TYPES::get,
                        null, SPRITE_SETS::get, AI_TYPES::get, STAT_TYPES::get,
                        DEPLETABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(ID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, null, AI_TYPES::get, STAT_TYPES::get,
                        DEPLETABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(ID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, SPRITE_SETS::get, null, STAT_TYPES::get,
                        DEPLETABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(ID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, SPRITE_SETS::get, AI_TYPES::get, null,
                        DEPLETABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(ID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, SPRITE_SETS::get, AI_TYPES::get, STAT_TYPES::get,
                        null, STAT_EFFECT_TYPES::get,
                        ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(ID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, SPRITE_SETS::get, AI_TYPES::get, STAT_TYPES::get,
                        DEPLETABLE_STAT_TYPES::get, null,
                        ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(ID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, SPRITE_SETS::get, AI_TYPES::get, STAT_TYPES::get,
                        DEPLETABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        null, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(ID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, SPRITE_SETS::get, AI_TYPES::get, STAT_TYPES::get,
                        DEPLETABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        ACTIVE_ABILITY_TYPES::get, null, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(ID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, SPRITE_SETS::get, AI_TYPES::get, STAT_TYPES::get,
                        DEPLETABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, null,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentCharacterHandler(ID_HANDLER, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, SPRITE_SETS::get, AI_TYPES::get, STAT_TYPES::get,
                        DEPLETABLE_STAT_TYPES::get, STAT_EFFECT_TYPES::get,
                        ACTIVE_ABILITY_TYPES::get, REACTIVE_ABILITY_TYPES::get, DATA_HANDLER,
                        null));
    }

    @Test
    void testWrite() {
        EntityUuid id = new EntityUuidStub();
        Character character = new CharacterStub(id, CHARACTER_TYPE);
        character.classifications().add(CLASSIFICATION);
        character.pronouns().put("nominative", "they");
        character.pronouns().put("oblique", "them");
        character.pronouns().put("genitive", "theirs");
        character.setStance("stance");
        character.setDirection("direction");
        character.setAIType(AI_TYPE);
    }
}

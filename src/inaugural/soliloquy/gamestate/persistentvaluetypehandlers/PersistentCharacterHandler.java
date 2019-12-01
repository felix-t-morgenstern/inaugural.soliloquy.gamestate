package inaugural.soliloquy.gamestate.persistentvaluetypehandlers;

import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.ruleset.entities.*;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbilityType;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbilityType;
import soliloquy.specs.ruleset.valueobjects.CharacterClassification;
import soliloquy.specs.sprites.entities.SpriteSet;

import java.util.function.Function;

public class PersistentCharacterHandler implements PersistentValueTypeHandler<Character> {
    private final PersistentValueTypeHandler<EntityUuid> ID_HANDLER;
    private final Function<String, CharacterType> GET_CHARACTER_TYPE;
    private final Function<String, CharacterClassification> GET_CHARACTER_CLASSIFICATION;
    private final Function<String, SpriteSet> GET_SPRITE_SET;
    private final Function<String, CharacterAIType> GET_AI_TYPE;
    private final Function<String, CharacterStatisticType> GET_STAT_TYPE;
    private final Function<String, CharacterDepletableStatisticType> GET_DEPLETABLE_STAT_TYPE;
    private final Function<String, StatusEffectType> GET_STATUS_TYPE;
    private final Function<String, ActiveAbilityType> GET_ACTIVE_ABILITY_TYPE;
    private final Function<String, ReactiveAbilityType> GET_REACTIVE_ABILITY_TYPE;
    private final PersistentValueTypeHandler<GenericParamsSet> DATA_HANDLER;
    private final PersistentValueTypeHandler<Item> ITEM_HANDLER;

    @SuppressWarnings("ConstantConditions")
    public PersistentCharacterHandler(PersistentValueTypeHandler<EntityUuid> idHandler,
                                      Function<String, CharacterType> getCharacterType,
                                      Function<String, CharacterClassification>
                                              getCharacterClassification,
                                      Function<String, SpriteSet> getSpriteSet,
                                      Function<String, CharacterAIType> getAIType,
                                      Function<String, CharacterStatisticType> getStatType,
                                      Function<String, CharacterDepletableStatisticType>
                                              getDepletableStatType,
                                      Function<String, StatusEffectType> getStatusType,
                                      Function<String, ActiveAbilityType> getActiveAbilityType,
                                      Function<String, ReactiveAbilityType> getReactiveAbilityType,
                                      PersistentValueTypeHandler<GenericParamsSet> dataHandler,
                                      PersistentValueTypeHandler<Item> itemHandler) {
        if (idHandler == null) {
            throw new IllegalArgumentException(
                    "PersistentCharacterHandler: idHandler cannot be null");
        }
        ID_HANDLER = idHandler;
        if (getCharacterType == null) {
            throw new IllegalArgumentException(
                    "PersistentCharacterHandler: getCharacterType cannot be null");
        }
        GET_CHARACTER_TYPE = getCharacterType;
        if (getCharacterClassification == null) {
            throw new IllegalArgumentException(
                    "PersistentCharacterHandler: getCharacterClassification cannot be null");
        }
        GET_CHARACTER_CLASSIFICATION = getCharacterClassification;
        if (getSpriteSet == null) {
            throw new IllegalArgumentException(
                    "PersistentCharacterHandler: getSpriteSet cannot be null");
        }
        GET_SPRITE_SET = getSpriteSet;
        if (getAIType == null) {
            throw new IllegalArgumentException(
                    "PersistentCharacterHandler: getAIType cannot be null");
        }
        GET_AI_TYPE = getAIType;
        if (getStatType == null) {
            throw new IllegalArgumentException(
                    "PersistentCharacterHandler: getStatType cannot be null");
        }
        GET_STAT_TYPE = getStatType;
        if (getDepletableStatType == null) {
            throw new IllegalArgumentException(
                    "PersistentCharacterHandler: getDepletableStatType cannot be null");
        }
        GET_DEPLETABLE_STAT_TYPE = getDepletableStatType;
        if (getStatusType == null) {
            throw new IllegalArgumentException(
                    "PersistentCharacterHandler: getStatusType cannot be null");
        }
        GET_STATUS_TYPE = getStatusType;
        if (getActiveAbilityType == null) {
            throw new IllegalArgumentException(
                    "PersistentCharacterHandler: getActiveAbilityType cannot be null");
        }
        GET_ACTIVE_ABILITY_TYPE = getActiveAbilityType;
        if (getReactiveAbilityType == null) {
            throw new IllegalArgumentException(
                    "PersistentCharacterHandler: getReactiveAbilityType cannot be null");
        }
        GET_REACTIVE_ABILITY_TYPE = getReactiveAbilityType;
        if (dataHandler == null) {
            throw new IllegalArgumentException(
                    "PersistentCharacterHandler: dataHandler cannot be null");
        }
        DATA_HANDLER = dataHandler;
        if (itemHandler == null) {
            throw new IllegalArgumentException(
                    "PersistentCharacterHandler: itemHandler cannot be null");
        }
        ITEM_HANDLER = itemHandler;
    }

    @Override
    public Character read(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String write(Character character) {
        return null;
    }

    @Override
    public Character getArchetype() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    private class CharacterDTO {
        String id;
        String characterTypeId;
        CharacterPronounDTO[] pronouns;
        String stance;
        String direction;
        String spriteSetId;
        CharacterEventDTO[] events;
        CharacterEntityWithTypeAndValueDTO[] depletableStats;
        String[] stats;
        CharacterEntityWithTypeAndValueDTO[] statusEffects;
        CharacterAbilityDTO[] activeAbilities;
        CharacterAbilityDTO[] reactiveAbilities;
        boolean isPlayerControlled;
        String data;
        String name;

        private class CharacterEventDTO {
            String eventName;
            String[] events;
        }
        private class CharacterPronounDTO {
            String gramCase;
            String pronoun;
        }
        private class CharacterEntityWithTypeAndValueDTO {
            String typeId;
            int value;
        }
        private class CharacterAbilityDTO {
            String typeId;
            boolean isHidden;
        }
    }
}

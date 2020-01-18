package inaugural.soliloquy.gamestate.persistentvaluetypehandlers;

import com.google.gson.Gson;
import soliloquy.specs.common.infrastructure.*;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.gameevents.GameCharacterEvent;
import soliloquy.specs.gamestate.factories.CharacterFactory;
import soliloquy.specs.ruleset.entities.*;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbilityType;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbilityType;
import soliloquy.specs.ruleset.valueobjects.CharacterClassification;
import soliloquy.specs.sprites.entities.SpriteSet;

import java.util.function.Function;

public class PersistentCharacterHandler implements PersistentValueTypeHandler<Character> {
    private final CharacterFactory CHARACTER_FACTORY;
    private final PersistentValueTypeHandler<EntityUuid> ID_HANDLER;
    private final Function<String, CharacterType> GET_CHARACTER_TYPE;
    private final Function<String, CharacterClassification> GET_CHARACTER_CLASSIFICATION;
    private final Function<String, SpriteSet> GET_SPRITE_SET;
    private final Function<String, CharacterAIType> GET_AI_TYPE;
    private final Function<String, GameCharacterEvent> GET_EVENT;
    private final Function<String, CharacterStaticStatisticType> GET_STATIC_STAT_TYPE;
    private final Function<String, CharacterDepletableStatisticType> GET_DEPLETABLE_STAT_TYPE;
    private final Function<String, StatusEffectType> GET_STATUS_TYPE;
    private final Function<String, ActiveAbilityType> GET_ACTIVE_ABILITY_TYPE;
    private final Function<String, ReactiveAbilityType> GET_REACTIVE_ABILITY_TYPE;
    private final PersistentValueTypeHandler<VariableCache> DATA_HANDLER;
    private final PersistentValueTypeHandler<Item> ITEM_HANDLER;

    @SuppressWarnings("ConstantConditions")
    public PersistentCharacterHandler(CharacterFactory characterFactory,
                                      PersistentValueTypeHandler<EntityUuid> idHandler,
                                      Function<String, CharacterType> getCharacterType,
                                      Function<String, CharacterClassification>
                                              getCharacterClassification,
                                      Function<String, SpriteSet> getSpriteSet,
                                      Function<String, CharacterAIType> getAIType,
                                      Function<String, GameCharacterEvent> getEvent,
                                      Function<String, CharacterStaticStatisticType> getStaticStatType,
                                      Function<String, CharacterDepletableStatisticType>
                                              getDepletableStatType,
                                      Function<String, StatusEffectType> getStatusType,
                                      Function<String, ActiveAbilityType> getActiveAbilityType,
                                      Function<String, ReactiveAbilityType> getReactiveAbilityType,
                                      PersistentValueTypeHandler<VariableCache> dataHandler,
                                      PersistentValueTypeHandler<Item> itemHandler) {
        if (characterFactory == null) {
            throw new IllegalArgumentException(
                    "PersistentCharacterHandler: characterFactory cannot be null");
        }
        CHARACTER_FACTORY = characterFactory;
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
        if (getEvent == null) {
            throw new IllegalArgumentException(
                    "PersistentCharacterHandler: getEvent cannot be null");
        }
        GET_EVENT = getEvent;
        if (getStaticStatType == null) {
            throw new IllegalArgumentException(
                    "PersistentCharacterHandler: getStaticStatType cannot be null");
        }
        GET_STATIC_STAT_TYPE = getStaticStatType;
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
    public Character read(String data) throws IllegalArgumentException {
        CharacterDTO dto = new Gson().fromJson(data, CharacterDTO.class);
        Character readCharacter =
                CHARACTER_FACTORY.make(GET_CHARACTER_TYPE.apply(dto.characterTypeId),
                        ID_HANDLER.read(dto.id), DATA_HANDLER.read(dto.data));

        for(String classificationId : dto.classifications) {
            readCharacter.classifications().add(
                    GET_CHARACTER_CLASSIFICATION.apply(classificationId));
        }

        for(CharacterPairedDataDTO pronounDTO : dto.pronouns) {
            readCharacter.pronouns().put(pronounDTO.key, pronounDTO.val);
        }
        readCharacter.setStance(dto.stance);
        readCharacter.setDirection(dto.direction);
        readCharacter.setSpriteSet(GET_SPRITE_SET.apply(dto.spriteSetId));
        readCharacter.setAIType(GET_AI_TYPE.apply(dto.aiTypeId));

        for (CharacterEventDTO eventDTO : dto.events) {
            for(String event : eventDTO.events) {
                readCharacter.events().addEvent(eventDTO.trigger, GET_EVENT.apply(event));
            }
        }

        for (CharacterPairedDataDTO equipmentSlot : dto.equipmentSlots) {
            readCharacter.equipmentSlots().addCharacterEquipmentSlot(equipmentSlot.key);
            if (equipmentSlot.val != null && !equipmentSlot.val.equals("")) {
                readCharacter.equipmentSlots().equipItemToSlot(equipmentSlot.key,
                        ITEM_HANDLER.read(equipmentSlot.val));
            }
        }

        for(String item : dto.inventoryItems) {
            readCharacter.inventory().add(ITEM_HANDLER.read(item));
        }

        for(CharacterEntityWithTypeAndValueDTO depletableStat : dto.depletableStats) {
            CharacterDepletableStatisticType type =
                    GET_DEPLETABLE_STAT_TYPE.apply(depletableStat.typeId);
            readCharacter.depletableStatistics().add(type);
            readCharacter.depletableStatistics().get(type).setCurrentValue(depletableStat.value);
        }

        for(String staticStat : dto.staticStats)
        {
            readCharacter.staticStatistics().add(GET_STATIC_STAT_TYPE.apply(staticStat));
        }

        for(CharacterEntityWithTypeAndValueDTO statusEffect : dto.statusEffects) {
            readCharacter.statusEffects().setStatusEffectLevel(
                    GET_STATUS_TYPE.apply(statusEffect.typeId), statusEffect.value);
        }

        for(CharacterAbilityDTO activeAbility : dto.activeAbilities) {
            ActiveAbilityType type = GET_ACTIVE_ABILITY_TYPE.apply(activeAbility.typeId);
            readCharacter.activeAbilities().add(type);
            if (activeAbility.isHidden) {
                readCharacter.activeAbilities().get(type).setIsHidden(true);
            }
        }

        for(CharacterAbilityDTO reactiveAbility : dto.reactiveAbilities) {
            ReactiveAbilityType type = GET_REACTIVE_ABILITY_TYPE.apply(reactiveAbility.typeId);
            readCharacter.reactiveAbilities().add(type);
            if (reactiveAbility.isHidden) {
                readCharacter.reactiveAbilities().get(type).setIsHidden(true);
            }
        }

        readCharacter.setPlayerControlled(dto.isPlayerControlled);

        readCharacter.setName(dto.name);

        return readCharacter;
    }

    @Override
    public String write(Character character) {
        if (character == null) {
            throw new IllegalArgumentException(
                    "PersistentCharacterHandler.write: character cannot be null");
        }
        CharacterDTO dto = new CharacterDTO();
        dto.id = ID_HANDLER.write(character.id());
        dto.characterTypeId = character.type().id();

        dto.classifications = new String[character.classifications().size()];

        int index = 0;
        for(CharacterClassification classification : character.classifications()) {
            dto.classifications[index++] = classification.id();
        }

        dto.pronouns = new CharacterPairedDataDTO[character.pronouns().size()];

        index = 0;
        for(Pair<String,String> pronoun : character.pronouns()) {
            CharacterPairedDataDTO pronounDTO = new CharacterPairedDataDTO();
            pronounDTO.key = pronoun.getItem1();
            pronounDTO.val = pronoun.getItem2();
            dto.pronouns[index++] = pronounDTO;
        }

        dto.stance = character.getStance();
        dto.direction = character.getDirection();
        dto.spriteSetId = character.getSpriteSet().id();
        dto.aiTypeId = character.getAIType().id();
        ReadableMap<String, ReadableCollection<GameCharacterEvent>> events =
                character.events().representation();

        index = 0;
        dto.events = new CharacterEventDTO[events.size()];
        for(Pair<String,ReadableCollection<GameCharacterEvent>> triggerEvents : events) {
            CharacterEventDTO eventDTO = new CharacterEventDTO();
            eventDTO.trigger = triggerEvents.getItem1();
            eventDTO.events = new String[triggerEvents.getItem2().size()];
            for (int i = 0; i < triggerEvents.getItem2().size(); i++) {
                eventDTO.events[i] = triggerEvents.getItem2().get(i).id();
            }
            dto.events[index++] = eventDTO;
        }

        index = 0;
        ReadableMap<String,Item> equipmentSlots = character.equipmentSlots().representation();
        dto.equipmentSlots = new CharacterPairedDataDTO[equipmentSlots.size()];
        for(Pair<String,Item> equipmentSlot : equipmentSlots)
        {
            CharacterPairedDataDTO equipmentSlotDTO = new CharacterPairedDataDTO();
            equipmentSlotDTO.key = equipmentSlot.getItem1();
            if (equipmentSlot.getItem2() != null)
            {
                equipmentSlotDTO.val = ITEM_HANDLER.write(equipmentSlot.getItem2());
            }
            dto.equipmentSlots[index++] = equipmentSlotDTO;
        }

        index = 0;
        ReadableCollection<Item> inventoryItems = character.inventory().representation();
        dto.inventoryItems = new String[inventoryItems.size()];
        for(Item inventoryItem : character.inventory().representation())
        {
            dto.inventoryItems[index++] = ITEM_HANDLER.write(inventoryItem);
        }

        index = 0;
        dto.depletableStats =
                new CharacterEntityWithTypeAndValueDTO[character.depletableStatistics().size()];
        for(CharacterDepletableStatistic depletableStat : character.depletableStatistics()) {
            CharacterEntityWithTypeAndValueDTO depletableStatDTO =
                    new CharacterEntityWithTypeAndValueDTO();
            depletableStatDTO.typeId = depletableStat.type().id();
            depletableStatDTO.value = depletableStat.getCurrentValue();
            dto.depletableStats[index++] = depletableStatDTO;
        }

        index = 0;
        dto.staticStats = new String[character.staticStatistics().size()];
        for(CharacterStaticStatistic stat : character.staticStatistics()) {
            dto.staticStats[index++] = stat.type().id();
        }

        index = 0;
        dto.statusEffects = new CharacterEntityWithTypeAndValueDTO[
                character.statusEffects().representation().size()];
        for(Pair<StatusEffectType, Integer> statEffect :
                character.statusEffects().representation()) {
            CharacterEntityWithTypeAndValueDTO statEffectDTO =
                    new CharacterEntityWithTypeAndValueDTO();
            statEffectDTO.typeId = statEffect.getItem1().id();
            statEffectDTO.value = statEffect.getItem2();
            dto.statusEffects[index++] = statEffectDTO;
        }

        index = 0;
        dto.activeAbilities = new CharacterAbilityDTO[character.activeAbilities().size()];
        for(CharacterAbility<ActiveAbilityType> charAbility : character.activeAbilities()) {
            CharacterAbilityDTO abilityDTO = new CharacterAbilityDTO();
            abilityDTO.typeId = charAbility.type().id();
            abilityDTO.isHidden = charAbility.getIsHidden();
            dto.activeAbilities[index++] = abilityDTO;
        }

        index = 0;
        dto.reactiveAbilities = new CharacterAbilityDTO[character.reactiveAbilities().size()];
        for(CharacterAbility<ReactiveAbilityType> charAbility : character.reactiveAbilities()) {
            CharacterAbilityDTO abilityDTO = new CharacterAbilityDTO();
            abilityDTO.typeId = charAbility.type().id();
            dto.reactiveAbilities[index++] = abilityDTO;
        }

        dto.isPlayerControlled = character.getPlayerControlled();
        dto.data = DATA_HANDLER.write(character.data());
        dto.name = character.getName();

        return new Gson().toJson(dto);
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
        String[] classifications;
        CharacterPairedDataDTO[] pronouns;
        String stance;
        String direction;
        String spriteSetId;
        String aiTypeId;
        CharacterEventDTO[] events;
        CharacterPairedDataDTO[] equipmentSlots;
        String[] inventoryItems;
        CharacterEntityWithTypeAndValueDTO[] depletableStats;
        String[] staticStats;
        CharacterEntityWithTypeAndValueDTO[] statusEffects;
        CharacterAbilityDTO[] activeAbilities;
        CharacterAbilityDTO[] reactiveAbilities;
        boolean isPlayerControlled;
        String data;
        String name;
    }

    private class CharacterPairedDataDTO {
        String key;
        String val;
    }
    private class CharacterEventDTO {
        String trigger;
        String[] events;
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

package inaugural.soliloquy.gamestate.persistence;

import inaugural.soliloquy.gamestate.archetypes.CharacterArchetype;
import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.gameevents.GameCharacterEvent;
import soliloquy.specs.gamestate.factories.CharacterFactory;
import soliloquy.specs.graphics.assets.ImageAssetSet;
import soliloquy.specs.ruleset.entities.*;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbility;
import soliloquy.specs.ruleset.entities.abilities.PassiveAbility;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbility;
import soliloquy.specs.ruleset.valueobjects.CharacterClassification;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class CharacterHandler extends AbstractTypeHandler<Character> {
    private final CharacterFactory CHARACTER_FACTORY;
    private final TypeHandler<UUID> UUID_HANDLER;
    private final Function<String, CharacterType> GET_CHARACTER_TYPE;
    private final Function<String, CharacterClassification> GET_CHARACTER_CLASSIFICATION;
    private final Function<String, ImageAssetSet> GET_IMAGE_ASSET_SET;
    private final Function<String, CharacterAIType> GET_AI_TYPE;
    private final Function<String, GameCharacterEvent> GET_EVENT;
    private final Function<String, CharacterStaticStatisticType> GET_STATIC_STAT_TYPE;
    private final Function<String, CharacterVariableStatisticType> GET_VARIABLE_STAT_TYPE;
    private final Function<String, StatusEffectType> GET_STATUS_TYPE;
    private final Function<String, PassiveAbility> GET_PASSIVE_ABILITY;
    private final Function<String, ActiveAbility> GET_ACTIVE_ABILITY;
    private final Function<String, ReactiveAbility> GET_REACTIVE_ABILITY;
    private final TypeHandler<VariableCache> DATA_HANDLER;
    private final TypeHandler<Item> ITEM_HANDLER;

    private static final Character ARCHETYPE = new CharacterArchetype();

    @SuppressWarnings("ConstantConditions")
    public CharacterHandler(CharacterFactory characterFactory,
                            TypeHandler<UUID> uuidHandler,
                            Function<String, CharacterType> getCharacterType,
                            Function<String, CharacterClassification>
                                    getCharacterClassification,
                            Function<String, ImageAssetSet> getImageAssetSet,
                            Function<String, CharacterAIType> getAIType,
                            Function<String, GameCharacterEvent> getEvent,
                            Function<String, CharacterStaticStatisticType> getStaticStatType,
                            Function<String, CharacterVariableStatisticType>
                                    getVariableStatType,
                            Function<String, StatusEffectType> getStatusType,
                            Function<String, PassiveAbility> getPassiveAbility,
                            Function<String, ActiveAbility> getActiveAbility,
                            Function<String, ReactiveAbility> getReactiveAbility,
                            TypeHandler<VariableCache> dataHandler,
                            TypeHandler<Item> itemHandler) {
        super(ARCHETYPE);
        CHARACTER_FACTORY = Check.ifNull(characterFactory, "characterFactory");
        UUID_HANDLER = Check.ifNull(uuidHandler, "uuidHandler");
        GET_CHARACTER_TYPE = Check.ifNull(getCharacterType, "getCharacterType");
        GET_CHARACTER_CLASSIFICATION = Check.ifNull(getCharacterClassification,
                "getCharacterClassification");
        GET_IMAGE_ASSET_SET = Check.ifNull(getImageAssetSet, "getImageAssetSet");
        GET_AI_TYPE = Check.ifNull(getAIType, "getAIType");
        GET_EVENT = Check.ifNull(getEvent, "getEvent");
        GET_STATIC_STAT_TYPE = Check.ifNull(getStaticStatType, "getStaticStatType");
        GET_VARIABLE_STAT_TYPE = Check.ifNull(getVariableStatType, "getVariableStatType");
        GET_STATUS_TYPE = Check.ifNull(getStatusType, "getStatusType");
        GET_PASSIVE_ABILITY = Check.ifNull(getPassiveAbility, "getPassiveAbility");
        GET_ACTIVE_ABILITY = Check.ifNull(getActiveAbility, "getActiveAbility");
        GET_REACTIVE_ABILITY = Check.ifNull(getReactiveAbility, "getReactiveAbility");
        DATA_HANDLER = Check.ifNull(dataHandler, "dataHandler");
        ITEM_HANDLER = Check.ifNull(itemHandler, "itemHandler");
    }

    @Override
    public Character read(String data) throws IllegalArgumentException {
        CharacterDTO dto = JSON.fromJson(data, CharacterDTO.class);
        Character readCharacter =
                CHARACTER_FACTORY.make(GET_CHARACTER_TYPE.apply(dto.characterTypeId),
                        UUID_HANDLER.read(dto.uuid), DATA_HANDLER.read(dto.data));

        for (String classificationId : dto.classifications) {
            readCharacter.classifications().add(
                    GET_CHARACTER_CLASSIFICATION.apply(classificationId));
        }

        for (CharacterPairedDataDTO pronounDTO : dto.pronouns) {
            readCharacter.pronouns().put(pronounDTO.key, pronounDTO.val);
        }
        readCharacter.setStance(dto.stance);
        readCharacter.setDirection(dto.direction);
        readCharacter.setImageAssetSet(GET_IMAGE_ASSET_SET.apply(dto.assetSetId));
        readCharacter.setAIType(GET_AI_TYPE.apply(dto.aiTypeId));

        for (CharacterEventDTO eventDTO : dto.events) {
            for (String event : eventDTO.events) {
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

        for (String item : dto.inventoryItems) {
            readCharacter.inventory().add(ITEM_HANDLER.read(item));
        }

        for (CharacterVariableStatisticDTO variableStat : dto.variableStats) {
            CharacterVariableStatisticType type =
                    GET_VARIABLE_STAT_TYPE.apply(variableStat.type);
            readCharacter.variableStatistics().add(type, DATA_HANDLER.read(variableStat.data));
            readCharacter.variableStatistics().get(type).setCurrentValue(variableStat.current);
        }

        for (CharacterEntityDTO staticStat : dto.staticStats) {
            readCharacter.staticStatistics().add(GET_STATIC_STAT_TYPE.apply(staticStat.type),
                    DATA_HANDLER.read(staticStat.data));
        }

        for (CharacterStatusEffectDTO statusEffect : dto.statusEffects) {
            readCharacter.statusEffects().setStatusEffectLevel(
                    GET_STATUS_TYPE.apply(statusEffect.type), statusEffect.value);
        }

        for (String passiveAbilityId : dto.passiveAbilityIds) {
            PassiveAbility passiveAbility = GET_PASSIVE_ABILITY.apply(passiveAbilityId);
            readCharacter.passiveAbilities().add(passiveAbility);
        }

        for (String activeAbilityId : dto.activeAbilityIds) {
            ActiveAbility activeAbility = GET_ACTIVE_ABILITY.apply(activeAbilityId);
            readCharacter.activeAbilities().add(activeAbility);
        }

        for (String reactiveAbilityId : dto.reactiveAbilityIds) {
            ReactiveAbility reactiveAbility = GET_REACTIVE_ABILITY.apply(reactiveAbilityId);
            readCharacter.reactiveAbilities().add(reactiveAbility);
        }

        readCharacter.setPlayerControlled(dto.isPlayerControlled);

        readCharacter.setName(dto.name);

        return readCharacter;
    }

    @Override
    public String write(Character character) {
        if (character == null) {
            throw new IllegalArgumentException(
                    "CharacterHandler.write: character cannot be null");
        }
        CharacterDTO dto = new CharacterDTO();
        dto.uuid = UUID_HANDLER.write(character.uuid());
        dto.characterTypeId = character.type().id();

        dto.classifications = new String[character.classifications().size()];

        AtomicInteger classificationIndex = new AtomicInteger(0);
        for (CharacterClassification classification : character.classifications()) {
            dto.classifications[classificationIndex.getAndIncrement()] = classification.id();
        }

        dto.pronouns = new CharacterPairedDataDTO[character.pronouns().size()];

        AtomicInteger pronounsIndex = new AtomicInteger(0);
        character.pronouns().forEach((pronounType, pronoun) -> {
            CharacterPairedDataDTO pronounDTO = new CharacterPairedDataDTO();
            pronounDTO.key = pronounType;
            pronounDTO.val = pronoun;
            dto.pronouns[pronounsIndex.getAndIncrement()] = pronounDTO;
        });

        dto.stance = character.getStance();
        dto.direction = character.getDirection();
        dto.assetSetId = character.getImageAssetSet().id();
        dto.aiTypeId = character.getAIType().id();
        Map<String, List<GameCharacterEvent>> eventsRepresentation =
                character.events().representation();

        AtomicInteger eventsIndex = new AtomicInteger(0);
        dto.events = new CharacterEventDTO[eventsRepresentation.size()];
        eventsRepresentation.forEach((trigger, events) -> {
            CharacterEventDTO eventDTO = new CharacterEventDTO();
            eventDTO.trigger = trigger;
            eventDTO.events = new String[events.size()];
            for (int i = 0; i < events.size(); i++) {
                eventDTO.events[i] = events.get(i).id();
            }
            dto.events[eventsIndex.getAndIncrement()] = eventDTO;
        });

        AtomicInteger equipmentSlotsIndex = new AtomicInteger(0);
        Map<String, Item> equipmentSlotsRepresentation =
                character.equipmentSlots().representation();
        dto.equipmentSlots = new CharacterPairedDataDTO[equipmentSlotsRepresentation.size()];
        equipmentSlotsRepresentation.forEach((slotType, item) -> {
            CharacterPairedDataDTO equipmentSlotDTO = new CharacterPairedDataDTO();
            equipmentSlotDTO.key = slotType;
            if (item != null) {
                equipmentSlotDTO.val = ITEM_HANDLER.write(item);
            }
            dto.equipmentSlots[equipmentSlotsIndex.getAndIncrement()] = equipmentSlotDTO;
        });

        AtomicInteger inventoryIndex = new AtomicInteger(0);
        List<Item> inventoryItems = character.inventory().representation();
        dto.inventoryItems = new String[inventoryItems.size()];
        inventoryItems.forEach(item ->
                dto.inventoryItems[inventoryIndex.getAndIncrement()] = ITEM_HANDLER.write(item));

        AtomicInteger variableStatsIndex = new AtomicInteger(0);
        dto.variableStats =
                new CharacterVariableStatisticDTO[character.variableStatistics().size()];
        character.variableStatistics().forEach(variableStat -> {
            CharacterVariableStatisticDTO variableStatDTO =
                    new CharacterVariableStatisticDTO();
            variableStatDTO.type = variableStat.type().id();
            variableStatDTO.data = DATA_HANDLER.write(variableStat.data());
            variableStatDTO.current = variableStat.getCurrentValue();
            dto.variableStats[variableStatsIndex.getAndIncrement()] = variableStatDTO;
        });

        AtomicInteger staticStatsIndex = new AtomicInteger(0);
        dto.staticStats = new CharacterEntityDTO[character.staticStatistics().size()];
        character.staticStatistics().forEach(staticStat -> {
            CharacterEntityDTO staticStatDTO = new CharacterEntityDTO();
            staticStatDTO.type = staticStat.type().id();
            staticStatDTO.data = DATA_HANDLER.write(staticStat.data());
            dto.staticStats[staticStatsIndex.getAndIncrement()] = staticStatDTO;
        });

        AtomicInteger statusEffectsIndex = new AtomicInteger(0);
        dto.statusEffects = new CharacterStatusEffectDTO[
                character.statusEffects().representation().size()];
        character.statusEffects().representation().forEach((type, value) -> {
            CharacterStatusEffectDTO statEffectDTO = new CharacterStatusEffectDTO();
            statEffectDTO.type = type.id();
            statEffectDTO.value = value;
            dto.statusEffects[statusEffectsIndex.getAndIncrement()] = statEffectDTO;
        });

        AtomicInteger passiveAbilitiesIndex = new AtomicInteger(0);
        dto.passiveAbilityIds = new String[character.passiveAbilities().size()];
        character.passiveAbilities().forEach(passiveAbility ->
                dto.passiveAbilityIds[passiveAbilitiesIndex.getAndIncrement()] =
                        passiveAbility.id());

        AtomicInteger activeAbilitiesIndex = new AtomicInteger(0);
        dto.activeAbilityIds = new String[character.activeAbilities().size()];
        character.activeAbilities().forEach(activeAbility ->
                dto.activeAbilityIds[activeAbilitiesIndex.getAndIncrement()] = activeAbility.id());

        AtomicInteger reactiveAbilitiesIndex = new AtomicInteger(0);
        dto.reactiveAbilityIds = new String[character.reactiveAbilities().size()];
        character.reactiveAbilities().forEach(reactiveAbility ->
                dto.reactiveAbilityIds[reactiveAbilitiesIndex.getAndIncrement()] =
                        reactiveAbility.id());

        dto.isPlayerControlled = character.getPlayerControlled();
        dto.data = DATA_HANDLER.write(character.data());
        dto.name = character.getName();

        return JSON.toJson(dto);
    }

    @Override
    public Character getArchetype() {
        return ARCHETYPE;
    }

    private class CharacterDTO {
        String uuid;
        String characterTypeId;
        String[] classifications;
        CharacterPairedDataDTO[] pronouns;
        String stance;
        String direction;
        String assetSetId;
        String aiTypeId;
        CharacterEventDTO[] events;
        CharacterPairedDataDTO[] equipmentSlots;
        String[] inventoryItems;
        CharacterVariableStatisticDTO[] variableStats;
        CharacterEntityDTO[] staticStats;
        CharacterStatusEffectDTO[] statusEffects;
        String[] passiveAbilityIds;
        String[] activeAbilityIds;
        String[] reactiveAbilityIds;
        boolean isPlayerControlled;
        String data;
        String name;
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    private class CharacterPairedDataDTO {
        String key;
        String val;
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    private class CharacterEventDTO {
        String trigger;
        String[] events;
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    private class CharacterStatusEffectDTO {
        String type;
        int value;
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    private class CharacterEntityDTO {
        String type;
        String data;
    }

    private class CharacterVariableStatisticDTO extends CharacterEntityDTO {
        int current;
    }
}

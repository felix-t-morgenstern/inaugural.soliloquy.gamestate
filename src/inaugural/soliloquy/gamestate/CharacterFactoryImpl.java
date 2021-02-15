package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.EntityUuidFactory;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.factories.*;
import soliloquy.specs.ruleset.entities.CharacterType;

public class CharacterFactoryImpl implements CharacterFactory {
    private final EntityUuidFactory ENTITY_UUID_FACTORY;
    private final ListFactory LIST_FACTORY;
    private final MapFactory MAP_FACTORY;
    private final CharacterEventsFactory CHARACTER_EVENTS_FACTORY;
    private final CharacterEquipmentSlotsFactory CHARACTER_EQUIPMENT_SLOTS_FACTORY;
    private final CharacterInventoryFactory CHARACTER_INVENTORY_FACTORY;
    private final CharacterVariableStatisticsFactory VARIABLE_STATS_FACTORY;
    private final CharacterEntitiesOfTypeFactory ENTITIES_FACTORY;
    private final CharacterStatusEffectsFactory CHARACTER_STATUS_EFFECTS_FACTORY;
    private final VariableCacheFactory DATA_FACTORY;

    @SuppressWarnings("ConstantConditions")
    public CharacterFactoryImpl(EntityUuidFactory entityUuidFactory,
                                ListFactory listFactory,
                                MapFactory mapFactory,
                                CharacterEventsFactory characterEventsFactory,
                                CharacterEquipmentSlotsFactory characterEquipmentSlotsFactory,
                                CharacterInventoryFactory characterInventoryFactory,
                                CharacterVariableStatisticsFactory variableStatsFactory,
                                CharacterEntitiesOfTypeFactory entitiesFactory,
                                CharacterStatusEffectsFactory characterStatusEffectsFactory,
                                VariableCacheFactory dataFactory) {
        if (entityUuidFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterFactory: entityUuidFactory must be non-null");
        }
        ENTITY_UUID_FACTORY = entityUuidFactory;
        if (listFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterFactory: listFactory must be non-null");
        }
        LIST_FACTORY = listFactory;
        if (mapFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterFactory: mapFactory must be non-null");
        }
        MAP_FACTORY = mapFactory;
        if (characterEventsFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterFactory: characterEventsFactory must be non-null");
        }
        CHARACTER_EVENTS_FACTORY = characterEventsFactory;
        if (characterEquipmentSlotsFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterFactory: characterEquipmentSlotsFactory must be non-null");
        }
        CHARACTER_EQUIPMENT_SLOTS_FACTORY = characterEquipmentSlotsFactory;
        if (characterInventoryFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterFactory: characterInventoryFactory must be non-null");
        }
        CHARACTER_INVENTORY_FACTORY = characterInventoryFactory;
        if (variableStatsFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterFactory: variableStatsFactory must be non-null");
        }
        VARIABLE_STATS_FACTORY = variableStatsFactory;
        if (entitiesFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterFactory: entitiesFactory must be non-null");
        }
        ENTITIES_FACTORY = entitiesFactory;
        if (characterStatusEffectsFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterFactory: characterStatusEffectsFactory must be non-null");
        }
        CHARACTER_STATUS_EFFECTS_FACTORY = characterStatusEffectsFactory;
        if (dataFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterFactory: dataFactory must be non-null");
        }
        DATA_FACTORY = dataFactory;
    }

    @Override
    public Character make(CharacterType characterType) throws IllegalArgumentException {
        return make(characterType, ENTITY_UUID_FACTORY.createRandomEntityUuid(),
                DATA_FACTORY.make());
    }

    @Override
    public Character make(CharacterType characterType, EntityUuid entityUuid, VariableCache data)
            throws IllegalArgumentException {
        if (characterType == null) {
            throw new IllegalArgumentException(
                    "CharacterFactory.make: characterType must be non-null");
        }
        if (entityUuid == null) {
            throw new IllegalArgumentException(
                    "CharacterFactory.make: entityUuid must be non-null");
        }
        return new CharacterImpl(entityUuid,
                characterType,
                LIST_FACTORY,
                MAP_FACTORY,
                CHARACTER_EVENTS_FACTORY,
                CHARACTER_EQUIPMENT_SLOTS_FACTORY,
                CHARACTER_INVENTORY_FACTORY,
                VARIABLE_STATS_FACTORY,
                ENTITIES_FACTORY,
                CHARACTER_STATUS_EFFECTS_FACTORY,
                data);
    }

    @Override
    public String getInterfaceName() {
        return CharacterFactory.class.getCanonicalName();
    }
}

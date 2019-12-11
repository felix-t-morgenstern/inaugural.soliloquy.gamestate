package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.EntityUuidFactory;
import soliloquy.specs.common.factories.GenericParamsSetFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.factories.*;
import soliloquy.specs.ruleset.entities.CharacterType;

public class CharacterFactoryImpl implements CharacterFactory {
    private final EntityUuidFactory ENTITY_UUID_FACTORY;
    private final CollectionFactory COLLECTION_FACTORY;
    private final MapFactory MAP_FACTORY;
    private final CharacterEventsFactory CHARACTER_EVENTS_FACTORY;
    private final CharacterEquipmentSlotsFactory CHARACTER_EQUIPMENT_SLOTS_FACTORY;
    private final CharacterInventoryFactory CHARACTER_INVENTORY_FACTORY;
    private final CharacterDepletableStatisticsFactory DEPLETABLE_STATS_FACTORY;
    private final CharacterEntitiesOfTypeFactory ENTITIES_FACTORY;
    private final CharacterStatusEffectsFactory CHARACTER_STATUS_EFFECTS_FACTORY;
    private final GenericParamsSetFactory GENERIC_PARAMS_SET_FACTORY;

    @SuppressWarnings("ConstantConditions")
    public CharacterFactoryImpl(EntityUuidFactory entityUuidFactory,
                                CollectionFactory collectionFactory,
                                MapFactory mapFactory,
                                CharacterEventsFactory characterEventsFactory,
                                CharacterEquipmentSlotsFactory characterEquipmentSlotsFactory,
                                CharacterInventoryFactory characterInventoryFactory,
                                CharacterDepletableStatisticsFactory depletableStatsFactory,
                                CharacterEntitiesOfTypeFactory entitiesFactory,
                                CharacterStatusEffectsFactory characterStatusEffectsFactory,
                                GenericParamsSetFactory genericParamsSetFactory) {
        if (entityUuidFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterFactory: entityUuidFactory must be non-null");
        }
        ENTITY_UUID_FACTORY = entityUuidFactory;
        if (collectionFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterFactory: collectionFactory must be non-null");
        }
        COLLECTION_FACTORY = collectionFactory;
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
        if (depletableStatsFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterFactory: depletableStatsFactory must be non-null");
        }
        DEPLETABLE_STATS_FACTORY = depletableStatsFactory;
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
        if (genericParamsSetFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterFactory: genericParamsSetFactory must be non-null");
        }
        GENERIC_PARAMS_SET_FACTORY = genericParamsSetFactory;
    }

    @Override
    public Character make(CharacterType characterType) throws IllegalArgumentException {
        return make(characterType, ENTITY_UUID_FACTORY.createRandomEntityUuid(),
                GENERIC_PARAMS_SET_FACTORY.make());
    }

    @Override
    public Character make(CharacterType characterType, EntityUuid entityUuid,
                          GenericParamsSet data)
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
                COLLECTION_FACTORY,
                MAP_FACTORY,
                CHARACTER_EVENTS_FACTORY,
                CHARACTER_EQUIPMENT_SLOTS_FACTORY,
                CHARACTER_INVENTORY_FACTORY,
                DEPLETABLE_STATS_FACTORY,
                ENTITIES_FACTORY,
                CHARACTER_STATUS_EFFECTS_FACTORY,
                data);
    }

    @Override
    public String getInterfaceName() {
        return CharacterFactory.class.getCanonicalName();
    }
}

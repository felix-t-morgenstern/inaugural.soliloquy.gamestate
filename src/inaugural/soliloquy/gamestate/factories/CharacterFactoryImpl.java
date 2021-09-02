package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.CharacterImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.EntityUuidFactory;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.factories.*;
import soliloquy.specs.ruleset.entities.CharacterType;

public class CharacterFactoryImpl implements CharacterFactory {
    private final EntityUuidFactory ENTITY_UUID_FACTORY;
    private final CharacterEventsFactory CHARACTER_EVENTS_FACTORY;
    private final CharacterEquipmentSlotsFactory CHARACTER_EQUIPMENT_SLOTS_FACTORY;
    private final CharacterInventoryFactory CHARACTER_INVENTORY_FACTORY;
    private final CharacterVariableStatisticsFactory VARIABLE_STATS_FACTORY;
    private final EntityMembersOfTypeFactory ENTITY_MEMBERS_OF_TYPE_FACTORY;
    private final CharacterStatusEffectsFactory CHARACTER_STATUS_EFFECTS_FACTORY;
    private final VariableCacheFactory DATA_FACTORY;

    public CharacterFactoryImpl(EntityUuidFactory entityUuidFactory,
                                CharacterEventsFactory characterEventsFactory,
                                CharacterEquipmentSlotsFactory characterEquipmentSlotsFactory,
                                CharacterInventoryFactory characterInventoryFactory,
                                CharacterVariableStatisticsFactory variableStatsFactory,
                                EntityMembersOfTypeFactory entityMembersOfTypeFactory,
                                CharacterStatusEffectsFactory characterStatusEffectsFactory,
                                VariableCacheFactory dataFactory) {
        ENTITY_UUID_FACTORY = Check.ifNull(entityUuidFactory, "entityUuidFactory");
        CHARACTER_EVENTS_FACTORY = Check.ifNull(characterEventsFactory, "characterEventsFactory");
        CHARACTER_EQUIPMENT_SLOTS_FACTORY = Check.ifNull(characterEquipmentSlotsFactory,
                "characterEquipmentSlotsFactory");
        CHARACTER_INVENTORY_FACTORY = Check.ifNull(characterInventoryFactory,
                "characterInventoryFactory");
        VARIABLE_STATS_FACTORY = Check.ifNull(variableStatsFactory, "variableStatsFactory");
        ENTITY_MEMBERS_OF_TYPE_FACTORY = Check.ifNull(entityMembersOfTypeFactory,
                "entityMembersOfTypeFactory");
        CHARACTER_STATUS_EFFECTS_FACTORY = Check.ifNull(characterStatusEffectsFactory,
                "characterStatusEffectsFactory");
        DATA_FACTORY = Check.ifNull(dataFactory, "dataFactory");
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
                CHARACTER_EVENTS_FACTORY,
                CHARACTER_EQUIPMENT_SLOTS_FACTORY,
                CHARACTER_INVENTORY_FACTORY,
                VARIABLE_STATS_FACTORY,
                ENTITY_MEMBERS_OF_TYPE_FACTORY,
                CHARACTER_STATUS_EFFECTS_FACTORY,
                data);
    }

    @Override
    public String getInterfaceName() {
        return CharacterFactory.class.getCanonicalName();
    }
}

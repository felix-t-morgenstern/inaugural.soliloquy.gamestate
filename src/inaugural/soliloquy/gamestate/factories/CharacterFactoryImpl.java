package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.CharacterImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.factories.*;
import soliloquy.specs.ruleset.entities.CharacterType;

import java.util.UUID;
import java.util.function.Supplier;

public class CharacterFactoryImpl implements CharacterFactory {
    private final Supplier<UUID> UUID_FACTORY;
    private final CharacterEventsFactory CHARACTER_EVENTS_FACTORY;
    private final CharacterEquipmentSlotsFactory CHARACTER_EQUIPMENT_SLOTS_FACTORY;
    private final CharacterInventoryFactory CHARACTER_INVENTORY_FACTORY;
    private final CharacterVariableStatisticsFactory VARIABLE_STATS_FACTORY;
    private final EntityMembersOfTypeFactory ENTITY_MEMBERS_OF_TYPE_FACTORY;
    private final CharacterStatusEffectsFactory CHARACTER_STATUS_EFFECTS_FACTORY;
    private final VariableCacheFactory DATA_FACTORY;

    public CharacterFactoryImpl(Supplier<UUID> uuidFactory,
                                CharacterEventsFactory characterEventsFactory,
                                CharacterEquipmentSlotsFactory characterEquipmentSlotsFactory,
                                CharacterInventoryFactory characterInventoryFactory,
                                CharacterVariableStatisticsFactory variableStatsFactory,
                                EntityMembersOfTypeFactory entityMembersOfTypeFactory,
                                CharacterStatusEffectsFactory characterStatusEffectsFactory,
                                VariableCacheFactory dataFactory) {
        UUID_FACTORY = Check.ifNull(uuidFactory, "uuidFactory");
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
        return make(characterType, UUID_FACTORY.get(), DATA_FACTORY.make());
    }

    @Override
    public Character make(CharacterType characterType, UUID uuid, VariableCache data)
            throws IllegalArgumentException {
        if (characterType == null) {
            throw new IllegalArgumentException(
                    "CharacterFactory.make: characterType must be non-null");
        }
        if (uuid == null) {
            throw new IllegalArgumentException("CharacterFactory.make: uuid must be non-null");
        }
        return new CharacterImpl(
                uuid,
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

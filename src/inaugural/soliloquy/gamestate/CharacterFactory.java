package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.ICollectionFactory;
import soliloquy.specs.common.factories.IEntityUuidFactory;
import soliloquy.specs.common.factories.IGenericParamsSetFactory;
import soliloquy.specs.common.factories.IMapFactory;
import soliloquy.specs.common.valueobjects.IEntityUuid;
import soliloquy.specs.gamestate.entities.ICharacter;
import soliloquy.specs.gamestate.factories.ICharacterEquipmentSlotsFactory;
import soliloquy.specs.gamestate.factories.ICharacterFactory;
import soliloquy.specs.gamestate.factories.ICharacterInventoryFactory;
import soliloquy.specs.gamestate.factories.ICharacterStatusEffectsFactory;
import soliloquy.specs.ruleset.entities.ICharacterType;

public class CharacterFactory implements ICharacterFactory {
    private final IEntityUuidFactory ENTITY_UUID_FACTORY;
    private final ICollectionFactory COLLECTION_FACTORY;
    private final IMapFactory MAP_FACTORY;
    private final ICharacterEquipmentSlotsFactory CHARACTER_EQUIPMENT_SLOTS_FACTORY;
    private final ICharacterInventoryFactory CHARACTER_INVENTORY_FACTORY;
    private final ICharacterStatusEffectsFactory CHARACTER_STATUS_EFFECTS_FACTORY;
    private final IGenericParamsSetFactory GENERIC_PARAMS_SET_FACTORY;

    @SuppressWarnings("ConstantConditions")
    public CharacterFactory(IEntityUuidFactory entityUuidFactory,
                            ICollectionFactory collectionFactory,
                            IMapFactory mapFactory,
                            ICharacterEquipmentSlotsFactory characterEquipmentSlotsFactory,
                            ICharacterInventoryFactory characterInventoryFactory,
                            ICharacterStatusEffectsFactory characterStatusEffectsFactory,
                            IGenericParamsSetFactory genericParamsSetFactory) {
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
    public ICharacter make(ICharacterType characterType) throws IllegalArgumentException {
        return make(characterType, ENTITY_UUID_FACTORY.createRandomEntityUuid());
    }

    @Override
    public ICharacter make(ICharacterType characterType, IEntityUuid entityUuid)
            throws IllegalArgumentException {
        if (characterType == null) {
            throw new IllegalArgumentException(
                    "CharacterFactory.make: characterType must be non-null");
        }
        if (entityUuid == null) {
            throw new IllegalArgumentException(
                    "CharacterFactory.make: entityUuid must be non-null");
        }
        return new Character(entityUuid,
                characterType,
                COLLECTION_FACTORY,
                MAP_FACTORY,
                CHARACTER_EQUIPMENT_SLOTS_FACTORY,
                CHARACTER_INVENTORY_FACTORY,
                CHARACTER_STATUS_EFFECTS_FACTORY,
                GENERIC_PARAMS_SET_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return ICharacterFactory.class.getCanonicalName();
    }
}

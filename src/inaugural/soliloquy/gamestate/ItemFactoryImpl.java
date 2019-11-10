package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.EntityUuidFactory;
import soliloquy.specs.common.factories.GenericParamsSetFactory;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.factories.ItemFactory;
import soliloquy.specs.ruleset.entities.ItemType;

public class ItemFactoryImpl implements ItemFactory {
    private final EntityUuidFactory ENTITY_UUID_FACTORY;
    private final GenericParamsSetFactory GENERIC_PARAMS_SET_FACTORY;
    private final PairFactory PAIR_FACTORY;

    @SuppressWarnings("ConstantConditions")
    public ItemFactoryImpl(EntityUuidFactory entityUuidFactory,
                           GenericParamsSetFactory genericParamsSetFactory,
                           PairFactory pairFactory) {
        if (entityUuidFactory == null) {
            throw new IllegalArgumentException(
                    "ItemFactoryImpl: entityUuidFactory cannot be null");
        }
        ENTITY_UUID_FACTORY = entityUuidFactory;
        if (genericParamsSetFactory == null) {
            throw new IllegalArgumentException(
                    "ItemFactoryImpl: genericParamsSetFactory cannot be null");
        }
        GENERIC_PARAMS_SET_FACTORY = genericParamsSetFactory;
        if (pairFactory == null) {
            throw new IllegalArgumentException(
                    "ItemFactoryImpl: pairFactory cannot be null");
        }
        PAIR_FACTORY = pairFactory;
    }

    @Override
    public Item make(ItemType itemType, GenericParamsSet data)
            throws IllegalArgumentException {
        return make(itemType, data, ENTITY_UUID_FACTORY.createRandomEntityUuid());
    }

    @Override
    public Item make(ItemType itemType, GenericParamsSet data, EntityUuid id)
            throws IllegalArgumentException {
        return new ItemImpl(id, itemType, data == null ? GENERIC_PARAMS_SET_FACTORY.make() : data,
                PAIR_FACTORY, ENTITY_UUID_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return ItemFactory.class.getCanonicalName();
    }
}

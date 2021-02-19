package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.ItemImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.EntityUuidFactory;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.factories.ItemFactory;
import soliloquy.specs.ruleset.entities.ItemType;

public class ItemFactoryImpl implements ItemFactory {
    private final EntityUuidFactory ENTITY_UUID_FACTORY;
    private final VariableCacheFactory DATA_FACTORY;
    private final PairFactory PAIR_FACTORY;

    public ItemFactoryImpl(EntityUuidFactory entityUuidFactory,
                           VariableCacheFactory dataFactory,
                           PairFactory pairFactory) {
        ENTITY_UUID_FACTORY = Check.ifNull(entityUuidFactory, "entityUuidFactory");
        DATA_FACTORY = Check.ifNull(dataFactory, "dataFactory");
        PAIR_FACTORY = Check.ifNull(pairFactory, "pairFactory");
    }

    @Override
    public Item make(ItemType itemType, VariableCache data)
            throws IllegalArgumentException {
        return make(itemType, data, ENTITY_UUID_FACTORY.createRandomEntityUuid());
    }

    @Override
    public Item make(ItemType itemType, VariableCache data, EntityUuid id)
            throws IllegalArgumentException {
        return new ItemImpl(id, itemType, data == null ? DATA_FACTORY.make() : data,
                PAIR_FACTORY, ENTITY_UUID_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return ItemFactory.class.getCanonicalName();
    }
}

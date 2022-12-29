package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.ItemImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.factories.ItemFactory;
import soliloquy.specs.ruleset.entities.ItemType;

import java.util.UUID;

public class ItemFactoryImpl implements ItemFactory {
    private final VariableCacheFactory DATA_FACTORY;

    public ItemFactoryImpl(VariableCacheFactory dataFactory) {
        DATA_FACTORY = Check.ifNull(dataFactory, "dataFactory");
    }

    @Override
    public Item make(ItemType itemType, VariableCache data)
            throws IllegalArgumentException {
        return make(itemType, data, UUID.randomUUID());
    }

    @Override
    public Item make(ItemType itemType, VariableCache data, UUID uuid)
            throws IllegalArgumentException {
        return new ItemImpl(uuid, itemType, data == null ? DATA_FACTORY.make() : data);
    }

    @Override
    public String getInterfaceName() {
        return ItemFactory.class.getCanonicalName();
    }
}

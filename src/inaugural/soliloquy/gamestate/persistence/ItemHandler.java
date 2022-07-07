package inaugural.soliloquy.gamestate.persistence;

import inaugural.soliloquy.gamestate.archetypes.ItemArchetype;
import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.factories.ItemFactory;
import soliloquy.specs.ruleset.entities.ItemType;

import java.util.UUID;
import java.util.function.Function;

public class ItemHandler extends AbstractTypeHandler<Item> {
    // TODO: Shift from Registry to ReadableRegistry; generate "Registry.readOnlyAccess", also refactor into other infrastructure classes
    private final Function<String, ItemType> GET_ITEM_TYPE;
    private final TypeHandler<UUID> UUID_HANDLER;
    private final TypeHandler<VariableCache> DATA_HANDLER;
    private final ItemFactory ITEM_FACTORY;

    private static final Item ARCHETYPE = new ItemArchetype();

    public ItemHandler(Function<String, ItemType> getItemType,
                       TypeHandler<UUID> uuidHandler,
                       TypeHandler<VariableCache> dataHandler,
                       ItemFactory itemFactory) {
        super(ARCHETYPE);
        GET_ITEM_TYPE = Check.ifNull(getItemType, "getItemType");
        UUID_HANDLER = Check.ifNull(uuidHandler, "uuidHandler");
        DATA_HANDLER = Check.ifNull(dataHandler, "dataHandler");
        ITEM_FACTORY = Check.ifNull(itemFactory, "itemFactory");
    }

    @Override
    public Item read(String input) throws IllegalArgumentException {
        Check.ifNullOrEmpty(input, "input");
        ItemDTO itemDTO = JSON.fromJson(input, ItemDTO.class);
        UUID uuid = UUID_HANDLER.read(itemDTO.uuid);
        ItemType itemType = GET_ITEM_TYPE.apply(itemDTO.typeId);
        VariableCache data = DATA_HANDLER.read(itemDTO.data);
        Item readItem = ITEM_FACTORY.make(itemType, data, uuid);
        readItem.setXTileWidthOffset(itemDTO.xOffset);
        readItem.setYTileHeightOffset(itemDTO.yOffset);
        if (itemType.hasCharges()) {
            readItem.setCharges(itemDTO.charges);
        } else if (itemType.isStackable()) {
            readItem.setNumberInStack(itemDTO.numberInStack);
        }
        return readItem;
    }

    @Override
    public String write(Item item) {
        Check.ifNull(item, "item");
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.uuid = UUID_HANDLER.write(item.uuid());
        itemDTO.typeId = item.type().id();
        itemDTO.xOffset = item.getXTileWidthOffset();
        itemDTO.yOffset = item.getYTileHeightOffset();
        if (item.type().hasCharges()) {
            itemDTO.charges = item.getCharges();
        } else if (item.type().isStackable()) {
            itemDTO.numberInStack = item.getNumberInStack();
        }
        itemDTO.data = DATA_HANDLER.write(item.data());
        return JSON.toJson(itemDTO);
    }

    private static class ItemDTO {
        String uuid;
        String typeId;
        Float xOffset;
        Float yOffset;
        Integer charges;
        Integer numberInStack;
        String data;
    }
}

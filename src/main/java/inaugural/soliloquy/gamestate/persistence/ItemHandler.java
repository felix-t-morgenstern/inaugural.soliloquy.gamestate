package inaugural.soliloquy.gamestate.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Vertex;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.factories.ItemFactory;
import soliloquy.specs.ruleset.entities.ItemType;

import java.util.UUID;
import java.util.function.Function;

import static inaugural.soliloquy.tools.generic.Archetypes.generateSimpleArchetype;

public class ItemHandler extends AbstractTypeHandler<Item> {
    private final Function<String, ItemType> GET_ITEM_TYPE;
    private final TypeHandler<VariableCache> DATA_HANDLER;
    private final ItemFactory ITEM_FACTORY;

    public ItemHandler(Function<String, ItemType> getItemType,
                       TypeHandler<VariableCache> dataHandler,
                       ItemFactory itemFactory) {
        super(generateSimpleArchetype(Item.class));
        GET_ITEM_TYPE = Check.ifNull(getItemType, "getItemType");
        DATA_HANDLER = Check.ifNull(dataHandler, "dataHandler");
        ITEM_FACTORY = Check.ifNull(itemFactory, "itemFactory");
    }

    @Override
    public Item read(String input) throws IllegalArgumentException {
        Check.ifNullOrEmpty(input, "input");
        ItemDTO itemDTO = JSON.fromJson(input, ItemDTO.class);
        UUID uuid = UUID.fromString(itemDTO.uuid);
        ItemType itemType = GET_ITEM_TYPE.apply(itemDTO.typeId);
        VariableCache data = DATA_HANDLER.read(itemDTO.data);
        Item readItem = ITEM_FACTORY.make(itemType, data, uuid);
        readItem.setTileOffset(Vertex.of(itemDTO.xOffset, itemDTO.yOffset));
        if (itemType.hasCharges()) {
            readItem.setCharges(itemDTO.charges);
        }
        else if (itemType.isStackable()) {
            readItem.setNumberInStack(itemDTO.numberInStack);
        }
        return readItem;
    }

    @Override
    public String write(Item item) {
        Check.ifNull(item, "item");
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.uuid = item.uuid().toString();
        itemDTO.typeId = item.type().id();
        itemDTO.xOffset = item.getTileOffset().X;
        itemDTO.yOffset = item.getTileOffset().Y;
        if (item.type().hasCharges()) {
            itemDTO.charges = item.getCharges();
        }
        else if (item.type().isStackable()) {
            itemDTO.numberInStack = item.getNumberInStack();
        }
        itemDTO.data = DATA_HANDLER.write(item.data());
        return JSON.toJson(itemDTO);
    }

    private static class ItemDTO {
        String uuid;
        String typeId;
        float xOffset;
        float yOffset;
        Integer charges;
        Integer numberInStack;
        String data;
    }
}

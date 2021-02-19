package inaugural.soliloquy.gamestate.persistentvaluetypehandlers;

import com.google.gson.Gson;
import inaugural.soliloquy.gamestate.archetypes.ItemArchetype;
import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.PersistentTypeHandler;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.PersistentValueTypeHandler;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.factories.ItemFactory;
import soliloquy.specs.ruleset.entities.ItemType;

import java.util.function.Function;

public class PersistentItemHandler extends PersistentTypeHandler<Item> {
    // TODO: Shift from Registry to ReadableRegistry; generate "Registry.readOnlyAccess", also refactor into other infrastructure classes
    private final Function<String, ItemType> GET_ITEM_TYPE;
    private final PersistentValueTypeHandler<EntityUuid> ENTITY_UUID_HANDLER;
    private final PersistentValueTypeHandler<VariableCache> DATA_HANDLER;
    private final ItemFactory ITEM_FACTORY;

    private static final Item ARCHETYPE = new ItemArchetype();

    public PersistentItemHandler(Function<String, ItemType> getItemType,
                                 PersistentValueTypeHandler<EntityUuid> entityUuidHandler,
                                 PersistentValueTypeHandler<VariableCache> dataHandler,
                                 ItemFactory itemFactory) {
        GET_ITEM_TYPE = Check.ifNull(getItemType, "getItemType");
        ENTITY_UUID_HANDLER = Check.ifNull(entityUuidHandler, "entityUuidHandler");
        DATA_HANDLER = Check.ifNull(dataHandler, "dataHandler");
        ITEM_FACTORY = Check.ifNull(itemFactory, "itemFactory");
    }

    @Override
    public Item getArchetype() {
        return ARCHETYPE;
    }

    @Override
    public Item read(String input) throws IllegalArgumentException {
        Check.ifNullOrEmpty(input, "input");
        ItemDTO itemDTO = new Gson().fromJson(input, ItemDTO.class);
        EntityUuid id = ENTITY_UUID_HANDLER.read(itemDTO.id);
        ItemType itemType = GET_ITEM_TYPE.apply(itemDTO.typeId);
        VariableCache data = DATA_HANDLER.read(itemDTO.data);
        Item readItem = ITEM_FACTORY.make(itemType, data, id);
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
        itemDTO.id = ENTITY_UUID_HANDLER.write(item.id());
        itemDTO.typeId = item.type().id();
        itemDTO.xOffset = item.getXTileWidthOffset();
        itemDTO.yOffset = item.getYTileHeightOffset();
        if (item.type().hasCharges()) {
            itemDTO.charges = item.getCharges();
        } else if (item.type().isStackable()) {
            itemDTO.numberInStack = item.getNumberInStack();
        }
        itemDTO.data = DATA_HANDLER.write(item.data());
        return new Gson().toJson(itemDTO);
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    private class ItemDTO {
        String id;
        String typeId;
        Float xOffset;
        Float yOffset;
        Integer charges;
        Integer numberInStack;
        String data;
    }
}

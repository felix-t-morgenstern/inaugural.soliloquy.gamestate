package inaugural.soliloquy.gamestate.persistentvaluetypehandlers;

import com.google.gson.Gson;
import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentTypeHandler;
import inaugural.soliloquy.gamestate.archetypes.ItemArchetype;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.factories.ItemFactory;
import soliloquy.specs.ruleset.entities.ItemType;

public class PersistentItemHandler extends PersistentTypeHandler<Item> implements PersistentValueTypeHandler<Item> {
    // TODO: Shift from Registry to ReadableRegistry; generate "Registry.readOnlyAccess", also refactor into other infrastructure classes
    private final Registry<ItemType> ITEM_TYPES_REGISTRY;
    private final PersistentValueTypeHandler<EntityUuid> ENTITY_UUID_HANDLER;
    private final PersistentValueTypeHandler<GenericParamsSet> GENERIC_PARAMS_SET_HANDLER;
    private final ItemFactory ITEM_FACTORY;

    private static final Item ARCHETYPE = new ItemArchetype();

    @SuppressWarnings("ConstantConditions")
    public PersistentItemHandler(Registry<ItemType> itemTypesRegistry,
                                 PersistentValueTypeHandler<EntityUuid> entityUuidHandler,
                                 PersistentValueTypeHandler<GenericParamsSet>
                                         genericParamsSetHandler,
                                 ItemFactory itemFactory) {
        if (itemTypesRegistry == null) {
            throw new IllegalArgumentException(
                    "PersistentItemHandler: itemTypesRegistry cannot be null");
        }
        ITEM_TYPES_REGISTRY = itemTypesRegistry;
        if (entityUuidHandler == null) {
            throw new IllegalArgumentException(
                    "PersistentItemHandler: entityUuidHandler cannot be null");
        }
        ENTITY_UUID_HANDLER = entityUuidHandler;
        if (genericParamsSetHandler == null) {
            throw new IllegalArgumentException(
                    "PersistentItemHandler: genericParamsSetHandler cannot be null");
        }
        GENERIC_PARAMS_SET_HANDLER = genericParamsSetHandler;
        if (itemFactory == null) {
            throw new IllegalArgumentException(
                    "PersistentItemHandler: itemFactory cannot be null");
        }
        ITEM_FACTORY = itemFactory;
    }

    @Override
    public Item getArchetype() {
        return ARCHETYPE;
    }

    @Override
    public Item read(String input) throws IllegalArgumentException {
        if (input == null) {
            throw new IllegalArgumentException("PersistentItemHandler.read: input cannot be null");
        }
        if (input.equals("")) {
            throw new IllegalArgumentException(
                    "PersistentItemHandler.read: input cannot be empty");
        }
        ItemDTO itemDTO = new Gson().fromJson(input, ItemDTO.class);
        EntityUuid id = ENTITY_UUID_HANDLER.read(itemDTO.id);
        ItemType itemType = ITEM_TYPES_REGISTRY.get(itemDTO.typeId);
        GenericParamsSet data = GENERIC_PARAMS_SET_HANDLER.read(itemDTO.data);
        Item readItem = ITEM_FACTORY.make(itemType, data, id);
        if (itemType.hasCharges()) {
            readItem.setCharges(itemDTO.charges);
        } else if (itemType.isStackable()) {
            readItem.setNumberInStack(itemDTO.numberInStack);
        }
        return readItem;
    }

    @Override
    public String write(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("PersistentItemHandler.write: item cannot be null");
        }
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.id = ENTITY_UUID_HANDLER.write(item.id());
        itemDTO.typeId = item.type().id();
        if (item.type().hasCharges()) {
            itemDTO.charges = item.getCharges();
        } else if (item.type().isStackable()) {
            itemDTO.numberInStack = item.getNumberInStack();
        }
        itemDTO.data = GENERIC_PARAMS_SET_HANDLER.write(item.data());
        return new Gson().toJson(itemDTO);
    }

    private class ItemDTO {
        String id;
        String typeId;
        Integer charges;
        Integer numberInStack;
        String data;
    }
}

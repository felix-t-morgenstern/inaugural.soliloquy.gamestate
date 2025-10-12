package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.ItemImpl;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.factories.ItemFactory;
import soliloquy.specs.ruleset.entities.ItemType;

import java.util.Map;
import java.util.UUID;

import static inaugural.soliloquy.tools.Tools.defaultIfNull;
import static inaugural.soliloquy.tools.collections.Collections.mapOf;

public class ItemFactoryImpl implements ItemFactory {
    @Override
    public Item make(ItemType itemType, Map<String, Object> data)
            throws IllegalArgumentException {
        return make(itemType, data, UUID.randomUUID());
    }

    @Override
    public Item make(ItemType itemType, Map<String, Object> data, UUID uuid)
            throws IllegalArgumentException {
        return new ItemImpl(uuid, itemType, defaultIfNull(data, mapOf()));
    }
}

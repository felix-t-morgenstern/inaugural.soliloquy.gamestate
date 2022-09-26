package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.factories.ItemFactory;
import soliloquy.specs.ruleset.entities.ItemType;

import java.util.UUID;

public class FakeItemFactory implements ItemFactory {
    @Override
    public Item make(ItemType type, VariableCache data) throws IllegalArgumentException {
        return new FakeItem();
    }

    @Override
    public Item make(ItemType type, VariableCache data, UUID uuid)
            throws IllegalArgumentException {
        return new FakeItem(type, data, uuid);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

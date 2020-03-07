package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.factories.ItemFactory;
import soliloquy.specs.ruleset.entities.ItemType;

public class FakeItemFactory implements ItemFactory {
    @Override
    public Item make(ItemType type, VariableCache data) throws IllegalArgumentException {
        return new FakeItem();
    }

    @Override
    public Item make(ItemType type, VariableCache data, EntityUuid id) throws IllegalArgumentException {
        return new FakeItem(type, data, id);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

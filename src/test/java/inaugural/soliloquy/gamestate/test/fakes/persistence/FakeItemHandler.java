package inaugural.soliloquy.gamestate.test.fakes.persistence;

import inaugural.soliloquy.gamestate.test.fakes.FakeItem;
import soliloquy.specs.gamestate.entities.Item;

public class FakeItemHandler extends FakeTypeHandler<Item> {
    @Override
    public String typeName() {
        return "Item";
    }

    @Override
    protected Item generateInstance() {
        return new FakeItem();
    }
}

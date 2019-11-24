package inaugural.soliloquy.gamestate.test.stubs.persistenttypehandlers;

import inaugural.soliloquy.gamestate.test.stubs.ItemStub;
import soliloquy.specs.gamestate.entities.Item;

public class PersistentItemHandlerStub extends PersistentValueTypeHandlerStub<Item> {
    @Override
    public String typeName() {
        return "Item";
    }

    @Override
    protected Item generateInstance() {
        return new ItemStub();
    }
}

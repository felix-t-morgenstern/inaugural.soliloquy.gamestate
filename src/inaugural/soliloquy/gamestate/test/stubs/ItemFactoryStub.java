package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.factories.ItemFactory;
import soliloquy.specs.ruleset.entities.ItemType;

public class ItemFactoryStub implements ItemFactory {
    @Override
    public Item make(ItemType type, GenericParamsSet data) throws IllegalArgumentException {
        return new ItemStub();
    }

    @Override
    public Item make(ItemType type, GenericParamsSet data, EntityUuid id) throws IllegalArgumentException {
        return new ItemStub(type, data, id);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
package inaugural.soliloquy.gamestate;

import soliloquy.specs.gamestate.entities.Item;

class ItemPresence {
    static boolean itemIsPresentElsewhere(Item item) {
        // NB: I am choosing to permit a Law of Demeter violation here, because it seems preferable
        //     to exposing an otherwise-unnecessary method on Item, or creating a private class
        //     here, just to treat item as a class member.
        return item.getCharacterEquipmentSlot() != null ||
                item.getInventoryCharacter() != null ||
                item.getContainingTile() != null ||
                item.getContainingTileFixture() != null;
    }
}

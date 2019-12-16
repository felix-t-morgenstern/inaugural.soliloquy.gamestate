package inaugural.soliloquy.gamestate;

import soliloquy.specs.gamestate.entities.Item;

abstract class CanTellIfItemIsPresentElsewhere extends HasDeletionInvariants {
    protected boolean itemIsPresentElsewhere(Item item) {
        return item.inventoryCharacter() != null || item.equipmentSlot() != null ||
                item.tileFixture() != null || item.tile() != null;
    }
}

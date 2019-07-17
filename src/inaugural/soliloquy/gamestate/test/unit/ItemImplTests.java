package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.ItemImpl;
import inaugural.soliloquy.gamestate.test.stubs.EntityUuidStub;
import inaugural.soliloquy.gamestate.test.stubs.ItemTypeStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.ruleset.entities.ItemType;

import static org.junit.jupiter.api.Assertions.*;

class ItemImplTests {
    private Item _item;

    private final EntityUuid ID = new EntityUuidStub();
    private final ItemType ITEM_TYPE = new ItemTypeStub();

    @BeforeEach
    void setUp() {
        _item = new ItemImpl(ID, ITEM_TYPE);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(Item.class.getCanonicalName(), _item.getInterfaceName());
    }

    @Test
    void testEquals() {
        Item item2 = new ItemImpl(ID, ITEM_TYPE);

        assertEquals(_item, item2);
    }

    @Test
    void testItemType() {
        assertSame(ITEM_TYPE, _item.itemType());
    }

    @Test
    void testSetAndGetCharges() {
        _item.setCharges(123);

        assertEquals(123, (int) _item.getCharges());
    }

    @Test
    void testSetAndGetChargesWhenHasChargesIsFalse() {
        ItemTypeStub._hasCharges = false;

        assertThrows(UnsupportedOperationException.class, () -> _item.setCharges(123));
        assertNull(_item.getCharges());
    }

    @Test
    void testSetNegativeCharges() {
        assertThrows(IllegalArgumentException.class, () -> _item.setCharges(-1));
    }

    @Test
    void testSetAndGetItemsInStack() {
        _item.setNumberInStack(123);

        assertEquals(123, (int) _item.getNumberInStack());
    }

    @Test
    void testSetAndGetNumberInStackWhenIsStackableIsFalse() {
        ItemTypeStub._isStackable = false;

        assertThrows(UnsupportedOperationException.class, () -> _item.setNumberInStack(123));
        assertNull(_item.getNumberInStack());
    }

    @Test
    void testId() {
        assertEquals(ID, _item.id());
    }
}
package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterInventoryImpl;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterInventory;
import soliloquy.specs.gamestate.entities.Item;

import java.util.ArrayList;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class CharacterInventoryImplTests {
    private final Character CHARACTER = new CharacterStub();
    private final CollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    private final Item ITEM = new ItemStub();
    private final Predicate<Item> ITEM_IS_PRESENT_ELSEWHERE = ItemStub::itemIsPresentElsewhere;

    private CharacterInventory _characterInventory;

    @BeforeEach
    void setUp() {
        _characterInventory = new CharacterInventoryImpl(CHARACTER, COLLECTION_FACTORY,
                ITEM_IS_PRESENT_ELSEWHERE);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new CharacterInventoryImpl(null,
                COLLECTION_FACTORY, ITEM_IS_PRESENT_ELSEWHERE));
        assertThrows(IllegalArgumentException.class, () -> new CharacterInventoryImpl(CHARACTER,
                COLLECTION_FACTORY, null));
        assertThrows(IllegalArgumentException.class, () -> new CharacterInventoryImpl(CHARACTER,
                COLLECTION_FACTORY, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(CharacterInventory.class.getCanonicalName(),
                _characterInventory.getInterfaceName());
    }

    @Test
    void testAddAndContains() {
        _characterInventory.add(ITEM);

        assertTrue(_characterInventory.contains(ITEM));
    }

    @Test
    void testAddAndRemove() {
        _characterInventory.add(ITEM);

        assertTrue(_characterInventory.remove(ITEM));
        assertFalse(_characterInventory.remove(ITEM));
    }

    @Test
    void testThrowsWhenItemIsNull() {
        assertThrows(IllegalArgumentException.class, () -> _characterInventory.add(null));
        assertThrows(IllegalArgumentException.class, () -> _characterInventory.contains(null));
        assertThrows(IllegalArgumentException.class, () -> _characterInventory.remove(null));
    }

    @Test
    void testThrowWhenItemIsDeleted() {
        ITEM.delete();

        assertThrows(IllegalArgumentException.class, () -> _characterInventory.add(ITEM));
        assertThrows(IllegalArgumentException.class, () -> _characterInventory.contains(ITEM));
        assertThrows(IllegalArgumentException.class, () -> _characterInventory.remove(ITEM));
    }

    @Test
    void testRepresentation() {
        Item item1 = new ItemStub();
        Item item2 = new ItemStub();
        Item item3 = new ItemStub();
        _characterInventory.add(item1);
        _characterInventory.add(item2);
        _characterInventory.add(item3);

        ReadableCollection<Item> representation = _characterInventory.representation();

        assertNotNull(representation);
        assertNotNull(representation.getArchetype());
        assertEquals(Item.class.getCanonicalName(),
                representation.getArchetype().getInterfaceName());
        assertEquals(3, representation.size());
        assertTrue(representation.contains(item1));
        assertTrue(representation.contains(item2));
        assertTrue(representation.contains(item3));
    }

    @Test
    void testThrowOnAddItemPresentElsewhere() {
        ((ItemStub)ITEM)._inventoryCharacter = new CharacterStub();
        assertThrows(IllegalArgumentException.class, () -> _characterInventory.add(ITEM));

        ((ItemStub)ITEM)._inventoryCharacter = null;
        ((ItemStub)ITEM)._tileFixture = new TileFixtureStub();
        assertThrows(IllegalArgumentException.class, () -> _characterInventory.add(ITEM));

        ((ItemStub)ITEM)._tileFixture = null;
        ((ItemStub)ITEM)._containingTile = new TileStub();
        assertThrows(IllegalArgumentException.class, () -> _characterInventory.add(ITEM));

        ((ItemStub)ITEM)._containingTile = null;
        ((ItemStub)ITEM)._equipmentCharacter = new CharacterStub();
        ((ItemStub)ITEM)._equipmentSlotType = "slotType";
        assertThrows(IllegalArgumentException.class, () -> _characterInventory.add(ITEM));
    }

    @Test
    void testIterator() {
        Item item1 = new ItemStub();
        Item item2 = new ItemStub();
        Item item3 = new ItemStub();

        _characterInventory.add(item1);
        _characterInventory.add(item2);
        _characterInventory.add(item3);

        ArrayList<Item> itemsFromIterator = new ArrayList<>();

        _characterInventory.forEach(itemsFromIterator::add);

        assertEquals(3, itemsFromIterator.size());
        assertTrue(itemsFromIterator.contains(item1));
        assertTrue(itemsFromIterator.contains(item2));
        assertTrue(itemsFromIterator.contains(item3));
    }

    @Test
    void testDelete() {
        _characterInventory.add(ITEM);

        _characterInventory.delete();

        assertTrue(_characterInventory.isDeleted());
        assertTrue(ITEM.isDeleted());
    }

    @Test
    void testCharacterDeletedInvariant() {
        CHARACTER.delete();

        assertThrows(IllegalStateException.class, () -> _characterInventory.add(ITEM));
        assertThrows(IllegalStateException.class, () -> _characterInventory.contains(ITEM));
        assertThrows(IllegalStateException.class, () -> _characterInventory.remove(ITEM));
        assertThrows(IllegalStateException.class, () -> _characterInventory.representation());
    }
}

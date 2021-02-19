package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.TileFixtureItemsImpl;
import inaugural.soliloquy.gamestate.test.fakes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtureItems;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;

import static org.junit.jupiter.api.Assertions.*;

class TileFixtureItemsImplTests {
    private final TileFixture TILE_FIXTURE = new FakeTileFixture();
    private final ListFactory LIST_FACTORY = new FakeListFactory();
    private final Item ITEM = new FakeItem();
    private final Item ITEM_2 = new FakeItem();
    private final Item ITEM_3 = new FakeItem();

    private TileFixtureItems _tileFixtureItems;

    @BeforeEach
    void setUp() {
        _tileFixtureItems = new TileFixtureItemsImpl(TILE_FIXTURE, LIST_FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new TileFixtureItemsImpl(null, LIST_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new TileFixtureItemsImpl(TILE_FIXTURE, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TileFixtureItems.class.getCanonicalName(),
                _tileFixtureItems.getInterfaceName());
    }

    @Test
    void testAddAndContains() {
        assertFalse(_tileFixtureItems.contains(ITEM));
        _tileFixtureItems.add(ITEM);

        assertTrue(_tileFixtureItems.contains(ITEM));
    }

    @Test
    void testAddCallsItemAssignmentFunction() {
        _tileFixtureItems.add(ITEM);

        assertSame(TILE_FIXTURE, ((FakeItem)ITEM)._tileFixture);
    }

    @Test
    void testRemoveCallsItemAssignmentFunction() {
        _tileFixtureItems.add(ITEM);
        assertSame(TILE_FIXTURE, ((FakeItem)ITEM)._tileFixture);

        _tileFixtureItems.remove(ITEM);

        assertNull(((FakeItem)ITEM)._tileFixture);
    }

    @Test
    void testAddItemAlreadyPresentInOtherLocationTypes() {
        ((FakeItem) ITEM)._equipmentCharacter = new FakeCharacter();
        ((FakeItem) ITEM)._equipmentSlotType = "EquipmentSlotType";

        assertThrows(IllegalArgumentException.class, () -> _tileFixtureItems.add(ITEM));

        ((FakeItem) ITEM)._equipmentCharacter = null;
        ((FakeItem) ITEM)._equipmentSlotType = null;
        ((FakeItem) ITEM)._inventoryCharacter = new FakeCharacter();

        assertThrows(IllegalArgumentException.class, () -> _tileFixtureItems.add(ITEM));

        ((FakeItem) ITEM)._inventoryCharacter = null;
        ((FakeItem) ITEM)._tile = new FakeTile();

        assertThrows(IllegalArgumentException.class, () -> _tileFixtureItems.add(ITEM));

        ((FakeItem) ITEM)._tile = null;
        ((FakeItem) ITEM)._tileFixture = new FakeTileFixture();

        assertThrows(IllegalArgumentException.class, () -> _tileFixtureItems.add(ITEM));
    }

    @Test
    void testAddAndContainsWithInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> _tileFixtureItems.add(null));
        assertThrows(IllegalArgumentException.class, () -> _tileFixtureItems.contains(null));
    }

    @Test
    void testRemove() {
        assertFalse(_tileFixtureItems.contains(ITEM));
        _tileFixtureItems.add(ITEM);
        assertTrue(_tileFixtureItems.contains(ITEM));

        assertTrue(_tileFixtureItems.remove(ITEM));
        assertFalse(_tileFixtureItems.remove(ITEM));
        assertFalse(_tileFixtureItems.contains(ITEM));
    }

    @Test
    void testRemoveWithInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> _tileFixtureItems.remove(null));
    }

    @Test
    void testGetRepresentation() {
        _tileFixtureItems.add(ITEM);
        _tileFixtureItems.add(ITEM_2);
        _tileFixtureItems.add(ITEM_3);

        List<Item> representation = _tileFixtureItems.representation();

        assertNotNull(representation);
        assertNotNull(representation.getArchetype());
        assertEquals(Item.class.getCanonicalName(),
                representation.getArchetype().getInterfaceName());
        assertEquals(3, representation.size());
        assertTrue(representation.contains(ITEM));
        assertTrue(representation.contains(ITEM_2));
        assertTrue(representation.contains(ITEM_3));
    }

    @Test
    void testDelete() {
        assertFalse(_tileFixtureItems.isDeleted());
        _tileFixtureItems.add(ITEM);
        _tileFixtureItems.delete();

        assertTrue(_tileFixtureItems.isDeleted());
        assertTrue(ITEM.isDeleted());
    }

    @Test
    void testDeletedInvariant() {
        _tileFixtureItems.delete();

        assertThrows(EntityDeletedException.class, () -> _tileFixtureItems.getInterfaceName());
        assertThrows(EntityDeletedException.class, () -> _tileFixtureItems.representation());
        assertThrows(EntityDeletedException.class, () -> _tileFixtureItems.add(ITEM));
        assertThrows(EntityDeletedException.class, () -> _tileFixtureItems.remove(ITEM));
        assertThrows(EntityDeletedException.class, () -> _tileFixtureItems.contains(ITEM));
    }

    @Test
    void testTileFixtureDeletedInvariant() {
        TILE_FIXTURE.delete();

        assertThrows(IllegalStateException.class, () -> _tileFixtureItems.getInterfaceName());
        assertThrows(IllegalStateException.class, () -> _tileFixtureItems.representation());
        assertThrows(IllegalStateException.class, () -> _tileFixtureItems.add(ITEM));
        assertThrows(IllegalStateException.class, () -> _tileFixtureItems.remove(ITEM));
        assertThrows(IllegalStateException.class, () -> _tileFixtureItems.contains(ITEM));
    }

    @Test
    void testItemAssignmentInvariant() {
        _tileFixtureItems.add(ITEM);
        ((FakeItem) ITEM)._tileFixture = null;

        assertThrows(IllegalStateException.class, () -> _tileFixtureItems.add(ITEM));
        assertThrows(IllegalStateException.class, () -> _tileFixtureItems.remove(ITEM));
        assertThrows(IllegalStateException.class, () -> _tileFixtureItems.contains(ITEM));
    }
}

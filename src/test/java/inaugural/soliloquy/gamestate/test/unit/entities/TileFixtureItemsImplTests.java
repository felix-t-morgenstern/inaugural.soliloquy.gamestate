package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.TileFixtureItemsImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeCharacter;
import inaugural.soliloquy.gamestate.test.fakes.FakeItem;
import inaugural.soliloquy.gamestate.test.fakes.FakeTile;
import inaugural.soliloquy.gamestate.test.fakes.FakeTileFixture;
import org.junit.Before;
import org.junit.Test;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtureItems;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;

import java.util.List;

import static org.junit.Assert.*;

public class TileFixtureItemsImplTests {
    private final TileFixture TILE_FIXTURE = new FakeTileFixture();
    private final Item ITEM = new FakeItem();
    private final Item ITEM_2 = new FakeItem();
    private final Item ITEM_3 = new FakeItem();

    private TileFixtureItems _tileFixtureItems;

    @Before
    public void setUp() {
        _tileFixtureItems = new TileFixtureItemsImpl(TILE_FIXTURE);
    }

    @Test
    public void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new TileFixtureItemsImpl(null));
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(TileFixtureItems.class.getCanonicalName(),
                _tileFixtureItems.getInterfaceName());
    }

    @Test
    public void testAddAndContains() {
        assertFalse(_tileFixtureItems.contains(ITEM));
        _tileFixtureItems.add(ITEM);

        assertTrue(_tileFixtureItems.contains(ITEM));
    }

    @Test
    public void testAddCallsItemAssignmentFunction() {
        _tileFixtureItems.add(ITEM);

        assertSame(TILE_FIXTURE, ((FakeItem) ITEM).tileFixture);
    }

    @Test
    public void testRemoveCallsItemAssignmentFunction() {
        _tileFixtureItems.add(ITEM);
        assertSame(TILE_FIXTURE, ((FakeItem) ITEM).tileFixture);

        _tileFixtureItems.remove(ITEM);

        assertNull(((FakeItem) ITEM).tileFixture);
    }

    @Test
    public void testAddItemAlreadyPresentInOtherLocationTypes() {
        ((FakeItem) ITEM).equipmentCharacter = new FakeCharacter();
        ((FakeItem) ITEM).equipmentSlotType = "EquipmentSlotType";

        assertThrows(IllegalArgumentException.class, () -> _tileFixtureItems.add(ITEM));

        ((FakeItem) ITEM).equipmentCharacter = null;
        ((FakeItem) ITEM).equipmentSlotType = null;
        ((FakeItem) ITEM).inventoryCharacter = new FakeCharacter();

        assertThrows(IllegalArgumentException.class, () -> _tileFixtureItems.add(ITEM));

        ((FakeItem) ITEM).inventoryCharacter = null;
        ((FakeItem) ITEM).tile = new FakeTile();

        assertThrows(IllegalArgumentException.class, () -> _tileFixtureItems.add(ITEM));

        ((FakeItem) ITEM).tile = null;
        ((FakeItem) ITEM).tileFixture = new FakeTileFixture();

        assertThrows(IllegalArgumentException.class, () -> _tileFixtureItems.add(ITEM));
    }

    @Test
    public void testAddAndContainsWithInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> _tileFixtureItems.add(null));
        assertThrows(IllegalArgumentException.class, () -> _tileFixtureItems.contains(null));
    }

    @Test
    public void testRemove() {
        assertFalse(_tileFixtureItems.contains(ITEM));
        _tileFixtureItems.add(ITEM);
        assertTrue(_tileFixtureItems.contains(ITEM));

        assertTrue(_tileFixtureItems.remove(ITEM));
        assertFalse(_tileFixtureItems.remove(ITEM));
        assertFalse(_tileFixtureItems.contains(ITEM));
    }

    @Test
    public void testRemoveWithInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> _tileFixtureItems.remove(null));
    }

    @Test
    public void testGetRepresentation() {
        _tileFixtureItems.add(ITEM);
        _tileFixtureItems.add(ITEM_2);
        _tileFixtureItems.add(ITEM_3);

        List<Item> representation = _tileFixtureItems.representation();

        assertNotNull(representation);
        assertEquals(3, representation.size());
        assertTrue(representation.contains(ITEM));
        assertTrue(representation.contains(ITEM_2));
        assertTrue(representation.contains(ITEM_3));
    }

    @Test
    public void testDelete() {
        assertFalse(_tileFixtureItems.isDeleted());
        _tileFixtureItems.add(ITEM);
        _tileFixtureItems.delete();

        assertTrue(_tileFixtureItems.isDeleted());
        assertTrue(ITEM.isDeleted());
    }

    @Test
    public void testDeletedInvariant() {
        _tileFixtureItems.delete();

        assertThrows(EntityDeletedException.class, () -> _tileFixtureItems.getInterfaceName());
        assertThrows(EntityDeletedException.class, () -> _tileFixtureItems.representation());
        assertThrows(EntityDeletedException.class, () -> _tileFixtureItems.add(ITEM));
        assertThrows(EntityDeletedException.class, () -> _tileFixtureItems.remove(ITEM));
        assertThrows(EntityDeletedException.class, () -> _tileFixtureItems.contains(ITEM));
    }

    @Test
    public void testTileFixtureDeletedInvariant() {
        TILE_FIXTURE.delete();

        assertThrows(IllegalStateException.class, () -> _tileFixtureItems.getInterfaceName());
        assertThrows(IllegalStateException.class, () -> _tileFixtureItems.representation());
        assertThrows(IllegalStateException.class, () -> _tileFixtureItems.add(ITEM));
        assertThrows(IllegalStateException.class, () -> _tileFixtureItems.remove(ITEM));
        assertThrows(IllegalStateException.class, () -> _tileFixtureItems.contains(ITEM));
    }

    @Test
    public void testItemAssignmentInvariant() {
        _tileFixtureItems.add(ITEM);
        ((FakeItem) ITEM).tileFixture = null;

        assertThrows(IllegalStateException.class, () -> _tileFixtureItems.add(ITEM));
        assertThrows(IllegalStateException.class, () -> _tileFixtureItems.remove(ITEM));
        assertThrows(IllegalStateException.class, () -> _tileFixtureItems.contains(ITEM));
    }
}

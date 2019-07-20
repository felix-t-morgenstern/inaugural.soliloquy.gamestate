package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TileItemsImpl;
import inaugural.soliloquy.gamestate.test.stubs.ItemStub;
import inaugural.soliloquy.gamestate.test.stubs.MapFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.TileStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileItems;

import static org.junit.jupiter.api.Assertions.*;

class TileItemsImplTests {
    private final Tile TILE = new TileStub();
    private final Item ITEM = new ItemStub();
    private final Item ITEM_2 = new ItemStub();
    private final Item ITEM_3 = new ItemStub();
    private final MapFactory MAP_FACTORY = new MapFactoryStub();

    private TileItems _tileItems;

    @BeforeEach
    void setUp() {
        _tileItems = new TileItemsImpl(TILE, MAP_FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new TileItemsImpl(null, MAP_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new TileItemsImpl(TILE, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TileItems.class.getCanonicalName(), _tileItems.getInterfaceName());
    }

    @Test
    void testAddAndContains() {
        assertFalse(_tileItems.contains(ITEM));
        _tileItems.add(ITEM);

        assertTrue(_tileItems.contains(ITEM));
    }

    @Test
    void testRemove() {
        assertFalse(_tileItems.remove(ITEM));
        _tileItems.add(ITEM);

        assertTrue(_tileItems.remove(ITEM));
        assertFalse(_tileItems.remove(ITEM));
    }

    @Test
    void testAddContainsAndRemoveWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _tileItems.add(null));
        assertThrows(IllegalArgumentException.class, () -> _tileItems.add(null, 0));
        assertThrows(IllegalArgumentException.class, () -> _tileItems.contains(null));
        assertThrows(IllegalArgumentException.class, () -> _tileItems.remove(null));
    }

    @Test
    void testGetRepresentation() {
        _tileItems.add(ITEM);
        _tileItems.add(ITEM_2);
        _tileItems.add(ITEM_3);

        ReadableMap<Item,Integer> representation = _tileItems.representation();

        assertNotNull(representation);
        assertNotNull(representation.getFirstArchetype());
        assertEquals(Item.class.getCanonicalName(),
                representation.getFirstArchetype().getInterfaceName());
        assertNotNull(representation.getSecondArchetype());
        assertEquals(Integer.class.getCanonicalName(),
                representation.getSecondArchetype().getClass().getCanonicalName());
        assertEquals(3, representation.size());
        assertTrue(representation.containsKey(ITEM));
        assertTrue(representation.containsKey(ITEM_2));
        assertTrue(representation.containsKey(ITEM_3));
    }

    @Test
    void testAddAtZIndex() {
        final int zIndex = 123;
        _tileItems.add(ITEM, zIndex);
        ReadableMap<Item,Integer> representation = _tileItems.representation();

        assertEquals((Integer) zIndex, representation.get(ITEM));
    }

    @Test
    void testDelete() {
        assertFalse(_tileItems.isDeleted());

        _tileItems.add(ITEM);
        _tileItems.add(ITEM_2);
        _tileItems.add(ITEM_3);

        _tileItems.delete();

        assertTrue(_tileItems.isDeleted());

        assertTrue(ITEM.isDeleted());
        assertTrue(ITEM_2.isDeleted());
        assertTrue(ITEM_3.isDeleted());
    }

    @Test
    void testDeletedInvariant() {
        _tileItems.delete();

        assertThrows(IllegalStateException.class, () -> _tileItems.getInterfaceName());
        assertThrows(IllegalStateException.class, () -> _tileItems.representation());
        assertThrows(IllegalStateException.class, () -> _tileItems.add(ITEM));
        assertThrows(IllegalStateException.class, () -> _tileItems.add(ITEM,0));
        assertThrows(IllegalStateException.class, () -> _tileItems.contains(ITEM));
        assertThrows(IllegalStateException.class, () -> _tileItems.remove(ITEM));
    }

    @Test
    void testTileDeletedInvariant() {
        TILE.delete();

        assertThrows(IllegalStateException.class, () -> _tileItems.getInterfaceName());
        assertThrows(IllegalStateException.class, () -> _tileItems.representation());
        assertThrows(IllegalStateException.class, () -> _tileItems.add(ITEM));
        assertThrows(IllegalStateException.class, () -> _tileItems.add(ITEM,0));
        assertThrows(IllegalStateException.class, () -> _tileItems.contains(ITEM));
        assertThrows(IllegalStateException.class, () -> _tileItems.remove(ITEM));
    }
}

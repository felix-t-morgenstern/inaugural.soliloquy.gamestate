package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TileFixtureItems;
import inaugural.soliloquy.gamestate.test.stubs.CollectionFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.ItemStub;
import inaugural.soliloquy.gamestate.test.stubs.TileFixtureStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.ICollectionFactory;
import soliloquy.specs.common.valueobjects.ICollection;
import soliloquy.specs.gamestate.entities.IItem;
import soliloquy.specs.gamestate.entities.ITileFixture;
import soliloquy.specs.gamestate.entities.ITileFixtureItems;

import static org.junit.jupiter.api.Assertions.*;

class TileFixtureItemsTests {
    private final ITileFixture TILE_FIXTURE = new TileFixtureStub();
    private final ICollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    private final IItem ITEM = new ItemStub();
    private final IItem ITEM_2 = new ItemStub();
    private final IItem ITEM_3 = new ItemStub();

    private ITileFixtureItems _tileFixtureItems;

    @BeforeEach
    void setUp() {
        _tileFixtureItems = new TileFixtureItems(TILE_FIXTURE, COLLECTION_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new TileFixtureItems(null, COLLECTION_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new TileFixtureItems(TILE_FIXTURE, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(ITileFixtureItems.class.getCanonicalName(),
                _tileFixtureItems.getInterfaceName());
    }

    @Test
    void testAddAndContains() {
        assertFalse(_tileFixtureItems.contains(ITEM));
        _tileFixtureItems.add(ITEM);

        assertTrue(_tileFixtureItems.contains(ITEM));
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

        ICollection<IItem> representation = _tileFixtureItems.getRepresentation();

        assertNotNull(representation);
        assertNotNull(representation.getArchetype());
        assertEquals(IItem.class.getCanonicalName(),
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

        assertThrows(IllegalStateException.class, () -> _tileFixtureItems.getInterfaceName());
        assertThrows(IllegalStateException.class, () -> _tileFixtureItems.getRepresentation());
        assertThrows(IllegalStateException.class, () -> _tileFixtureItems.add(ITEM));
        assertThrows(IllegalStateException.class, () -> _tileFixtureItems.remove(ITEM));
        assertThrows(IllegalStateException.class, () -> _tileFixtureItems.contains(ITEM));
    }

    @Test
    void testTileFixtureDeletedInvariant() {
        TILE_FIXTURE.delete();

        assertThrows(IllegalStateException.class, () -> _tileFixtureItems.getInterfaceName());
        assertThrows(IllegalStateException.class, () -> _tileFixtureItems.getRepresentation());
        assertThrows(IllegalStateException.class, () -> _tileFixtureItems.add(ITEM));
        assertThrows(IllegalStateException.class, () -> _tileFixtureItems.remove(ITEM));
        assertThrows(IllegalStateException.class, () -> _tileFixtureItems.contains(ITEM));
    }
}

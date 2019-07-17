package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TileFixtureItemsImpl;
import inaugural.soliloquy.gamestate.test.stubs.CollectionFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.ItemStub;
import inaugural.soliloquy.gamestate.test.stubs.TileFixtureStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.infrastructure.ReadOnlyCollection;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtureItems;

import static org.junit.jupiter.api.Assertions.*;

class TileFixtureItemsImplTests {
    private final TileFixture TILE_FIXTURE = new TileFixtureStub();
    private final CollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    private final Item ITEM = new ItemStub();
    private final Item ITEM_2 = new ItemStub();
    private final Item ITEM_3 = new ItemStub();

    private TileFixtureItems _tileFixtureItems;

    @BeforeEach
    void setUp() {
        _tileFixtureItems = new TileFixtureItemsImpl(TILE_FIXTURE, COLLECTION_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new TileFixtureItemsImpl(null, COLLECTION_FACTORY));
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

        ReadOnlyCollection<Item> representation = _tileFixtureItems.representation();

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

        assertThrows(IllegalStateException.class, () -> _tileFixtureItems.getInterfaceName());
        assertThrows(IllegalStateException.class, () -> _tileFixtureItems.representation());
        assertThrows(IllegalStateException.class, () -> _tileFixtureItems.add(ITEM));
        assertThrows(IllegalStateException.class, () -> _tileFixtureItems.remove(ITEM));
        assertThrows(IllegalStateException.class, () -> _tileFixtureItems.contains(ITEM));
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
}
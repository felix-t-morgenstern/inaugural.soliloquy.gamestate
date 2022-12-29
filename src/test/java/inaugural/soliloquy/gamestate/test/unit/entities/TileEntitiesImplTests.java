package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.TileEntitiesImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeCharacter;
import inaugural.soliloquy.gamestate.test.fakes.FakeItem;
import inaugural.soliloquy.gamestate.test.fakes.FakeTile;
import inaugural.soliloquy.gamestate.test.fakes.FakeTileFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileEntities;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;

import java.util.ArrayList;
import java.util.Map;

import static inaugural.soliloquy.tools.generic.Archetypes.generateSimpleArchetype;
import static org.junit.jupiter.api.Assertions.*;

class TileEntitiesImplTests {
    private final Tile TILE = new FakeTile();
    private final Item ARCHETYPE = generateSimpleArchetype(Item.class);
    private final Item ITEM = new FakeItem();
    private final Item ITEM_2 = new FakeItem();
    private final Item ITEM_3 = new FakeItem();

    private TileEntitiesImpl<Item> _tileEntities;

    @BeforeEach
    void setUp() {
        _tileEntities = new TileEntitiesImpl<>(TILE, ARCHETYPE);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new TileEntitiesImpl<>(null, ARCHETYPE));
        assertThrows(IllegalArgumentException.class, () -> new TileEntitiesImpl<>(TILE, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TileEntities.class.getCanonicalName() + "<" + Item.class.getCanonicalName() +
                        ">",
                _tileEntities.getInterfaceName());
    }

    @Test
    void testAddAndContains() {
        assertFalse(_tileEntities.contains(ITEM));
        _tileEntities.add(ITEM);

        assertTrue(_tileEntities.contains(ITEM));
    }

    @Test
    void testAddItemAlreadyElsewhereInVariousLocations() {
        ((FakeItem) ITEM).equipmentCharacter = new FakeCharacter();
        ((FakeItem) ITEM).equipmentSlotType = "EquipmentSlotType";

        assertThrows(IllegalArgumentException.class, () -> _tileEntities.add(ITEM));
        assertThrows(IllegalArgumentException.class, () -> _tileEntities.add(ITEM, 0));

        ((FakeItem) ITEM).equipmentCharacter = null;
        ((FakeItem) ITEM).equipmentSlotType = null;
        ((FakeItem) ITEM).inventoryCharacter = new FakeCharacter();

        assertThrows(IllegalArgumentException.class, () -> _tileEntities.add(ITEM));
        assertThrows(IllegalArgumentException.class, () -> _tileEntities.add(ITEM, 0));

        ((FakeItem) ITEM).inventoryCharacter = null;
        ((FakeItem) ITEM).tile = new FakeTile();

        assertThrows(IllegalArgumentException.class, () -> _tileEntities.add(ITEM));
        assertThrows(IllegalArgumentException.class, () -> _tileEntities.add(ITEM, 0));

        ((FakeItem) ITEM).tile = null;
        ((FakeItem) ITEM).tileFixture = new FakeTileFixture();

        assertThrows(IllegalArgumentException.class, () -> _tileEntities.add(ITEM));
        assertThrows(IllegalArgumentException.class, () -> _tileEntities.add(ITEM, 0));
    }

    @Test
    void testRemove() {
        assertFalse(_tileEntities.remove(ITEM));
        _tileEntities.add(ITEM);

        assertTrue(_tileEntities.remove(ITEM));
        assertFalse(_tileEntities.remove(ITEM));
    }

    @Test
    void testAddContainsAndRemoveWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _tileEntities.add(null));
        assertThrows(IllegalArgumentException.class, () -> _tileEntities.add(null, 0));
        assertThrows(IllegalArgumentException.class, () -> _tileEntities.contains(null));
        assertThrows(IllegalArgumentException.class, () -> _tileEntities.remove(null));
    }

    @Test
    void testGetRepresentation() {
        _tileEntities.add(ITEM);
        _tileEntities.add(ITEM_2);
        _tileEntities.add(ITEM_3);

        Map<Item, Integer> representation = _tileEntities.representation();

        assertNotNull(representation);
        assertEquals(3, representation.size());
        assertTrue(representation.containsKey(ITEM));
        assertTrue(representation.containsKey(ITEM_2));
        assertTrue(representation.containsKey(ITEM_3));
    }

    @Test
    void testAddAtZIndex() {
        final int zIndex = 123;
        _tileEntities.add(ITEM, zIndex);
        Map<Item, Integer> representation = _tileEntities.representation();

        assertEquals((Integer) zIndex, representation.get(ITEM));
    }

    @Test
    void testIterator() {
        _tileEntities.add(ITEM, 123);
        _tileEntities.add(ITEM_2, 456);
        _tileEntities.add(ITEM_3, 789);

        ArrayList<Pair<Item, Integer>> fromIterator = new ArrayList<>();

        _tileEntities.forEach(fromIterator::add);

        assertEquals(3, fromIterator.size());
        boolean[] entityFound = new boolean[3];
        fromIterator.forEach(pair -> {
            if (pair.getItem1() == ITEM) {
                assertFalse(entityFound[0]);
                assertEquals(123, pair.getItem2());
                entityFound[0] = true;
            }
            if (pair.getItem1() == ITEM_2) {
                assertFalse(entityFound[1]);
                assertEquals(456, pair.getItem2());
                entityFound[1] = true;
            }
            if (pair.getItem1() == ITEM_3) {
                assertFalse(entityFound[2]);
                assertEquals(789, pair.getItem2());
                entityFound[2] = true;
            }
        });
    }

    @Test
    void testInitializeActionAfterAdding() {
        final ArrayList<Item> addedToGameZone = new ArrayList<>();

        _tileEntities.initializeActionAfterAdding(addedToGameZone::add);
        _tileEntities.add(ITEM);

        assertEquals(1, addedToGameZone.size());
        assertTrue(addedToGameZone.contains(ITEM));
    }

    @Test
    void testInitializeActionAfterRemoving() {
        final ArrayList<Item> removedFromGameZone = new ArrayList<>();

        _tileEntities.initializeActionAfterRemoving(
                removedFromGameZone::add);
        _tileEntities.remove(ITEM);

        assertEquals(0, removedFromGameZone.size());

        _tileEntities.add(ITEM);
        _tileEntities.remove(ITEM);

        assertEquals(1, removedFromGameZone.size());
        assertTrue(removedFromGameZone.contains(ITEM));
    }

    @Test
    void testInitializeMoreThanOnce() {
        _tileEntities.initializeActionAfterAdding(null);
        _tileEntities.initializeActionAfterAdding(e -> {});
        assertThrows(UnsupportedOperationException.class,
                () -> _tileEntities.initializeActionAfterAdding(e -> {}));

        _tileEntities.initializeActionAfterRemoving(null);
        _tileEntities.initializeActionAfterRemoving(e -> {});
        assertThrows(UnsupportedOperationException.class,
                () -> _tileEntities.initializeActionAfterRemoving(e -> {}));
    }

    @Test
    void testGetArchetype() {
        assertSame(ARCHETYPE, _tileEntities.getArchetype());
    }

    @Test
    void testDelete() {
        assertFalse(_tileEntities.isDeleted());

        _tileEntities.add(ITEM);
        _tileEntities.add(ITEM_2);
        _tileEntities.add(ITEM_3);

        _tileEntities.delete();

        assertTrue(_tileEntities.isDeleted());

        assertTrue(ITEM.isDeleted());
        assertTrue(ITEM_2.isDeleted());
        assertTrue(ITEM_3.isDeleted());
    }

    @Test
    void testDeletedInvariant() {
        _tileEntities.delete();

        assertThrows(EntityDeletedException.class, () -> _tileEntities.getInterfaceName());
        assertThrows(EntityDeletedException.class, () -> _tileEntities.representation());
        assertThrows(EntityDeletedException.class, () -> _tileEntities.add(ITEM));
        assertThrows(EntityDeletedException.class, () -> _tileEntities.add(ITEM, 0));
        assertThrows(EntityDeletedException.class, () -> _tileEntities.contains(ITEM));
        assertThrows(EntityDeletedException.class, () -> _tileEntities.remove(ITEM));
        assertThrows(EntityDeletedException.class, () -> _tileEntities.iterator());
    }

    @Test
    void testTileDeletedInvariant() {
        TILE.delete();

        assertThrows(IllegalStateException.class, () -> _tileEntities.getInterfaceName());
        assertThrows(IllegalStateException.class, () -> _tileEntities.representation());
        assertThrows(IllegalStateException.class, () -> _tileEntities.add(ITEM));
        assertThrows(IllegalStateException.class, () -> _tileEntities.add(ITEM, 0));
        assertThrows(IllegalStateException.class, () -> _tileEntities.contains(ITEM));
        assertThrows(IllegalStateException.class, () -> _tileEntities.remove(ITEM));
        assertThrows(IllegalStateException.class, () -> _tileEntities.iterator());
    }

    @Test
    void testItemAssignmentInvariant() {
        _tileEntities.add(ITEM);
        ((FakeItem) ITEM).tile = null;

        assertThrows(IllegalStateException.class, () -> _tileEntities.add(ITEM));
        assertThrows(IllegalStateException.class, () -> _tileEntities.add(ITEM, 0));
        assertThrows(IllegalStateException.class, () -> _tileEntities.contains(ITEM));
        assertThrows(IllegalStateException.class, () -> _tileEntities.remove(ITEM));
    }
}

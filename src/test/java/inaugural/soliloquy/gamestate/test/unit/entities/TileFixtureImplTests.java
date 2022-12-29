package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.TileFixtureImpl;
import inaugural.soliloquy.gamestate.test.fakes.*;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Vertex;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtureItems;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.gamestate.factories.TileFixtureItemsFactory;

import java.util.HashMap;
import java.util.UUID;

import static inaugural.soliloquy.tools.random.Random.randomFloat;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.jupiter.api.Assertions.*;

class TileFixtureImplTests {
    private final UUID UUID = java.util.UUID.randomUUID();
    private final FakeFixtureType TYPE = new FakeFixtureType();
    private final TileFixtureItemsFactory TILE_FIXTURE_ITEMS_FACTORY =
            new FakeTileFixtureItemsFactory();
    private final VariableCache DATA = new VariableCacheStub();

    private TileFixture tileFixture;

    @BeforeEach
    void setUp() {
        tileFixture = new TileFixtureImpl(UUID, TYPE, TILE_FIXTURE_ITEMS_FACTORY, DATA);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new TileFixtureImpl(null, TYPE, TILE_FIXTURE_ITEMS_FACTORY, DATA));
        assertThrows(IllegalArgumentException.class,
                () -> new TileFixtureImpl(UUID, null, TILE_FIXTURE_ITEMS_FACTORY, DATA));
        assertThrows(IllegalArgumentException.class,
                () -> new TileFixtureImpl(UUID, TYPE, null, DATA));
        assertThrows(IllegalArgumentException.class,
                () -> new TileFixtureImpl(UUID, TYPE, TILE_FIXTURE_ITEMS_FACTORY, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TileFixture.class.getCanonicalName(), tileFixture.getInterfaceName());
    }

    @Test
    void testUuid() {
        assertSame(UUID, tileFixture.uuid());
    }

    @Test
    void testType() {
        assertSame(TYPE, tileFixture.type());
    }

    @Test
    void testMovementEvents() {
        assertNotNull(tileFixture.movementEvents());
    }

    @Test
    void testAbilityEvents() {
        assertNotNull(tileFixture.abilityEvents());
    }

    @Test
    void testMakeGameEventTarget() {
        GameEventTarget gameEventTarget = tileFixture.makeGameEventTarget();

        assertNotNull(gameEventTarget);
        assertNull(gameEventTarget.tile());
        assertNull(gameEventTarget.tileWallSegment());
        assertNotNull(gameEventTarget.tileFixture());
        assertEquals(GameEventTarget.class.getCanonicalName(), gameEventTarget.getInterfaceName());
    }

    @Test
    void testTileFixtureItems() {
        assertNotNull(tileFixture.items());
        assertSame(tileFixture, ((FakeTileFixtureItems) tileFixture.items()).TILE_FIXTURE);
    }

    @Test
    void testAssignTileFixtureToTile() {
        Tile tile = new FakeTile();
        assertNull(tileFixture.tile());

        // NB: TileFixture.TILE should NOT be exposed, and calling TileFixture.assignCharacterToTile
        // violates the invariant condition; therefore, TileFixturesStub calls
        // TileFixture.assignCharacterToTile indirectly, as it should be in production code
        tile.fixtures().add(tileFixture);

        assertSame(tile, tileFixture.tile());
    }

    @Test
    void testData() {
        assertSame(DATA, tileFixture.data());
    }

    @Test
    void testDelete() {
        Tile tile = new FakeTile();
        tile.fixtures().add(tileFixture);
        TileFixtureItems containedItems = tileFixture.items();
        HashMap<TileFixture, Integer> entities =
                ((FakeTileEntities<TileFixture>) tile.fixtures()).ENTITIES;
        int originalNumberOfContainedItems = entities.size();

        tileFixture.delete();

        // The test is simply asking the TileFixtureItems to handle deletion of its Items
        assertTrue(tileFixture.isDeleted());
        assertTrue(containedItems.isDeleted());

        assertFalse(tile.fixtures().contains(tileFixture));
        assertFalse(entities.containsKey(tileFixture));
        assertEquals(originalNumberOfContainedItems - 1, entities.size());
    }

    @Test
    void testSetAndGetName() {
        String name = randomString();

        tileFixture.setName(name);

        assertEquals(name, tileFixture.getName());
    }

    @Test
    void testSetAndGetTileOffset() {
        Vertex tileOffset = Vertex.of(randomFloat(), randomFloat());

        tileFixture.setTileOffset(tileOffset);

        assertSame(tileOffset, tileFixture.getTileOffset());
    }

    @Test
    void testCreatedWithDefaultOffsets() {
        assertEquals(Vertex.of(FakeFixtureType.DEFAULT_X_TILE_WIDTH_OFFSET,
                FakeFixtureType.DEFAULT_Y_TILE_HEIGHT_OFFSET), tileFixture.getTileOffset());
    }

    @Test
    void testDeletedInvariant() {
        tileFixture.delete();

        assertThrows(EntityDeletedException.class, () -> tileFixture.tile());
        assertThrows(EntityDeletedException.class, () -> tileFixture.type());
        assertThrows(EntityDeletedException.class, () -> tileFixture.getTileOffset());
        assertThrows(EntityDeletedException.class,
                () -> tileFixture.setTileOffset(Vertex.of(0f, 0f)));
        assertThrows(EntityDeletedException.class, () -> tileFixture.movementEvents());
        assertThrows(EntityDeletedException.class, () -> tileFixture.items());
        assertThrows(EntityDeletedException.class,
                () -> tileFixture.assignTileAfterAddedToTileEntitiesOfType(null));
        assertThrows(EntityDeletedException.class, () -> tileFixture.data());
        assertThrows(EntityDeletedException.class, () -> tileFixture.getName());
        assertThrows(EntityDeletedException.class, () -> tileFixture.setName(""));
    }

    @Test
    void testContainingTileInvariant() {
        Tile tile = new FakeTile();
        tile.fixtures().add(tileFixture);
        ((FakeTileEntities<TileFixture>) tile.fixtures()).ENTITIES.remove(tileFixture);

        assertThrows(IllegalStateException.class, () -> tileFixture.tile());
        assertThrows(IllegalStateException.class, () -> tileFixture.type());
        assertThrows(IllegalStateException.class, () -> tileFixture.getTileOffset());
        assertThrows(IllegalStateException.class,
                () -> tileFixture.setTileOffset(Vertex.of(0f, 0f)));
        assertThrows(IllegalStateException.class, () -> tileFixture.movementEvents());
        assertThrows(IllegalStateException.class, () -> tileFixture.items());
        assertThrows(IllegalStateException.class,
                () -> tileFixture.assignTileAfterAddedToTileEntitiesOfType(null));
        assertThrows(IllegalStateException.class, () -> tileFixture.data());
        assertThrows(IllegalStateException.class, () -> tileFixture.delete());
        assertThrows(IllegalStateException.class, () -> tileFixture.getName());
        assertThrows(IllegalStateException.class, () -> tileFixture.setName(""));
    }
}

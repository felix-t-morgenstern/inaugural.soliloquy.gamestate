package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TileFixturesImpl;
import inaugural.soliloquy.gamestate.test.stubs.MapFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.TileFixtureStub;
import inaugural.soliloquy.gamestate.test.stubs.TileStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtures;

import static org.junit.jupiter.api.Assertions.*;

class TileFixturesImplTests {
    private final Tile TILE = new TileStub();
    private final MapFactory MAP_FACTORY = new MapFactoryStub();
    private final TileFixture TILE_FIXTURE = new TileFixtureStub();
    private final TileFixture TILE_FIXTURE_2 = new TileFixtureStub();
    private final TileFixture TILE_FIXTURE_3 = new TileFixtureStub();

    private TileFixtures _tileFixtures;

    @BeforeEach
    void setUp() {
        _tileFixtures = new TileFixturesImpl(TILE, MAP_FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new TileFixturesImpl(null, MAP_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new TileFixturesImpl(TILE, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TileFixtures.class.getCanonicalName(), _tileFixtures.getInterfaceName());
    }

    @Test
    void testAddAndContainsAndRemove() {
        assertFalse(_tileFixtures.contains(TILE_FIXTURE));
        assertFalse(_tileFixtures.remove(TILE_FIXTURE));
        _tileFixtures.add(TILE_FIXTURE);

        assertTrue(_tileFixtures.contains(TILE_FIXTURE));

        assertTrue(_tileFixtures.remove(TILE_FIXTURE));

        assertFalse(_tileFixtures.contains(TILE_FIXTURE));
    }

    @Test
    void testAddTileFixtureAlreadyPresentOnAnotherTile() {
        // TODO: Complete and pass this test
    }

    @Test
    void testAddAndContainsWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _tileFixtures.add(null));
        assertThrows(IllegalArgumentException.class, () -> _tileFixtures.add(null, 0));
        assertThrows(IllegalArgumentException.class, () -> _tileFixtures.contains(null));
        assertThrows(IllegalArgumentException.class, () -> _tileFixtures.remove(null));
    }

    @Test
    void testRepresentation() {
        _tileFixtures.add(TILE_FIXTURE);
        _tileFixtures.add(TILE_FIXTURE_2);
        _tileFixtures.add(TILE_FIXTURE_3);

        ReadableMap<TileFixture,Integer> representation = _tileFixtures.representation();

        assertNotNull(representation);
        assertNotNull(representation.getFirstArchetype());
        assertEquals(TileFixture.class.getCanonicalName(),
                representation.getFirstArchetype().getInterfaceName());
        assertNotNull(representation.getSecondArchetype());
        assertEquals(Integer.class.getCanonicalName(),
                representation.getSecondArchetype().getClass().getCanonicalName());
        assertEquals(3, representation.size());
        assertTrue(representation.containsKey(TILE_FIXTURE));
        assertTrue(representation.containsKey(TILE_FIXTURE_2));
        assertTrue(representation.containsKey(TILE_FIXTURE_3));
    }

    @Test
    void testAddAtZIndex() {
        final int zIndex = 123;
        _tileFixtures.add(TILE_FIXTURE, zIndex);
        ReadableMap<TileFixture,Integer> representation = _tileFixtures.representation();

        assertEquals((Integer) zIndex, representation.get(TILE_FIXTURE));
    }

    @Test
    void testDelete() {
        assertFalse(_tileFixtures.isDeleted());

        _tileFixtures.add(TILE_FIXTURE);
        _tileFixtures.add(TILE_FIXTURE_2);
        _tileFixtures.add(TILE_FIXTURE_3);

        _tileFixtures.delete();

        assertTrue(_tileFixtures.isDeleted());

        assertTrue(TILE_FIXTURE.isDeleted());
        assertTrue(TILE_FIXTURE_2.isDeleted());
        assertTrue(TILE_FIXTURE_3.isDeleted());
    }

    @Test
    void testDeletedInvariant() {
        _tileFixtures.delete();

        assertThrows(IllegalStateException.class, () -> _tileFixtures.getInterfaceName());
        assertThrows(IllegalStateException.class, () -> _tileFixtures.representation());
        assertThrows(IllegalStateException.class, () -> _tileFixtures.add(TILE_FIXTURE));
        assertThrows(IllegalStateException.class, () -> _tileFixtures.add(TILE_FIXTURE,0));
        assertThrows(IllegalStateException.class, () -> _tileFixtures.contains(TILE_FIXTURE));
        assertThrows(IllegalStateException.class, () -> _tileFixtures.remove(TILE_FIXTURE));
    }

    @Test
    void testTileDeletedInvariant() {
        TILE.delete();

        assertThrows(IllegalStateException.class, () -> _tileFixtures.getInterfaceName());
        assertThrows(IllegalStateException.class, () -> _tileFixtures.representation());
        assertThrows(IllegalStateException.class, () -> _tileFixtures.add(TILE_FIXTURE));
        assertThrows(IllegalStateException.class, () -> _tileFixtures.add(TILE_FIXTURE,0));
        assertThrows(IllegalStateException.class, () -> _tileFixtures.contains(TILE_FIXTURE));
        assertThrows(IllegalStateException.class, () -> _tileFixtures.remove(TILE_FIXTURE));
    }

    @Test
    void testTileFixtureAssignmentInvariant() {
        _tileFixtures.add(TILE_FIXTURE);
        ((TileFixtureStub)TILE_FIXTURE)._tile = null;

        assertThrows(IllegalStateException.class, () -> _tileFixtures.add(TILE_FIXTURE));
        assertThrows(IllegalStateException.class, () -> _tileFixtures.add(TILE_FIXTURE,0));
        assertThrows(IllegalStateException.class, () -> _tileFixtures.contains(TILE_FIXTURE));
        assertThrows(IllegalStateException.class, () -> _tileFixtures.remove(TILE_FIXTURE));
    }
}

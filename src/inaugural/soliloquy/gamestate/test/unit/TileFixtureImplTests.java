package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TileFixtureImpl;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtureItems;
import soliloquy.specs.gamestate.entities.gameevents.GameEvent;
import soliloquy.specs.gamestate.factories.TileFixtureItemsFactory;
import soliloquy.specs.ruleset.entities.FixtureType;

import static org.junit.jupiter.api.Assertions.*;

class TileFixtureImplTests {
    private TileFixture _tileFixture;

    private final FixtureType FIXTURE_TYPE = new FixtureTypeStub();
    private final Coordinate PIXEL_OFFSET = new CoordinateStub();
    private final TileFixtureItemsFactory TILE_FIXTURE_ITEMS_FACTORY =
            new TileFixtureItemsFactoryStub();
    private final GenericParamsSet DATA = new GenericParamsSetStub();

    @BeforeEach
    void setUp() {
        _tileFixture = new TileFixtureImpl(FIXTURE_TYPE, PIXEL_OFFSET, new CollectionFactoryStub(),
                TILE_FIXTURE_ITEMS_FACTORY, DATA);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TileFixture.class.getCanonicalName(), _tileFixture.getInterfaceName());
    }

    @Test
    void testFixtureType () {
        assertSame(FIXTURE_TYPE, _tileFixture.fixtureType());
    }

    @Test
    void testPixelOffset() {
        assertSame(PIXEL_OFFSET, _tileFixture.pixelOffset());
    }

    @Test
    void testEvents() {
        assertNotNull(_tileFixture.events());
        assertEquals(GameEvent.class.getCanonicalName(),
                _tileFixture.events().getArchetype().getInterfaceName());
    }

    @Test
    void testTileFixtureItems() {
        assertNotNull(_tileFixture.containedItems());
        assertSame(_tileFixture, ((TileFixtureItemsStub)_tileFixture.containedItems()).TILE_FIXTURE);
    }

    @Test
    void testAssignTileFixtureToTile() {
        Tile tile = new TileStub();
        assertNull(_tileFixture.tile());

        // NB: TileFixture.TILE should NOT be exposed, and calling TileFixture.assignCharacterToTile
        // violates the invariant condition; therefore, TileFixturesStub calls
        // TileFixture.assignCharacterToTile indirectly, as it should be in production code
        tile.fixtures().add(_tileFixture);

        assertSame(tile, _tileFixture.tile());
    }

    @Test
    void testData() {
        assertSame(DATA, _tileFixture.data());
    }

    @Test
    void testDelete() {
        TileFixtureItems containedItems = _tileFixture.containedItems();

        _tileFixture.delete();

        // The test is simply asking the TileFixtureItems to handle deletion of its Items
        assertTrue(_tileFixture.isDeleted());
        assertTrue(containedItems.isDeleted());
    }

    @Test
    void testSetAndGetName() {
        _tileFixture.setName("Name");

        assertEquals("Name", _tileFixture.getName());
    }

    @Test
    void testDeletedInvariant() {
        _tileFixture.delete();

        assertThrows(IllegalStateException.class, () -> _tileFixture.tile());
        assertThrows(IllegalStateException.class, () -> _tileFixture.fixtureType());
        assertThrows(IllegalStateException.class, () -> _tileFixture.pixelOffset());
        assertThrows(IllegalStateException.class, () -> _tileFixture.events());
        assertThrows(IllegalStateException.class, () -> _tileFixture.containedItems());
        assertThrows(IllegalStateException.class, () -> _tileFixture.assignTileFixtureToTileAfterAddingToTileFixtures(null));
        assertThrows(IllegalStateException.class, () -> _tileFixture.data());
        assertThrows(IllegalStateException.class, () -> _tileFixture.delete());
        assertThrows(IllegalStateException.class, () -> _tileFixture.getName());
        assertThrows(IllegalStateException.class, () -> _tileFixture.setName(""));
        assertThrows(IllegalStateException.class, () -> _tileFixture.getInterfaceName());
    }

    @Test
    void testContainingTileInvariant() {
        Tile tile = new TileStub();
        tile.fixtures().add(_tileFixture);
        ((TileFixturesStub) tile.fixtures()).FIXTURES.remove(_tileFixture);

        assertThrows(IllegalStateException.class, () -> _tileFixture.tile());
        assertThrows(IllegalStateException.class, () -> _tileFixture.fixtureType());
        assertThrows(IllegalStateException.class, () -> _tileFixture.pixelOffset());
        assertThrows(IllegalStateException.class, () -> _tileFixture.events());
        assertThrows(IllegalStateException.class, () -> _tileFixture.containedItems());
        assertThrows(IllegalStateException.class, () -> _tileFixture.assignTileFixtureToTileAfterAddingToTileFixtures(null));
        assertThrows(IllegalStateException.class, () -> _tileFixture.data());
        assertThrows(IllegalStateException.class, () -> _tileFixture.delete());
        assertThrows(IllegalStateException.class, () -> _tileFixture.isDeleted());
        assertThrows(IllegalStateException.class, () -> _tileFixture.getName());
        assertThrows(IllegalStateException.class, () -> _tileFixture.setName(""));
        assertThrows(IllegalStateException.class, () -> _tileFixture.getInterfaceName());
    }
}

package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TileFixtureImpl;
import inaugural.soliloquy.gamestate.test.fakes.*;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtureItems;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.gamestate.factories.TileFixtureItemsFactory;
import soliloquy.specs.ruleset.entities.FixtureType;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class TileFixtureImplTests {
    private TileFixture _tileFixture;

    private final EntityUuid ID = new FakeEntityUuid();
    private final FixtureType TYPE = new FakeFixtureType();
    private final CoordinateFactory COORDINATE_FACTORY = new FakeCoordinateFactory();
    private final TileFixtureItemsFactory TILE_FIXTURE_ITEMS_FACTORY =
            new FakeTileFixtureItemsFactory();
    private final VariableCache DATA = new VariableCacheStub();

    @BeforeEach
    void setUp() {
        _tileFixture = new TileFixtureImpl(ID, TYPE, COORDINATE_FACTORY,
                new FakeCollectionFactory(), TILE_FIXTURE_ITEMS_FACTORY, DATA);
    }

    @Test
    void testConstructorWithInvalidParams() {
        // TODO: Test and implement this
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TileFixture.class.getCanonicalName(), _tileFixture.getInterfaceName());
    }

    @Test
    void testId() {
        assertSame(ID, _tileFixture.id());
    }

    @Test
    void testType () {
        assertSame(TYPE, _tileFixture.type());
    }

    @Test
    void testPixelOffset() {
        assertNotNull(_tileFixture.pixelOffset());
    }

    @Test
    void testMovementEvents() {
        assertNotNull(_tileFixture.movementEvents());
    }

    @Test
    void testAbilityEvents() {
        assertNotNull(_tileFixture.abilityEvents());
    }

    @Test
    void testMakeGameEventTarget() {
        GameEventTarget gameEventTarget = _tileFixture.makeGameEventTarget();

        assertNotNull(gameEventTarget);
        assertNull(gameEventTarget.tile());
        assertNotNull(gameEventTarget.tileFixture());
        assertEquals(GameEventTarget.class.getCanonicalName(), gameEventTarget.getInterfaceName());
    }

    @Test
    void testTileFixtureItems() {
        assertNotNull(_tileFixture.items());
        assertSame(_tileFixture, ((FakeTileFixtureItems)_tileFixture.items()).TILE_FIXTURE);
    }

    @Test
    void testAssignTileFixtureToTile() {
        Tile tile = new FakeTile();
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
        Tile tile = new FakeTile();
        tile.fixtures().add(_tileFixture);
        TileFixtureItems containedItems = _tileFixture.items();
        HashMap<TileFixture,Integer> entities =
                ((FakeTileEntities<TileFixture>)tile.fixtures()).ENTITIES;
        int originalNumberOfContainedItems = entities.size();

        _tileFixture.delete();

        // The test is simply asking the TileFixtureItems to handle deletion of its Items
        assertTrue(_tileFixture.isDeleted());
        assertTrue(containedItems.isDeleted());

        assertFalse(tile.fixtures().contains(_tileFixture));
        assertFalse(entities.containsKey(_tileFixture));
        assertEquals(originalNumberOfContainedItems - 1, entities.size());
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
        assertThrows(IllegalStateException.class, () -> _tileFixture.type());
        assertThrows(IllegalStateException.class, () -> _tileFixture.pixelOffset());
        assertThrows(IllegalStateException.class, () -> _tileFixture.movementEvents());
        assertThrows(IllegalStateException.class, () -> _tileFixture.items());
        assertThrows(IllegalStateException.class, () -> _tileFixture.assignTileAfterAddedToTileEntitiesOfType(null));
        assertThrows(IllegalStateException.class, () -> _tileFixture.data());
        assertThrows(IllegalStateException.class, () -> _tileFixture.getName());
        assertThrows(IllegalStateException.class, () -> _tileFixture.setName(""));
    }

    @Test
    void testContainingTileInvariant() {
        Tile tile = new FakeTile();
        tile.fixtures().add(_tileFixture);
        ((FakeTileEntities<TileFixture>)tile.fixtures()).ENTITIES.remove(_tileFixture);

        assertThrows(IllegalStateException.class, () -> _tileFixture.tile());
        assertThrows(IllegalStateException.class, () -> _tileFixture.type());
        assertThrows(IllegalStateException.class, () -> _tileFixture.pixelOffset());
        assertThrows(IllegalStateException.class, () -> _tileFixture.movementEvents());
        assertThrows(IllegalStateException.class, () -> _tileFixture.items());
        assertThrows(IllegalStateException.class, () -> _tileFixture.assignTileAfterAddedToTileEntitiesOfType(null));
        assertThrows(IllegalStateException.class, () -> _tileFixture.data());
        assertThrows(IllegalStateException.class, () -> _tileFixture.delete());
        assertThrows(IllegalStateException.class, () -> _tileFixture.getName());
        assertThrows(IllegalStateException.class, () -> _tileFixture.setName(""));
    }
}

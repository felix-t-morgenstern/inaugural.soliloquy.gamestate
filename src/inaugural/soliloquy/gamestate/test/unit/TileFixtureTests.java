package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TileFixture;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.common.specs.ICoordinate;
import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.common.specs.IMap;
import soliloquy.gamestate.specs.*;
import soliloquy.ruleset.gameentities.abilities.specs.IActiveAbility;
import soliloquy.ruleset.gameentities.abilities.specs.IReactiveAbility;
import soliloquy.ruleset.gameentities.specs.IFixtureType;

import static org.junit.jupiter.api.Assertions.*;

class TileFixtureTests {
    private ITileFixture _tileFixture;

    private final IFixtureType FIXTURE_TYPE = new FixtureTypeStub();
    private final ICoordinate PIXEL_OFFSET = new CoordinateStub();
    private final IMap<String, IActiveAbility> ACTIVE_ABILITIES = new MapStub<>();
    private final IMap<String, IReactiveAbility> REACTIVE_ABILITIES = new MapStub<>();
    private final ITileFixtureItemsFactory TILE_FIXTURE_ITEMS_FACTORY = new TileFixtureItemsFactoryStub();
    private final IGenericParamsSet DATA = new GenericParamsSetStub();

    @BeforeEach
    void setUp() {
        _tileFixture = new TileFixture(FIXTURE_TYPE, PIXEL_OFFSET, ACTIVE_ABILITIES,
                REACTIVE_ABILITIES, TILE_FIXTURE_ITEMS_FACTORY, DATA);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(ITileFixture.class.getCanonicalName(), _tileFixture.getInterfaceName());
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
    void testActiveAbilities() {
        assertSame(ACTIVE_ABILITIES, _tileFixture.activeAbilities());
    }

    @Test
    void testReactiveAbilities() {
        assertSame(REACTIVE_ABILITIES, _tileFixture.reactiveAbilities());
    }

    @Test
    void testTileFixtureItems() {
        assertNotNull(_tileFixture.containedItems());
        assertSame(_tileFixture, ((TileFixtureItemsStub)_tileFixture.containedItems()).TILE_FIXTURE);
    }

    @Test
    void testAssignTileFixtureToTile() {
        ITile tile = new TileStub();
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
        IItem item = new ItemStub();
        ITileFixtureItems containedItems = _tileFixture.containedItems();

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
        assertThrows(IllegalStateException.class, () -> _tileFixture.activeAbilities());
        assertThrows(IllegalStateException.class, () -> _tileFixture.reactiveAbilities());
        assertThrows(IllegalStateException.class, () -> _tileFixture.containedItems());
        assertThrows(IllegalStateException.class, () -> _tileFixture.assignTileFixtureToTile(null));
        assertThrows(IllegalStateException.class, () -> _tileFixture.data());
        assertThrows(IllegalStateException.class, () -> _tileFixture.delete());
        assertThrows(IllegalStateException.class, () -> _tileFixture.getName());
        assertThrows(IllegalStateException.class, () -> _tileFixture.setName(""));
        assertThrows(IllegalStateException.class, () -> _tileFixture.getInterfaceName());
    }

    @Test
    void testContainingTileInvariant() {
        ITile tile = new TileStub();
        tile.fixtures().add(_tileFixture);
        ((TileFixturesStub) tile.fixtures()).FIXTURES.remove(_tileFixture);

        assertThrows(IllegalStateException.class, () -> _tileFixture.tile());
        assertThrows(IllegalStateException.class, () -> _tileFixture.fixtureType());
        assertThrows(IllegalStateException.class, () -> _tileFixture.pixelOffset());
        assertThrows(IllegalStateException.class, () -> _tileFixture.activeAbilities());
        assertThrows(IllegalStateException.class, () -> _tileFixture.reactiveAbilities());
        assertThrows(IllegalStateException.class, () -> _tileFixture.containedItems());
        assertThrows(IllegalStateException.class, () -> _tileFixture.assignTileFixtureToTile(null));
        assertThrows(IllegalStateException.class, () -> _tileFixture.data());
        assertThrows(IllegalStateException.class, () -> _tileFixture.delete());
        assertThrows(IllegalStateException.class, () -> _tileFixture.isDeleted());
        assertThrows(IllegalStateException.class, () -> _tileFixture.getName());
        assertThrows(IllegalStateException.class, () -> _tileFixture.setName(""));
        assertThrows(IllegalStateException.class, () -> _tileFixture.getInterfaceName());
    }
}

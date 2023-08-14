package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.TileFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeGameZone;
import inaugural.soliloquy.gamestate.test.fakes.FakeTileEntitiesFactory;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.TileEntitiesFactory;
import soliloquy.specs.gamestate.factories.TileFactory;

import static org.junit.jupiter.api.Assertions.*;

class TileFactoryImplTests {
    private final GameZone GAME_ZONE = new FakeGameZone();
    private final int X = 123;
    private final int Y = 456;
    private final TileEntitiesFactory TILE_ENTITIES_FACTORY = new FakeTileEntitiesFactory();
    private final VariableCache DATA = new VariableCacheStub();

    private TileFactory tileFactory;

    @BeforeEach
    void setUp() {
        tileFactory = new TileFactoryImpl(TILE_ENTITIES_FACTORY);
        ((FakeGameZone) GAME_ZONE).TILES = new Tile[999][999];
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new TileFactoryImpl(null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TileFactory.class.getCanonicalName(), tileFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        Tile tile = tileFactory.make(X, Y, DATA);

        assertNotNull(tile);
        assertEquals(X, tile.location().x());
        assertEquals(Y, tile.location().y());
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> tileFactory.make(-1, Y, DATA));
        assertThrows(IllegalArgumentException.class, () -> tileFactory.make(X, -1, DATA));
        assertThrows(IllegalArgumentException.class, () -> tileFactory.make(X, Y, null));
    }
}

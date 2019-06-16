package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TileItemsFactory;
import inaugural.soliloquy.gamestate.test.stubs.MapFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.TileStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.IMapFactory;
import soliloquy.specs.gamestate.entities.ITile;
import soliloquy.specs.gamestate.factories.ITileItemsFactory;

import static org.junit.jupiter.api.Assertions.*;

class TileItemsFactoryTests {
    private final ITile TILE = new TileStub();
    private final IMapFactory MAP_FACTORY = new MapFactoryStub();

    private ITileItemsFactory _tileItemsFactory;

    @BeforeEach
    void setUp() {
        _tileItemsFactory = new TileItemsFactory(MAP_FACTORY);
    }

    @Test
    void testConstructorWithNullInput() {
        assertThrows(IllegalArgumentException.class, () -> new TileItemsFactory(null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(ITileItemsFactory.class.getCanonicalName(),
                _tileItemsFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        assertNotNull(_tileItemsFactory.make(TILE));
    }

    @Test
    void testMakeWithNullInput() {
        assertThrows(IllegalArgumentException.class, () -> _tileItemsFactory.make(null));
    }
}

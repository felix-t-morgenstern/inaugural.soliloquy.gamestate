package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TileCharactersFactory;
import inaugural.soliloquy.gamestate.test.stubs.MapFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.TileStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.IMapFactory;
import soliloquy.specs.gamestate.entities.ITile;
import soliloquy.specs.gamestate.factories.ITileCharactersFactory;

import static org.junit.jupiter.api.Assertions.*;

class TileCharactersFactoryTests {
    private final ITile TILE = new TileStub();
    private final IMapFactory MAP_FACTORY = new MapFactoryStub();

    private ITileCharactersFactory _tileCharactersFactory;

    @BeforeEach
    void setUp() {
        _tileCharactersFactory = new TileCharactersFactory(MAP_FACTORY);
    }

    @Test
    void testConstructorWithNullInput() {
        assertThrows(IllegalArgumentException.class, () -> new TileCharactersFactory(null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(ITileCharactersFactory.class.getCanonicalName(),
                _tileCharactersFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        assertNotNull(_tileCharactersFactory.make(TILE));
    }

    @Test
    void testMakeWithNullInput() {
        assertThrows(IllegalArgumentException.class, () -> _tileCharactersFactory.make(null));
    }
}

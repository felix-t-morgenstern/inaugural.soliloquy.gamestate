package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TileCharactersFactoryImpl;
import inaugural.soliloquy.gamestate.test.stubs.MapFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.TileStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.TileCharactersFactory;

import static org.junit.jupiter.api.Assertions.*;

class TileCharactersFactoryImplTests {
    private final Tile TILE = new TileStub();
    private final MapFactory MAP_FACTORY = new MapFactoryStub();

    private TileCharactersFactory _tileCharactersFactory;

    @BeforeEach
    void setUp() {
        _tileCharactersFactory = new TileCharactersFactoryImpl(MAP_FACTORY);
    }

    @Test
    void testConstructorWithNullInput() {
        assertThrows(IllegalArgumentException.class, () -> new TileCharactersFactoryImpl(null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TileCharactersFactory.class.getCanonicalName(),
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

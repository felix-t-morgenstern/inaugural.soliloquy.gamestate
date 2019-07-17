package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TileFixturesFactoryImpl;
import inaugural.soliloquy.gamestate.test.stubs.MapFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.TileStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.TileFixturesFactory;

import static org.junit.jupiter.api.Assertions.*;

class TileFixturesFactoryImplTests {
    private final Tile TILE = new TileStub();
    private final MapFactory MAP_FACTORY = new MapFactoryStub();

    private TileFixturesFactory _tileFixturesFactory;

    @BeforeEach
    void setUp() {
        _tileFixturesFactory = new TileFixturesFactoryImpl(MAP_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithNullInput() {
        assertThrows(IllegalArgumentException.class, () -> new TileFixturesFactoryImpl(null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TileFixturesFactory.class.getCanonicalName(),
                _tileFixturesFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        assertNotNull(_tileFixturesFactory.make(TILE));
    }

    @Test
    void testMakeWithNullInput() {
        assertThrows(IllegalArgumentException.class, () -> _tileFixturesFactory.make(null));
    }
}

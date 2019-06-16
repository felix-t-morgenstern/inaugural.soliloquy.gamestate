package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TileFixturesFactory;
import inaugural.soliloquy.gamestate.test.stubs.MapFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.TileStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.IMapFactory;
import soliloquy.specs.gamestate.entities.ITile;
import soliloquy.specs.gamestate.factories.ITileFixturesFactory;

import static org.junit.jupiter.api.Assertions.*;

class TileFixturesFactoryTests {
    private final ITile TILE = new TileStub();
    private final IMapFactory MAP_FACTORY = new MapFactoryStub();

    private ITileFixturesFactory _tileFixturesFactory;

    @BeforeEach
    void setUp() {
        _tileFixturesFactory = new TileFixturesFactory(MAP_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithNullInput() {
        assertThrows(IllegalArgumentException.class, () -> new TileFixturesFactory(null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(ITileFixturesFactory.class.getCanonicalName(),
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

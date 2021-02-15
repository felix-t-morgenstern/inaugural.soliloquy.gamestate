package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TileFixtureItemsFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeListFactory;
import inaugural.soliloquy.gamestate.test.fakes.FakeTileFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.factories.TileFixtureItemsFactory;

import static org.junit.jupiter.api.Assertions.*;

class TileFixtureItemsFactoryImplTests {
    private final ListFactory LIST_FACTORY = new FakeListFactory();
    private final TileFixture TILE_FIXTURE = new FakeTileFixture();

    private TileFixtureItemsFactory _tileFixtureItemsFactory;

    @BeforeEach
    void setUp() {
        _tileFixtureItemsFactory = new TileFixtureItemsFactoryImpl(LIST_FACTORY);
    }

    @Test
    void testConstructorWithNullInput() {
        assertThrows(IllegalArgumentException.class, () -> new TileFixtureItemsFactoryImpl(null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TileFixtureItemsFactory.class.getCanonicalName(),
                _tileFixtureItemsFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        assertNotNull(_tileFixtureItemsFactory.make(TILE_FIXTURE));
    }

    @Test
    void testMakeWithNullInput() {
        assertThrows(IllegalArgumentException.class, () -> _tileFixtureItemsFactory.make(null));
    }
}

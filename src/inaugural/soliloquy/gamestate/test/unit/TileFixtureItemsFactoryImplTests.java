package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TileFixtureItemsFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeCollectionFactory;
import inaugural.soliloquy.gamestate.test.fakes.FakeTileFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.factories.TileFixtureItemsFactory;

import static org.junit.jupiter.api.Assertions.*;

class TileFixtureItemsFactoryImplTests {
    private final CollectionFactory COLLECTION_FACTORY = new FakeCollectionFactory();
    private final TileFixture TILE_FIXTURE = new FakeTileFixture();

    private TileFixtureItemsFactory _tileFixtureItemsFactory;

    @BeforeEach
    void setUp() {
        _tileFixtureItemsFactory = new TileFixtureItemsFactoryImpl(COLLECTION_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
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

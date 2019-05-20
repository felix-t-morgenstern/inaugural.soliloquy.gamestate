package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TileFixtureItemsFactory;
import inaugural.soliloquy.gamestate.test.stubs.CollectionFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.TileFixtureStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.common.specs.ICollectionFactory;
import soliloquy.gamestate.specs.ITileFixture;
import soliloquy.gamestate.specs.ITileFixtureItemsFactory;

import static org.junit.jupiter.api.Assertions.*;

class TileFixtureItemsFactoryTests {
    private final ICollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    private final ITileFixture TILE_FIXTURE = new TileFixtureStub();

    private ITileFixtureItemsFactory _tileFixtureItemsFactory;

    @BeforeEach
    void setUp() {
        _tileFixtureItemsFactory = new TileFixtureItemsFactory(COLLECTION_FACTORY);
    }

    @Test
    void testConstructorWithNullInput() {
        assertThrows(IllegalArgumentException.class, () -> new TileFixtureItemsFactory(null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(ITileFixtureItemsFactory.class.getCanonicalName(),
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

package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.TileFixtureItemsFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeTileFixture;
import org.junit.Before;
import org.junit.Test;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.factories.TileFixtureItemsFactory;

import static org.junit.Assert.*;

public class TileFixtureItemsFactoryImplTests {
    private final TileFixture TILE_FIXTURE = new FakeTileFixture();

    private TileFixtureItemsFactory tileFixtureItemsFactory;

    @Before
    public void setUp() {
        tileFixtureItemsFactory = new TileFixtureItemsFactoryImpl();
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(TileFixtureItemsFactory.class.getCanonicalName(),
                tileFixtureItemsFactory.getInterfaceName());
    }

    @Test
    public void testMake() {
        assertNotNull(tileFixtureItemsFactory.make(TILE_FIXTURE));
    }

    @Test
    public void testMakeWithNullInput() {
        assertThrows(IllegalArgumentException.class, () -> tileFixtureItemsFactory.make(null));
    }
}

package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.TileFactoryImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.TileEntitiesFactory;
import soliloquy.specs.gamestate.factories.TileFactory;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TileFactoryImplTests {
    @Mock private TileEntitiesFactory mockTileEntitiesFactory;
    @Mock private VariableCache mockData;

    private TileFactory tileFactory;

    @Before
    public void setUp() {
        tileFactory = new TileFactoryImpl(mockTileEntitiesFactory);
    }

    @Test
    public void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new TileFactoryImpl(null));
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(TileFactory.class.getCanonicalName(), tileFactory.getInterfaceName());
    }

    @Test
    public void testMake() {
        Tile tile = tileFactory.make(mockData);

        assertNotNull(tile);
        assertSame(mockData, tile.data());
    }

    @Test
    public void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> tileFactory.make(null));
    }
}

package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.TileEntitiesFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeItem;
import inaugural.soliloquy.gamestate.test.fakes.FakeTile;
import org.junit.Before;
import org.junit.Test;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileEntities;
import soliloquy.specs.gamestate.factories.TileEntitiesFactory;

import static org.junit.Assert.*;

public class TileEntitiesFactoryImplTests {
    private final Tile TILE = new FakeTile();
    private final Item ARCHETYPE = new FakeItem();

    private TileEntitiesFactory _tileEntitiesFactory;

    @Before
    public void setUp() {
        _tileEntitiesFactory = new TileEntitiesFactoryImpl();
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(TileEntitiesFactory.class.getCanonicalName(),
                _tileEntitiesFactory.getInterfaceName());
    }

    @Test
    public void testMake() {
        TileEntities<Item> tileEntities = _tileEntitiesFactory.make(TILE, ARCHETYPE, null, null);

        assertNotNull(tileEntities);
        assertSame(ARCHETYPE, tileEntities.archetype());
    }

    @Test
    public void testMakeWithNullInput() {
        assertThrows(IllegalArgumentException.class,
                () -> _tileEntitiesFactory.make(null, ARCHETYPE, null, null));
        assertThrows(IllegalArgumentException.class,
                () -> _tileEntitiesFactory.make(TILE, null, null, null));
    }
}

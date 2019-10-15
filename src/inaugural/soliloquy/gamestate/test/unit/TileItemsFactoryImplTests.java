package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TileItemsFactoryImpl;
import inaugural.soliloquy.gamestate.test.stubs.ItemStub;
import inaugural.soliloquy.gamestate.test.stubs.MapFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.TileStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.TileItemsFactory;

import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class TileItemsFactoryImplTests {
    private final Tile TILE = new TileStub();
    private final MapFactory MAP_FACTORY = new MapFactoryStub();
    private final Predicate<Item> ITEM_IS_PRESENT_ELSEWHERE = ItemStub::itemIsPresentElsewhere;

    private TileItemsFactory _tileItemsFactory;

    @BeforeEach
    void setUp() {
        _tileItemsFactory = new TileItemsFactoryImpl(MAP_FACTORY, ITEM_IS_PRESENT_ELSEWHERE);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithNullInput() {
        assertThrows(IllegalArgumentException.class, () -> new TileItemsFactoryImpl(null,
                ITEM_IS_PRESENT_ELSEWHERE));
        assertThrows(IllegalArgumentException.class, () -> new TileItemsFactoryImpl(MAP_FACTORY,
                null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TileItemsFactory.class.getCanonicalName(),
                _tileItemsFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        assertNotNull(_tileItemsFactory.make(TILE));
    }

    @Test
    void testMakeWithNullInput() {
        assertThrows(IllegalArgumentException.class, () -> _tileItemsFactory.make(null));
    }
}

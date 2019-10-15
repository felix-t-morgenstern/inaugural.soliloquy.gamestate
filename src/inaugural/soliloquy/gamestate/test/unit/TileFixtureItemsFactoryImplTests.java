package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TileFixtureItemsFactoryImpl;
import inaugural.soliloquy.gamestate.test.stubs.CollectionFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.ItemStub;
import inaugural.soliloquy.gamestate.test.stubs.TileFixtureStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.factories.TileFixtureItemsFactory;

import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class TileFixtureItemsFactoryImplTests {
    private final CollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    private final TileFixture TILE_FIXTURE = new TileFixtureStub();
    private final Predicate<Item> ITEM_IS_PRESENT_ELSEWHERE = ItemStub::itemIsPresentElsewhere;

    private TileFixtureItemsFactory _tileFixtureItemsFactory;

    @BeforeEach
    void setUp() {
        _tileFixtureItemsFactory = new TileFixtureItemsFactoryImpl(COLLECTION_FACTORY,
                ITEM_IS_PRESENT_ELSEWHERE);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithNullInput() {
        assertThrows(IllegalArgumentException.class, () -> new TileFixtureItemsFactoryImpl(null,
                ITEM_IS_PRESENT_ELSEWHERE));
        assertThrows(IllegalArgumentException.class, () -> new TileFixtureItemsFactoryImpl(
                COLLECTION_FACTORY, null));
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

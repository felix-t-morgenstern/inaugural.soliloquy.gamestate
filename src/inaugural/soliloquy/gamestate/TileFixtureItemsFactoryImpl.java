package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtureItems;
import soliloquy.specs.gamestate.factories.TileFixtureItemsFactory;

import java.util.function.Predicate;

public class TileFixtureItemsFactoryImpl implements TileFixtureItemsFactory {
    private final CollectionFactory COLLECTION_FACTORY;
    private final Predicate<Item> ITEM_IS_PRESENT_ELSEWHERE;

    @SuppressWarnings("ConstantConditions")
    public TileFixtureItemsFactoryImpl(CollectionFactory collectionFactory,
                                       Predicate<Item> itemIsPresentElsewhere) {
        if (collectionFactory == null) {
            throw new IllegalArgumentException(
                    "TileFixtureItemsFactory: collectionFactory must be non-null");
        }
        COLLECTION_FACTORY = collectionFactory;
        if (itemIsPresentElsewhere == null) {
            throw new IllegalArgumentException(
                    "TileFixtureItemsFactory: itemIsPresentElsewhere must be non-null");
        }
        ITEM_IS_PRESENT_ELSEWHERE = itemIsPresentElsewhere;
    }

    @Override
    public TileFixtureItems make(TileFixture tileFixture) {
        if (tileFixture == null) {
            throw new IllegalArgumentException(
                    "TileFixtureItemsFactory.make: tileFixture must be non-null");
        }
        return new TileFixtureItemsImpl(tileFixture, COLLECTION_FACTORY,
                ITEM_IS_PRESENT_ELSEWHERE);
    }

    @Override
    public String getInterfaceName() {
        return TileFixtureItemsFactory.class.getCanonicalName();
    }
}

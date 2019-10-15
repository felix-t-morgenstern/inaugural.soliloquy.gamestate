package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileItems;
import soliloquy.specs.gamestate.factories.TileItemsFactory;

import java.util.function.Predicate;

public class TileItemsFactoryImpl implements TileItemsFactory {
    private final MapFactory MAP_FACTORY;
    private final Predicate<Item> ITEM_IS_PRESENT_ELSEWHERE;

    @SuppressWarnings("ConstantConditions")
    public TileItemsFactoryImpl(MapFactory mapFactory, Predicate<Item> itemIsPresentElsewhere) {
        if(mapFactory == null) {
            throw new IllegalArgumentException("TileItemsFactory: mapFactory must be non-null");
        }
        MAP_FACTORY = mapFactory;
        if(itemIsPresentElsewhere == null) {
            throw new IllegalArgumentException("TileItemsFactory: itemIsPresentElsewhere must " +
                    "be non-null");
        }
        ITEM_IS_PRESENT_ELSEWHERE = itemIsPresentElsewhere;
    }

    @Override
    public TileItems make(Tile tile) throws IllegalArgumentException {
        if(tile == null) {
            throw new IllegalArgumentException("TileItemsFactory.make: tile must be non-null");
        }
        return new TileItemsImpl(tile, MAP_FACTORY, ITEM_IS_PRESENT_ELSEWHERE);
    }

    @Override
    public String getInterfaceName() {
        return TileItemsFactory.class.getCanonicalName();
    }
}

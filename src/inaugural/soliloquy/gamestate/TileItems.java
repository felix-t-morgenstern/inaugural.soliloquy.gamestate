package inaugural.soliloquy.gamestate;

import soliloquy.common.specs.IMap;
import soliloquy.common.specs.IMapFactory;
import soliloquy.gamestate.specs.IItem;
import soliloquy.gamestate.specs.ITile;
import soliloquy.gamestate.specs.ITileItems;

public class TileItems implements ITileItems {
    private final ITile TILE;
    private final IMapFactory MAP_FACTORY;

    public TileItems(ITile tile, IMapFactory mapFactory) {
        TILE = tile;
        MAP_FACTORY = mapFactory;
    }

    @Override
    public IMap<IItem, Integer> getItemsRepresentation() throws IllegalStateException {
        return null;
    }

    @Override
    public void addItem(IItem iItem) throws IllegalArgumentException {

    }

    @Override
    public void addItem(IItem iItem, int i) throws IllegalArgumentException {

    }

    @Override
    public boolean removeItem(IItem iItem) throws IllegalArgumentException {
        return false;
    }

    @Override
    public boolean containsItem(IItem iItem) throws IllegalArgumentException {
        return false;
    }

    @Override
    public void delete() throws IllegalStateException {

    }

    @Override
    public boolean isDeleted() {
        return false;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

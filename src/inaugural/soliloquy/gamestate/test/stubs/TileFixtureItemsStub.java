package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.common.specs.ICollection;
import soliloquy.gamestate.specs.IItem;
import soliloquy.gamestate.specs.ITileFixture;
import soliloquy.gamestate.specs.ITileFixtureItems;

public class TileFixtureItemsStub implements ITileFixtureItems {
    public final ITileFixture TILE_FIXTURE;

    private boolean _deleted;

    public TileFixtureItemsStub(ITileFixture tileFixture) {
        TILE_FIXTURE = tileFixture;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public void delete() throws IllegalStateException {
        _deleted = true;
    }

    @Override
    public boolean isDeleted() {
        return _deleted;
    }

    @Override
    public ICollection<IItem> getRepresentation() throws UnsupportedOperationException, IllegalStateException {
        return null;
    }

    @Override
    public void add(IItem iItem) throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public boolean remove(IItem iItem) throws IllegalArgumentException, IllegalStateException {
        return false;
    }

    @Override
    public boolean contains(IItem iItem) throws IllegalArgumentException, IllegalStateException {
        return false;
    }
}
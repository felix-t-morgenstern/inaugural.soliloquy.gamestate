package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileCharacters;
import soliloquy.specs.gamestate.factories.TileCharactersFactory;

public class TileCharactersFactoryStub implements TileCharactersFactory {
    @Override
    public TileCharacters make(Tile tile) throws IllegalArgumentException {
        return new TileCharactersStub(tile);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

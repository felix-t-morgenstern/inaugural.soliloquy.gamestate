package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.gamestate.specs.IGameZone;
import soliloquy.gamestate.specs.IGameZonesRepo;

public class GameZonesRepoStub implements IGameZonesRepo {
    @Override
    public IGameZone getGameZone(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.IGameZone;
import soliloquy.specs.gamestate.valueobjects.IGameZonesRepo;

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

package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.valueobjects.GameZonesRepo;

public class GameZonesRepoStub implements GameZonesRepo {
    @Override
    public GameZone getGameZone(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

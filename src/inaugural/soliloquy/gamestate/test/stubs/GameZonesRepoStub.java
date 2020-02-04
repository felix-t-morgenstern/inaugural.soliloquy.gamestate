package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.GameZonesRepo;

import java.util.HashMap;

public class GameZonesRepoStub implements GameZonesRepo {
    public HashMap<String, GameZone> REPO = new HashMap<>();

    @Override
    public GameZone getGameZone(String s) throws IllegalArgumentException {
        return REPO.get(s);
    }

    @Override
    public void saveGameZone(GameZone gameZone) throws IllegalArgumentException {

    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

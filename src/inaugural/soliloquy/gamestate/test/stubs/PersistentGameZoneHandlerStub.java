package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;
import soliloquy.specs.gamestate.entities.GameZone;

public class PersistentGameZoneHandlerStub implements PersistentValueTypeHandler<GameZone> {
    public String ReadData;
    public GameZone WrittenGameZone;

    public final static String WRITTEN_DATA = "This is the written GameZone";

    public static final GameZone GAME_ZONE = new GameZoneStub();

    @Override
    public GameZone read(String data) throws IllegalArgumentException {
        ReadData = data;
        return GAME_ZONE;
    }

    @Override
    public String write(GameZone gameZone) {
        WrittenGameZone = gameZone;
        return WRITTEN_DATA;
    }

    @Override
    public GameZone getArchetype() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

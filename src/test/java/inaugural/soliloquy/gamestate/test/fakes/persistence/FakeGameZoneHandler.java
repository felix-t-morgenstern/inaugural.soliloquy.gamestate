package inaugural.soliloquy.gamestate.test.fakes.persistence;

import org.mockito.Mockito;
import soliloquy.specs.gamestate.entities.GameZone;

public class FakeGameZoneHandler extends FakeTypeHandler<GameZone> {
    @Override
    public String typeName() {
        return "GameZone";
    }

    @Override
    protected GameZone generateInstance() {
        return Mockito.mock(GameZone.class);
    }
}

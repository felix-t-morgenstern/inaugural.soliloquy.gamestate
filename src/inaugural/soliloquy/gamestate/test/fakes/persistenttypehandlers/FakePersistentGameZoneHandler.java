package inaugural.soliloquy.gamestate.test.fakes.persistenttypehandlers;

import inaugural.soliloquy.gamestate.test.fakes.FakeGameZone;
import soliloquy.specs.gamestate.entities.GameZone;

public class FakePersistentGameZoneHandler extends FakePersistentValueTypeHandler<GameZone> {
    @Override
    public String typeName() {
        return "GameZone";
    }

    @Override
    protected GameZone generateInstance() {
        return new FakeGameZone();
    }
}

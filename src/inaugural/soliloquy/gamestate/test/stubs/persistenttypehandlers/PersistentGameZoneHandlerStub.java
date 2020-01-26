package inaugural.soliloquy.gamestate.test.stubs.persistenttypehandlers;

import inaugural.soliloquy.gamestate.test.stubs.GameZoneStub;
import soliloquy.specs.gamestate.entities.GameZone;

public class PersistentGameZoneHandlerStub extends PersistentValueTypeHandlerStub<GameZone> {
    @Override
    public String typeName() {
        return "GameZone";
    }

    @Override
    protected GameZone generateInstance() {
        return new GameZoneStub();
    }
}

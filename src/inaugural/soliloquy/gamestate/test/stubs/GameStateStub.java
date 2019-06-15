package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.common.specs.IMap;
import soliloquy.common.specs.IPersistentVariableCache;
import soliloquy.gamestate.specs.*;
import soliloquy.ruleset.gameentities.specs.ICharacterAIType;
import soliloquy.ruleset.primary.specs.IRuleset;

public class GameStateStub implements IGameState {
    @Override
    public IParty party() throws IllegalStateException {
        return null;
    }

    @Override
    public IPersistentVariableCache persistentVariables() {
        return null;
    }

    @Override
    public IMap<String, ICharacterAIType> characterAIs() {
        return null;
    }

    @Override
    public IGameZonesRepo gameZonesRepo() {
        return null;
    }

    @Override
    public IGameZone getCurrentGameZone() {
        return new GameZoneStub();
    }

    @Override
    public void setCurrentGameZone(IGameZone iGameZone) {

    }

    @Override
    public IRoundManager roundManager() {
        return null;
    }

    @Override
    public IMap<Integer, IKeyBindingContext> keyBindingContexts() throws IllegalStateException {
        return null;
    }

    @Override
    public IRuleset ruleset() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

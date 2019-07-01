package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.IMap;
import soliloquy.specs.common.infrastructure.IPersistentVariableCache;
import soliloquy.specs.gamestate.entities.IGameZone;
import soliloquy.specs.gamestate.entities.IKeyBindingContext;
import soliloquy.specs.gamestate.entities.IParty;
import soliloquy.specs.gamestate.entities.IRoundManager;
import soliloquy.specs.gamestate.valueobjects.IGameState;
import soliloquy.specs.gamestate.valueobjects.IGameZonesRepo;
import soliloquy.specs.ruleset.IRuleset;
import soliloquy.specs.ruleset.entities.ICharacterAIType;

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

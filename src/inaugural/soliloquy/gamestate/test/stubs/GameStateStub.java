package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.KeyBindingContext;
import soliloquy.specs.gamestate.entities.Party;
import soliloquy.specs.gamestate.entities.RoundManager;
import soliloquy.specs.gamestate.valueobjects.GameState;
import soliloquy.specs.gamestate.valueobjects.GameZonesRepo;
import soliloquy.specs.ruleset.Ruleset;
import soliloquy.specs.ruleset.entities.CharacterAIType;

public class GameStateStub implements GameState {
    @Override
    public Party party() throws IllegalStateException {
        return null;
    }

    @Override
    public VariableCache variableCache() {
        return null;
    }

    @Override
    public Map<String, CharacterAIType> characterAIs() {
        return null;
    }

    @Override
    public GameZonesRepo gameZonesRepo() {
        return null;
    }

    @Override
    public GameZone getCurrentGameZone() {
        return new GameZoneStub();
    }

    @Override
    public void setCurrentGameZone(GameZone gameZone) {

    }

    @Override
    public RoundManager roundManager() {
        return null;
    }

    @Override
    public Map<Integer, KeyBindingContext> keyBindingContexts() throws IllegalStateException {
        return null;
    }

    @Override
    public Ruleset ruleset() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

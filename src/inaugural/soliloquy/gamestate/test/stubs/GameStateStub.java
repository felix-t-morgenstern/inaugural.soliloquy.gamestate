package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.gamestate.factories.*;
import soliloquy.specs.ruleset.entities.CharacterAIType;

public class GameStateStub implements GameState {
    public final Party Party;
    public final VariableCache Data;
    public final RoundManagerStub RoundManager;

    public GameZone GameZone;

    public GameStateStub(Party party, VariableCache data) {
        Party = party;
        Data = data;
        RoundManager = new RoundManagerStub();
    }

    @Override
    public Party party() throws IllegalStateException {
        return Party;
    }

    @Override
    public VariableCache variableCache() {
        return Data;
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
        return GameZone;
    }

    @Override
    public void setCurrentGameZone(GameZone gameZone) {
        GameZone = gameZone;
    }

    @Override
    public Camera camera() {
        return null;
    }

    @Override
    public Registry<GameMovementEvent> movementEvents() {
        return null;
    }

    @Override
    public Registry<GameAbilityEvent> abilityEvents() {
        return null;
    }

    @Override
    public RoundManager roundManager() {
        return RoundManager;
    }

    @Override
    public Map<Integer, KeyBindingContext> keyBindingContexts() throws IllegalStateException {
        return null;
    }

    @Override
    public ItemFactory itemFactory() {
        return null;
    }

    @Override
    public CharacterFactory characterFactory() {
        return null;
    }

    @Override
    public TimerFactory timerFactory() {
        return null;
    }

    @Override
    public KeyBindingFactory keyBindingFactory() {
        return null;
    }

    @Override
    public KeyBindingContextFactory keyBindingContextFactory() {
        return null;
    }

    @Override
    public KeyPressListenerFactory keyPressListenerFactory() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.GameState;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.gamestate.factories.*;
import soliloquy.specs.ruleset.entities.CharacterAIType;

public class GameStateArchetype implements GameState {
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
        return null;
    }

    @Override
    public void setCurrentGameZone(GameZone gameZone) {

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
        return null;
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
    public TurnBasedTimerFactory turnBasedTimerFactory() {
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
    public KeyEventListener keyEventListener() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return GameState.class.getCanonicalName();
    }
}

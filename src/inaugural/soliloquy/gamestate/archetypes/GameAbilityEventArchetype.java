package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.game.Game;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.logger.Logger;
import soliloquy.specs.ruleset.entities.abilities.AbilitySource;
import soliloquy.specs.ruleset.entities.abilities.AbilityType;

public class GameAbilityEventArchetype implements GameAbilityEvent {
    @Override
    public void fire(AbilitySource abilitySource, AbilityType abilityType, GameEventTarget gameEventTarget) throws IllegalArgumentException {

    }

    @Override
    public Game game() {
        return null;
    }

    @Override
    public Logger logger() {
        return null;
    }

    @Override
    public String id() throws IllegalStateException {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return GameAbilityEvent.class.getCanonicalName();
    }
}

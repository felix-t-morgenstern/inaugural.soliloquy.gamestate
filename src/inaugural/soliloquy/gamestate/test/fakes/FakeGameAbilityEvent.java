package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.game.Game;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.logger.Logger;
import soliloquy.specs.ruleset.entities.abilities.AbilitySource;
import soliloquy.specs.ruleset.entities.abilities.AbilityType;

public class FakeGameAbilityEvent implements GameAbilityEvent {
    private String _id;

    public FakeGameAbilityEvent(String id) {
        _id = id;
    }

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
        return _id;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

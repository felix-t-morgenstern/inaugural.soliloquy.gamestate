package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.gamestate.entities.abilities.AbilitySource;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.ruleset.entities.abilities.Ability;

public class FakeGameAbilityEvent implements GameAbilityEvent {
    private String _id;

    public FakeGameAbilityEvent(String id) {
        _id = id;
    }

    @Override
    public String id() throws IllegalStateException {
        return _id;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public void fire(AbilitySource abilitySource, Ability ability, GameEventTarget gameEventTarget)
            throws IllegalArgumentException {

    }
}

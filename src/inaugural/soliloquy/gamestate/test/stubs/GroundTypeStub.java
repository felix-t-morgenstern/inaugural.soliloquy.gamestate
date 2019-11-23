package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.game.Game;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.logger.Logger;
import soliloquy.specs.ruleset.entities.GroundType;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbility;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbility;
import soliloquy.specs.sprites.entities.Sprite;

public class GroundTypeStub implements GroundType {
    @Override
    public String id() throws IllegalStateException {
        return null;
    }

    @Override
    public Sprite sprite() {
        return null;
    }

    @Override
    public boolean onStep(Character character) {
        return false;
    }

    @Override
    public boolean canStep(Character character) {
        return false;
    }

    @Override
    public Map<String, ActiveAbility> activeAbilities() throws IllegalStateException {
        return null;
    }

    @Override
    public Map<String, ReactiveAbility> reactiveAbilities() throws IllegalStateException {
        return null;
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
    public String getInterfaceName() {
        return null;
    }
}
package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.game.Game;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.graphics.assets.ImageAsset;
import soliloquy.specs.graphics.renderables.colorshifting.ColorShift;
import soliloquy.specs.logger.Logger;
import soliloquy.specs.ruleset.entities.GroundType;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbility;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbility;

public class FakeGroundType implements GroundType {
    private String _id;

    public FakeGroundType() {

    }

    public FakeGroundType(String id) {
        _id = id;
    }

    @Override
    public String id() throws IllegalStateException {
        return _id;
    }

    @Override
    public ImageAsset imageAsset() {
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

    @Override
    public List<ColorShift> defaultColorShifts() {
        return null;
    }
}

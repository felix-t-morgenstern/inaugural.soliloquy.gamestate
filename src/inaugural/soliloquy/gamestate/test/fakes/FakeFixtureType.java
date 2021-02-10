package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.game.Game;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.graphics.assets.Sprite;
import soliloquy.specs.graphics.colorshifting.ColorShiftType;
import soliloquy.specs.logger.Logger;
import soliloquy.specs.ruleset.entities.FixtureType;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbility;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbility;

public class FakeFixtureType implements FixtureType {
    public String _id;

    public final static float DEFAULT_X_TILE_WIDTH_OFFSET = 0.111f;
    public final static float DEFAULT_Y_TILE_HEIGHT_OFFSET = 0.222f;

    public FakeFixtureType() {

    }

    public FakeFixtureType(String id) {
        _id = id;
    }

    @Override
    public boolean isContainer() throws IllegalStateException {
        return false;
    }

    @Override
    public String id() throws IllegalStateException {
        return _id;
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

    @Override
    public Collection<ColorShiftType> defaultColorShifts() {
        return null;
    }

    @Override
    public float defaultXTileWidthOffset() {
        return DEFAULT_X_TILE_WIDTH_OFFSET;
    }

    @Override
    public float defaultYTileHeightOffset() {
        return DEFAULT_Y_TILE_HEIGHT_OFFSET;
    }
}

package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.game.Game;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.graphics.assets.ImageAsset;
import soliloquy.specs.graphics.renderables.colorshifting.ColorShift;
import soliloquy.specs.logger.Logger;
import soliloquy.specs.ruleset.entities.FixtureType;

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
    public float defaultXTileWidthOffset() {
        return DEFAULT_X_TILE_WIDTH_OFFSET;
    }

    @Override
    public float defaultYTileHeightOffset() {
        return DEFAULT_Y_TILE_HEIGHT_OFFSET;
    }

    @Override
    public List<ColorShift> defaultColorShifts() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String s) {

    }
}

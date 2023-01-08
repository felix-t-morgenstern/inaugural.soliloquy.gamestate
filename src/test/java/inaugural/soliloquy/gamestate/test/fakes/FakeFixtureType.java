package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.shared.Direction;
import soliloquy.specs.common.valueobjects.Vertex;
import soliloquy.specs.game.Game;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.graphics.assets.ImageAsset;
import soliloquy.specs.graphics.renderables.colorshifting.ColorShift;
import soliloquy.specs.logger.Logger;
import soliloquy.specs.ruleset.entities.FixtureType;

public class FakeFixtureType implements FixtureType {
    public String id;

    public final static float DEFAULT_X_TILE_WIDTH_OFFSET = 0.111f;
    public final static float DEFAULT_Y_TILE_HEIGHT_OFFSET = 0.222f;

    public FakeFixtureType() {

    }

    public FakeFixtureType(String id) {
        this.id = id;
    }

    @Override
    public boolean isContainer() throws IllegalStateException {
        return false;
    }

    @Override
    public String id() throws IllegalStateException {
        return id;
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
    public int additionalMovementCost() {
        return 0;
    }

    @Override
    public int heightMovementPenaltyMitigation(Tile tile, Character character,
                                               Direction direction) {
        return 0;
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

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String s) {

    }

    @Override
    public Vertex defaultTileOffset() {
        return Vertex.of(DEFAULT_X_TILE_WIDTH_OFFSET, DEFAULT_Y_TILE_HEIGHT_OFFSET);
    }
}

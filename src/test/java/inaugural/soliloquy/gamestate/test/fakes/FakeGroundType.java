package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.shared.Direction;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.graphics.assets.ImageAsset;
import soliloquy.specs.graphics.renderables.colorshifting.ColorShift;
import soliloquy.specs.ruleset.entities.GroundType;

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
    public int additionalMovementCost() {
        return 0;
    }

    @Override
    public int heightMovementPenaltyMitigation(Tile tile, Character character,
                                               Direction direction) {
        return 0;
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
}

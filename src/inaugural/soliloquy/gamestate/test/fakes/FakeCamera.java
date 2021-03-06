package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.gamestate.entities.Camera;
import soliloquy.specs.gamestate.entities.Character;

public class FakeCamera implements Camera {
    @Override
    public Coordinate getTileLocation() {
        return null;
    }

    @Override
    public void setTileLocation(int i, int i1) throws IllegalArgumentException {

    }

    @Override
    public float getXTileWidthOffset() {
        return 0;
    }

    @Override
    public float getYTileHeightOffset() {
        return 0;
    }

    @Override
    public void setXTileWidthOffset(float v) {

    }

    @Override
    public void setYTileHeightOffset(float v) {

    }

    @Override
    public int getTileRenderingRadius() {
        return 0;
    }

    @Override
    public void setTileRenderingRadius(int i) throws IllegalArgumentException {

    }

    @Override
    public boolean getAllTilesVisible() {
        return false;
    }

    @Override
    public void setAllTilesVisible(boolean b) {

    }

    @Override
    public Map<Character, Integer> charactersProvidingVisibility() {
        return null;
    }

    @Override
    public Map<Coordinate, Integer> coordinatesProvidingVisibility() {
        return null;
    }

    @Override
    public void calculateVisibleTiles() throws IllegalStateException {

    }

    @Override
    public List<Coordinate> visibleTiles() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

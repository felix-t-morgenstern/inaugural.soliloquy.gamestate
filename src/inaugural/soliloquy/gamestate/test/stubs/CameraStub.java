package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;
import soliloquy.specs.gamestate.entities.Camera;
import soliloquy.specs.gamestate.entities.Character;

public class CameraStub implements Camera {
    @Override
    public Coordinate getTileLocation() {
        return null;
    }

    @Override
    public void setTileLocation(int i, int i1) throws IllegalArgumentException {

    }

    @Override
    public ReadableCoordinate getPixelOffset() {
        return null;
    }

    @Override
    public void setPixelOffset(int i, int i1) throws IllegalArgumentException {

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
    public Collection<ReadableCoordinate> visibleTiles() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

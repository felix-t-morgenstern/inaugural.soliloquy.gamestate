package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.valueobjects.ICoordinate;

public class CoordinateArchetype implements ICoordinate {
    @Override
    public int getX() {
        return 0;
    }

    @Override
    public void setX(int i) {

    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public void setY(int i) {

    }

    @Override
    public int compareTo(ICoordinate o) {
        return 0;
    }

    @Override
    public ICoordinate makeClone() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return "soliloquy.common.specs.ICoordinate";
    }
}

package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.valueobjects.Coordinate;

public class CoordinateArchetype implements Coordinate {
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
    public int compareTo(Coordinate o) {
        return 0;
    }

    @Override
    public Coordinate makeClone() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return "soliloquy.common.specs.ICoordinate";
    }
}

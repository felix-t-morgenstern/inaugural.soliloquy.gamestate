package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;

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
    public ReadableCoordinate readOnlyRepresentation() {
        return null;
    }

    @Override
    public Coordinate makeClone() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return Coordinate.class.getCanonicalName();
    }

    @Override
    public int compareTo(ReadableCoordinate o) {
        return 0;
    }
}

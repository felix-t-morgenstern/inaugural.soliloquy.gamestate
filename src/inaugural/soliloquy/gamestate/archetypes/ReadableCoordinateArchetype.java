package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;

public class ReadableCoordinateArchetype implements ReadableCoordinate {
    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public int compareTo(ReadableCoordinate readableCoordinate) {
        return 0;
    }

    @Override
    public Coordinate makeClone() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return ReadableCoordinate.class.getCanonicalName();
    }
}

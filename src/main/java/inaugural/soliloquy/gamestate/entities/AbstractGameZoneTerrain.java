package inaugural.soliloquy.gamestate.entities;

import soliloquy.specs.common.valueobjects.Coordinate3d;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.shared.GameZoneTerrain;

public abstract class AbstractGameZoneTerrain extends AbstractGameEventTargetEntity
        implements GameZoneTerrain {
    protected GameZone gameZone;
    protected Coordinate3d location;

    @Override
    public GameZone gameZone() throws IllegalStateException {
        enforceInvariants("gameZone");
        return gameZone;
    }

    @Override
    public Coordinate3d location() throws IllegalStateException {
        enforceInvariants("location");
        return location;
    }

    @Override
    public void assignGameZoneAfterAddedToGameZone(GameZone gameZone, Coordinate3d location)
            throws IllegalArgumentException, IllegalStateException {
        if (gameZone != null && location == null) {
            throw new IllegalArgumentException(
                    "AbstractGameZoneTerrain.assignGameZoneAfterAddedToGameZone: If gameZone is " +
                            "non-null, location must be specified");
        }
        this.gameZone = gameZone;
        this.location = location;
    }
}

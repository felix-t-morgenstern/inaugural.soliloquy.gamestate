package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.valueobjects.Coordinate3d;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.ruleset.entities.WallSegmentType;

import java.util.Map;

public class WallSegmentImpl extends AbstractGameZoneTerrain implements WallSegment {
    private final Map<String, Object> DATA;

    private WallSegmentType type;

    public WallSegmentImpl(Map<String, Object> data) {
        DATA = Check.ifNull(data, "data");
    }

    @Override
    public WallSegmentType getType() throws IllegalStateException, EntityDeletedException {
        enforceDeletionInvariants();
        return type;
    }

    @Override
    public void setType(WallSegmentType wallSegmentType)
            throws IllegalArgumentException, IllegalStateException, EntityDeletedException {
        enforceDeletionInvariants();
        type = Check.ifNull(wallSegmentType, "wallSegmentType");
    }

    @Override
    public Map<String, Object> data() throws IllegalStateException {
        enforceDeletionInvariants();
        return DATA;
    }

    @Override
    public GameEventTarget makeGameEventTarget() throws IllegalStateException {
        enforceInvariants("makeGameEventTarget");
        var wallSegment = this;
        return new GameEventTarget() {
            @Override
            public Tile tile() {
                return null;
            }

            @Override
            public TileFixture tileFixture() {
                return null;
            }

            @Override
            public WallSegment tileWallSegment() {
                return wallSegment;
            }
        };
    }

    @Override
    void enforceInvariants(String methodName) {
        enforceDeletionInvariants();
    }

    @Override
    protected String containingClassName() {
        return null;
    }

    @Override
    protected Deletable getContainingObject() {
        return null;
    }

    @Override
    public GameZone gameZone() throws IllegalStateException {
        return null;
    }

    @Override
    public Coordinate3d location() throws IllegalStateException {
        return null;
    }

    @Override
    public void assignGameZoneAfterAddedToGameZone(GameZone gameZone, Coordinate3d coordinate3d)
            throws IllegalArgumentException, IllegalStateException {

    }
}

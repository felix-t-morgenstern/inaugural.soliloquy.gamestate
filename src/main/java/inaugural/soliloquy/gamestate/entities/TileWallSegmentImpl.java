package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.ruleset.entities.WallSegmentType;

public class TileWallSegmentImpl extends AbstractGameEventTargetEntity implements TileWallSegment {
    private final VariableCache DATA;

    private WallSegmentType type;
    private String name;
    private Tile tile;

    public TileWallSegmentImpl(VariableCache data) {
        DATA = Check.ifNull(data, "data");
    }

    @Override
    public WallSegmentType getType() throws IllegalStateException, EntityDeletedException {
        enforceDeletionInvariants();
        enforceAggregateAssignmentInvariant("getType");
        return type;
    }

    @Override
    public void setType(WallSegmentType wallSegmentType)
            throws IllegalArgumentException, IllegalStateException, EntityDeletedException {
        enforceDeletionInvariants();
        enforceAggregateAssignmentInvariant("setType");
        type = Check.ifNull(wallSegmentType, "wallSegmentType");
    }

    @Override
    public Tile tile() {
        enforceDeletionInvariants();
        enforceAggregateAssignmentInvariant("tile");
        return tile;
    }

    @Override
    public void assignTileAfterAddedToTileEntitiesOfType(Tile tile)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants();
        enforceAggregateAssignmentInvariant(
                "assignTileAfterAddedToTileEntitiesOfType");
        this.tile = tile;
        enforceAggregateAssignmentInvariant("assignTileAfterAddedToTileEntitiesOfType");
    }

    @Override
    public VariableCache data() throws IllegalStateException {
        enforceDeletionInvariants();
        enforceAggregateAssignmentInvariant("data");
        return DATA;
    }

    @Override
    public String getName() {
        enforceDeletionInvariants();
        enforceAggregateAssignmentInvariant("getName");
        return name;
    }

    @Override
    public void setName(String name) {
        enforceDeletionInvariants();
        enforceAggregateAssignmentInvariant("setName");
        this.name = name;
    }

    @Override
    public GameEventTarget makeGameEventTarget() throws IllegalStateException {
        enforceInvariants("makeGameEventTarget");
        var tileWallSegment = this;
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
            public TileWallSegment tileWallSegment() {
                return tileWallSegment;
            }

            @Override
            public String getInterfaceName() {
                return GameEventTarget.class.getCanonicalName();
            }
        };
    }

    @Override
    public String getInterfaceName() {
        return TileWallSegment.class.getCanonicalName();
    }

    private void enforceAggregateAssignmentInvariant(String methodName) {
        if (tile != null) {
            var tileWallSegmentDirection = tile.wallSegments().getDirection(this);
            if (tileWallSegmentDirection == null) {
                throw new IllegalStateException("TileWallSegmentImpl." + methodName +
                        ": This TileWallSegment not found in Tile to which it was assigned");
            }
        }
    }

    @Override
    void enforceInvariants(String methodName) {
        enforceDeletionInvariants();
        enforceAggregateAssignmentInvariant(methodName);
    }

    @Override
    protected String containingClassName() {
        return "Tile";
    }

    @Override
    protected Deletable getContainingObject() {
        return tile;
    }
}

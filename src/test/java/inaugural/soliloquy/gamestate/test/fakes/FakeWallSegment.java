package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.WallSegment;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.ruleset.entities.WallSegmentType;

public class FakeWallSegment implements WallSegment {
    private WallSegmentType type;
    private boolean isDeleted;
    private VariableCache data;

    public Tile _tile;

    public FakeWallSegment() {

    }

    FakeWallSegment(VariableCache data) {
        this.data = data;
    }

    @Override
    public WallSegmentType getType() throws IllegalStateException {
        return type;
    }

    @Override
    public void setType(WallSegmentType type) throws IllegalStateException {
        this.type = type;
    }

    @Override
    public VariableCache data() throws IllegalStateException {
        return data;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String s) {

    }

    @Override
    public void delete() throws IllegalStateException {
        isDeleted = true;
    }

    @Override
    public boolean isDeleted() {
        return isDeleted;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public List<GameMovementEvent> movementEvents() throws IllegalStateException {
        return null;
    }

    @Override
    public List<GameAbilityEvent> abilityEvents() throws IllegalStateException {
        return null;
    }

    @Override
    public GameEventTarget makeGameEventTarget() throws IllegalStateException {
        return null;
    }
}

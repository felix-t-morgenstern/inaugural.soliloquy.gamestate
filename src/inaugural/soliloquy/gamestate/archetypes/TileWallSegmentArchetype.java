package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileWallSegment;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.ruleset.entities.WallSegmentType;

public class TileWallSegmentArchetype implements TileWallSegment {
    @Override
    public WallSegmentType getType() throws IllegalStateException {
        return null;
    }

    @Override
    public void setType(WallSegmentType iWallSegmentType) throws IllegalStateException {

    }

    @Override
    public VariableCache data() throws IllegalStateException {
        return null;
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

    }

    @Override
    public boolean isDeleted() {
        return false;
    }

    @Override
    public String getInterfaceName() {
        return TileWallSegment.class.getCanonicalName();
    }

    @Override
    public Tile tile() throws IllegalStateException {
        return null;
    }

    @Override
    public void assignTileAfterAddedToTileEntitiesOfType(Tile tile) throws IllegalArgumentException, IllegalStateException {

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

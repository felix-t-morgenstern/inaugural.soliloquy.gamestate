package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtureItems;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.ruleset.entities.FixtureType;

public class TileFixtureArchetype implements TileFixture {
    @Override
    public Tile tile() throws IllegalStateException {
        return null;
    }

    @Override
    public void assignTileAfterAddedToTileEntitiesOfType(Tile tile) throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public FixtureType type() throws IllegalStateException {
        return null;
    }

    @Override
    public TileFixtureItems items() throws IllegalStateException {
        return null;
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
        return TileFixture.class.getCanonicalName();
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

    @Override
    public EntityUuid uuid() {
        return null;
    }

    @Override
    public float getXTileWidthOffset() throws IllegalStateException, EntityDeletedException {
        return 0;
    }

    @Override
    public float getYTileHeightOffset() throws IllegalStateException, EntityDeletedException {
        return 0;
    }

    @Override
    public void setXTileWidthOffset(float v) throws IllegalStateException, EntityDeletedException {

    }

    @Override
    public void setYTileHeightOffset(float v) throws IllegalStateException, EntityDeletedException {

    }
}

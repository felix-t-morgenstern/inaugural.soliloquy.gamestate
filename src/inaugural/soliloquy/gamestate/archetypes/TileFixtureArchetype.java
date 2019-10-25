package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtureItems;
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
    public FixtureType fixtureType() throws IllegalStateException {
        return null;
    }

    @Override
    public Coordinate pixelOffset() throws IllegalStateException {
        return null;
    }

    @Override
    public TileFixtureItems items() throws IllegalStateException {
        return null;
    }

    @Override
    public void assignTileFixtureToTileAfterAddingToTileFixtures(Tile tile)
            throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public GenericParamsSet data() throws IllegalStateException {
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
    public Collection<GameMovementEvent> movementEvents() throws IllegalStateException {
        return null;
    }

    @Override
    public Collection<GameAbilityEvent> abilityEvents() throws IllegalStateException {
        return null;
    }

    @Override
    public GameEventTarget makeGameEventTarget() throws IllegalStateException {
        return null;
    }
}

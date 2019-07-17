package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtureItems;
import soliloquy.specs.ruleset.entities.FixtureType;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbility;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbility;

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
    public Map<String, ActiveAbility> activeAbilities() throws IllegalStateException {
        return null;
    }

    @Override
    public Map<String, ReactiveAbility> reactiveAbilities() throws IllegalStateException {
        return null;
    }

    @Override
    public TileFixtureItems containedItems() throws IllegalStateException {
        return null;
    }

    @Override
    public void assignTileFixtureToTile(Tile tile) throws IllegalArgumentException, IllegalStateException {

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
}

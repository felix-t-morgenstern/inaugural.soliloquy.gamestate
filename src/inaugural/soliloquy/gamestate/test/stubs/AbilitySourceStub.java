package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.ruleset.entities.abilities.Ability;
import soliloquy.specs.ruleset.entities.abilities.AbilitySource;

public class AbilitySourceStub implements AbilitySource {
    @Override
    public Ability ability() throws IllegalStateException {
        return null;
    }

    @Override
    public void initializeAbility(Ability ability) throws IllegalArgumentException, UnsupportedOperationException {

    }

    @Override
    public void initializeItemSource(Item item) throws IllegalArgumentException, UnsupportedOperationException {

    }

    @Override
    public Item item() throws IllegalStateException {
        return null;
    }

    @Override
    public void initializeCharacterSource(Character character) throws IllegalArgumentException, UnsupportedOperationException {

    }

    @Override
    public Character character() throws IllegalStateException {
        return null;
    }

    @Override
    public void initializeTileSource(Tile tile) throws IllegalArgumentException, UnsupportedOperationException {

    }

    @Override
    public Tile tile() throws IllegalStateException {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

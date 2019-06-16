package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.ICharacter;
import soliloquy.specs.gamestate.entities.IItem;
import soliloquy.specs.gamestate.entities.ITile;
import soliloquy.specs.ruleset.entities.abilities.IAbility;
import soliloquy.specs.ruleset.entities.abilities.IAbilitySource;

public class AbilitySourceStub implements IAbilitySource {
    @Override
    public IAbility ability() throws IllegalStateException {
        return null;
    }

    @Override
    public void initializeAbility(IAbility iAbility) throws IllegalArgumentException, UnsupportedOperationException {

    }

    @Override
    public void initializeItemSource(IItem iItem) throws IllegalArgumentException, UnsupportedOperationException {

    }

    @Override
    public IItem item() throws IllegalStateException {
        return null;
    }

    @Override
    public void initializeCharacterSource(ICharacter iCharacter) throws IllegalArgumentException, UnsupportedOperationException {

    }

    @Override
    public ICharacter character() throws IllegalStateException {
        return null;
    }

    @Override
    public void initializeTileSource(ITile iTile) throws IllegalArgumentException, UnsupportedOperationException {

    }

    @Override
    public ITile tile() throws IllegalStateException {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

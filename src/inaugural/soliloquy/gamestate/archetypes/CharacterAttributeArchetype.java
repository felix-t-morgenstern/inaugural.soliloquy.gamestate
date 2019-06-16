package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.valueobjects.IMap;
import soliloquy.specs.gamestate.entities.ICharacter;
import soliloquy.specs.gamestate.entities.ICharacterAttribute;
import soliloquy.specs.ruleset.entities.IAttributeType;

public class CharacterAttributeArchetype implements ICharacterAttribute {
    @Override
    public IAttributeType attribute() {
        return null;
    }

    @Override
    public ICharacter character() throws IllegalStateException {
        return null;
    }

    @Override
    public int totalValue() throws IllegalStateException {
        return 0;
    }

    @Override
    public IMap<String, Integer> modifiers() throws IllegalStateException {
        return null;
    }

    @Override
    public void calculateValue() throws IllegalStateException {

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
        return ICharacterAttribute.class.getCanonicalName();
    }
}

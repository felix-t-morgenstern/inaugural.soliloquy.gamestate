package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.infrastructure.IReadOnlyMap;
import soliloquy.specs.gamestate.entities.ICharacter;
import soliloquy.specs.gamestate.entities.ICharacterVitalAttribute;
import soliloquy.specs.ruleset.entities.IVitalAttributeType;

public class CharacterVitalAttributeArchetype implements ICharacterVitalAttribute {
    @Override
    public IVitalAttributeType vitalAttributeType() throws IllegalStateException {
        return null;
    }

    @Override
    public int getCurrentValue() throws IllegalStateException {
        return 0;
    }

    @Override
    public void setCurrentValue(int i) throws IllegalStateException, IllegalArgumentException {

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
    public IReadOnlyMap<String, Integer> modifiersRepresentation() throws IllegalStateException {
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
        return ICharacterVitalAttribute.class.getCanonicalName();
    }
}

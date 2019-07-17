package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.infrastructure.ReadOnlyMap;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterVitalAttribute;
import soliloquy.specs.ruleset.entities.VitalAttributeType;

public class CharacterVitalAttributeArchetype implements CharacterVitalAttribute {
    @Override
    public VitalAttributeType vitalAttributeType() throws IllegalStateException {
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
    public Character character() throws IllegalStateException {
        return null;
    }

    @Override
    public int totalValue() throws IllegalStateException {
        return 0;
    }

    @Override
    public ReadOnlyMap<String, Integer> modifiersRepresentation() throws IllegalStateException {
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
        return CharacterVitalAttribute.class.getCanonicalName();
    }
}

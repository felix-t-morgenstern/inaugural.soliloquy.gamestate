package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.gamestate.entities.CharacterAttribute;
import soliloquy.specs.ruleset.entities.AttributeType;

public class CharacterAttributeArchetype implements CharacterAttribute {
    @Override
    public AttributeType attributeType() {
        return null;
    }

    @Override
    public int totalValue() throws IllegalStateException {
        return 0;
    }

    @Override
    public ReadableMap<String, Integer> modifiersRepresentation() throws IllegalStateException {
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
        return CharacterAttribute.class.getCanonicalName();
    }
}

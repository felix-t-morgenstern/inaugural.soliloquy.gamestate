package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.ruleset.valueobjects.CharacterClassification;

public class CharacterClassificationArchetype implements CharacterClassification {
    @Override
    public String getPluralName() {
        return null;
    }

    @Override
    public void setPluralName(String s) throws IllegalArgumentException {

    }

    @Override
    public String getInterfaceName() {
        return "soliloquy.ruleset.gameentities.specs.ICharacterClassification";
    }

    @Override
    public String id() throws IllegalStateException {
        return null;
    }
}

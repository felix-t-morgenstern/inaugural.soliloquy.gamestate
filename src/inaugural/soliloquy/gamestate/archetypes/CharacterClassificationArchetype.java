package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.ruleset.gameentities.specs.ICharacterClassification;

public class CharacterClassificationArchetype implements ICharacterClassification {
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

package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.ruleset.valueobjects.CharacterClassification;

public class CharacterClassificationArchetype implements CharacterClassification {
    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String s) {

    }

    @Override
    public String getPluralName() {
        return null;
    }

    @Override
    public void setPluralName(String s) throws IllegalArgumentException {

    }

    @Override
    public String getInterfaceName() {
        return CharacterClassification.class.getCanonicalName();
    }

    @Override
    public String id() throws IllegalStateException {
        return null;
    }
}

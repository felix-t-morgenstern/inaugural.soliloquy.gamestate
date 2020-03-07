package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.ruleset.valueobjects.CharacterClassification;

public class FakeCharacterClassification implements CharacterClassification {
    private final String ID;

    public FakeCharacterClassification(String id) {
        ID = id;
    }

    @Override
    public String id() throws IllegalStateException {
        return ID;
    }

    @Override
    public String getPluralName() {
        return null;
    }

    @Override
    public void setPluralName(String s) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String s) {

    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

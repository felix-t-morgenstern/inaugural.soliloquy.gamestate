package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.game.Game;
import soliloquy.specs.logger.Logger;
import soliloquy.specs.ruleset.entities.CharacterAIType;

public class FakeCharacterAIType implements CharacterAIType {
    private final String ID;

    public FakeCharacterAIType() {
        ID = null;
    }

    public FakeCharacterAIType(String id) {
        ID = id;
    }

    @Override
    public void act() {

    }

    @Override
    public Game game() {
        return null;
    }

    @Override
    public Logger logger() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public String id() throws IllegalStateException {
        return ID;
    }
}

package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.game.Game;
import soliloquy.specs.gamestate.entities.CharacterEvent;
import soliloquy.specs.logger.Logger;
import soliloquy.specs.ruleset.entities.CharacterAIType;

public class CharacterAITypeStub implements CharacterAIType {
    @Override
    public void act() {

    }

    @Override
    public Map<String, Collection<CharacterEvent>> events() {
        return null;
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
        return null;
    }
}

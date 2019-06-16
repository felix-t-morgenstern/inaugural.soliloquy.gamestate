package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.valueobjects.ICollection;
import soliloquy.specs.common.valueobjects.IMap;
import soliloquy.specs.game.IGame;
import soliloquy.specs.gamestate.entities.ICharacterEvent;
import soliloquy.specs.logger.ILogger;
import soliloquy.specs.ruleset.entities.ICharacterAIType;

public class CharacterAITypeStub implements ICharacterAIType {
    @Override
    public void act() {

    }

    @Override
    public IMap<String, ICollection<ICharacterEvent>> events() {
        return null;
    }

    @Override
    public IGame game() {
        return null;
    }

    @Override
    public ILogger logger() {
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

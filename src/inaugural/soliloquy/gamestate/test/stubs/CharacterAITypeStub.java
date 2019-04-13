package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.IMap;
import soliloquy.game.primary.specs.IGame;
import soliloquy.logger.specs.ILogger;
import soliloquy.ruleset.gameentities.specs.ICharacterEvent;
import soliloquy.ruleset.gameentities.specs.ICharacterAIType;

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
    public void read(String s, boolean b) throws IllegalArgumentException {

    }

    @Override
    public String write() throws IllegalArgumentException {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

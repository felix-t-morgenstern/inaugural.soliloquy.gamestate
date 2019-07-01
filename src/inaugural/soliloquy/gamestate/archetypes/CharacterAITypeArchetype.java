package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.infrastructure.ICollection;
import soliloquy.specs.common.infrastructure.IMap;
import soliloquy.specs.game.IGame;
import soliloquy.specs.gamestate.entities.ICharacterEvent;
import soliloquy.specs.logger.ILogger;
import soliloquy.specs.ruleset.entities.ICharacterAIType;

public class CharacterAITypeArchetype implements ICharacterAIType {
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
    public String id() throws IllegalStateException {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return ICharacterAIType.class.getCanonicalName();
    }
}

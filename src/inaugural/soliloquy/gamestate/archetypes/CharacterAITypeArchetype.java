package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.IMap;
import soliloquy.game.primary.specs.IGame;
import soliloquy.gamestate.specs.ICharacterEvent;
import soliloquy.logger.specs.ILogger;
import soliloquy.ruleset.gameentities.specs.ICharacterAIType;

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

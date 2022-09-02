package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.game.Game;
import soliloquy.specs.logger.Logger;
import soliloquy.specs.ruleset.entities.CharacterAIType;

public class CharacterAITypeArchetype implements CharacterAIType {
    @Override
    public void act() {

    }

    @Override
    public void fireEvent(String s, VariableCache variableCache) throws IllegalArgumentException {

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
    public String id() throws IllegalStateException {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return CharacterAIType.class.getCanonicalName();
    }
}

package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.ReadablePair;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;

public class CharacterQueueEntryArchetype implements Pair<Character, VariableCache> {
    @Override
    public Character getItem1() {
        return null;
    }

    @Override
    public void setItem1(Character character) throws IllegalArgumentException {

    }

    @Override
    public VariableCache getItem2() {
        return null;
    }

    @Override
    public void setItem2(VariableCache variableCache) throws IllegalArgumentException {

    }

    @Override
    public ReadablePair<Character, VariableCache> representation() {
        return null;
    }

    @Override
    public Character getFirstArchetype() throws IllegalStateException {
        return null;
    }

    @Override
    public VariableCache getSecondArchetype() throws IllegalStateException {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return Pair.class.getCanonicalName() + "<" + Character.class.getCanonicalName() + "," +
                VariableCache.class.getCanonicalName() + ">";
    }
}

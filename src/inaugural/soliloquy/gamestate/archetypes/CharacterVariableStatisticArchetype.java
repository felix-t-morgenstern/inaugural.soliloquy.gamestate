package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistic;
import soliloquy.specs.ruleset.entities.CharacterVariableStatisticType;

public class CharacterVariableStatisticArchetype implements CharacterVariableStatistic {
    @Override
    public CharacterVariableStatisticType type() throws IllegalStateException {
        return null;
    }

    @Override
    public int getCurrentValue() throws IllegalStateException {
        return 0;
    }

    @Override
    public void setCurrentValue(int i) throws IllegalStateException, IllegalArgumentException {

    }

    @Override
    public int totalValue() throws IllegalStateException {
        return 0;
    }

    @Override
    public ReadableMap<String, Integer> representation() throws IllegalStateException {
        return null;
    }

    @Override
    public void calculate() throws IllegalStateException {

    }

    @Override
    public void delete() throws IllegalStateException {

    }

    @Override
    public boolean isDeleted() {
        return false;
    }

    @Override
    public String getInterfaceName() {
        return CharacterVariableStatistic.class.getCanonicalName();
    }

    @Override
    public VariableCache data() throws IllegalStateException {
        return null;
    }
}

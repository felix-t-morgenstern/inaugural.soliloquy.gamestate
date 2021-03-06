package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.CharacterStatistic;
import soliloquy.specs.ruleset.entities.CharacterStaticStatisticType;

public class CharacterStaticStatisticArchetype
        implements CharacterStatistic<CharacterStaticStatisticType> {
    @Override
    public CharacterStaticStatisticType type() {
        return null;
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
        return CharacterStatistic.class.getCanonicalName() + "<" +
                CharacterStaticStatisticType.class.getCanonicalName() + ">";
    }

    @Override
    public VariableCache data() throws IllegalStateException {
        return null;
    }

    @Override
    public int totalValue() throws IllegalStateException {
        return 0;
    }

    @Override
    public Map<String, Integer> representation() throws IllegalStateException {
        return null;
    }

    @Override
    public void calculate() throws IllegalStateException {

    }
}

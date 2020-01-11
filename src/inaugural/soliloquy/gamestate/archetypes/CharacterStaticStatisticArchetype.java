package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.gamestate.entities.CharacterStaticStatistic;
import soliloquy.specs.ruleset.entities.CharacterStaticStatisticType;

public class CharacterStaticStatisticArchetype implements CharacterStaticStatistic {
    @Override
    public CharacterStaticStatisticType type() {
        return null;
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
        return CharacterStaticStatistic.class.getCanonicalName();
    }
}

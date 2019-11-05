package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.gamestate.entities.CharacterStatistic;
import soliloquy.specs.ruleset.entities.CharacterStatisticType;

public class CharacterStatisticArchetype implements CharacterStatistic {
    @Override
    public CharacterStatisticType type() {
        return null;
    }

    @Override
    public int totalValue() throws IllegalStateException {
        return 0;
    }

    @Override
    public ReadableMap<String, Integer> modifiersRepresentation() throws IllegalStateException {
        return null;
    }

    @Override
    public void calculateValue() throws IllegalStateException {

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
        return CharacterStatistic.class.getCanonicalName();
    }
}

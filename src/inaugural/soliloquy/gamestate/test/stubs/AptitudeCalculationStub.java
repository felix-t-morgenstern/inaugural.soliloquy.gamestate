package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.ruleset.entities.AptitudeType;
import soliloquy.specs.ruleset.gameconcepts.CharacterStatisticCalculation;

public class AptitudeCalculationStub implements CharacterStatisticCalculation<AptitudeType> {
    public static final int VALUE = 123123;
    public static final Map<String,Integer> MODIFIERS = new MapFactoryStub().make("",0);

    public static Character _character;
    public static AptitudeType _aptitudeType;
    @Override
    public Pair<Integer, Map<String, Integer>> calculateStatistic(Character character,
                                                                  AptitudeType aptitudeType)
            throws IllegalArgumentException, IllegalStateException {
        _character = character;
        _aptitudeType = aptitudeType;
        return new PairStub<>(VALUE, MODIFIERS);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

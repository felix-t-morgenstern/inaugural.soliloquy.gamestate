package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.ruleset.entities.CharacterStatisticType;
import soliloquy.specs.ruleset.gameconcepts.CharacterStatisticCalculation;

public class CharacterStatisticCalculationStub implements CharacterStatisticCalculation {
    public static final int VALUE = 123123;
    public static final Map<String,Integer> MODIFIERS = new MapFactoryStub().make("",0);

    public static Character _character;
    public static CharacterStatisticType _statisticType;
    @Override
    public Pair<Integer, Map<String, Integer>> calculate(Character character,
                                                         CharacterStatisticType statisticType)
            throws IllegalArgumentException, IllegalStateException {
        _character = character;
        _statisticType = statisticType;
        return new PairStub<>(VALUE, MODIFIERS);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

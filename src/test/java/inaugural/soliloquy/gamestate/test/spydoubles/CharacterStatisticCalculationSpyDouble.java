package inaugural.soliloquy.gamestate.test.spydoubles;

import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.ruleset.entities.CharacterStatisticType;
import soliloquy.specs.ruleset.gameconcepts.CharacterStatisticCalculation;

import java.util.HashMap;
import java.util.Map;

public class CharacterStatisticCalculationSpyDouble implements CharacterStatisticCalculation {
    public static final int VALUE = 123123;
    public static final Map<String, Integer> MODIFIERS = new HashMap<>();

    public Character _character;
    public CharacterStatisticType _statisticType;

    @Override
    public Pair<Integer, Map<String, Integer>> calculate(Character character,
                                                         CharacterStatisticType statisticType)
            throws IllegalArgumentException, IllegalStateException {
        _character = character;
        _statisticType = statisticType;
        return new Pair<>(VALUE, MODIFIERS);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

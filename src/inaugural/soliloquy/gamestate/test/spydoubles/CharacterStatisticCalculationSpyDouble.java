package inaugural.soliloquy.gamestate.test.spydoubles;

import inaugural.soliloquy.gamestate.test.fakes.FakeMapFactory;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.ruleset.entities.CharacterStatisticType;
import soliloquy.specs.ruleset.gameconcepts.CharacterStatisticCalculation;

public class CharacterStatisticCalculationSpyDouble implements CharacterStatisticCalculation {
    public static final int VALUE = 123123;
    public static final Map<String, Integer> MODIFIERS = new FakeMapFactory().make("", 0);

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

package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.ruleset.entities.AttributeType;
import soliloquy.specs.ruleset.gameconcepts.CharacterStatisticCalculation;

public class AttributeCalculationStub implements CharacterStatisticCalculation<AttributeType> {
    public static final int VALUE = 123123;
    public static final Map<String,Integer> MODIFIERS = new MapFactoryStub().make("",0);

    public static Character _character;
    public static AttributeType _attributeType;
    @Override
    public Pair<Integer, Map<String, Integer>> calculateStatistic(Character character,
                                                                  AttributeType attributeType)
            throws IllegalArgumentException, IllegalStateException {
        _character = character;
        _attributeType = attributeType;
        return new PairStub<>(VALUE, MODIFIERS);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

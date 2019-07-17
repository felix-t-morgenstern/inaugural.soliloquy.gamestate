package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.ruleset.entities.VitalAttributeType;
import soliloquy.specs.ruleset.gameconcepts.VitalAttributeCalculation;

public class VitalAttributeCalculationStub implements VitalAttributeCalculation {
    public static final int VALUE = 123123;
    public static final Map<String,Integer> MODIFIERS = new MapFactoryStub().make("",0);

    public static Character _character;
    public static VitalAttributeType _vitalAttributeType;

    @Override
    public Pair<Integer, Map<String, Integer>> calculateVitalAttributeMaxValue
            (Character character, VitalAttributeType vitalAttributeType)
            throws IllegalArgumentException, IllegalStateException {
        _character = character;
        _vitalAttributeType = vitalAttributeType;
        return new PairStub<>(VALUE, MODIFIERS);
    }
}

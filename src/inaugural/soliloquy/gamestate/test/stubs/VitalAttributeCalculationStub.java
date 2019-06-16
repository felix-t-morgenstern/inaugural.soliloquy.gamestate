package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.valueobjects.IMap;
import soliloquy.specs.common.valueobjects.IPair;
import soliloquy.specs.gamestate.entities.ICharacter;
import soliloquy.specs.ruleset.entities.IVitalAttributeType;
import soliloquy.specs.ruleset.gameconcepts.IVitalAttributeCalculation;

public class VitalAttributeCalculationStub implements IVitalAttributeCalculation {
    public static final int VALUE = 123123;
    public static final IMap<String,Integer> MODIFIERS = new MapFactoryStub().make("",0);

    public static ICharacter _character;
    public static IVitalAttributeType _vitalAttributeType;

    @Override
    public IPair<Integer, IMap<String, Integer>> calculateVitalAttributeMaxValue
            (ICharacter character, IVitalAttributeType vitalAttributeType)
            throws IllegalArgumentException, IllegalStateException {
        _character = character;
        _vitalAttributeType = vitalAttributeType;
        return new PairStub<>(VALUE, MODIFIERS);
    }
}

package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.common.specs.IMap;
import soliloquy.common.specs.IPair;
import soliloquy.gamestate.specs.ICharacter;
import soliloquy.ruleset.gameconcepts.specs.IVitalAttributeCalculation;
import soliloquy.ruleset.gameentities.specs.IVitalAttributeType;

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

package inaugural.soliloquy.gamestate;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterAptitude;
import soliloquy.specs.ruleset.entities.AptitudeType;
import soliloquy.specs.ruleset.gameconcepts.CharacterStatisticCalculation;

public class CharacterAptitudeImpl extends CharacterStatistic<AptitudeType> implements CharacterAptitude {
    @SuppressWarnings("ConstantConditions")
    public CharacterAptitudeImpl(Character character, AptitudeType aptitudeType,
                                 CharacterStatisticCalculation<AptitudeType>
                                         characterAptitudeCalculation) {
        super(character, aptitudeType, characterAptitudeCalculation);
    }

    @Override
    public AptitudeType aptitudeType() {
        enforceDeletionInvariants("aptitudeType");
        return ENTITY_TYPE;
    }

    @Override
    public String getInterfaceName() {
        enforceDeletionInvariants("getInterfaceName");
        return CharacterAptitude.class.getCanonicalName();
    }

    @Override
    protected String className() {
        return "CharacterAptitudeImpl";
    }
}

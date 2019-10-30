package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterAptitude;
import soliloquy.specs.ruleset.entities.AptitudeType;
import soliloquy.specs.ruleset.gameconcepts.CharacterStatisticCalculation;

public class CharacterAptitudeImpl extends HasDeletionInvariants implements CharacterAptitude {
    private final Character CHARACTER;
    private final AptitudeType APTITUDE_TYPE;
    private final CharacterStatisticCalculation<AptitudeType> CHARACTER_APTITUDE_CALCULATION;

    private int _totalValue;
    private Map<String,Integer> _modifiers;

    @SuppressWarnings("ConstantConditions")
    public CharacterAptitudeImpl(Character character, AptitudeType aptitudeType,
                                 CharacterStatisticCalculation<AptitudeType>
                                         characterAptitudeCalculation) {
        if (character == null) {
            throw new IllegalArgumentException("CharacterAptitudeImpl: character cannot be null");
        }
        CHARACTER = character;
        if (aptitudeType == null) {
            throw new IllegalArgumentException(
                    "CharacterAptitudeImpl: aptitudeType cannot be null");
        }
        APTITUDE_TYPE = aptitudeType;
        if (characterAptitudeCalculation == null) {
            throw new IllegalArgumentException(
                    "CharacterAptitudeImpl: characterAptitudeCalculation cannot be null");
        }
        CHARACTER_APTITUDE_CALCULATION = characterAptitudeCalculation;
    }

    @Override
    public AptitudeType aptitudeType() {
        enforceDeletionInvariants("aptitudeType");
        return APTITUDE_TYPE;
    }

    @Override
    public int totalValue() throws IllegalStateException {
        enforceDeletionInvariants("totalValue");
        return _totalValue;
    }

    @Override
    public ReadableMap<String, Integer> modifiersRepresentation() throws IllegalStateException {
        enforceDeletionInvariants("modifiersRepresentation");
        // TODO: Test and implement whether truly read-only
        return _modifiers.readOnlyRepresentation();
    }

    @Override
    public void calculateValue() throws IllegalStateException {
        enforceDeletionInvariants("calculateValue");
        Pair<Integer,Map<String,Integer>> calculatedValueAndModifiers =
                CHARACTER_APTITUDE_CALCULATION
                        .calculateStatistic(CHARACTER, APTITUDE_TYPE);
        _totalValue = calculatedValueAndModifiers.getItem1();
        _modifiers = calculatedValueAndModifiers.getItem2();
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

    @Override
    protected String containingClassName() {
        return "Character";
    }

    @Override
    protected boolean containingObjectIsDeleted() {
        return CHARACTER.isDeleted();
    }

    @Override
    public void delete() throws IllegalStateException {
        enforceDeletionInvariants("delete");
        _isDeleted = true;
    }
}

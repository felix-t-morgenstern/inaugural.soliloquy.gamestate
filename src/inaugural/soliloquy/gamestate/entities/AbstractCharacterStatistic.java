package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterStatistic;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.ruleset.entities.CharacterStatisticType;
import soliloquy.specs.ruleset.gameconcepts.CharacterStatisticCalculation;

abstract class AbstractCharacterStatistic<TEntityType extends CharacterStatisticType>
        extends HasDeletionInvariants implements CharacterStatistic<TEntityType> {
    private final Character CHARACTER;
    private final TEntityType ENTITY_TYPE;
    private final VariableCache DATA;
    private final CharacterStatisticCalculation CHARACTER_STATISTIC_CALCULATION;

    private int _totalValue;
    private Map<String,Integer> _modifiers;

    AbstractCharacterStatistic(Character character, TEntityType entityType, VariableCache data,
                               CharacterStatisticCalculation characterStatisticCalculation) {
        CHARACTER = Check.ifNull(character, "character");
        ENTITY_TYPE = Check.ifNull(entityType, "entityType");
        DATA = Check.ifNull(data, "data");
        CHARACTER_STATISTIC_CALCULATION = Check.ifNull(characterStatisticCalculation,
                "characterStatisticCalculation");
    }

    @Override
    public TEntityType type() {
        enforceDeletionInvariants("type");
        return ENTITY_TYPE;
    }

    @Override
    public VariableCache data() {
        enforceDeletionInvariants("data");
        return DATA;
    }

    @Override
    public int totalValue() throws IllegalStateException {
        enforceDeletionInvariants("totalValue");
        return _totalValue;
    }

    @Override
    public Map<String, Integer> representation() throws IllegalStateException {
        enforceDeletionInvariants("representation");
        return _modifiers.makeClone();
    }

    @Override
    public void calculate() throws IllegalStateException {
        enforceDeletionInvariants("calculate");
        Pair<Integer,Map<String,Integer>> calculatedValueAndModifiers =
                CHARACTER_STATISTIC_CALCULATION
                        .calculate(CHARACTER, ENTITY_TYPE);
        _totalValue = calculatedValueAndModifiers.getItem1();
        _modifiers = calculatedValueAndModifiers.getItem2();
    }

    @Override
    protected Deletable getContainingObject() {
        return CHARACTER;
    }

    @Override
    protected String containingClassName() {
        return "Character";
    }
}

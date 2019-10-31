package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterValueFromModifiers;
import soliloquy.specs.ruleset.gameconcepts.CharacterStatisticCalculation;

abstract class CharacterStatistic<TEntityType> extends HasDeletionInvariants
        implements CharacterValueFromModifiers {
    final TEntityType ENTITY_TYPE;
    private final Character CHARACTER;
    private final CharacterStatisticCalculation<TEntityType> CHARACTER_STATISTIC_CALCULATION;

    private int _totalValue;
    private Map<String,Integer> _modifiers;

    CharacterStatistic(Character character, TEntityType entityType,
                       CharacterStatisticCalculation<TEntityType> characterStatisticCalculation) {
        if (character == null) {
            throw new IllegalArgumentException(className() + ": character cannot be null");
        }
        CHARACTER = character;
        if (entityType == null) {
            throw new IllegalArgumentException(className() +  ": entityType cannot be null");
        }
        ENTITY_TYPE = entityType;
        if (characterStatisticCalculation == null) {
            throw new IllegalArgumentException(className() +
                    ": characterStatisticCalculation cannot be null");
        }
        CHARACTER_STATISTIC_CALCULATION = characterStatisticCalculation;
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
                CHARACTER_STATISTIC_CALCULATION
                        .calculateStatistic(CHARACTER, ENTITY_TYPE);
        _totalValue = calculatedValueAndModifiers.getItem1();
        _modifiers = calculatedValueAndModifiers.getItem2();
    }

    @Override
    protected boolean containingObjectIsDeleted() {
        return CHARACTER.isDeleted();
    }

    @Override
    protected String containingClassName() {
        return "Character";
    }

    @Override
    public void delete() throws IllegalStateException {
        enforceDeletionInvariants("delete");
        _isDeleted = true;
    }
}

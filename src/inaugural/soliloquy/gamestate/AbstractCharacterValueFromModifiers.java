package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterValueFromModifiers;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.ruleset.entities.CharacterStatisticType;
import soliloquy.specs.ruleset.gameconcepts.CharacterStatisticCalculation;

abstract class AbstractCharacterValueFromModifiers<TEntityType extends CharacterStatisticType>
        extends HasDeletionInvariants
        implements CharacterValueFromModifiers {
    final TEntityType ENTITY_TYPE;
    private final Character CHARACTER;
    private final CharacterStatisticCalculation CHARACTER_STATISTIC_CALCULATION;

    private int _totalValue;
    private Map<String,Integer> _modifiers;

    AbstractCharacterValueFromModifiers(Character character, TEntityType entityType,
                                        CharacterStatisticCalculation characterStatisticCalculation) {
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

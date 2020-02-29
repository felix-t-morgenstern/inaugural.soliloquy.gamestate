package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.tools.Check;
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
        CHARACTER = Check.ifNull(character, "character");
        ENTITY_TYPE = Check.ifNull(entityType, "entityType");
        CHARACTER_STATISTIC_CALCULATION = Check.ifNull(characterStatisticCalculation,
                "characterStatisticCalculation");
    }

    @Override
    public int totalValue() throws IllegalStateException {
        enforceDeletionInvariants("totalValue");
        return _totalValue;
    }

    @Override
    public ReadableMap<String, Integer> representation() throws IllegalStateException {
        enforceDeletionInvariants("representation");
        // TODO: Test and implement whether truly read-only
        return _modifiers.readOnlyRepresentation();
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

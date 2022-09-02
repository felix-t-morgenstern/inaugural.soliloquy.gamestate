package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.gamestate.archetypes.CharacterVariableStatisticArchetype;
import inaugural.soliloquy.gamestate.archetypes.CharacterVariableStatisticTypeArchetype;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistic;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistics;
import soliloquy.specs.gamestate.factories.EntityMemberOfTypeFactory;
import soliloquy.specs.ruleset.entities.CharacterVariableStatisticType;

import java.util.HashMap;
import java.util.Map;

public class CharacterVariableStatisticsImpl
        extends EntityMembersOfTypeImpl<CharacterVariableStatisticType,
        CharacterVariableStatistic, Character>
        implements CharacterVariableStatistics {
    private static final CharacterVariableStatisticType ENTITY_MEMBER_TYPE_ARCHETYPE =
            new CharacterVariableStatisticTypeArchetype();
    private static final CharacterVariableStatistic ENTITY_MEMBER_ARCHETYPE =
            new CharacterVariableStatisticArchetype();

    @SuppressWarnings("ConstantConditions")
    public CharacterVariableStatisticsImpl(Character character,
                                           EntityMemberOfTypeFactory<
                                                   CharacterVariableStatisticType,
                                                   CharacterVariableStatistic, Character>
                                                   factory,
                                           VariableCacheFactory dataFactory) {
        super(character, c -> t -> d -> factory.make(c, t, d), dataFactory,
                ENTITY_MEMBER_TYPE_ARCHETYPE, ENTITY_MEMBER_ARCHETYPE);
        Check.ifNull(factory, "factory");
    }

    @Override
    public Map<CharacterVariableStatisticType, Integer> currentValues() {
        enforceDeletionInvariants();
        HashMap<CharacterVariableStatisticType, Integer> currentValues = new HashMap<>();
        ENTITIES.forEach((t, s) -> currentValues.put(t, s.getCurrentValue()));
        return currentValues;
    }

    @Override
    public Map<CharacterVariableStatisticType, Integer> maxValues() {
        enforceDeletionInvariants();
        HashMap<CharacterVariableStatisticType, Integer> maxValues = new HashMap<>();
        ENTITIES.forEach((t, s) -> {
            s.calculate();
            maxValues.put(t, s.totalValue());
        });
        return maxValues;
    }

    @Override
    public String getInterfaceName() {
        return CharacterVariableStatistics.class.getCanonicalName();
    }
}

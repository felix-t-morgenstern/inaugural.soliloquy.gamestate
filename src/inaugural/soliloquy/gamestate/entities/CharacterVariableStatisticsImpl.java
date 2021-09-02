package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.gamestate.archetypes.CharacterVariableStatisticArchetype;
import inaugural.soliloquy.gamestate.archetypes.CharacterVariableStatisticTypeArchetype;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistic;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistics;
import soliloquy.specs.gamestate.factories.EntityMemberOfTypeFactory;
import soliloquy.specs.ruleset.entities.CharacterVariableStatisticType;

public class CharacterVariableStatisticsImpl
        extends EntityMembersOfTypeImpl<CharacterVariableStatisticType,
                    CharacterVariableStatistic, Character>
        implements CharacterVariableStatistics {
    private static final CharacterVariableStatisticType ENTITY_MEMBER_TYPE_ARCHETYPE =
            new CharacterVariableStatisticTypeArchetype();
    private static final CharacterVariableStatistic ENTITY_MEMBER_ARCHETYPE =
            new CharacterVariableStatisticArchetype();

    private final MapFactory MAP_FACTORY;

    public CharacterVariableStatisticsImpl(Character character,
                                           EntityMemberOfTypeFactory<
                                                   CharacterVariableStatisticType,
                                                   CharacterVariableStatistic, Character>
                                                   factory,
                                           ListFactory listFactory,
                                           VariableCacheFactory dataFactory,
                                           MapFactory mapFactory) {
        super(character, c -> t -> d -> factory.make(c, t, d), listFactory, dataFactory,
                ENTITY_MEMBER_TYPE_ARCHETYPE, ENTITY_MEMBER_ARCHETYPE);
        Check.ifNull(factory, "factory");
        MAP_FACTORY = Check.ifNull(mapFactory, "mapFactory");
    }

    @Override
    public Map<CharacterVariableStatisticType, Integer> currentValues() {
        enforceDeletionInvariants();
        Map<CharacterVariableStatisticType, Integer> currentValues =
                MAP_FACTORY.make(ENTITY_MEMBER_TYPE_ARCHETYPE, 0);
        ENTITIES.forEach((t,s) -> currentValues.put(t, s.getCurrentValue()));
        return currentValues;
    }

    @Override
    public Map<CharacterVariableStatisticType, Integer> maxValues() {
        enforceDeletionInvariants();
        Map<CharacterVariableStatisticType, Integer> maxValues =
                MAP_FACTORY.make(ENTITY_MEMBER_TYPE_ARCHETYPE, 0);
        ENTITIES.forEach((t,s) -> {
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

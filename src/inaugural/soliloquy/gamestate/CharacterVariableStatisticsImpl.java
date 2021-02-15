package inaugural.soliloquy.gamestate;

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
import soliloquy.specs.gamestate.factories.CharacterEntityOfTypeFactory;
import soliloquy.specs.ruleset.entities.CharacterVariableStatisticType;

public class CharacterVariableStatisticsImpl
        extends CharacterEntitiesOfTypeImpl<CharacterVariableStatisticType,
            CharacterVariableStatistic>
        implements CharacterVariableStatistics {
    private static final CharacterVariableStatistic ARCHETYPE =
            new CharacterVariableStatisticArchetype();
    private static final CharacterVariableStatisticType TYPE_ARCHETYPE =
            new CharacterVariableStatisticTypeArchetype();

    private final MapFactory MAP_FACTORY;

    public CharacterVariableStatisticsImpl(Character character,
                                           CharacterEntityOfTypeFactory<
                                                     CharacterVariableStatisticType,
                                                     CharacterVariableStatistic>
                                                     factory,
                                           ListFactory listFactory,
                                           VariableCacheFactory dataFactory,
                                           MapFactory mapFactory) {
        super(character, c -> t -> d -> factory.make(c, t, d), listFactory, dataFactory,
                ARCHETYPE);
        Check.ifNull(factory, "factory");
        MAP_FACTORY = Check.ifNull(mapFactory, "mapFactory");
    }

    @Override
    public Map<CharacterVariableStatisticType, Integer> currentValues() {
        enforceDeletionInvariants("currentValues");
        Map<CharacterVariableStatisticType, Integer> currentValues =
                MAP_FACTORY.make(TYPE_ARCHETYPE, 0);
        ENTITIES.forEach((t,s) -> currentValues.put(t, s.getCurrentValue()));
        return currentValues;
    }

    @Override
    public Map<CharacterVariableStatisticType, Integer> maxValues() {
        enforceDeletionInvariants("maxValues");
        Map<CharacterVariableStatisticType, Integer> maxValues =
                MAP_FACTORY.make(TYPE_ARCHETYPE, 0);
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

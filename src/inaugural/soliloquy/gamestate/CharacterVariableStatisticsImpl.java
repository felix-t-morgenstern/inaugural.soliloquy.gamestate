package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.CharacterVariableStatisticArchetype;
import inaugural.soliloquy.gamestate.archetypes.CharacterVariableStatisticTypeArchetype;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.ReadableMap;
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

    @SuppressWarnings("ConstantConditions")
    public CharacterVariableStatisticsImpl(Character character,
                                           MapFactory mapFactory,
                                           CollectionFactory collectionFactory,
                                           CharacterEntityOfTypeFactory<
                                                     CharacterVariableStatisticType,
                                                     CharacterVariableStatistic>
                                                     factory) {
        super(character, t -> c -> factory.make(c, t), collectionFactory, ARCHETYPE);
        if (factory == null) {
            throw new IllegalArgumentException(
                    "CharacterVariableStatisticsImpl: factory cannot be null");
        }
        if (mapFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterVariableStatisticsImpl: mapFactory cannot be null");
        }
        MAP_FACTORY = mapFactory;
    }

    @Override
    public ReadableMap<CharacterVariableStatisticType, Integer> currentValues() {
        enforceDeletionInvariants("currentValues");
        Map<CharacterVariableStatisticType, Integer> currentValues =
                MAP_FACTORY.make(TYPE_ARCHETYPE, 0);
        ENTITIES.forEach((t,s) -> currentValues.put(t, s.getCurrentValue()));
        return currentValues.readOnlyRepresentation();
    }

    @Override
    public ReadableMap<CharacterVariableStatisticType, Integer> maxValues() {
        enforceDeletionInvariants("maxValues");
        Map<CharacterVariableStatisticType, Integer> maxValues =
                MAP_FACTORY.make(TYPE_ARCHETYPE, 0);
        ENTITIES.forEach((t,s) -> {
            s.calculate();
            maxValues.put(t, s.totalValue());
        });
        return maxValues.readOnlyRepresentation();
    }

    @Override
    public String getInterfaceName() {
        return CharacterVariableStatistics.class.getCanonicalName();
    }
}

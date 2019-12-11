package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.CharacterDepletableStatisticArchetype;
import inaugural.soliloquy.gamestate.archetypes.CharacterDepletableStatisticTypeArchetype;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterDepletableStatistic;
import soliloquy.specs.gamestate.entities.CharacterDepletableStatistics;
import soliloquy.specs.ruleset.entities.CharacterDepletableStatisticType;

import java.util.function.Function;

public class CharacterDepletableStatisticsImpl
        extends CharacterEntitiesOfTypeImpl<CharacterDepletableStatisticType,
            CharacterDepletableStatistic>
        implements CharacterDepletableStatistics {
    private static final CharacterDepletableStatistic ARCHETYPE =
            new CharacterDepletableStatisticArchetype();
    private static final CharacterDepletableStatisticType TYPE_ARCHETYPE =
            new CharacterDepletableStatisticTypeArchetype();

    private final MapFactory MAP_FACTORY;

    @SuppressWarnings("ConstantConditions")
    public CharacterDepletableStatisticsImpl(Character character,
                                             MapFactory mapFactory,
                                             CollectionFactory collectionFactory,
                                             Function<CharacterDepletableStatisticType,
                                                     Function<Character,
                                                             CharacterDepletableStatistic>>
                                                     entityFactory) {
        super(character, entityFactory, collectionFactory, ARCHETYPE);
        if (mapFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterDepletableStatisticsImpl: mapFactory cannot be null");
        }
        MAP_FACTORY = mapFactory;
    }

    @Override
    public ReadableMap<CharacterDepletableStatisticType, Integer> currentValues() {
        enforceDeletionInvariants("currentValues");
        Map<CharacterDepletableStatisticType, Integer> currentValues =
                MAP_FACTORY.make(TYPE_ARCHETYPE, 0);
        ENTITIES.forEach((t,s) -> currentValues.put(t, s.getCurrentValue()));
        return currentValues.readOnlyRepresentation();
    }

    @Override
    public ReadableMap<CharacterDepletableStatisticType, Integer> maxValues() {
        enforceDeletionInvariants("maxValues");
        Map<CharacterDepletableStatisticType, Integer> maxValues =
                MAP_FACTORY.make(TYPE_ARCHETYPE, 0);
        ENTITIES.forEach((t,s) -> {
            s.calculateValue();
            maxValues.put(t, s.totalValue());
        });
        return maxValues.readOnlyRepresentation();
    }

    @Override
    public String getInterfaceName() {
        return CharacterDepletableStatistics.class.getCanonicalName();
    }
}

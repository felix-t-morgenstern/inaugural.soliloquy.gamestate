package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistic;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistics;
import soliloquy.specs.gamestate.factories.CharacterVariableStatisticsFactory;
import soliloquy.specs.gamestate.factories.CharacterEntityOfTypeFactory;
import soliloquy.specs.ruleset.entities.CharacterVariableStatisticType;

public class CharacterVariableStatisticsFactoryImpl
        implements CharacterVariableStatisticsFactory {
    private final MapFactory MAP_FACTORY;
    private final CollectionFactory COLLECTION_FACTORY;
    private final CharacterEntityOfTypeFactory<CharacterVariableStatisticType,
            CharacterVariableStatistic> FACTORY;

    @SuppressWarnings("ConstantConditions")
    public CharacterVariableStatisticsFactoryImpl(MapFactory mapFactory,
                                                  CollectionFactory collectionFactory,
                                                  CharacterEntityOfTypeFactory<
                                                            CharacterVariableStatisticType,
                                                            CharacterVariableStatistic>
                                                            factory) {
        if (mapFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterVariableStatisticsFactoryImpl: mapFactory cannot be null");
        }
        MAP_FACTORY = mapFactory;
        if (collectionFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterVariableStatisticsFactoryImpl: collectionFactory cannot be null");
        }
        COLLECTION_FACTORY = collectionFactory;
        if (factory == null) {
            throw new IllegalArgumentException(
                    "CharacterVariableStatisticsFactoryImpl: factory cannot be null");
        }
        FACTORY = factory;
    }

    @Override
    public CharacterVariableStatistics make(Character character) {
        if (character == null) {
            throw new IllegalArgumentException(
                    "CharacterVariableStatisticsFactoryImpl.make: character cannot be null");
        }
        return new CharacterVariableStatisticsImpl(character, MAP_FACTORY, COLLECTION_FACTORY,
                FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return CharacterVariableStatisticsFactory.class.getCanonicalName();
    }
}

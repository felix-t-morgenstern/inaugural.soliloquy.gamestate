package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterDepletableStatistic;
import soliloquy.specs.gamestate.entities.CharacterDepletableStatistics;
import soliloquy.specs.gamestate.factories.CharacterDepletableStatisticsFactory;
import soliloquy.specs.gamestate.factories.CharacterEntityOfTypeFactory;
import soliloquy.specs.ruleset.entities.CharacterDepletableStatisticType;

public class CharacterDepletableStatisticsFactoryImpl
        implements CharacterDepletableStatisticsFactory {
    private final MapFactory MAP_FACTORY;
    private final CollectionFactory COLLECTION_FACTORY;
    private final CharacterEntityOfTypeFactory<CharacterDepletableStatisticType,
            CharacterDepletableStatistic> FACTORY;

    @SuppressWarnings("ConstantConditions")
    public CharacterDepletableStatisticsFactoryImpl(MapFactory mapFactory,
                                                    CollectionFactory collectionFactory,
                                                    CharacterEntityOfTypeFactory<
                                                            CharacterDepletableStatisticType,
                                                            CharacterDepletableStatistic>
                                                            factory) {
        if (mapFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterDepletableStatisticsFactoryImpl: mapFactory cannot be null");
        }
        MAP_FACTORY = mapFactory;
        if (collectionFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterDepletableStatisticsFactoryImpl: collectionFactory cannot be null");
        }
        COLLECTION_FACTORY = collectionFactory;
        if (factory == null) {
            throw new IllegalArgumentException(
                    "CharacterDepletableStatisticsFactoryImpl: factory cannot be null");
        }
        FACTORY = factory;
    }

    @Override
    public CharacterDepletableStatistics make(Character character) {
        if (character == null) {
            throw new IllegalArgumentException(
                    "CharacterDepletableStatisticsFactoryImpl.make: character cannot be null");
        }
        return new CharacterDepletableStatisticsImpl(character, MAP_FACTORY, COLLECTION_FACTORY,
                FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return CharacterDepletableStatisticsFactory.class.getCanonicalName();
    }
}

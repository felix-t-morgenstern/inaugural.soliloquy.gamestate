package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistic;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistics;
import soliloquy.specs.gamestate.factories.CharacterVariableStatisticsFactory;
import soliloquy.specs.gamestate.factories.CharacterEntityOfTypeFactory;
import soliloquy.specs.ruleset.entities.CharacterVariableStatisticType;

public class CharacterVariableStatisticsFactoryImpl
        implements CharacterVariableStatisticsFactory {
    private final MapFactory MAP_FACTORY;
    private final ListFactory LIST_FACTORY;
    private final VariableCacheFactory DATA_FACTORY;
    private final CharacterEntityOfTypeFactory<CharacterVariableStatisticType,
            CharacterVariableStatistic> FACTORY;

    public CharacterVariableStatisticsFactoryImpl(MapFactory mapFactory,
                                                  ListFactory listFactory,
                                                  VariableCacheFactory dataFactory,
                                                  CharacterEntityOfTypeFactory<
                                                            CharacterVariableStatisticType,
                                                            CharacterVariableStatistic>
                                                            factory) {
        MAP_FACTORY = Check.ifNull(mapFactory, "mapFactory");
        LIST_FACTORY = Check.ifNull(listFactory, "listFactory");
        DATA_FACTORY = Check.ifNull(dataFactory, "dataFactory");
        FACTORY = Check.ifNull(factory, "factory");
    }

    @Override
    public CharacterVariableStatistics make(Character character) {
        if (character == null) {
            throw new IllegalArgumentException(
                    "CharacterVariableStatisticsFactoryImpl.make: character cannot be null");
        }
        return new CharacterVariableStatisticsImpl(character, FACTORY, LIST_FACTORY,
                DATA_FACTORY, MAP_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return CharacterVariableStatisticsFactory.class.getCanonicalName();
    }
}

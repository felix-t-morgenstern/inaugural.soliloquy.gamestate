package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.CharacterVariableStatisticsImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistic;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistics;
import soliloquy.specs.gamestate.factories.CharacterVariableStatisticsFactory;
import soliloquy.specs.gamestate.factories.EntityMemberOfTypeFactory;
import soliloquy.specs.ruleset.entities.CharacterVariableStatisticType;

public class CharacterVariableStatisticsFactoryImpl
        implements CharacterVariableStatisticsFactory {
    private final VariableCacheFactory DATA_FACTORY;
    private final EntityMemberOfTypeFactory<CharacterVariableStatisticType,
            CharacterVariableStatistic, Character> FACTORY;

    @SuppressWarnings("ConstantConditions")
    public CharacterVariableStatisticsFactoryImpl(VariableCacheFactory dataFactory,
                                                  EntityMemberOfTypeFactory<
                                                          CharacterVariableStatisticType,
                                                          CharacterVariableStatistic, Character>
                                                          factory) {
        DATA_FACTORY = Check.ifNull(dataFactory, "dataFactory");
        FACTORY = Check.ifNull(factory, "factory");
    }

    @Override
    public CharacterVariableStatistics make(Character character) {
        return new CharacterVariableStatisticsImpl(Check.ifNull(character, "character"), FACTORY,
                DATA_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return CharacterVariableStatisticsFactory.class.getCanonicalName();
    }
}

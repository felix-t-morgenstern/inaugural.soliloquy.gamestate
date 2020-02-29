package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistic;
import soliloquy.specs.gamestate.factories.CharacterEntityOfTypeFactory;
import soliloquy.specs.ruleset.entities.CharacterVariableStatisticType;
import soliloquy.specs.ruleset.gameconcepts.CharacterStatisticCalculation;

public class CharacterVariableStatisticFactory
        implements CharacterEntityOfTypeFactory<CharacterVariableStatisticType,
        CharacterVariableStatistic> {
    private final CharacterStatisticCalculation CALCULATION;
    private final VariableCacheFactory DATA_FACTORY;

    public CharacterVariableStatisticFactory(VariableCacheFactory dataFactory,
                                             CharacterStatisticCalculation calculation) {
        DATA_FACTORY = Check.ifNull(dataFactory, "dataFactory");
        CALCULATION = Check.ifNull(calculation, "calculation");
    }

    // TODO: Test throws on invalid params
    @Override
    public CharacterVariableStatistic make(Character character,
                                             CharacterVariableStatisticType type)
            throws IllegalArgumentException {
        return make(character, type, DATA_FACTORY.make());
    }

    // TODO: Test and implement!
    // TODO: Test throws on invalid params
    @Override
    public CharacterVariableStatistic make(Character character,
                                           CharacterVariableStatisticType type,
                                           VariableCache data) throws IllegalArgumentException {
        return new CharacterVariableStatisticImpl(Check.ifNull(character, "character"),
                Check.ifNull(type, "type"), Check.ifNull(data, "data"),
                CALCULATION);
    }

    @Override
    public String getInterfaceName() {
        return CharacterEntityOfTypeFactory.class.getCanonicalName() + "<" +
                CharacterVariableStatisticType.class.getCanonicalName() + "," +
                CharacterVariableStatistic.class.getCanonicalName() + ">";
    }
}

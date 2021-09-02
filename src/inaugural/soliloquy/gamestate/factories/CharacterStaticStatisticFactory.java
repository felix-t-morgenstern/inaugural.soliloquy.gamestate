package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.CharacterStaticStatisticImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterStatistic;
import soliloquy.specs.gamestate.factories.EntityMemberOfTypeFactory;
import soliloquy.specs.ruleset.entities.CharacterStaticStatisticType;
import soliloquy.specs.ruleset.gameconcepts.CharacterStatisticCalculation;

public class CharacterStaticStatisticFactory
        implements EntityMemberOfTypeFactory<CharacterStaticStatisticType,
                CharacterStatistic<CharacterStaticStatisticType>, Character> {
    private final VariableCacheFactory DATA_FACTORY;
    private final CharacterStatisticCalculation CALCULATION;

    public CharacterStaticStatisticFactory(VariableCacheFactory dataFactory,
                                           CharacterStatisticCalculation calculation) {
        DATA_FACTORY = Check.ifNull(dataFactory, "dataFactory");
        CALCULATION = Check.ifNull(calculation, "calculation");
    }

    // TODO: Test throws on invalid params
    @Override
    public CharacterStatistic<CharacterStaticStatisticType> make(Character character,
                                                                 CharacterStaticStatisticType type)
            throws IllegalArgumentException {
        return new CharacterStaticStatisticImpl(character, type, DATA_FACTORY.make(), CALCULATION);
    }

    // TODO: Test and implement
    // TODO: Test throws on invalid params
    @Override
    public CharacterStatistic<CharacterStaticStatisticType> make(Character character,
                                                                 CharacterStaticStatisticType type,
                                                                 VariableCache data)
            throws IllegalArgumentException {
        return new CharacterStaticStatisticImpl(character, type, data, CALCULATION);
    }

    @Override
    public String getInterfaceName() {
        return EntityMemberOfTypeFactory.class.getCanonicalName() + "<" +
                CharacterStaticStatisticType.class.getCanonicalName() + "," +
                CharacterStatistic.class.getCanonicalName() + "<" +
                CharacterStaticStatisticType.class.getCanonicalName() + ">>";
    }
}

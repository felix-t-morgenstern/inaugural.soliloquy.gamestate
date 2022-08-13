package inaugural.soliloquy.gamestate.entities;

import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterStatistic;
import soliloquy.specs.ruleset.entities.CharacterStaticStatisticType;
import soliloquy.specs.ruleset.gameconcepts.CharacterStatisticCalculation;

public class CharacterStaticStatisticImpl
        extends AbstractCharacterStatistic<CharacterStaticStatisticType>
        implements CharacterStatistic<CharacterStaticStatisticType> {
    public CharacterStaticStatisticImpl(Character character, CharacterStaticStatisticType type,
                                        VariableCache data,
                                        CharacterStatisticCalculation
                                                characterStatisticCalculation) {
        super(character, type, data, characterStatisticCalculation);
    }

    // Todo: Ensure tested properly
    @Override
    public String getInterfaceName() {
        enforceDeletionInvariants();
        return CharacterStatistic.class.getCanonicalName() + "<" +
                CharacterStaticStatisticType.class.getCanonicalName() + ">";
    }
}

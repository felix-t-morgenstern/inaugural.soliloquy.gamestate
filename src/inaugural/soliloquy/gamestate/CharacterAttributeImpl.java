package inaugural.soliloquy.gamestate;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterAttribute;
import soliloquy.specs.ruleset.entities.AttributeType;
import soliloquy.specs.ruleset.gameconcepts.CharacterStatisticCalculation;

public class CharacterAttributeImpl extends CharacterStatistic<AttributeType> implements CharacterAttribute {
    @SuppressWarnings("ConstantConditions")
    public CharacterAttributeImpl(Character character, AttributeType attributeType,
                                  CharacterStatisticCalculation<AttributeType>
                                   characterStatisticCalculation) {
        super(character, attributeType, characterStatisticCalculation);
    }

    @Override
    protected String className() {
        return "CharacterAttributeImpl";
    }

    @Override
    public AttributeType attributeType() {
        enforceDeletionInvariants("attributeType");
        return ENTITY_TYPE;
    }

    @Override
    public String getInterfaceName() {
        enforceDeletionInvariants("getInterfaceName");
        return CharacterAttribute.class.getCanonicalName();
    }
}

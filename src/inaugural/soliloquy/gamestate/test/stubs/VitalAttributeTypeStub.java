package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.entities.IAction;
import soliloquy.specs.common.valueobjects.IEntityUuid;
import soliloquy.specs.common.valueobjects.IPair;
import soliloquy.specs.game.IGame;
import soliloquy.specs.gamestate.entities.ICharacter;
import soliloquy.specs.logger.ILogger;
import soliloquy.specs.ruleset.entities.IElement;
import soliloquy.specs.ruleset.entities.IVitalAttributeType;
import soliloquy.specs.ruleset.entities.abilities.IAbilitySource;
import soliloquy.specs.sprites.entities.ISprite;

public class VitalAttributeTypeStub implements IVitalAttributeType {
    @Override
    public String getPluralName() {
        return null;
    }

    @Override
    public void setPluralName(String s) throws IllegalArgumentException {

    }

    @Override
    public IAction<ICharacter> onTurnStart() {
        return null;
    }

    @Override
    public IAction<ICharacter> onTurnEnd() {
        return null;
    }

    @Override
    public IAction<ICharacter> onRoundStart(Integer integer) {
        return null;
    }

    @Override
    public IAction<ICharacter> onRoundEnd(Integer integer) {
        return null;
    }

    @Override
    public IGame game() {
        return null;
    }

    @Override
    public ILogger logger() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public IPair<ISprite, Integer> getIcon(String s, IEntityUuid iEntityUuid) {
        return null;
    }

    @Override
    public void alterCurrentValue(ICharacter iCharacter, int i, boolean b, IElement iElement, IAbilitySource iAbilitySource) throws IllegalArgumentException {

    }

    @Override
    public String id() throws IllegalStateException {
        return null;
    }
}

package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.common.specs.IAction;
import soliloquy.common.specs.IEntityUuid;
import soliloquy.common.specs.IPair;
import soliloquy.game.primary.specs.IGame;
import soliloquy.gamestate.specs.ICharacter;
import soliloquy.logger.specs.ILogger;
import soliloquy.ruleset.gameentities.abilities.specs.IAbilitySource;
import soliloquy.ruleset.gameentities.specs.IElement;
import soliloquy.ruleset.gameentities.specs.IVitalAttributeType;
import soliloquy.sprites.specs.ISprite;

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

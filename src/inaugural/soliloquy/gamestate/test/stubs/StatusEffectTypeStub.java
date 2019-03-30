package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.common.specs.IAction;
import soliloquy.common.specs.IEntityUuid;
import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.common.specs.IPair;
import soliloquy.game.primary.specs.IGame;
import soliloquy.gamestate.specs.ICharacter;
import soliloquy.gamestate.specs.IGameZone;
import soliloquy.logger.specs.ILogger;
import soliloquy.ruleset.gameentities.specs.IStatusEffectType;
import soliloquy.sprites.specs.ISprite;

public class StatusEffectTypeStub implements IStatusEffectType {
    @Override
    public IPair<ISprite, Integer> getIcon(String s, IEntityUuid iEntityUuid) {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String s) {

    }

    @Override
    public void read(String s, boolean b) throws IllegalArgumentException {

    }

    @Override
    public String write() throws IllegalArgumentException {
        return null;
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
    public IGameZone getGameZone() throws IllegalStateException {
        return null;
    }

    @Override
    public IGenericParamsSet params() throws IllegalStateException {
        return null;
    }

    @Override
    public void delete() throws IllegalStateException {

    }

    @Override
    public boolean isDeleted() {
        return false;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public boolean stopsAtZero() {
        return false;
    }

    @Override
    public String nameAtValue(int i) throws UnsupportedOperationException {
        return null;
    }
}

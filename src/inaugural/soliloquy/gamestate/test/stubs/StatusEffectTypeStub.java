package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.game.Game;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.logger.Logger;
import soliloquy.specs.ruleset.entities.StatusEffectType;
import soliloquy.specs.sprites.entities.Sprite;

public class StatusEffectTypeStub implements StatusEffectType {
    @Override
    public boolean stopsAtZero() {
        return false;
    }

    @Override
    public String nameAtValue(int i) throws UnsupportedOperationException {
        return null;
    }

    @Override
    public String id() throws IllegalStateException {
        return null;
    }

    @Override
    public Action<Character> onTurnStart() {
        return null;
    }

    @Override
    public Action<Character> onTurnEnd() {
        return null;
    }

    @Override
    public Action<Character> onRoundStart(Integer integer) {
        return null;
    }

    @Override
    public Action<Character> onRoundEnd(Integer integer) {
        return null;
    }

    @Override
    public Game game() {
        return null;
    }

    @Override
    public Logger logger() {
        return null;
    }

    @Override
    public String getInterfaceName() {
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
    public Pair<Sprite, Integer> getIcon(Object o, String s, Character character) {
        return null;
    }
}

package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.ReadablePair;
import soliloquy.specs.game.Game;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.logger.Logger;
import soliloquy.specs.ruleset.entities.StatusEffectType;
import soliloquy.specs.sprites.entities.Sprite;

import java.util.function.BiConsumer;

public class FakeStatusEffectType implements StatusEffectType {
    private final String ID;

    public FakeStatusEffectType() {
        ID = null;
    }

    public FakeStatusEffectType(String id) {
        ID = id;
    }

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
        return ID;
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

    @Override
    public ReadablePair<BiConsumer<Character, Integer>, Integer> onTurnStart() {
        return null;
    }

    @Override
    public ReadablePair<BiConsumer<Character, Integer>, Integer> onTurnEnd() {
        return null;
    }

    @Override
    public ReadablePair<BiConsumer<Character, Integer>, Integer> onRoundEnd() {
        return null;
    }
}

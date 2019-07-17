package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.game.Game;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.logger.Logger;
import soliloquy.specs.ruleset.entities.Element;
import soliloquy.specs.ruleset.entities.StatusEffectType;
import soliloquy.specs.ruleset.entities.abilities.AbilitySource;
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
    public Pair<Sprite, Integer> getIcon(String s, EntityUuid entityUuid) {
        return null;
    }

    @Override
    public void alterCurrentValue(Character character, int i, boolean b, Element element, AbilitySource abilitySource) throws IllegalArgumentException {

    }
}

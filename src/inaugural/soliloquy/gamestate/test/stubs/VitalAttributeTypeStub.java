package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.game.Game;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.logger.Logger;
import soliloquy.specs.ruleset.entities.Element;
import soliloquy.specs.ruleset.entities.VitalAttributeType;
import soliloquy.specs.ruleset.entities.abilities.AbilitySource;
import soliloquy.specs.sprites.entities.Sprite;

public class VitalAttributeTypeStub implements VitalAttributeType {
    @Override
    public String id() throws IllegalStateException {
        return null;
    }

    @Override
    public String getPluralName() {
        return null;
    }

    @Override
    public void setPluralName(String s) throws IllegalArgumentException {

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

package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.game.Game;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.logger.Logger;
import soliloquy.specs.ruleset.entities.CharacterDepletableStatisticType;
import soliloquy.specs.sprites.entities.Sprite;
import soliloquy.specs.sprites.entities.SpriteSet;

public class CharacterDepletableStatisticTypeStub implements CharacterDepletableStatisticType {
    private final String ID;

    public CharacterDepletableStatisticTypeStub(String id) {
        ID = id;
    }

    @Override
    public String id() throws IllegalStateException {
        return ID;
    }

    @Override
    public String getPluralName() {
        return null;
    }

    @Override
    public void setPluralName(String s) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String s) {

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
    public String getDescription() {
        return null;
    }

    @Override
    public void setDescription(String s) {

    }

    @Override
    public SpriteSet getSpriteSet() {
        return null;
    }

    @Override
    public void setSpriteSet(SpriteSet spriteSet) {

    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public Pair<Sprite, Integer> getIcon(Object o, String s, Character character) {
        return null;
    }
}

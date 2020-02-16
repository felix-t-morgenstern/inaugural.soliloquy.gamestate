package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.ReadablePair;
import soliloquy.specs.game.Game;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.logger.Logger;
import soliloquy.specs.ruleset.entities.CharacterDepletableStatisticType;
import soliloquy.specs.sprites.entities.Sprite;
import soliloquy.specs.sprites.entities.SpriteSet;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class CharacterDepletableStatisticTypeStub implements CharacterDepletableStatisticType {
    private final String ID;

    public int _onTurnStartFirePriority;
    public int _onTurnEndFirePriority;
    public int _onRoundEndFirePriority;

    public ArrayList<Character> _onTurnStartCharactersFired = new ArrayList<>();
    public ArrayList<Character> _onTurnEndCharactersFired = new ArrayList<>();
    public ArrayList<Character> _onRoundEndCharactersFired = new ArrayList<>();
    public ArrayList<Integer> _onRoundEndRoundsElapsed = new ArrayList<>();

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
    public ReadablePair<Consumer<Character>, Integer> onTurnStart() {
        return new PairStub<>(c -> _onTurnStartCharactersFired.add(c), _onTurnStartFirePriority);
    }

    @Override
    public ReadablePair<Consumer<Character>, Integer> onTurnEnd() {
        return new PairStub<>(c -> _onTurnEndCharactersFired.add(c), _onTurnEndFirePriority);
    }

    @Override
    public ReadablePair<BiConsumer<Character, Integer>, Integer> onRoundEnd() {
            return new PairStub<>((c,n) -> {
                _onRoundEndCharactersFired.add(c);
                _onRoundEndRoundsElapsed.add(n);
            }, _onRoundEndFirePriority);
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

package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.game.Game;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.graphics.assets.Sprite;
import soliloquy.specs.logger.Logger;
import soliloquy.specs.ruleset.entities.StatusEffectType;

import java.util.function.BiConsumer;

public class StatusEffectTypeArchetype implements StatusEffectType {
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
    public String getName() {
        return null;
    }

    @Override
    public void setName(String s) {

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
        return StatusEffectType.class.getCanonicalName();
    }

    @Override
    public Pair<Sprite, Integer> getIcon(Object o, String s, Character character) {
        return null;
    }

    @Override
    public Pair<BiConsumer<Character, Integer>, Integer> onTurnStart() {
        return null;
    }

    @Override
    public Pair<BiConsumer<Character, Integer>, Integer> onTurnEnd() {
        return null;
    }

    @Override
    public Pair<BiConsumer<Character, Integer>, Integer> onRoundEnd() {
        return null;
    }
}

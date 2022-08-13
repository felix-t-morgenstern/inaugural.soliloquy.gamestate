package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.game.Game;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.graphics.assets.ImageAsset;
import soliloquy.specs.graphics.assets.ImageAssetSet;
import soliloquy.specs.logger.Logger;
import soliloquy.specs.ruleset.entities.CharacterVariableStatisticType;

import java.util.function.BiConsumer;

public class CharacterVariableStatisticTypeArchetype implements CharacterVariableStatisticType {
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
    public String getDescription() {
        return null;
    }

    @Override
    public void setDescription(String s) {

    }

    @Override
    public ImageAssetSet imageAssetSet() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return CharacterVariableStatisticType.class.getCanonicalName();
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

    @Override
    public Pair<ImageAsset, Integer> getIcon(CharacterVariableStatisticType type, String s,
                                             Character character) {
        return null;
    }
}

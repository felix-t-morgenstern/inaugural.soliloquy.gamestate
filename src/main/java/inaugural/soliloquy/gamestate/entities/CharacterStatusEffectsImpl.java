package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterStatusEffects;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.ruleset.entities.character.StatusEffectType;

import java.util.Map;

import static inaugural.soliloquy.tools.collections.Collections.mapOf;

public class CharacterStatusEffectsImpl extends HasDeletionInvariants
        implements CharacterStatusEffects {
    private final Character CHARACTER;
    private final Map<StatusEffectType, Integer> STATUS_EFFECT_LEVELS;

    public CharacterStatusEffectsImpl(Character character) {
        CHARACTER = Check.ifNull(character, "character");
        STATUS_EFFECT_LEVELS = mapOf();
    }

    @Override
    public Integer getStatusEffectLevel(StatusEffectType type) throws IllegalStateException,
            IllegalArgumentException {
        Check.ifNull(type, "type");
        enforceDeletionInvariants();
        return STATUS_EFFECT_LEVELS.getOrDefault(type, 0);
    }

    @Override
    public Map<StatusEffectType, Integer> representation() {
        enforceDeletionInvariants();
        return mapOf(STATUS_EFFECT_LEVELS);
    }

    @Override
    public void setStatusEffectLevel(StatusEffectType type, int level)
            throws IllegalStateException {
        enforceDeletionInvariants();
        Check.ifNull(type, "type");
        if (level == 0) {
            STATUS_EFFECT_LEVELS.remove(type);
        }
        else {
            STATUS_EFFECT_LEVELS.put(type, level);
        }
    }

    @Override
    public void clearStatusEffects() throws IllegalStateException {
        enforceDeletionInvariants();
        STATUS_EFFECT_LEVELS.clear();
    }

    @Override
    protected String containingClassName() {
        return "Character";
    }

    @Override
    protected Deletable getContainingObject() {
        return CHARACTER;
    }
}

package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterStatusEffects;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.ruleset.entities.character.StatusEffectType;

import java.util.HashMap;
import java.util.Map;

public class CharacterStatusEffectsImpl extends HasDeletionInvariants
        implements CharacterStatusEffects {
    private final Character CHARACTER;
    private final HashMap<StatusEffectType, Integer> STATUS_EFFECT_LEVELS;

    public CharacterStatusEffectsImpl(Character character) {
        CHARACTER = Check.ifNull(character, "character");
        STATUS_EFFECT_LEVELS = new HashMap<>();
    }

    @Override
    public Integer getStatusEffectLevel(StatusEffectType type) throws IllegalStateException,
            IllegalArgumentException {
        Check.ifNull(type, "type");
        enforceInvariants("getStatusEffectLevel");
        return STATUS_EFFECT_LEVELS.getOrDefault(type, 0);
    }

    @Override
    public Map<StatusEffectType, Integer> representation() {
        enforceInvariants("getAllStatusEffects");
        return new HashMap<>(STATUS_EFFECT_LEVELS);
    }

    @Override
    public void setStatusEffectLevel(StatusEffectType type, int level)
            throws IllegalStateException {
        enforceInvariants("alterStatusEffect");
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
        enforceInvariants("alterStatusEffect");
        STATUS_EFFECT_LEVELS.clear();
    }

    @Override
    public String getInterfaceName() {
        enforceInvariants("alterStatusEffect");
        return CharacterStatusEffects.class.getCanonicalName();
    }

    @Override
    protected String containingClassName() {
        return "Character";
    }

    @Override
    protected Deletable getContainingObject() {
        return CHARACTER;
    }

    private void enforceInvariants(String methodName) {
        if (CHARACTER == null) {
            throw new IllegalStateException("CharacterStatusEffects." + methodName +
                    ": character is null");
        }
        enforceDeletionInvariants();
    }
}

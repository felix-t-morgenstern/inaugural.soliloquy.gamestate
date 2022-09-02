package inaugural.soliloquy.gamestate.entities;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterStatusEffects;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.gamestate.entities.abilities.AbilitySource;
import soliloquy.specs.ruleset.entities.Element;
import soliloquy.specs.ruleset.entities.StatusEffectType;
import soliloquy.specs.ruleset.gameconcepts.StatusEffectResistanceCalculation;

import java.util.HashMap;
import java.util.Map;

public class CharacterStatusEffectsImpl extends HasDeletionInvariants
        implements CharacterStatusEffects {
    private final Character CHARACTER;
    private final StatusEffectResistanceCalculation RESISTANCE_CALCULATION;
    private final HashMap<StatusEffectType, Integer> STATUS_EFFECT_LEVELS;

    public CharacterStatusEffectsImpl(Character character,
                                      StatusEffectResistanceCalculation resistanceCalculation) {
        CHARACTER = character;
        RESISTANCE_CALCULATION = resistanceCalculation;
        STATUS_EFFECT_LEVELS = new HashMap<>();
    }

    @Override
    public Integer getStatusEffectLevel(StatusEffectType type) throws IllegalStateException,
            IllegalArgumentException {
        if (type == null) {
            throw new IllegalArgumentException(
                    "CharacterStatusEffects.getStatusEffectLevel: type cannot be null");
        }
        enforceInvariants("getStatusEffectLevel");
        return STATUS_EFFECT_LEVELS.getOrDefault(type, 0);
    }

    @Override
    public Map<StatusEffectType, Integer> representation() {
        enforceInvariants("getAllStatusEffects");
        return new HashMap<>(STATUS_EFFECT_LEVELS);
    }

    @Override
    public void alterStatusEffect(StatusEffectType type, int baseAmount, boolean stopAtZero,
                                  Element element, AbilitySource abilitySource)
            throws IllegalStateException, IllegalArgumentException {
        enforceInvariants("alterStatusEffect");
        if (type == null) {
            throw new IllegalArgumentException(
                    "CharacterStatusEffects.alterStatusEffect: type cannot be null");
        }
        if (element == null) {
            throw new IllegalArgumentException(
                    "CharacterStatusEffects.alterStatusEffect: element cannot be null");
        }
        int effectiveChange = RESISTANCE_CALCULATION.calculateEffectiveChange(CHARACTER,
                type, baseAmount, stopAtZero, element, abilitySource);
        int currentLevel = getStatusEffectLevel(type);
        if (effectiveChange == -currentLevel) {
            STATUS_EFFECT_LEVELS.remove(type);
        }
        else {
            STATUS_EFFECT_LEVELS.put(type, currentLevel + effectiveChange);
        }
    }

    @Override
    public void setStatusEffectLevel(StatusEffectType type, int level)
            throws IllegalStateException {
        enforceInvariants("alterStatusEffect");
        if (type == null) {
            throw new IllegalArgumentException(
                    "CharacterStatusEffects.setStatusEffectLevel: type cannot be null");
        }
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

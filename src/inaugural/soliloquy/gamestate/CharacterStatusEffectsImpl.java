package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.ReadOnlyMap;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterStatusEffects;
import soliloquy.specs.ruleset.entities.Element;
import soliloquy.specs.ruleset.entities.StatusEffectType;
import soliloquy.specs.ruleset.entities.abilities.AbilitySource;
import soliloquy.specs.ruleset.gameconcepts.ResistanceCalculation;

import java.util.HashMap;

public class CharacterStatusEffectsImpl extends HasDeletionInvariants
        implements CharacterStatusEffects {
    private final Map<String, StatusEffectType> STATUS_EFFECT_TYPES;
    private final Character CHARACTER;
    private final MapFactory MAP_FACTORY;
    private final ResistanceCalculation RESISTANCE_CALCULATION;
    private final HashMap<String,Integer> STATUS_EFFECT_LEVELS;

    public CharacterStatusEffectsImpl(Character character,
                                      Map<String, StatusEffectType> statusEffectTypes,
                                      MapFactory mapFactory,
                                      ResistanceCalculation resistanceCalculation){
        STATUS_EFFECT_TYPES = statusEffectTypes;
        CHARACTER = character;
        MAP_FACTORY = mapFactory;
        RESISTANCE_CALCULATION = resistanceCalculation;
        STATUS_EFFECT_LEVELS = new HashMap<>();
    }

    @Override
    public Integer getStatusEffectLevel(String statusEffectTypeId) throws IllegalStateException,
            IllegalArgumentException {
        if (!STATUS_EFFECT_TYPES.containsKey(statusEffectTypeId)) {
            throw new IllegalArgumentException(
                    "CharacterStatusEffects.getStatusEffectLevel: statusEffectTypeId is not a valid value");
        }
        enforceInvariants("getStatusEffectLevel");
        if (CHARACTER.getDead()) {
            throw new IllegalStateException("CharacterStatusEffects.setStatusEffectLevel: character is dead");
        }
        return STATUS_EFFECT_LEVELS.getOrDefault(statusEffectTypeId, 0);
    }

    @Override
    public ReadOnlyMap<String, Integer> allStatusEffectsRepresentation() {
        enforceInvariants("getAllStatusEffects");
        Map<String, Integer> statusEffectLevels = MAP_FACTORY.make("", 0);
        for (java.util.Map.Entry<String, Integer> statusEffectLevel : STATUS_EFFECT_LEVELS.entrySet()) {
            statusEffectLevels.put(statusEffectLevel.getKey(), statusEffectLevel.getValue());
        }
        return statusEffectLevels.readOnlyRepresentation();
    }

    @Override
    public void alterStatusEffect(String statusEffectTypeId, int baseAmount, boolean stopAtZero,
                                  Element element, AbilitySource abilitySource)
            throws IllegalStateException, IllegalArgumentException {
        enforceInvariants("alterStatusEffect");
        if (CHARACTER.getDead()) {
            throw new IllegalStateException(
                    "CharacterStatusEffects.alterStatusEffect: character is dead");
        }
        if (statusEffectTypeId == null) {
            throw new IllegalArgumentException(
                    "CharacterStatusEffects.alterStatusEffect: statusEffectTypeId cannot be null");
        }
        if (statusEffectTypeId.equals("")) {
            throw new IllegalArgumentException(
                    "CharacterStatusEffects.alterStatusEffect: statusEffectTypeId cannot be empty");
        }
        if (element == null) {
            throw new IllegalArgumentException(
                    "CharacterStatusEffects.alterStatusEffect: element cannot be null");
        }
        StatusEffectType statusEffectType = STATUS_EFFECT_TYPES.get(statusEffectTypeId);
        if (statusEffectType == null) {
            throw new IllegalArgumentException(
                    "CharacterStatusEffects.alterStatusEffect: statusEffectTypeId does not correspond to a valid StatusEffectType");
        }
        int effectiveChange = RESISTANCE_CALCULATION.calculateEffectiveChange(CHARACTER,
                statusEffectType, baseAmount, stopAtZero, element, abilitySource);
        STATUS_EFFECT_LEVELS.put(statusEffectTypeId,
                getStatusEffectLevel(statusEffectTypeId) + effectiveChange);
    }

    @Override
    public void setStatusEffectLevel(String statusEffectTypeId, int level)
            throws IllegalStateException {
        if (!STATUS_EFFECT_TYPES.containsKey(statusEffectTypeId)) {
            throw new IllegalArgumentException(
                    "CharacterStatusEffects.setStatusEffectLevel: statusEffectTypeId is not a valid value");
        }
        enforceInvariants("alterStatusEffect");
        if (CHARACTER.getDead()) {
            throw new IllegalStateException("CharacterStatusEffects.setStatusEffectLevel: character is dead");
        }
        if (level == 0) {
            STATUS_EFFECT_LEVELS.remove(statusEffectTypeId);
        } else {
            STATUS_EFFECT_LEVELS.put(statusEffectTypeId, level);
        }
    }

    @Override
    public void clearStatusEffects() throws IllegalStateException {
        enforceInvariants("alterStatusEffect");
        if (CHARACTER.getDead()) {
            throw new IllegalStateException("CharacterStatusEffects.setStatusEffectLevel: character is dead");
        }
        STATUS_EFFECT_LEVELS.clear();
    }

    @Override
    public String getInterfaceName() {
        enforceInvariants("alterStatusEffect");
        return CharacterStatusEffects.class.getCanonicalName();
    }

    @Override
    public void delete() throws IllegalStateException {
        enforceInvariants("delete");
        _isDeleted = true;
    }

    @Override
    protected String className() {
        return "CharacterStatusEffects";
    }

    @Override
    protected String containingClassName() {
        return "Character";
    }

    @Override
    protected boolean containingObjectIsDeleted() {
        return CHARACTER.isDeleted();
    }

    private void enforceInvariants(String methodName) {
        if (CHARACTER == null) {
            throw new IllegalStateException("CharacterStatusEffects." + methodName +
                    ": character is null");
        }
        enforceDeletionInvariants(methodName);
    }
}

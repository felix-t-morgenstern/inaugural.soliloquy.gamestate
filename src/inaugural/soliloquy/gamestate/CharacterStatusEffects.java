package inaugural.soliloquy.gamestate;

import soliloquy.common.specs.IMap;
import soliloquy.common.specs.IMapFactory;
import soliloquy.gamestate.specs.ICharacter;
import soliloquy.gamestate.specs.ICharacterStatusEffects;
import soliloquy.ruleset.gameconcepts.specs.IResistanceCalculation;
import soliloquy.ruleset.gameentities.abilities.specs.IAbilitySource;
import soliloquy.ruleset.gameentities.specs.IElement;
import soliloquy.ruleset.gameentities.specs.IStatusEffectType;

import java.util.HashMap;
import java.util.Map;

public class CharacterStatusEffects implements ICharacterStatusEffects {
    private final IMap<String,IStatusEffectType> STATUS_EFFECT_TYPES;
    private final ICharacter CHARACTER;
    private final IMapFactory MAP_FACTORY;
    private final IResistanceCalculation RESISTANCE_CALCULATION;
    private final HashMap<String,Integer> STATUS_EFFECT_LEVELS;

    public CharacterStatusEffects(IMap<String, IStatusEffectType> statusEffectTypes,
                                  ICharacter character, IMapFactory mapFactory,
                                  IResistanceCalculation resistanceCalculation){
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
        if (CHARACTER == null) {
            throw new IllegalStateException("CharacterStatusEffects.getStatusEffectLevel: character is null");
        }
        if (CHARACTER.getDead()) {
            throw new IllegalStateException("CharacterStatusEffects.getStatusEffectLevel: character is dead");
        }
        if (CHARACTER.isDeleted()) {
            throw new IllegalStateException("CharacterStatusEffects.getStatusEffectLevel: character is deleted");
        }
        return STATUS_EFFECT_LEVELS.getOrDefault(statusEffectTypeId, 0);
    }

    @Override
    public IMap<String, Integer> getAllStatusEffects() {
        IMap<String, Integer> statusEffectLevels = MAP_FACTORY.make("", 0);
        for (Map.Entry<String, Integer> statusEffectLevel : STATUS_EFFECT_LEVELS.entrySet()) {
            statusEffectLevels.put(statusEffectLevel.getKey(), statusEffectLevel.getValue());
        }
        return statusEffectLevels;
    }

    @Override
    public void alterStatusEffect(String statusEffectTypeId, int baseAmount, boolean stopAtZero,
                                  IElement element, IAbilitySource abilitySource)
            throws IllegalStateException, IllegalArgumentException {
        if (CHARACTER.getDead()) {
            throw new IllegalStateException(
                    "CharacterStatusEffects.alterStatusEffect: character is dead");
        }
        if (CHARACTER.isDeleted()) {
            throw new IllegalStateException(
                    "CharacterStatusEffects.alterStatusEffect: character is deleted");
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
        IStatusEffectType statusEffectType = STATUS_EFFECT_TYPES.get(statusEffectTypeId);
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
    public void setStatusEffectLevel(String statusEffectTypeId, int level) throws IllegalStateException {
        if (!STATUS_EFFECT_TYPES.containsKey(statusEffectTypeId)) {
            throw new IllegalArgumentException(
                    "CharacterStatusEffects.setStatusEffectLevel: statusEffectTypeId is not a valid value");
        }
        if (CHARACTER == null) {
            throw new IllegalStateException("CharacterStatusEffects.setStatusEffectLevel: character is null");
        }
        if (CHARACTER.getDead()) {
            throw new IllegalStateException("CharacterStatusEffects.setStatusEffectLevel: character is dead");
        }
        if (CHARACTER.isDeleted()) {
            throw new IllegalStateException("CharacterStatusEffects.setStatusEffectLevel: character is deleted");
        }
        if (level == 0) {
            STATUS_EFFECT_LEVELS.remove(statusEffectTypeId);
        } else {
            STATUS_EFFECT_LEVELS.put(statusEffectTypeId, level);
        }
    }

    @Override
    public void clearStatusEffects() throws IllegalStateException {
        if (CHARACTER == null) {
            throw new IllegalStateException("CharacterStatusEffects.setStatusEffectLevel: character is null");
        }
        if (CHARACTER.getDead()) {
            throw new IllegalStateException("CharacterStatusEffects.setStatusEffectLevel: character is dead");
        }
        if (CHARACTER.isDeleted()) {
            throw new IllegalStateException("CharacterStatusEffects.setStatusEffectLevel: character is deleted");
        }
        STATUS_EFFECT_LEVELS.clear();
    }

    @Override
    public String getInterfaceName() {
        return ICharacterStatusEffects.class.getCanonicalName();
    }

    @Override
    public void read(String s, boolean b) throws IllegalArgumentException {

    }

    @Override
    public String write() throws IllegalArgumentException {
        return null;
    }
}

package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.graphics.assets.ImageAssetSet;
import soliloquy.specs.ruleset.entities.CharacterAIType;
import soliloquy.specs.ruleset.entities.CharacterStaticStatisticType;
import soliloquy.specs.ruleset.entities.CharacterType;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbility;
import soliloquy.specs.ruleset.entities.abilities.PassiveAbility;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbility;
import soliloquy.specs.ruleset.valueobjects.CharacterClassification;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CharacterArchetype implements Character {
    @Override
    public String getInterfaceName() {
        return Character.class.getCanonicalName();
    }

    @Override
    public CharacterType type() throws EntityDeletedException {
        return null;
    }

    @Override
    public List<CharacterClassification> classifications() throws EntityDeletedException {
        return null;
    }

    @Override
    public Map<String, String> pronouns() throws EntityDeletedException {
        return null;
    }

    @Override
    public VariableCache data() throws EntityDeletedException {
        return null;
    }

    @Override
    public String getStance() throws EntityDeletedException {
        return null;
    }

    @Override
    public void setStance(String s) throws EntityDeletedException {

    }

    @Override
    public String getDirection() throws EntityDeletedException {
        return null;
    }

    @Override
    public void setDirection(String s) throws IllegalArgumentException, EntityDeletedException {

    }

    @Override
    public ImageAssetSet getImageAssetSet() throws EntityDeletedException {
        return null;
    }

    @Override
    public void setImageAssetSet(ImageAssetSet imageAssetSet)
            throws IllegalArgumentException, EntityDeletedException {

    }

    @Override
    public CharacterAIType getAIType() throws EntityDeletedException {
        return null;
    }

    @Override
    public void setAIType(CharacterAIType characterAIType)
            throws IllegalArgumentException, EntityDeletedException {

    }

    @Override
    public CharacterEvents events() {
        return null;
    }

    @Override
    public CharacterEquipmentSlots equipmentSlots() throws EntityDeletedException {
        return null;
    }

    @Override
    public CharacterInventory inventory() throws EntityDeletedException {
        return null;
    }

    @Override
    public CharacterVariableStatistics variableStatistics() throws EntityDeletedException {
        return null;
    }

    @Override
    public EntityMembersOfType<CharacterStaticStatisticType,
            CharacterStatistic<CharacterStaticStatisticType>, Character> staticStatistics()
            throws EntityDeletedException {
        return null;
    }

    @Override
    public CharacterStatusEffects statusEffects() throws EntityDeletedException {
        return null;
    }

    @Override
    public List<ActiveAbility> activeAbilities() throws EntityDeletedException {
        return null;
    }

    @Override
    public List<PassiveAbility> passiveAbilities() throws EntityDeletedException {
        return null;
    }

    @Override
    public List<ReactiveAbility> reactiveAbilities() throws EntityDeletedException {
        return null;
    }

    @Override
    public boolean getPlayerControlled() throws EntityDeletedException {
        return false;
    }

    @Override
    public void setPlayerControlled(boolean b) throws EntityDeletedException {

    }

    @Override
    public void delete() throws EntityDeletedException {

    }

    @Override
    public boolean isDeleted() {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String s) {

    }

    @Override
    public UUID uuid() {
        return null;
    }

    @Override
    public Tile tile() throws EntityDeletedException {
        return null;
    }

    @Override
    public void assignTileAfterAddedToTileEntitiesOfType(Tile tile)
            throws IllegalArgumentException, EntityDeletedException {

    }
}

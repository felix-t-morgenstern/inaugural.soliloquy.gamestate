package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.graphics.assets.SpriteSet;
import soliloquy.specs.ruleset.entities.CharacterAIType;
import soliloquy.specs.ruleset.entities.CharacterStaticStatisticType;
import soliloquy.specs.ruleset.entities.CharacterType;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbilityType;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbilityType;
import soliloquy.specs.ruleset.valueobjects.CharacterClassification;

public class CharacterArchetype implements Character {

    @Override
    public String getInterfaceName() {
        return Character.class.getCanonicalName();
    }

    @Override
    public CharacterType type() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<CharacterClassification> classifications() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<String, String> pronouns() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public VariableCache data() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Tile tile() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getStance() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setStance(String s) throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getDirection() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDirection(String s) throws IllegalArgumentException, IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public SpriteSet getSpriteSet() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setSpriteSet(SpriteSet spriteSet) throws IllegalArgumentException, IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public CharacterAIType getAIType() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setAIType(CharacterAIType characterAIType) throws IllegalArgumentException, IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public CharacterEvents events() {
        throw new UnsupportedOperationException();
    }

    @Override
    public CharacterEquipmentSlots equipmentSlots() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public CharacterInventory inventory() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public CharacterVariableStatistics variableStatistics() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public CharacterEntitiesOfType<CharacterStaticStatisticType, CharacterStatistic<CharacterStaticStatisticType>> staticStatistics() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public CharacterStatusEffects statusEffects() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public CharacterEntitiesOfType<ActiveAbilityType, CharacterEntityOfType<ActiveAbilityType>> activeAbilities() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public CharacterEntitiesOfType<ReactiveAbilityType, CharacterEntityOfType<ReactiveAbilityType>> reactiveAbilities() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getPlayerControlled() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPlayerControlled(boolean b) throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isDeleted() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void assignTileAfterAddedToTileEntitiesOfType(Tile tile) throws IllegalArgumentException, IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setName(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public EntityUuid id() {
        throw new UnsupportedOperationException();
    }
}

package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.gameevents.GameEvent;
import soliloquy.specs.ruleset.entities.CharacterAIType;
import soliloquy.specs.ruleset.entities.CharacterType;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbilityType;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbilityType;
import soliloquy.specs.ruleset.valueobjects.CharacterClassification;
import soliloquy.specs.sprites.entities.SpriteSet;

public class CharacterArchetype implements Character {

    @Override
    public String getInterfaceName() {
        return "soliloquy.specs.gamestate.ICharacter";
    }

    @Override
    public CharacterType characterType() throws IllegalStateException {
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
    public GenericParamsSet data() throws IllegalStateException {
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
    public Map<String, Collection<GameEvent>> events() {
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
    public Map<String, CharacterVitalAttribute> vitalAttributes() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<String, CharacterAttribute> attributes() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public CharacterStatusEffects statusEffects() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<String, CharacterAbility<ActiveAbilityType>> activeAbilities() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<String, CharacterAbility<ReactiveAbilityType>> reactiveAbilities() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<String, CharacterAptitude> aptitudes() throws IllegalStateException {
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
    public boolean getHidden() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setHidden(boolean b) throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getDead() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDead(boolean b) throws IllegalStateException {
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
    public void assignToTileAfterAddingToTileCharacters(Tile tile) throws IllegalArgumentException, IllegalStateException {
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

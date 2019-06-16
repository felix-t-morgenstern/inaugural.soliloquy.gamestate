package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.valueobjects.ICollection;
import soliloquy.specs.common.valueobjects.IEntityUuid;
import soliloquy.specs.common.valueobjects.IGenericParamsSet;
import soliloquy.specs.common.valueobjects.IMap;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.ruleset.entities.ICharacterAIType;
import soliloquy.specs.ruleset.entities.ICharacterType;
import soliloquy.specs.ruleset.entities.abilities.IActiveAbilityType;
import soliloquy.specs.ruleset.entities.abilities.IReactiveAbilityType;
import soliloquy.specs.ruleset.valueobjects.ICharacterClassification;
import soliloquy.specs.sprites.entities.ISpriteSet;

public class CharacterArchetype implements ICharacter {
    @Override
    public ICharacterType characterType() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ICollection<ICharacterClassification> classifications() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public IMap<String, String> pronouns() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ITile tile() throws IllegalStateException {
        return null;
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
    public ISpriteSet getSpriteSet() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setSpriteSet(ISpriteSet iSpriteSet) throws IllegalArgumentException, IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ICharacterAIType getAIType() throws IllegalStateException {
        return null;
    }

    @Override
    public void setAIType(ICharacterAIType iCharacterAIType) throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public IMap<String, ICollection<ICharacterEvent>> events() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ICharacterEquipmentSlots equipmentSlots() throws IllegalStateException {
        return null;
    }

    @Override
    public ICharacterInventory inventory() throws IllegalStateException {
        return null;
    }

    @Override
    public IMap<String, ICharacterVitalAttribute> vitalAttributes() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public IMap<String, ICharacterAttribute> attributes() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ICharacterStatusEffects statusEffects() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public IMap<String, ICharacterAbility<IActiveAbilityType>> activeAbilities()
            throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public IMap<String, ICharacterAbility<IReactiveAbilityType>> reactiveAbilities()
            throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public IMap<String, ICharacterAptitude> aptitudes() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getPlayerControlled() throws IllegalStateException {
        return false;
    }

    @Override
    public void setPlayerControlled(boolean b) throws IllegalStateException {

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
        return false;
    }

    @Override
    public void setDead(boolean b) throws IllegalStateException {

    }

    @Override
    public IGenericParamsSet data() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void assignToTile(ITile iTile) throws IllegalArgumentException, IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isDeleted() {
        throw new UnsupportedOperationException();
    }

    @Override
    public IEntityUuid id() {
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
    public String getInterfaceName() {
        return "soliloquy.specs.gamestate.ICharacter";
    }
}

package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameCharacterEvent;
import soliloquy.specs.ruleset.entities.CharacterAIType;
import soliloquy.specs.ruleset.entities.CharacterType;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbilityType;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbilityType;
import soliloquy.specs.ruleset.valueobjects.CharacterClassification;
import soliloquy.specs.sprites.entities.SpriteSet;

public class CharacterStub implements Character {
    private boolean _isDeleted;
    private boolean _isDead;

    public Tile _tile;

    public final CharacterEquipmentSlotsStub EQUIPMENT = new CharacterEquipmentSlotsStub(this);
    public final CharacterInventoryStub INVENTORY = new CharacterInventoryStub(this);

    @Override
    public CharacterType characterType() throws IllegalStateException {
        return null;
    }

    @Override
    public Collection<CharacterClassification> classifications() throws IllegalStateException {
        return null;
    }

    @Override
    public Map<String, String> pronouns() throws IllegalStateException {
        return null;
    }

    @Override
    public Tile tile() throws IllegalStateException {
        return _tile;
    }

    @Override
    public String getStance() throws IllegalStateException {
        return null;
    }

    @Override
    public void setStance(String s) throws IllegalStateException {

    }

    @Override
    public String getDirection() throws IllegalStateException {
        return null;
    }

    @Override
    public void setDirection(String s) throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public SpriteSet getSpriteSet() throws IllegalStateException {
        return null;
    }

    @Override
    public void setSpriteSet(SpriteSet spriteSet) throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public CharacterAIType getAIType() throws IllegalStateException {
        return null;
    }

    @Override
    public void setAIType(CharacterAIType characterAIType) throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public Map<String, Collection<GameCharacterEvent>> events() {
        return null;
    }

    @Override
    public CharacterEquipmentSlots equipmentSlots() throws IllegalStateException {
        return EQUIPMENT;
    }

    @Override
    public CharacterInventory inventory() throws IllegalStateException {
        return INVENTORY;
    }

    @Override
    public Map<String, CharacterDepletableStatistic> depletableStatistics() throws IllegalStateException {
        return null;
    }

    @Override
    public Map<String, CharacterStatistic> statistics() throws IllegalStateException {
        return null;
    }

    @Override
    public CharacterStatusEffects statusEffects() throws IllegalStateException {
        return null;
    }

    @Override
    public Map<String, CharacterAbility<ActiveAbilityType>> activeAbilities()
            throws IllegalStateException {
        return null;
    }

    @Override
    public Map<String, CharacterAbility<ReactiveAbilityType>> reactiveAbilities()
            throws IllegalStateException {
        return null;
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
        return false;
    }

    @Override
    public void setHidden(boolean b) throws IllegalStateException {

    }

    @Override
    public boolean getDead() throws IllegalStateException {
        return _isDead;
    }

    @Override
    public void setDead(boolean b) throws IllegalStateException {
        _isDead = b;
    }

    @Override
    public GenericParamsSet data() throws IllegalStateException {
        return null;
    }

    @Override
    public void delete() throws IllegalStateException {
        _isDeleted = true;
    }

    @Override
    public void assignToTileAfterAddingToTileCharacters(Tile tile) throws IllegalArgumentException, IllegalStateException {
        _tile = tile;
    }

    @Override
    public boolean isDeleted() {
        return _isDeleted;
    }

    @Override
    public EntityUuid id() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String s) {

    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

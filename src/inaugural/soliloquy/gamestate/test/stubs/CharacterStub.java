package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.IEntityUuid;
import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.common.specs.IMap;
import soliloquy.gamestate.specs.*;
import soliloquy.ruleset.gameentities.specs.ICharacterEvent;
import soliloquy.ruleset.gameentities.specs.ICharacterClassification;
import soliloquy.ruleset.gameentities.specs.ICharacterType;
import soliloquy.sprites.specs.ISpriteSet;

public class CharacterStub implements ICharacter {
    private boolean _isDeleted;
    private boolean _isDead;
    private ITile _tile;

    @Override
    public ICharacterType characterType() throws IllegalStateException {
        return null;
    }

    @Override
    public ICollection<ICharacterClassification> classifications() throws IllegalStateException {
        return null;
    }

    @Override
    public ITile getTile() throws IllegalStateException {
        return _tile;
    }

    @Override
    public void setTile(ITile tile) throws IllegalArgumentException, IllegalStateException {
        _tile = tile;
    }

    @Override
    public void setTile(ITile iTile, int i) throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public IMap<String, String> pronouns() throws IllegalStateException {
        return null;
    }

    @Override
    public IGenericParamsSet traits() throws IllegalStateException {
        return null;
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
    public ISpriteSet getSpriteSet() throws IllegalStateException {
        return null;
    }

    @Override
    public void setSpriteSet(ISpriteSet iSpriteSet) throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public String getAITypeId() throws IllegalStateException {
        return null;
    }

    @Override
    public void setAITypeId(String characterAITypeId) throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public IGenericParamsSet characterAIParams() throws IllegalStateException {
        return null;
    }

    @Override
    public IMap<String, ICollection<ICharacterEvent>> characterEvents() {
        return null;
    }

    @Override
    public IMap<String, ICharacterEquipmentSlot> equipment() throws IllegalStateException {
        return null;
    }

    @Override
    public IMap<Integer, IItem> inventory() throws IllegalStateException {
        return null;
    }

    @Override
    public IMap<String, ICharacterVitalAttribute> vitalAttributes() throws IllegalStateException {
        return null;
    }

    @Override
    public IMap<String, ICharacterAttribute> attributes() throws IllegalStateException {
        return null;
    }

    @Override
    public ICharacterStatusEffects statusEffects() throws IllegalStateException {
        return null;
    }

    @Override
    public IMap<String, ICharacterAbility> activeAbilities() throws IllegalStateException {
        return null;
    }

    @Override
    public IMap<String, ICharacterAbility> reactiveAbilities() throws IllegalStateException {
        return null;
    }

    @Override
    public IMap<String, ICharacterAptitude> aptitudes() throws IllegalStateException {
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
    public IGameZone gameZone() throws IllegalStateException {
        return null;
    }

    @Override
    public IGenericParamsSet data() throws IllegalStateException {
        return null;
    }

    @Override
    public void delete() throws IllegalStateException {
        _isDeleted = true;
    }

    @Override
    public boolean isDeleted() {
        return _isDeleted;
    }

    @Override
    public IEntityUuid id() {
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
    public void read(String s, boolean b) throws IllegalArgumentException {

    }

    @Override
    public String write() throws IllegalArgumentException {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

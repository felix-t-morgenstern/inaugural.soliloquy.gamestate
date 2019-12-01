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
    private String _stance;
    private String _direction;
    private SpriteSet _spriteSet;
    private CharacterAIType _aiType;

    private final EntityUuid ID;
    private final CharacterType TYPE;
    private final Collection<CharacterClassification> CLASSIFICATIONS = new CollectionStub<>();
    private final Map<String,String> PRONOUNS = new MapStub<>();
    // TODO: Implement a stub class for CharacterEvents!!!
    // TODO: Implement a stub class for CharacterEvents!!!
    // TODO: Implement a stub class for CharacterEvents!!!
    // TODO: Implement a stub class for CharacterEvents!!!
    // TODO: Implement a stub class for CharacterEvents!!!
    private final CharacterEvents EVENTS = null;

    public Tile _tile;

    public final CharacterEquipmentSlotsStub EQUIPMENT = new CharacterEquipmentSlotsStub(this);
    public final CharacterInventoryStub INVENTORY = new CharacterInventoryStub(this);

    public CharacterStub() {
        ID = null;
        TYPE = null;
    }

    public CharacterStub(EntityUuid id, CharacterType type) {
        ID = id;
        TYPE = type;
    }

    @Override
    public CharacterType characterType() throws IllegalStateException {
        return TYPE;
    }

    @Override
    public Collection<CharacterClassification> classifications() throws IllegalStateException {
        return CLASSIFICATIONS;
    }

    @Override
    public Map<String, String> pronouns() throws IllegalStateException {
        return PRONOUNS;
    }

    @Override
    public Tile tile() throws IllegalStateException {
        return _tile;
    }

    @Override
    public String getStance() throws IllegalStateException {
        return _stance;
    }

    @Override
    public void setStance(String stance) throws IllegalStateException {
        _stance = stance;
    }

    @Override
    public String getDirection() throws IllegalStateException {
        return _direction;
    }

    @Override
    public void setDirection(String direction) throws IllegalArgumentException, IllegalStateException {
        _direction = direction;
    }

    @Override
    public SpriteSet getSpriteSet() throws IllegalStateException {
        return _spriteSet;
    }

    @Override
    public void setSpriteSet(SpriteSet spriteSet) throws IllegalArgumentException, IllegalStateException {
        _spriteSet = spriteSet;
    }

    @Override
    public CharacterAIType getAIType() throws IllegalStateException {
        return _aiType;
    }

    @Override
    public void setAIType(CharacterAIType aiType) throws IllegalArgumentException, IllegalStateException {
        _aiType = aiType;
    }

    @Override
    public CharacterEvents events() {
        return EVENTS;
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
        return ID;
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

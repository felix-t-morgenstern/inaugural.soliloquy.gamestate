package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.ruleset.entities.CharacterAIType;
import soliloquy.specs.ruleset.entities.CharacterStaticStatisticType;
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
    private boolean _playerControlled;
    private String _name;
    private VariableCache _data;

    private final EntityUuid ID;
    private final CharacterType TYPE;
    private final Collection<CharacterClassification> CLASSIFICATIONS = new CollectionStub<>();
    private final Map<String,String> PRONOUNS = new MapStub<>();
    private final CharacterEvents EVENTS = new CharacterEventsStub(this);
    private final CharacterVariableStatistics VARIABLE_STATS;
    private final CharacterEntitiesOfType<CharacterStaticStatisticType,
            CharacterStatistic<CharacterStaticStatisticType>>
            STATIC_STATS = new CharacterEntitiesOfTypeStub<>(this, c -> t -> d ->
            new CharacterStaticStatisticStub(c, t, d));
    private final CharacterStatusEffects STATUS_EFFECTS = new CharacterStatusEffectsStub();
    private final CharacterEntitiesOfType<ActiveAbilityType, CharacterEntityOfType<ActiveAbilityType>>
            ACTIVE_ABILITIES = new CharacterEntitiesOfTypeStub<>(this, c -> t -> d ->
            new CharacterAbilityStub<>(c, t, d));
    private final CharacterEntitiesOfType<ReactiveAbilityType,
            CharacterEntityOfType<ReactiveAbilityType>> REACTIVE_ABILITIES =
            new CharacterEntitiesOfTypeStub<>(this, c -> t -> d ->
                    new CharacterAbilityStub<>(c, t, d));

    public Tile _tile;

    public final CharacterEquipmentSlotsStub EQUIPMENT = new CharacterEquipmentSlotsStub(this);
    public final CharacterInventoryStub INVENTORY = new CharacterInventoryStub(this);

    public CharacterStub() {
        ID = null;
        TYPE = null;
        VARIABLE_STATS = new CharacterVariableStatisticsStub(this);
    }

    public CharacterStub(EntityUuid id, CharacterType type, VariableCache data) {
        ID = id;
        TYPE = type;
        _data = data;
        VARIABLE_STATS = new CharacterVariableStatisticsStub(this);
    }

    @Override
    public CharacterType type() throws IllegalStateException {
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
    public CharacterVariableStatistics variableStatistics() throws IllegalStateException {
        return VARIABLE_STATS;
    }

    @Override
    public CharacterEntitiesOfType<CharacterStaticStatisticType,
            CharacterStatistic<CharacterStaticStatisticType>>
        staticStatistics() throws IllegalStateException {
        return STATIC_STATS;
    }

    @Override
    public CharacterStatusEffects statusEffects() throws IllegalStateException {
        return STATUS_EFFECTS;
    }

    @Override
    public CharacterEntitiesOfType<ActiveAbilityType, CharacterEntityOfType<ActiveAbilityType>>
            activeAbilities() throws IllegalStateException {
        return ACTIVE_ABILITIES;
    }

    @Override
    public CharacterEntitiesOfType<ReactiveAbilityType, CharacterEntityOfType<ReactiveAbilityType>>
            reactiveAbilities() throws IllegalStateException {
        return REACTIVE_ABILITIES;
    }

    @Override
    public boolean getPlayerControlled() throws IllegalStateException {
        return _playerControlled;
    }

    @Override
    public void setPlayerControlled(boolean b) throws IllegalStateException {
        _playerControlled = b;
    }

    @Override
    public VariableCache data() throws IllegalStateException {
        return _data;
    }

    @Override
    public void delete() throws IllegalStateException {
        _isDeleted = true;
    }

    @Override
    public void assignTileAfterAddedToTileEntitiesOfType(Tile tile) throws IllegalArgumentException, IllegalStateException {
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
        return _name;
    }

    @Override
    public void setName(String name) {
        _name = name;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

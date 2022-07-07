package inaugural.soliloquy.gamestate.test.fakes;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FakeCharacter implements Character {
    private boolean _isDeleted;
    private String _stance;
    private String _direction;
    private ImageAssetSet _imageAssetSet;
    private CharacterAIType _aiType;
    private boolean _playerControlled;
    private String _name;
    private VariableCache _data;

    private final UUID UUID;
    private final CharacterType TYPE;
    private final List<CharacterClassification> CLASSIFICATIONS = new FakeList<>();
    private final Map<String,String> PRONOUNS = new FakeMap<>();
    private final CharacterEvents EVENTS = new FakeCharacterEvents(this);
    private final CharacterVariableStatistics VARIABLE_STATS;
    private final EntityMembersOfType<CharacterStaticStatisticType,
            CharacterStatistic<CharacterStaticStatisticType>, Character>
            STATIC_STATS = new FakeEntityMembersOfType<>(this, c -> t -> d ->
            new FakeCharacterStaticStatistic(t, d));
    private final CharacterStatusEffects STATUS_EFFECTS = new FakeCharacterStatusEffects();
    private final List<PassiveAbility> PASSIVE_ABILITIES = new ArrayList<>();
    private final List<ActiveAbility> ACTIVE_ABILITIES = new ArrayList<>();
    private final List<ReactiveAbility> REACTIVE_ABILITIES = new ArrayList<>();

    public Tile _tile;

    public final FakeCharacterEquipmentSlots EQUIPMENT = new FakeCharacterEquipmentSlots(this);
    public final FakeCharacterInventory INVENTORY = new FakeCharacterInventory(this);

    public FakeCharacter() {
        UUID = null;
        TYPE = null;
        VARIABLE_STATS = new FakeCharacterVariableStatistics(this);
    }

    public FakeCharacter(UUID uuid, CharacterType type, VariableCache data) {
        UUID = uuid;
        TYPE = type;
        _data = data;
        VARIABLE_STATS = new FakeCharacterVariableStatistics(this);
    }

    @Override
    public CharacterType type() throws IllegalStateException {
        return TYPE;
    }

    @Override
    public List<CharacterClassification> classifications() throws IllegalStateException {
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
    public ImageAssetSet getImageAssetSet() throws IllegalStateException {
        return _imageAssetSet;
    }

    @Override
    public void setImageAssetSet(ImageAssetSet imageAssetSet) throws IllegalArgumentException, IllegalStateException {
        _imageAssetSet = imageAssetSet;
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
    public EntityMembersOfType<CharacterStaticStatisticType,
            CharacterStatistic<CharacterStaticStatisticType>, Character>
        staticStatistics() throws IllegalStateException {
        return STATIC_STATS;
    }

    @Override
    public CharacterStatusEffects statusEffects() throws IllegalStateException {
        return STATUS_EFFECTS;
    }

    @Override
    public List<PassiveAbility> passiveAbilities() throws EntityDeletedException {
        return PASSIVE_ABILITIES;
    }

    @Override
    public List<ActiveAbility> activeAbilities() throws IllegalStateException {
        return ACTIVE_ABILITIES;
    }

    @Override
    public List<ReactiveAbility> reactiveAbilities() throws IllegalStateException {
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
    public UUID uuid() {
        return UUID;
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

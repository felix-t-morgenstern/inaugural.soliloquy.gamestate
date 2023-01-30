package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.shared.Direction;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.graphics.assets.ImageAssetSet;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbility;
import soliloquy.specs.ruleset.entities.abilities.PassiveAbility;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbility;
import soliloquy.specs.ruleset.entities.character.CharacterAIType;
import soliloquy.specs.ruleset.entities.character.CharacterType;
import soliloquy.specs.ruleset.entities.character.CharacterVariableStatisticType;
import soliloquy.specs.ruleset.valueobjects.CharacterClassification;

import java.util.*;

public class FakeCharacter implements Character {
    private boolean _isDeleted;
    private String _stance;
    private Direction _direction;
    private ImageAssetSet _imageAssetSet;
    private CharacterAIType _aiType;
    private boolean _playerControlled;
    private String _name;
    private VariableCache _data;

    private final UUID UUID;
    private final CharacterType TYPE;
    private final List<CharacterClassification> CLASSIFICATIONS = new ArrayList<>();
    private final Map<String, String> PRONOUNS = new HashMap<>();
    private final List<PassiveAbility> PASSIVE_ABILITIES = new ArrayList<>();
    private final List<ActiveAbility> ACTIVE_ABILITIES = new ArrayList<>();
    private final List<ReactiveAbility> REACTIVE_ABILITIES = new ArrayList<>();

    public Tile _tile;

    public final FakeCharacterEquipmentSlots EQUIPMENT = new FakeCharacterEquipmentSlots(this);
    public final FakeCharacterInventory INVENTORY = new FakeCharacterInventory(this);

    public FakeCharacter() {
        UUID = null;
        TYPE = null;
    }

    public FakeCharacter(UUID uuid, CharacterType type, VariableCache data) {
        UUID = uuid;
        TYPE = type;
        _data = data;
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
    public Direction getDirection() throws IllegalStateException {
        return _direction;
    }

    @Override
    public void setDirection(Direction direction)
            throws IllegalArgumentException, IllegalStateException {
        _direction = direction;
    }

    @Override
    public ImageAssetSet getImageAssetSet() throws IllegalStateException {
        return _imageAssetSet;
    }

    @Override
    public void setImageAssetSet(ImageAssetSet imageAssetSet)
            throws IllegalArgumentException, IllegalStateException {
        _imageAssetSet = imageAssetSet;
    }

    @Override
    public CharacterAIType getAIType() throws IllegalStateException {
        return _aiType;
    }

    @Override
    public void setAIType(CharacterAIType aiType)
            throws IllegalArgumentException, IllegalStateException {
        _aiType = aiType;
    }

    @Override
    public CharacterEvents events() {
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
    public int getVariableStatisticCurrentValue(
            CharacterVariableStatisticType characterVariableStatisticType)
            throws IllegalArgumentException, EntityDeletedException {
        return 0;
    }

    @Override
    public void setVariableStatisticCurrentValue(
            CharacterVariableStatisticType characterVariableStatisticType, int i)
            throws IllegalArgumentException, EntityDeletedException {

    }

    @Override
    public Map<CharacterVariableStatisticType, Integer> variableStatisticCurrentValuesRepresentation()
            throws EntityDeletedException {
        return null;
    }

    @Override
    public CharacterStatusEffects statusEffects() throws IllegalStateException {
        return null;
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
    public void assignTileAfterAddedToTileEntitiesOfType(Tile tile)
            throws IllegalArgumentException, IllegalStateException {
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

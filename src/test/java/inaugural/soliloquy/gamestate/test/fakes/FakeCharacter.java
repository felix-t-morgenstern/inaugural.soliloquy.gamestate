package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.shared.Direction;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.io.graphics.assets.ImageAssetSet;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbility;
import soliloquy.specs.ruleset.entities.abilities.PassiveAbility;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbility;
import soliloquy.specs.ruleset.entities.character.CharacterAIType;
import soliloquy.specs.ruleset.entities.character.CharacterType;
import soliloquy.specs.ruleset.entities.character.VariableStatisticType;
import soliloquy.specs.ruleset.valueobjects.CharacterClassification;

import java.util.*;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.collections.Collections.mapOf;

public class FakeCharacter implements Character {
    private boolean isDeleted;
    private String stance;
    private Direction direction;
    private ImageAssetSet imageAssetSet;
    private CharacterAIType aiType;
    private boolean playerControlled;
    private String name;

    private final UUID UUID;
    private final CharacterType TYPE;
    private final List<CharacterClassification> CLASSIFICATIONS = listOf();
    private final Map<String, String> PRONOUNS = mapOf();
    private final List<PassiveAbility> PASSIVE_ABILITIES = listOf();
    private final List<ActiveAbility> ACTIVE_ABILITIES = listOf();
    private final List<ReactiveAbility> REACTIVE_ABILITIES = listOf();

    public Tile _tile;

    public final FakeCharacterEquipmentSlots EQUIPMENT = new FakeCharacterEquipmentSlots(this);
    public final FakeCharacterInventory INVENTORY = new FakeCharacterInventory(this);

    public FakeCharacter() {
        UUID = null;
        TYPE = null;
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
        return stance;
    }

    @Override
    public void setStance(String stance) throws IllegalStateException {
        this.stance = stance;
    }

    @Override
    public Direction getDirection() throws IllegalStateException {
        return direction;
    }

    @Override
    public void setDirection(Direction direction)
            throws IllegalArgumentException, IllegalStateException {
        this.direction = direction;
    }

    @Override
    public ImageAssetSet getImageAssetSet() throws IllegalStateException {
        return imageAssetSet;
    }

    @Override
    public void setImageAssetSet(ImageAssetSet imageAssetSet)
            throws IllegalArgumentException, IllegalStateException {
        this.imageAssetSet = imageAssetSet;
    }

    @Override
    public CharacterAIType getAIType() throws IllegalStateException {
        return aiType;
    }

    @Override
    public void setAIType(CharacterAIType aiType)
            throws IllegalArgumentException, IllegalStateException {
        this.aiType = aiType;
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
            VariableStatisticType characterVariableStatisticType)
            throws IllegalArgumentException, EntityDeletedException {
        return 0;
    }

    @Override
    public void setVariableStatisticCurrentValue(
            VariableStatisticType characterVariableStatisticType, int i)
            throws IllegalArgumentException, EntityDeletedException {

    }

    @Override
    public Map<VariableStatisticType, Integer> variableStatisticCurrentValuesRepresentation()
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
        return playerControlled;
    }

    @Override
    public void setPlayerControlled(boolean b) throws IllegalStateException {
        playerControlled = b;
    }

    @Override
    public Map<String, Object> data() throws IllegalStateException {
        return null;
    }

    @Override
    public void delete() throws IllegalStateException {
        isDeleted = true;
    }

    @Override
    public void assignTileAfterAddedToTileEntitiesOfType(Tile tile)
            throws IllegalArgumentException, IllegalStateException {
        _tile = tile;
    }

    @Override
    public boolean isDeleted() {
        return isDeleted;
    }

    @Override
    public UUID uuid() {
        return UUID;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}

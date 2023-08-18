package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.shared.Direction;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.gamestate.factories.CharacterEquipmentSlotsFactory;
import soliloquy.specs.gamestate.factories.CharacterEventsFactory;
import soliloquy.specs.gamestate.factories.CharacterInventoryFactory;
import soliloquy.specs.gamestate.factories.CharacterStatusEffectsFactory;
import soliloquy.specs.graphics.assets.ImageAssetSet;
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

public class CharacterImpl implements Character {
    private final UUID UUID;
    private final CharacterType CHARACTER_TYPE;
    private final List<CharacterClassification> CHARACTER_CLASSIFICATIONS;
    private final Map<String, String> PRONOUNS;
    private final CharacterEvents EVENTS;
    private final CharacterEquipmentSlots EQUIPMENT_SLOTS;
    private final CharacterInventory INVENTORY;
    private final Map<VariableStatisticType, Integer> VARIABLE_STATISTIC_CURRENT_VALUES;
    private final CharacterStatusEffects STATUS_EFFECTS;
    private final List<PassiveAbility> PASSIVE_ABILITIES;
    private final List<ActiveAbility> ACTIVE_ABILITIES;
    private final List<ReactiveAbility> REACTIVE_ABILITIES;
    private final VariableCache DATA;

    private Tile tile;
    private String stance;
    private Direction direction;
    private ImageAssetSet imageAssetSet;
    private boolean playerControlled;
    private boolean deleted;
    private String name;
    private CharacterAIType aiType;

    public CharacterImpl(UUID uuid,
                         CharacterType characterType,
                         CharacterEventsFactory characterEventsFactory,
                         CharacterEquipmentSlotsFactory equipmentSlotsFactory,
                         CharacterInventoryFactory inventoryFactory,
                         CharacterStatusEffectsFactory statusEffectsFactory,
                         VariableCache data) {
        UUID = Check.ifNull(uuid, "uuid");
        CHARACTER_TYPE = Check.ifNull(characterType, "characterType");
        CHARACTER_CLASSIFICATIONS = listOf();
        PRONOUNS = mapOf();
        EVENTS = Check.ifNull(characterEventsFactory, "characterEventsFactory").make(this);
        EQUIPMENT_SLOTS = Check.ifNull(equipmentSlotsFactory, "equipmentSlotsFactory").make(this);
        INVENTORY = Check.ifNull(inventoryFactory, "inventoryFactory").make(this);
        VARIABLE_STATISTIC_CURRENT_VALUES = mapOf();
        STATUS_EFFECTS = Check.ifNull(statusEffectsFactory, "statusEffectsFactory").make(this);
        PASSIVE_ABILITIES = listOf();
        ACTIVE_ABILITIES = listOf();
        REACTIVE_ABILITIES = listOf();
        DATA = Check.ifNull(data, "data");
    }

    @Override
    public CharacterType type() throws IllegalStateException {
        enforceInvariant("characterType", true);
        return CHARACTER_TYPE;
    }

    @Override
    public List<CharacterClassification> classifications() throws IllegalStateException {
        enforceInvariant("classifications", true);
        return CHARACTER_CLASSIFICATIONS;
    }

    @Override
    public Map<String, String> pronouns() throws IllegalStateException {
        enforceInvariant("pronouns", true);
        return PRONOUNS;
    }

    @Override
    public Tile tile() throws IllegalStateException {
        enforceInvariant("tile", true);
        return tile;
    }

    @Override
    public String getStance() throws IllegalStateException {
        enforceInvariant("getStance", true);
        return stance;
    }

    @Override
    public void setStance(String stance) throws IllegalStateException {
        enforceInvariant("setStance", true);
        this.stance = stance;
    }

    @Override
    public Direction getDirection() throws IllegalStateException {
        enforceInvariant("getDirection", true);
        return direction;
    }

    @Override
    public void setDirection(Direction direction)
            throws IllegalArgumentException, IllegalStateException {
        enforceInvariant("setDirection", true);
        this.direction = direction;
    }

    @Override
    public ImageAssetSet getImageAssetSet() throws IllegalStateException {
        enforceInvariant("getImageAssetSet", true);
        return imageAssetSet;
    }

    @Override
    public void setImageAssetSet(ImageAssetSet imageAssetSet)
            throws IllegalArgumentException, IllegalStateException {
        enforceInvariant("setImageAssetSet", true);
        this.imageAssetSet = Check.ifNull(imageAssetSet, "imageAssetSet");
    }

    @Override
    public CharacterAIType getAIType() throws IllegalStateException {
        enforceInvariant("getAIType", true);
        return aiType;
    }

    @Override
    public void setAIType(CharacterAIType aiType)
            throws IllegalArgumentException, IllegalStateException {
        enforceInvariant("setAIType", true);
        this.aiType = Check.ifNull(aiType, "aiType");
    }

    @Override
    public CharacterEvents events() {
        enforceInvariant("characterEvents", true);
        return EVENTS;
    }

    @Override
    public CharacterEquipmentSlots equipmentSlots() throws IllegalStateException {
        enforceInvariant("equipmentSlots", true);
        return EQUIPMENT_SLOTS;
    }

    @Override
    public CharacterInventory inventory() throws IllegalStateException {
        enforceInvariant("inventory", true);
        return INVENTORY;
    }

    @Override
    public int getVariableStatisticCurrentValue(VariableStatisticType variableStatType)
            throws IllegalArgumentException, EntityDeletedException {
        enforceInvariant("getVariableStatisticCurrentValue", true);
        Check.ifNull(variableStatType, "variableStatType");
        return VARIABLE_STATISTIC_CURRENT_VALUES.getOrDefault(variableStatType, 0);
    }

    @Override
    public void setVariableStatisticCurrentValue(VariableStatisticType variableStatType,
                                                 int value)
            throws IllegalArgumentException, EntityDeletedException {
        enforceInvariant("setVariableStatisticCurrentValue", true);
        Check.ifNull(variableStatType, "variableStatType");
        VARIABLE_STATISTIC_CURRENT_VALUES.put(variableStatType, value);
    }

    @Override
    public Map<VariableStatisticType, Integer> variableStatisticCurrentValuesRepresentation()
            throws EntityDeletedException {
        enforceInvariant("variableStatisticCurrentValuesRepresentation", true);
        return mapOf(VARIABLE_STATISTIC_CURRENT_VALUES);
    }

    @Override
    public CharacterStatusEffects statusEffects() throws IllegalStateException {
        enforceInvariant("statusEffects", true);
        return STATUS_EFFECTS;
    }

    @Override
    public List<PassiveAbility> passiveAbilities() throws EntityDeletedException {
        enforceInvariant("passiveAbilities", true);
        return PASSIVE_ABILITIES;
    }

    @Override
    public List<ActiveAbility> activeAbilities() throws IllegalStateException {
        enforceInvariant("activeAbilities", true);
        return ACTIVE_ABILITIES;
    }

    @Override
    public List<ReactiveAbility> reactiveAbilities() throws IllegalStateException {
        enforceInvariant("reactiveAbilities", true);
        return REACTIVE_ABILITIES;
    }

    @Override
    public boolean getPlayerControlled() throws IllegalStateException {
        enforceInvariant("getPlayerControlled", true);
        return playerControlled;
    }

    @Override
    public void setPlayerControlled(boolean playerControlled) throws IllegalStateException {
        enforceInvariant("setPlayerControlled", true);
        this.playerControlled = playerControlled;
    }

    @Override
    public VariableCache data() throws IllegalStateException {
        enforceInvariant("data", true);
        return DATA;
    }

    @Override
    public void delete() throws IllegalStateException {
        enforceInvariant("delete", false);
        // delete should remove the Character from its Tile, via its TileCharacters, which will
        // handle removal from the GameZone.
        deleted = true;
        if (tile != null) {
            tile.characters().remove(this);
        }
        tile = null;
        EQUIPMENT_SLOTS.delete();
        INVENTORY.delete();
        STATUS_EFFECTS.delete();
    }

    @Override
    public void assignTileAfterAddedToTileEntitiesOfType(Tile tile)
            throws IllegalArgumentException, IllegalStateException {
        enforceInvariant("assignTileAfterAddedToTileEntitiesOfType", true);
        this.tile = tile;
        enforceInvariant("assignTileAfterAddedToTileEntitiesOfType", true);
    }

    @Override
    public boolean isDeleted() {
        enforceInvariant("isDeleted", false);
        return deleted;
    }

    @Override
    public String getName() {
        enforceInvariant("getName", true);
        return name;
    }

    @Override
    public void setName(String name) {
        enforceInvariant("setName", true);
        this.name = Check.ifNullOrEmpty(name, "name");
    }

    @Override
    public UUID uuid() {
        return UUID;
    }

    @Override
    public String getInterfaceName() {
        enforceInvariant("getInterfaceName", true);
        return Character.class.getCanonicalName();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Character character)) {
            return false;
        }
        if (character.isDeleted()) {
            return false;
        }
        return character.uuid().equals(UUID);
    }

    private void enforceInvariant(String methodName, boolean cannotBeDeleted) {
        if (cannotBeDeleted && deleted) {
            throw new EntityDeletedException(
                    "CharacterImpl." + methodName + ": Character is deleted");
        }
        if (tile != null && !tile.characters().contains(this)) {
            throw new IllegalStateException("CharacterImpl." + methodName +
                    ": Character is not present on its specified Tile");
        }
    }
}

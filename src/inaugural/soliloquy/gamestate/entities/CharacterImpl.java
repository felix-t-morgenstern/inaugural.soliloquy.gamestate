package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.gamestate.archetypes.CharacterActiveAbilityArchetype;
import inaugural.soliloquy.gamestate.archetypes.CharacterClassificationArchetype;
import inaugural.soliloquy.gamestate.archetypes.CharacterReactiveAbilityArchetype;
import inaugural.soliloquy.gamestate.archetypes.CharacterStaticStatisticArchetype;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.gamestate.factories.*;
import soliloquy.specs.graphics.assets.SpriteSet;
import soliloquy.specs.ruleset.entities.CharacterAIType;
import soliloquy.specs.ruleset.entities.CharacterStaticStatisticType;
import soliloquy.specs.ruleset.entities.CharacterType;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbilityType;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbilityType;
import soliloquy.specs.ruleset.valueobjects.CharacterClassification;

public class CharacterImpl implements Character {
    private final EntityUuid ID;
    private final CharacterType CHARACTER_TYPE;
    private final List<CharacterClassification> CHARACTER_CLASSIFICATIONS;
    private final Map<String,String> PRONOUNS;
    private final CharacterEvents EVENTS;
    private final CharacterEquipmentSlots EQUIPMENT_SLOTS;
    private final CharacterInventory INVENTORY;
    private final CharacterVariableStatistics VARIABLE_STATISTICS;
    private final CharacterEntitiesOfType<CharacterStaticStatisticType,
            CharacterStatistic<CharacterStaticStatisticType>> STATIC_STATISTICS;
    private final CharacterStatusEffects STATUS_EFFECTS;
    private final CharacterEntitiesOfType<ActiveAbilityType,
            CharacterEntityOfType<ActiveAbilityType>> ACTIVE_ABILITIES;
    private final CharacterEntitiesOfType<ReactiveAbilityType,
            CharacterEntityOfType<ReactiveAbilityType>> REACTIVE_ABILITIES;
    private final VariableCache DATA;

    private final static CharacterEntityOfType<CharacterStaticStatisticType> STATIC_STAT_ARCHETYPE =
            new CharacterStaticStatisticArchetype();
    private final static CharacterEntityOfType<ActiveAbilityType> ACTIVE_ABILITY_ARCHETYPE =
            new CharacterActiveAbilityArchetype();
    private final static CharacterEntityOfType<ReactiveAbilityType> REACTIVE_ABILITY_ARCHETYPE =
            new CharacterReactiveAbilityArchetype();

    private Tile _tile;
    private String _stance;
    private String _direction;
    private SpriteSet _spriteSet;
    private boolean _playerControlled;
    private boolean _deleted;
    private String _name;
    private CharacterAIType _aiType;

    @SuppressWarnings("ConstantConditions")
    public CharacterImpl(EntityUuid id,
                         CharacterType characterType,
                         ListFactory listFactory,
                         MapFactory mapFactory,
                         CharacterEventsFactory characterEventsFactory,
                         CharacterEquipmentSlotsFactory equipmentSlotsFactory,
                         CharacterInventoryFactory inventoryFactory,
                         CharacterVariableStatisticsFactory variableStatsFactory,
                         CharacterEntitiesOfTypeFactory entitiesOfTypeFactory,
                         CharacterStatusEffectsFactory statusEffectsFactory,
                         VariableCache data) {
        ID = Check.ifNull(id, "id");
        CHARACTER_TYPE = Check.ifNull(characterType, "characterType");
        CHARACTER_CLASSIFICATIONS = Check.ifNull(listFactory, "listFactory")
                .make(new CharacterClassificationArchetype());
        PRONOUNS = Check.ifNull(mapFactory, "mapFactory").make("","");
        EVENTS = Check.ifNull(characterEventsFactory, "characterEventsFactory").make(this);
        EQUIPMENT_SLOTS = Check.ifNull(equipmentSlotsFactory, "equipmentSlotsFactory").make(this);
        INVENTORY = Check.ifNull(inventoryFactory, "inventoryFactory").make(this);
        VARIABLE_STATISTICS = Check.ifNull(variableStatsFactory, "variableStatsFactory")
                .make(this);
        //noinspection unchecked
        STATIC_STATISTICS = Check.ifNull(entitiesOfTypeFactory, "entitiesOfTypeFactory")
                .make(this, STATIC_STAT_ARCHETYPE);
        STATUS_EFFECTS = Check.ifNull(statusEffectsFactory, "statusEffectsFactory").make(this);
        //noinspection unchecked
        ACTIVE_ABILITIES = entitiesOfTypeFactory.make(this, ACTIVE_ABILITY_ARCHETYPE);
        //noinspection unchecked
        REACTIVE_ABILITIES = entitiesOfTypeFactory.make(this, REACTIVE_ABILITY_ARCHETYPE);
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
        return _tile;
    }

    @Override
    public String getStance() throws IllegalStateException {
        enforceInvariant("getStance", true);
        return _stance;
    }

    @Override
    public void setStance(String stance) throws IllegalStateException {
        enforceInvariant("setStance", true);
        _stance = stance;
    }

    @Override
    public String getDirection() throws IllegalStateException {
        enforceInvariant("getDirection", true);
        return _direction;
    }

    @Override
    public void setDirection(String direction)
            throws IllegalArgumentException, IllegalStateException {
        enforceInvariant("setDirection", true);
        _direction = direction;
    }

    @Override
    public SpriteSet getSpriteSet() throws IllegalStateException {
        enforceInvariant("getSpriteSet", true);
        return _spriteSet;
    }

    @Override
    public void setSpriteSet(SpriteSet spriteSet)
            throws IllegalArgumentException, IllegalStateException {
        enforceInvariant("setSpriteSet", true);
        _spriteSet = spriteSet;
    }

    @Override
    public CharacterAIType getAIType() throws IllegalStateException {
        enforceInvariant("getAIType", true);
        return _aiType;
    }

    @Override
    public void setAIType(CharacterAIType characterAIType)
            throws IllegalArgumentException, IllegalStateException {
        enforceInvariant("setAIType", true);
        if (characterAIType == null) {
            throw new IllegalArgumentException(
                    "Character.setAIType: characterAIType cannot be null");
        }
        _aiType = characterAIType;
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
    public CharacterVariableStatistics variableStatistics() throws IllegalStateException {
        enforceInvariant("variableStatistics", true);
        return VARIABLE_STATISTICS;
    }

    @Override
    public CharacterEntitiesOfType<CharacterStaticStatisticType,
            CharacterStatistic<CharacterStaticStatisticType>> staticStatistics()
            throws IllegalStateException {
        enforceInvariant("staticStatistics", true);
        return STATIC_STATISTICS;
    }

    @Override
    public CharacterStatusEffects statusEffects() throws IllegalStateException {
        enforceInvariant("statusEffects", true);
        return STATUS_EFFECTS;
    }

    @Override
    public CharacterEntitiesOfType<ActiveAbilityType,
            CharacterEntityOfType<ActiveAbilityType>> activeAbilities()
            throws IllegalStateException {
        enforceInvariant("activeAbilities", true);
        return ACTIVE_ABILITIES;
    }

    @Override
    public CharacterEntitiesOfType<ReactiveAbilityType,
            CharacterEntityOfType<ReactiveAbilityType>> reactiveAbilities()
            throws IllegalStateException {
        enforceInvariant("reactiveAbilities", true);
        return REACTIVE_ABILITIES;
    }

    @Override
    public boolean getPlayerControlled() throws IllegalStateException {
        enforceInvariant("getPlayerControlled", true);
        return _playerControlled;
    }

    @Override
    public void setPlayerControlled(boolean playerControlled) throws IllegalStateException {
        enforceInvariant("setPlayerControlled", true);
        _playerControlled = playerControlled;
    }

    @Override
    public VariableCache data() throws IllegalStateException {
        enforceInvariant("data", true);
        return DATA;
    }

    @Override
    public void delete() throws IllegalStateException {
        enforceInvariant("characterType", false);
        // delete should remove the Character from its Tile, via its TileCharacters, which will
        // handle removal from the GameZone.
        _deleted = true;
        if (_tile != null) {
            _tile.characters().remove(this);
        }
        _tile = null;
        EQUIPMENT_SLOTS.delete();
        INVENTORY.delete();
        VARIABLE_STATISTICS.delete();
        STATIC_STATISTICS.delete();
        STATUS_EFFECTS.delete();
        ACTIVE_ABILITIES.delete();
        REACTIVE_ABILITIES.delete();
    }

    @Override
    public void assignTileAfterAddedToTileEntitiesOfType(Tile tile)
            throws IllegalArgumentException, IllegalStateException {
        enforceInvariant("assignTileAfterAddedToTileEntitiesOfType", true);
        _tile = tile;
        enforceInvariant("assignTileAfterAddedToTileEntitiesOfType", true);
    }

    @Override
    public boolean isDeleted() {
        enforceInvariant("characterType", false);
        return _deleted;
    }

    @Override
    public String getName() {
        enforceInvariant("getName", true);
        return _name;
    }

    @Override
    public void setName(String name) {
        enforceInvariant("setName", true);
        _name = name;
    }

    @Override
    public EntityUuid id() {
        return ID;
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
        if (!(o instanceof Character)) {
            return false;
        }
        Character character = (Character) o;
        if (character.isDeleted())
        {
            return false;
        }
        return character.id().equals(ID);
    }

    private void enforceInvariant(String methodName, boolean cannotBeDeleted) {
        if (cannotBeDeleted && _deleted) {
            throw new EntityDeletedException("CharacterImpl." + methodName +
                    ": Character is deleted");
        }
        if (_tile != null && !_tile.characters().contains(this)) {
            throw new IllegalStateException("CharacterImpl." + methodName +
                    ": Character is not present on its specified Tile");
        }
    }
}

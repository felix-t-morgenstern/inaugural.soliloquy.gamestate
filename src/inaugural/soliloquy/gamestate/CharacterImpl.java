package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.CharacterActiveAbilityArchetype;
import inaugural.soliloquy.gamestate.archetypes.CharacterClassificationArchetype;
import inaugural.soliloquy.gamestate.archetypes.CharacterReactiveAbilityArchetype;
import inaugural.soliloquy.gamestate.archetypes.CharacterStaticStatisticArchetype;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.factories.*;
import soliloquy.specs.ruleset.entities.CharacterAIType;
import soliloquy.specs.ruleset.entities.CharacterStaticStatisticType;
import soliloquy.specs.ruleset.entities.CharacterType;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbilityType;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbilityType;
import soliloquy.specs.ruleset.valueobjects.CharacterClassification;
import soliloquy.specs.sprites.entities.SpriteSet;

public class CharacterImpl implements Character {
    private final EntityUuid ID;
    private final CharacterType CHARACTER_TYPE;
    private final Collection<CharacterClassification> CHARACTER_CLASSIFICATIONS;
    private final Map<String,String> PRONOUNS;
    private final CharacterEvents EVENTS;
    private final CharacterEquipmentSlots EQUIPMENT_SLOTS;
    private final CharacterInventory INVENTORY;
    private final CharacterDepletableStatistics DEPLETABLE_STATISTICS;
    private final CharacterEntitiesOfType<CharacterStaticStatisticType, CharacterStaticStatistic>
            STATIC_STATISTICS;
    private final CharacterStatusEffects STATUS_EFFECTS;
    private final CharacterEntitiesOfType<ActiveAbilityType, CharacterAbility<ActiveAbilityType>>
            ACTIVE_ABILITIES;
    private final CharacterEntitiesOfType<ReactiveAbilityType,
            CharacterAbility<ReactiveAbilityType>> REACTIVE_ABILITIES;
    private final GenericParamsSet DATA;

    private final static CharacterStaticStatistic STATIC_STAT_ARCHETYPE =
            new CharacterStaticStatisticArchetype();
    private final static CharacterAbility<ActiveAbilityType> ACTIVE_ABILITY_ARCHETYPE =
            new CharacterActiveAbilityArchetype();
    private final static CharacterAbility<ReactiveAbilityType> REACTIVE_ABILITY_ARCHETYPE =
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
                         CollectionFactory collectionFactory,
                         MapFactory mapFactory,
                         CharacterEventsFactory characterEventsFactory,
                         CharacterEquipmentSlotsFactory equipmentSlotsFactory,
                         CharacterInventoryFactory inventoryFactory,
                         CharacterDepletableStatisticsFactory depletableStatsFactory,
                         CharacterEntitiesOfTypeFactory entitiesOfTypeFactory,
                         CharacterStatusEffectsFactory statusEffectsFactory,
                         GenericParamsSet data) {
        if (id == null) {
            throw new IllegalArgumentException("Character: id must be non-null");
        }
        ID = id;
        if (characterType == null) {
            throw new IllegalArgumentException("Character: characterType must be non-null");
        }
        CHARACTER_TYPE = characterType;
        if (collectionFactory == null) {
            throw new IllegalArgumentException("Character: collectionFactory must be non-null");
        }
        CHARACTER_CLASSIFICATIONS = collectionFactory.make(new CharacterClassificationArchetype());
        if (mapFactory == null) {
            throw new IllegalArgumentException("Character: mapFactory must be non-null");
        }
        PRONOUNS = mapFactory.make("","");
        if (characterEventsFactory == null) {
            throw new IllegalArgumentException(
                    "Character: characterEventsFactory must be non-null");
        }
        EVENTS = characterEventsFactory.make(this);
        if (equipmentSlotsFactory == null) {
            throw new IllegalArgumentException(
                    "Character: equipmentSlotsFactory must be non-null");
        }
        EQUIPMENT_SLOTS = equipmentSlotsFactory.make(this);
        if (inventoryFactory == null) {
            throw new IllegalArgumentException("Character: inventoryFactory must be non-null");
        }
        INVENTORY = inventoryFactory.make(this);
        if (depletableStatsFactory == null) {
            throw new IllegalArgumentException(
                    "Character: depletableStatsFactory must be non-null");
        }
        DEPLETABLE_STATISTICS = depletableStatsFactory.make(this);
        if (entitiesOfTypeFactory == null) {
            throw new IllegalArgumentException(
                    "Character: entitiesOfTypeFactory must be non-null");
        }
        STATIC_STATISTICS = entitiesOfTypeFactory.make(this, STATIC_STAT_ARCHETYPE);
        if (statusEffectsFactory == null) {
            throw new IllegalArgumentException("Character: statusEffectsFactory must be non-null");
        }
        STATUS_EFFECTS = statusEffectsFactory.make(this);
        ACTIVE_ABILITIES = entitiesOfTypeFactory.make(this, ACTIVE_ABILITY_ARCHETYPE);
        REACTIVE_ABILITIES = entitiesOfTypeFactory.make(this, REACTIVE_ABILITY_ARCHETYPE);
        if (data == null) {
            throw new IllegalArgumentException("Character: data must be non-null");
        }
        DATA = data;
    }

    @Override
    public CharacterType type() throws IllegalStateException {
        enforceInvariant("characterType", true);
        return CHARACTER_TYPE;
    }

    @Override
    public Collection<CharacterClassification> classifications() throws IllegalStateException {
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
    public CharacterDepletableStatistics depletableStatistics() throws IllegalStateException {
        enforceInvariant("depletableStatistics", true);
        return DEPLETABLE_STATISTICS;
    }

    @Override
    public CharacterEntitiesOfType<CharacterStaticStatisticType,
            CharacterStaticStatistic> staticStatistics() throws IllegalStateException {
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
            CharacterAbility<ActiveAbilityType>> activeAbilities() throws IllegalStateException {
        enforceInvariant("activeAbilities", true);
        return ACTIVE_ABILITIES;
    }

    @Override
    public CharacterEntitiesOfType<ReactiveAbilityType,
            CharacterAbility<ReactiveAbilityType>> reactiveAbilities() throws IllegalStateException {
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
    public GenericParamsSet data() throws IllegalStateException {
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
        DEPLETABLE_STATISTICS.delete();
        STATIC_STATISTICS.delete();
        STATUS_EFFECTS.delete();
        ACTIVE_ABILITIES.delete();
        REACTIVE_ABILITIES.delete();
    }

    @Override
    public void assignToTileAfterAddingToTileCharacters(Tile tile)
            throws IllegalArgumentException, IllegalStateException {
        enforceInvariant("assignToTileAfterAddingToTileCharacters", true);
        _tile = tile;
        enforceInvariant("assignToTileAfterAddingToTileCharacters", true);
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
        enforceInvariant("id", true);
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
            throw new IllegalStateException("CharacterImpl." + methodName +
                    ": Character is deleted");
        }
        if (_tile != null && !_tile.characters().contains(this)) {
            throw new IllegalStateException("CharacterImpl." + methodName +
                    ": Character is not present on its specified Tile");
        }
    }
}

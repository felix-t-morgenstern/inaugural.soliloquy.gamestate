package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.*;
import soliloquy.specs.common.factories.ICollectionFactory;
import soliloquy.specs.common.factories.IGenericParamsSetFactory;
import soliloquy.specs.common.factories.IMapFactory;
import soliloquy.specs.common.valueobjects.ICollection;
import soliloquy.specs.common.valueobjects.IEntityUuid;
import soliloquy.specs.common.valueobjects.IGenericParamsSet;
import soliloquy.specs.common.valueobjects.IMap;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.factories.ICharacterEquipmentSlotsFactory;
import soliloquy.specs.gamestate.factories.ICharacterInventoryFactory;
import soliloquy.specs.gamestate.factories.ICharacterStatusEffectsFactory;
import soliloquy.specs.ruleset.entities.ICharacterAIType;
import soliloquy.specs.ruleset.entities.ICharacterType;
import soliloquy.specs.ruleset.entities.abilities.IActiveAbilityType;
import soliloquy.specs.ruleset.entities.abilities.IReactiveAbilityType;
import soliloquy.specs.ruleset.valueobjects.ICharacterClassification;
import soliloquy.specs.sprites.entities.ISpriteSet;

public class Character implements ICharacter {
    private final static ICharacterEvent CHARACTER_EVENT_ARCHETYPE = new CharacterEventArchetype();
    private final static ICharacterVitalAttribute CHARACTER_VITAL_ATTRIBUTE_ARCHETYPE =
            new CharacterVitalAttributeArchetype();
    private final static ICharacterAttribute CHARACTER_ATTRIBUTE_ARCHETYPE =
            new CharacterAttributeArchetype();
    private final static ICharacterAbility<IActiveAbilityType> CHARACTER_ACTIVE_ABILITY_ARCHETYPE =
            new CharacterActiveAbilityArchetype();
    private final static ICharacterAbility<IReactiveAbilityType> CHARACTER_REACTIVE_ABILITY_ARCHETYPE =
            new CharacterReactiveAbilityArchetype();
    private final static ICharacterAptitude CHARACTER_APTITUDE_ARCHETYPE =
            new CharacterAptitudeArchetype();

    private final IEntityUuid ID;
    private final ICharacterType CHARACTER_TYPE;
    private final ICollection<ICharacterClassification> CHARACTER_CLASSIFICATIONS;
    private final IMap<String,String> PRONOUNS;
    private final IMap<String, ICollection<ICharacterEvent>> EVENTS;
    private final ICharacterEquipmentSlots EQUIPMENT_SLOTS;
    private final ICharacterInventory INVENTORY;
    private final IMap<String, ICharacterVitalAttribute> VITAL_ATTRIBUTES;
    private final IMap<String, ICharacterAttribute> ATTRIBUTES;
    private final ICharacterStatusEffects STATUS_EFFECTS;
    private final IMap<String, ICharacterAbility<IActiveAbilityType>> ACTIVE_ABILITIES;
    private final IMap<String, ICharacterAbility<IReactiveAbilityType>> REACTIVE_ABILITIES;
    private final IMap<String, ICharacterAptitude> APTITUDES;
    private final IGenericParamsSet DATA;

    private ITile _tile;
    private String _stance;
    private String _direction;
    private ISpriteSet _spriteSet;
    private boolean _playerControlled;
    private boolean _hidden;
    private boolean _dead;
    private boolean _deleted;
    private String _name;
    private ICharacterAIType _aiType;

    @SuppressWarnings("ConstantConditions")
    public Character(IEntityUuid id,
                     ICharacterType characterType,
                     ICollectionFactory collectionFactory,
                     IMapFactory mapFactory,
                     ICharacterEquipmentSlotsFactory equipmentSlotsFactory,
                     ICharacterInventoryFactory inventoryFactory,
                     ICharacterStatusEffectsFactory statusEffectsFactory,
                     IGenericParamsSetFactory genericParamsSetFactory) {
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
        EVENTS = mapFactory.make("", collectionFactory.make(CHARACTER_EVENT_ARCHETYPE));
        if (equipmentSlotsFactory == null) {
            throw new IllegalArgumentException(
                    "Character: equipmentSlotsFactory must be non-null");
        }
        EQUIPMENT_SLOTS = equipmentSlotsFactory.make(this);
        if (inventoryFactory == null) {
            throw new IllegalArgumentException("Character: inventoryFactory must be non-null");
        }
        INVENTORY = inventoryFactory.make(this);
        VITAL_ATTRIBUTES = mapFactory.make("", CHARACTER_VITAL_ATTRIBUTE_ARCHETYPE);
        ATTRIBUTES = mapFactory.make("", CHARACTER_ATTRIBUTE_ARCHETYPE);
        if (statusEffectsFactory == null) {
            throw new IllegalArgumentException("Character: statusEffectsFactory must be non-null");
        }
        STATUS_EFFECTS = statusEffectsFactory.make(this);
        ACTIVE_ABILITIES = mapFactory.make("", CHARACTER_ACTIVE_ABILITY_ARCHETYPE);
        REACTIVE_ABILITIES = mapFactory.make("", CHARACTER_REACTIVE_ABILITY_ARCHETYPE);
        APTITUDES = mapFactory.make("", CHARACTER_APTITUDE_ARCHETYPE);
        if (genericParamsSetFactory == null) {
            throw new IllegalArgumentException(
                    "Character: genericParamsSetFactory must be non-null");
        }
        DATA = genericParamsSetFactory.make();
    }

    @Override
    public ICharacterType characterType() throws IllegalStateException {
        enforceInvariant("characterType", true);
        return CHARACTER_TYPE;
    }

    @Override
    public ICollection<ICharacterClassification> classifications() throws IllegalStateException {
        enforceInvariant("classifications", true);
        return CHARACTER_CLASSIFICATIONS;
    }

    @Override
    public IMap<String, String> pronouns() throws IllegalStateException {
        enforceInvariant("pronouns", true);
        return PRONOUNS;
    }

    @Override
    public ITile tile() throws IllegalStateException {
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
    public ISpriteSet getSpriteSet() throws IllegalStateException {
        enforceInvariant("getSpriteSet", true);
        return _spriteSet;
    }

    @Override
    public void setSpriteSet(ISpriteSet spriteSet)
            throws IllegalArgumentException, IllegalStateException {
        enforceInvariant("setSpriteSet", true);
        _spriteSet = spriteSet;
    }

    @Override
    public ICharacterAIType getAIType() throws IllegalStateException {
        enforceInvariant("getAIType", true);
        return _aiType;
    }

    @Override
    public void setAIType(ICharacterAIType characterAIType)
            throws IllegalArgumentException, IllegalStateException {
        enforceInvariant("setAIType", true);
        if (characterAIType == null) {
            throw new IllegalArgumentException(
                    "Character.setAIType: characterAIType cannot be null");
        }
        _aiType = characterAIType;
    }

    @Override
    public IMap<String, ICollection<ICharacterEvent>> events() {
        enforceInvariant("characterEvents", true);
        return EVENTS;
    }

    @Override
    public ICharacterEquipmentSlots equipmentSlots() throws IllegalStateException {
        enforceInvariant("equipmentSlots", true);
        return EQUIPMENT_SLOTS;
    }

    @Override
    public ICharacterInventory inventory() throws IllegalStateException {
        enforceInvariant("inventory", true);
        return INVENTORY;
    }

    @Override
    public IMap<String, ICharacterVitalAttribute> vitalAttributes() throws IllegalStateException {
        enforceInvariant("vitalAttributes", true);
        return VITAL_ATTRIBUTES;
    }

    @Override
    public IMap<String, ICharacterAttribute> attributes() throws IllegalStateException {
        enforceInvariant("attributes", true);
        return ATTRIBUTES;
    }

    @Override
    public ICharacterStatusEffects statusEffects() throws IllegalStateException {
        enforceInvariant("statusEffects", true);
        return STATUS_EFFECTS;
    }

    @Override
    public IMap<String, ICharacterAbility<IActiveAbilityType>> activeAbilities()
            throws IllegalStateException {
        enforceInvariant("activeAbilities", true);
        return ACTIVE_ABILITIES;
    }

    @Override
    public IMap<String, ICharacterAbility<IReactiveAbilityType>> reactiveAbilities()
            throws IllegalStateException {
        enforceInvariant("reactiveAbilities", true);
        return REACTIVE_ABILITIES;
    }

    @Override
    public IMap<String, ICharacterAptitude> aptitudes() throws IllegalStateException {
        enforceInvariant("aptitudes", true);
        return APTITUDES;
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
    public boolean getHidden() throws IllegalStateException {
        enforceInvariant("getHidden", true);
        return _hidden;
    }

    @Override
    public void setHidden(boolean hidden) throws IllegalStateException {
        enforceInvariant("setHidden", true);
        _hidden = hidden;
    }

    @Override
    public boolean getDead() throws IllegalStateException {
        enforceInvariant("getDead", true);
        return _dead;
    }

    @Override
    public void setDead(boolean dead) throws IllegalStateException {
        enforceInvariant("setDead", true);
        _dead = dead;
    }

    @Override
    public IGenericParamsSet data() throws IllegalStateException {
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
            _tile.characters().removeCharacter(this);
        }
        _tile = null;
        for(ICollection<ICharacterEvent> events : EVENTS.getValues()) {
            for(ICharacterEvent event : events) {
                event.delete();
            }
        }
        EQUIPMENT_SLOTS.delete();
        INVENTORY.delete();
        deleteAll(VITAL_ATTRIBUTES);
        deleteAll(ATTRIBUTES);
        STATUS_EFFECTS.delete();
        deleteAll(ACTIVE_ABILITIES);
        deleteAll(REACTIVE_ABILITIES);
        deleteAll(APTITUDES);
    }

    private <V extends IDeletable> void deleteAll(IMap<?,V> deletables) {
        for(IDeletable deletable : deletables.getValues()) {
            deletable.delete();
        }
    }

    @Override
    public void assignToTile(ITile tile)
            throws IllegalArgumentException, IllegalStateException {
        enforceInvariant("assignCharacterToTile", true);
        _tile = tile;
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
    public IEntityUuid id() {
        enforceInvariant("id", true);
        return ID;
    }

    @Override
    public String getInterfaceName() {
        enforceInvariant("getInterfaceName", true);
        return ICharacter.class.getCanonicalName();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof ICharacter)) {
            return false;
        }
        ICharacter character = (ICharacter) o;
        if (character.isDeleted())
        {
            return false;
        }
        return character.id().equals(ID);
    }

    private void enforceInvariant(String methodName, boolean cannotBeDeleted) {
        if (cannotBeDeleted && _deleted) {
            throw new IllegalStateException("Character." + methodName + ": Character is deleted");
        }
        if (_tile != null && !_tile.characters().containsCharacter(this)) {
            throw new IllegalStateException("Character." + methodName +
                    ": Character is not present on its specified Tile");
        }
        if (_tile != null && _tile.gameZone() != null &&
                _tile.gameZone().getCharactersRepresentation().get(ID) != this) {
            throw new IllegalStateException("Character." + methodName +
                    ": Character is not registered in its specified Tile's GameZone");
        }
    }
}

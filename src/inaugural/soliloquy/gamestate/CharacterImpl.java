package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.*;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.GenericParamsSetFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.factories.CharacterEquipmentSlotsFactory;
import soliloquy.specs.gamestate.factories.CharacterInventoryFactory;
import soliloquy.specs.gamestate.factories.CharacterStatusEffectsFactory;
import soliloquy.specs.ruleset.entities.CharacterAIType;
import soliloquy.specs.ruleset.entities.CharacterType;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbilityType;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbilityType;
import soliloquy.specs.ruleset.valueobjects.CharacterClassification;
import soliloquy.specs.sprites.entities.SpriteSet;

public class CharacterImpl implements Character {
    private final static CharacterEvent CHARACTER_EVENT_ARCHETYPE = new CharacterEventArchetype();
    private final static CharacterVitalAttribute CHARACTER_VITAL_ATTRIBUTE_ARCHETYPE =
            new CharacterVitalAttributeArchetype();
    private final static CharacterAttribute CHARACTER_ATTRIBUTE_ARCHETYPE =
            new CharacterAttributeArchetype();
    private final static CharacterAbility<ActiveAbilityType> CHARACTER_ACTIVE_ABILITY_ARCHETYPE =
            new CharacterActiveAbilityArchetype();
    private final static CharacterAbility<ReactiveAbilityType> CHARACTER_REACTIVE_ABILITY_ARCHETYPE =
            new CharacterReactiveAbilityArchetype();
    private final static CharacterAptitude CHARACTER_APTITUDE_ARCHETYPE =
            new CharacterAptitudeArchetype();

    private final EntityUuid ID;
    private final CharacterType CHARACTER_TYPE;
    private final Collection<CharacterClassification> CHARACTER_CLASSIFICATIONS;
    private final Map<String,String> PRONOUNS;
    private final Map<String, Collection<CharacterEvent>> EVENTS;
    private final CharacterEquipmentSlots EQUIPMENT_SLOTS;
    private final CharacterInventory INVENTORY;
    private final Map<String, CharacterVitalAttribute> VITAL_ATTRIBUTES;
    private final Map<String, CharacterAttribute> ATTRIBUTES;
    private final CharacterStatusEffects STATUS_EFFECTS;
    private final Map<String, CharacterAbility<ActiveAbilityType>> ACTIVE_ABILITIES;
    private final Map<String, CharacterAbility<ReactiveAbilityType>> REACTIVE_ABILITIES;
    private final Map<String, CharacterAptitude> APTITUDES;
    private final GenericParamsSet DATA;

    private Tile _tile;
    private String _stance;
    private String _direction;
    private SpriteSet _spriteSet;
    private boolean _playerControlled;
    private boolean _hidden;
    private boolean _dead;
    private boolean _deleted;
    private String _name;
    private CharacterAIType _aiType;

    @SuppressWarnings("ConstantConditions")
    public CharacterImpl(EntityUuid id,
                         CharacterType characterType,
                         CollectionFactory collectionFactory,
                         MapFactory mapFactory,
                         CharacterEquipmentSlotsFactory equipmentSlotsFactory,
                         CharacterInventoryFactory inventoryFactory,
                         CharacterStatusEffectsFactory statusEffectsFactory,
                         GenericParamsSetFactory genericParamsSetFactory) {
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
    public CharacterType characterType() throws IllegalStateException {
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
    public Map<String, Collection<CharacterEvent>> events() {
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
    public Map<String, CharacterVitalAttribute> vitalAttributes() throws IllegalStateException {
        enforceInvariant("vitalAttributes", true);
        return VITAL_ATTRIBUTES;
    }

    @Override
    public Map<String, CharacterAttribute> attributes() throws IllegalStateException {
        enforceInvariant("attributes", true);
        return ATTRIBUTES;
    }

    @Override
    public CharacterStatusEffects statusEffects() throws IllegalStateException {
        enforceInvariant("statusEffects", true);
        return STATUS_EFFECTS;
    }

    @Override
    public Map<String, CharacterAbility<ActiveAbilityType>> activeAbilities()
            throws IllegalStateException {
        enforceInvariant("activeAbilities", true);
        return ACTIVE_ABILITIES;
    }

    @Override
    public Map<String, CharacterAbility<ReactiveAbilityType>> reactiveAbilities()
            throws IllegalStateException {
        enforceInvariant("reactiveAbilities", true);
        return REACTIVE_ABILITIES;
    }

    @Override
    public Map<String, CharacterAptitude> aptitudes() throws IllegalStateException {
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
            _tile.characters().removeCharacter(this);
        }
        _tile = null;
        for(Collection<CharacterEvent> events : EVENTS.getValues()) {
            for(CharacterEvent event : events) {
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

    private <V extends Deletable> void deleteAll(Map<?,V> deletables) {
        for(Deletable deletable : deletables.getValues()) {
            deletable.delete();
        }
    }

    @Override
    public void assignToTile(Tile tile)
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
            throw new IllegalStateException("Character." + methodName + ": Character is deleted");
        }
        if (_tile != null && !_tile.characters().containsCharacter(this)) {
            throw new IllegalStateException("Character." + methodName +
                    ": Character is not present on its specified Tile");
        }
        if (_tile != null && _tile.gameZone() != null &&
                _tile.gameZone().charactersRepresentation().get(ID) != this) {
            throw new IllegalStateException("Character." + methodName +
                    ": Character is not registered in its specified Tile's GameZone");
        }
    }
}

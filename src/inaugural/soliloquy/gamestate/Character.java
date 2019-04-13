package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.CharacterClassificationArchetype;
import soliloquy.common.specs.*;
import soliloquy.gamestate.specs.*;
import soliloquy.ruleset.gameentities.specs.ICharacterEvent;
import soliloquy.ruleset.gameentities.specs.ICharacterAIType;
import soliloquy.ruleset.gameentities.specs.ICharacterClassification;
import soliloquy.ruleset.gameentities.specs.ICharacterType;
import soliloquy.sprites.specs.ISpriteSet;

public class Character implements ICharacter {
    private final IEntityUuid ID;
    private final ICharacterType CHARACTER_TYPE;
    private final ICollection<ICharacterClassification> CHARACTER_CLASSIFICATIONS;
    private final IMap<String,String> PRONOUNS;
    private final IGenericParamsSet TRAITS;
    private final IMap<String, ICharacterAIType> AI_TYPES;
    private final IGenericParamsSet AI_PARAMS;
    private final IMap<String, ICollection<ICharacterEvent>> AI_EVENTS;
    private final IMap<String, ICharacterEquipmentSlot> EQUIPMENT_SLOTS;
    private final IMap<Integer, IItem> INVENTORY;
    private final IMap<String, ICharacterVitalAttribute> VITAL_ATTRIBUTES;
    private final IMap<String, ICharacterAttribute> ATTRIBUTES;
    private final ICharacterStatusEffects STATUS_EFFECTS;
    private final IMap<String, ICharacterAbility> ACTIVE_ABILITIES;
    private final IMap<String, ICharacterAbility> REACTIVE_ABILITIES;
    private final IMap<String, ICharacterAptitude> APTITUDES;
    private final IGenericParamsSet DATA;

    private ITile _tile;
    private String _stance;
    private String _direction;
    private ISpriteSet _spriteSet;
    private String _characterAITypeId;
    private boolean _playerControlled;
    private boolean _hidden;
    private boolean _dead;
    private boolean _deleted;
    private String _name;

    public Character(IEntityUuid id,
                     ICharacterType characterType,
                     ICollectionFactory collectionFactory,
                     IMapFactory mapFactory,
                     IGenericParamsSet traits,
                     IMap<String,ICharacterAIType> aiTypes,
                     IGenericParamsSet aiParams,
                     IMap<String, ICollection<ICharacterEvent>> aiEvents,
                     IMap<String,ICharacterEquipmentSlot> equipmentSlots,
                     IMap<Integer, IItem> inventory,
                     IMap<String, ICharacterVitalAttribute> vitalAttributes,
                     IMap<String, ICharacterAttribute> attributes,
                     ICharacterStatusEffects statusEffects,
                     IMap<String, ICharacterAbility> activeAbilities,
                     IMap<String, ICharacterAbility> reactiveAbilities,
                     IMap<String, ICharacterAptitude> aptitudes,
                     IGenericParamsSet data) {
        ID = id;
        CHARACTER_TYPE = characterType;
        CHARACTER_CLASSIFICATIONS = collectionFactory.make(new CharacterClassificationArchetype());
        PRONOUNS = mapFactory.make("","");
        TRAITS = traits;
        AI_TYPES = aiTypes;
        AI_PARAMS = aiParams;
        AI_EVENTS = aiEvents;
        EQUIPMENT_SLOTS = equipmentSlots;
        INVENTORY = inventory;
        VITAL_ATTRIBUTES = vitalAttributes;
        ATTRIBUTES = attributes;
        STATUS_EFFECTS = statusEffects;
        ACTIVE_ABILITIES = activeAbilities;
        REACTIVE_ABILITIES = reactiveAbilities;
        APTITUDES = aptitudes;
        DATA = data;
    }

    @Override
    public ICharacterType characterType() throws IllegalStateException {
        if (_deleted) {
            throw new IllegalStateException("Character.characterType: Character is deleted");
        }
        return CHARACTER_TYPE;
    }

    @Override
    public ICollection<ICharacterClassification> classifications() throws IllegalStateException {
        if (_deleted) {
            throw new IllegalStateException("Character.classifications: Character is deleted");
        }
        return CHARACTER_CLASSIFICATIONS;
    }

    @Override
    public ITile getTile() throws IllegalStateException {
        if (_deleted) {
            throw new IllegalStateException("Character.getTile: Character is deleted");
        }
        return _tile;
    }

    @Override
    public void setTile(ITile tile) throws IllegalArgumentException, IllegalStateException {
        if (_deleted) {
            throw new IllegalStateException("Character.setTile(ITile): Character is deleted");
        }
        setTile(tile, 0);
    }

    @Override
    public void setTile(ITile tile, int zIndex) throws IllegalArgumentException, IllegalStateException {
        if (_deleted) {
            throw new IllegalStateException("Character.setTile(ITile, int): Character is deleted");
        }
        _tile = tile;
        if (tile != null) {
            tile.characters().put(this, zIndex);
        }
        IGameZone gameZone = gameZone();
        if (gameZone != null && !gameZone.characters().containsValue(this)) {
            gameZone.characters().put(ID, this);
        }
    }

    @Override
    public IMap<String, String> pronouns() throws IllegalStateException {
        if (_deleted) {
            throw new IllegalStateException("Character.pronouns: Character is deleted");
        }
        return PRONOUNS;
    }

    @Override
    public IGenericParamsSet traits() throws IllegalStateException {
        if (_deleted) {
            throw new IllegalStateException("Character.traits: Character is deleted");
        }
        return TRAITS;
    }

    @Override
    public String getStance() throws IllegalStateException {
        if (_deleted) {
            throw new IllegalStateException("Character.getStance: Character is deleted");
        }
        return _stance;
    }

    @Override
    public void setStance(String stance) throws IllegalStateException {
        if (_deleted) {
            throw new IllegalStateException("Character.setStance: Character is deleted");
        }
        _stance = stance;
    }

    @Override
    public String getDirection() throws IllegalStateException {
        if (_deleted) {
            throw new IllegalStateException("Character.getDirection: Character is deleted");
        }
        return _direction;
    }

    @Override
    public void setDirection(String direction) throws IllegalArgumentException, IllegalStateException {
        if (_deleted) {
            throw new IllegalStateException("Character.setDirection: Character is deleted");
        }
        _direction = direction;
    }

    @Override
    public ISpriteSet getSpriteSet() throws IllegalStateException {
        if (_deleted) {
            throw new IllegalStateException("Character.getSpriteSet: Character is deleted");
        }
        return _spriteSet;
    }

    @Override
    public void setSpriteSet(ISpriteSet spriteSet) throws IllegalArgumentException, IllegalStateException {
        if (_deleted) {
            throw new IllegalStateException("Character.setSpriteSet: Character is deleted");
        }
        _spriteSet = spriteSet;
    }

    @Override
    public String getAITypeId() throws IllegalStateException {
        if (_deleted) {
            throw new IllegalStateException("Character.getAITypeId: Character is deleted");
        }
        return _characterAITypeId;
    }

    @Override
    public void setAITypeId(String characterAITypeId)
            throws IllegalArgumentException, IllegalStateException {
        if (_deleted) {
            throw new IllegalStateException("Character.setAITypeId: Character is deleted");
        }
        if (!AI_TYPES.containsKey(characterAITypeId)) {
            throw new IllegalArgumentException("Character.setAITypeId: \"" + characterAITypeId
                    + "\" is not a valid AI Type Id");
        }
        _characterAITypeId = characterAITypeId;
    }

    @Override
    public IGenericParamsSet characterAIParams() throws IllegalStateException {
        if (_deleted) {
            throw new IllegalStateException("Character.characterAIParams: Character is deleted");
        }
        return AI_PARAMS;
    }

    @Override
    public IMap<String, ICollection<ICharacterEvent>> characterEvents() {
        if (_deleted) {
            throw new IllegalStateException("Character.characterEvents: Character is deleted");
        }
        return AI_EVENTS;
    }

    @Override
    public IMap<String, ICharacterEquipmentSlot> equipment() throws IllegalStateException {
        if (_deleted) {
            throw new IllegalStateException("Character.equipment: Character is deleted");
        }
        return EQUIPMENT_SLOTS;
    }

    @Override
    public IMap<Integer, IItem> inventory() throws IllegalStateException {
        if (_deleted) {
            throw new IllegalStateException("Character.inventory: Character is deleted");
        }
        return INVENTORY;
    }

    @Override
    public IMap<String, ICharacterVitalAttribute> vitalAttributes() throws IllegalStateException {
        if (_deleted) {
            throw new IllegalStateException("Character.vitalAttributes: Character is deleted");
        }
        return VITAL_ATTRIBUTES;
    }

    @Override
    public IMap<String, ICharacterAttribute> attributes() throws IllegalStateException {
        if (_deleted) {
            throw new IllegalStateException("Character.attributes: Character is deleted");
        }
        return ATTRIBUTES;
    }

    @Override
    public ICharacterStatusEffects statusEffects() throws IllegalStateException {
        if (_deleted) {
            throw new IllegalStateException("Character.statusEffects: Character is deleted");
        }
        return STATUS_EFFECTS;
    }

    @Override
    public IMap<String, ICharacterAbility> activeAbilities() throws IllegalStateException {
        if (_deleted) {
            throw new IllegalStateException("Character.activeAbilities: Character is deleted");
        }
        return ACTIVE_ABILITIES;
    }

    @Override
    public IMap<String, ICharacterAbility> reactiveAbilities() throws IllegalStateException {
        if (_deleted) {
            throw new IllegalStateException("Character.reactiveAbilities: Character is deleted");
        }
        return REACTIVE_ABILITIES;
    }

    @Override
    public IMap<String, ICharacterAptitude> aptitudes() throws IllegalStateException {
        if (_deleted) {
            throw new IllegalStateException("Character.aptitudes: Character is deleted");
        }
        return APTITUDES;
    }

    @Override
    public boolean getPlayerControlled() throws IllegalStateException {
        if (_deleted) {
            throw new IllegalStateException("Character.getPlayerControlled: Character is deleted");
        }
        return _playerControlled;
    }

    @Override
    public void setPlayerControlled(boolean playerControlled) throws IllegalStateException {
        if (_deleted) {
            throw new IllegalStateException("Character.setPlayerControlled: Character is deleted");
        }
        _playerControlled = playerControlled;
    }

    @Override
    public boolean getHidden() throws IllegalStateException {
        if (_deleted) {
            throw new IllegalStateException("Character.getHidden: Character is deleted");
        }
        return _hidden;
    }

    @Override
    public void setHidden(boolean hidden) throws IllegalStateException {
        if (_deleted) {
            throw new IllegalStateException("Character.setHidden: Character is deleted");
        }
        _hidden = hidden;
    }

    @Override
    public boolean getDead() throws IllegalStateException {
        if (_deleted) {
            throw new IllegalStateException("Character.getDead: Character is deleted");
        }
        return _dead;
    }

    @Override
    public void setDead(boolean dead) throws IllegalStateException {
        if (_deleted) {
            throw new IllegalStateException("Character.setDead: Character is deleted");
        }
        _dead = dead;
    }

    @Override
    public IGameZone gameZone() throws IllegalStateException {
        if (_deleted) {
            throw new IllegalStateException("Character.gameZone: Character is deleted");
        }
        if (_tile != null) {
            return _tile.gameZone();
        } else {
            return null;
        }
    }

    @Override
    public IGenericParamsSet data() throws IllegalStateException {
        if (_deleted) {
            throw new IllegalStateException("Character.data: Character is deleted");
        }
        return DATA;
    }

    @Override
    public void delete() throws IllegalStateException {
        if (_deleted) {
            throw new IllegalStateException("Character.delete: Character is deleted");
        }
        IGameZone gameZone = gameZone();
        if (gameZone != null) {
            gameZone.characters().removeByKey(ID);
        }
        if (_tile != null) {
            _tile.characters().removeByKey(this);
        }
        _tile = null;
        _deleted = true;
    }

    @Override
    public boolean isDeleted() {
        return _deleted;
    }

    @Override
    public String getName() {
        if (_deleted) {
            throw new IllegalStateException("Character.getName: Character is deleted");
        }
        return _name;
    }

    @Override
    public void setName(String name) {
        if (_deleted) {
            throw new IllegalStateException("Character.setName: Character is deleted");
        }
        _name = name;
    }

    @Override
    public IEntityUuid id() {
        if (_deleted) {
            throw new IllegalStateException("Character.id: Character is deleted");
        }
        return ID;
    }

    @Override
    public void read(String s, boolean b) throws IllegalArgumentException {
        if (_deleted) {
            throw new IllegalStateException("Character.read: Character is deleted");
        }

    }

    @Override
    public String write() throws IllegalArgumentException {
        if (_deleted) {
            throw new IllegalStateException("Character.write: Character is deleted");
        }
        return null;
    }

    @Override
    public String getInterfaceName() {
        if (_deleted) {
            throw new IllegalStateException("Character.getInterfaceName: Character is deleted");
        }
        return "soliloquy.gamestate.specs.ICharacter";
    }
}

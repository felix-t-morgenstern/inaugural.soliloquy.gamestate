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
    private final IGenericParamsSet AI_PARAMS;
    private final IMap<String, ICollection<ICharacterEvent>> AI_EVENTS;
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
                     IGenericParamsSet aiParams,
                     IMap<String, ICollection<ICharacterEvent>> aiEvents,
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
        AI_PARAMS = aiParams;
        AI_EVENTS = aiEvents;
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
    public IGenericParamsSet traits() throws IllegalStateException {
        enforceInvariant("traits", true);
        return TRAITS;
    }

    @Override
    public ITile tile() throws IllegalStateException {
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
    public void setDirection(String direction) throws IllegalArgumentException, IllegalStateException {
        enforceInvariant("setDirection", true);
        _direction = direction;
    }

    @Override
    public ISpriteSet getSpriteSet() throws IllegalStateException {
        enforceInvariant("getSpriteSet", true);
        return _spriteSet;
    }

    @Override
    public void setSpriteSet(ISpriteSet spriteSet) throws IllegalArgumentException, IllegalStateException {
        enforceInvariant("setSpriteSet", true);
        _spriteSet = spriteSet;
    }

    @Override
    public ICharacterAIType getAIType() throws IllegalStateException {
        return null;
    }

    @Override
    public void setAIType(ICharacterAIType iCharacterAIType) throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public IGenericParamsSet characterAIParams() throws IllegalStateException {
        enforceInvariant("characterAIParams", true);
        return AI_PARAMS;
    }

    @Override
    public IMap<String, ICollection<ICharacterEvent>> characterEvents() {
        enforceInvariant("characterEvents", true);
        return AI_EVENTS;
    }

    @Override
    public ICharacterEquipmentSlots equipmentSlots() throws IllegalStateException {
        return null;
    }

    @Override
    public ICharacterInventory inventory() throws IllegalStateException {
        return null;
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
    public IMap<String, ICharacterAbility> activeAbilities() throws IllegalStateException {
        enforceInvariant("activeAbilities", true);
        return ACTIVE_ABILITIES;
    }

    @Override
    public IMap<String, ICharacterAbility> reactiveAbilities() throws IllegalStateException {
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
    public IGameZone gameZone() throws IllegalStateException {
        enforceInvariant("gameZone", true);
        if (_tile != null) {
            return _tile.gameZone();
        } else {
            return null;
        }
    }

    @Override
    public IGenericParamsSet data() throws IllegalStateException {
        enforceInvariant("data", true);
        return DATA;
    }

    @Override
    public void delete() throws IllegalStateException {
        enforceInvariant("characterType", false);
        // TODO: Implement and test!
        _deleted = true;
    }

    @Override
    public void assignCharacterToTile(ITile iTile) throws IllegalArgumentException, IllegalStateException {

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
    public void read(String s, boolean b) throws IllegalArgumentException {
        enforceInvariant("read", true);

    }

    @Override
    public String write() throws IllegalArgumentException {
        enforceInvariant("write", true);
        return null;
    }

    @Override
    public String getInterfaceName() {
        enforceInvariant("getInterfaceName", true);
        return "soliloquy.gamestate.specs.ICharacter";
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

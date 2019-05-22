package inaugural.soliloquy.gamestate;

import soliloquy.common.specs.*;
import soliloquy.gamestate.specs.*;
import soliloquy.ruleset.gameentities.abilities.specs.IActiveAbilityType;
import soliloquy.ruleset.gameentities.abilities.specs.IReactiveAbilityType;
import soliloquy.ruleset.gameentities.specs.ICharacterAIType;
import soliloquy.ruleset.gameentities.specs.ICharacterClassification;
import soliloquy.ruleset.gameentities.specs.ICharacterType;
import soliloquy.sprites.specs.ISpriteSet;

import java.util.HashMap;
import java.util.Map;

public class TileCharacters implements ITileCharacters {
    private final ITile TILE;
    private final IMapFactory MAP_FACTORY;
    private final HashMap<ICharacter,Integer> CHARACTERS = new HashMap<>();
    private final ICharacter ARCHETYPE = new CharacterArchetype();

    public TileCharacters(ITile tile, IMapFactory mapFactory) {
        TILE = tile;
        MAP_FACTORY = mapFactory;
    }

    @Override
    public IMap<ICharacter, Integer> getCharactersRepresentation() {
        IMap<ICharacter,Integer> charactersRepresentation = MAP_FACTORY.make(ARCHETYPE,0);
        for (Map.Entry<ICharacter,Integer> entry : CHARACTERS.entrySet()) {
            charactersRepresentation.put(entry.getKey(), entry.getValue());
        }
        return charactersRepresentation;
    }

    @Override
    public void addCharacter(ICharacter character) throws IllegalArgumentException {
        addCharacter(character, 0);
    }

    @Override
    public void addCharacter(ICharacter character, int zIndex) throws IllegalArgumentException {
        if (character == null) {
            throw new IllegalArgumentException(
                    "TileCharacters.addCharacter: character cannot be null");
        }
        CHARACTERS.put(character, zIndex);
        character.assignCharacterToTile(TILE);
    }

    @Override
    public boolean removeCharacter(ICharacter character) throws IllegalArgumentException {
        if (character == null) {
            throw new IllegalArgumentException(
                    "TileCharacters.removeCharacter: character cannot be null");
        }
        return CHARACTERS.remove(character) != null;
    }

    @Override
    public Integer getZIndex(ICharacter character) throws IllegalArgumentException {
        if (character == null) {
            throw new IllegalArgumentException(
                    "TileCharacters.getZIndex: character cannot be null");
        }
        return CHARACTERS.get(character);
    }

    @Override
    public void setZIndex(ICharacter character, int zIndex) throws IllegalArgumentException {
        if (character == null) {
            throw new IllegalArgumentException(
                    "TileCharacters.setZIndex: character cannot be null");
        }
        if (!CHARACTERS.containsKey(character)) {
            throw new IllegalArgumentException(
                    "TileCharacters.setZIndex: character is not present");
        }
        CHARACTERS.put(character, zIndex);
    }

    @Override
    public boolean containsCharacter(ICharacter character) throws IllegalArgumentException {
        if (character == null) {
            throw new IllegalArgumentException(
                    "TileCharacters.containsCharacter: character cannot be null");
        }
        return CHARACTERS.containsKey(character);
    }

    @Override
    public String getInterfaceName() {
        return ITileCharacters.class.getCanonicalName();
    }

    @Override
    public void delete() throws IllegalStateException {

    }

    @Override
    public boolean isDeleted() {
        return false;
    }

    private class CharacterArchetype implements ICharacter {

        @Override
        public ICharacterType characterType() throws IllegalStateException {
            return null;
        }

        @Override
        public ICollection<ICharacterClassification> classifications() throws IllegalStateException {
            return null;
        }

        @Override
        public IMap<String, String> pronouns() throws IllegalStateException {
            return null;
        }

        @Override
        public IGenericParamsSet traits() throws IllegalStateException {
            return null;
        }

        @Override
        public ITile tile() throws IllegalStateException {
            return null;
        }

        @Override
        public String getStance() throws IllegalStateException {
            return null;
        }

        @Override
        public void setStance(String s) throws IllegalStateException {

        }

        @Override
        public String getDirection() throws IllegalStateException {
            return null;
        }

        @Override
        public void setDirection(String s) throws IllegalArgumentException, IllegalStateException {

        }

        @Override
        public ISpriteSet getSpriteSet() throws IllegalStateException {
            return null;
        }

        @Override
        public void setSpriteSet(ISpriteSet iSpriteSet) throws IllegalArgumentException, IllegalStateException {

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
            return null;
        }

        @Override
        public IMap<String, ICollection<ICharacterEvent>> characterEvents() {
            return null;
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
            return null;
        }

        @Override
        public IMap<String, ICharacterAttribute> attributes() throws IllegalStateException {
            return null;
        }

        @Override
        public ICharacterStatusEffects statusEffects() throws IllegalStateException {
            return null;
        }

        @Override
        public IMap<String, ICharacterAbility<IActiveAbilityType>> activeAbilities() throws IllegalStateException {
            return null;
        }

        @Override
        public IMap<String, ICharacterAbility<IReactiveAbilityType>> reactiveAbilities() throws IllegalStateException {
            return null;
        }

        @Override
        public IMap<String, ICharacterAptitude> aptitudes() throws IllegalStateException {
            return null;
        }

        @Override
        public boolean getPlayerControlled() throws IllegalStateException {
            return false;
        }

        @Override
        public void setPlayerControlled(boolean b) throws IllegalStateException {

        }

        @Override
        public boolean getHidden() throws IllegalStateException {
            return false;
        }

        @Override
        public void setHidden(boolean b) throws IllegalStateException {

        }

        @Override
        public boolean getDead() throws IllegalStateException {
            return false;
        }

        @Override
        public void setDead(boolean b) throws IllegalStateException {

        }

        @Override
        public IGenericParamsSet data() throws IllegalStateException {
            return null;
        }

        @Override
        public void delete() throws IllegalStateException {

        }

        @Override
        public boolean isDeleted() {
            return false;
        }

        @Override
        public void assignCharacterToTile(ITile iTile) throws IllegalArgumentException, IllegalStateException {

        }

        @Override
        public IEntityUuid id() {
            return null;
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public void setName(String s) {

        }

        @Override
        public String getInterfaceName() {
            return ICharacter.class.getCanonicalName();
        }
    }
}

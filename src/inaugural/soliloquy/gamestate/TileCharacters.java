package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.CharacterArchetype;
import soliloquy.common.specs.*;
import soliloquy.gamestate.specs.*;

import java.util.HashMap;
import java.util.Map;

public class TileCharacters implements ITileCharacters {
    private final ITile TILE;
    private final IMapFactory MAP_FACTORY;
    private final HashMap<ICharacter,Integer> CHARACTERS = new HashMap<>();
    private final static ICharacter ARCHETYPE = new CharacterArchetype();

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
        character.assignToTile(TILE);
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
}

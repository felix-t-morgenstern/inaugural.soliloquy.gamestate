package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileCharacters;

import java.util.HashMap;

public class TileCharactersStub implements TileCharacters {
    public final HashMap<Character,Integer> CHARACTERS = new HashMap<>();
    private final Tile TILE;
    public final Collection<Character> REMOVED_CHARACTERS = new CollectionStub<>();

    TileCharactersStub(Tile tile) {
        TILE = tile;
    }

    @Override
    public ReadableMap<Character, Integer> charactersRepresentation() {
        return null;
    }

    @Override
    public void add(Character character) throws IllegalArgumentException {
        add(character,0);
    }

    @Override
    public void add(Character character, int zIndex) throws IllegalArgumentException {
        CHARACTERS.put(character,zIndex);
        character.assignToTileAfterAddingToTileCharacters(TILE);
    }

    @Override
    public boolean remove(Character character) throws IllegalArgumentException {
        REMOVED_CHARACTERS.add(character);
        return CHARACTERS.remove(character) != null;
    }

    @Override
    public Integer getZIndex(Character character) throws IllegalArgumentException {
        return null;
    }

    @Override
    public void setZIndex(Character character, int i) throws IllegalArgumentException {

    }

    @Override
    public boolean contains(Character character) throws IllegalArgumentException {
        return CHARACTERS.containsKey(character);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public void delete() throws IllegalStateException {

    }

    @Override
    public boolean isDeleted() {
        return false;
    }
}

package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.valueobjects.ICollection;
import soliloquy.specs.common.valueobjects.IMap;
import soliloquy.specs.gamestate.entities.ICharacter;
import soliloquy.specs.gamestate.entities.ITile;
import soliloquy.specs.gamestate.entities.ITileCharacters;

import java.util.HashMap;

public class TileCharactersStub implements ITileCharacters {
    public final HashMap<ICharacter,Integer> CHARACTERS = new HashMap<>();
    private final ITile TILE;
    public final ICollection<ICharacter> REMOVED_CHARACTERS = new CollectionStub<>();

    TileCharactersStub(ITile tile) {
        TILE = tile;
    }

    @Override
    public IMap<ICharacter, Integer> getCharactersRepresentation() {
        return null;
    }

    @Override
    public void addCharacter(ICharacter character) throws IllegalArgumentException {
        addCharacter(character,0);
    }

    @Override
    public void addCharacter(ICharacter character, int zIndex) throws IllegalArgumentException {
        CHARACTERS.put(character,zIndex);
        TILE.gameZone().assignCharacterToGameZone(character);
        character.assignToTile(TILE);
    }

    @Override
    public boolean removeCharacter(ICharacter character) throws IllegalArgumentException {
        REMOVED_CHARACTERS.add(character);
        return CHARACTERS.remove(character) != null;
    }

    @Override
    public Integer getZIndex(ICharacter iCharacter) throws IllegalArgumentException {
        return null;
    }

    @Override
    public void setZIndex(ICharacter iCharacter, int i) throws IllegalArgumentException {

    }

    @Override
    public boolean containsCharacter(ICharacter character) throws IllegalArgumentException {
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

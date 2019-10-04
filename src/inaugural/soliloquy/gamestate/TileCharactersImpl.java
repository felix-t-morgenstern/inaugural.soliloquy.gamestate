package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.CharacterArchetype;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileCharacters;

import java.util.HashMap;

public class TileCharactersImpl extends HasDeletionInvariants implements TileCharacters {
    private final Tile TILE;
    private final MapFactory MAP_FACTORY;
    private final HashMap<Character,Integer> CHARACTERS = new HashMap<>();
    private final static Character ARCHETYPE = new CharacterArchetype();

    public TileCharactersImpl(Tile tile, MapFactory mapFactory) {
        TILE = tile;
        MAP_FACTORY = mapFactory;
    }

    @Override
    public ReadableMap<Character, Integer> charactersRepresentation() {
        enforceDeletionInvariants("getCharactersRepresentation");
        Map<Character,Integer> characters = MAP_FACTORY.make(ARCHETYPE,0);
        for (java.util.Map.Entry<Character,Integer> entry : CHARACTERS.entrySet()) {
            characters.put(entry.getKey(), entry.getValue());
        }
        return characters.readOnlyRepresentation();
    }

    @Override
    public void add(Character character) throws IllegalArgumentException {
        add(character, 0);
    }

    @Override
    public void add(Character character, int zIndex) throws IllegalArgumentException {
        enforceDeletionInvariants("addCharacter");
        enforceCharacterAssignmentInvariant(character, "add");
        if (character == null) {
            throw new IllegalArgumentException(
                    "TileCharacters.addCharacter: character cannot be null");
        }
        CHARACTERS.put(character, zIndex);
        character.assignToTileAfterAddingToTileCharacters(TILE);
    }

    @Override
    public boolean remove(Character character) throws IllegalArgumentException {
        enforceDeletionInvariants("removeCharacter");
        enforceCharacterAssignmentInvariant(character, "remove");
        if (character == null) {
            throw new IllegalArgumentException(
                    "TileCharacters.removeCharacter: character cannot be null");
        }
        if (CHARACTERS.containsKey(character)) {
            character.assignToTileAfterAddingToTileCharacters(null);
        }
        return CHARACTERS.remove(character) != null;
    }

    @Override
    public Integer getZIndex(Character character) throws IllegalArgumentException {
        enforceDeletionInvariants("getZIndex");
        enforceCharacterAssignmentInvariant(character, "getZIndex");
        if (character == null) {
            throw new IllegalArgumentException(
                    "TileCharacters.getZIndex: character cannot be null");
        }
        return CHARACTERS.get(character);
    }

    @Override
    public void setZIndex(Character character, int zIndex) throws IllegalArgumentException {
        enforceDeletionInvariants("setZIndex");
        enforceCharacterAssignmentInvariant(character, "setZIndex");
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
    public boolean contains(Character character) throws IllegalArgumentException {
        enforceDeletionInvariants("contains");
        enforceCharacterAssignmentInvariant(character, "contains");
        if (character == null) {
            throw new IllegalArgumentException(
                    "TileCharacters.containsCharacter: character cannot be null");
        }
        return CHARACTERS.containsKey(character);
    }

    @Override
    public String getInterfaceName() {
        enforceDeletionInvariants("getInterfaceName");
        return TileCharacters.class.getCanonicalName();
    }

    @Override
    public void delete() throws IllegalStateException {
        enforceDeletionInvariants("delete");
        _isDeleted = true;
        CHARACTERS.forEach((c,i) -> c.delete());
    }

    @Override
    protected String className() {
        return "TileCharacters";
    }

    @Override
    protected String containingClassName() {
        return "Tile";
    }

    @Override
    protected boolean containingObjectIsDeleted() {
        return TILE.isDeleted();
    }

    private void enforceCharacterAssignmentInvariant(Character character, String methodName) {
        if (character != null && CHARACTERS.containsKey(character) && character.tile() != TILE) {
            throw new IllegalStateException("TileCharactersImpl." + methodName + ": character " +
                    "was found in this class, but this class' Tile was not assigned to that " +
                    "Character");
        }
    }
}

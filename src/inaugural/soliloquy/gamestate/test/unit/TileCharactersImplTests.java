package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TileCharactersImpl;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStub;
import inaugural.soliloquy.gamestate.test.stubs.MapFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.TileStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileCharacters;

import static org.junit.jupiter.api.Assertions.*;

class TileCharactersImplTests {
    private final Tile TILE = new TileStub();
    private final MapFactory MAP_FACTORY = new MapFactoryStub();
    private final Character CHARACTER = new CharacterStub();

    private TileCharacters _tileCharacters;

    @BeforeEach
    void setUp() {
        _tileCharacters = new TileCharactersImpl(TILE, MAP_FACTORY);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TileCharacters.class.getCanonicalName(), _tileCharacters.getInterfaceName());
    }

    @Test
    void testAddCharacterAndContainsCharacter() {
        assertFalse(_tileCharacters.contains(CHARACTER));

        _tileCharacters.add(CHARACTER);

        _tileCharacters.remove(CHARACTER);
        assertSame(null, CHARACTER.tile());
    }

    @Test
    void testAddCharacterAlreadyPresentOnAnotherTile() {
        ((CharacterStub)CHARACTER)._tile = new TileStub();

        assertThrows(IllegalArgumentException.class, () -> _tileCharacters.add(CHARACTER));
        assertThrows(IllegalArgumentException.class, () -> _tileCharacters.add(CHARACTER, 0));
    }

    @Test
    void testAddCharacterCallsCharacterAssignmentFunction() {
        _tileCharacters.add(CHARACTER);

        assertSame(TILE, CHARACTER.tile());
    }

    @Test
    void testAddCharacterWithZIndexAndGetZIndex() {
        assertNull(_tileCharacters.getZIndex(CHARACTER));

        final int Z_INDEX = 123;
        _tileCharacters.add(CHARACTER, Z_INDEX);

        assertEquals((Integer) Z_INDEX, _tileCharacters.getZIndex(CHARACTER));
    }

    @Test
    void testSetZIndex() {
        _tileCharacters.add(CHARACTER);
        assertEquals((Integer) 0, _tileCharacters.getZIndex(CHARACTER));

        final int Z_INDEX = 123;
        _tileCharacters.setZIndex(CHARACTER, Z_INDEX);

        assertEquals((Integer) Z_INDEX, _tileCharacters.getZIndex(CHARACTER));
    }

    @Test
    void testSetZIndexForAbsentCharacter() {
        assertThrows(IllegalArgumentException.class,
                () -> _tileCharacters.setZIndex(CHARACTER, 0));
    }

    @Test
    void testRemoveCharacter() {
        _tileCharacters.add(CHARACTER);
        assertTrue(_tileCharacters.contains(CHARACTER));

        assertTrue(_tileCharacters.remove(CHARACTER));
        assertFalse(_tileCharacters.contains(CHARACTER));
    }

    @Test
    void testGetCharactersRepresentation() {
        final int Z_INDEX = 123;
        _tileCharacters.add(CHARACTER, Z_INDEX);

        ReadableMap<Character, Integer> characters = _tileCharacters.charactersRepresentation();

        assertEquals(1, characters.size());
        assertEquals((Integer) Z_INDEX, characters.get(CHARACTER));
    }

    @Test
    void testCallsWithNullCharacter() {
        assertThrows(IllegalArgumentException.class, () -> _tileCharacters.add(null));
        assertThrows(IllegalArgumentException.class, () -> _tileCharacters.add(null, 0));
        assertThrows(IllegalArgumentException.class, () -> _tileCharacters.getZIndex(null));
        assertThrows(IllegalArgumentException.class, () -> _tileCharacters.setZIndex(null, 0));
        assertThrows(IllegalArgumentException.class, () -> _tileCharacters.contains(null));
    }

    @Test
    void testDelete() {
        _tileCharacters.add(CHARACTER);

        _tileCharacters.delete();

        assertTrue(_tileCharacters.isDeleted());
        assertTrue(CHARACTER.isDeleted());
    }

    @Test
    void testDeletedInvariant() {
        _tileCharacters.delete();

        assertThrows(IllegalStateException.class, () -> _tileCharacters.charactersRepresentation());
        assertThrows(IllegalStateException.class, () -> _tileCharacters.add(CHARACTER));
        assertThrows(IllegalStateException.class, () -> _tileCharacters.add(CHARACTER, 0));
        assertThrows(IllegalStateException.class, () -> _tileCharacters.remove(CHARACTER));
        assertThrows(IllegalStateException.class, () -> _tileCharacters.getZIndex(CHARACTER));
        assertThrows(IllegalStateException.class, () -> _tileCharacters.setZIndex(CHARACTER, 0));
        assertThrows(IllegalStateException.class, () -> _tileCharacters.contains(CHARACTER));
        assertThrows(IllegalStateException.class, () -> _tileCharacters.getInterfaceName());
        assertThrows(IllegalStateException.class, () -> _tileCharacters.delete());
    }

    @Test
    void testTileDeletedInvariant() {
        TILE.deleteAfterDeletingContainingGameZone();

        assertThrows(IllegalStateException.class, () -> _tileCharacters.charactersRepresentation());
        assertThrows(IllegalStateException.class, () -> _tileCharacters.add(CHARACTER));
        assertThrows(IllegalStateException.class, () -> _tileCharacters.add(CHARACTER, 0));
        assertThrows(IllegalStateException.class, () -> _tileCharacters.remove(CHARACTER));
        assertThrows(IllegalStateException.class, () -> _tileCharacters.getZIndex(CHARACTER));
        assertThrows(IllegalStateException.class, () -> _tileCharacters.setZIndex(CHARACTER, 0));
        assertThrows(IllegalStateException.class, () -> _tileCharacters.contains(CHARACTER));
        assertThrows(IllegalStateException.class, () -> _tileCharacters.getInterfaceName());
        assertThrows(IllegalStateException.class, () -> _tileCharacters.delete());
    }

    @Test
    void testCharacterAssignmentInvariant() {
        _tileCharacters.add(CHARACTER);
        ((CharacterStub)CHARACTER)._tile = null;

        assertThrows(IllegalStateException.class, () -> _tileCharacters.add(CHARACTER));
        assertThrows(IllegalStateException.class, () -> _tileCharacters.add(CHARACTER, 0));
        assertThrows(IllegalStateException.class, () -> _tileCharacters.remove(CHARACTER));
        assertThrows(IllegalStateException.class, () -> _tileCharacters.getZIndex(CHARACTER));
        assertThrows(IllegalStateException.class, () -> _tileCharacters.setZIndex(CHARACTER, 0));
        assertThrows(IllegalStateException.class, () -> _tileCharacters.contains(CHARACTER));
    }
}

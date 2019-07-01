package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TileCharacters;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStub;
import inaugural.soliloquy.gamestate.test.stubs.MapFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.TileStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.IMapFactory;
import soliloquy.specs.common.infrastructure.IReadOnlyMap;
import soliloquy.specs.gamestate.entities.ICharacter;
import soliloquy.specs.gamestate.entities.ITile;
import soliloquy.specs.gamestate.entities.ITileCharacters;

import static org.junit.jupiter.api.Assertions.*;

class TileCharactersTests {
    private final ITile TILE = new TileStub();
    private final IMapFactory MAP_FACTORY = new MapFactoryStub();
    private final ICharacter CHARACTER = new CharacterStub();

    private ITileCharacters _tileCharacters;

    @BeforeEach
    void setUp() {
        _tileCharacters = new TileCharacters(TILE, MAP_FACTORY);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(ITileCharacters.class.getCanonicalName(), _tileCharacters.getInterfaceName());
    }

    @Test
    void testAddCharacterAndContainsCharacter() {
        assertFalse(_tileCharacters.containsCharacter(CHARACTER));
        _tileCharacters.addCharacter(CHARACTER);

        assertTrue(_tileCharacters.containsCharacter(CHARACTER));
        assertSame(TILE, CHARACTER.tile());
    }

    @Test
    void testAddCharacterWithZIndexAndGetZIndex() {
        assertNull(_tileCharacters.getZIndex(CHARACTER));

        final int Z_INDEX = 123;
        _tileCharacters.addCharacter(CHARACTER, Z_INDEX);

        assertEquals((Integer) Z_INDEX, _tileCharacters.getZIndex(CHARACTER));
    }

    @Test
    void testSetZIndex() {
        _tileCharacters.addCharacter(CHARACTER);
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
        _tileCharacters.addCharacter(CHARACTER);
        assertTrue(_tileCharacters.containsCharacter(CHARACTER));

        assertTrue(_tileCharacters.removeCharacter(CHARACTER));
        assertFalse(_tileCharacters.containsCharacter(CHARACTER));
    }

    @Test
    void testGetCharactersRepresentation() {
        final int Z_INDEX = 123;
        _tileCharacters.addCharacter(CHARACTER, Z_INDEX);

        IReadOnlyMap<ICharacter, Integer> characters = _tileCharacters.charactersRepresentation();

        assertEquals(1, characters.size());
        assertEquals((Integer) Z_INDEX, characters.get(CHARACTER));
    }

    @Test
    void testCallsWithNullCharacter() {
        assertThrows(IllegalArgumentException.class, () -> _tileCharacters.addCharacter(null));
        assertThrows(IllegalArgumentException.class, () -> _tileCharacters.addCharacter(null, 0));
        assertThrows(IllegalArgumentException.class, () -> _tileCharacters.getZIndex(null));
        assertThrows(IllegalArgumentException.class, () -> _tileCharacters.setZIndex(null, 0));
        assertThrows(IllegalArgumentException.class, () -> _tileCharacters.containsCharacter(null));
    }

    @Test
    void testDelete() {
        _tileCharacters.addCharacter(CHARACTER);

        _tileCharacters.delete();

        assertTrue(_tileCharacters.isDeleted());
        assertTrue(CHARACTER.isDeleted());
    }

    @Test
    void testDeletedInvariant() {
        _tileCharacters.delete();

        assertThrows(IllegalStateException.class, () -> _tileCharacters.charactersRepresentation());
        assertThrows(IllegalStateException.class, () -> _tileCharacters.addCharacter(CHARACTER));
        assertThrows(IllegalStateException.class, () -> _tileCharacters.addCharacter(CHARACTER, 0));
        assertThrows(IllegalStateException.class, () -> _tileCharacters.removeCharacter(CHARACTER));
        assertThrows(IllegalStateException.class, () -> _tileCharacters.getZIndex(CHARACTER));
        assertThrows(IllegalStateException.class, () -> _tileCharacters.setZIndex(CHARACTER, 0));
        assertThrows(IllegalStateException.class, () -> _tileCharacters.containsCharacter(CHARACTER));
        assertThrows(IllegalStateException.class, () -> _tileCharacters.getInterfaceName());
        assertThrows(IllegalStateException.class, () -> _tileCharacters.delete());
    }

    @Test
    void testTileDeletedInvariant() {
        TILE.delete();

        assertThrows(IllegalStateException.class, () -> _tileCharacters.charactersRepresentation());
        assertThrows(IllegalStateException.class, () -> _tileCharacters.addCharacter(CHARACTER));
        assertThrows(IllegalStateException.class, () -> _tileCharacters.addCharacter(CHARACTER, 0));
        assertThrows(IllegalStateException.class, () -> _tileCharacters.removeCharacter(CHARACTER));
        assertThrows(IllegalStateException.class, () -> _tileCharacters.getZIndex(CHARACTER));
        assertThrows(IllegalStateException.class, () -> _tileCharacters.setZIndex(CHARACTER, 0));
        assertThrows(IllegalStateException.class, () -> _tileCharacters.containsCharacter(CHARACTER));
        assertThrows(IllegalStateException.class, () -> _tileCharacters.getInterfaceName());
        assertThrows(IllegalStateException.class, () -> _tileCharacters.delete());
    }
}

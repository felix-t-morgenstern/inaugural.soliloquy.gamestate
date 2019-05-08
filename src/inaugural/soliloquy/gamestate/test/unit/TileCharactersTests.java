package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TileCharacters;
import inaugural.soliloquy.gamestate.test.stubs.CharacterStub;
import inaugural.soliloquy.gamestate.test.stubs.MapFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.TileStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.common.specs.IMap;
import soliloquy.common.specs.IMapFactory;
import soliloquy.gamestate.specs.ICharacter;
import soliloquy.gamestate.specs.ITile;
import soliloquy.gamestate.specs.ITileCharacters;

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

        final Integer Z_INDEX = 123;
        _tileCharacters.addCharacter(CHARACTER, Z_INDEX);

        assertEquals(Z_INDEX, _tileCharacters.getZIndex(CHARACTER));
    }

    @Test
    void testSetZIndex() {
        _tileCharacters.addCharacter(CHARACTER);
        assertEquals((Integer) 0, _tileCharacters.getZIndex(CHARACTER));

        final Integer Z_INDEX = 123;
        _tileCharacters.setZIndex(CHARACTER, Z_INDEX);

        assertEquals(Z_INDEX, _tileCharacters.getZIndex(CHARACTER));
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
        final Integer Z_INDEX = 123;
        _tileCharacters.addCharacter(CHARACTER, Z_INDEX);

        IMap<ICharacter,Integer> characters = _tileCharacters.getCharactersRepresentation();

        assertEquals(1, characters.size());
        assertEquals(characters.get(CHARACTER), Z_INDEX);
    }

    @Test
    void testCallsWithNullCharacter() {
        assertThrows(IllegalArgumentException.class, () -> _tileCharacters.addCharacter(null));
        assertThrows(IllegalArgumentException.class, () -> _tileCharacters.addCharacter(null, 0));
        assertThrows(IllegalArgumentException.class, () -> _tileCharacters.getZIndex(null));
        assertThrows(IllegalArgumentException.class, () -> _tileCharacters.setZIndex(null, 0));
        assertThrows(IllegalArgumentException.class, () -> _tileCharacters.containsCharacter(null));
    }
}

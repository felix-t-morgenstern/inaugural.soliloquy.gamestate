package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.archetypes.CharacterArchetype;
import inaugural.soliloquy.gamestate.entities.CameraImpl;
import inaugural.soliloquy.gamestate.test.fakes.*;
import inaugural.soliloquy.gamestate.test.spydoubles.TileVisibilitySpyDouble;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.gamestate.entities.Camera;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.ruleset.gameconcepts.TileVisibility;

import static org.junit.jupiter.api.Assertions.*;

class CameraImplTests {
    private Camera _camera;

    private final FakeCoordinateFactory COORDINATE_FACTORY = new FakeCoordinateFactory();
    private final TileVisibility TILE_VISIBILITY = new TileVisibilitySpyDouble();
    private final FakeGameZone GAME_ZONE = new FakeGameZone();

    @BeforeEach
    void setUp() {
        _camera = new CameraImpl(COORDINATE_FACTORY, TILE_VISIBILITY, () -> GAME_ZONE);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new CameraImpl(null, TILE_VISIBILITY, () -> GAME_ZONE));
        assertThrows(IllegalArgumentException.class,
                () -> new CameraImpl(COORDINATE_FACTORY, null, () -> GAME_ZONE));
        assertThrows(IllegalArgumentException.class,
                () -> new CameraImpl(COORDINATE_FACTORY, TILE_VISIBILITY, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(Camera.class.getCanonicalName(), _camera.getInterfaceName());
    }

    @Test
    void testGetAndSetTileLocation() {
        _camera.setTileLocation(123, 456);
        assertEquals(123, _camera.getTileLocation().getX());
        assertEquals(456, _camera.getTileLocation().getY());
    }

    @Test
    void testSetInvalidTileLocation() {
        assertThrows(IllegalArgumentException.class, () -> _camera.setTileLocation(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> _camera.setTileLocation(0, -1));
    }

    @Test
    void testGetTileLocationProducesClone() {
        _camera.setTileLocation(123, 456);
        Coordinate tileLocation = _camera.getTileLocation();
        tileLocation.setX(789);
        assertSame(123, _camera.getTileLocation().getX());
    }

    @Test
    void testGetAndSetXTileWidthOffset() {
        float offset = 0.123f;

        _camera.setXTileWidthOffset(offset);

        assertEquals(offset, _camera.getXTileWidthOffset());
    }

    @Test
    void testGetAndSetYTileHeightOffset() {
        float offset = 0.123f;

        _camera.setYTileHeightOffset(offset);

        assertEquals(offset, _camera.getYTileHeightOffset());
    }

    @Test
    void testGetAndSetTileRenderingRadius() {
        _camera.setTileRenderingRadius(123);
        assertSame(123, _camera.getTileRenderingRadius());
    }

    @Test
    void testSetInvalidTileRenderingRadius() {
        assertThrows(IllegalArgumentException.class, () -> _camera.setTileRenderingRadius(-1));
    }

    @Test
    void testGetAndSetAllTilesVisible() {
        _camera.setAllTilesVisible(true);
        assertSame(true, _camera.getAllTilesVisible());

        _camera.setAllTilesVisible(false);
        assertSame(false, _camera.getAllTilesVisible());
    }

    @Test
    void testCharactersProvidingVisibility() {
        assertNotNull(_camera.charactersProvidingVisibility());

        Character character = new CharacterArchetype();
        _camera.charactersProvidingVisibility().put(character, 123);
        assertSame(123, _camera.charactersProvidingVisibility().get(character));
    }

    @Test
    void testCoordinatesProvidingVisibility() {
        assertNotNull(_camera.coordinatesProvidingVisibility());

        Coordinate coordinate = new FakeCoordinate(123, 456);
        _camera.coordinatesProvidingVisibility().put(coordinate, 789);
        assertEquals(789, (int) _camera.coordinatesProvidingVisibility().get(coordinate));
    }

    @Test
    void testVisibleTiles() {
        assertNotNull(_camera.visibleTiles());

        Coordinate coordinate = new FakeCoordinate(123, 456);
        _camera.visibleTiles().add(coordinate);
        assertEquals(123, _camera.visibleTiles().get(0).getX());
        assertEquals(456, _camera.visibleTiles().get(0).getY());
    }

    @Test
    void testCalculateVisibleTilesWithAllTilesVisibleAndMinimumCoordinateBoundaries() {
        _camera.setAllTilesVisible(true);
        _camera.setTileLocation(2, 2);
        _camera.setTileRenderingRadius(5);
        _camera.calculateVisibleTiles();
        assertSame(49, _camera.visibleTiles().size());
    }

    @Test
    void testCalculateVisibleTilesWithAllTilesVisibleAndMaximumCoordinateBoundaries() {
        GAME_ZONE.FAKE_MAX_COORDINATES = new FakeCoordinate(4, 4);
        _camera.setAllTilesVisible(true);
        _camera.setTileLocation(2, 2);
        _camera.setTileRenderingRadius(5);
        _camera.calculateVisibleTiles();
        assertSame(25, _camera.visibleTiles().size());
    }

    @Test
    void testCalculateVisibleTilesWithZeroVisibilityRadius() {
        _camera.setTileRenderingRadius(0);
        _camera.calculateVisibleTiles();

        assertSame(0, _camera.visibleTiles().size());
    }

    @Test
    void testCalculateVisibleTiles() {
        _camera.setAllTilesVisible(false);
        _camera.setTileLocation(10, 10);
        _camera.setTileRenderingRadius(8);

        Tile tile = new FakeTile(new FakeCoordinate(4, 4));
        Character character1 = new FakeCharacter();
        tile.characters().add(character1);

        _camera.charactersProvidingVisibility().put(character1, 3);
        _camera.coordinatesProvidingVisibility().put(new FakeCoordinate(17, 13), 2);

        _camera.calculateVisibleTiles();
        assertEquals(22, _camera.visibleTiles().size());
        assertEquals(22, ((TileVisibilitySpyDouble) TILE_VISIBILITY)._tilesChecked.size());
    }
}

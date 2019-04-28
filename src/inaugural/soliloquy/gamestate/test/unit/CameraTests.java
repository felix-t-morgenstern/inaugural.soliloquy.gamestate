package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.Camera;
import inaugural.soliloquy.gamestate.archetypes.CharacterArchetype;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.common.specs.ICoordinate;
import soliloquy.game.primary.specs.IGame;
import soliloquy.gamestate.specs.ICamera;
import soliloquy.gamestate.specs.ICharacter;
import soliloquy.logger.specs.ILogger;
import soliloquy.ruleset.gameconcepts.specs.ITileVisibility;

import static org.junit.jupiter.api.Assertions.*;

class CameraTests {
    private ICamera _camera;

    private IGame _game;
    private ILogger _logger;

    @BeforeEach
    void setUp() {
        _game = new GameStub();
        _logger = new LoggerStub();

        _camera = new Camera(_game, _logger, new CoordinateFactoryStub(),
                new CollectionFactoryStub(), new MapFactoryStub(), new GameStateStub());
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(ICamera.class.getCanonicalName(), _camera.getInterfaceName());
    }

    @Test
    void testGame() {
        assertSame(_game, _camera.game());
    }

    @Test
    void testLogger() {
        assertSame(_logger, _camera.logger());
    }

    @Test
    void testGetAndSetTileLocation() {
        _camera.setTileLocation(123,456);
        assertEquals(123, _camera.getTileLocation().getX());
        assertEquals(456, _camera.getTileLocation().getY());
    }

    @Test
    void testSetInvalidTileLocation() {
        assertThrows(IllegalArgumentException.class, () -> _camera.setTileLocation(-1,0));
        assertThrows(IllegalArgumentException.class, () -> _camera.setTileLocation(0,-1));
    }

    @Test
    void testGetTileLocationProducesClone() {
        _camera.setTileLocation(123,456);
        ICoordinate tileLocation = _camera.getTileLocation();
        tileLocation.setX(789);
        assertSame(123, _camera.getTileLocation().getX());
    }

    @Test
    void testGetAndSetPixelOffset() {
        _camera.setPixelOffset(123,456);
        assertEquals(123, _camera.getPixelOffset().getX());
        assertEquals(456, _camera.getPixelOffset().getY());
    }

    @Test
    void testSetInvalidPixelOffset() {
        assertThrows(IllegalArgumentException.class, () -> _camera.setPixelOffset(-1,0));
        assertThrows(IllegalArgumentException.class, () -> _camera.setPixelOffset(0,-1));
    }

    @Test
    void testGetPixelOffsetProducesClone() {
        _camera.setPixelOffset(123,456);
        ICoordinate pixelOffset = _camera.getPixelOffset();
        pixelOffset.setX(789);
        assertSame(123, _camera.getPixelOffset().getX());
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

        ICharacter character = new CharacterArchetype();
        _camera.charactersProvidingVisibility().put(character,123);
        assertSame(123, _camera.charactersProvidingVisibility().get(character));
    }

    @Test
    void testCoordinatesProvidingVisibility() {
        assertNotNull(_camera.coordinatesProvidingVisibility());

        ICoordinate coordinate = new CoordinateStub(123,456);
        _camera.coordinatesProvidingVisibility().put(coordinate,789);
        assertEquals(789, (int) _camera.coordinatesProvidingVisibility().get(coordinate));
    }

    @Test
    void testVisibleTiles() {
        assertNotNull(_camera.visibileTiles());

        ICoordinate coordinate = new CoordinateStub(123,456);
        _camera.visibileTiles().add(coordinate);
        assertEquals(123, _camera.visibileTiles().get(0).getX());
        assertEquals(456, _camera.visibileTiles().get(0).getY());
    }

    @Test
    void testSetAndGetTileVisibility() {
        ITileVisibility tileVisibility = new TileVisibilityStub();
        _camera.setTileVisibility(tileVisibility);
        assertSame(tileVisibility, _camera.getTileVisibility());
    }

    @Test
    void testCalculateVisibleTilesWithoutTileVisibility() {
        _camera.setTileVisibility(null);
        assertThrows(IllegalStateException.class, () -> _camera.calculateVisibileTiles());
    }

    @Test
    void testCalculateVisibleTilesWithAllTilesVisibleAndMinimumCoordinateBoundaries() {
        _camera.setTileVisibility(new TileVisibilityStub());
        _camera.setAllTilesVisible(true);
        _camera.setTileLocation(2,2);
        _camera.setTileRenderingRadius(5);
        _camera.calculateVisibileTiles();
        assertSame(49, _camera.visibileTiles().size());
    }

    @Test
    void testCalculateVisibleTilesWithAllTilesVisibleAndMaximumCoordinateBoundaries() {
        GameZoneStub._maxX = 4;
        GameZoneStub._maxY = 4;
        _camera.setTileVisibility(new TileVisibilityStub());
        _camera.setAllTilesVisible(true);
        _camera.setTileLocation(2,2);
        _camera.setTileRenderingRadius(5);
        _camera.calculateVisibileTiles();
        assertSame(25, _camera.visibileTiles().size());
    }

    @Test
    void testCalculateVisibleTilesWithZeroVisibilityRadius() {
        _camera.setTileVisibility(new TileVisibilityStub());
        _camera.setTileRenderingRadius(0);
        _camera.calculateVisibileTiles();

        assertSame(0, _camera.visibileTiles().size());
    }

    @Test
    void testCalculateVisibleTiles() {
        // TODO: FIX THIS TEST!!!
        // TODO: FIX THIS TEST!!!
        // TODO: FIX THIS TEST!!!
        // TODO: FIX THIS TEST!!!
        // TODO: FIX THIS TEST!!!
//        ITileVisibility tileVisibility = new TileVisibilityStub();
//        _camera.setTileVisibility(tileVisibility);
//        _camera.setAllTilesVisible(false);
//        _camera.setTileLocation(10,10);
//        _camera.setTileRenderingRadius(8);
//
//        ICharacter character1 = new CharacterStub();
//        //character1.setTile(new TileStub(new CoordinateStub(4,4)));
//
//        _camera.charactersProvidingVisibility().put(character1,3);
//        _camera.coordinatesProvidingVisibility().put(new CoordinateStub(17,13),2);
//
//        _camera.calculateVisibileTiles();
//        //assertEquals(22, _camera.visibileTiles().size());
//        //assertEquals(22, ((TileVisibilityStub) tileVisibility)._tilesChecked.size());
    }
}

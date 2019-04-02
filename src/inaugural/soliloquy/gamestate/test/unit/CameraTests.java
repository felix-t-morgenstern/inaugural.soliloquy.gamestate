package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.Camera;
import inaugural.soliloquy.gamestate.CharacterArchetype;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.common.specs.ICoordinate;
import soliloquy.game.primary.specs.IGame;
import soliloquy.gamestate.specs.ICamera;
import soliloquy.gamestate.specs.ICharacter;
import soliloquy.logger.specs.ILogger;

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
                new CollectionFactoryStub(), new MapFactoryStub());
    }

    @Test
    void testGetInterfaceName() {
        assertEquals("soliloquy.gamestate.specs.ICamera", _camera.getInterfaceName());
    }

    @Test
    void testGame() {
        assertSame(_camera.game(), _game);
    }

    @Test
    void testLogger() {
        assertSame(_camera.logger(), _logger);
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
        assertSame(_camera.getTileLocation().getX(), 123);
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
        assertSame(_camera.getPixelOffset().getX(), 123);
    }

    @Test
    void testGetAndSetTileRenderingRadius() {
        _camera.setTileRenderingRadius(123);
        assertSame(_camera.getTileRenderingRadius(), 123);
    }

    @Test
    void testSetInvalidTileRenderingRadius() {
        assertThrows(IllegalArgumentException.class, () -> _camera.setTileRenderingRadius(-1));
    }

    @Test
    void testGetAndSetAllTilesVisible() {
        _camera.setAllTilesVisible(true);
        assertSame(_camera.getAllTilesVisible(), true);

        _camera.setAllTilesVisible(false);
        assertSame(_camera.getAllTilesVisible(), false);
    }

    @Test
    void testCharactersProvidingVisibility() {
        assertNotNull(_camera.charactersProvidingVisibility());

        ICharacter character = new CharacterArchetype();
        _camera.charactersProvidingVisibility().put(character,123);
        assertSame(_camera.charactersProvidingVisibility().get(character), 123);
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
        assertSame(_camera.visibileTiles().get(0), coordinate);
    }

    @Test
    void testCalculateVisibleTilesWithAllTilesVisible() {
        // TODO: Implement this test
    }

    @Test
    void testCalculateVisibleTiles() {
        // TODO: Implement this test
    }
}

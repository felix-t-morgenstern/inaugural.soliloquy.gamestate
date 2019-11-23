package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CameraFactoryImpl;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.game.Game;
import soliloquy.specs.gamestate.entities.Camera;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.factories.CameraFactory;
import soliloquy.specs.logger.Logger;
import soliloquy.specs.ruleset.gameconcepts.TileVisibility;

import static org.junit.jupiter.api.Assertions.*;

class CameraFactoryImplTests {
    private final Game GAME = new GameStub();
    private final Logger LOGGER = new LoggerStub();
    private final CoordinateFactory COORDINATE_FACTORY = new CoordinateFactoryStub();
    private final CollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    private final MapFactory MAP_FACTORY = new MapFactoryStub();
    private final TileVisibility TILE_VISIBILITY = new TileVisibilityStub();
    private final GameZone GAME_ZONE = new GameZoneStub(true);

    private CameraFactory _cameraFactory;

    @BeforeEach
    void setUp() {
        _cameraFactory = new CameraFactoryImpl(GAME, LOGGER, COORDINATE_FACTORY,
                COLLECTION_FACTORY, MAP_FACTORY, TILE_VISIBILITY, () -> GAME_ZONE);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                new CameraFactoryImpl(null, LOGGER, COORDINATE_FACTORY, COLLECTION_FACTORY,
                        MAP_FACTORY, TILE_VISIBILITY, () -> GAME_ZONE));
        assertThrows(IllegalArgumentException.class, () ->
                new CameraFactoryImpl(GAME, null, COORDINATE_FACTORY, COLLECTION_FACTORY,
                        MAP_FACTORY, TILE_VISIBILITY, () -> GAME_ZONE));
        assertThrows(IllegalArgumentException.class, () ->
                new CameraFactoryImpl(GAME, LOGGER, null, COLLECTION_FACTORY,
                        MAP_FACTORY, TILE_VISIBILITY, () -> GAME_ZONE));
        assertThrows(IllegalArgumentException.class, () ->
                new CameraFactoryImpl(GAME, LOGGER, COORDINATE_FACTORY, null,
                        MAP_FACTORY, TILE_VISIBILITY, () -> GAME_ZONE));
        assertThrows(IllegalArgumentException.class, () ->
                new CameraFactoryImpl(GAME, LOGGER, COORDINATE_FACTORY, COLLECTION_FACTORY,
                        null, TILE_VISIBILITY, () -> GAME_ZONE));
        assertThrows(IllegalArgumentException.class, () ->
                new CameraFactoryImpl(GAME, LOGGER, COORDINATE_FACTORY, COLLECTION_FACTORY,
                        MAP_FACTORY, null, () -> GAME_ZONE));
        assertThrows(IllegalArgumentException.class, () ->
                new CameraFactoryImpl(GAME, LOGGER, COORDINATE_FACTORY, COLLECTION_FACTORY,
                        MAP_FACTORY, TILE_VISIBILITY, null));
    }

    @Test
    void testMake() {
        Camera camera = _cameraFactory.make();

        try {
            camera.setTileRenderingRadius(1);
            camera.calculateVisibileTiles();
            fail("Should have triggered GameZoneStubException");
        } catch(GameZoneStub.GameZoneStubException e) {
            assertSame(GAME_ZONE, e.GAME_ZONE);
        }
    }
}

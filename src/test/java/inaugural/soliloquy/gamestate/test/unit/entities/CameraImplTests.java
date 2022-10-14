package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.archetypes.CharacterArchetype;
import inaugural.soliloquy.gamestate.entities.CameraImpl;
import inaugural.soliloquy.gamestate.test.fakes.*;
import inaugural.soliloquy.gamestate.test.spydoubles.TileVisibilitySpyDouble;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.common.valueobjects.Vertex;
import soliloquy.specs.gamestate.entities.Camera;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.graphics.renderables.providers.ProviderAtTime;
import soliloquy.specs.ruleset.gameconcepts.TileVisibility;

import static inaugural.soliloquy.tools.random.Random.randomFloatWithInclusiveFloor;
import static inaugural.soliloquy.tools.random.Random.randomInt;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CameraImplTests {
    private final int TILE_LOCATION_X = randomInt();
    private final int TILE_LOCATION_Y = randomInt();
    private final TileVisibility TILE_VISIBILITY = new TileVisibilitySpyDouble();
    private final FakeGameZone GAME_ZONE = new FakeGameZone();

    @Mock private Coordinate tileLocation;

    private Camera camera;

    @BeforeEach
    void setUp() {
        tileLocation = mock(Coordinate.class);
        when(tileLocation.x()).thenReturn(TILE_LOCATION_X);
        when(tileLocation.y()).thenReturn(TILE_LOCATION_Y);

        camera = new CameraImpl(TILE_VISIBILITY, () -> GAME_ZONE);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new CameraImpl(null, () -> GAME_ZONE));
        assertThrows(IllegalArgumentException.class, () -> new CameraImpl(TILE_VISIBILITY, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(Camera.class.getCanonicalName(), camera.getInterfaceName());
    }

    @Test
    void testGetAndSetTileLocation() {
        Coordinate tileLocation = mock(Coordinate.class);

        camera.setTileLocation(tileLocation);

        assertSame(tileLocation, camera.getTileLocation());
    }

    @Test
    void testSetTileLocationWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> camera.setTileLocation(null));
    }

    @Test
    void testGetAndSetTileCenterOffsetProvider() {
        //noinspection unchecked
        ProviderAtTime<Vertex> tileCenterOffsetProvider = mock(ProviderAtTime.class);

        camera.setTileCenterOffsetProvider(tileCenterOffsetProvider);

        assertSame(tileCenterOffsetProvider, camera.tileCenterOffsetProvider());
    }

    @Test
    void testSetTileCenterOffsetProviderWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> camera.setTileCenterOffsetProvider(null));
    }

    @Test
    void testSetAndGetZoom() {
        float zoom = randomFloatWithInclusiveFloor(0.001f);

        camera.setZoom(zoom);

        assertEquals(zoom, camera.getZoom());
    }

    @Test
    void testSetZoomWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> camera.setZoom(0f));
    }

    @Test
    void testGetAndSetTileRenderingRadius() {
        int radius = randomInt();

        camera.setTileRenderingRadius(radius);

        assertEquals(radius, camera.getTileRenderingRadius());
    }

    @Test
    void testSetInvalidTileRenderingRadius() {
        assertThrows(IllegalArgumentException.class, () -> camera.setTileRenderingRadius(-1));
    }

    @Test
    void testGetAndSetAllTilesVisible() {
        camera.setAllTilesVisible(true);
        assertSame(true, camera.getAllTilesVisible());

        camera.setAllTilesVisible(false);
        assertSame(false, camera.getAllTilesVisible());
    }

    @Test
    void testCharactersProvidingVisibility() {
        int characterVisibilityProvided = randomInt();

        assertNotNull(camera.charactersProvidingVisibility());

        Character character = new CharacterArchetype();
        camera.charactersProvidingVisibility().put(character, characterVisibilityProvided);
        assertEquals(characterVisibilityProvided,
                camera.charactersProvidingVisibility().get(character));
    }

    @Test
    void testCoordinatesProvidingVisibility() {
        int tileLocationVisibilityProvided = randomInt();

        assertNotNull(camera.coordinatesProvidingVisibility());

        camera.coordinatesProvidingVisibility().put(tileLocation, tileLocationVisibilityProvided);
        assertEquals(tileLocationVisibilityProvided,
                (int) camera.coordinatesProvidingVisibility().get(tileLocation));
    }

    @Test
    void testVisibleTiles() {
        assertNotNull(camera.visibleTiles());

        camera.visibleTiles().add(tileLocation);
        assertEquals(TILE_LOCATION_X, camera.visibleTiles().get(0).x());
        assertEquals(TILE_LOCATION_Y, camera.visibleTiles().get(0).y());
    }

    @Test
    void testCalculateVisibleTilesWithAllTilesVisibleAndMinimumCoordinateBoundaries() {
        when(tileLocation.x()).thenReturn(2);
        when(tileLocation.y()).thenReturn(2);
        camera.setAllTilesVisible(true);
        camera.setTileLocation(tileLocation);
        camera.setTileRenderingRadius(5);

        camera.calculateVisibleTiles();

        assertSame(49, camera.visibleTiles().size());
    }

    @Test
    void testCalculateVisibleTilesWithAllTilesVisibleAndMaximumCoordinateBoundaries() {
        Coordinate gameZoneMaxCoordinates = mock(Coordinate.class);
        when(gameZoneMaxCoordinates.x()).thenReturn(4);
        when(gameZoneMaxCoordinates.y()).thenReturn(4);
        GAME_ZONE.FAKE_MAX_COORDINATES = gameZoneMaxCoordinates;
        when(tileLocation.x()).thenReturn(2);
        when(tileLocation.y()).thenReturn(2);
        camera.setAllTilesVisible(true);
        camera.setTileLocation(tileLocation);
        camera.setTileRenderingRadius(5);

        camera.calculateVisibleTiles();

        assertSame(25, camera.visibleTiles().size());
    }

    @Test
    void testCalculateVisibleTilesWithZeroVisibilityRadius() {
        camera.setTileRenderingRadius(0);
        camera.calculateVisibleTiles();

        assertSame(0, camera.visibleTiles().size());
    }

    @Test
    void testCalculateVisibleTiles() {
        when(tileLocation.x()).thenReturn(10);
        when(tileLocation.y()).thenReturn(10);
        camera.setAllTilesVisible(false);
        camera.setTileLocation(tileLocation);
        camera.setTileRenderingRadius(8);
        Tile tile = new FakeTile(Coordinate.of(4, 4));
        Character character1 = new FakeCharacter();
        tile.characters().add(character1);
        camera.charactersProvidingVisibility().put(character1, 3);
        camera.coordinatesProvidingVisibility().put(Coordinate.of(17, 13), 2);

        camera.calculateVisibleTiles();

        assertEquals(22, camera.visibleTiles().size());
        assertEquals(22, ((TileVisibilitySpyDouble) TILE_VISIBILITY)._tilesChecked.size());
    }
}

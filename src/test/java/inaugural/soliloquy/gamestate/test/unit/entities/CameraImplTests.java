package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.archetypes.CharacterArchetype;
import inaugural.soliloquy.gamestate.entities.CameraImpl;
import inaugural.soliloquy.gamestate.test.fakes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.common.valueobjects.Vertex;
import soliloquy.specs.gamestate.entities.Camera;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.graphics.renderables.providers.ProviderAtTime;
import soliloquy.specs.ruleset.gameconcepts.TileVisibility;

import static inaugural.soliloquy.tools.random.Random.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CameraImplTests {
    private final int TILE_LOCATION_X = randomInt();
    private final int TILE_LOCATION_Y = randomInt();

    @Mock private Tile mockTile;
    @Mock private TileVisibility mockTileVisibility;
    @Mock private GameZone mockGameZone;
    @Mock private Coordinate tileLocation;

    private Camera camera;

    @BeforeEach
    void setUp() {
        mockTile = mock(Tile.class);

        mockGameZone = mock(GameZone.class);
        when(mockGameZone.maxCoordinates()).thenReturn(Coordinate.of(99, 99));
        when(mockGameZone.tile(any())).thenReturn(mockTile);

        tileLocation = mock(Coordinate.class);
        when(tileLocation.x()).thenReturn(TILE_LOCATION_X);
        when(tileLocation.y()).thenReturn(TILE_LOCATION_Y);

        mockTileVisibility = mock(TileVisibility.class);
        when(mockTileVisibility.canSeeTile(any(), any())).thenReturn(true);

        camera = new CameraImpl(mockTileVisibility, () -> mockGameZone);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new CameraImpl(null, () -> mockGameZone));
        assertThrows(IllegalArgumentException.class, () -> new CameraImpl(mockTileVisibility, null));
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
        int radius = randomIntWithInclusiveFloor(0);

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
        Coordinate gameZoneMaxCoordinates = Coordinate.of(4, 4);
        when(mockGameZone.maxCoordinates()).thenReturn(gameZoneMaxCoordinates);
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
        verify(mockTileVisibility, times(22)).canSeeTile(any(), any());
    }
}

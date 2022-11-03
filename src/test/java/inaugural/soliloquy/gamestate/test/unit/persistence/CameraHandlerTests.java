package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.CameraHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.common.valueobjects.Vertex;
import soliloquy.specs.gamestate.entities.Camera;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.factories.CameraFactory;
import soliloquy.specs.graphics.renderables.providers.ProviderAtTime;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CameraHandlerTests {
    private final String CHARACTER_1_UUID = "4b304158-fa99-44fd-a85a-572b3213c2ab";
    private final String CHARACTER_2_UUID = "9497038a-2d3a-4073-923e-b59c73607baf";
    private final String CHARACTER_3_UUID = "30773bab-7015-4456-9235-cbdf5d7c5086";
    private final int TILE_LOCATION_X = 111;
    private final int TILE_LOCATION_Y = 222;
    private final String TILE_CENTER_OFFSET_PROVIDER_TYPE = "tileCenterOffsetProviderType";
    private final String TILE_CENTER_OFFSET_PROVIDER = "tileCenterOffsetProvider";
    private final float ZOOM = 0.789f;
    private final int TILE_RENDERING_RADIUS = 333;
    private final boolean ALL_TILES_VISIBLE = true;

    private HashMap<Character, Integer> charactersProvidingVisibility;
    private HashMap<Coordinate, Integer> coordinatesProvidingVisibility;

    @Mock private CameraFactory mockCameraFactory;
    @Mock private Camera mockCamera;
    @Mock private GameZone mockGameZone;
    @Mock private Supplier<GameZone> mockGetCurrentGameZone;
    @Mock private Character mockCharacter1;
    @Mock private Character mockCharacter2;
    @Mock private Character mockCharacter3;
    @Mock private ProviderAtTime<Vertex> mockTileCenterOffsetProvider;
    @Mock private TypeHandler<ProviderAtTime<Vertex>> mockTileCenterOffsetProviderHandler;
    @Mock private PersistentValuesHandler mockPersistentValuesHandler;

    private TypeHandler<Camera> cameraHandler;

    private final String WRITTEN_VALUE = "{\"tileLocationX\":111,\"tileLocationY\":222," +
            "\"tileCenterOffsetProviderType\":\"tileCenterOffsetProviderType\"," +
            "\"tileCenterOffsetProvider\":\"tileCenterOffsetProvider\",\"zoom\":0.789," +
            "\"tileRenderingRadius\":333,\"allTilesVisible\":true," +
            "\"charactersProvidingVisibility\":[{\"characterId\":\"4b304158-fa99-44fd-a85a" +
            "-572b3213c2ab\",\"tiles\":444}," +
            "{\"characterId\":\"30773bab-7015-4456-9235-cbdf5d7c5086\",\"tiles\":555}]," +
            "\"coordinatesProvidingVisibility\":[{\"x\":1,\"y\":2,\"tiles\":3},{\"x\":4,\"y\":5," +
            "\"tiles\":6}]}";

    @BeforeEach
    void setUp() {
        mockCharacter1 = mock(Character.class);
        when(mockCharacter1.uuid()).thenReturn(UUID.fromString(CHARACTER_1_UUID));
        mockCharacter2 = mock(Character.class);
        mockCharacter3 = mock(Character.class);
        when(mockCharacter3.uuid()).thenReturn(UUID.fromString(CHARACTER_3_UUID));
        charactersProvidingVisibility = new HashMap<>() {{
            put(mockCharacter1, 444);
            put(mockCharacter3, 555);
        }};

        coordinatesProvidingVisibility = new HashMap<>() {{
            put(Coordinate.of(1, 2), 3);
            put(Coordinate.of(4, 5), 6);
        }};

        mockGameZone = mock(GameZone.class);
        when(mockGameZone.charactersRepresentation()).thenReturn(new HashMap<>() {{
            put(UUID.fromString(CHARACTER_1_UUID), mockCharacter1);
            put(UUID.fromString(CHARACTER_2_UUID), mockCharacter2);
            put(UUID.fromString(CHARACTER_3_UUID), mockCharacter3);
        }});

        //noinspection unchecked
        mockGetCurrentGameZone = mock(Supplier.class);
        when(mockGetCurrentGameZone.get()).thenReturn(mockGameZone);

        //noinspection unchecked
        mockTileCenterOffsetProvider = mock(ProviderAtTime.class);
        when(mockTileCenterOffsetProvider.getInterfaceName()).thenReturn(
                TILE_CENTER_OFFSET_PROVIDER_TYPE);

        //noinspection unchecked
        mockTileCenterOffsetProviderHandler = mock(TypeHandler.class);
        when(mockTileCenterOffsetProviderHandler.read(anyString())).thenReturn(
                mockTileCenterOffsetProvider);
        when(mockTileCenterOffsetProviderHandler.write(any())).thenReturn(
                TILE_CENTER_OFFSET_PROVIDER);

        mockPersistentValuesHandler = mock(PersistentValuesHandler.class);
        //noinspection unchecked,rawtypes
        when(mockPersistentValuesHandler.getTypeHandler(anyString())).thenReturn(
                (TypeHandler) mockTileCenterOffsetProviderHandler);

        mockCamera = mock(Camera.class);
        when(mockCamera.getTileLocation()).thenReturn(
                Coordinate.of(TILE_LOCATION_X, TILE_LOCATION_Y));
        when(mockCamera.tileCenterOffsetProvider()).thenReturn(mockTileCenterOffsetProvider);
        when(mockCamera.getZoom()).thenReturn(ZOOM);
        when(mockCamera.getTileRenderingRadius()).thenReturn(TILE_RENDERING_RADIUS);
        when(mockCamera.getAllTilesVisible()).thenReturn(ALL_TILES_VISIBLE);
        when(mockCamera.charactersProvidingVisibility()).thenReturn(charactersProvidingVisibility);
        when(mockCamera.coordinatesProvidingVisibility()).thenReturn(
                coordinatesProvidingVisibility);

        mockCameraFactory = mock(CameraFactory.class);
        when(mockCameraFactory.make(any())).thenReturn(mockCamera);

        cameraHandler = new CameraHandler(mockCameraFactory, mockPersistentValuesHandler,
                mockGetCurrentGameZone);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new CameraHandler(null, mockPersistentValuesHandler, mockGetCurrentGameZone));
        assertThrows(IllegalArgumentException.class,
                () -> new CameraHandler(mockCameraFactory, null, mockGetCurrentGameZone));
        assertThrows(IllegalArgumentException.class,
                () -> new CameraHandler(mockCameraFactory, mockPersistentValuesHandler, null));
    }

    @Test
    void testWrite() {
        String output = cameraHandler.write(mockCamera);

        assertEquals(WRITTEN_VALUE, output);
        verify(mockPersistentValuesHandler, times(1)).getTypeHandler(
                TILE_CENTER_OFFSET_PROVIDER_TYPE);
        verify(mockTileCenterOffsetProviderHandler, times(1)).write(mockTileCenterOffsetProvider);
    }

    @Test
    void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> cameraHandler.write(null));
    }

    @Test
    void testRead() {
        Camera camera = cameraHandler.read(WRITTEN_VALUE);

        assertNotNull(camera);
        assertSame(mockCamera, camera);
        verify(mockCameraFactory, times(1)).make(mockGetCurrentGameZone);
        verify(mockCamera, times(1)).setTileLocation(
                eq(Coordinate.of(TILE_LOCATION_X, TILE_LOCATION_Y)));
        verify(mockPersistentValuesHandler, times(1)).getTypeHandler(TILE_CENTER_OFFSET_PROVIDER_TYPE);
        verify(mockTileCenterOffsetProviderHandler, times(1)).read(TILE_CENTER_OFFSET_PROVIDER);
        verify(mockCamera, times(1)).setTileCenterOffsetProvider(mockTileCenterOffsetProvider);
        verify(mockCamera, times(1)).setZoom(ZOOM);
        verify(mockCamera, times(1)).setTileRenderingRadius(TILE_RENDERING_RADIUS);
        verify(mockCamera, times(1)).setAllTilesVisible(ALL_TILES_VISIBLE);
        verify(mockGetCurrentGameZone, times(1)).get();
        assertEquals(charactersProvidingVisibility, camera.charactersProvidingVisibility());
        assertEquals(coordinatesProvidingVisibility, camera.coordinatesProvidingVisibility());
    }

    @Test
    void testReadWhereCharacterProvidingVisibilityIsNotInGameZone() {
        when(mockGameZone.charactersRepresentation()).thenReturn(new HashMap<>() {{
            put(UUID.fromString(CHARACTER_1_UUID), mockCharacter1);
            put(UUID.fromString(CHARACTER_2_UUID), mockCharacter2);
        }});

        assertThrows(IllegalStateException.class, () -> cameraHandler.read(WRITTEN_VALUE));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(
                TypeHandler.class.getCanonicalName() + "<" + Camera.class.getCanonicalName() + ">",
                cameraHandler.getInterfaceName());
    }
}

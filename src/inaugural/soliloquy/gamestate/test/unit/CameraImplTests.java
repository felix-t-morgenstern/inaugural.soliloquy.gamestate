package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CameraImpl;
import inaugural.soliloquy.gamestate.archetypes.CharacterArchetype;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;
import soliloquy.specs.gamestate.entities.Camera;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.ruleset.gameconcepts.TileVisibility;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class CameraImplTests {
    private Camera _camera;

    private final CoordinateFactoryStub COORDINATE_FACTORY = new CoordinateFactoryStub();
    private final CollectionFactoryStub COLLECTION_FACTORY = new CollectionFactoryStub();
    private final MapFactoryStub MAP_FACTORY = new MapFactoryStub();
    private final TileVisibility TILE_VISIBILITY = new TileVisibilityStub();
    private final GameZoneStub GAME_ZONE = new GameZoneStub();

    @BeforeEach
    void setUp() {
        _camera = new CameraImpl(COORDINATE_FACTORY, COLLECTION_FACTORY, MAP_FACTORY,
                TILE_VISIBILITY, () -> GAME_ZONE);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new CameraImpl(null,
                COLLECTION_FACTORY, MAP_FACTORY, TILE_VISIBILITY, () -> GAME_ZONE));
        assertThrows(IllegalArgumentException.class, () -> new CameraImpl(COORDINATE_FACTORY,
                null, MAP_FACTORY, TILE_VISIBILITY, () -> GAME_ZONE));
        assertThrows(IllegalArgumentException.class, () -> new CameraImpl(COORDINATE_FACTORY,
                COLLECTION_FACTORY, null, TILE_VISIBILITY, () -> GAME_ZONE));
        assertThrows(IllegalArgumentException.class, () -> new CameraImpl(COORDINATE_FACTORY,
                COLLECTION_FACTORY, MAP_FACTORY, null, () -> GAME_ZONE));
        assertThrows(IllegalArgumentException.class, () -> new CameraImpl(COORDINATE_FACTORY,
                COLLECTION_FACTORY, MAP_FACTORY, TILE_VISIBILITY, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(Camera.class.getCanonicalName(), _camera.getInterfaceName());
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
        Coordinate tileLocation = _camera.getTileLocation();
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

    @SuppressWarnings("unused")
    @Test
    void testGetPixelOffsetProducesClone() {
        _camera.setPixelOffset(123,456);
        ReadableCoordinate pixelOffset = _camera.getPixelOffset();
        assertThrows(ClassCastException.class, () -> {Coordinate x = (Coordinate)pixelOffset;});
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
        _camera.charactersProvidingVisibility().put(character,123);
        assertSame(123, _camera.charactersProvidingVisibility().get(character));
    }

    @Test
    void testCoordinatesProvidingVisibility() {
        assertNotNull(_camera.coordinatesProvidingVisibility());

        Coordinate coordinate = new CoordinateStub(123,456);
        _camera.coordinatesProvidingVisibility().put(coordinate,789);
        assertEquals(789, (int) _camera.coordinatesProvidingVisibility().get(coordinate));
    }

    @Test
    void testVisibleTiles() {
        assertNotNull(_camera.visibleTiles());

        Coordinate coordinate = new CoordinateStub(123,456);
        _camera.visibleTiles().add(coordinate);
        assertEquals(123, _camera.visibleTiles().get(0).getX());
        assertEquals(456, _camera.visibleTiles().get(0).getY());
    }

    @Test
    void testCalculateVisibleTilesWithAllTilesVisibleAndMinimumCoordinateBoundaries() {
        _camera.setAllTilesVisible(true);
        _camera.setTileLocation(2,2);
        _camera.setTileRenderingRadius(5);
        _camera.calculateVisibleTiles();
        assertSame(49, _camera.visibleTiles().size());
    }

    @Test
    void testCalculateVisibleTilesWithAllTilesVisibleAndMaximumCoordinateBoundaries() {
        GAME_ZONE.FAKE_MAX_COORDINATES = new ReadableCoordinateStub(4,4);
        _camera.setAllTilesVisible(true);
        _camera.setTileLocation(2,2);
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
        _camera.setTileLocation(10,10);
        _camera.setTileRenderingRadius(8);

        Tile tile = new TileStub(new CoordinateStub(4,4));
        Character character1 = new CharacterStub();
        tile.characters().add(character1);
        //character1.setTile(new TileStub(new CoordinateStub(4,4)));

        _camera.charactersProvidingVisibility().put(character1,3);
        _camera.coordinatesProvidingVisibility().put(new CoordinateStub(17,13),2);

        _camera.calculateVisibleTiles();
        assertEquals(22, _camera.visibleTiles().size());
        assertEquals(22, ((TileVisibilityStub) TILE_VISIBILITY)._tilesChecked.size());
    }
}

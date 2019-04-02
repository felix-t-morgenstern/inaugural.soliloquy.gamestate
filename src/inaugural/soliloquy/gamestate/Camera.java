package inaugural.soliloquy.gamestate;

import soliloquy.common.specs.*;
import soliloquy.game.primary.specs.IGame;
import soliloquy.gamestate.specs.ICamera;
import soliloquy.gamestate.specs.ICharacter;
import soliloquy.logger.specs.ILogger;

public class Camera implements ICamera {
    private final IGame GAME;
    private final ILogger LOGGER;
    private final ICoordinateFactory COORDINATE_FACTORY;
    private final IMap<ICharacter,Integer> CHARACTERS_PROVIDING_VISIBILITY;
    private final IMap<ICoordinate,Integer> COORDINATES_PROVIDING_VISIBILITY;
    private final ICollection<ICoordinate> VISIBLE_TILES;

    private int _tileLocationX;
    private int _tileLocationY;
    private int _pixelOffsetX;
    private int _pixelOffsetY;
    private int _tileRenderingRadius;
    private boolean _allTilesVisible;

    public Camera(IGame game, ILogger logger, ICoordinateFactory coordinateFactory,
                  ICollectionFactory collectionFactory, IMapFactory mapFactory) {
        GAME = game;
        LOGGER = logger;
        COORDINATE_FACTORY = coordinateFactory;
        CHARACTERS_PROVIDING_VISIBILITY = mapFactory.make(new CharacterArchetype(), 0);
        COORDINATES_PROVIDING_VISIBILITY = mapFactory.make(new CoordinateArchetype(), 0);
        VISIBLE_TILES = collectionFactory.make(new CoordinateArchetype());
    }

    @Override
    public ICoordinate getTileLocation() {
        return COORDINATE_FACTORY.make(_tileLocationX, _tileLocationY);
    }

    @Override
    public void setTileLocation(int x, int y) throws IllegalArgumentException {
        if (x < 0) {
            throw new IllegalArgumentException(
                    "Camera.setTileLocation: x (" + x + ") cannot be < 0");
        }
        if (y < 0) {
            throw new IllegalArgumentException(
                    "Camera.setTileLocation: y (" + y + ") cannot be < 0");
        }
        _tileLocationX = x;
        _tileLocationY = y;
    }

    @Override
    public ICoordinate getPixelOffset() {
        return COORDINATE_FACTORY.make(_pixelOffsetX, _pixelOffsetY);
    }

    @Override
    public void setPixelOffset(int x, int y) throws IllegalArgumentException {
        if (x < 0) {
            throw new IllegalArgumentException(
                    "Camera.setPixelOffset: x (" + x + ") cannot be < 0");
        }
        if (y < 0) {
            throw new IllegalArgumentException(
                    "Camera.setPixelOffset: y (" + y + ") cannot be < 0");
        }
        _pixelOffsetX = x;
        _pixelOffsetY = y;
    }

    @Override
    public int getTileRenderingRadius() {
        return _tileRenderingRadius;
    }

    @Override
    public void setTileRenderingRadius(int tileRenderingRadius) throws IllegalArgumentException {
        if (tileRenderingRadius < 0) {
            throw new IllegalArgumentException(
                    "Camera.setTileRenderingRadius: tileRenderingRadius ("
                            + tileRenderingRadius + ") cannot be < 0");
        }
        _tileRenderingRadius = tileRenderingRadius;
    }

    @Override
    public boolean getAllTilesVisible() {
        return _allTilesVisible;
    }

    @Override
    public void setAllTilesVisible(boolean allTilesVisible) {
        _allTilesVisible = allTilesVisible;
    }

    @Override
    public IMap<ICharacter,Integer> charactersProvidingVisibility() {
        return CHARACTERS_PROVIDING_VISIBILITY;
    }

    @Override
    public IMap<ICoordinate, Integer> coordinatesProvidingVisibility() {
        return COORDINATES_PROVIDING_VISIBILITY;
    }

    @Override
    public void calculateVisibileTiles() {
        // TODO: Implement!
    }

    @Override
    public ICollection<ICoordinate> visibileTiles() {
        return VISIBLE_TILES;
    }

    @Override
    public IGame game() {
        return GAME;
    }

    @Override
    public ILogger logger() {
        return LOGGER;
    }

    @Override
    public String getInterfaceName() {
        return "soliloquy.gamestate.specs.ICamera";
    }
}

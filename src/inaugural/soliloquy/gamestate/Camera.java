package inaugural.soliloquy.gamestate;

import soliloquy.common.specs.*;
import soliloquy.game.primary.specs.IGame;
import soliloquy.gamestate.specs.ICamera;
import soliloquy.gamestate.specs.ICharacter;
import soliloquy.gamestate.specs.IGameState;
import soliloquy.logger.specs.ILogger;
import soliloquy.ruleset.gameconcepts.specs.ITileVisibility;

public class Camera implements ICamera {
    private final IGame GAME;
    private final ILogger LOGGER;
    private final ICoordinateFactory COORDINATE_FACTORY;
    private final IMap<ICharacter,Integer> CHARACTERS_PROVIDING_VISIBILITY;
    private final IMap<ICoordinate,Integer> COORDINATES_PROVIDING_VISIBILITY;
    private final ICollection<ICoordinate> VISIBLE_TILES;
    private final IGameState GAME_STATE;

    private int _tileLocationX;
    private int _tileLocationY;
    private int _pixelOffsetX;
    private int _pixelOffsetY;
    private int _tileRenderingRadius;
    private boolean _allTilesVisible;

    private ITileVisibility _tileVisibility;

    public Camera(IGame game, ILogger logger, ICoordinateFactory coordinateFactory,
                  ICollectionFactory collectionFactory, IMapFactory mapFactory,
                  IGameState gameState) {
        GAME = game;
        LOGGER = logger;
        COORDINATE_FACTORY = coordinateFactory;
        CHARACTERS_PROVIDING_VISIBILITY = mapFactory.make(new CharacterArchetype(), 0);
        COORDINATES_PROVIDING_VISIBILITY = mapFactory.make(new CoordinateArchetype(), 0);
        VISIBLE_TILES = collectionFactory.make(new CoordinateArchetype());
        GAME_STATE = gameState;
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
    public ITileVisibility getTileVisibility() {
        return _tileVisibility;
    }

    @Override
    public void setTileVisibility(ITileVisibility tileVisibility) {
        _tileVisibility = tileVisibility;
    }

    @Override
    public void calculateVisibileTiles() {
        visibileTiles().clear();
        // TODO: Add a test to ensure that this behaves appropriately
        if (_tileRenderingRadius == 0) {
            return;
        }
        if (_allTilesVisible) {
            int minX = Math.max(0, _tileLocationX - (_tileRenderingRadius - 1));
            // TODO: Add a test to ensure that maximum coordinates behave properly
            int maxX = Math.min(GAME_STATE.getCurrentGameZone().getDimensions().getX(),
                    _tileLocationX + (_tileRenderingRadius - 1));
            int minY = Math.max(0, _tileLocationY - (_tileRenderingRadius - 1));
            // TODO: Add a test to ensure that maximum coordinates behave properly
            int maxY = Math.min(GAME_STATE.getCurrentGameZone().getDimensions().getY(),
                    _tileLocationY + (_tileRenderingRadius - 1));
            for(int x = minX; x <= maxX; x++) {
                for (int y = minY; y <= maxY; y++) {
                    visibileTiles().add(COORDINATE_FACTORY.make(x,y));
                }
            }
        }
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

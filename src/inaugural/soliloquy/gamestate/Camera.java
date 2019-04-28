package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.CharacterArchetype;
import inaugural.soliloquy.gamestate.archetypes.CoordinateArchetype;
import soliloquy.common.specs.*;
import soliloquy.game.primary.specs.IGame;
import soliloquy.gamestate.specs.ICamera;
import soliloquy.gamestate.specs.ICharacter;
import soliloquy.gamestate.specs.IGameState;
import soliloquy.gamestate.specs.ITile;
import soliloquy.logger.specs.ILogger;
import soliloquy.ruleset.gameconcepts.specs.ITileVisibility;

import java.util.HashMap;
import java.util.Map;

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
    public void calculateVisibileTiles() throws IllegalStateException {
        if (_tileVisibility == null) {
            throw new IllegalStateException("Camera.calculateVisibleTiles: tileVisibility is null");
        }
        visibileTiles().clear();
        if (_tileRenderingRadius == 0) {
            return;
        }

        int minRenderingX = Math.max(0, _tileLocationX - (_tileRenderingRadius - 1));
        int maxRenderingX = Math.min(GAME_STATE.getCurrentGameZone().getMaxCoordinates().getX(),
                _tileLocationX + (_tileRenderingRadius - 1));
        int minRenderingY = Math.max(0, _tileLocationY - (_tileRenderingRadius - 1));
        int maxRenderingY = Math.min(GAME_STATE.getCurrentGameZone().getMaxCoordinates().getY(),
                _tileLocationY + (_tileRenderingRadius - 1));

        if (_allTilesVisible) {
            for(int x = minRenderingX; x <= maxRenderingX; x++) {
                for (int y = minRenderingY; y <= maxRenderingY; y++) {
                    visibileTiles().add(COORDINATE_FACTORY.make(x,y));
                }
            }
        } else {
            HashMap<ICoordinate,Integer> coordinatesProvidingVisibility = new HashMap<>();
            for(IPair<ICharacter,Integer> characterProvidingVisibility
                    : CHARACTERS_PROVIDING_VISIBILITY) {
                coordinatesProvidingVisibility.put(characterProvidingVisibility.getItem1().tile()
                        .getLocation(), characterProvidingVisibility.getItem2());
            }
            for(IPair<ICoordinate,Integer> coordinateProvidingVisibility
                    : COORDINATES_PROVIDING_VISIBILITY) {
                coordinatesProvidingVisibility.put(coordinateProvidingVisibility.getItem1(),
                        coordinateProvidingVisibility.getItem2());
            }
            for(Map.Entry<ICoordinate,Integer> coordinateProvidingVisibility
                    : coordinatesProvidingVisibility.entrySet()) {
                ICoordinate coordinate = coordinateProvidingVisibility.getKey();
                Integer coordinateVisibilityRadius = coordinateProvidingVisibility.getValue();
                ITile originTile = GAME_STATE.getCurrentGameZone().tile(coordinate);
                int minVisibleX = Math.max(0,
                        coordinate.getX() - (coordinateVisibilityRadius - 1));
                int maxVisibleX = Math.min(GAME_STATE.getCurrentGameZone().getMaxCoordinates()
                        .getX(), coordinate.getX() + (coordinateVisibilityRadius - 1));
                int minVisibleY = Math.max(0,
                        coordinate.getY() - (coordinateVisibilityRadius - 1));
                int maxVisibleY = Math.min(GAME_STATE.getCurrentGameZone().getMaxCoordinates()
                        .getY(), coordinate.getY() + (coordinateVisibilityRadius - 1));

                int minXToAdd = Math.max(minVisibleX, minRenderingX);
                int maxXToAdd = Math.min(maxVisibleX, maxRenderingX);
                int minYToAdd = Math.max(minVisibleY, minRenderingY);
                int maxYToAdd = Math.min(maxVisibleY, maxRenderingY);

                for (int x = minXToAdd; x <= maxXToAdd; x++) {
                    for (int y = minYToAdd; y <= maxYToAdd; y++) {
                        ICoordinate targetCoordinate = COORDINATE_FACTORY.make(x,y);
                        if (!visibleTilesContainsCoordinate(x, y)) {
                            ITile targetTile =
                                    GAME_STATE.getCurrentGameZone().tile(targetCoordinate);
                            if(_tileVisibility.canSeeTile(originTile, targetTile)) {
                                visibileTiles().add(targetCoordinate);
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean visibleTilesContainsCoordinate(int x, int y) {
        for(ICoordinate coordinate : visibileTiles()) {
            if (coordinate.getX() == x && coordinate.getY() == y) {
                return true;
            }
        }
        return false;
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
        return ICamera.class.getCanonicalName();
    }
}

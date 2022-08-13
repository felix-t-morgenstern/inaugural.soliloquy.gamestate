package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.gamestate.archetypes.CharacterArchetype;
import inaugural.soliloquy.gamestate.archetypes.CoordinateArchetype;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.gamestate.entities.Camera;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.ruleset.gameconcepts.TileVisibility;

import java.util.HashMap;
import java.util.function.Supplier;

public class CameraImpl implements Camera {
    private final CoordinateFactory COORDINATE_FACTORY;
    private final Map<Character, Integer> CHARACTERS_PROVIDING_VISIBILITY;
    private final Map<Coordinate, Integer> COORDINATES_PROVIDING_VISIBILITY;
    private final TileVisibility TILE_VISIBILITY;
    private final List<Coordinate> VISIBLE_TILES;
    private final Supplier<GameZone> GET_CURRENT_GAME_ZONE;

    private int _tileLocationX;
    private int _tileLocationY;
    private float _xTileWidthOffset;
    private float _yTileWidthOffset;
    private int _tileRenderingRadius;
    private boolean _allTilesVisible;

    @SuppressWarnings("ConstantConditions")
    public CameraImpl(CoordinateFactory coordinateFactory, ListFactory listFactory,
                      MapFactory mapFactory, TileVisibility tileVisibility,
                      Supplier<GameZone> getCurrentGameZone) {
        COORDINATE_FACTORY = Check.ifNull(coordinateFactory, "coordinateFactory");
        Check.ifNull(mapFactory, "mapFactory");
        CHARACTERS_PROVIDING_VISIBILITY = mapFactory.make(new CharacterArchetype(), 0);
        COORDINATES_PROVIDING_VISIBILITY = mapFactory.make(new CoordinateArchetype(), 0);
        TILE_VISIBILITY = Check.ifNull(tileVisibility, "tileVisibility");
        Check.ifNull(listFactory, "listFactory");
        VISIBLE_TILES = listFactory.make(new CoordinateArchetype());
        GET_CURRENT_GAME_ZONE = Check.ifNull(getCurrentGameZone, "getCurrentGameZone");
    }

    @Override
    public Coordinate getTileLocation() {
        return COORDINATE_FACTORY.make(_tileLocationX, _tileLocationY);
    }

    @Override
    public void setTileLocation(int x, int y) throws IllegalArgumentException {
        _tileLocationX = Check.ifNonNegative(x, "x");
        _tileLocationY = Check.ifNonNegative(y, "y");
    }

    // TODO: Test and implement
    @Override
    public float getXTileWidthOffset() {
        return _xTileWidthOffset;
    }

    @Override
    public float getYTileHeightOffset() {
        return _yTileWidthOffset;
    }

    @Override
    public void setXTileWidthOffset(float xTileWidthOffset) {
        _xTileWidthOffset = xTileWidthOffset;
    }

    @Override
    public void setYTileHeightOffset(float yTileHeightOffset) {
        _yTileWidthOffset = yTileHeightOffset;
    }

    @Override
    public int getTileRenderingRadius() {
        return _tileRenderingRadius;
    }

    @Override
    public void setTileRenderingRadius(int tileRenderingRadius) throws IllegalArgumentException {
        _tileRenderingRadius = Check.ifNonNegative(tileRenderingRadius, "tileRenderingRadius");
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
    public Map<Character, Integer> charactersProvidingVisibility() {
        return CHARACTERS_PROVIDING_VISIBILITY;
    }

    @Override
    public Map<Coordinate, Integer> coordinatesProvidingVisibility() {
        return COORDINATES_PROVIDING_VISIBILITY;
    }

    @Override
    public void calculateVisibleTiles() throws IllegalStateException {
        // TODO: Revisit whether this method truly needs to be this long
        VISIBLE_TILES.clear();
        if (_tileRenderingRadius == 0) {
            return;
        }

        GameZone gameZone = GET_CURRENT_GAME_ZONE.get();

        int minRenderingX = Math.max(0, _tileLocationX - (_tileRenderingRadius - 1));
        int maxRenderingX = Math.min(gameZone.maxCoordinates().getX(),
                _tileLocationX + (_tileRenderingRadius - 1));
        int minRenderingY = Math.max(0, _tileLocationY - (_tileRenderingRadius - 1));
        int maxRenderingY = Math.min(gameZone.maxCoordinates().getY(),
                _tileLocationY + (_tileRenderingRadius - 1));

        if (_allTilesVisible) {
            for (int x = minRenderingX; x <= maxRenderingX; x++) {
                for (int y = minRenderingY; y <= maxRenderingY; y++) {
                    VISIBLE_TILES.add(COORDINATE_FACTORY.make(x, y));
                }
            }
        }
        else {
            HashMap<Coordinate, Integer> coordinatesProvidingVisibility = new HashMap<>();
            CHARACTERS_PROVIDING_VISIBILITY.keySet().forEach(characterProvidingVisibility ->
                    coordinatesProvidingVisibility
                            .put(characterProvidingVisibility.tile().location(),
                                    CHARACTERS_PROVIDING_VISIBILITY
                                            .get(characterProvidingVisibility)));
            COORDINATES_PROVIDING_VISIBILITY.keySet().forEach(coordinateProvidingVisibility ->
                    coordinatesProvidingVisibility.put(coordinateProvidingVisibility,
                            COORDINATES_PROVIDING_VISIBILITY.get(coordinateProvidingVisibility)));
            coordinatesProvidingVisibility.forEach((coordinate, coordinateVisibilityRadius) -> {
                Tile originTile = gameZone.tile(coordinate.getX(), coordinate.getY());
                int minVisibleX = Math.max(0,
                        coordinate.getX() - (coordinateVisibilityRadius - 1));
                int maxVisibleX = Math.min(gameZone.maxCoordinates().getX(),
                        coordinate.getX() + (coordinateVisibilityRadius - 1));
                int minVisibleY = Math.max(0,
                        coordinate.getY() - (coordinateVisibilityRadius - 1));
                int maxVisibleY = Math.min(gameZone.maxCoordinates().getY(),
                        coordinate.getY() + (coordinateVisibilityRadius - 1));

                int minXToAdd = Math.max(minVisibleX, minRenderingX);
                int maxXToAdd = Math.min(maxVisibleX, maxRenderingX);
                int minYToAdd = Math.max(minVisibleY, minRenderingY);
                int maxYToAdd = Math.min(maxVisibleY, maxRenderingY);

                for (int x = minXToAdd; x <= maxXToAdd; x++) {
                    for (int y = minYToAdd; y <= maxYToAdd; y++) {
                        if (!visibleTilesContainsCoordinate(x, y)) {
                            Tile targetTile = gameZone.tile(x, y);
                            if (TILE_VISIBILITY.canSeeTile(originTile, targetTile)) {
                                VISIBLE_TILES.add(COORDINATE_FACTORY.make(x, y));
                            }
                        }
                    }
                }
            });
        }
    }

    private boolean visibleTilesContainsCoordinate(int x, int y) {
        for (Coordinate coordinate : VISIBLE_TILES) {
            if (coordinate.getX() == x && coordinate.getY() == y) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Coordinate> visibleTiles() {
        return VISIBLE_TILES;
    }

    @Override
    public String getInterfaceName() {
        return Camera.class.getCanonicalName();
    }
}

package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.CharacterArchetype;
import inaugural.soliloquy.gamestate.archetypes.CoordinateArchetype;
import inaugural.soliloquy.gamestate.archetypes.ReadableCoordinateArchetype;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;
import soliloquy.specs.gamestate.entities.Camera;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.ruleset.gameconcepts.TileVisibility;

import java.util.HashMap;
import java.util.function.Supplier;

public class CameraImpl implements Camera {
    private final CoordinateFactory COORDINATE_FACTORY;
    private final Map<Character,Integer> CHARACTERS_PROVIDING_VISIBILITY;
    private final Map<Coordinate,Integer> COORDINATES_PROVIDING_VISIBILITY;
    private final TileVisibility TILE_VISIBILITY;
    private final Collection<ReadableCoordinate> VISIBLE_TILES;
    private final Supplier<GameZone> GET_CURRENT_GAME_ZONE;

    private int _tileLocationX;
    private int _tileLocationY;
    private int _pixelOffsetX;
    private int _pixelOffsetY;
    private int _tileRenderingRadius;
    private boolean _allTilesVisible;

    @SuppressWarnings("ConstantConditions")
    public CameraImpl(CoordinateFactory coordinateFactory, CollectionFactory collectionFactory,
                      MapFactory mapFactory, TileVisibility tileVisibility,
                      Supplier<GameZone> getCurrentGameZone) {
        COORDINATE_FACTORY = Check.ifNull(coordinateFactory, "coordinateFactory");
        Check.ifNull(mapFactory, "mapFactory");
        CHARACTERS_PROVIDING_VISIBILITY = mapFactory.make(new CharacterArchetype(), 0);
        COORDINATES_PROVIDING_VISIBILITY = mapFactory.make(new CoordinateArchetype(), 0);
        TILE_VISIBILITY = Check.ifNull(tileVisibility, "tileVisibility");
        Check.ifNull(collectionFactory, "collectionFactory");
        VISIBLE_TILES = collectionFactory.make(new ReadableCoordinateArchetype());
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

    @Override
    public ReadableCoordinate getPixelOffset() {
        return COORDINATE_FACTORY.make(_pixelOffsetX, _pixelOffsetY).readOnlyRepresentation();
    }

    @Override
    public void setPixelOffset(int x, int y) throws IllegalArgumentException {
        _pixelOffsetX = Check.ifNonNegative(x, "x");
        _pixelOffsetY = Check.ifNonNegative(y, "y");
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
    public Map<Character,Integer> charactersProvidingVisibility() {
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
            for(int x = minRenderingX; x <= maxRenderingX; x++) {
                for (int y = minRenderingY; y <= maxRenderingY; y++) {
                    VISIBLE_TILES.add(COORDINATE_FACTORY.make(x,y));
                }
            }
        } else {
            HashMap<ReadableCoordinate,Integer> coordinatesProvidingVisibility = new HashMap<>();
            for(Pair<Character,Integer> characterProvidingVisibility
                    : CHARACTERS_PROVIDING_VISIBILITY) {
                coordinatesProvidingVisibility.put(characterProvidingVisibility.getItem1().tile()
                        .location(), characterProvidingVisibility.getItem2());
            }
            for(Pair<Coordinate,Integer> coordinateProvidingVisibility
                    : COORDINATES_PROVIDING_VISIBILITY) {
                coordinatesProvidingVisibility.put(coordinateProvidingVisibility.getItem1(),
                        coordinateProvidingVisibility.getItem2());
            }
            for(java.util.Map.Entry<ReadableCoordinate,Integer> coordinateProvidingVisibility
                    : coordinatesProvidingVisibility.entrySet()) {
                ReadableCoordinate coordinate = coordinateProvidingVisibility.getKey();
                Integer coordinateVisibilityRadius = coordinateProvidingVisibility.getValue();
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
                            if(TILE_VISIBILITY.canSeeTile(originTile, targetTile)) {
                                VISIBLE_TILES.add(COORDINATE_FACTORY.make(x,y));
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean visibleTilesContainsCoordinate(int x, int y) {
        for(ReadableCoordinate coordinate : VISIBLE_TILES) {
            if (coordinate.getX() == x && coordinate.getY() == y) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<ReadableCoordinate> visibleTiles() {
        return VISIBLE_TILES;
    }

    @Override
    public String getInterfaceName() {
        return Camera.class.getCanonicalName();
    }
}

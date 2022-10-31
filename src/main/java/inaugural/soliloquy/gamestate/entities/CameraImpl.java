package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.common.valueobjects.Vertex;
import soliloquy.specs.gamestate.entities.Camera;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.graphics.renderables.providers.ProviderAtTime;
import soliloquy.specs.ruleset.gameconcepts.TileVisibility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class CameraImpl implements Camera {
    private final Map<Character, Integer> CHARACTERS_PROVIDING_VISIBILITY;
    private final Map<Coordinate, Integer> COORDINATES_PROVIDING_VISIBILITY;
    private final TileVisibility TILE_VISIBILITY;
    private final List<Coordinate> VISIBLE_TILES;
    private final Supplier<GameZone> GET_CURRENT_GAME_ZONE;

    private Coordinate tileLocation;
    private ProviderAtTime<Vertex> tileCenterOffsetProvider;
    private int tileRenderingRadius;
    private float zoom;
    private boolean allTilesVisible;

    public CameraImpl(TileVisibility tileVisibility, Supplier<GameZone> getCurrentGameZone) {
        CHARACTERS_PROVIDING_VISIBILITY = new HashMap<>();
        COORDINATES_PROVIDING_VISIBILITY = new HashMap<>();
        TILE_VISIBILITY = Check.ifNull(tileVisibility, "tileVisibility");
        VISIBLE_TILES = new ArrayList<>();
        GET_CURRENT_GAME_ZONE = Check.ifNull(getCurrentGameZone, "getCurrentGameZone");
    }

    @Override
    public Coordinate getTileLocation() {
        return tileLocation;
    }

    @Override
    public void setTileLocation(Coordinate tileLocation) throws IllegalArgumentException {
        this.tileLocation = Check.ifNull(tileLocation, "tileLocation");
    }

    @Override
    public ProviderAtTime<Vertex> tileCenterOffsetProvider() {
        return tileCenterOffsetProvider;
    }

    @Override
    public void setTileCenterOffsetProvider(ProviderAtTime<Vertex> tileCenterOffsetProvider)
            throws IllegalArgumentException {
        this.tileCenterOffsetProvider =
                Check.ifNull(tileCenterOffsetProvider, "tileCenterOffsetProvider");
    }

    @Override
    public float getZoom() {
        return zoom;
    }

    @Override
    public void setZoom(float zoom) throws IllegalArgumentException {
        this.zoom = Check.throwOnLteZero(zoom, "zoom");
    }

    @Override
    public int getTileRenderingRadius() {
        return tileRenderingRadius;
    }

    @Override
    public void setTileRenderingRadius(int tileRenderingRadius) throws IllegalArgumentException {
        this.tileRenderingRadius = Check.ifNonNegative(tileRenderingRadius, "tileRenderingRadius");
    }

    @Override
    public boolean getAllTilesVisible() {
        return allTilesVisible;
    }

    @Override
    public void setAllTilesVisible(boolean allTilesVisible) {
        this.allTilesVisible = allTilesVisible;
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
        if (tileRenderingRadius == 0) {
            return;
        }

        GameZone gameZone = GET_CURRENT_GAME_ZONE.get();

        int minRenderingX = Math.max(0, tileLocation.x() - (tileRenderingRadius - 1));
        int maxRenderingX = Math.min(gameZone.maxCoordinates().x(),
                tileLocation.x() + (tileRenderingRadius - 1));
        int minRenderingY = Math.max(0, tileLocation.y() - (tileRenderingRadius - 1));
        int maxRenderingY = Math.min(gameZone.maxCoordinates().y(),
                tileLocation.y() + (tileRenderingRadius - 1));

        if (allTilesVisible) {
            for (int x = minRenderingX; x <= maxRenderingX; x++) {
                for (int y = minRenderingY; y <= maxRenderingY; y++) {
                    VISIBLE_TILES.add(Coordinate.of(x, y));
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
                Tile originTile = gameZone.tile(coordinate);
                int minVisibleX = Math.max(0, coordinate.x() - (coordinateVisibilityRadius - 1));
                int maxVisibleX = Math.min(gameZone.maxCoordinates().x(),
                        coordinate.x() + (coordinateVisibilityRadius - 1));
                int minVisibleY = Math.max(0, coordinate.y() - (coordinateVisibilityRadius - 1));
                int maxVisibleY = Math.min(gameZone.maxCoordinates().y(),
                        coordinate.y() + (coordinateVisibilityRadius - 1));

                int minXToAdd = Math.max(minVisibleX, minRenderingX);
                int maxXToAdd = Math.min(maxVisibleX, maxRenderingX);
                int minYToAdd = Math.max(minVisibleY, minRenderingY);
                int maxYToAdd = Math.min(maxVisibleY, maxRenderingY);

                for (int x = minXToAdd; x <= maxXToAdd; x++) {
                    for (int y = minYToAdd; y <= maxYToAdd; y++) {
                        if (!visibleTilesContainsCoordinate(x, y)) {
                            Tile targetTile = gameZone.tile(Coordinate.of(x, y));
                            if (TILE_VISIBILITY.canSeeTile(originTile, targetTile)) {
                                VISIBLE_TILES.add(Coordinate.of(x, y));
                            }
                        }
                    }
                }
            });
        }
    }

    private boolean visibleTilesContainsCoordinate(int x, int y) {
        for (Coordinate coordinate : VISIBLE_TILES) {
            if (coordinate.x() == x && coordinate.y() == y) {
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

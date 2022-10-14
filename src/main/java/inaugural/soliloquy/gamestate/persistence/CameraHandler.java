package inaugural.soliloquy.gamestate.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.common.valueobjects.Vertex;
import soliloquy.specs.gamestate.entities.Camera;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.factories.CameraFactory;
import soliloquy.specs.graphics.renderables.providers.ProviderAtTime;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import static inaugural.soliloquy.tools.generic.Archetypes.generateSimpleArchetype;

public class CameraHandler extends AbstractTypeHandler<Camera> {
    private final CameraFactory FACTORY;
    private final PersistentValuesHandler PERSISTENT_VALUES_HANDLER;
    private final Supplier<GameZone> GET_CURRENT_GAME_ZONE;


    public CameraHandler(CameraFactory factory, PersistentValuesHandler persistentValuesHandler,
                         Supplier<GameZone> getCurrentGameZone) {
        super(generateSimpleArchetype(Camera.class));
        FACTORY = Check.ifNull(factory, "factory");
        PERSISTENT_VALUES_HANDLER =
                Check.ifNull(persistentValuesHandler, "persistentValuesHandler");
        GET_CURRENT_GAME_ZONE = Check.ifNull(getCurrentGameZone, "getCurrentGameZone");
    }

    @Override
    public Camera read(String data) throws IllegalArgumentException {
        CameraDTO dto = JSON.fromJson(data, CameraDTO.class);

        Camera camera = FACTORY.make(GET_CURRENT_GAME_ZONE);

        camera.setTileLocation(Coordinate.of(dto.tileLocationX, dto.tileLocationY));

        TypeHandler<ProviderAtTime<Vertex>> tileCenterOffsetProviderHandler =
                PERSISTENT_VALUES_HANDLER.getTypeHandler(dto.tileCenterOffsetProviderType);
        ProviderAtTime<Vertex> tileCenterOffsetProvider =
                tileCenterOffsetProviderHandler.read(dto.tileCenterOffsetProvider);
        camera.setTileCenterOffsetProvider(tileCenterOffsetProvider);

        camera.setZoom(dto.zoom);

        camera.setTileRenderingRadius(dto.tileRenderingRadius);

        camera.setAllTilesVisible(dto.allTilesVisible);

        List<Character> charactersInCurrentGameZone =
                GET_CURRENT_GAME_ZONE.get().charactersRepresentation();
        for (CameraDTO.CharacterProvidingVisibilityDTO characterProvidingVisibilityDTO :
                dto.charactersProvidingVisibility) {
            UUID characterId = UUID.fromString(characterProvidingVisibilityDTO.characterId);
            Optional<Character> characterProvidingVisibilityFromGameZone =
                    charactersInCurrentGameZone.stream().filter(c -> characterId.equals(c.uuid()))
                            .findFirst();
            if (!characterProvidingVisibilityFromGameZone.isPresent()) {
                throw new IllegalStateException("CameraHandler.read: character with UUID = " +
                        characterProvidingVisibilityDTO.characterId +
                        " not present in current GameZone");
            }
            Character characterProvidingVisibility = characterProvidingVisibilityFromGameZone.get();
            camera.charactersProvidingVisibility()
                    .put(characterProvidingVisibility, characterProvidingVisibilityDTO.tiles);
        }

        for (CameraDTO.CoordinateProvidingVisibilityDTO coordinateProvidingVisibilityDTO :
                dto.coordinatesProvidingVisibility) {
            camera.coordinatesProvidingVisibility()
                    .put(Coordinate.of(coordinateProvidingVisibilityDTO.x,
                                    coordinateProvidingVisibilityDTO.y),
                            coordinateProvidingVisibilityDTO.tiles);
        }

        return camera;
    }

    @Override
    public String write(Camera camera) {
        Check.ifNull(camera, "camera");

        CameraDTO dto = new CameraDTO();

        dto.tileLocationX = camera.getTileLocation().x();
        dto.tileLocationY = camera.getTileLocation().y();

        String tileCenterOffsetProviderType = camera.tileCenterOffsetProvider().getInterfaceName();
        TypeHandler<ProviderAtTime<Vertex>> tileCenterOffsetProviderHandler =
                PERSISTENT_VALUES_HANDLER.getTypeHandler(tileCenterOffsetProviderType);
        dto.tileCenterOffsetProviderType = tileCenterOffsetProviderType;
        dto.tileCenterOffsetProvider =
                tileCenterOffsetProviderHandler.write(camera.tileCenterOffsetProvider());

        dto.zoom = camera.getZoom();

        dto.tileRenderingRadius = camera.getTileRenderingRadius();

        dto.allTilesVisible = camera.getAllTilesVisible();

        Map<Character, Integer> charactersProvidingVisibility =
                camera.charactersProvidingVisibility();
        dto.charactersProvidingVisibility =
                new CameraDTO.CharacterProvidingVisibilityDTO[charactersProvidingVisibility.size()];
        int index = 0;
        for (Map.Entry<Character, Integer> entry : charactersProvidingVisibility.entrySet()) {
            CameraDTO.CharacterProvidingVisibilityDTO characterProvidingVisibilityDTO =
                    new CameraDTO.CharacterProvidingVisibilityDTO();
            characterProvidingVisibilityDTO.characterId = entry.getKey().uuid().toString();
            characterProvidingVisibilityDTO.tiles = entry.getValue();
            dto.charactersProvidingVisibility[index++] = characterProvidingVisibilityDTO;
        }

        Map<Coordinate, Integer> coordinatesProvidingVisibility =
                camera.coordinatesProvidingVisibility();
        dto.coordinatesProvidingVisibility =
                new CameraDTO.CoordinateProvidingVisibilityDTO[coordinatesProvidingVisibility.size()];
        index = 0;
        for (Map.Entry<Coordinate, Integer> entry : coordinatesProvidingVisibility.entrySet()) {
            CameraDTO.CoordinateProvidingVisibilityDTO coordinateProvidingVisibilityDTO =
                    new CameraDTO.CoordinateProvidingVisibilityDTO();
            coordinateProvidingVisibilityDTO.x = entry.getKey().x();
            coordinateProvidingVisibilityDTO.y = entry.getKey().y();
            coordinateProvidingVisibilityDTO.tiles = entry.getValue();
            dto.coordinatesProvidingVisibility[index++] = coordinateProvidingVisibilityDTO;
        }

        return JSON.toJson(dto);
    }

    private static class CameraDTO {
        int tileLocationX;
        int tileLocationY;
        String tileCenterOffsetProviderType;
        String tileCenterOffsetProvider;
        float zoom;
        int tileRenderingRadius;
        boolean allTilesVisible;
        CharacterProvidingVisibilityDTO[] charactersProvidingVisibility;
        CoordinateProvidingVisibilityDTO[] coordinatesProvidingVisibility;

        private static class CharacterProvidingVisibilityDTO {
            String characterId;
            int tiles;
        }

        private static class CoordinateProvidingVisibilityDTO {
            int x;
            int y;
            int tiles;
        }
    }
}

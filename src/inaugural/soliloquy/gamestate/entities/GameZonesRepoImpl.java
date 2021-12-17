package inaugural.soliloquy.gamestate.entities;

import soliloquy.specs.common.exceptions.SoliloquyIOException;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.GameZonesRepo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameZonesRepoImpl implements GameZonesRepo {
    private final TypeHandler<GameZone> GAME_ZONE_HANDLER;
    private final HashMap<String, Path> FILE_LOCATIONS = new HashMap<>();

    @SuppressWarnings("ConstantConditions")
    public GameZonesRepoImpl(TypeHandler<GameZone> gameZoneHandler,
                             Map<String, Path> fileLocations) {
        if (gameZoneHandler == null) {
            throw new IllegalArgumentException(
                    "GameZonesRepoImpl: gameZoneHandler cannot be null");
        }
        GAME_ZONE_HANDLER = gameZoneHandler;
        if (fileLocations == null) {
            throw new IllegalArgumentException(
                    "GameZonesRepoImpl: fileLocations cannot be null");
        }
        FILE_LOCATIONS.putAll(fileLocations);
    }

    @Override
    public GameZone getGameZone(String id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("GameZonesRepoImpl.getGameZone: id cannot be null");
        }
        if (id.equals("")) {
            throw new IllegalArgumentException(
                    "GameZonesRepoImpl.getGameZone: id cannot be empty");
        }
        if (!FILE_LOCATIONS.containsKey(id)) {
            throw new IllegalArgumentException(
                    "GameZonesRepoImpl.getGameZone: no file location corresponding to id");
        }
        try {
            String fileContents = new String(Files.readAllBytes(FILE_LOCATIONS.get(id)));
            return GAME_ZONE_HANDLER.read(fileContents);
        } catch (IOException e) {
            throw new SoliloquyIOException(e);
        }
    }

    @Override
    public void saveGameZone(GameZone gameZone) throws IllegalArgumentException {
        if (gameZone == null) {
            throw new IllegalArgumentException(
                    "GameZonesRepoImpl.saveGameZone: gameZone cannot be null");
        }
        if (!FILE_LOCATIONS.containsKey(gameZone.id())) {
            throw new IllegalArgumentException(
                    "GameZonesRepoImpl.saveGameZone: no file location corresponding to " +
                            "gameZone.id");
        }
        Path filePath = FILE_LOCATIONS.get(gameZone.id());
        File file = new File(filePath.toString());
        try {
            if (!file.delete()) {
                throw new IOException("GameZoneRepoImpl.saveGameZone: Could not delete file (" +
                        filePath.toString() + ")");
            }
            Files.write(filePath, new ArrayList<>());
            Files.write(filePath,
                    GAME_ZONE_HANDLER.write(gameZone).getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new SoliloquyIOException(e);
        }
    }

    @Override
    public String getInterfaceName() {
        return GameZonesRepo.class.getCanonicalName();
    }
}

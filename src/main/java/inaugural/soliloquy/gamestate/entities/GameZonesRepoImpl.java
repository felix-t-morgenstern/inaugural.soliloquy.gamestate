package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.exceptions.SoliloquyIOException;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.GameZonesRepo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameZonesRepoImpl implements GameZonesRepo {
    private final TypeHandler<GameZone> GAME_ZONE_HANDLER;
    private final HashMap<String, Path> FILE_LOCATIONS = new HashMap<>();

    public GameZonesRepoImpl(TypeHandler<GameZone> gameZoneHandler,
                             Map<String, Path> fileLocations) {
        GAME_ZONE_HANDLER = Check.ifNull(gameZoneHandler, "gameZoneHandler");
        Check.ifNull(fileLocations, "fileLocations");
        FILE_LOCATIONS.putAll(fileLocations);
    }

    @Override
    public GameZone getGameZone(String id) throws IllegalArgumentException {
        Check.ifNull(id, "id");
        if (!FILE_LOCATIONS.containsKey(id)) {
            throw new IllegalArgumentException(
                    "GameZonesRepoImpl.getGameZone: no file location corresponding to id");
        }
        try {
            var fileContents = new String(Files.readAllBytes(FILE_LOCATIONS.get(id)));
            return GAME_ZONE_HANDLER.read(fileContents);
        }
        catch (IOException e) {
            throw new SoliloquyIOException(e);
        }
    }

    @Override
    public void saveGameZone(GameZone gameZone) throws IllegalArgumentException {
        Check.ifNull(gameZone, "gameZone");
        if (!FILE_LOCATIONS.containsKey(gameZone.id())) {
            throw new IllegalArgumentException(
                    "GameZonesRepoImpl.saveGameZone: no file location corresponding to " +
                            "gameZone.id");
        }
        var filePath = FILE_LOCATIONS.get(gameZone.id());
        var file = new File(filePath.toString());
        try {
            if (!file.delete()) {
                throw new IOException("GameZoneRepoImpl.saveGameZone: Could not delete file (" +
                        filePath + ")");
            }
            Files.write(filePath, new ArrayList<>());
            Files.writeString(filePath,
                    GAME_ZONE_HANDLER.write(gameZone),
                    StandardOpenOption.APPEND);
        }
        catch (IOException e) {
            throw new SoliloquyIOException(e);
        }
    }

    @Override
    public String getInterfaceName() {
        return GameZonesRepo.class.getCanonicalName();
    }
}

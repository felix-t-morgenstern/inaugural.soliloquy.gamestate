package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.exceptions.SoliloquyIOException;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.GameZoneRepo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.collections.Collections.mapOf;

public class GameZoneRepoImpl implements GameZoneRepo {
    private final TypeHandler<GameZone> GAME_ZONE_HANDLER;
    private final Map<String, Path> FILE_LOCATIONS = mapOf();

    private GameZone currentGameZone;

    public GameZoneRepoImpl(TypeHandler<GameZone> gameZoneHandler,
                            Map<String, Path> fileLocations) {
        GAME_ZONE_HANDLER = Check.ifNull(gameZoneHandler, "gameZoneHandler");
        Check.ifNull(fileLocations, "fileLocations");
        FILE_LOCATIONS.putAll(fileLocations);
    }

    @Override
    public GameZone currentGameZone() {
        return currentGameZone;
    }

    @Override
    public void loadGameZone(String id)
            throws IllegalArgumentException, UnsupportedOperationException {
        if (currentGameZone != null) {
            throw new UnsupportedOperationException(
                    "GameZoneRepoImpl.loadGameZone: GameZone has already been loaded");
        }
        Check.ifNull(id, "id");
        if (!FILE_LOCATIONS.containsKey(id)) {
            throw new IllegalArgumentException(
                    "GameZoneRepoImpl.getGameZone: no file location corresponding to id");
        }
        try {
            var fileContents = new String(Files.readAllBytes(FILE_LOCATIONS.get(id)));
            currentGameZone = GAME_ZONE_HANDLER.read(fileContents);
        }
        catch (IOException e) {
            throw new SoliloquyIOException(e);
        }
    }

    @Override
    public void unloadGameZone() {
        currentGameZone = null;
    }

    @Override
    public void saveGameZone(GameZone gameZone) throws IllegalArgumentException {
        Check.ifNull(gameZone, "gameZone");
        if (!FILE_LOCATIONS.containsKey(gameZone.id())) {
            throw new IllegalArgumentException(
                    "GameZoneRepoImpl.saveGameZone: no file location corresponding to " +
                            "gameZone.id");
        }
        var filePath = FILE_LOCATIONS.get(gameZone.id());
        var file = new File(filePath.toString());
        try {
            if (!file.delete()) {
                throw new IOException("GameZoneRepoImpl.saveGameZone: Could not delete file (" +
                        filePath + ")");
            }
            Files.write(filePath, listOf());
            Files.writeString(filePath,
                    GAME_ZONE_HANDLER.write(gameZone),
                    StandardOpenOption.APPEND);
        }
        catch (IOException e) {
            throw new SoliloquyIOException(e);
        }
    }
}

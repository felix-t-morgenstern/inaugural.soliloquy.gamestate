package inaugural.soliloquy.gamestate.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.GameState;
import soliloquy.specs.gamestate.entities.Camera;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Party;
import soliloquy.specs.gamestate.entities.RoundManager;
import soliloquy.specs.gamestate.entities.timers.ClockBasedTimerManager;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;

import static inaugural.soliloquy.tools.generic.Archetypes.generateSimpleArchetype;

public class GameStateHandler extends AbstractTypeHandler<GameState> {
    private final GameState GAME_STATE;
    private final TypeHandler<Party> PARTY_HANDLER;
    private final TypeHandler<VariableCache> VARIABLE_CACHE_HANDLER;
    private final TypeHandler<Camera> CAMERA_HANDLER;
    private final TypeHandler<RoundManager> ROUND_MANAGER_HANDLER;
    private final TypeHandler<RoundBasedTimerManager> ROUND_BASED_TIMER_HANDLER;
    private final TypeHandler<ClockBasedTimerManager> CLOCK_BASED_TIMER_HANDLER;

    public GameStateHandler(GameState gameState,
                            TypeHandler<Party> partyHandler,
                            TypeHandler<VariableCache> variableCacheHandler,
                            TypeHandler<Camera> cameraHandler,
                            TypeHandler<RoundManager> roundManagerHandler,
                            TypeHandler<RoundBasedTimerManager> roundBasedTimerHandler,
                            TypeHandler<ClockBasedTimerManager> clockBasedTimerHandler) {
        super(generateSimpleArchetype(GameState.class));

        GAME_STATE = Check.ifNull(gameState, "gameState");
        PARTY_HANDLER = Check.ifNull(partyHandler, "partyHandler");
        VARIABLE_CACHE_HANDLER = Check.ifNull(variableCacheHandler, "variableCacheHandler");
        CAMERA_HANDLER = Check.ifNull(cameraHandler, "cameraHandler");
        ROUND_MANAGER_HANDLER = Check.ifNull(roundManagerHandler, "roundManagerHandler");
        ROUND_BASED_TIMER_HANDLER = Check.ifNull(roundBasedTimerHandler, "roundBasedTimerHandler");
        CLOCK_BASED_TIMER_HANDLER = Check.ifNull(clockBasedTimerHandler, "clockBasedTimerHandler");
    }

    @Override
    public GameState read(String writtenData) throws IllegalArgumentException {
        Check.ifNullOrEmpty(writtenData, "writtenData");

        GameStateDTO dto = JSON.fromJson(writtenData, GameStateDTO.class);

        GameZone currentGameZone = GAME_STATE.gameZonesRepo().getGameZone(dto.currentGameZoneId);
        GAME_STATE.setCurrentGameZone(currentGameZone);

        PARTY_HANDLER.read(dto.party);
        VARIABLE_CACHE_HANDLER.read(dto.variableCache);
        CAMERA_HANDLER.read(dto.camera);
        ROUND_MANAGER_HANDLER.read(dto.roundManager);
        ROUND_BASED_TIMER_HANDLER.read(dto.roundBasedTimerHandler);
        CLOCK_BASED_TIMER_HANDLER.read(dto.clockBasedTimerHandler);

        return null;
    }

    @Override
    public String write(GameState gameState) {
        Check.ifNull(gameState, "gameState");

        GameStateDTO dto = new GameStateDTO();

        dto.party = PARTY_HANDLER.write(gameState.party());
        dto.variableCache = VARIABLE_CACHE_HANDLER.write(gameState.getVariableCache());
        dto.currentGameZoneId = gameState.getCurrentGameZone().id();
        dto.camera = CAMERA_HANDLER.write(gameState.camera());
        dto.roundManager = ROUND_MANAGER_HANDLER.write(gameState.roundManager());
        dto.roundBasedTimerHandler =
                ROUND_BASED_TIMER_HANDLER.write(gameState.roundBasedTimerManager());
        dto.clockBasedTimerHandler =
                CLOCK_BASED_TIMER_HANDLER.write(gameState.clockBasedTimerManager());

        return JSON.toJson(dto);
    }

    private static class GameStateDTO {
        String party;
        String variableCache;
        String currentGameZoneId;
        String camera;
        String roundManager;
        String roundBasedTimerHandler;
        String clockBasedTimerHandler;
    }
}

package inaugural.soliloquy.gamestate.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.GameState;
import soliloquy.specs.gamestate.entities.Party;
import soliloquy.specs.gamestate.entities.RoundManager;
import soliloquy.specs.gamestate.entities.timers.ClockBasedTimerManager;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;

import java.util.Map;

public class GameStateHandler extends AbstractTypeHandler<GameState> {
    private final GameState GAME_STATE;
    private final TypeHandler<Party> PARTY_HANDLER;
    @SuppressWarnings("rawtypes") private final TypeHandler<Map> MAP_HANDLER;
    private final TypeHandler<RoundManager> ROUND_MANAGER_HANDLER;
    private final TypeHandler<RoundBasedTimerManager> ROUND_BASED_TIMER_HANDLER;
    private final TypeHandler<ClockBasedTimerManager> CLOCK_BASED_TIMER_HANDLER;

    public GameStateHandler(GameState gameState,
                            TypeHandler<Party> partyHandler,
                            @SuppressWarnings("rawtypes") TypeHandler<Map> mapHandler,
                            TypeHandler<RoundManager> roundManagerHandler,
                            TypeHandler<RoundBasedTimerManager> roundBasedTimerHandler,
                            TypeHandler<ClockBasedTimerManager> clockBasedTimerHandler) {
        GAME_STATE = Check.ifNull(gameState, "gameState");
        PARTY_HANDLER = Check.ifNull(partyHandler, "partyHandler");
        MAP_HANDLER = Check.ifNull(mapHandler, "mapHandler");
        ROUND_MANAGER_HANDLER = Check.ifNull(roundManagerHandler, "roundManagerHandler");
        ROUND_BASED_TIMER_HANDLER = Check.ifNull(roundBasedTimerHandler, "roundBasedTimerHandler");
        CLOCK_BASED_TIMER_HANDLER = Check.ifNull(clockBasedTimerHandler, "clockBasedTimerHandler");
    }

    @SuppressWarnings("unchecked")
    @Override
    public GameState read(String writtenData) throws IllegalArgumentException {
        Check.ifNullOrEmpty(writtenData, "writtenData");

        GameStateDTO dto = JSON.fromJson(writtenData, GameStateDTO.class);

        GAME_STATE.gameZoneRepo().loadGameZone(dto.currentGameZoneId);

        PARTY_HANDLER.read(dto.party);
        MAP_HANDLER.read(dto.data);
        ROUND_MANAGER_HANDLER.read(dto.roundManager);
        ROUND_BASED_TIMER_HANDLER.read(dto.roundBasedTimerManager);
        CLOCK_BASED_TIMER_HANDLER.read(dto.clockBasedTimerManager);

        return null;
    }

    @Override
    public String write(GameState gameState) {
        Check.ifNull(gameState, "gameState");

        var dto = new GameStateDTO();

        dto.party = PARTY_HANDLER.write(gameState.party());
        dto.data = MAP_HANDLER.write(gameState.data());
        dto.currentGameZoneId = gameState.gameZoneRepo().currentGameZone().id();
        dto.roundManager = ROUND_MANAGER_HANDLER.write(gameState.roundManager());
        dto.roundBasedTimerManager =
                ROUND_BASED_TIMER_HANDLER.write(gameState.roundBasedTimerManager());
        dto.clockBasedTimerManager =
                CLOCK_BASED_TIMER_HANDLER.write(gameState.clockBasedTimerManager());

        return JSON.toJson(dto);
    }

    private static class GameStateDTO {
        String party;
        String data;
        String currentGameZoneId;
        String roundManager;
        String roundBasedTimerManager;
        String clockBasedTimerManager;
    }
}

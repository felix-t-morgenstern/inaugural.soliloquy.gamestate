package inaugural.soliloquy.gamestate.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.RoundManager;

import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class RoundManagerHandler extends AbstractTypeHandler<RoundManager> {
    private final RoundManager ROUND_MANAGER;
    @SuppressWarnings("rawtypes") private final TypeHandler<Map> MAP_HANDLER;
    private final Function<UUID, Character> GET_CHARACTER_FROM_CURRENT_GAME_ZONE;

    public RoundManagerHandler(RoundManager roundManager,
                               @SuppressWarnings("rawtypes") TypeHandler<Map> mapHandler,
                               Function<UUID, Character> getCharacterFromCurrentGameZone) {

        ROUND_MANAGER = Check.ifNull(roundManager, "roundManager");
        MAP_HANDLER = Check.ifNull(mapHandler, "mapHandler");
        GET_CHARACTER_FROM_CURRENT_GAME_ZONE =
                Check.ifNull(getCharacterFromCurrentGameZone, "getCharacterFromCurrentGameZone");
    }

    @SuppressWarnings("unchecked")
    @Override
    public RoundManager read(String writtenValue) throws IllegalArgumentException {
        Check.ifNullOrEmpty(writtenValue, "writtenValue");

        ROUND_MANAGER.clearQueue();

        var dto = JSON.fromJson(writtenValue, DTO.class);

        ROUND_MANAGER.setRoundNumber(dto.roundNumber);

        for (var i = 0; i < dto.characterIds.length; i++) {
            var character = GET_CHARACTER_FROM_CURRENT_GAME_ZONE.apply(
                    UUID.fromString(dto.characterIds[i]));
            var characterRoundData = MAP_HANDLER.read(dto.characterRoundData[i]);
            ROUND_MANAGER.setCharacterPositionInQueue(character, i);
            //noinspection unchecked
            ROUND_MANAGER.setCharacterRoundData(character, characterRoundData);
        }

        return null;
    }

    @Override
    public String write(RoundManager roundManager) {
        Check.ifNull(roundManager, "roundManager");

        var dto = new DTO();

        dto.roundNumber = roundManager.getRoundNumber();

        var queue = roundManager.characterQueueRepresentation();
        dto.characterIds = new String[queue.size()];
        dto.characterRoundData = new String[queue.size()];

        for (var i = 0; i < queue.size(); i++) {
            dto.characterIds[i] = queue.get(i).FIRST.uuid().toString();
            dto.characterRoundData[i] = MAP_HANDLER.write(queue.get(i).SECOND);
        }

        return JSON.toJson(dto);
    }

    private static class DTO {
        int roundNumber;
        String[] characterIds;
        String[] characterRoundData;
    }
}

package inaugural.soliloquy.gamestate.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.RoundManager;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import static inaugural.soliloquy.tools.generic.Archetypes.generateSimpleArchetype;

public class RoundManagerHandler extends AbstractTypeHandler<RoundManager> {
    private final RoundManager ROUND_MANAGER;
    private final TypeHandler<VariableCache> VARIABLE_CACHE_HANDLER;
    private final Function<UUID, Character> GET_CHARACTER_FROM_CURRENT_GAME_ZONE;

    public RoundManagerHandler(RoundManager roundManager,
                               TypeHandler<VariableCache> variableCacheHandler,
                               Function<UUID, Character> getCharacterFromCurrentGameZone) {
        super(generateSimpleArchetype(RoundManager.class));

        ROUND_MANAGER = Check.ifNull(roundManager, "roundManager");
        VARIABLE_CACHE_HANDLER = Check.ifNull(variableCacheHandler, "variableCacheHandler");
        GET_CHARACTER_FROM_CURRENT_GAME_ZONE =
                Check.ifNull(getCharacterFromCurrentGameZone, "getCharacterFromCurrentGameZone");
    }

    @Override
    public RoundManager read(String writtenValue) throws IllegalArgumentException {
        Check.ifNullOrEmpty(writtenValue, "writtenValue");

        ROUND_MANAGER.clearQueue();

        RoundManagerDTO dto = JSON.fromJson(writtenValue, RoundManagerDTO.class);

        ROUND_MANAGER.setRoundNumber(dto.roundNumber);

        for (int i = 0; i < dto.characterIds.length; i++) {
            Character character = GET_CHARACTER_FROM_CURRENT_GAME_ZONE.apply(
                    UUID.fromString(dto.characterIds[i]));
            VariableCache characterRoundData =
                    VARIABLE_CACHE_HANDLER.read(dto.characterRoundData[i]);
            ROUND_MANAGER.setCharacterPositionInQueue(character, i);
            ROUND_MANAGER.setCharacterRoundData(character, characterRoundData);
        }

        return null;
    }

    @Override
    public String write(RoundManager roundManager) {
        Check.ifNull(roundManager, "roundManager");

        RoundManagerDTO dto = new RoundManagerDTO();

        dto.roundNumber = roundManager.getRoundNumber();

        List<Pair<Character, VariableCache>> queue = roundManager.characterQueueRepresentation();
        dto.characterIds = new String[queue.size()];
        dto.characterRoundData = new String[queue.size()];

        for (int i = 0; i < queue.size(); i++) {
            dto.characterIds[i] = queue.get(i).getItem1().uuid().toString();
            dto.characterRoundData[i] = VARIABLE_CACHE_HANDLER.write(queue.get(i).getItem2());
        }

        return JSON.toJson(dto);
    }

    private static class RoundManagerDTO {
        int roundNumber;
        String[] characterIds;
        String[] characterRoundData;
    }
}

package inaugural.soliloquy.gamestate.persistentvaluetypehandlers;

import com.google.gson.Gson;
import inaugural.soliloquy.gamestate.archetypes.GameStateArchetype;
import inaugural.soliloquy.tools.persistence.PersistentTypeHandler;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.PersistentValueTypeHandler;
import soliloquy.specs.gamestate.GameState;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.timers.OneTimeTurnBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RecurringTurnBasedTimer;
import soliloquy.specs.gamestate.factories.GameStateFactory;
import soliloquy.specs.gamestate.factories.PartyFactory;

import java.util.ArrayList;
import java.util.Arrays;

public class PersistentGameStateHandler extends PersistentTypeHandler<GameState> {
    private final GameStateFactory GAME_STATE_FACTORY;
    private final PartyFactory PARTY_FACTORY;
    private final GameZonesRepo GAME_ZONES_REPO;
    private final PersistentValueTypeHandler<VariableCache> VARIABLE_CACHE_HANDLER;
    private final PersistentValueTypeHandler<Character> CHARACTER_HANDLER;
    private final PersistentValueTypeHandler<OneTimeTurnBasedTimer>
            ONE_TIME_TURN_BASED_TIMER_HANDLER;
    private final PersistentValueTypeHandler<RecurringTurnBasedTimer>
            RECURRING_TURN_BASED_TIMER_HANDLER;

    private static final GameState ARCHETYPE = new GameStateArchetype();

    @SuppressWarnings("ConstantConditions")
    public PersistentGameStateHandler(GameStateFactory gameStateFactory,
                                      PartyFactory partyFactory,
                                      GameZonesRepo gameZonesRepo,
                                      PersistentValueTypeHandler<VariableCache>
                                              variableCacheHandler,
                                      PersistentValueTypeHandler<Character> characterHandler,
                                      PersistentValueTypeHandler<OneTimeTurnBasedTimer>
                                                  oneTimeTurnBasedTimerHandler,
                                      PersistentValueTypeHandler<RecurringTurnBasedTimer>
                                                  recurringTurnBasedTimerHandler) {
        if (gameStateFactory == null) {
            throw new IllegalArgumentException(
                    "PersistentGameStateHandler: gameStateFactory cannot be null");
        }
        GAME_STATE_FACTORY = gameStateFactory;
        if (partyFactory == null) {
            throw new IllegalArgumentException(
                    "PersistentGameStateHandler: partyFactory cannot be null");
        }
        PARTY_FACTORY = partyFactory;
        if (gameZonesRepo == null) {
            throw new IllegalArgumentException(
                    "PersistentGameStateHandler: gameZonesRepo cannot be null");
        }
        GAME_ZONES_REPO = gameZonesRepo;
        if (variableCacheHandler == null) {
            throw new IllegalArgumentException(
                    "PersistentGameStateHandler: variableCacheHandler cannot be null");
        }
        VARIABLE_CACHE_HANDLER = variableCacheHandler;
        if (characterHandler == null) {
            throw new IllegalArgumentException(
                    "PersistentGameStateHandler: characterHandler cannot be null");
        }
        CHARACTER_HANDLER = characterHandler;
        if (oneTimeTurnBasedTimerHandler == null) {
            throw new IllegalArgumentException(
                    "PersistentGameStateHandler: oneTimeTurnBasedTimerHandler cannot be null");
        }
        ONE_TIME_TURN_BASED_TIMER_HANDLER = oneTimeTurnBasedTimerHandler;
        if (recurringTurnBasedTimerHandler == null) {
            throw new IllegalArgumentException(
                    "PersistentGameStateHandler: recurringTurnBasedTimerHandler cannot be null");
        }
        RECURRING_TURN_BASED_TIMER_HANDLER = recurringTurnBasedTimerHandler;
    }

    @Override
    public GameState getArchetype() {
        return ARCHETYPE;
    }

    @Override
    public GameState read(String writtenData) throws IllegalArgumentException {
        if (writtenData == null) {
            throw new IllegalArgumentException(
                    "PersistentGameStateHandler.read: writtenData cannot be null");
        }
        if (writtenData.equals("")) {
            throw new IllegalArgumentException(
                    "PersistentGameStateHandler.read: writtenData cannot be empty");
        }
        GameStateDTO dto = new Gson().fromJson(writtenData, GameStateDTO.class);
        Party party = PARTY_FACTORY.make(VARIABLE_CACHE_HANDLER.read(dto.partyAttributes));
        GameState gameState = GAME_STATE_FACTORY.make(party,
                VARIABLE_CACHE_HANDLER.read(dto.data));
        GameZone currentGameZone = GAME_ZONES_REPO.getGameZone(dto.currentGameZoneId);
        gameState.setCurrentGameZone(currentGameZone);
        Arrays.stream(dto.pcsInCurrentGameZone).forEach(pcDto ->
                party.characters().add(findInTile(currentGameZone, pcDto.id, pcDto.x, pcDto.y)));
        Arrays.stream(dto.pcsNotInCurrentGameZone).forEach(pc ->
                party.characters().add(CHARACTER_HANDLER.read(pc)));
        gameState.roundManager().setRoundNumber(dto.roundNumber);
        for (CharacterInRoundDTO charDto : dto.charsInRound) {
            gameState.roundManager().setCharacterPositionInQueue(
                    findInTile(currentGameZone, charDto.id, charDto.x, charDto.y),
                    Integer.MAX_VALUE, VARIABLE_CACHE_HANDLER.read(charDto.data)
            );
        }
        Arrays.stream(dto.oneTimeTurnBasedTimers).forEach(ONE_TIME_TURN_BASED_TIMER_HANDLER::read);
        Arrays.stream(dto.recurringTurnBasedTimers).forEach(RECURRING_TURN_BASED_TIMER_HANDLER::read);
        return gameState;
    }

    private Character findInTile(GameZone currentGameZone, String id, int x, int y) {
        for (Pair<Character, Integer> character :
                currentGameZone.tile(x, y).characters()) {
            if (character.getItem1().uuid().toString().equals(id)) {
                return character.getItem1();
            }
        }
        throw new IllegalArgumentException("PersistentGameStateHandler: character " + id +
                " not found at (" + x + "," + y + ")");
    }

    // TODO: Organize this method a bit better
    @Override
    public String write(GameState gameState) {
        if (gameState == null) {
            throw new IllegalArgumentException(
                    "PersistentGameStateHandler.write: gameState cannot be null");
        }
        GameStateDTO dto = new GameStateDTO();
        dto.currentGameZoneId = gameState.getCurrentGameZone().id();
        dto.data = VARIABLE_CACHE_HANDLER.write(gameState.variableCache());
        dto.partyAttributes = VARIABLE_CACHE_HANDLER.write(gameState.party().attributes());
        ArrayList<Character> pcsInGameZone = new ArrayList<>();
        ArrayList<Character> pcsNotInGameZone = new ArrayList<>();
        for(Character pc : gameState.party().characters()) {
            if (pc.tile() == null) {
                pcsNotInGameZone.add(pc);
            } else {
                pcsInGameZone.add(pc);
            }
        }
        dto.pcsInCurrentGameZone = new PcInGameZoneDTO[pcsInGameZone.size()];
        dto.pcsNotInCurrentGameZone = new String[pcsNotInGameZone.size()];
        for (int i = 0; i < pcsInGameZone.size(); i++) {
            dto.pcsInCurrentGameZone[i] = new PcInGameZoneDTO();
            dto.pcsInCurrentGameZone[i].x = pcsInGameZone.get(i).tile().location().getX();
            dto.pcsInCurrentGameZone[i].y = pcsInGameZone.get(i).tile().location().getY();
            dto.pcsInCurrentGameZone[i].id = pcsInGameZone.get(i).uuid().toString();
        }
        for (int i = 0; i < pcsNotInGameZone.size(); i++) {
            dto.pcsNotInCurrentGameZone[i] = CHARACTER_HANDLER.write(pcsNotInGameZone.get(i));
        }
        dto.roundNumber = gameState.roundManager().getRoundNumber();
        dto.charsInRound = new CharacterInRoundDTO[gameState.roundManager().queueSize()];
        int index = 0;
        for(Pair<Character, VariableCache> charWithData : gameState.roundManager()) {
            dto.charsInRound[index] = new CharacterInRoundDTO();
            dto.charsInRound[index].id = charWithData.getItem1().uuid().toString();
            dto.charsInRound[index].x = charWithData.getItem1().tile().location().getX();
            dto.charsInRound[index].y = charWithData.getItem1().tile().location().getY();
            dto.charsInRound[index].data = VARIABLE_CACHE_HANDLER.write(charWithData.getItem2());
            index++;
        }

        List<OneTimeTurnBasedTimer> oneTimeTurnBasedTimers =
                gameState.roundManager().oneTimeTurnBasedTimersRepresentation();
        dto.oneTimeTurnBasedTimers = new String[oneTimeTurnBasedTimers.size()];
        index = 0;
        for(OneTimeTurnBasedTimer oneTimeTurnBasedTimer : oneTimeTurnBasedTimers) {
            dto.oneTimeTurnBasedTimers[index++] =
                    ONE_TIME_TURN_BASED_TIMER_HANDLER.write(oneTimeTurnBasedTimer);
        }

        List<RecurringTurnBasedTimer> recurringTurnBasedTimers =
                gameState.roundManager().recurringTurnBasedTimersRepresentation();
        dto.recurringTurnBasedTimers = new String[recurringTurnBasedTimers.size()];
        index = 0;
        for(RecurringTurnBasedTimer recurringTurnBasedTimer : recurringTurnBasedTimers) {
            dto.recurringTurnBasedTimers[index++] =
                    RECURRING_TURN_BASED_TIMER_HANDLER.write(recurringTurnBasedTimer);
        }
        return new Gson().toJson(dto);
    }

    private class GameStateDTO {
        String currentGameZoneId;
        PcInGameZoneDTO[] pcsInCurrentGameZone;
        String[] pcsNotInCurrentGameZone;
        String data;
        String partyAttributes;
        int roundNumber;
        CharacterInRoundDTO[] charsInRound;
        String[] oneTimeTurnBasedTimers;
        String[] recurringTurnBasedTimers;
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    private class PcInGameZoneDTO {
        int x;
        int y;
        String id;
    }

    private class CharacterInRoundDTO extends PcInGameZoneDTO {
        String data;
    }
}

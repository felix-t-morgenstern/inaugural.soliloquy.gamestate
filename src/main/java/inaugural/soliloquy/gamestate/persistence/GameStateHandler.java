package inaugural.soliloquy.gamestate.persistence;

import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.gamestate.GameState;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.gamestate.entities.timers.OneTimeRoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RecurringRoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;
import soliloquy.specs.gamestate.factories.*;
import soliloquy.specs.ruleset.entities.CharacterAIType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GameStateHandler extends AbstractTypeHandler<GameState> {
    private final GameStateFactory GAME_STATE_FACTORY;
    private final PartyFactory PARTY_FACTORY;
    private final GameZonesRepo GAME_ZONES_REPO;
    private final TypeHandler<VariableCache> VARIABLE_CACHE_HANDLER;
    private final TypeHandler<Character> CHARACTER_HANDLER;
    private final TypeHandler<OneTimeRoundBasedTimer>
            ONE_TIME_ROUND_BASED_TIMER_HANDLER;
    private final TypeHandler<RecurringRoundBasedTimer>
            RECURRING_ROUND_BASED_TIMER_HANDLER;

    private static final GameState ARCHETYPE = new GameStateArchetype();

    @SuppressWarnings("ConstantConditions")
    public GameStateHandler(GameStateFactory gameStateFactory,
                            PartyFactory partyFactory,
                            GameZonesRepo gameZonesRepo,
                            TypeHandler<VariableCache>
                                    variableCacheHandler,
                            TypeHandler<Character> characterHandler,
                            TypeHandler<OneTimeRoundBasedTimer>
                                    oneTimeRoundBasedTimerHandler,
                            TypeHandler<RecurringRoundBasedTimer>
                                    recurringRoundBasedTimerHandler) {
        super(ARCHETYPE);
        if (gameStateFactory == null) {
            throw new IllegalArgumentException(
                    "GameStateHandler: gameStateFactory cannot be null");
        }
        GAME_STATE_FACTORY = gameStateFactory;
        if (partyFactory == null) {
            throw new IllegalArgumentException(
                    "GameStateHandler: partyFactory cannot be null");
        }
        PARTY_FACTORY = partyFactory;
        if (gameZonesRepo == null) {
            throw new IllegalArgumentException(
                    "GameStateHandler: gameZonesRepo cannot be null");
        }
        GAME_ZONES_REPO = gameZonesRepo;
        if (variableCacheHandler == null) {
            throw new IllegalArgumentException(
                    "GameStateHandler: variableCacheHandler cannot be null");
        }
        VARIABLE_CACHE_HANDLER = variableCacheHandler;
        if (characterHandler == null) {
            throw new IllegalArgumentException(
                    "GameStateHandler: characterHandler cannot be null");
        }
        CHARACTER_HANDLER = characterHandler;
        if (oneTimeRoundBasedTimerHandler == null) {
            throw new IllegalArgumentException(
                    "GameStateHandler: oneTimeRoundBasedTimerHandler cannot be null");
        }
        ONE_TIME_ROUND_BASED_TIMER_HANDLER = oneTimeRoundBasedTimerHandler;
        if (recurringRoundBasedTimerHandler == null) {
            throw new IllegalArgumentException(
                    "GameStateHandler: recurringRoundBasedTimerHandler cannot be null");
        }
        RECURRING_ROUND_BASED_TIMER_HANDLER = recurringRoundBasedTimerHandler;
    }

    @Override
    public GameState read(String writtenData) throws IllegalArgumentException {
        if (writtenData == null) {
            throw new IllegalArgumentException(
                    "GameStateHandler.read: writtenData cannot be null");
        }
        if (writtenData.equals("")) {
            throw new IllegalArgumentException(
                    "GameStateHandler.read: writtenData cannot be empty");
        }

        GameStateDTO dto = JSON.fromJson(writtenData, GameStateDTO.class);

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
            Character character = findInTile(currentGameZone, charDto.id, charDto.x, charDto.y);
            gameState.roundManager()
                    .setCharacterPositionInQueue(character, Integer.MAX_VALUE);
            gameState.roundManager()
                    .setCharacterRoundData(character, VARIABLE_CACHE_HANDLER.read(charDto.data));
        }

        Arrays.stream(dto.oneTimeRoundBasedTimers)
                .forEach(ONE_TIME_ROUND_BASED_TIMER_HANDLER::read);
        Arrays.stream(dto.recurringRoundBasedTimers)
                .forEach(RECURRING_ROUND_BASED_TIMER_HANDLER::read);

        return gameState;
    }

    private Character findInTile(GameZone currentGameZone, String id, int x, int y) {
        for (Pair<Character, Integer> character :
                currentGameZone.tile(x, y).characters()) {
            if (character.getItem1().uuid().toString().equals(id)) {
                return character.getItem1();
            }
        }
        throw new IllegalArgumentException("GameStateHandler: character " + id +
                " not found at (" + x + "," + y + ")");
    }

    // TODO: Organize this method a bit better
    @Override
    public String write(GameState gameState) {
        if (gameState == null) {
            throw new IllegalArgumentException(
                    "GameStateHandler.write: gameState cannot be null");
        }
        GameStateDTO dto = new GameStateDTO();
        dto.currentGameZoneId = gameState.getCurrentGameZone().id();
        dto.data = VARIABLE_CACHE_HANDLER.write(gameState.variableCache());
        dto.partyAttributes = VARIABLE_CACHE_HANDLER.write(gameState.party().attributes());
        ArrayList<Character> pcsInGameZone = new ArrayList<>();
        ArrayList<Character> pcsNotInGameZone = new ArrayList<>();
        for (Character pc : gameState.party().characters()) {
            if (pc.tile() == null) {
                pcsNotInGameZone.add(pc);
            }
            else {
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
        for (Pair<Character, VariableCache> charWithData :
                gameState.roundManager().characterQueueRepresentation()) {
            dto.charsInRound[index] = new CharacterInRoundDTO();
            dto.charsInRound[index].id = charWithData.getItem1().uuid().toString();
            dto.charsInRound[index].x = charWithData.getItem1().tile().location().getX();
            dto.charsInRound[index].y = charWithData.getItem1().tile().location().getY();
            dto.charsInRound[index].data = VARIABLE_CACHE_HANDLER.write(charWithData.getItem2());
            index++;
        }

        List<OneTimeRoundBasedTimer> oneTimeRoundBasedTimers =
                gameState.roundBasedTimerManager().oneTimeRoundBasedTimersRepresentation();
        dto.oneTimeRoundBasedTimers = new String[oneTimeRoundBasedTimers.size()];
        index = 0;
        for (OneTimeRoundBasedTimer oneTimeRoundBasedTimer : oneTimeRoundBasedTimers) {
            dto.oneTimeRoundBasedTimers[index++] =
                    ONE_TIME_ROUND_BASED_TIMER_HANDLER.write(oneTimeRoundBasedTimer);
        }

        List<RecurringRoundBasedTimer> recurringRoundBasedTimers =
                gameState.roundBasedTimerManager().recurringRoundBasedTimersRepresentation();
        dto.recurringRoundBasedTimers = new String[recurringRoundBasedTimers.size()];
        index = 0;
        for (RecurringRoundBasedTimer recurringRoundBasedTimer : recurringRoundBasedTimers) {
            dto.recurringRoundBasedTimers[index++] =
                    RECURRING_ROUND_BASED_TIMER_HANDLER.write(recurringRoundBasedTimer);
        }
        return JSON.toJson(dto);
    }

    private static class GameStateDTO {
        String currentGameZoneId;
        PcInGameZoneDTO[] pcsInCurrentGameZone;
        String[] pcsNotInCurrentGameZone;
        String data;
        String partyAttributes;
        int roundNumber;
        CharacterInRoundDTO[] charsInRound;
        String[] oneTimeRoundBasedTimers;
        String[] recurringRoundBasedTimers;
    }

    private static class PcInGameZoneDTO {
        int x;
        int y;
        String id;
    }

    private static class CharacterInRoundDTO extends PcInGameZoneDTO {
        String data;
    }

    private static class GameStateArchetype implements GameState {
        @Override
        public Party party() throws IllegalStateException {
            return null;
        }

        @Override
        public VariableCache variableCache() {
            return null;
        }

        @Override
        public Map<String, CharacterAIType> characterAIs() {
            return null;
        }

        @Override
        public GameZonesRepo gameZonesRepo() {
            return null;
        }

        @Override
        public GameZone getCurrentGameZone() {
            return null;
        }

        @Override
        public void setCurrentGameZone(GameZone gameZone) {

        }

        @Override
        public Camera camera() {
            return null;
        }

        @Override
        public Registry<GameMovementEvent> movementEvents() {
            return null;
        }

        @Override
        public Registry<GameAbilityEvent> abilityEvents() {
            return null;
        }

        @Override
        public RoundManager roundManager() {
            return null;
        }

        @Override
        public RoundBasedTimerManager roundBasedTimerManager() {
            return null;
        }

        @Override
        public Map<Integer, KeyBindingContext> keyBindingContexts() throws IllegalStateException {
            return null;
        }

        @Override
        public ItemFactory itemFactory() {
            return null;
        }

        @Override
        public CharacterFactory characterFactory() {
            return null;
        }

        @Override
        public RoundBasedTimerFactory roundBasedTimerFactory() {
            return null;
        }

        @Override
        public KeyBindingFactory keyBindingFactory() {
            return null;
        }

        @Override
        public KeyBindingContextFactory keyBindingContextFactory() {
            return null;
        }

        @Override
        public KeyEventListener keyEventListener() {
            return null;
        }

        @Override
        public String getInterfaceName() {
            return GameState.class.getCanonicalName();
        }
    }
}

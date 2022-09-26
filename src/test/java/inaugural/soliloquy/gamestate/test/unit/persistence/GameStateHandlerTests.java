package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.GameStateHandler;
import inaugural.soliloquy.gamestate.test.fakes.*;
import inaugural.soliloquy.gamestate.test.fakes.persistence.FakeCharacterHandler;
import inaugural.soliloquy.gamestate.test.fakes.persistence.FakeOneTimeRoundBasedTimerHandler;
import inaugural.soliloquy.gamestate.test.fakes.persistence.FakeRecurringRoundBasedTimerHandler;
import inaugural.soliloquy.gamestate.test.fakes.persistence.FakeVariableCacheHandler;
import inaugural.soliloquy.gamestate.test.stubs.GameZonesRepoStub;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.gamestate.GameState;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.timers.OneTimeRoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RecurringRoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;
import soliloquy.specs.gamestate.factories.PartyFactory;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GameStateHandlerTests {
    private final FakeGameStateFactory GAME_STATE_FACTORY = new FakeGameStateFactory();
    private final PartyFactory PARTY_FACTORY = new FakePartyFactory();
    private final GameZonesRepoStub GAME_ZONES_REPO = new GameZonesRepoStub();
    private final FakeVariableCacheHandler VARIABLE_CACHE_HANDLER =
            new FakeVariableCacheHandler();
    private final FakeCharacterHandler CHARACTER_HANDLER = new FakeCharacterHandler();
    private final FakeOneTimeRoundBasedTimerHandler ONE_TIME_TIMER_HANDLER =
            new FakeOneTimeRoundBasedTimerHandler();
    private final FakeRecurringRoundBasedTimerHandler RECURRING_TIMER_HANDLER =
            new FakeRecurringRoundBasedTimerHandler();
    private final String GAME_ZONE_ID = "gameZoneId";
    private final VariableCache DATA = new VariableCacheStub();
    private final String PC_IN_GAME_ZONE_ID = "b37594cf-5cd5-441d-9dff-adc7392c221e";
    private final String NPC_ID = "740390c6-6956-4f21-8dea-98f2c8ef3cbb";
    private final int PC_X = 12;
    private final int PC_Y = 34;
    private final int NPC_X = 56;
    private final int NPC_Y = 78;
    private final int ROUND_NUMBER = 123456;

    private TypeHandler<GameState> _gameStateHandler;

    private final String WRITTEN_DATA =
            "{\"currentGameZoneId\":\"gameZoneId\",\"pcsInCurrentGameZone\":[{\"x\":12,\"y\":34," +
                    "\"id\":\"b37594cf-5cd5-441d-9dff-adc7392c221e\"}]," +
                    "\"pcsNotInCurrentGameZone\":[\"Character0\"],\"data\":\"VariableCache0\"," +
                    "\"partyAttributes\":\"VariableCache1\",\"roundNumber\":123456," +
                    "\"charsInRound\":[{\"data\":\"VariableCache2\",\"x\":12,\"y\":34," +
                    "\"id\":\"b37594cf-5cd5-441d-9dff-adc7392c221e\"}," +
                    "{\"data\":\"VariableCache3\",\"x\":56,\"y\":78," +
                    "\"id\":\"740390c6-6956-4f21-8dea-98f2c8ef3cbb\"}]," +
                    "\"oneTimeRoundBasedTimers\":[\"OneTimeTimer0\"]," +
                    "\"recurringRoundBasedTimers\":[\"RecurringTimer0\"]}";

    @BeforeEach
    void setUp() {
        _gameStateHandler = new GameStateHandler(GAME_STATE_FACTORY, PARTY_FACTORY,
                GAME_ZONES_REPO, VARIABLE_CACHE_HANDLER, CHARACTER_HANDLER, ONE_TIME_TIMER_HANDLER,
                RECURRING_TIMER_HANDLER);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateHandler(null, PARTY_FACTORY,
                        GAME_ZONES_REPO, VARIABLE_CACHE_HANDLER, CHARACTER_HANDLER,
                        ONE_TIME_TIMER_HANDLER, RECURRING_TIMER_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateHandler(GAME_STATE_FACTORY, null,
                        GAME_ZONES_REPO, VARIABLE_CACHE_HANDLER, CHARACTER_HANDLER,
                        ONE_TIME_TIMER_HANDLER, RECURRING_TIMER_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateHandler(GAME_STATE_FACTORY, PARTY_FACTORY,
                        null, VARIABLE_CACHE_HANDLER, CHARACTER_HANDLER,
                        ONE_TIME_TIMER_HANDLER, RECURRING_TIMER_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateHandler(GAME_STATE_FACTORY, PARTY_FACTORY,
                        GAME_ZONES_REPO, null, CHARACTER_HANDLER,
                        ONE_TIME_TIMER_HANDLER, RECURRING_TIMER_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateHandler(GAME_STATE_FACTORY, PARTY_FACTORY,
                        GAME_ZONES_REPO, VARIABLE_CACHE_HANDLER, null,
                        ONE_TIME_TIMER_HANDLER, RECURRING_TIMER_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateHandler(GAME_STATE_FACTORY, PARTY_FACTORY,
                        GAME_ZONES_REPO, VARIABLE_CACHE_HANDLER, CHARACTER_HANDLER,
                        null, RECURRING_TIMER_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateHandler(GAME_STATE_FACTORY, PARTY_FACTORY,
                        GAME_ZONES_REPO, VARIABLE_CACHE_HANDLER, CHARACTER_HANDLER,
                        ONE_TIME_TIMER_HANDLER, null));
    }

    @Test
    void testWrite() {
        FakeParty party = new FakeParty();
        FakeGameState gameState = new FakeGameState(party, DATA);
        String pcNotInGameZoneId = "8642a24b-97a7-40de-bb27-ea502c435381";
        FakeCharacter pcNotInGameZone =
                new FakeCharacter(UUID.fromString(pcNotInGameZoneId), null, null);
        FakeCharacter pcInGameZone =
                new FakeCharacter(UUID.fromString(PC_IN_GAME_ZONE_ID), null, null);
        VariableCache pcInGameZoneRoundData = new VariableCacheStub();
        FakeCharacter npcInGameZone = new FakeCharacter(UUID.fromString(NPC_ID), null, null);
        VariableCache nonPcInGameZoneRoundData = new VariableCacheStub();
        party.characters().add(pcNotInGameZone);
        party.characters().add(pcInGameZone);
        FakeGameZone gameZone = new FakeGameZone();
        gameZone._customId = GAME_ZONE_ID;
        gameZone.TILES[PC_X][PC_Y].characters().add(pcInGameZone);
        pcInGameZone._tile = new FakeTile(gameZone, PC_X, PC_Y);
        gameZone.TILES[NPC_X][NPC_Y].characters().add(npcInGameZone);
        npcInGameZone._tile = new FakeTile(gameZone, NPC_X, NPC_Y);
        gameState.setCurrentGameZone(gameZone);
        gameState.RoundManager.setRoundNumber(ROUND_NUMBER);
        gameState.RoundManager.setCharacterPositionInQueue(pcInGameZone, 0);
        gameState.RoundManager.setCharacterRoundData(pcInGameZone, pcInGameZoneRoundData);
        gameState.RoundManager.setCharacterPositionInQueue(npcInGameZone, 1);
        gameState.RoundManager.setCharacterRoundData(npcInGameZone, nonPcInGameZoneRoundData);

        gameState.RoundBasedTimerManager = mock(RoundBasedTimerManager.class);
        OneTimeRoundBasedTimer oneTimeRoundBasedTimer = new FakeOneTimeRoundBasedTimer();
        when(gameState.RoundBasedTimerManager.oneTimeRoundBasedTimersRepresentation())
                .thenReturn(new ArrayList<>() {{
                    add(oneTimeRoundBasedTimer);
                }});
        RecurringRoundBasedTimer recurringRoundBasedTimer = new FakeRecurringRoundBasedTimer();
        when(gameState.RoundBasedTimerManager.recurringRoundBasedTimersRepresentation())
                .thenReturn(new ArrayList<>() {{
                    add(recurringRoundBasedTimer);
                }});

        String writtenData = _gameStateHandler.write(gameState);

        assertEquals(WRITTEN_DATA, writtenData);
        // TODO: Test and implement proper write inputs and outputs for handlers
    }

    @Test
    void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _gameStateHandler.write(null));
    }

    @Test
    void testRead() {
        Character pcInGameZone =
                new FakeCharacter(UUID.fromString(PC_IN_GAME_ZONE_ID), null, null);
        Character npcInGameZone = new FakeCharacter(UUID.fromString(NPC_ID), null, null);
        FakeGameZone gameZone = new FakeGameZone();
        gameZone.RETURN_ACTUAL_TILE_AT_LOCATION = true;
        gameZone.TILES[PC_X][PC_Y].characters().add(pcInGameZone);
        gameZone.TILES[NPC_X][NPC_Y].characters().add(npcInGameZone);
        gameZone._customId = GAME_ZONE_ID;
        GAME_ZONES_REPO.REPO.put(GAME_ZONE_ID, gameZone);

        GameState gameState = _gameStateHandler.read(WRITTEN_DATA);

        assertNotNull(gameState);
        assertEquals(4, VARIABLE_CACHE_HANDLER.READ_INPUTS.size());
        assertEquals("VariableCache1", VARIABLE_CACHE_HANDLER.READ_INPUTS.get(0));
        assertEquals("VariableCache0", VARIABLE_CACHE_HANDLER.READ_INPUTS.get(1));
        assertSame(gameZone, gameState.getCurrentGameZone());
        assertNotNull(gameState.party());
        assertEquals(4, VARIABLE_CACHE_HANDLER.READ_OUTPUTS.size());
        assertSame(VARIABLE_CACHE_HANDLER.READ_OUTPUTS.get(1), gameState.variableCache());
        assertSame(VARIABLE_CACHE_HANDLER.READ_OUTPUTS.get(0), gameState.party().attributes());
        assertEquals(1, CHARACTER_HANDLER.READ_INPUTS.size());
        assertEquals("Character0", CHARACTER_HANDLER.READ_INPUTS.get(0));
        assertTrue(gameState.party().characters().contains(CHARACTER_HANDLER.READ_OUTPUTS.get(0)));
        assertEquals(2, gameState.party().characters().size());
        assertEquals(ROUND_NUMBER, gameState.roundManager().getRoundNumber());
        ArrayList<Pair<Character, VariableCache>> charactersWithData =
                new ArrayList<>(gameState.roundManager().characterQueueRepresentation());
        assertEquals("VariableCache2", VARIABLE_CACHE_HANDLER.READ_INPUTS.get(2));
        assertEquals("VariableCache3", VARIABLE_CACHE_HANDLER.READ_INPUTS.get(3));
        assertSame(VARIABLE_CACHE_HANDLER.READ_OUTPUTS.get(2),
                charactersWithData.get(0).getItem2());
        assertSame(VARIABLE_CACHE_HANDLER.READ_OUTPUTS.get(3),
                charactersWithData.get(1).getItem2());
        assertSame(pcInGameZone, charactersWithData.get(0).getItem1());
        assertSame(npcInGameZone, charactersWithData.get(1).getItem1());
        // NB: All that matters is that the Timers are read; the factory within the handler should
        //     add the read Timers are added to the RoundManager, which is not under the purview of
        //     this test.
        assertEquals("OneTimeTimer0", ONE_TIME_TIMER_HANDLER.READ_INPUTS.get(0));
        assertEquals("RecurringTimer0", RECURRING_TIMER_HANDLER.READ_INPUTS.get(0));
    }

    @Test
    void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _gameStateHandler.read(null));
        assertThrows(IllegalArgumentException.class, () -> _gameStateHandler.read(""));
    }

    @Test
    void testReadWhenPartyMemberInGameZoneIsNotAtSpecifiedLocation() {
        Character pcInGameZone =
                new FakeCharacter(UUID.fromString(PC_IN_GAME_ZONE_ID), null, null);
        Character nonPcInGameZone =
                new FakeCharacter(UUID.fromString(NPC_ID), null, null);
        FakeGameZone gameZone = new FakeGameZone();
        gameZone.RETURN_ACTUAL_TILE_AT_LOCATION = true;
        gameZone.TILES[PC_X + 1][PC_Y].characters().add(pcInGameZone);
        gameZone.TILES[NPC_X][NPC_Y].characters().add(nonPcInGameZone);
        gameZone._customId = GAME_ZONE_ID;
        GAME_ZONES_REPO.REPO.put(GAME_ZONE_ID, gameZone);

        assertThrows(IllegalArgumentException.class, () -> _gameStateHandler.read(WRITTEN_DATA));
    }

    @Test
    void testReadWhenCharacterInRoundQueueIsNotAtSpecifiedLocation() {
        Character pcInGameZone =
                new FakeCharacter(UUID.fromString(PC_IN_GAME_ZONE_ID), null, null);
        Character nonPcInGameZone =
                new FakeCharacter(UUID.fromString(NPC_ID), null, null);
        FakeGameZone gameZone = new FakeGameZone();
        gameZone.RETURN_ACTUAL_TILE_AT_LOCATION = true;
        gameZone.TILES[PC_X][PC_Y].characters().add(pcInGameZone);
        gameZone.TILES[NPC_X + 1][NPC_Y].characters().add(nonPcInGameZone);
        gameZone._customId = GAME_ZONE_ID;
        GAME_ZONES_REPO.REPO.put(GAME_ZONE_ID, gameZone);

        assertThrows(IllegalArgumentException.class, () -> _gameStateHandler.read(WRITTEN_DATA));
    }

    @Test
    void testArchetype() {
        assertNotNull(_gameStateHandler.getArchetype());
        assertEquals(GameState.class.getCanonicalName(),
                _gameStateHandler.getArchetype().getInterfaceName());
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        GameState.class.getCanonicalName() + ">",
                _gameStateHandler.getInterfaceName());
    }
}

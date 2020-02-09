package inaugural.soliloquy.gamestate.test.unit.persistenttypehandlers;

import inaugural.soliloquy.gamestate.persistentvaluetypehandlers.PersistentGameStateHandler;
import inaugural.soliloquy.gamestate.test.stubs.*;
import inaugural.soliloquy.gamestate.test.stubs.persistenttypehandlers.PersistentCharacterHandlerStub;
import inaugural.soliloquy.gamestate.test.stubs.persistenttypehandlers.PersistentOneTimeTimerHandlerStub;
import inaugural.soliloquy.gamestate.test.stubs.persistenttypehandlers.PersistentRecurringTimerHandlerStub;
import inaugural.soliloquy.gamestate.test.stubs.persistenttypehandlers.PersistentVariableCacheHandlerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.ReadablePair;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.GameState;
import soliloquy.specs.gamestate.entities.OneTimeTimer;
import soliloquy.specs.gamestate.entities.RecurringTimer;
import soliloquy.specs.gamestate.factories.PartyFactory;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PersistentGameStateHandlerTests {
    private final GameStateFactoryStub GAME_STATE_FACTORY = new GameStateFactoryStub();
    private final PartyFactory PARTY_FACTORY = new PartyFactoryStub();
    private final GameZonesRepoStub GAME_ZONES_REPO = new GameZonesRepoStub();
    private final PersistentVariableCacheHandlerStub VARIABLE_CACHE_HANDLER =
            new PersistentVariableCacheHandlerStub();
    private final PersistentCharacterHandlerStub CHARACTER_HANDLER =
            new PersistentCharacterHandlerStub();
    private final PersistentOneTimeTimerHandlerStub ONE_TIME_TIMER_HANDLER =
            new PersistentOneTimeTimerHandlerStub();
    private final PersistentRecurringTimerHandlerStub RECURRING_TIMER_HANDLER =
            new PersistentRecurringTimerHandlerStub();
    private final String GAME_ZONE_ID = "gameZoneId";
    private final VariableCache DATA = new VariableCacheStub();
    private final String PC_IN_GAME_ZONE_ID = "b37594cf-5cd5-441d-9dff-adc7392c221e";
    private final String NPC_ID = "740390c6-6956-4f21-8dea-98f2c8ef3cbb";
    private final int PC_X = 12;
    private final int PC_Y = 34;
    private final int NPC_X = 56;
    private final int NPC_Y = 78;
    private final int ROUND_NUMBER = 123456;

    private PersistentValueTypeHandler<GameState> _gameStateHandler;

    private final String WRITTEN_DATA = "{\"currentGameZoneId\":\"gameZoneId\",\"pcsInCurrentGameZone\":[{\"x\":12,\"y\":34,\"id\":\"b37594cf-5cd5-441d-9dff-adc7392c221e\"}],\"pcsNotInCurrentGameZone\":[\"Character0\"],\"data\":\"VariableCache0\",\"partyAttributes\":\"VariableCache1\",\"roundNumber\":123456,\"charsInRound\":[{\"data\":\"VariableCache2\",\"x\":12,\"y\":34,\"id\":\"b37594cf-5cd5-441d-9dff-adc7392c221e\"},{\"data\":\"VariableCache3\",\"x\":56,\"y\":78,\"id\":\"740390c6-6956-4f21-8dea-98f2c8ef3cbb\"}],\"oneTimeTimers\":[\"OneTimeTimer0\"],\"recurringTimers\":[\"RecurringTimer0\"]}";

    @BeforeEach
    void setUp() {
        _gameStateHandler = new PersistentGameStateHandler(GAME_STATE_FACTORY, PARTY_FACTORY,
                GAME_ZONES_REPO, VARIABLE_CACHE_HANDLER, CHARACTER_HANDLER, ONE_TIME_TIMER_HANDLER,
                RECURRING_TIMER_HANDLER);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentGameStateHandler(null, PARTY_FACTORY,
                        GAME_ZONES_REPO, VARIABLE_CACHE_HANDLER, CHARACTER_HANDLER,
                        ONE_TIME_TIMER_HANDLER, RECURRING_TIMER_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentGameStateHandler(GAME_STATE_FACTORY, null,
                        GAME_ZONES_REPO, VARIABLE_CACHE_HANDLER, CHARACTER_HANDLER,
                        ONE_TIME_TIMER_HANDLER, RECURRING_TIMER_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentGameStateHandler(GAME_STATE_FACTORY, PARTY_FACTORY,
                        null, VARIABLE_CACHE_HANDLER, CHARACTER_HANDLER,
                        ONE_TIME_TIMER_HANDLER, RECURRING_TIMER_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentGameStateHandler(GAME_STATE_FACTORY, PARTY_FACTORY,
                        GAME_ZONES_REPO, null, CHARACTER_HANDLER,
                        ONE_TIME_TIMER_HANDLER, RECURRING_TIMER_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentGameStateHandler(GAME_STATE_FACTORY, PARTY_FACTORY,
                        GAME_ZONES_REPO, VARIABLE_CACHE_HANDLER, null,
                        ONE_TIME_TIMER_HANDLER, RECURRING_TIMER_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentGameStateHandler(GAME_STATE_FACTORY, PARTY_FACTORY,
                        GAME_ZONES_REPO, VARIABLE_CACHE_HANDLER, CHARACTER_HANDLER,
                        null, RECURRING_TIMER_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentGameStateHandler(GAME_STATE_FACTORY, PARTY_FACTORY,
                        GAME_ZONES_REPO, VARIABLE_CACHE_HANDLER, CHARACTER_HANDLER,
                        ONE_TIME_TIMER_HANDLER, null));
    }

    @Test
    void testWrite() {
        PartyStub party = new PartyStub();
        GameStateStub gameState = new GameStateStub(party, DATA);
        String pcNotInGameZoneId = "8642a24b-97a7-40de-bb27-ea502c435381";
        CharacterStub pcNotInGameZone =
                new CharacterStub(new EntityUuidStub(pcNotInGameZoneId), null, null);
        CharacterStub pcInGameZone =
                new CharacterStub(new EntityUuidStub(PC_IN_GAME_ZONE_ID), null, null);
        VariableCache pcInGameZoneRoundData = new VariableCacheStub();
        CharacterStub npcInGameZone = new CharacterStub(new EntityUuidStub(NPC_ID), null, null);
        VariableCache nonPcInGameZoneRoundData = new VariableCacheStub();
        party.characters().add(pcNotInGameZone);
        party.characters().add(pcInGameZone);
        GameZoneStub gameZone = new GameZoneStub();
        gameZone._customId = GAME_ZONE_ID;
        gameZone.TILES[PC_X][PC_Y].characters().add(pcInGameZone);
        pcInGameZone._tile = new TileStub(gameZone, PC_X, PC_Y);
        gameZone.TILES[NPC_X][NPC_Y].characters().add(npcInGameZone);
        npcInGameZone._tile = new TileStub(gameZone, NPC_X, NPC_Y);
        gameState.setCurrentGameZone(gameZone);
        gameState.RoundManager.setRoundNumber(ROUND_NUMBER);
        gameState.RoundManager.setCharacterPositionInQueue(pcInGameZone, 0, pcInGameZoneRoundData);
        gameState.RoundManager.setCharacterPositionInQueue(npcInGameZone, 1,
                nonPcInGameZoneRoundData);
        OneTimeTimer oneTimeTimer = new OneTimeTimerStub();
        gameState.RoundManager.OneTimeTimers.add(oneTimeTimer);
        RecurringTimer recurringTimer = new RecurringTimerStub();
        gameState.RoundManager.RecurringTimers.add(recurringTimer);

        String writtenData = _gameStateHandler.write(gameState);

        assertEquals(WRITTEN_DATA, writtenData);
        // TODO: Test and implement proper write inputs and outputs for handlers
    }

    @Test
    void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _gameStateHandler.write(null));
    }

    @Test
    void testRead(){
        Character pcInGameZone =
                new CharacterStub(new EntityUuidStub(PC_IN_GAME_ZONE_ID), null, null);
        Character npcInGameZone = new CharacterStub(new EntityUuidStub(NPC_ID), null, null);
        GameZoneStub gameZone = new GameZoneStub();
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
        ArrayList<ReadablePair<Character, VariableCache>> charactersWithData = new ArrayList<>();
        gameState.roundManager().forEach(charactersWithData::add);
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
                new CharacterStub(new EntityUuidStub(PC_IN_GAME_ZONE_ID), null, null);
        Character nonPcInGameZone =
                new CharacterStub(new EntityUuidStub(NPC_ID), null, null);
        GameZoneStub gameZone = new GameZoneStub();
        gameZone.RETURN_ACTUAL_TILE_AT_LOCATION = true;
        gameZone.TILES[PC_X +1][PC_Y].characters().add(pcInGameZone);
        gameZone.TILES[NPC_X][NPC_Y].characters().add(nonPcInGameZone);
        gameZone._customId = GAME_ZONE_ID;
        GAME_ZONES_REPO.REPO.put(GAME_ZONE_ID, gameZone);

        assertThrows(IllegalArgumentException.class, () -> _gameStateHandler.read(WRITTEN_DATA));
    }

    @Test
    void testReadWhenCharacterInRoundQueueIsNotAtSpecifiedLocation() {
        Character pcInGameZone =
                new CharacterStub(new EntityUuidStub(PC_IN_GAME_ZONE_ID), null, null);
        Character nonPcInGameZone =
                new CharacterStub(new EntityUuidStub(NPC_ID), null, null);
        GameZoneStub gameZone = new GameZoneStub();
        gameZone.RETURN_ACTUAL_TILE_AT_LOCATION = true;
        gameZone.TILES[PC_X][PC_Y].characters().add(pcInGameZone);
        gameZone.TILES[NPC_X+1][NPC_Y].characters().add(nonPcInGameZone);
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
        assertEquals(PersistentValueTypeHandler.class.getCanonicalName() + "<" +
                GameState.class.getCanonicalName() + ">",
                _gameStateHandler.getInterfaceName());
    }
}

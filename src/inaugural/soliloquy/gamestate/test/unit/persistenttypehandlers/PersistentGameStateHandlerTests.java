package inaugural.soliloquy.gamestate.test.unit.persistenttypehandlers;

import inaugural.soliloquy.gamestate.persistentvaluetypehandlers.PersistentGameStateHandler;
import inaugural.soliloquy.gamestate.test.stubs.*;
import inaugural.soliloquy.gamestate.test.stubs.persistenttypehandlers.PersistentCharacterHandlerStub;
import inaugural.soliloquy.gamestate.test.stubs.persistenttypehandlers.PersistentVariableCacheHandlerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.GameState;
import soliloquy.specs.gamestate.factories.PartyFactory;

import static org.junit.jupiter.api.Assertions.*;

class PersistentGameStateHandlerTests {
    private final GameStateFactoryStub GAME_STATE_FACTORY = new GameStateFactoryStub();
    private final PartyFactory PARTY_FACTORY = new PartyFactoryStub();
    private final GameZonesRepoStub GAME_ZONES_REPO = new GameZonesRepoStub();
    private final PersistentVariableCacheHandlerStub VARIABLE_CACHE_HANDLER =
            new PersistentVariableCacheHandlerStub();
    private final PersistentCharacterHandlerStub CHARACTER_HANDLER =
            new PersistentCharacterHandlerStub();
    private final String GAME_ZONE_ID = "gameZoneId";
    private final VariableCache DATA = new VariableCacheStub();
    private final String PC_IN_GAME_ZONE_ID = "b37594cf-5cd5-441d-9dff-adc7392c221e";
    private final String CHAR_NOT_IN_PARTY_ID = "740390c6-6956-4f21-8dea-98f2c8ef3cbb";
    private final int PARTY_MEMBER_X = 12;
    private final int PARTY_MEMBER_Y = 34;

    private PersistentValueTypeHandler<GameState> _gameStateHandler;

    private final String WRITTEN_DATA = "{\"currentGameZoneId\":\"gameZoneId\",\"pcsInCurrentGameZone\":[{\"x\":12,\"y\":34,\"id\":\"b37594cf-5cd5-441d-9dff-adc7392c221e\"}],\"pcsNotInCurrentGameZone\":[\"Character0\"],\"data\":\"VariableCache0\",\"partyAttributes\":\"VariableCache1\"}";

    @BeforeEach
    void setUp() {
        _gameStateHandler = new PersistentGameStateHandler(GAME_STATE_FACTORY, PARTY_FACTORY,
                GAME_ZONES_REPO, VARIABLE_CACHE_HANDLER, CHARACTER_HANDLER);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentGameStateHandler(null, PARTY_FACTORY,
                        GAME_ZONES_REPO, VARIABLE_CACHE_HANDLER, CHARACTER_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentGameStateHandler(GAME_STATE_FACTORY, null,
                        GAME_ZONES_REPO, VARIABLE_CACHE_HANDLER, CHARACTER_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentGameStateHandler(GAME_STATE_FACTORY, PARTY_FACTORY,
                        null, VARIABLE_CACHE_HANDLER, CHARACTER_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentGameStateHandler(GAME_STATE_FACTORY, PARTY_FACTORY,
                        GAME_ZONES_REPO, null, CHARACTER_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentGameStateHandler(GAME_STATE_FACTORY, PARTY_FACTORY,
                        GAME_ZONES_REPO, VARIABLE_CACHE_HANDLER, null));
    }

    @Test
    void testWrite() {
        PartyStub party = new PartyStub();
        GameStateStub gameState = new GameStateStub(party, DATA);
        String pcNotInGameZoneId = "8642a24b-97a7-40de-bb27-ea502c435381";
        Character pcNotInGameZone =
                new CharacterStub(new EntityUuidStub(pcNotInGameZoneId), null, null);
        Character pcInGameZone =
                new CharacterStub(new EntityUuidStub(PC_IN_GAME_ZONE_ID), null, null);
        party.characters().add(pcNotInGameZone);
        party.characters().add(pcInGameZone);
        GameZoneStub gameZone = new GameZoneStub();
        gameZone._customId = GAME_ZONE_ID;
        gameZone.TILES[PARTY_MEMBER_X][PARTY_MEMBER_Y].characters().add(pcInGameZone);
        gameState.setCurrentGameZone(gameZone);

        String writtenData = _gameStateHandler.write(gameState);

        assertEquals(WRITTEN_DATA, writtenData);
    }

    @Test
    void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _gameStateHandler.write(null));
    }

    @Test
    void testRead(){
        Character pcInGameZone =
                new CharacterStub(new EntityUuidStub(PC_IN_GAME_ZONE_ID), null, null);
        Character nonPcInGameZone =
                new CharacterStub(new EntityUuidStub(CHAR_NOT_IN_PARTY_ID), null, null);
        GameZoneStub gameZone = new GameZoneStub();
        gameZone.RETURN_ACTUAL_TILE_AT_LOCATION = true;
        gameZone.TILES[PARTY_MEMBER_X][PARTY_MEMBER_Y].characters().add(pcInGameZone);
        gameZone.TILES[PARTY_MEMBER_X][PARTY_MEMBER_Y].characters().add(nonPcInGameZone);
        gameZone._customId = GAME_ZONE_ID;
        GAME_ZONES_REPO.REPO.put(GAME_ZONE_ID, gameZone);

        GameState gameState = _gameStateHandler.read(WRITTEN_DATA);

        assertNotNull(gameState);
        assertEquals(2, VARIABLE_CACHE_HANDLER.READ_INPUTS.size());
        assertEquals("VariableCache1", VARIABLE_CACHE_HANDLER.READ_INPUTS.get(0));
        assertEquals("VariableCache0", VARIABLE_CACHE_HANDLER.READ_INPUTS.get(1));
        assertSame(gameZone, gameState.getCurrentGameZone());
        assertNotNull(gameState.party());
        assertEquals(2, VARIABLE_CACHE_HANDLER.READ_OUTPUTS.size());
        assertSame(VARIABLE_CACHE_HANDLER.READ_OUTPUTS.get(1), gameState.variableCache());
        assertSame(VARIABLE_CACHE_HANDLER.READ_OUTPUTS.get(0), gameState.party().attributes());
        assertEquals(1, CHARACTER_HANDLER.READ_INPUTS.size());
        assertEquals("Character0", CHARACTER_HANDLER.READ_INPUTS.get(0));
        assertTrue(gameState.party().characters().contains(CHARACTER_HANDLER.READ_OUTPUTS.get(0)));
        assertEquals(2, gameState.party().characters().size());
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
                new CharacterStub(new EntityUuidStub(CHAR_NOT_IN_PARTY_ID), null, null);
        GameZoneStub gameZone = new GameZoneStub();
        gameZone.RETURN_ACTUAL_TILE_AT_LOCATION = true;
        gameZone.TILES[PARTY_MEMBER_X+1][PARTY_MEMBER_Y].characters().add(pcInGameZone);
        gameZone.TILES[PARTY_MEMBER_X][PARTY_MEMBER_Y].characters().add(nonPcInGameZone);
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

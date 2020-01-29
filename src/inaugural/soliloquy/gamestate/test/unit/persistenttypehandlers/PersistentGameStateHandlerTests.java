package inaugural.soliloquy.gamestate.test.unit.persistenttypehandlers;

import inaugural.soliloquy.gamestate.persistentvaluetypehandlers.PersistentGameStateHandler;
import inaugural.soliloquy.gamestate.test.stubs.GameStateFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.persistenttypehandlers.PersistentCharacterHandlerStub;
import inaugural.soliloquy.gamestate.test.stubs.persistenttypehandlers.PersistentEntityUuidHandlerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;
import soliloquy.specs.gamestate.entities.GameState;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PersistentGameStateHandlerTests {
    private final GameStateFactoryStub GAME_STATE_FACTORY = new GameStateFactoryStub();
    private final PersistentCharacterHandlerStub CHARACTER_HANDLER =
            new PersistentCharacterHandlerStub();
    private final PersistentEntityUuidHandlerStub ID_HANDLER =
            new PersistentEntityUuidHandlerStub();

    private PersistentValueTypeHandler<GameState> _gameStateHandler;

    private final String WRITTEN_DATA = "";

    @BeforeEach
    void setUp() {
        _gameStateHandler = new PersistentGameStateHandler(GAME_STATE_FACTORY, CHARACTER_HANDLER,
                ID_HANDLER);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentGameStateHandler(null, CHARACTER_HANDLER,
                        ID_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentGameStateHandler(GAME_STATE_FACTORY, null,
                        ID_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentGameStateHandler(GAME_STATE_FACTORY, CHARACTER_HANDLER,
                        null));
    }
}

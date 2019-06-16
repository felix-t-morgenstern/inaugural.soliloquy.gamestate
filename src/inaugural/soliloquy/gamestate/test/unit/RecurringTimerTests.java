package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.RecurringTimer;
import inaugural.soliloquy.gamestate.test.stubs.ActionStub;
import inaugural.soliloquy.gamestate.test.stubs.GameStub;
import inaugural.soliloquy.gamestate.test.stubs.LoggerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.IAction;
import soliloquy.specs.game.IGame;
import soliloquy.specs.gamestate.entities.IRecurringTimer;
import soliloquy.specs.logger.ILogger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecurringTimerTests {
    private IRecurringTimer _recurringTimer;

    private final String TIMER_ID = "TimerId";
    private final IAction<Void> TIMER_ACTION = new ActionStub<>();

    private final IGame GAME = new GameStub();
    private final ILogger LOGGER = new LoggerStub();

    @BeforeEach
    void setUp() {
        _recurringTimer = new RecurringTimer(TIMER_ID, TIMER_ACTION, 0, 0, GAME, LOGGER);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(IRecurringTimer.class.getCanonicalName(), _recurringTimer.getInterfaceName());
    }

    @Test
    void testSetAndGetRoundModulo() {
        _recurringTimer.setRoundModulo(123);

        assertEquals(123, _recurringTimer.getRoundModulo());
    }

    @Test
    void testSetAndGetRoundOffset() {
        _recurringTimer.setRoundOffset(456);

        assertEquals(456, _recurringTimer.getRoundOffset());
    }

    @Test
    void testTimerAction() {
        assertEquals(TIMER_ACTION, _recurringTimer.action());
    }

    @Test
    void testSetAndGetPrioirity() {
        _recurringTimer.setPriority(123);

        assertEquals(123, _recurringTimer.getPriority());
    }

    @Test
    void testGame() {
        assertEquals(GAME, _recurringTimer.game());
    }

    @Test
    void testLogger() {
        assertEquals(LOGGER, _recurringTimer.logger());
    }

    @Test
    void testId() {
        assertEquals(TIMER_ID, _recurringTimer.id());
    }
}

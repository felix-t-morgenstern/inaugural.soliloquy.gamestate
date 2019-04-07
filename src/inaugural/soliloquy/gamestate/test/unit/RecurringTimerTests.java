package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.RecurringTimer;
import inaugural.soliloquy.gamestate.test.stubs.GameStub;
import inaugural.soliloquy.gamestate.test.stubs.LoggerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.game.primary.specs.IGame;
import soliloquy.gamestate.specs.IRecurringTimer;
import soliloquy.logger.specs.ILogger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecurringTimerTests {
    private IRecurringTimer _recurringTimer;

    private final String TIMER_ID = "TimerId";
    private final String TIMER_ACTION_ID = "TimerActionId";

    private final IGame GAME = new GameStub();
    private final ILogger LOGGER = new LoggerStub();

    @BeforeEach
    public void setUp() {
        _recurringTimer = new RecurringTimer(TIMER_ID, TIMER_ACTION_ID, GAME, LOGGER);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals("soliloquy.gamestate.specs.IRecurringTimer",
                _recurringTimer.getInterfaceName());
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
    void testTimerTypeId() {
        assertEquals(TIMER_ACTION_ID, _recurringTimer.timerActionId());
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

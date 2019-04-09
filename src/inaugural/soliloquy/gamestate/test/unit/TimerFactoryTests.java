package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TimerFactory;
import inaugural.soliloquy.gamestate.test.stubs.GameStub;
import inaugural.soliloquy.gamestate.test.stubs.LoggerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.game.primary.specs.IGame;
import soliloquy.gamestate.specs.IOneTimeTimer;
import soliloquy.gamestate.specs.IRecurringTimer;
import soliloquy.gamestate.specs.ITimerFactory;
import soliloquy.logger.specs.ILogger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimerFactoryTests {
    private ITimerFactory _timerFactory;

    private final IGame GAME = new GameStub();
    private final ILogger LOGGER = new LoggerStub();

    @BeforeEach
    void setUp() {
        _timerFactory = new TimerFactory(GAME, LOGGER);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals("soliloquy.gamestate.specs.ITimerFactory",
                _timerFactory.getInterfaceName());
    }

    @Test
    void testMakeOneTimeTimer() {
        String timerId = "TimerId";
        String timerActionId = "TimerTypeId";
        long roundWhenGoesOff = 123123L;

        IOneTimeTimer oneTimeTimer =
                _timerFactory.makeOneTimeTimer(timerId, timerActionId, roundWhenGoesOff);

        assertEquals(timerId, oneTimeTimer.id());
        assertEquals(timerActionId, oneTimeTimer.timerActionId());
        assertEquals(roundWhenGoesOff, oneTimeTimer.getRoundWhenGoesOff());
    }

    @Test
    void testMakeRecurringTimer() {
        String timerId = "TimerId";
        String timerActionId = "TimerTypeId";
        int roundModulo = 123;
        int roundOffset = 12;

        IRecurringTimer recurringTimer =
                _timerFactory.makeRecurringTimer(timerId, timerActionId, roundModulo, roundOffset);

        assertEquals(timerId, recurringTimer.id());
        assertEquals(timerActionId, recurringTimer.timerActionId());
        assertEquals(roundModulo, recurringTimer.getRoundModulo());
        assertEquals(roundOffset, recurringTimer.getRoundOffset());
    }
}

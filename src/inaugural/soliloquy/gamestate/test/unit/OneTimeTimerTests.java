package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.OneTimeTimer;
import inaugural.soliloquy.gamestate.test.stubs.GameStub;
import inaugural.soliloquy.gamestate.test.stubs.LoggerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.game.primary.specs.IGame;
import soliloquy.gamestate.specs.IOneTimeTimer;
import soliloquy.logger.specs.ILogger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OneTimeTimerTests {
    private IOneTimeTimer _oneTimeTimer;

    private final String TIMER_ID = "TimerId";
    private final String TIMER_ACTION_ID = "TimerActionId";

    private final IGame GAME = new GameStub();
    private final ILogger LOGGER = new LoggerStub();

    @BeforeEach
    void setUp() {
        _oneTimeTimer = new OneTimeTimer(TIMER_ID, TIMER_ACTION_ID, 0L, GAME, LOGGER);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals("soliloquy.gamestate.specs.IOneTimeTimer",
                _oneTimeTimer.getInterfaceName());
    }

    @Test
    void testSetAndGetRoundWhenGoesOff() {
        long roundWhenGoesOff = 123l;
        _oneTimeTimer.setRoundWhenGoesOff(roundWhenGoesOff);

        assertEquals(roundWhenGoesOff, _oneTimeTimer.getRoundWhenGoesOff());
    }

    @Test
    void testTimerTypeId() {
        assertEquals(TIMER_ACTION_ID, _oneTimeTimer.timerActionId());
    }

    @Test
    void testSetAndGetPrioirity() {
        _oneTimeTimer.setPriority(123);

        assertEquals(123, _oneTimeTimer.getPriority());
    }

    @Test
    void testGame() {
        assertEquals(GAME, _oneTimeTimer.game());
    }

    @Test
    void testLogger() {
        assertEquals(LOGGER, _oneTimeTimer.logger());
    }

    @Test
    void testId() {
        assertEquals(TIMER_ID, _oneTimeTimer.id());
    }
}

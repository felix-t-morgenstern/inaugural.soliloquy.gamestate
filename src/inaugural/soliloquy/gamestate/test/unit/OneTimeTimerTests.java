package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.OneTimeTimer;
import inaugural.soliloquy.gamestate.test.stubs.ActionStub;
import inaugural.soliloquy.gamestate.test.stubs.GameStub;
import inaugural.soliloquy.gamestate.test.stubs.LoggerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.IAction;
import soliloquy.specs.game.IGame;
import soliloquy.specs.gamestate.entities.IOneTimeTimer;
import soliloquy.specs.logger.ILogger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OneTimeTimerTests {
    private IOneTimeTimer _oneTimeTimer;

    private final String TIMER_ID = "TimerId";
    private final IAction<Void> ACTION = new ActionStub<>();

    private final IGame GAME = new GameStub();
    private final ILogger LOGGER = new LoggerStub();

    @BeforeEach
    void setUp() {
        _oneTimeTimer = new OneTimeTimer(TIMER_ID, ACTION, 0L, GAME, LOGGER);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(IOneTimeTimer.class.getCanonicalName(), _oneTimeTimer.getInterfaceName());
    }

    @Test
    void testEquals() {
        IOneTimeTimer oneTimeTimer = new OneTimeTimer(TIMER_ID, ACTION, 0L, GAME, LOGGER);
        assertEquals(_oneTimeTimer, oneTimeTimer);
    }

    @Test
    void testSetAndGetRoundWhenGoesOff() {
        long roundWhenGoesOff = 123l;
        _oneTimeTimer.setRoundWhenGoesOff(roundWhenGoesOff);

        assertEquals(roundWhenGoesOff, _oneTimeTimer.getRoundWhenGoesOff());
    }

    @Test
    void testTimerTypeId() {
        assertEquals(ACTION, _oneTimeTimer.action());
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

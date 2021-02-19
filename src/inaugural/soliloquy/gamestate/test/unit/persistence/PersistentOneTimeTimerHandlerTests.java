package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistentvaluetypehandlers.PersistentOneTimeTimerHandler;
import inaugural.soliloquy.gamestate.test.fakes.FakeAction;
import inaugural.soliloquy.gamestate.test.fakes.FakeOneTimeTimer;
import inaugural.soliloquy.gamestate.test.fakes.FakeRegistry;
import inaugural.soliloquy.gamestate.test.fakes.FakeTimerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.persistence.PersistentValueTypeHandler;
import soliloquy.specs.gamestate.entities.OneTimeTimer;
import soliloquy.specs.gamestate.factories.TimerFactory;

import static org.junit.jupiter.api.Assertions.*;

class PersistentOneTimeTimerHandlerTests {
    private final TimerFactory TIMER_FACTORY = new FakeTimerFactory();

    private final String ONE_TIME_TIMER_ID = "oneTimeTimerId";

    private final String ACTION_ID = "actionId";
    private final Action ACTION = new FakeAction(ACTION_ID);
    private final Registry<Action> ACTIONS = new FakeRegistry<>();

    private final long ROUND_WHEN_GOES_OFF = 123123123;

    private final String WRITTEN_VALUE = "{\"id\":\"oneTimeTimerId\",\"actionId\":\"actionId\",\"round\":123123123}";

    private PersistentValueTypeHandler<OneTimeTimer> _oneTimeTimerHandler;

    @BeforeEach
    void setUp() {
        ACTIONS.add(ACTION);

        _oneTimeTimerHandler = new PersistentOneTimeTimerHandler(TIMER_FACTORY, ACTIONS::get);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentOneTimeTimerHandler(null, ACTIONS::get));
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentOneTimeTimerHandler(TIMER_FACTORY, null));
    }

    @Test
    void testArchetype() {
        assertNotNull(_oneTimeTimerHandler.getArchetype());
        assertEquals(OneTimeTimer.class.getCanonicalName(),
                _oneTimeTimerHandler.getArchetype().getInterfaceName());
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(PersistentValueTypeHandler.class.getCanonicalName() + "<" +
                OneTimeTimer.class.getCanonicalName() + ">",
                _oneTimeTimerHandler.getInterfaceName());
    }

    @Test
    void testWrite() {
        OneTimeTimer oneTimeTimer = new FakeOneTimeTimer(ONE_TIME_TIMER_ID, ACTION);
        oneTimeTimer.setRoundWhenGoesOff(ROUND_WHEN_GOES_OFF);

        String writtenValue = _oneTimeTimerHandler.write(oneTimeTimer);

        assertEquals(WRITTEN_VALUE, writtenValue);
    }

    @Test
    void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _oneTimeTimerHandler.write(null));
    }

    @Test
    void testRead() {
        OneTimeTimer readResult = _oneTimeTimerHandler.read(WRITTEN_VALUE);

        assertNotNull(readResult);
        assertEquals(ONE_TIME_TIMER_ID, readResult.id());
        assertSame(ACTION, readResult.action());
        assertEquals(ROUND_WHEN_GOES_OFF, readResult.getRoundWhenGoesOff());
    }

    @Test
    void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _oneTimeTimerHandler.read(null));
        assertThrows(IllegalArgumentException.class, () -> _oneTimeTimerHandler.read(""));
    }
}

package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.OneTimeRoundBasedTimerHandler;
import inaugural.soliloquy.gamestate.test.fakes.FakeAction;
import inaugural.soliloquy.gamestate.test.fakes.FakeOneTimeRoundBasedTimer;
import inaugural.soliloquy.gamestate.test.fakes.FakeRegistry;
import inaugural.soliloquy.gamestate.test.fakes.FakeRoundBasedTimerFactory;
import org.junit.Before;
import org.junit.Test;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.timers.OneTimeRoundBasedTimer;
import soliloquy.specs.gamestate.factories.RoundBasedTimerFactory;

import static org.junit.Assert.*;

public class OneTimeRoundBasedTimerHandlerTests {
    private final RoundBasedTimerFactory TURN_BASED_TIMER_FACTORY =
            new FakeRoundBasedTimerFactory();

    private final String ONE_TIME_TIMER_ID = "oneTimeRoundBasedTimerId";

    private final String ACTION_ID = "actionId";
    @SuppressWarnings("rawtypes")
    private final Action ACTION = new FakeAction(ACTION_ID);
    @SuppressWarnings("rawtypes")
    private final Registry<Action> ACTIONS = new FakeRegistry<>();

    private final int ROUND_WHEN_GOES_OFF = 123123123;
    private final int PRIORITY = 456;

    private final String WRITTEN_VALUE =
            "{\"id\":\"oneTimeRoundBasedTimerId\",\"actionId\":\"actionId\",\"round\":123123123," +
                    "\"priority\":456}";

    private TypeHandler<OneTimeRoundBasedTimer> _oneTimeRoundBasedTimerHandler;

    @Before
    public void setUp() {
        ACTIONS.add(ACTION);

        _oneTimeRoundBasedTimerHandler =
                new OneTimeRoundBasedTimerHandler(TURN_BASED_TIMER_FACTORY, ACTIONS::get);
    }

    @Test
    public void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new OneTimeRoundBasedTimerHandler(null, ACTIONS::get));
        assertThrows(IllegalArgumentException.class,
                () -> new OneTimeRoundBasedTimerHandler(TURN_BASED_TIMER_FACTORY, null));
    }

    @Test
    public void testArchetype() {
        assertNotNull(_oneTimeRoundBasedTimerHandler.archetype());
        assertEquals(OneTimeRoundBasedTimer.class.getCanonicalName(),
                _oneTimeRoundBasedTimerHandler.archetype().getInterfaceName());
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        OneTimeRoundBasedTimer.class.getCanonicalName() + ">",
                _oneTimeRoundBasedTimerHandler.getInterfaceName());
    }

    @Test
    public void testWrite() {
        OneTimeRoundBasedTimer oneTimeRoundBasedTimer = new FakeOneTimeRoundBasedTimer(
                ONE_TIME_TIMER_ID, ACTION, ROUND_WHEN_GOES_OFF, PRIORITY);

        String writtenValue = _oneTimeRoundBasedTimerHandler.write(oneTimeRoundBasedTimer);

        assertEquals(WRITTEN_VALUE, writtenValue);
    }

    @Test
    public void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _oneTimeRoundBasedTimerHandler.write(null));
    }

    @Test
    public void testRead() {
        OneTimeRoundBasedTimer readResult = _oneTimeRoundBasedTimerHandler.read(WRITTEN_VALUE);

        assertNotNull(readResult);
        assertEquals(ONE_TIME_TIMER_ID, readResult.id());
        assertSame(ACTION_ID, readResult.actionId());
        assertEquals(ROUND_WHEN_GOES_OFF, readResult.roundWhenGoesOff());
        assertEquals(PRIORITY, readResult.priority());
    }

    @Test
    public void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _oneTimeRoundBasedTimerHandler.read(null));
        assertThrows(IllegalArgumentException.class, () ->
                _oneTimeRoundBasedTimerHandler.read(""));
    }
}

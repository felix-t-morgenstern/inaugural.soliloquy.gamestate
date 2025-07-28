package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.OneTimeRoundBasedTimerHandler;
import inaugural.soliloquy.gamestate.test.fakes.FakeAction;
import inaugural.soliloquy.gamestate.test.fakes.FakeOneTimeRoundBasedTimer;
import inaugural.soliloquy.gamestate.test.fakes.FakeRoundBasedTimerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.timers.OneTimeRoundBasedTimer;
import soliloquy.specs.gamestate.factories.RoundBasedTimerFactory;

import java.util.Map;

import static inaugural.soliloquy.tools.collections.Collections.mapOf;
import static org.junit.jupiter.api.Assertions.*;
import static soliloquy.specs.common.valueobjects.Pair.pairOf;

public class OneTimeRoundBasedTimerHandlerTests {
    private final RoundBasedTimerFactory TURN_BASED_TIMER_FACTORY =
            new FakeRoundBasedTimerFactory();

    private final String ONE_TIME_TIMER_ID = "oneTimeRoundBasedTimerId";

    private final String ACTION_ID = "actionId";
    @SuppressWarnings("rawtypes")
    private final Action ACTION = new FakeAction(ACTION_ID);

    @SuppressWarnings("rawtypes")
    private Map<String, Action> actions;

    private final int ROUND_WHEN_GOES_OFF = 123123123;
    private final int PRIORITY = 456;

    private final String WRITTEN_VALUE =
            "{\"id\":\"oneTimeRoundBasedTimerId\",\"actionId\":\"actionId\",\"round\":123123123," +
                    "\"priority\":456}";

    private TypeHandler<OneTimeRoundBasedTimer> handler;

    @BeforeEach
    public void setUp() {
        actions = mapOf(pairOf(ACTION_ID, ACTION));

        handler =
                new OneTimeRoundBasedTimerHandler(TURN_BASED_TIMER_FACTORY, actions::get);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> new OneTimeRoundBasedTimerHandler(null, actions::get));
        assertThrows(IllegalArgumentException.class,
                () -> new OneTimeRoundBasedTimerHandler(TURN_BASED_TIMER_FACTORY, null));
    }

    @Test
    public void testWrite() {
        OneTimeRoundBasedTimer oneTimeRoundBasedTimer = new FakeOneTimeRoundBasedTimer(
                ONE_TIME_TIMER_ID, ACTION, ROUND_WHEN_GOES_OFF, PRIORITY);

        String writtenValue = handler.write(oneTimeRoundBasedTimer);

        assertEquals(WRITTEN_VALUE, writtenValue);
    }

    @Test
    public void testWriteWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () ->
                handler.write(null));
    }

    @Test
    public void testRead() {
        OneTimeRoundBasedTimer readResult = handler.read(WRITTEN_VALUE);

        assertNotNull(readResult);
        assertEquals(ONE_TIME_TIMER_ID, readResult.id());
        assertSame(ACTION_ID, readResult.actionId());
        assertEquals(ROUND_WHEN_GOES_OFF, readResult.roundWhenGoesOff());
        assertEquals(PRIORITY, readResult.priority());
    }

    @Test
    public void testReadWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () ->
                handler.read(null));
        assertThrows(IllegalArgumentException.class, () ->
                handler.read(""));
    }
}

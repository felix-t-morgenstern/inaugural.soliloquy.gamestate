package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.OneTimeTurnBasedTimerHandler;
import inaugural.soliloquy.gamestate.test.fakes.FakeAction;
import inaugural.soliloquy.gamestate.test.fakes.FakeOneTimeTurnBasedTimer;
import inaugural.soliloquy.gamestate.test.fakes.FakeRegistry;
import inaugural.soliloquy.gamestate.test.fakes.FakeTurnBasedTimerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.timers.OneTimeTurnBasedTimer;
import soliloquy.specs.gamestate.factories.TurnBasedTimerFactory;

import static org.junit.jupiter.api.Assertions.*;

class OneTimeTurnBasedTimerHandlerTests {
    private final TurnBasedTimerFactory TURN_BASED_TIMER_FACTORY = new FakeTurnBasedTimerFactory();

    private final String ONE_TIME_TIMER_ID = "oneTimeTurnBasedTimerId";

    private final String ACTION_ID = "actionId";
    @SuppressWarnings("rawtypes")
    private final Action ACTION = new FakeAction(ACTION_ID);
    @SuppressWarnings("rawtypes")
    private final Registry<Action> ACTIONS = new FakeRegistry<>();

    private final long ROUND_WHEN_GOES_OFF = 123123123;
    private final int PRIORITY = 456;

    private final String WRITTEN_VALUE = "{\"id\":\"oneTimeTurnBasedTimerId\",\"actionId\":\"actionId\",\"round\":123123123,\"priority\":456}";

    private TypeHandler<OneTimeTurnBasedTimer> _oneTimeTurnBasedTimerHandler;

    @BeforeEach
    void setUp() {
        ACTIONS.add(ACTION);

        _oneTimeTurnBasedTimerHandler = new OneTimeTurnBasedTimerHandler(TURN_BASED_TIMER_FACTORY, ACTIONS::get);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new OneTimeTurnBasedTimerHandler(null, ACTIONS::get));
        assertThrows(IllegalArgumentException.class,
                () -> new OneTimeTurnBasedTimerHandler(TURN_BASED_TIMER_FACTORY, null));
    }

    @Test
    void testArchetype() {
        assertNotNull(_oneTimeTurnBasedTimerHandler.getArchetype());
        assertEquals(OneTimeTurnBasedTimer.class.getCanonicalName(),
                _oneTimeTurnBasedTimerHandler.getArchetype().getInterfaceName());
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                OneTimeTurnBasedTimer.class.getCanonicalName() + ">",
                _oneTimeTurnBasedTimerHandler.getInterfaceName());
    }

    @Test
    void testWrite() {
        OneTimeTurnBasedTimer oneTimeTurnBasedTimer = new FakeOneTimeTurnBasedTimer(
                ONE_TIME_TIMER_ID, ACTION, ROUND_WHEN_GOES_OFF, PRIORITY);

        String writtenValue = _oneTimeTurnBasedTimerHandler.write(oneTimeTurnBasedTimer);

        assertEquals(WRITTEN_VALUE, writtenValue);
    }

    @Test
    void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _oneTimeTurnBasedTimerHandler.write(null));
    }

    @Test
    void testRead() {
        OneTimeTurnBasedTimer readResult = _oneTimeTurnBasedTimerHandler.read(WRITTEN_VALUE);

        assertNotNull(readResult);
        assertEquals(ONE_TIME_TIMER_ID, readResult.id());
        assertSame(ACTION, readResult.action());
        assertEquals(ROUND_WHEN_GOES_OFF, readResult.roundWhenGoesOff());
        assertEquals(PRIORITY, readResult.priority());
    }

    @Test
    void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _oneTimeTurnBasedTimerHandler.read(null));
        assertThrows(IllegalArgumentException.class, () -> _oneTimeTurnBasedTimerHandler.read(""));
    }
}

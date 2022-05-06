package inaugural.soliloquy.gamestate.test.unit.entities.timers;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import soliloquy.specs.gamestate.entities.timers.OneTimeRoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RecurringRoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;

class RoundBasedTimerManagerImplTests {
    @Mock private OneTimeRoundBasedTimer _mockOneTimeRoundBasedTimer;
    @Mock private RecurringRoundBasedTimer _mockRecurringRoundBasedTimer;

    private RoundBasedTimerManager _roundBasedTimerManager;

    @BeforeEach
    void setUp() {

    }


}

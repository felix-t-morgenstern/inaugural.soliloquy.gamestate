package inaugural.soliloquy.gamestate.entities.timers;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.gamestate.entities.gameevents.GameEventFiring;
import soliloquy.specs.gamestate.entities.timers.OneTimeRoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RecurringRoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;

import java.util.*;

public class RoundBasedTimerManagerImpl implements RoundBasedTimerManager {
    private final GameEventFiring GAME_EVENT_FIRING;
    private final HashSet<OneTimeRoundBasedTimer> ONE_TIME_ROUND_BASED_TIMERS = new HashSet<>();
    private final HashSet<RecurringRoundBasedTimer> RECURRING_ROUND_BASED_TIMERS = new HashSet<>();

    public RoundBasedTimerManagerImpl(GameEventFiring gameEventFiring) {
        GAME_EVENT_FIRING = Check.ifNull(gameEventFiring, "gameEventFiring");
    }

    @Override
    public void registerOneTimeRoundBasedTimer(OneTimeRoundBasedTimer oneTimeRoundBasedTimer)
            throws IllegalArgumentException {
        ONE_TIME_ROUND_BASED_TIMERS.add(
                Check.ifNull(oneTimeRoundBasedTimer, "oneTimeRoundBasedTimer"));
    }

    @Override
    public void deregisterOneTimeRoundBasedTimer(OneTimeRoundBasedTimer oneTimeRoundBasedTimer)
            throws IllegalArgumentException {
        ONE_TIME_ROUND_BASED_TIMERS.remove(
                Check.ifNull(oneTimeRoundBasedTimer, "oneTimeRoundBasedTimer"));
    }

    @Override
    public void registerRecurringRoundBasedTimer(RecurringRoundBasedTimer recurringRoundBasedTimer)
            throws IllegalArgumentException {
        RECURRING_ROUND_BASED_TIMERS.add(
                Check.ifNull(recurringRoundBasedTimer, "recurringRoundBasedTimer"));
    }

    @Override
    public void deregisterRecurringRoundBasedTimer(RecurringRoundBasedTimer
                                                           recurringRoundBasedTimer)
            throws IllegalArgumentException {
        RECURRING_ROUND_BASED_TIMERS.remove(
                Check.ifNull(recurringRoundBasedTimer, "recurringRoundBasedTimer"));
    }

    @Override
    public void fireTimersForRoundsElapsed(int previousRound, int newRound)
            throws IllegalArgumentException {
        Check.throwOnSecondLte(previousRound, newRound, "previousRound", "newRound");

        HashMap<Integer, List<RoundBasedTimer>> timersFiredByRound = new HashMap<>();

        ONE_TIME_ROUND_BASED_TIMERS.forEach(oneTimeRoundBasedTimer -> {
            if (newRound > oneTimeRoundBasedTimer.roundWhenGoesOff()) {
                addRoundBasedTimerToFire(oneTimeRoundBasedTimer,
                        oneTimeRoundBasedTimer.roundWhenGoesOff(), timersFiredByRound);
            }
        });

        for (int i = previousRound; i < newRound; i++) {
            int round = i;
            RECURRING_ROUND_BASED_TIMERS.forEach(recurringRoundBasedTimer -> {
                if ((round - recurringRoundBasedTimer.roundOffset()) %
                        recurringRoundBasedTimer.roundModulo() == 0) {
                    addRoundBasedTimerToFire(recurringRoundBasedTimer, round, timersFiredByRound);
                }
            });
        }

        ArrayList<Integer> roundsToFire = new ArrayList<>(timersFiredByRound.keySet());
        Collections.sort(roundsToFire);
        for (int roundToFire : roundsToFire) {
            timersFiredByRound.get(roundToFire)
                    .forEach(timer -> GAME_EVENT_FIRING.registerEvent(timer, timer.priority()));
        }
    }

    private void addRoundBasedTimerToFire(RoundBasedTimer roundBasedTimer, int round,
                                          HashMap<Integer, List<RoundBasedTimer>> timersFiredByRound) {
        if (!timersFiredByRound.containsKey(round)) {
            timersFiredByRound.put(round, new ArrayList<>());
        }

        timersFiredByRound.get(round).add(roundBasedTimer);
    }

    @Override
    public List<OneTimeRoundBasedTimer> oneTimeRoundBasedTimersRepresentation() {
        return new ArrayList<>(ONE_TIME_ROUND_BASED_TIMERS);
    }

    @Override
    public List<RecurringRoundBasedTimer> recurringRoundBasedTimersRepresentation() {
        return new ArrayList<>(RECURRING_ROUND_BASED_TIMERS);
    }

    @Override
    public String getInterfaceName() {
        return RoundBasedTimerManager.class.getCanonicalName();
    }
}

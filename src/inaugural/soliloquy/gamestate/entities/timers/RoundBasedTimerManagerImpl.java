package inaugural.soliloquy.gamestate.entities.timers;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.gamestate.entities.timers.OneTimeRoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RecurringRoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;

import java.util.*;

public class RoundBasedTimerManagerImpl implements RoundBasedTimerManager {
    private final HashSet<OneTimeRoundBasedTimer> ONE_TIME_ROUND_BASED_TIMERS = new HashSet<>();
    private final HashSet<RecurringRoundBasedTimer> RECURRING_ROUND_BASED_TIMERS = new HashSet<>();

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
                if (!timersFiredByRound.containsKey(oneTimeRoundBasedTimer.roundWhenGoesOff())) {
                    timersFiredByRound.put(oneTimeRoundBasedTimer.roundWhenGoesOff(),
                            new ArrayList<>());
                }

                timersFiredByRound.get(oneTimeRoundBasedTimer.roundWhenGoesOff())
                        .add(oneTimeRoundBasedTimer);
            }
        });

        ArrayList<Integer> roundsToFire = new ArrayList<>(timersFiredByRound.keySet());
        Collections.sort(roundsToFire);
        for (int roundToFire : roundsToFire) {
            timersFiredByRound.get(roundToFire).forEach(RoundBasedTimer::run);
        }
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

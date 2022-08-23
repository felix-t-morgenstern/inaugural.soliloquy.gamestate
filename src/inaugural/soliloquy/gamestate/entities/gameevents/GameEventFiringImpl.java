package inaugural.soliloquy.gamestate.entities.gameevents;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.gamestate.entities.gameevents.GameEventFiring;
import soliloquy.specs.gamestate.infrastructure.GameSaveBlocker;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class GameEventFiringImpl implements GameEventFiring {
    private final GameSaveBlocker GAME_SAVE_BLOCKER;
    private final Consumer<Throwable> HANDLE_ERROR;

    private final ConcurrentHashMap<Integer, LinkedBlockingQueue<Runnable>> QUEUE =
            new ConcurrentHashMap<>();

    private UUID _currentBlockId;

    public GameEventFiringImpl(GameSaveBlocker gameSaveBlocker,
                               Consumer<Throwable> handleError) {
        GAME_SAVE_BLOCKER = Check.ifNull(gameSaveBlocker, "gameSaveBlocker");
        HANDLE_ERROR = Check.ifNull(handleError, "handleError");
        _currentBlockId = null;
    }

    @Override
    public synchronized void registerEvent(Runnable event, int priority)
            throws IllegalArgumentException {
        addEventToQueue(event, priority);
    }

    private synchronized void addEventToQueue(Runnable event, int priority) {
        LinkedBlockingQueue<Runnable> eventsAtPriority;
        if (!QUEUE.containsKey(priority)) {
            eventsAtPriority = new LinkedBlockingQueue<>();
            QUEUE.put(priority, eventsAtPriority);
        }
        else {
            eventsAtPriority = QUEUE.get(priority);
        }
        eventsAtPriority.offer(event);

        UUID newBlock = setBlockIfNoneExists();

        if (newBlock != null) {
            GAME_SAVE_BLOCKER.placeEventFiringBlock(newBlock);
            runAllEventsInQueueRecursively();
        }
    }

    private synchronized void removeEventFromQueue(Runnable event, int priority) {
        LinkedBlockingQueue<Runnable> eventsAtPriority = QUEUE.get(priority);
        eventsAtPriority.remove(event);
        if (eventsAtPriority.isEmpty()) {
            QUEUE.remove(priority);
        }
    }

    private synchronized void runAllEventsInQueueRecursively() {
        int highestPriority = Collections.max(QUEUE.keySet());
        Runnable event = QUEUE.get(highestPriority).peek();
        assert event != null;
        CompletableFuture.runAsync(() -> {
            try {
                event.run();
                removeEventFromQueue(event, highestPriority);
                if (!QUEUE.isEmpty()) {
                    runAllEventsInQueueRecursively();
                }
                else {
                    GAME_SAVE_BLOCKER.releaseEventFiringBlock(_currentBlockId);
                    _currentBlockId = null;
                }
            }
            catch (Exception e) {
                HANDLE_ERROR.accept(e);
            }
        });
    }

    private synchronized UUID setBlockIfNoneExists() {
        return _currentBlockId == null ? _currentBlockId = UUID.randomUUID() : null;
    }

    @Override
    public boolean freeForGameplayInput() {
        return QUEUE.isEmpty();
    }

    @Override
    public String getInterfaceName() {
        return GameEventFiring.class.getCanonicalName();
    }
}

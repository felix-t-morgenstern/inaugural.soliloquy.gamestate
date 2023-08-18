package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.entities.Action;

import java.util.List;

import static inaugural.soliloquy.tools.collections.Collections.listOf;

public class FakeAction<T> implements Action<T> {
    private final String id;
    public boolean actionRun;
    public T mostRecentInput;

    @SuppressWarnings("rawtypes") public static final List<FakeAction> ACTIONS_FIRED = listOf();

    public FakeAction(String id) {
        this.id = id;
    }

    @Override
    public void run(T t) throws IllegalArgumentException {
        actionRun = true;
        mostRecentInput = t;
        ACTIONS_FIRED.add(this);
    }

    @Override
    public String id() throws IllegalStateException {
        return id;
    }

    @Override
    public T archetype() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

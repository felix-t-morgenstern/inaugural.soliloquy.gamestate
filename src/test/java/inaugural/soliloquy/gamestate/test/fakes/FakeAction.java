package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.entities.Action;

import java.util.ArrayList;
import java.util.List;

public class FakeAction<T> implements Action<T> {
    private final String _id;
    public boolean _actionRun;
    public T _mostRecentInput;

    @SuppressWarnings("rawtypes") public static final List<FakeAction> ACTIONS_FIRED =
            new ArrayList<>();

    public FakeAction(String id) {
        _id = id;
    }

    @Override
    public void run(T t) throws IllegalArgumentException {
        _actionRun = true;
        _mostRecentInput = t;
        ACTIONS_FIRED.add(this);
    }

    @Override
    public String id() throws IllegalStateException {
        return _id;
    }

    @Override
    public T getArchetype() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

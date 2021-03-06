package inaugural.soliloquy.gamestate.test.fakes.persistence;

import soliloquy.specs.common.persistence.PersistentValueTypeHandler;

import java.util.ArrayList;
import java.util.List;

public abstract class FakePersistentValueTypeHandler<T> implements PersistentValueTypeHandler<T> {
    public final List<String> READ_INPUTS = new ArrayList<>();
    public final List<T> READ_OUTPUTS = new ArrayList<>();

    public final List<T> WRITE_INPUTS = new ArrayList<>();
    public final List<String> WRITE_OUTPUTS = new ArrayList<>();

    public abstract String typeName();

    protected abstract T generateInstance();

    @Override
    public T read(String readValue) throws IllegalArgumentException {
        if (readValue == null || readValue.equals(""))
        {
            throw new IllegalArgumentException();
        }
        READ_INPUTS.add(readValue);
        T output = generateInstance();
        READ_OUTPUTS.add(output);
        return output;
    }

    @Override
    public String write(T t) {
        WRITE_INPUTS.add(t);
        String output = typeName() + WRITE_OUTPUTS.size();
        WRITE_OUTPUTS.add(output);
        return output;
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

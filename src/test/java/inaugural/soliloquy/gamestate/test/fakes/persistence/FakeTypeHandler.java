package inaugural.soliloquy.gamestate.test.fakes.persistence;

import soliloquy.specs.common.persistence.TypeHandler;

import java.util.List;

import static inaugural.soliloquy.tools.collections.Collections.listOf;

public abstract class FakeTypeHandler<T> implements TypeHandler<T> {
    public final List<String> READ_INPUTS = listOf();
    public final List<T> READ_OUTPUTS = listOf();

    public final List<T> WRITE_INPUTS = listOf();
    public final List<String> WRITE_OUTPUTS = listOf();

    public abstract String typeName();

    protected abstract T generateInstance();

    @Override
    public T read(String readValue) throws IllegalArgumentException {
        if (readValue == null || readValue.equals("")) {
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
    public T archetype() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.infrastructure.ImmutableMap;
import soliloquy.specs.gamestate.entities.Setting;

import java.util.Objects;

public class SettingImpl<T> implements Setting<T> {
    private final String ID;
    private final ImmutableMap<String, Object> CONTROL_PARAMS;

    private String name;
    private T value;

    public SettingImpl(String id, String name, T defaultValue,
                       ImmutableMap<String, Object> controlParams) {
        ID = Check.ifNullOrEmpty(id, "id");
        this.name = Check.ifNullOrEmpty(name, "name");
        value = defaultValue;
        CONTROL_PARAMS = Check.ifNull(controlParams, "controlParams");
    }

    @Override
    public String id() throws IllegalStateException {
        return ID;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = Check.ifNullOrEmpty(name, "name");
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T value) throws IllegalArgumentException {
        this.value = value;
    }

    @Override
    public ImmutableMap<String, Object> controlParams() {
        return CONTROL_PARAMS;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, name, value, CONTROL_PARAMS);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Setting)) {
            return false;
        }
        //noinspection rawtypes
        return ((Setting) o).id().equals(ID);
    }
}


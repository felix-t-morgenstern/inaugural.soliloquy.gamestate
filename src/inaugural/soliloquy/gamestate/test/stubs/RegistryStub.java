package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.shared.HasId;

import java.util.HashMap;
import java.util.Iterator;

public class RegistryStub<T extends HasId> implements Registry<T> {
    private final HashMap<String,T> REGISTRY = new HashMap<>();

    @Override
    public boolean contains(String s) {
        return REGISTRY.containsKey(s);
    }

    @Override
    public T get(String s) {
        return REGISTRY.get(s);
    }

    @Override
    public void register(T t) throws IllegalArgumentException {
        REGISTRY.put(t.id(), t);
    }

    @Override
    public boolean remove(String s) {
        return REGISTRY.remove(s) != null;
    }

    @Override
    public int size() {
        return REGISTRY.size();
    }

    @Override
    public Iterator<T> iterator() {
        return REGISTRY.values().iterator();
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

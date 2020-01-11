package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.ReadableCollection;
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
    public void add(T t) throws IllegalArgumentException {
        REGISTRY.put(t.id(), t);
    }

    @Override
    public void addAll(ReadableCollection<? extends T> collection) throws UnsupportedOperationException {

    }

    @Override
    public void addAll(T[] ts) throws UnsupportedOperationException {

    }

    @Override
    public void clear() throws UnsupportedOperationException {

    }

    @Override
    public boolean remove(T t) throws UnsupportedOperationException {
        return false;
    }

    @Override
    public ReadableCollection<T> representation() {
        return null;
    }

    @Override
    public boolean remove(String s) {
        return REGISTRY.remove(s) != null;
    }

    @Override
    public boolean contains(T t) {
        return false;
    }

    @Override
    public boolean equals(ReadableCollection<T> readableCollection) {
        return false;
    }

    @Override
    public T get(int i) {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
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

    @Override
    public Collection<T> makeClone() {
        return null;
    }
}

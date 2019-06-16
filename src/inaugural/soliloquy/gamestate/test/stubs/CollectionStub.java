package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.entities.IFunction;
import soliloquy.specs.common.valueobjects.ICollection;

import java.util.ArrayList;
import java.util.Iterator;

public class CollectionStub<V> implements ICollection<V> {
    private V _archetype;
    private ArrayList<V> _collection = new ArrayList<>();

    public CollectionStub() {
    }

    CollectionStub(V archetype) {
        _archetype = archetype;
    }

    @Override
    public Iterator<V> iterator() {
        return _collection.iterator();
    }

    @Override
    public ICollection<V> makeClone() {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public V getArchetype() {
        return _archetype;
    }

    @Override
    public String getInterfaceName() {
        return "soliloquy.common.specs.ICollection<" + _archetype.getClass().getCanonicalName() + ">";
    }

    @Override
    public void add(V item) throws UnsupportedOperationException {
        _collection.add(item);
    }

    @Override
    public void addAll(ICollection<? extends V> items) throws UnsupportedOperationException {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public void addAll(V[] items) throws UnsupportedOperationException {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() throws UnsupportedOperationException {
        // Stub method; unimplemented
        _collection.clear();
    }

    @Override
    public boolean contains(V item) {
        return _collection.contains(item);
    }

    @Override
    public boolean equals(ICollection<V> items) {
        if (items == null) return false;
        if (_collection.size() != items.size()) return false;
        for(V item : _collection) if(!items.contains(item)) return false;
        return true;
    }

    @Override
    public V get(int index) {
        return _collection.get(index);
    }

    @Override
    public boolean isEmpty() {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return _collection.size();
    }

    @Override
    public boolean removeItem(V item) throws UnsupportedOperationException {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public ICollection<IFunction<V, String>> validators() {
        return null;
    }

    @Override
    public String getUnparameterizedInterfaceName() {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }
}
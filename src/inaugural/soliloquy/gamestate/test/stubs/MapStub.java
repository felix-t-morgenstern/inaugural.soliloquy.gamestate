package inaugural.soliloquy.gamestate.test.stubs;


import soliloquy.common.specs.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapStub<K,V> implements IMap<K,V> {
    private HashMap<K,V> _map = new HashMap<>();

    @Override
    public Iterator<IPair<K, V>> iterator() {
        return new MapIterator(_map, new PairFactoryStub());
    }

    @Override
    public K getFirstArchetype() throws IllegalStateException {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public V getSecondArchetype() throws IllegalStateException {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public String getInterfaceName() {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public IMap<K, V> makeClone() {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsKey(K key) {
        return _map.containsKey(key);
    }

    @Override
    public boolean containsValue(V value) {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(IPair<K, V> item) throws IllegalArgumentException {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(ICollection<V> items) throws IllegalArgumentException {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(IMap<K, V> map) throws IllegalArgumentException {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public V get(K key) throws IllegalArgumentException, IllegalStateException {
        return _map.get(key);
    }

    @Override
    public ICollection<K> getKeys() {
        ICollection<K> keys = new CollectionStub<>();
        for (K key : _map.keySet())
        {
            keys.add(key);
        }
        return keys;
    }

    @Override
    public ICollection<V> getValues() {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public ICollection<K> indicesOf(V item) {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean itemExists(K key) {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public void put(K key, V value) throws IllegalArgumentException {
        _map.put(key, value);
    }

    @Override
    public void putAll(ICollection<IPair<K, V>> items) throws IllegalArgumentException {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public V removeByKey(K key) {
        return _map.remove(key);
    }

    @Override
    public boolean removeByKeyAndValue(K key, V value) {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public void setValidator(IFunction<IPair<K, V>, String> validator) {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return _map.size();
    }

    @Override
    public String getUnparameterizedInterfaceName() {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    private class MapIterator implements Iterator<IPair<K,V>> {
        private Iterator<Map.Entry<K,V>> _hashMapIterator;
        private IPairFactory _pairFactory;

        MapIterator(HashMap<K,V> hashMap, IPairFactory pairFactory) {
            _hashMapIterator = hashMap.entrySet().iterator();
            _pairFactory = pairFactory;
        }

        @Override
        public boolean hasNext() {
            return _hashMapIterator.hasNext();
        }

        @Override
        public IPair<K, V> next() {
            Map.Entry<K,V> entry = _hashMapIterator.next();
            return _pairFactory.make(entry.getKey(), entry.getValue());
        }

    }
}

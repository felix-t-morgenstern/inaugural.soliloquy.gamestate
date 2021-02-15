package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.gamestate.entities.gameevents.GameCharacterEvent;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

public class GameCharacterEventReadableCollectionArchetype implements List<GameCharacterEvent> {

    @Override
    public GameCharacterEvent get(int i) {
        return null;
    }

    @Override
    public GameCharacterEvent set(int i, GameCharacterEvent gameCharacterEvent) {
        return null;
    }

    @Override
    public void add(int i, GameCharacterEvent gameCharacterEvent) {

    }

    @Override
    public GameCharacterEvent remove(int i) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<GameCharacterEvent> listIterator() {
        return null;
    }

    @Override
    public ListIterator<GameCharacterEvent> listIterator(int i) {
        return null;
    }

    @Override
    public java.util.List<GameCharacterEvent> subList(int i, int i1) {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return null;
    }

    @Override
    public boolean add(GameCharacterEvent gameCharacterEvent) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends GameCharacterEvent> collection) {
        return false;
    }

    @Override
    public boolean addAll(int i, Collection<? extends GameCharacterEvent> collection) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Iterator<GameCharacterEvent> iterator() {
        return null;
    }

    @Override
    public List<GameCharacterEvent> makeClone() {
        return null;
    }

    @Override
    public GameCharacterEvent getArchetype() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return List.class.getCanonicalName() + "<" +
                GameCharacterEvent.class.getCanonicalName() + ">";
    }
}

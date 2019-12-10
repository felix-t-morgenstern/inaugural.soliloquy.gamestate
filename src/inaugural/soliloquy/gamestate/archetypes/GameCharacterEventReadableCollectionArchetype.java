package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.gamestate.entities.gameevents.GameCharacterEvent;

import java.util.Iterator;

public class GameCharacterEventReadableCollectionArchetype
        implements ReadableCollection<GameCharacterEvent> {
    @Override
    public boolean contains(GameCharacterEvent gameCharacterEvent) {
        return false;
    }

    @Override
    public boolean equals(ReadableCollection<GameCharacterEvent> readableCollection) {
        return false;
    }

    @Override
    public GameCharacterEvent get(int i) {
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
        return 0;
    }

    @Override
    public Iterator<GameCharacterEvent> iterator() {
        return null;
    }

    @Override
    public Collection<GameCharacterEvent> makeClone() {
        return null;
    }

    @Override
    public GameCharacterEvent getArchetype() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return ReadableCollection.class.getCanonicalName() + "<" +
                GameCharacterEvent.class.getCanonicalName() + ">";
    }
}

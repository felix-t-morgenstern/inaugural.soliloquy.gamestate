package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.common.shared.HasId;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEntitiesOfType;
import soliloquy.specs.gamestate.entities.CharacterEntityOfType;

import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Function;

public class CharacterEntitiesOfTypeStub<TEntityType extends HasId,
        TCharacterEntity extends CharacterEntityOfType<TEntityType>>
            implements CharacterEntitiesOfType<TEntityType, TCharacterEntity> {
    public Character CHARACTER;
    public HashMap<TEntityType, TCharacterEntity> ENTITIES;
    public Function<TEntityType, Function<Character,TCharacterEntity>> MAKE_ENTITY;

    public boolean _isDeleted;

    public CharacterEntitiesOfTypeStub(Character character,
                                       Function<TEntityType, Function<Character,TCharacterEntity>>
                                               makeEntity) {
        CHARACTER = character;
        MAKE_ENTITY = makeEntity;
        ENTITIES = new HashMap<>();
    }

    @Override
    public void add(TEntityType entityType) throws IllegalArgumentException {
        if (!ENTITIES.containsKey(entityType)) {
            ENTITIES.put(entityType, MAKE_ENTITY.apply(entityType).apply(CHARACTER));
        }
    }

    @Override
    public TCharacterEntity get(TEntityType entityType) throws IllegalArgumentException {
        return ENTITIES.get(entityType);
    }

    @Override
    public int size() {
        return ENTITIES.size();
    }

    @Override
    public boolean remove(TEntityType entityType) throws IllegalArgumentException {
        return ENTITIES.remove(entityType) != null;
    }

    @Override
    public boolean contains(TEntityType entityType) throws IllegalArgumentException {
        return ENTITIES.containsKey(entityType);
    }

    @Override
    public void clear() {
        ENTITIES.clear();
    }

    @Override
    public ReadableCollection<TCharacterEntity> representation() {
        Collection<TCharacterEntity> representation = new CollectionStub<>();
        ENTITIES.values().forEach(representation::add);
        return representation.readOnlyRepresentation();
    }

    @Override
    public Iterator<TCharacterEntity> iterator() {
        return ENTITIES.values().iterator();
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public void delete() throws IllegalStateException {
        _isDeleted = true;
    }

    @Override
    public boolean isDeleted() {
        return _isDeleted;
    }
}

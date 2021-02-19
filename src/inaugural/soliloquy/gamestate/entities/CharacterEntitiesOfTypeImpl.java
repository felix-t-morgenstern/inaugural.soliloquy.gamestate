package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.generic.CanGetInterfaceName;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.shared.HasId;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEntitiesOfType;
import soliloquy.specs.gamestate.entities.CharacterEntityOfType;
import soliloquy.specs.gamestate.entities.Deletable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Function;

public class CharacterEntitiesOfTypeImpl<TType extends HasId,
        TEntity extends CharacterEntityOfType<TType>>
            extends HasDeletionInvariants implements CharacterEntitiesOfType<TType,TEntity> {
    private final Character CHARACTER;
    private final Function<Character,Function<TType,Function<VariableCache,TEntity>>>
            ENTITY_FACTORY;
    private final ListFactory LIST_FACTORY;
    private final VariableCacheFactory DATA_FACTORY;
    private final TEntity ARCHETYPE;

    private static final CanGetInterfaceName CAN_GET_INTERFACE_NAME = new CanGetInterfaceName();

    final HashMap<TType, TEntity> ENTITIES = new HashMap<>();

    public CharacterEntitiesOfTypeImpl(Character character,
                                       Function<Character,Function<TType,Function<VariableCache,
                                               TEntity>>> entityFactory,
                                       ListFactory listFactory,
                                       VariableCacheFactory dataFactory,
                                       TEntity archetype) {
        CHARACTER = Check.ifNull(character, "character");
        ENTITY_FACTORY = Check.ifNull(entityFactory, "entityFactory");
        LIST_FACTORY = Check.ifNull(listFactory, "listFactory");
        DATA_FACTORY = Check.ifNull(dataFactory, "dataFactory");
        ARCHETYPE = Check.ifNull(archetype, "archetype");
    }

    @Override
    public void add(TType type) throws IllegalArgumentException {
        add(type, DATA_FACTORY.make());
    }

    @Override
    public void add(TType type, VariableCache data) throws IllegalArgumentException {
        enforceDeletionInvariants("add");
        if (!ENTITIES.containsKey(type)) {
            ENTITIES.put(Check.ifNull(type, "type"),
                    ENTITY_FACTORY.apply(CHARACTER).apply(type).apply(Check.ifNull(data, "data")));
        }
    }

    @Override
    public TEntity get(TType type) throws IllegalArgumentException {
        enforceDeletionInvariants("get");
        if (type == null) {
            throw new IllegalArgumentException(
                    "CharacterEntitiesOfTypeImpl.get: type cannot be null");
        }
        return ENTITIES.get(type);
    }

    @Override
    public int size() {
        enforceDeletionInvariants("size");
        return ENTITIES.size();
    }

    @Override
    public boolean remove(TType type) throws IllegalArgumentException {
        enforceDeletionInvariants("remove");
        if (type == null) {
            throw new IllegalArgumentException(
                    "CharacterEntitiesOfTypeImpl.remove: type cannot be null");
        }
        return ENTITIES.remove(type) != null;
    }

    @Override
    public boolean contains(TType type) throws IllegalArgumentException {
        enforceDeletionInvariants("contains");
        if (type == null) {
            throw new IllegalArgumentException(
                    "CharacterEntitiesOfTypeImpl.contains: type cannot be null");
        }
        return ENTITIES.containsKey(type);
    }

    @Override
    public void clear() {
        enforceDeletionInvariants("clear");
        ENTITIES.clear();
    }

    @Override
    public List<TEntity> representation() {
        enforceDeletionInvariants("representation");
        List<TEntity> entities = LIST_FACTORY.make(ARCHETYPE);
        entities.addAll(ENTITIES.values());
        return entities;
    }

    @Override
    public Iterator<TEntity> iterator() {
        enforceDeletionInvariants("iterator");
        return ENTITIES.values().iterator();
    }

    @Override
    protected String className() {
        return "CharacterEntitiesOfTypeImpl";
    }

    @Override
    protected String containingClassName() {
        return "Character";
    }

    @Override
    Deletable getContainingObject() {
        return CHARACTER;
    }

    @Override
    public void delete() throws IllegalStateException {
        ENTITIES.values().forEach(Deletable::delete);
        ENTITIES.clear();
        super.delete();
    }

    @Override
    public String getInterfaceName() {
        return CharacterEntitiesOfType.class.getCanonicalName() + "<" +
                CAN_GET_INTERFACE_NAME.getProperTypeName(ARCHETYPE) + ">";
    }
}

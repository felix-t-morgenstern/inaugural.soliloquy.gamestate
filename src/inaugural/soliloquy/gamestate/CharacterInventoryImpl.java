package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.ItemArchetype;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterInventory;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;

import java.util.HashSet;
import java.util.Iterator;

public class CharacterInventoryImpl extends CanTellIfItemIsPresentElsewhere
        implements CharacterInventory {
    private final Character CHARACTER;
    private final CollectionFactory COLLECTION_FACTORY;
    private final HashSet<Item> INVENTORY;

    private final static Item ITEM_ARCHETYPE = new ItemArchetype();

    @SuppressWarnings("ConstantConditions")
    public CharacterInventoryImpl(Character character, CollectionFactory collectionFactory) {
        if (character == null) {
            throw new IllegalArgumentException(
                    "CharacterInventoryImpl: character must be non-null");
        }
        CHARACTER = character;
        if (collectionFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterInventoryImpl: collectionFactory must be non-null");
        }
        COLLECTION_FACTORY = collectionFactory;
        INVENTORY = new HashSet<>();
    }

    @Override
    protected String className() {
        return "CharacterInventoryImpl";
    }

    @Override
    protected String containingClassName() {
        return "Character";
    }

    @Override
    protected Deletable getContainingObject() {
        return CHARACTER;
    }

    @Override
    public ReadableCollection<Item> representation() throws IllegalStateException {
        // TODO: Consider using a Collection under the hood, to avoid duplicate Collection creation
        //       (and refactor similar implementations)
        enforceDeletionInvariants("_representation");
        Collection<Item> representation = COLLECTION_FACTORY.make(ITEM_ARCHETYPE);
        INVENTORY.forEach(representation::add);
        return representation.representation();
    }

    @Override
    public void add(Item item) throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("add");
        throwOnNullOrDeletedItem("add", item);
        if (itemIsPresentElsewhere(item)) {
            throw new IllegalArgumentException(
                    "CharacterInventoryImpl.add: item is present elsewhere");
        }
        INVENTORY.add(item);
    }

    @Override
    public boolean remove(Item item) throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("remove");
        throwOnNullOrDeletedItem("remove", item);
        return INVENTORY.remove(item);
    }

    @Override
    public boolean contains(Item item) throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("contains");
        throwOnNullOrDeletedItem("contains", item);
        return INVENTORY.contains(item);
    }

    private void throwOnNullOrDeletedItem(String methodName, Item item) {
        if (item == null) {
            throw new IllegalArgumentException("CharacterInventoryImpl." + methodName +
                    ": item cannot be null");
        }
        if (item.isDeleted()) {
            throw new IllegalArgumentException("CharacterInventoryImpl." + methodName +
                    ": item cannot be deleted");
        }
    }

    @Override
    public void afterDeleted() throws IllegalStateException {
        INVENTORY.forEach(Item::delete);
    }

    @Override
    public String getInterfaceName() {
        return CharacterInventory.class.getCanonicalName();
    }

    @Override
    public Iterator<Item> iterator() {
        return INVENTORY.iterator();
    }
}

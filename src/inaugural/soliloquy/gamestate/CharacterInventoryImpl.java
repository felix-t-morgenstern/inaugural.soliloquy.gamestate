package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.ItemArchetype;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterInventory;
import soliloquy.specs.gamestate.entities.Item;

import java.util.HashSet;
import java.util.function.Predicate;

public class CharacterInventoryImpl extends HasDeletionInvariants implements CharacterInventory {
    private final Character CHARACTER;
    private final CollectionFactory COLLECTION_FACTORY;
    private final Predicate<Item> ITEM_IS_PRESENT_ELSEWHERE;
    private final HashSet<Item> INVENTORY;

    private final static Item ITEM_ARCHETYPE = new ItemArchetype();

    @SuppressWarnings("ConstantConditions")
    public CharacterInventoryImpl(Character character, CollectionFactory collectionFactory,
                                  Predicate<Item> itemIsPresentElsewhere) {
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
        if (itemIsPresentElsewhere == null) {
            throw new IllegalArgumentException(
                    "CharacterInventoryImpl: itemIsPresentElsewhere must be non-null");
        }
        ITEM_IS_PRESENT_ELSEWHERE = itemIsPresentElsewhere;
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
    protected boolean containingObjectIsDeleted() {
        return CHARACTER.isDeleted();
    }

    @Override
    public ReadableCollection<Item> representation() throws IllegalStateException {
        enforceDeletionInvariants("representation");
        Collection<Item> representation = COLLECTION_FACTORY.make(ITEM_ARCHETYPE);
        INVENTORY.forEach(representation::add);
        return representation.readOnlyRepresentation();
    }

    @Override
    public void add(Item item) throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("add");
        throwOnNullOrDeletedItem("add", item);
        if (ITEM_IS_PRESENT_ELSEWHERE.test(item)) {
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
    public void delete() throws IllegalStateException {
        INVENTORY.forEach(Item::delete);
        _isDeleted = true;
    }

    @Override
    public String getInterfaceName() {
        return CharacterInventory.class.getCanonicalName();
    }
}

package inaugural.soliloquy.gamestate.test.fakes;

import inaugural.soliloquy.gamestate.test.stubs.ItemTypeStub;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.common.valueobjects.Vertex;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.ruleset.entities.ItemType;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbility;
import soliloquy.specs.ruleset.entities.abilities.PassiveAbility;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbility;

import java.util.List;
import java.util.UUID;

public class FakeItem implements Item {
    private final ItemType ITEM_TYPE = new ItemTypeStub();

    private boolean _deleted;

    public Character equipmentCharacter;
    public String equipmentSlotType;
    public Character inventoryCharacter;
    public TileFixture tileFixture;
    public Tile tile;
    public float xTileWidthOffset;
    public float yTileHeightOffset;

    private Integer charges;
    private Integer numberInStack;
    private UUID uuid;
    private ItemType itemType;
    private VariableCache data;

    public FakeItem() {

    }

    public FakeItem(ItemType itemType) {
        this.itemType = itemType;
    }

    public FakeItem(ItemType itemType, VariableCache data, UUID uuid) {
        this.uuid = uuid;
        this.itemType = itemType;
        this.data = data;
    }

    @Override
    public ItemType type() throws IllegalStateException {
        return itemType != null ? itemType : ITEM_TYPE;
    }

    @Override
    public Integer getCharges() throws IllegalStateException {
        return charges;
    }

    @Override
    public void setCharges(int charges)
            throws UnsupportedOperationException, IllegalStateException {
        this.charges = charges;
    }

    @Override
    public Integer getNumberInStack() throws IllegalStateException {
        return numberInStack;
    }

    @Override
    public void setNumberInStack(int numberInStack)
            throws UnsupportedOperationException, IllegalArgumentException, IllegalStateException {
        this.numberInStack = numberInStack;
    }

    @Override
    public Item takeFromStack(int i)
            throws UnsupportedOperationException, IllegalArgumentException, IllegalStateException {
        return null;
    }

    @Override
    public Character inventoryCharacter() throws IllegalStateException {
        return inventoryCharacter;
    }

    @Override
    public Pair<Character, String> equipmentSlot()
            throws IllegalStateException {
        if (equipmentCharacter == null || equipmentSlotType == null) {
            return null;
        }
        else {
            return new Pair<>(equipmentCharacter, equipmentSlotType);
        }
    }

    @Override
    public Tile tile() throws IllegalStateException {
        return tile;
    }

    @Override
    public TileFixture tileFixture() throws IllegalStateException {
        return tileFixture;
    }

    @Override
    public void assignInventoryCharacterAfterAddedToCharacterInventory(Character character)
            throws IllegalStateException, IllegalArgumentException {

    }

    @Override
    public void assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(
            Character character, String equipmentSlotType)
            throws IllegalStateException, IllegalArgumentException {
        equipmentCharacter = character;
        this.equipmentSlotType = equipmentSlotType;
    }

    @Override
    public void assignTileAfterAddedToTileEntitiesOfType(Tile tile)
            throws IllegalStateException, IllegalArgumentException {
        this.tile = tile;
    }

    @Override
    public void assignTileFixtureAfterAddedItemToTileFixtureItems(TileFixture tileFixture)
            throws IllegalArgumentException, IllegalStateException {
        this.tileFixture = tileFixture;
    }

    @Override
    public List<PassiveAbility> passiveAbilities() throws EntityDeletedException {
        return null;
    }

    @Override
    public List<ActiveAbility> activeAbilities() throws EntityDeletedException {
        return null;
    }

    @Override
    public List<ReactiveAbility> reactiveAbilities() throws EntityDeletedException {
        return null;
    }

    @Override
    public UUID uuid() {
        return uuid;
    }

    @Override
    public VariableCache data() throws IllegalStateException {
        return data;
    }

    @Override
    public void delete() throws IllegalStateException {
        _deleted = true;
    }

    @Override
    public boolean isDeleted() {
        return _deleted;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String s) {

    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public String getPluralName() {
        return null;
    }

    @Override
    public void setPluralName(String s) throws IllegalArgumentException {

    }

    @Override
    public Vertex getTileOffset() throws IllegalStateException, EntityDeletedException {
        return Vertex.of(xTileWidthOffset, yTileHeightOffset);
    }

    @Override
    public void setTileOffset(Vertex vertex)
            throws IllegalArgumentException, IllegalStateException, EntityDeletedException {
        xTileWidthOffset = vertex.X;
        yTileHeightOffset = vertex.Y;
    }
}

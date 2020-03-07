package inaugural.soliloquy.gamestate.test.fakes;

import inaugural.soliloquy.gamestate.test.stubs.ItemTypeStub;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.ruleset.entities.ItemType;

public class FakeItem implements Item {
    private final ItemType ITEM_TYPE = new ItemTypeStub();

    private boolean _deleted;

    public Character _equipmentCharacter;
    public String _equipmentSlotType;
    public Character _inventoryCharacter;
    public TileFixture _tileFixture;
    public Tile _tile;

    private Integer _charges;
    private Integer _numberInStack;
    private EntityUuid _id;
    private ItemType _itemType;
    private VariableCache _data;

    public FakeItem() {

    }

    public FakeItem(ItemType itemType, VariableCache data, EntityUuid id) {
        _id = id;
        _itemType = itemType;
        _data = data;
    }

    @Override
    public ItemType type() throws IllegalStateException {
        return _itemType != null ? _itemType : ITEM_TYPE;
    }

    @Override
    public Integer getCharges() throws IllegalStateException {
        return _charges;
    }

    @Override
    public void setCharges(int charges)
            throws UnsupportedOperationException, IllegalStateException {
        _charges = charges;
    }

    @Override
    public Integer getNumberInStack() throws IllegalStateException {
        return _numberInStack;
    }

    @Override
    public void setNumberInStack(int numberInStack)
            throws UnsupportedOperationException, IllegalArgumentException, IllegalStateException {
        _numberInStack = numberInStack;
    }

    @Override
    public Item takeFromStack(int i)
            throws UnsupportedOperationException, IllegalArgumentException, IllegalStateException {
        return null;
    }

    @Override
    public Character inventoryCharacter() throws IllegalStateException {
        return _inventoryCharacter;
    }

    @Override
    public Pair<Character, String> equipmentSlot()
            throws IllegalStateException {
        if (_equipmentCharacter == null || _equipmentSlotType == null) {
            return null;
        }
        else {
            return new FakePair<>(_equipmentCharacter, _equipmentSlotType);
        }
    }

    @Override
    public Tile tile() throws IllegalStateException {
        return _tile;
    }

    @Override
    public TileFixture tileFixture() throws IllegalStateException {
        return _tileFixture;
    }

    @Override
    public void assignInventoryCharacterAfterAddedToCharacterInventory(Character character)
            throws IllegalStateException, IllegalArgumentException {

    }

    @Override
    public void assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(
            Character character, String equipmentSlotType)
            throws IllegalStateException, IllegalArgumentException {
        _equipmentCharacter = character;
        _equipmentSlotType = equipmentSlotType;
    }

    @Override
    public void assignTileAfterAddedToTileEntitiesOfType(Tile tile)
            throws IllegalStateException, IllegalArgumentException {
        _tile = tile;
    }

    @Override
    public void assignTileFixtureAfterAddedItemToTileFixtureItems(TileFixture tileFixture)
            throws IllegalArgumentException, IllegalStateException {
        _tileFixture = tileFixture;
    }

    @Override
    public EntityUuid id() {
        return _id;
    }

    @Override
    public VariableCache data() throws IllegalStateException {
        return _data;
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
}

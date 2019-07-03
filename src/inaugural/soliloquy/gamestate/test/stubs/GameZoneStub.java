package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.entities.IAction;
import soliloquy.specs.common.infrastructure.ICollection;
import soliloquy.specs.common.infrastructure.IMap;
import soliloquy.specs.common.infrastructure.IReadOnlyMap;
import soliloquy.specs.common.valueobjects.ICoordinate;
import soliloquy.specs.common.valueobjects.IEntityUuid;
import soliloquy.specs.game.IGame;
import soliloquy.specs.gamestate.entities.ICharacter;
import soliloquy.specs.gamestate.entities.IGameZone;
import soliloquy.specs.gamestate.entities.IItem;
import soliloquy.specs.gamestate.entities.ITile;
import soliloquy.specs.logger.ILogger;

public class GameZoneStub implements IGameZone {
    public static int _maxX = 999;
    public static int _maxY = 999;

    public IMap<IEntityUuid, ICharacter> _characters = new MapStub<>();
    public IMap<IEntityUuid, IItem> _items = new MapStub<>();

    @Override
    public String zoneType() {
        return null;
    }

    @Override
    public ICoordinate getMaxCoordinates() {
        return new CoordinateStub(_maxX,_maxY);
    }

    @Override
    public void setDimensions(ICoordinate iCoordinate) throws IllegalArgumentException {

    }

    @Override
    public ITile tile(ICoordinate tileLocation) throws IllegalArgumentException {
        return new TileStub(tileLocation);
    }

    @Override
    public IReadOnlyMap<IEntityUuid, ICharacter> charactersRepresentation() {
        return _characters.readOnlyRepresentation();
    }

    @Override
    public void assignCharacterToGameZone(ICharacter character) {
        _characters.put(character.id(), character);
    }

    @Override
    public IReadOnlyMap<IEntityUuid, IItem> itemsRepresentation() {
        return _items.makeClone();
    }

    @Override
    public boolean containsItem(IItem iItem) {
        return false;
    }

    @Override
    public ICollection<IAction<Void>> onEntry() {
        return null;
    }

    @Override
    public ICollection<IAction<Void>> onExit() {
        return null;
    }

    @Override
    public IGame game() {
        return null;
    }

    @Override
    public ILogger logger() {
        return null;
    }

    @Override
    public String id() throws IllegalStateException {
        return null;
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
}

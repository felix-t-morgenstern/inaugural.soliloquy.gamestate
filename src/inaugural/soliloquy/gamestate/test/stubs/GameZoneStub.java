package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.common.specs.*;
import soliloquy.game.primary.specs.IGame;
import soliloquy.gamestate.specs.ICharacter;
import soliloquy.gamestate.specs.IGameZone;
import soliloquy.gamestate.specs.IItem;
import soliloquy.gamestate.specs.ITile;
import soliloquy.logger.specs.ILogger;

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
    public IMap<IEntityUuid, ICharacter> getCharactersRepresentation() {
        return _characters.makeClone();
    }

    @Override
    public boolean containsCharacter(ICharacter iCharacter) {
        return false;
    }

    @Override
    public IMap<IEntityUuid, IItem> getItemsRepresentation() {
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
    public void read(String s, boolean b) throws IllegalArgumentException {

    }

    @Override
    public String write() throws IllegalArgumentException {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

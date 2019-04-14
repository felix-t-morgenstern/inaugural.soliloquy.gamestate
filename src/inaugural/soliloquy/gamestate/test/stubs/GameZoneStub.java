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

    private IMap<IEntityUuid, ICharacter> _characters = new MapStub<>();

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
    public IMap<IEntityUuid, ICharacter> getCharacters() {
        return _characters.makeClone();
    }

    @Override
    public void addCharacter(ICharacter character) throws IllegalArgumentException {
        _characters.put(character.id(), character);
    }

    @Override
    public boolean removeCharacter(ICharacter character) throws IllegalArgumentException {
        return _characters.removeByKey(character.id()) != null;
    }

    @Override
    public IMap<IEntityUuid, IItem> getItems() {
        return null;
    }

    @Override
    public void addItem(IItem item) throws IllegalArgumentException {

    }

    @Override
    public boolean removeItem(IItem item) throws IllegalArgumentException {
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

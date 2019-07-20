package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.game.Game;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.logger.Logger;

public class GameZoneStub implements GameZone {
    public static int _maxX = 999;
    public static int _maxY = 999;

    public Map<EntityUuid, Character> _characters = new MapStub<>();
    public Map<EntityUuid, Item> _items = new MapStub<>();

    @Override
    public String zoneType() {
        return null;
    }

    @Override
    public Coordinate getMaxCoordinates() {
        return new CoordinateStub(_maxX,_maxY);
    }

    @Override
    public void setDimensions(Coordinate iCoordinate) throws IllegalArgumentException {

    }

    @Override
    public Tile tile(Coordinate tileLocation) throws IllegalArgumentException {
        return new TileStub(tileLocation);
    }

    @Override
    public ReadableMap<EntityUuid, Character> charactersRepresentation() {
        return _characters.readOnlyRepresentation();
    }

    @Override
    public void assignCharacterToGameZone(Character character) {
        _characters.put(character.id(), character);
    }

    @Override
    public ReadableMap<EntityUuid, Item> itemsRepresentation() {
        return _items.makeClone();
    }

    @Override
    public boolean containsItem(Item item) {
        return false;
    }

    @Override
    public Collection<Action<Void>> onEntry() {
        return null;
    }

    @Override
    public Collection<Action<Void>> onExit() {
        return null;
    }

    @Override
    public Game game() {
        return null;
    }

    @Override
    public Logger logger() {
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

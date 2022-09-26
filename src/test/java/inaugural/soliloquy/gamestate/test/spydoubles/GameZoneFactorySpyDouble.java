package inaugural.soliloquy.gamestate.test.spydoubles;

import inaugural.soliloquy.gamestate.test.fakes.FakeGameZone;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.GameZoneFactory;

public class GameZoneFactorySpyDouble implements GameZoneFactory {
    public String _inputId;
    public String _inputZoneType;
    public Tile[][] _inputTiles;
    public VariableCache _inputData;

    public final GameZone OUTPUT = new FakeGameZone();

    @Override
    public GameZone make(String id, String zoneType, Tile[][] tiles, VariableCache data)
            throws IllegalArgumentException {
        _inputId = id;
        _inputZoneType = zoneType;
        _inputTiles = tiles;
        _inputData = data;
        return OUTPUT;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

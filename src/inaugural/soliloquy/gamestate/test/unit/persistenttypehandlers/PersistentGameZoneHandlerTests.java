package inaugural.soliloquy.gamestate.test.unit.persistenttypehandlers;

import inaugural.soliloquy.gamestate.test.stubs.persistenttypehandlers.PersistentTileHandlerStub;
import inaugural.soliloquy.gamestate.test.stubs.persistenttypehandlers.PersistentVariableCacheHandlerStub;
import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;

class PersistentGameZoneHandlerTests {
    private final PersistentValueTypeHandler<Tile> TILE_HANDLER = new PersistentTileHandlerStub();
    private final PersistentValueTypeHandler<VariableCache> DATA_HANDLER =
            new PersistentVariableCacheHandlerStub();

    private PersistentValueTypeHandler<GameZone> _gameZoneHandler;
}

package inaugural.soliloquy.gamestate.persistentvaluetypehandlers;

import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentTypeHandler;
import inaugural.soliloquy.gamestate.archetypes.GameZoneArchetype;
import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;

public class PersistentGameZoneHandler extends PersistentTypeHandler<GameZone> {
    private final PersistentValueTypeHandler<Tile> TILE_HANDLER;
    private final PersistentValueTypeHandler<VariableCache> DATA_HANDLER;

    private static final GameZone ARCHETYPE = new GameZoneArchetype();

    @Override
    public GameZone getArchetype() {
        return ARCHETYPE;
    }

    @Override
    public GameZone read(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String write(GameZone gameZone) {
        return null;
    }
}

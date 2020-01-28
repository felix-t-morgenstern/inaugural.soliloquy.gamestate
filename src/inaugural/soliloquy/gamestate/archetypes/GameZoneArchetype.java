package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;

public class GameZoneArchetype implements GameZone {
    @Override
    public String type() {
        return null;
    }

    @Override
    public ReadableCoordinate maxCoordinates() {
        return null;
    }

    @Override
    public Tile tile(int i, int i1) throws IllegalArgumentException {
        return null;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Collection<Action> onEntry() {
        return null;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Collection<Action> onExit() {
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
    public void delete() throws IllegalStateException {

    }

    @Override
    public boolean isDeleted() {
        return false;
    }

    @Override
    public VariableCache data() throws IllegalStateException {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return GameZone.class.getCanonicalName();
    }
}

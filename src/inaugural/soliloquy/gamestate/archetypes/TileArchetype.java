package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.graphics.assets.Sprite;
import soliloquy.specs.ruleset.entities.GroundType;

public class TileArchetype implements Tile {
    @Override
    public GameZone gameZone() throws IllegalStateException {
        return null;
    }

    @Override
    public Coordinate location() throws IllegalStateException {
        return null;
    }

    @Override
    public int getHeight() throws IllegalStateException {
        return 0;
    }

    @Override
    public void setHeight(int i) throws IllegalStateException {

    }

    @Override
    public GroundType getGroundType() throws IllegalStateException {
        return null;
    }

    @Override
    public void setGroundType(GroundType groundType) throws IllegalStateException {

    }

    @Override
    public TileEntities<Character> characters() {
        return null;
    }

    @Override
    public TileEntities<Item> items() {
        return null;
    }

    @Override
    public TileEntities<TileFixture> fixtures() throws IllegalStateException {
        return null;
    }

    @Override
    public TileWallSegments wallSegments() throws IllegalStateException {
        return null;
    }

    @Override
    public List<GameMovementEvent> movementEvents() throws IllegalStateException {
        return null;
    }

    @Override
    public List<GameAbilityEvent> abilityEvents() throws IllegalStateException {
        return null;
    }

    @Override
    public GameEventTarget makeGameEventTarget() throws IllegalStateException {
        return null;
    }

    @Override
    public Map<Sprite, Integer> sprites() throws IllegalStateException {
        return null;
    }

    @Override
    public void assignGameZoneAfterAddedToGameZone(GameZone gameZone)
            throws IllegalArgumentException, IllegalStateException {

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
        return Tile.class.getCanonicalName();
    }
}

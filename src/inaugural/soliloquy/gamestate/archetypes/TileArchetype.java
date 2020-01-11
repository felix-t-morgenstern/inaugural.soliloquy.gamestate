package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.ruleset.entities.GroundType;
import soliloquy.specs.sprites.entities.Sprite;

public class TileArchetype implements Tile {
    @Override
    public GameZone gameZone() throws IllegalStateException {
        return null;
    }

    @Override
    public ReadableCoordinate location() throws IllegalStateException {
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
    public Collection<GameMovementEvent> movementEvents() throws IllegalStateException {
        return null;
    }

    @Override
    public Collection<GameAbilityEvent> abilityEvents() throws IllegalStateException {
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
    public void delete() throws IllegalStateException {

    }

    @Override
    public boolean isDeleted() {
        return false;
    }

    @Override
    public GenericParamsSet data() throws IllegalStateException {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return Tile.class.getCanonicalName();
    }
}
package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.ICoordinate;
import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.common.specs.IMap;
import soliloquy.gamestate.specs.*;
import soliloquy.ruleset.gameentities.abilities.specs.IActiveAbility;
import soliloquy.ruleset.gameentities.abilities.specs.IReactiveAbility;
import soliloquy.ruleset.gameentities.specs.IGroundType;
import soliloquy.sprites.specs.ISprite;

public class TileStub implements ITile {
    public ICoordinate _tileLocation;

    public TileStub(ICoordinate tileLocation) {
        _tileLocation = tileLocation;
    }

    @Override
    public IGameZone getGameZone() throws IllegalStateException {
        return null;
    }

    @Override
    public IGenericParamsSet params() throws IllegalStateException {
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
    public ICoordinate getLocation() throws IllegalStateException {
        return _tileLocation;
    }

    @Override
    public void setLocation(ICoordinate iCoordinate) throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public IMap<Integer, ICharacter> characters() throws IllegalStateException {
        return null;
    }

    @Override
    public IMap<Integer, IItem> items() throws IllegalStateException {
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
    public IGroundType getGroundType() throws IllegalStateException {
        return null;
    }

    @Override
    public void setGroundType(IGroundType iGroundType) throws IllegalStateException {

    }

    @Override
    public IMap<Integer, ITileFixture> fixtures() throws IllegalStateException {
        return null;
    }

    @Override
    public IMap<Integer, IMap<Integer, ITileWallSegment>> nTileWallSegments() throws IllegalStateException {
        return null;
    }

    @Override
    public IMap<Integer, IMap<Integer, ITileWallSegment>> nwTileWallSegments() throws IllegalStateException {
        return null;
    }

    @Override
    public IMap<Integer, IMap<Integer, ITileWallSegment>> wTileWallSegments() throws IllegalStateException {
        return null;
    }

    @Override
    public IMap<Integer, ICollection<ISprite>> sprites() throws IllegalStateException {
        return null;
    }

    @Override
    public IMap<String, IActiveAbility> activeAbilities() throws IllegalStateException {
        return null;
    }

    @Override
    public IMap<String, IReactiveAbility> reactiveAbilities() throws IllegalStateException {
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

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
    public static final IGameZone GAME_ZONE = new GameZoneStub();

    private final ITileCharacters CHARACTERS = new TileCharactersStub(this);
    private final ITileFixtures FIXTURES = new TileFixturesStub(this);

    public ICoordinate _tileLocation;
    public IMap<ICharacter, Integer> _characters = new MapStub<>();

    public TileStub() {
    }

    public TileStub(ICoordinate tileLocation) {
        _tileLocation = tileLocation;
    }

    @Override
    public IGenericParamsSet data() throws IllegalStateException {
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
    public IGameZone gameZone() throws IllegalStateException {
        return GAME_ZONE;
    }

    @Override
    public ICoordinate getLocation() throws IllegalStateException {
        return _tileLocation;
    }

    @Override
    public void setLocation(ICoordinate iCoordinate) throws IllegalArgumentException, IllegalStateException {

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
    public ITileCharacters characters() {
        return CHARACTERS;
    }

    @Override
    public ITileItems items() {
        return null;
    }

    @Override
    public ITileFixtures fixtures() throws IllegalStateException {
        return FIXTURES;
    }

    @Override
    public ITileWallSegments nTileWallSegments() throws IllegalStateException {
        return null;
    }

    @Override
    public ITileWallSegments nwTileWallSegments() throws IllegalStateException {
        return null;
    }

    @Override
    public ITileWallSegments wTileWallSegments() throws IllegalStateException {
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
    public String getInterfaceName() {
        return null;
    }
}

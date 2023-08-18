package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.GameZoneFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeGameZone;
import inaugural.soliloquy.gamestate.test.fakes.FakeTile;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.Before;
import org.junit.Test;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Coordinate2d;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.GameZoneFactory;


import java.util.List;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.Assert.*;

public class GameZoneFactoryImplTests {
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final List<Character> ADDED_TO_END_OF_ROUND_MANAGER = listOf();
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final List<Character> REMOVED_FROM_ROUND_MANAGER = listOf();
    private final String ID = randomString();
    private final String TYPE = randomString();
    private final Tile[][] TILES = new Tile[1][2];
    private final VariableCache DATA = new VariableCacheStub();

    private GameZoneFactory factory;

    @Before
    public void setUp() {
        factory = new GameZoneFactoryImpl(ADDED_TO_END_OF_ROUND_MANAGER::add, REMOVED_FROM_ROUND_MANAGER::add);
    }

    @Test
    public void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneFactoryImpl(null, REMOVED_FROM_ROUND_MANAGER::add));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneFactoryImpl(ADDED_TO_END_OF_ROUND_MANAGER::add, null));
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(GameZoneFactory.class.getCanonicalName(),
                factory.getInterfaceName());
    }

    @Test
    public void testMake() {
        TILES[0][0] = new FakeTile(0, 0, new VariableCacheStub());
        TILES[0][1] = new FakeTile(0, 1, new VariableCacheStub());

        var gameZone = factory.make(ID, TYPE, TILES, DATA);

        assertEquals(ID, gameZone.id());
        assertEquals(TYPE, gameZone.type());
        assertEquals(0, gameZone.maxCoordinates().X);
        assertEquals(1, gameZone.maxCoordinates().Y);
        assertSame(TILES[0][0], gameZone.tile(Coordinate2d.of(0, 0)));
        assertSame(TILES[0][1], gameZone.tile(Coordinate2d.of(0, 1)));
        assertSame(DATA, gameZone.data());
    }

    @Test
    public void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> factory.make(null, TYPE, TILES,
                DATA));
        assertThrows(IllegalArgumentException.class, () -> factory.make("", TYPE, TILES,
                DATA));
        assertThrows(IllegalArgumentException.class, () -> factory.make(ID, null, TILES,
                DATA));
        assertThrows(IllegalArgumentException.class, () -> factory.make(ID, "", TILES,
                DATA));
        var tilesWithZeroXIndex = new Tile[0][1];
        assertThrows(IllegalArgumentException.class, () -> factory.make(ID, TYPE,
                tilesWithZeroXIndex, DATA));
        var tilesWithZeroYIndex = new Tile[1][0];
        assertThrows(IllegalArgumentException.class, () -> factory.make(ID, TYPE,
                tilesWithZeroYIndex, DATA));
        var tilesWithNullEntry = new Tile[1][1];
        assertThrows(IllegalArgumentException.class, () -> factory.make(ID, TYPE,
                tilesWithNullEntry, DATA));
        var tilesWithAssignedTile = new Tile[1][1];
        tilesWithAssignedTile[0][0] = new FakeTile(0, 0, new VariableCacheStub());
        tilesWithAssignedTile[0][0].assignGameZoneAfterAddedToGameZone(new FakeGameZone());
        assertThrows(IllegalArgumentException.class, () -> factory.make(ID, TYPE,
                tilesWithAssignedTile, DATA));
        var tilesWithMismatchedXCoordinate2d = new Tile[1][1];
        tilesWithMismatchedXCoordinate2d[0][0] = new FakeTile(1, 0, new VariableCacheStub());
        assertThrows(IllegalArgumentException.class, () -> factory.make(ID, TYPE,
                tilesWithMismatchedXCoordinate2d, DATA));
        var tilesWithMismatchedYCoordinate2d = new Tile[1][1];
        tilesWithMismatchedYCoordinate2d[0][0] = new FakeTile(0, 1, new VariableCacheStub());
        assertThrows(IllegalArgumentException.class, () -> factory.make(ID, TYPE,
                tilesWithMismatchedYCoordinate2d, DATA));
        assertThrows(IllegalArgumentException.class, () -> factory.make(ID, TYPE, TILES,
                null));
    }
}

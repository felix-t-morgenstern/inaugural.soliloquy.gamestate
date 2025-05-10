package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.GameZoneFactoryImpl;
import org.apache.commons.lang3.function.TriConsumer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Coordinate2d;
import soliloquy.specs.common.valueobjects.Coordinate3d;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.shared.GameZoneTerrain;
import soliloquy.specs.gamestate.factories.GameZoneFactory;

import java.util.List;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.random.Random.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GameZoneFactoryImplTests {
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final List<Character> ADDED_TO_END_OF_ROUND_MANAGER = listOf();
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final List<Character> REMOVED_FROM_ROUND_MANAGER = listOf();
    private final String ID = randomString();
    private final Coordinate2d MAX_COORDINATES =
            Coordinate2d.of(randomIntWithInclusiveFloor(1), randomIntWithInclusiveFloor(1));
    private final Coordinate3d TILE_LOC = Coordinate3d.of(
            randomIntInRange(0, MAX_COORDINATES.X),
            randomIntInRange(0, MAX_COORDINATES.Y),
            randomInt());

    @Mock private TriConsumer<GameZoneTerrain, GameZone, Coordinate3d>
            mockAssignLocationAfterPlacement;
    @Mock private VariableCache mockData;
    @Mock private Tile mockTile;

    private GameZoneFactory factory;

    @Before
    public void setUp() {
        factory = new GameZoneFactoryImpl(ADDED_TO_END_OF_ROUND_MANAGER::add,
                REMOVED_FROM_ROUND_MANAGER::add, mockAssignLocationAfterPlacement);
    }

    @Test
    public void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneFactoryImpl(null, REMOVED_FROM_ROUND_MANAGER::add,
                        mockAssignLocationAfterPlacement));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneFactoryImpl(ADDED_TO_END_OF_ROUND_MANAGER::add, null,
                        mockAssignLocationAfterPlacement));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneFactoryImpl(ADDED_TO_END_OF_ROUND_MANAGER::add,
                        REMOVED_FROM_ROUND_MANAGER::add, null));
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(GameZoneFactory.class.getCanonicalName(),
                factory.getInterfaceName());
    }

    @Test
    public void testMake() {
        var gameZone = factory.make(ID, MAX_COORDINATES, mockData);
        gameZone.putTile(mockTile, TILE_LOC);

        assertEquals(ID, gameZone.id());
        assertEquals(MAX_COORDINATES, gameZone.maxCoordinates());
        assertSame(mockData, gameZone.data());
        verify(mockAssignLocationAfterPlacement)
                .accept(same(mockTile), same(gameZone), eq(TILE_LOC));
    }

    @Test
    public void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> factory.make(null, MAX_COORDINATES, mockData));
        assertThrows(IllegalArgumentException.class,
                () -> factory.make("", MAX_COORDINATES, mockData));
        assertThrows(IllegalArgumentException.class, () -> factory.make(ID, null, mockData));
        assertThrows(IllegalArgumentException.class,
                () -> factory.make(ID, Coordinate2d.of(-1, randomIntWithInclusiveFloor(1)),
                        mockData));
        assertThrows(IllegalArgumentException.class,
                () -> factory.make(ID, Coordinate2d.of(randomIntWithInclusiveFloor(1), -1),
                        mockData));
        assertThrows(IllegalArgumentException.class, () -> factory.make(ID, MAX_COORDINATES, null));
    }
}

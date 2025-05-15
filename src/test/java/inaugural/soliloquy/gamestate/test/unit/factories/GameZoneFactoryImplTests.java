package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.GameZoneFactoryImpl;
import org.apache.commons.lang3.function.TriConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.common.valueobjects.Coordinate2d;
import soliloquy.specs.common.valueobjects.Coordinate3d;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.shared.GameZoneTerrain;
import soliloquy.specs.gamestate.factories.GameZoneFactory;

import java.util.List;
import java.util.Map;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.random.Random.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static soliloquy.specs.common.valueobjects.Coordinate2d.coordinate2dOf;
import static soliloquy.specs.common.valueobjects.Coordinate3d.coordinate3dOf;

@ExtendWith(MockitoExtension.class)
public class GameZoneFactoryImplTests {
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final List<Character> ADDED_TO_END_OF_ROUND_MANAGER = listOf();
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final List<Character> REMOVED_FROM_ROUND_MANAGER = listOf();
    private final String ID = randomString();
    private final Coordinate2d MAX_COORDINATES =
            coordinate2dOf(randomIntWithInclusiveFloor(1), randomIntWithInclusiveFloor(1));
    private final Coordinate3d TILE_LOC = coordinate3dOf(
            randomIntInRange(0, MAX_COORDINATES.X),
            randomIntInRange(0, MAX_COORDINATES.Y),
            randomInt());

    @Mock private TriConsumer<GameZoneTerrain, GameZone, Coordinate3d>
            mockAssignLocationAfterPlacement;
    @Mock private Map<String, Object> mockData;
    @Mock private Tile mockTile;

    private GameZoneFactory factory;

    @BeforeEach
    public void setUp() {
        factory = new GameZoneFactoryImpl(ADDED_TO_END_OF_ROUND_MANAGER::add,
                REMOVED_FROM_ROUND_MANAGER::add, mockAssignLocationAfterPlacement);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
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
    public void testMakeWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> factory.make(null, MAX_COORDINATES, mockData));
        assertThrows(IllegalArgumentException.class,
                () -> factory.make("", MAX_COORDINATES, mockData));
        assertThrows(IllegalArgumentException.class, () -> factory.make(ID, null, mockData));
        assertThrows(IllegalArgumentException.class,
                () -> factory.make(ID, coordinate2dOf(-1, randomIntWithInclusiveFloor(1)),
                        mockData));
        assertThrows(IllegalArgumentException.class,
                () -> factory.make(ID, coordinate2dOf(randomIntWithInclusiveFloor(1), -1),
                        mockData));
        assertThrows(IllegalArgumentException.class, () -> factory.make(ID, MAX_COORDINATES, null));
    }
}

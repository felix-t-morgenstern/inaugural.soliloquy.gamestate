package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.TileImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.common.valueobjects.Coordinate3d;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.ruleset.entities.GroundType;

import java.util.Map;
import java.util.function.Function;

import static inaugural.soliloquy.tools.random.Random.randomCoordinate3d;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static soliloquy.specs.common.valueobjects.Coordinate3d.coordinate3dOf;

@ExtendWith(MockitoExtension.class)
public class TileImplTests {
    private final Coordinate3d LOCATION = randomCoordinate3d();

    @Mock private TileEntities<Character> mockCharacters;
    @Mock private TileEntities<Item> mockItems;
    @Mock private TileEntities<TileFixture> mockFixtures;
    @SuppressWarnings("rawtypes") @Mock private Function<Tile, TileEntities> mockEntitiesFactory;
    @Mock private Map<String, Object> mockData;
    @Mock private GameZone mockGameZone;
    @Mock private GroundType mockGroundType;

    private Tile tile;

    @BeforeEach
    public void setUp() {
        lenient().when(mockEntitiesFactory.apply(any()))
                .thenReturn(mockCharacters)
                .thenReturn(mockItems)
                .thenReturn(mockFixtures);

        tile = new TileImpl(mockEntitiesFactory, mockData);

        lenient().when(mockGameZone.tile(any())).thenReturn(mock(Tile.class));
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> new TileImpl(null, mockData));
        assertThrows(IllegalArgumentException.class, () -> new TileImpl(mockEntitiesFactory, null));
    }

    @Test
    public void testSetAndGetGroundType() {
        tile.setGroundType(mockGroundType);

        assertSame(mockGroundType, tile.getGroundType());
    }

    @Test
    public void testCharacters() {
        assertSame(mockCharacters, tile.characters());
    }

    @Test
    public void testItems() {
        assertSame(mockItems, tile.items());
    }

    @Test
    public void testFixtures() {
        assertSame(mockFixtures, tile.fixtures());
    }

    @Test
    public void testMovementEvents() {
        assertNotNull(tile.movementEvents());
    }

    @Test
    public void testAbilityEvents() {
        assertNotNull(tile.abilityEvents());
    }

    @Test
    public void testSprites() {
        assertNotNull(tile.sprites());
    }

    @Test
    public void testData() {
        assertNotNull(tile.data());
    }

    @Test
    public void testMakeGameEventTarget() {
        GameEventTarget gameEventTarget = tile.makeGameEventTarget();

        assertNotNull(gameEventTarget);
        assertNotNull(gameEventTarget.tile());
        assertNull(gameEventTarget.tileFixture());
        assertNull(gameEventTarget.tileWallSegment());
    }

    @Test
    public void testAssignGameZoneAfterAddedToGameZoneAndLocation() {
        when(mockGameZone.tile(any())).thenReturn(tile);

        ((TileImpl) tile).assignGameZoneAfterAddedToGameZone(mockGameZone, LOCATION);

        assertSame(mockGameZone, tile.gameZone());
        assertEquals(LOCATION, tile.location());
    }

    @Test
    public void testAssignGameZoneAfterAddedToGameZoneWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> ((TileImpl) tile).assignGameZoneAfterAddedToGameZone(mockGameZone, null));
    }

    // I'm testing a method not listed on the interface, since it's expected to be passed into
    // the constructor of GameZone as a method reference, while remaining closed to consumers of
    // the interface
    @Test
    public void testThrowsOnDeleteWhenGameZoneIsNotDeleted() {
        when(mockGameZone.isDeleted()).thenReturn(false);
        ((TileImpl) tile).assignGameZoneAfterAddedToGameZone(mockGameZone,
                coordinate3dOf(0, 0, 0));

        assertThrows(IllegalStateException.class, tile::delete);
    }

    @Test
    public void testDeletedInvariant() {
        tile.delete();

        assertThrows(EntityDeletedException.class, () -> tile.gameZone());
        assertThrows(EntityDeletedException.class, () -> tile.location());
        assertThrows(EntityDeletedException.class, () -> tile.getGroundType());
        assertThrows(EntityDeletedException.class, () -> tile.setGroundType(mockGroundType));
        assertThrows(EntityDeletedException.class, () -> tile.characters());
        assertThrows(EntityDeletedException.class, () -> tile.fixtures());
        assertThrows(EntityDeletedException.class, () -> tile.items());
        assertThrows(EntityDeletedException.class, () -> tile.movementEvents());
        assertThrows(EntityDeletedException.class, () -> tile.abilityEvents());
        assertThrows(EntityDeletedException.class, () -> tile.sprites());
        assertThrows(EntityDeletedException.class, () -> tile.data());
    }

    @Test
    public void testGameZoneLocationCorrespondenceInvariant() {
        ((TileImpl) tile).assignGameZoneAfterAddedToGameZone(mockGameZone, coordinate3dOf(0, 0, 0));

        when(mockGameZone.tile(any())).thenReturn(null);

        assertThrows(IllegalStateException.class, () -> tile.gameZone());
        assertThrows(IllegalStateException.class, () -> tile.location());
        assertThrows(IllegalStateException.class, () -> tile.getGroundType());
        assertThrows(IllegalStateException.class, () -> tile.setGroundType(mockGroundType));
        assertThrows(IllegalStateException.class, () -> tile.characters());
        assertThrows(IllegalStateException.class, () -> tile.fixtures());
        assertThrows(IllegalStateException.class, () -> tile.items());
        assertThrows(IllegalStateException.class, () -> tile.movementEvents());
        assertThrows(IllegalStateException.class, () -> tile.abilityEvents());
        assertThrows(IllegalStateException.class, () -> tile.sprites());
        assertThrows(IllegalStateException.class, () -> tile.data());
    }

    @Test
    public void testGameZoneMismatchInvariant() {
        ((TileImpl) tile).assignGameZoneAfterAddedToGameZone(mockGameZone, coordinate3dOf(0, 0, 0));

        assertThrows(IllegalStateException.class, () -> tile.gameZone());
        assertThrows(IllegalStateException.class, () -> tile.location());
        assertThrows(IllegalStateException.class, () -> tile.getGroundType());
        assertThrows(IllegalStateException.class, () -> tile.setGroundType(mockGroundType));
        assertThrows(IllegalStateException.class, () -> tile.characters());
        assertThrows(IllegalStateException.class, () -> tile.fixtures());
        assertThrows(IllegalStateException.class, () -> tile.items());
        assertThrows(IllegalStateException.class, () -> tile.movementEvents());
        assertThrows(IllegalStateException.class, () -> tile.abilityEvents());
        assertThrows(IllegalStateException.class, () -> tile.sprites());
        assertThrows(IllegalStateException.class, () -> tile.data());
    }
}

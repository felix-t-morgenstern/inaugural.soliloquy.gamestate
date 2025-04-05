package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.TileImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeGroundType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Coordinate3d;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.gamestate.factories.TileEntitiesFactory;
import soliloquy.specs.ruleset.entities.GroundType;

import static inaugural.soliloquy.tools.random.Random.randomCoordinate3d;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TileImplTests {
    private final Coordinate3d LOCATION = randomCoordinate3d();

    @Mock private TileEntities<Character> mockCharacters;
    @Mock private TileEntities<Item> mockItems;
    @Mock private TileEntities<TileFixture> mockFixtures;
    @Mock private TileEntitiesFactory mockEntitiesFactory;
    @Mock private VariableCache mockData;
    @Mock private GameZone mockGameZone;
    @Mock private GroundType mockGroundType;

    private Tile tile;

    @Before
    public void setUp() {
        //noinspection unchecked,rawtypes
        when(mockEntitiesFactory.make(any(), any(), any(), any()))
                .thenReturn((TileEntities)mockCharacters)
                .thenReturn(mockItems)
                .thenReturn(mockFixtures);

        tile = new TileImpl(mockEntitiesFactory, mockData);

        when(mockGameZone.tile(any())).thenReturn(mock(Tile.class));
    }

    @Test
    public void testConstructorWithInvalidParams() {
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
        assertEquals(GameEventTarget.class.getCanonicalName(), gameEventTarget.getInterfaceName());
    }

    @Test
    public void testAssignGameZoneAfterAddedToGameZoneAndLocation() {
        when(mockGameZone.tile(any())).thenReturn(tile);

        tile.assignGameZoneAfterAddedToGameZone(mockGameZone, LOCATION);

        assertSame(mockGameZone, tile.gameZone());
        assertEquals(LOCATION, tile.location());
    }

    @Test
    public void testAssignGameZoneAfterAddedToGameZoneWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> tile.assignGameZoneAfterAddedToGameZone(mockGameZone, null));
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(Tile.class.getCanonicalName(), tile.getInterfaceName());
    }

    @Test
    public void testThrowsOnDeleteWhenGameZoneIsNotDeleted() {
        when(mockGameZone.isDeleted()).thenReturn(false);
        tile.assignGameZoneAfterAddedToGameZone(mockGameZone, Coordinate3d.of(0, 0, 0));

        assertThrows(IllegalStateException.class, tile::delete);
    }

    @Test
    public void testDeletedInvariant() {
        tile.delete();

        assertThrows(EntityDeletedException.class, () -> tile.gameZone());
        assertThrows(EntityDeletedException.class, () -> tile.location());
        assertThrows(EntityDeletedException.class, () -> tile.getGroundType());
        assertThrows(EntityDeletedException.class, () -> tile.setGroundType(new FakeGroundType()));
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
        tile.assignGameZoneAfterAddedToGameZone(mockGameZone, Coordinate3d.of(0, 0, 0));

        when(mockGameZone.tile(any())).thenReturn(null);

        assertThrows(IllegalStateException.class, () -> tile.gameZone());
        assertThrows(IllegalStateException.class, () -> tile.location());
        assertThrows(IllegalStateException.class, () -> tile.getGroundType());
        assertThrows(IllegalStateException.class, () -> tile.setGroundType(new FakeGroundType()));
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
        tile.assignGameZoneAfterAddedToGameZone(mockGameZone, Coordinate3d.of(0, 0, 0));

        assertThrows(IllegalStateException.class, () -> tile.gameZone());
        assertThrows(IllegalStateException.class, () -> tile.location());
        assertThrows(IllegalStateException.class, () -> tile.getGroundType());
        assertThrows(IllegalStateException.class, () -> tile.setGroundType(new FakeGroundType()));
        assertThrows(IllegalStateException.class, () -> tile.characters());
        assertThrows(IllegalStateException.class, () -> tile.fixtures());
        assertThrows(IllegalStateException.class, () -> tile.items());
        assertThrows(IllegalStateException.class, () -> tile.movementEvents());
        assertThrows(IllegalStateException.class, () -> tile.abilityEvents());
        assertThrows(IllegalStateException.class, () -> tile.sprites());
        assertThrows(IllegalStateException.class, () -> tile.data());
    }
}

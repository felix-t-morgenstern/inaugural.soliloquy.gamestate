package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.TileFixtureFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.common.valueobjects.Vertex;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtureItems;
import soliloquy.specs.gamestate.factories.TileFixtureFactory;
import soliloquy.specs.ruleset.entities.FixtureType;

import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import static inaugural.soliloquy.tools.collections.Collections.mapOf;
import static inaugural.soliloquy.tools.random.Random.*;
import static inaugural.soliloquy.tools.testing.Assertions.once;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static soliloquy.specs.common.valueobjects.Pair.pairOf;

@ExtendWith(MockitoExtension.class)
public class TileFixtureFactoryImplTests {
    private final UUID UUID = java.util.UUID.randomUUID();
    private final Vertex DEFAULT_TILE_OFFSET = randomVertex();

    @Mock private FixtureType mockFixtureType;
    @Mock private Function<TileFixture, TileFixtureItems> mockTileFixtureItemsFactory;

    private Map<String, Object> data;

    private TileFixtureFactory tileFixtureFactory;

    @BeforeEach
    public void setUp() {
        data = mapOf(pairOf(randomString(), randomInt()));

        lenient().when(mockFixtureType.defaultTileOffset()).thenReturn(DEFAULT_TILE_OFFSET);

        tileFixtureFactory = new TileFixtureFactoryImpl(mockTileFixtureItemsFactory);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> new TileFixtureFactoryImpl(null));
    }

    @Test
    public void testMake() {
        var tileFixture = tileFixtureFactory.make(mockFixtureType, null);

        assertNotNull(tileFixture);
        assertNotNull(tileFixture.uuid());
        assertSame(mockFixtureType, tileFixture.type());
        assertEquals(mapOf(), tileFixture.data());
        assertEquals(DEFAULT_TILE_OFFSET, tileFixture.getTileOffset());
        assertNotNull(tileFixture.movementEvents());
        assertNotNull(tileFixture.abilityEvents());
        verify(mockTileFixtureItemsFactory, once()).apply(tileFixture);
    }

    @Test
    public void testMakeWithData() {
        var tileFixture = tileFixtureFactory.make(mockFixtureType, data);

        assertNotNull(tileFixture);
        assertNotNull(tileFixture.uuid());
        assertSame(mockFixtureType, tileFixture.type());
        assertNotSame(data, tileFixture.data());
        assertEquals(data, tileFixture.data());
        assertEquals(DEFAULT_TILE_OFFSET, tileFixture.getTileOffset());
        assertNotNull(tileFixture.movementEvents());
        assertNotNull(tileFixture.abilityEvents());
        verify(mockTileFixtureItemsFactory, once()).apply(tileFixture);
    }

    @Test
    public void testMakeWithUuid() {
        var tileFixture = tileFixtureFactory.make(mockFixtureType, null, UUID);

        assertNotNull(tileFixture);
        assertEquals(UUID, tileFixture.uuid());
        assertSame(mockFixtureType, tileFixture.type());
        assertEquals(mapOf(), tileFixture.data());
        assertEquals(DEFAULT_TILE_OFFSET, tileFixture.getTileOffset());
        assertNotNull(tileFixture.movementEvents());
        assertNotNull(tileFixture.abilityEvents());
        verify(mockTileFixtureItemsFactory, once()).apply(tileFixture);
    }

    @Test
    public void testMakeWithUuidAndData() {
        var tileFixture = tileFixtureFactory.make(mockFixtureType, data, UUID);

        assertNotNull(tileFixture);
        assertEquals(UUID, tileFixture.uuid());
        assertSame(mockFixtureType, tileFixture.type());
        assertNotSame(data, tileFixture.data());
        assertEquals(data, tileFixture.data());
        assertEquals(DEFAULT_TILE_OFFSET, tileFixture.getTileOffset());
        assertNotNull(tileFixture.movementEvents());
        assertNotNull(tileFixture.abilityEvents());
        verify(mockTileFixtureItemsFactory, once()).apply(tileFixture);
    }

    @Test
    public void testMakeWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> tileFixtureFactory.make(null, null));

        assertThrows(IllegalArgumentException.class,
                () -> tileFixtureFactory.make(null, null, UUID));
        assertThrows(IllegalArgumentException.class,
                () -> tileFixtureFactory.make(mockFixtureType, null, null));
    }
}

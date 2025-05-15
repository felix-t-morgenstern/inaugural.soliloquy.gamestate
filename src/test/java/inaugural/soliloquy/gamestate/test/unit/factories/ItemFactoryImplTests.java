package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.ItemFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.common.valueobjects.Vertex;
import soliloquy.specs.gamestate.factories.ItemFactory;
import soliloquy.specs.ruleset.entities.ItemType;

import java.util.Map;
import java.util.UUID;

import static inaugural.soliloquy.tools.collections.Collections.mapOf;
import static inaugural.soliloquy.tools.random.Random.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static soliloquy.specs.common.valueobjects.Pair.pairOf;

@ExtendWith(MockitoExtension.class)
public class ItemFactoryImplTests {
    private final UUID UUID = java.util.UUID.randomUUID();
    private final Vertex DEFAULT_TILE_OFFSET = randomVertex();
    private final String DATA_KEY = randomString();
    private final int DATA_VAL = randomInt();

    @Mock private ItemType mockItemType;

    private Map<String, Object> data;

    private ItemFactory itemFactory;

    @BeforeEach
    public void setUp() {
        lenient().when(mockItemType.isStackable()).thenReturn(true);
        lenient().when(mockItemType.defaultTileOffset()).thenReturn(DEFAULT_TILE_OFFSET);

        data = mapOf(pairOf(DATA_KEY, DATA_VAL));

        itemFactory = new ItemFactoryImpl();
    }

    @Test
    public void testMake() {
        var item = itemFactory.make(mockItemType, null);
        item.setNumberInStack(10);
        var takenFromStack = item.takeFromStack(5);

        assertNotNull(item);
        assertNotNull(item.uuid());
        assertSame(mockItemType, item.type());
        assertEquals(mapOf(), item.data());
        assertNotEquals(item.uuid(), takenFromStack.uuid());
        assertEquals(DEFAULT_TILE_OFFSET, item.getTileOffset());
    }

    @Test
    public void testMakeWithData() {
        var item = itemFactory.make(mockItemType, data);
        item.setNumberInStack(10);
        var takenFromStack = item.takeFromStack(5);

        assertNotNull(item);
        assertNotNull(item.uuid());
        assertSame(mockItemType, item.type());
        assertSame(data, item.data());
        assertNotSame(item.uuid(), takenFromStack.uuid());
        assertEquals(DEFAULT_TILE_OFFSET, item.getTileOffset());
    }

    @Test
    public void testMakeWithId() {
        var item = itemFactory.make(mockItemType, null, UUID);
        item.setNumberInStack(10);
        var takenFromStack = item.takeFromStack(5);

        assertNotNull(item);
        assertSame(UUID, item.uuid());
        assertSame(mockItemType, item.type());
        assertEquals(mapOf(), item.data());
        assertNotSame(item.uuid(), takenFromStack.uuid());
        assertEquals(DEFAULT_TILE_OFFSET, item.getTileOffset());
    }

    @Test
    public void testMakeWithIdAndData() {
        var item = itemFactory.make(mockItemType, data, UUID);
        item.setNumberInStack(10);
        var takenFromStack = item.takeFromStack(5);

        assertNotNull(item);
        assertSame(UUID, item.uuid());
        assertSame(mockItemType, item.type());
        assertSame(data, item.data());
        assertNotSame(item.uuid(), takenFromStack.uuid());
        assertEquals(DEFAULT_TILE_OFFSET, item.getTileOffset());
    }

    @Test
    public void testMakeWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> itemFactory.make(null, data));

        assertThrows(IllegalArgumentException.class,
                () -> itemFactory.make(null, data, UUID));
        assertThrows(IllegalArgumentException.class,
                () -> itemFactory.make(mockItemType, data, null));
    }
}

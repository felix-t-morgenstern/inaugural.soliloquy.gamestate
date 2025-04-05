package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.entities.WallSegmentImpl;
import inaugural.soliloquy.gamestate.factories.WallSegmentFactoryImpl;
import inaugural.soliloquy.tools.testing.Mock.HandlerAndEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.gamestate.factories.WallSegmentFactory;
import soliloquy.specs.ruleset.entities.WallSegmentType;

import java.util.function.Function;

import static inaugural.soliloquy.tools.collections.Collections.arrayOf;
import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static inaugural.soliloquy.tools.testing.Mock.generateMockEntityAndHandler;
import static inaugural.soliloquy.tools.testing.Mock.generateMockLookupFunction;
import static inaugural.soliloquy.tools.valueobjects.Pair.pairOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class WallSegmentFactoryImplTests {
    private final String DATA_STRING = randomString();
    private final HandlerAndEntity<VariableCache> DATA_HANDLER_AND_DATA =
            generateMockEntityAndHandler(VariableCache.class, DATA_STRING);
    private final TypeHandler<VariableCache> MOCK_DATA_HANDLER = DATA_HANDLER_AND_DATA.handler;
    private final VariableCache MOCK_DATA = DATA_HANDLER_AND_DATA.entity;

    private final String SEGMENT_TYPE_ID = randomString();
    private final String MOVEMENT_EVENT_ID = randomString();
    private final String ABILITY_EVENT_ID = randomString();

    private final WallSegmentFactory.Definition DEFINITION =
            new WallSegmentFactory.Definition(SEGMENT_TYPE_ID, arrayOf(MOVEMENT_EVENT_ID),
                    arrayOf(ABILITY_EVENT_ID), DATA_STRING);

    @Mock private WallSegmentType mockSegmentType;
    @Mock private GameMovementEvent mockGameMovementEvent;
    @Mock private GameAbilityEvent mockGameAbilityEvent;

    private Function<String, WallSegmentType> mockGetSegmentType;
    private Function<String, GameMovementEvent> mockGetGameMovementEvent;
    private Function<String, GameAbilityEvent> mockGetGameAbilityEvent;

    private WallSegmentFactory wallSegmentFactory;

    @Before
    public void setUp() {
        mockGetSegmentType = generateMockLookupFunction(pairOf(SEGMENT_TYPE_ID, mockSegmentType));
        mockGetGameMovementEvent =
                generateMockLookupFunction(pairOf(MOVEMENT_EVENT_ID, mockGameMovementEvent));
        mockGetGameAbilityEvent =
                generateMockLookupFunction(pairOf(ABILITY_EVENT_ID, mockGameAbilityEvent));

        wallSegmentFactory = new WallSegmentFactoryImpl(MOCK_DATA_HANDLER, mockGetSegmentType,
                mockGetGameMovementEvent, mockGetGameAbilityEvent);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new WallSegmentFactoryImpl(null, mockGetSegmentType, mockGetGameMovementEvent,
                        mockGetGameAbilityEvent));
        assertThrows(IllegalArgumentException.class,
                () -> new WallSegmentFactoryImpl(MOCK_DATA_HANDLER, null, mockGetGameMovementEvent,
                        mockGetGameAbilityEvent));
        assertThrows(IllegalArgumentException.class,
                () -> new WallSegmentFactoryImpl(MOCK_DATA_HANDLER, mockGetSegmentType, null,
                        mockGetGameAbilityEvent));
        assertThrows(IllegalArgumentException.class,
                () -> new WallSegmentFactoryImpl(MOCK_DATA_HANDLER, mockGetSegmentType,
                        mockGetGameMovementEvent, null));
    }

    @Test
    public void testMake() {
        var output = wallSegmentFactory.make(DEFINITION);

        assertNotNull(output);
        assertSame(WallSegmentImpl.class, output.getClass());
        assertSame(mockSegmentType, output.getType());
        assertEquals(listOf(mockGameMovementEvent), output.movementEvents());
        assertEquals(listOf(mockGameAbilityEvent), output.abilityEvents());
        assertSame(MOCK_DATA, output.data());

        verify(mockGetSegmentType).apply(SEGMENT_TYPE_ID);
        verify(MOCK_DATA_HANDLER).read(DATA_STRING);
        verify(mockGetGameMovementEvent).apply(MOVEMENT_EVENT_ID);
        verify(mockGetGameAbilityEvent).apply(ABILITY_EVENT_ID);
    }

    @Test
    public void testMakeWithInvalidParams() {
        var invalidId = randomString();

        assertThrows(IllegalArgumentException.class, () -> wallSegmentFactory.make(null));
        assertThrows(IllegalArgumentException.class,
                () -> wallSegmentFactory.make(
                        new WallSegmentFactory.Definition(null, arrayOf(), arrayOf(),
                                DATA_STRING)));
        assertThrows(IllegalArgumentException.class,
                () -> wallSegmentFactory.make(
                        new WallSegmentFactory.Definition("", arrayOf(), arrayOf(),
                                DATA_STRING)));
        assertThrows(IllegalArgumentException.class,
                () -> wallSegmentFactory.make(
                        new WallSegmentFactory.Definition(invalidId, arrayOf(), arrayOf(),
                                DATA_STRING)));
        assertThrows(IllegalArgumentException.class,
                () -> wallSegmentFactory.make(
                        new WallSegmentFactory.Definition(SEGMENT_TYPE_ID, null, arrayOf(),
                                DATA_STRING)));
        assertThrows(IllegalArgumentException.class,
                () -> wallSegmentFactory.make(
                        new WallSegmentFactory.Definition(SEGMENT_TYPE_ID, arrayOf(""),
                                arrayOf(), DATA_STRING)));
        assertThrows(IllegalArgumentException.class,
                () -> wallSegmentFactory.make(
                        new WallSegmentFactory.Definition(SEGMENT_TYPE_ID, arrayOf(invalidId),
                                arrayOf(), DATA_STRING)));
        assertThrows(IllegalArgumentException.class,
                () -> wallSegmentFactory.make(
                        new WallSegmentFactory.Definition(SEGMENT_TYPE_ID, arrayOf(), null,
                                DATA_STRING)));
        assertThrows(IllegalArgumentException.class,
                () -> wallSegmentFactory.make(
                        new WallSegmentFactory.Definition(SEGMENT_TYPE_ID, arrayOf(),
                                arrayOf(""), DATA_STRING)));
        assertThrows(IllegalArgumentException.class,
                () -> wallSegmentFactory.make(
                        new WallSegmentFactory.Definition(SEGMENT_TYPE_ID, arrayOf(),
                                arrayOf(invalidId), DATA_STRING)));
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(WallSegmentFactory.class.getCanonicalName(),
                wallSegmentFactory.getInterfaceName());
    }
}

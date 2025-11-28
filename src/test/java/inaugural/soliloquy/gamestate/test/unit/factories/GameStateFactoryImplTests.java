package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.GameStateImpl;
import inaugural.soliloquy.gamestate.factories.GameStateFactoryImpl;
import inaugural.soliloquy.tools.collections.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.gamestate.entities.GameZoneRepo;
import soliloquy.specs.gamestate.entities.Party;
import soliloquy.specs.gamestate.entities.RoundManager;
import soliloquy.specs.gamestate.entities.timers.ClockBasedTimerManager;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;
import soliloquy.specs.gamestate.factories.CharacterFactory;
import soliloquy.specs.gamestate.factories.GameStateFactory;
import soliloquy.specs.gamestate.factories.ItemFactory;
import soliloquy.specs.gamestate.factories.RoundBasedTimerFactory;

import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.jupiter.api.Assertions.*;
import static soliloquy.specs.common.valueobjects.Pair.pairOf;

@ExtendWith(MockitoExtension.class)
public class GameStateFactoryImplTests {
    @Mock private GameZoneRepo mockGameZoneRepo;
    @Mock private RoundManager mockRoundManager;
    @Mock private RoundBasedTimerManager mockRoundBasedTimerManager;
    @Mock private ClockBasedTimerManager mockClockBasedTimerManager;
    @Mock private ItemFactory mockItemFactory;
    @Mock private CharacterFactory mockCharacterFactory;
    @Mock private RoundBasedTimerFactory mockRoundBasedTimerFactory;
    @Mock private Party mockParty;

    private GameStateFactory gameStateFactory;

    @BeforeEach
    public void setUp() {
        gameStateFactory = new GameStateFactoryImpl(mockGameZoneRepo, mockRoundManager,
                mockRoundBasedTimerManager, mockClockBasedTimerManager, mockItemFactory,
                mockCharacterFactory, mockRoundBasedTimerFactory);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateFactoryImpl(null, mockRoundManager, mockRoundBasedTimerManager,
                        mockClockBasedTimerManager, mockItemFactory, mockCharacterFactory,
                        mockRoundBasedTimerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateFactoryImpl(mockGameZoneRepo, null, mockRoundBasedTimerManager,
                        mockClockBasedTimerManager, mockItemFactory, mockCharacterFactory,
                        mockRoundBasedTimerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateFactoryImpl(mockGameZoneRepo, mockRoundManager, null,
                        mockClockBasedTimerManager, mockItemFactory, mockCharacterFactory,
                        mockRoundBasedTimerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateFactoryImpl(mockGameZoneRepo, mockRoundManager,
                        mockRoundBasedTimerManager, null, mockItemFactory, mockCharacterFactory,
                        mockRoundBasedTimerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateFactoryImpl(mockGameZoneRepo, mockRoundManager,
                        mockRoundBasedTimerManager, mockClockBasedTimerManager, null,
                        mockCharacterFactory, mockRoundBasedTimerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateFactoryImpl(mockGameZoneRepo, mockRoundManager,
                        mockRoundBasedTimerManager, mockClockBasedTimerManager, mockItemFactory,
                        null, mockRoundBasedTimerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateFactoryImpl(mockGameZoneRepo, mockRoundManager,
                        mockRoundBasedTimerManager, mockClockBasedTimerManager, mockItemFactory,
                        mockCharacterFactory, null));
    }

    @Test
    public void testMake() {
        var data = Collections.<String, Object>mapOf(pairOf(randomString(), randomString()));

        var gameState = gameStateFactory.make(mockParty, data);

        assertNotNull(gameState);
        assertInstanceOf(GameStateImpl.class, gameState);
        assertSame(mockParty, gameState.party());
        assertEquals(data, gameState.data());
        // TODO: Consider adding more extensive testing of constructor params
    }
}

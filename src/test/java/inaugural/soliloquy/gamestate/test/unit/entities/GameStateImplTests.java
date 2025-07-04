package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.GameStateImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.gamestate.GameState;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.gamestate.entities.timers.ClockBasedTimerManager;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;
import soliloquy.specs.gamestate.factories.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class GameStateImplTests {
    @Mock private Map<String, GameMovementEvent> mockGameMovementEvents;
    @Mock private Map<String, GameAbilityEvent> mockGameAbilityEvents;
    @Mock private GameZoneRepo mockGameZoneRepo;
    @Mock private RoundManager mockRoundManager;
    @Mock private RoundBasedTimerManager mockRoundBasedTimerManager;
    @Mock private ClockBasedTimerManager mockClockBasedTimerManager;
    @Mock private ItemFactory mockItemFactory;
    @Mock private CharacterFactory mockCharacterFactory;
    @Mock private RoundBasedTimerFactory mockRoundBasedTimerFactory;
    @Mock private Party mockParty;
    @Mock private Map<String, Object> mockData;

    private GameState gameState;

    @BeforeEach
    public void setUp() {
        gameState = new GameStateImpl(mockParty,
                mockData,
                mockGameZoneRepo,
                mockRoundManager,
                mockRoundBasedTimerManager,
                mockClockBasedTimerManager,
                mockItemFactory,
                mockCharacterFactory,
                mockRoundBasedTimerFactory);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(null,
                        mockData,
                        mockGameZoneRepo,

                        mockRoundManager,
                        mockRoundBasedTimerManager,
                        mockClockBasedTimerManager,
                        mockItemFactory,
                        mockCharacterFactory,
                        mockRoundBasedTimerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(mockParty,
                        null,
                        mockGameZoneRepo,
                        mockRoundManager,
                        mockRoundBasedTimerManager,
                        mockClockBasedTimerManager,
                        mockItemFactory,
                        mockCharacterFactory,
                        mockRoundBasedTimerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(mockParty,
                        mockData,
                        null,
                        mockRoundManager,
                        mockRoundBasedTimerManager,
                        mockClockBasedTimerManager,
                        mockItemFactory,
                        mockCharacterFactory,
                        mockRoundBasedTimerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(mockParty,
                        mockData,
                        mockGameZoneRepo,

                        null,
                        mockRoundBasedTimerManager,
                        mockClockBasedTimerManager,
                        mockItemFactory,
                        mockCharacterFactory,
                        mockRoundBasedTimerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(mockParty,
                        mockData,
                        mockGameZoneRepo,

                        mockRoundManager,
                        null,
                        mockClockBasedTimerManager,
                        mockItemFactory,
                        mockCharacterFactory,
                        mockRoundBasedTimerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(mockParty,
                        mockData,
                        mockGameZoneRepo,

                        mockRoundManager,
                        mockRoundBasedTimerManager,
                        null,
                        mockItemFactory,
                        mockCharacterFactory,
                        mockRoundBasedTimerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(mockParty,
                        mockData,
                        mockGameZoneRepo,

                        mockRoundManager,
                        mockRoundBasedTimerManager,
                        mockClockBasedTimerManager,
                        null,
                        mockCharacterFactory,
                        mockRoundBasedTimerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(mockParty,
                        mockData,
                        mockGameZoneRepo,
                        mockRoundManager,
                        mockRoundBasedTimerManager,
                        mockClockBasedTimerManager,
                        mockItemFactory,
                        null,
                        mockRoundBasedTimerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(mockParty,
                        mockData,
                        mockGameZoneRepo,
                        mockRoundManager,
                        mockRoundBasedTimerManager,
                        mockClockBasedTimerManager,
                        mockItemFactory,
                        mockCharacterFactory,
                        null));
    }

    @Test
    public void testParty() {
        assertSame(mockParty, gameState.party());
    }

    @Test
    public void testCharacterAIs() {
        assertNotNull(gameState.characterAIs());
    }

    @Test
    public void testGameZoneRepo() {
        assertSame(mockGameZoneRepo, gameState.gameZoneRepo());
    }

    @Test
    public void testMovementEvents() {
        assertNotNull(gameState.movementEvents());
    }

    @Test
    public void testAbilityEvents() {
        assertNotNull(gameState.abilityEvents());
    }

    @Test
    public void testRoundManager() {
        assertSame(mockRoundManager, gameState.roundManager());
    }

    @Test
    public void testItemFactory() {
        assertNotNull(gameState.itemFactory());
    }

    @Test
    public void testCharacterFactory() {
        assertNotNull(gameState.characterFactory());
    }

    @Test
    public void testRoundBasedTimerManager() {
        assertSame(mockRoundBasedTimerManager, gameState.roundBasedTimerManager());
    }

    @Test
    public void testClockBasedTimerManager() {
        assertSame(mockClockBasedTimerManager, gameState.clockBasedTimerManager());
    }

    @Test
    public void testRoundBasedTimerFactory() {
        assertSame(mockRoundBasedTimerFactory, gameState.roundBasedTimerFactory());
    }
}

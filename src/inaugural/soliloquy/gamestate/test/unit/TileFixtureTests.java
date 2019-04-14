package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TileFixture;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.ICoordinate;
import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.common.specs.IMap;
import soliloquy.gamestate.specs.IGameZone;
import soliloquy.gamestate.specs.IItem;
import soliloquy.gamestate.specs.ITileFixture;
import soliloquy.ruleset.gameentities.abilities.specs.IActiveAbility;
import soliloquy.ruleset.gameentities.abilities.specs.IReactiveAbility;
import soliloquy.ruleset.gameentities.specs.IFixtureType;

import static org.junit.jupiter.api.Assertions.*;

class TileFixtureTests {
    private ITileFixture _tileFixture;

    private final IFixtureType FIXTURE_TYPE = new FixtureTypeStub();
    private final ICollection<IItem> CONTAINED_ITEMS = new CollectionStub<>();
    private final ICoordinate PIXEL_OFFSET = new CoordinateStub();
    private final IMap<String, IActiveAbility> ACTIVE_ABILITIES = new MapStub<>();
    private final IMap<String, IReactiveAbility> REACTIVE_ABILITIES = new MapStub<>();
    private final IGameZone GAME_ZONE = new GameZoneStub();
    private final IGenericParamsSet DATA = new GenericParamsSetStub();

    @BeforeEach
    void setUp() {
        _tileFixture = new TileFixture(FIXTURE_TYPE, CONTAINED_ITEMS, PIXEL_OFFSET,
                ACTIVE_ABILITIES, REACTIVE_ABILITIES, GAME_ZONE, DATA);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals("soliloquy.gamestate.specs.ITileFixture", _tileFixture.getInterfaceName());
    }

    @Test
    void testFixtureType () {
        assertSame(FIXTURE_TYPE, _tileFixture.fixtureType());
    }

    @Test
    void testContainedItems() {
        assertSame(CONTAINED_ITEMS, _tileFixture.containedItems());
    }

    @Test
    void testPixelOffset() {
        assertSame(PIXEL_OFFSET, _tileFixture.pixelOffset());
    }

    @Test
    void testActiveAbilities() {
        assertSame(ACTIVE_ABILITIES, _tileFixture.activeAbilities());
    }

    @Test
    void testReactiveAbilities() {
        assertSame(REACTIVE_ABILITIES, _tileFixture.reactiveAbilities());
    }

    @Test
    void testGameZone() {
        assertSame(GAME_ZONE, _tileFixture.gameZone());
    }

    @Test
    void testData() {
        assertSame(DATA, _tileFixture.data());
    }

    @Test
    void testDelete() {
        // TODO: Implement and test deletion of Items within the Fixture
        _tileFixture.delete();

        assertTrue(_tileFixture.isDeleted());
    }

    @Test
    void testSetAndGetName() {
        _tileFixture.setName("Name");

        assertEquals("Name", _tileFixture.getName());
    }
}

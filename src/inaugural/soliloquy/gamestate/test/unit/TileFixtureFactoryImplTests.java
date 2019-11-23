package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TileFixtureFactoryImpl;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.factories.EntityUuidFactory;
import soliloquy.specs.common.factories.GenericParamsSetFactory;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.factories.TileFixtureFactory;
import soliloquy.specs.gamestate.factories.TileFixtureItemsFactory;
import soliloquy.specs.ruleset.entities.FixtureType;

import static org.junit.jupiter.api.Assertions.*;

class TileFixtureFactoryImplTests {
    private final EntityUuidFactory ENTITY_UUID_FACTORY = new EntityUuidFactoryStub();
    private final CoordinateFactory COORDINATE_FACTORY = new CoordinateFactoryStub();
    private final CollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    private final TileFixtureItemsFactory TILE_FIXTURE_ITEMS_FACTORY =
            new TileFixtureItemsFactoryStub();
    private final GenericParamsSetFactory GENERIC_PARAMS_SET_FACTORY =
            new GenericParamsSetFactoryStub();
    private final FixtureType FIXTURE_TYPE = new FixtureTypeStub();
    private final GenericParamsSet DATA = new GenericParamsSetStub();
    private final EntityUuid ID = new EntityUuidStub();

    private TileFixtureFactory _tileFixtureFactory;

    @BeforeEach
    void setUp() {
        _tileFixtureFactory = new TileFixtureFactoryImpl(ENTITY_UUID_FACTORY, COORDINATE_FACTORY,
                COLLECTION_FACTORY, TILE_FIXTURE_ITEMS_FACTORY, GENERIC_PARAMS_SET_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new TileFixtureFactoryImpl(null, COORDINATE_FACTORY,
                        COLLECTION_FACTORY, TILE_FIXTURE_ITEMS_FACTORY,
                        GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new TileFixtureFactoryImpl(ENTITY_UUID_FACTORY, null,
                        COLLECTION_FACTORY, TILE_FIXTURE_ITEMS_FACTORY,
                        GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new TileFixtureFactoryImpl(ENTITY_UUID_FACTORY, COORDINATE_FACTORY,
                        null, TILE_FIXTURE_ITEMS_FACTORY,
                        GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new TileFixtureFactoryImpl(ENTITY_UUID_FACTORY, COORDINATE_FACTORY,
                        COLLECTION_FACTORY, null,
                        GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new TileFixtureFactoryImpl(ENTITY_UUID_FACTORY, COORDINATE_FACTORY,
                        COLLECTION_FACTORY, TILE_FIXTURE_ITEMS_FACTORY,
                        null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TileFixtureFactory.class.getCanonicalName(),
                _tileFixtureFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        TileFixture tileFixture = _tileFixtureFactory.make(FIXTURE_TYPE, null);

        assertNotNull(tileFixture);
        assertSame(EntityUuidFactoryStub.RANDOM_ENTITY_UUID, tileFixture.id());
        assertSame(FIXTURE_TYPE, tileFixture.type());
        assertSame(GenericParamsSetFactoryStub.GENERIC_PARAMS_SET, tileFixture.data());
        assertNotNull(tileFixture.pixelOffset());
        assertNotNull(tileFixture.movementEvents());
        assertNotNull(tileFixture.abilityEvents());
        assertSame(tileFixture, ((TileFixtureItemsStub)tileFixture.items()).TILE_FIXTURE);
    }

    @Test
    void testMakeWithData() {
        TileFixture tileFixture = _tileFixtureFactory.make(FIXTURE_TYPE, DATA);

        assertNotNull(tileFixture);
        assertSame(EntityUuidFactoryStub.RANDOM_ENTITY_UUID, tileFixture.id());
        assertSame(FIXTURE_TYPE, tileFixture.type());
        assertSame(DATA, tileFixture.data());
        assertNotNull(tileFixture.pixelOffset());
        assertNotNull(tileFixture.movementEvents());
        assertNotNull(tileFixture.abilityEvents());
        assertSame(tileFixture, ((TileFixtureItemsStub)tileFixture.items()).TILE_FIXTURE);
    }

    @Test
    void testMakeWithId() {
        TileFixture tileFixture = _tileFixtureFactory.make(FIXTURE_TYPE, null, ID);

        assertNotNull(tileFixture);
        assertSame(ID, tileFixture.id());
        assertSame(FIXTURE_TYPE, tileFixture.type());
        assertSame(GenericParamsSetFactoryStub.GENERIC_PARAMS_SET, tileFixture.data());
        assertNotNull(tileFixture.pixelOffset());
        assertNotNull(tileFixture.movementEvents());
        assertNotNull(tileFixture.abilityEvents());
        assertSame(tileFixture, ((TileFixtureItemsStub)tileFixture.items()).TILE_FIXTURE);
    }

    @Test
    void testMakeWithIdAndData() {
        TileFixture tileFixture = _tileFixtureFactory.make(FIXTURE_TYPE, DATA, ID);

        assertNotNull(tileFixture);
        assertSame(ID, tileFixture.id());
        assertSame(FIXTURE_TYPE, tileFixture.type());
        assertSame(DATA, tileFixture.data());
        assertNotNull(tileFixture.pixelOffset());
        assertNotNull(tileFixture.movementEvents());
        assertNotNull(tileFixture.abilityEvents());
        assertSame(tileFixture, ((TileFixtureItemsStub)tileFixture.items()).TILE_FIXTURE);
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _tileFixtureFactory.make(null, null));

        assertThrows(IllegalArgumentException.class,
                () -> _tileFixtureFactory.make(null, null, ID));
        assertThrows(IllegalArgumentException.class,
                () -> _tileFixtureFactory.make(FIXTURE_TYPE, null, null));
    }
}
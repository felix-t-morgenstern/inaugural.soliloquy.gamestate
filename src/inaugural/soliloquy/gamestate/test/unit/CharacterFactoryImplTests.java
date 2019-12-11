package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterFactoryImpl;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.EntityUuidFactory;
import soliloquy.specs.common.factories.GenericParamsSetFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.factories.*;
import soliloquy.specs.ruleset.entities.CharacterType;

import static org.junit.jupiter.api.Assertions.*;

class CharacterFactoryImplTests {
    private final EntityUuidFactory ENTITY_UUID_FACTORY = new EntityUuidFactoryStub();
    private final CollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    private final MapFactory MAP_FACTORY = new MapFactoryStub();
    private final CharacterEventsFactory CHARACTER_EVENTS_FACTORY =
            new CharacterEventsFactoryStub();
    private final CharacterEquipmentSlotsFactory CHARACTER_EQUIPMENT_SLOT_FACTORY =
            new CharacterEquipmentSlotsFactoryStub();
    private final CharacterInventoryFactory CHARACTER_INVENTORY_FACTORY =
            new CharacterInventoryFactoryStub();
    private final CharacterDepletableStatisticsFactory DEPLETABLE_STATS_FACTORY =
            new CharacterDepletableStatisticsFactoryStub();
    private final CharacterEntitiesOfTypeFactory ENTITIES_FACTORY =
            new CharacterEntitiesOfTypeFactoryStub();
    private final CharacterStatusEffectsFactory CHARACTER_STATUS_EFFECTS_FACTORY =
            new CharacterStatusEffectsFactoryStub();
    private final GenericParamsSetFactory GENERIC_PARAMS_SET_FACTORY =
            new GenericParamsSetFactoryStub();
    private final GenericParamsSet DATA = new GenericParamsSetStub();
    private final CharacterType CHARACTER_TYPE = new CharacterTypeStub();
    private final EntityUuid ENTITY_UUID = new EntityUuidStub();

    private CharacterFactory _characterFactory;

    @BeforeEach
    void setUp() {
        _characterFactory = new CharacterFactoryImpl(ENTITY_UUID_FACTORY,
                COLLECTION_FACTORY,
                MAP_FACTORY,
                CHARACTER_EVENTS_FACTORY,
                CHARACTER_EQUIPMENT_SLOT_FACTORY,
                CHARACTER_INVENTORY_FACTORY,
                DEPLETABLE_STATS_FACTORY,
                ENTITIES_FACTORY,
                CHARACTER_STATUS_EFFECTS_FACTORY,
                GENERIC_PARAMS_SET_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(null,
                        COLLECTION_FACTORY,
                        MAP_FACTORY,
                        CHARACTER_EVENTS_FACTORY,
                        CHARACTER_EQUIPMENT_SLOT_FACTORY,
                        CHARACTER_INVENTORY_FACTORY,
                        DEPLETABLE_STATS_FACTORY,
                        ENTITIES_FACTORY,
                        CHARACTER_STATUS_EFFECTS_FACTORY,
                        GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(ENTITY_UUID_FACTORY,
                        null,
                        MAP_FACTORY,
                        CHARACTER_EVENTS_FACTORY,
                        CHARACTER_EQUIPMENT_SLOT_FACTORY,
                        CHARACTER_INVENTORY_FACTORY,
                        DEPLETABLE_STATS_FACTORY,
                        ENTITIES_FACTORY,
                        CHARACTER_STATUS_EFFECTS_FACTORY,
                        GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(ENTITY_UUID_FACTORY,
                        COLLECTION_FACTORY,
                        null,
                        CHARACTER_EVENTS_FACTORY,
                        CHARACTER_EQUIPMENT_SLOT_FACTORY,
                        CHARACTER_INVENTORY_FACTORY,
                        DEPLETABLE_STATS_FACTORY,
                        ENTITIES_FACTORY,
                        CHARACTER_STATUS_EFFECTS_FACTORY,
                        GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(ENTITY_UUID_FACTORY,
                        COLLECTION_FACTORY,
                        MAP_FACTORY,
                        null,
                        CHARACTER_EQUIPMENT_SLOT_FACTORY,
                        CHARACTER_INVENTORY_FACTORY,
                        DEPLETABLE_STATS_FACTORY,
                        ENTITIES_FACTORY,
                        CHARACTER_STATUS_EFFECTS_FACTORY,
                        GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(ENTITY_UUID_FACTORY,
                        COLLECTION_FACTORY,
                        MAP_FACTORY,
                        CHARACTER_EVENTS_FACTORY,
                        null,
                        CHARACTER_INVENTORY_FACTORY,
                        DEPLETABLE_STATS_FACTORY,
                        ENTITIES_FACTORY,
                        CHARACTER_STATUS_EFFECTS_FACTORY,
                        GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(ENTITY_UUID_FACTORY,
                        COLLECTION_FACTORY,
                        MAP_FACTORY,
                        CHARACTER_EVENTS_FACTORY,
                        CHARACTER_EQUIPMENT_SLOT_FACTORY,
                        null,
                        DEPLETABLE_STATS_FACTORY,
                        ENTITIES_FACTORY,
                        CHARACTER_STATUS_EFFECTS_FACTORY,
                        GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(ENTITY_UUID_FACTORY,
                        COLLECTION_FACTORY,
                        MAP_FACTORY,
                        CHARACTER_EVENTS_FACTORY,
                        CHARACTER_EQUIPMENT_SLOT_FACTORY,
                        CHARACTER_INVENTORY_FACTORY,
                        null,
                        ENTITIES_FACTORY,
                        CHARACTER_STATUS_EFFECTS_FACTORY,
                        GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(ENTITY_UUID_FACTORY,
                        COLLECTION_FACTORY,
                        MAP_FACTORY,
                        CHARACTER_EVENTS_FACTORY,
                        CHARACTER_EQUIPMENT_SLOT_FACTORY,
                        CHARACTER_INVENTORY_FACTORY,
                        DEPLETABLE_STATS_FACTORY,
                        null,
                        CHARACTER_STATUS_EFFECTS_FACTORY,
                        GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(ENTITY_UUID_FACTORY,
                        COLLECTION_FACTORY,
                        MAP_FACTORY,
                        CHARACTER_EVENTS_FACTORY,
                        CHARACTER_EQUIPMENT_SLOT_FACTORY,
                        CHARACTER_INVENTORY_FACTORY,
                        DEPLETABLE_STATS_FACTORY,
                        ENTITIES_FACTORY,
                        null,
                        GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(ENTITY_UUID_FACTORY,
                        COLLECTION_FACTORY,
                        MAP_FACTORY,
                        CHARACTER_EVENTS_FACTORY,
                        CHARACTER_EQUIPMENT_SLOT_FACTORY,
                        CHARACTER_INVENTORY_FACTORY,
                        DEPLETABLE_STATS_FACTORY,
                        ENTITIES_FACTORY,
                        CHARACTER_STATUS_EFFECTS_FACTORY,
                        null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(CharacterFactory.class.getCanonicalName(),
                _characterFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        Character character = _characterFactory.make(CHARACTER_TYPE);

        assertNotNull(character);
        assertSame(EntityUuidFactoryStub.RANDOM_ENTITY_UUID, character.id());
    }

    @Test
    void testMakeWithEntityUuid() {
        Character character = _characterFactory.make(CHARACTER_TYPE, ENTITY_UUID, DATA);

        assertNotNull(character);
        assertSame(ENTITY_UUID, character.id());
        assertSame(DATA, character.data());
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _characterFactory.make(null));
        assertThrows(IllegalArgumentException.class,
                () -> _characterFactory.make(null, ENTITY_UUID, DATA));
        assertThrows(IllegalArgumentException.class,
                () -> _characterFactory.make(CHARACTER_TYPE, null, DATA));
        assertThrows(IllegalArgumentException.class,
                () -> _characterFactory.make(CHARACTER_TYPE, ENTITY_UUID, null));
    }
}

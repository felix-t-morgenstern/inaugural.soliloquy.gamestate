package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.CharacterFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.*;
import inaugural.soliloquy.gamestate.test.fakes.FakeCharacterEquipmentSlotsFactory;
import inaugural.soliloquy.gamestate.test.stubs.EntityUuidFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.EntityUuidFactory;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.factories.*;
import soliloquy.specs.ruleset.entities.CharacterType;

import static org.junit.jupiter.api.Assertions.*;

class CharacterFactoryImplTests {
    private final EntityUuidFactory ENTITY_UUID_FACTORY = new EntityUuidFactoryStub();
    private final ListFactory LIST_FACTORY = new FakeListFactory();
    private final MapFactory MAP_FACTORY = new FakeMapFactory();
    private final CharacterEventsFactory CHARACTER_EVENTS_FACTORY =
            new FakeCharacterEventsFactory();
    private final CharacterEquipmentSlotsFactory CHARACTER_EQUIPMENT_SLOT_FACTORY =
            new FakeCharacterEquipmentSlotsFactory();
    private final CharacterInventoryFactory CHARACTER_INVENTORY_FACTORY =
            new FakeCharacterInventoryFactory();
    private final CharacterVariableStatisticsFactory VARIABLE_STATS_FACTORY =
            new FakeCharacterVariableStatisticsFactory();
    private final CharacterEntitiesOfTypeFactory ENTITIES_FACTORY =
            new FakeCharacterEntitiesOfTypeFactory();
    private final CharacterStatusEffectsFactory CHARACTER_STATUS_EFFECTS_FACTORY =
            new FakeCharacterStatusEffectsFactory();
    private final VariableCacheFactory DATA_FACTORY = new FakeVariableCacheFactory();
    private final VariableCache DATA = new VariableCacheStub();
    private final CharacterType CHARACTER_TYPE = new FakeCharacterType();
    private final EntityUuid ENTITY_UUID = new FakeEntityUuid();

    private CharacterFactory _characterFactory;

    @BeforeEach
    void setUp() {
        _characterFactory = new CharacterFactoryImpl(ENTITY_UUID_FACTORY,
                LIST_FACTORY,
                MAP_FACTORY,
                CHARACTER_EVENTS_FACTORY,
                CHARACTER_EQUIPMENT_SLOT_FACTORY,
                CHARACTER_INVENTORY_FACTORY,
                VARIABLE_STATS_FACTORY,
                ENTITIES_FACTORY,
                CHARACTER_STATUS_EFFECTS_FACTORY,
                DATA_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(null,
                        LIST_FACTORY,
                        MAP_FACTORY,
                        CHARACTER_EVENTS_FACTORY,
                        CHARACTER_EQUIPMENT_SLOT_FACTORY,
                        CHARACTER_INVENTORY_FACTORY,
                        VARIABLE_STATS_FACTORY,
                        ENTITIES_FACTORY,
                        CHARACTER_STATUS_EFFECTS_FACTORY,
                        DATA_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(ENTITY_UUID_FACTORY,
                        null,
                        MAP_FACTORY,
                        CHARACTER_EVENTS_FACTORY,
                        CHARACTER_EQUIPMENT_SLOT_FACTORY,
                        CHARACTER_INVENTORY_FACTORY,
                        VARIABLE_STATS_FACTORY,
                        ENTITIES_FACTORY,
                        CHARACTER_STATUS_EFFECTS_FACTORY,
                        DATA_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(ENTITY_UUID_FACTORY,
                        LIST_FACTORY,
                        null,
                        CHARACTER_EVENTS_FACTORY,
                        CHARACTER_EQUIPMENT_SLOT_FACTORY,
                        CHARACTER_INVENTORY_FACTORY,
                        VARIABLE_STATS_FACTORY,
                        ENTITIES_FACTORY,
                        CHARACTER_STATUS_EFFECTS_FACTORY,
                        DATA_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(ENTITY_UUID_FACTORY,
                        LIST_FACTORY,
                        MAP_FACTORY,
                        null,
                        CHARACTER_EQUIPMENT_SLOT_FACTORY,
                        CHARACTER_INVENTORY_FACTORY,
                        VARIABLE_STATS_FACTORY,
                        ENTITIES_FACTORY,
                        CHARACTER_STATUS_EFFECTS_FACTORY,
                        DATA_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(ENTITY_UUID_FACTORY,
                        LIST_FACTORY,
                        MAP_FACTORY,
                        CHARACTER_EVENTS_FACTORY,
                        null,
                        CHARACTER_INVENTORY_FACTORY,
                        VARIABLE_STATS_FACTORY,
                        ENTITIES_FACTORY,
                        CHARACTER_STATUS_EFFECTS_FACTORY,
                        DATA_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(ENTITY_UUID_FACTORY,
                        LIST_FACTORY,
                        MAP_FACTORY,
                        CHARACTER_EVENTS_FACTORY,
                        CHARACTER_EQUIPMENT_SLOT_FACTORY,
                        null,
                        VARIABLE_STATS_FACTORY,
                        ENTITIES_FACTORY,
                        CHARACTER_STATUS_EFFECTS_FACTORY,
                        DATA_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(ENTITY_UUID_FACTORY,
                        LIST_FACTORY,
                        MAP_FACTORY,
                        CHARACTER_EVENTS_FACTORY,
                        CHARACTER_EQUIPMENT_SLOT_FACTORY,
                        CHARACTER_INVENTORY_FACTORY,
                        null,
                        ENTITIES_FACTORY,
                        CHARACTER_STATUS_EFFECTS_FACTORY,
                        DATA_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(ENTITY_UUID_FACTORY,
                        LIST_FACTORY,
                        MAP_FACTORY,
                        CHARACTER_EVENTS_FACTORY,
                        CHARACTER_EQUIPMENT_SLOT_FACTORY,
                        CHARACTER_INVENTORY_FACTORY,
                        VARIABLE_STATS_FACTORY,
                        null,
                        CHARACTER_STATUS_EFFECTS_FACTORY,
                        DATA_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(ENTITY_UUID_FACTORY,
                        LIST_FACTORY,
                        MAP_FACTORY,
                        CHARACTER_EVENTS_FACTORY,
                        CHARACTER_EQUIPMENT_SLOT_FACTORY,
                        CHARACTER_INVENTORY_FACTORY,
                        VARIABLE_STATS_FACTORY,
                        ENTITIES_FACTORY,
                        null,
                        DATA_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(ENTITY_UUID_FACTORY,
                        LIST_FACTORY,
                        MAP_FACTORY,
                        CHARACTER_EVENTS_FACTORY,
                        CHARACTER_EQUIPMENT_SLOT_FACTORY,
                        CHARACTER_INVENTORY_FACTORY,
                        VARIABLE_STATS_FACTORY,
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
        assertSame(EntityUuidFactoryStub.RANDOM_ENTITY_UUID, character.uuid());
    }

    @Test
    void testMakeWithEntityUuid() {
        Character character = _characterFactory.make(CHARACTER_TYPE, ENTITY_UUID, DATA);

        assertNotNull(character);
        assertSame(ENTITY_UUID, character.uuid());
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

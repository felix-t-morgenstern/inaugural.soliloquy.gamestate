package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.CharacterFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.*;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.factories.*;
import soliloquy.specs.ruleset.entities.CharacterType;

import java.util.UUID;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class CharacterFactoryImplTests {
    private final UUID UUID = new UUID(123L, 456L);
    private final Supplier<UUID> UUID_FACTORY = () -> UUID;
    private final CharacterEventsFactory CHARACTER_EVENTS_FACTORY =
            new FakeCharacterEventsFactory();
    private final CharacterEquipmentSlotsFactory CHARACTER_EQUIPMENT_SLOT_FACTORY =
            new FakeCharacterEquipmentSlotsFactory();
    private final CharacterInventoryFactory CHARACTER_INVENTORY_FACTORY =
            new FakeCharacterInventoryFactory();
    private final CharacterVariableStatisticsFactory VARIABLE_STATS_FACTORY =
            new FakeCharacterVariableStatisticsFactory();
    private final EntityMembersOfTypeFactory ENTITIES_FACTORY =
            new FakeEntityMembersOfTypeFactory();
    private final CharacterStatusEffectsFactory CHARACTER_STATUS_EFFECTS_FACTORY =
            new FakeCharacterStatusEffectsFactory();
    private final VariableCacheFactory DATA_FACTORY = new FakeVariableCacheFactory();
    private final VariableCache DATA = new VariableCacheStub();
    private final CharacterType CHARACTER_TYPE = new FakeCharacterType();

    private CharacterFactory _characterFactory;

    @BeforeEach
    void setUp() {
        _characterFactory = new CharacterFactoryImpl(UUID_FACTORY,
                CHARACTER_EVENTS_FACTORY,
                CHARACTER_EQUIPMENT_SLOT_FACTORY,
                CHARACTER_INVENTORY_FACTORY,
                VARIABLE_STATS_FACTORY,
                ENTITIES_FACTORY,
                CHARACTER_STATUS_EFFECTS_FACTORY,
                DATA_FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(null,
                        CHARACTER_EVENTS_FACTORY,
                        CHARACTER_EQUIPMENT_SLOT_FACTORY,
                        CHARACTER_INVENTORY_FACTORY,
                        VARIABLE_STATS_FACTORY,
                        ENTITIES_FACTORY,
                        CHARACTER_STATUS_EFFECTS_FACTORY,
                        DATA_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(UUID_FACTORY,
                        null,
                        CHARACTER_EQUIPMENT_SLOT_FACTORY,
                        CHARACTER_INVENTORY_FACTORY,
                        VARIABLE_STATS_FACTORY,
                        ENTITIES_FACTORY,
                        CHARACTER_STATUS_EFFECTS_FACTORY,
                        DATA_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(UUID_FACTORY,
                        CHARACTER_EVENTS_FACTORY,
                        null,
                        CHARACTER_INVENTORY_FACTORY,
                        VARIABLE_STATS_FACTORY,
                        ENTITIES_FACTORY,
                        CHARACTER_STATUS_EFFECTS_FACTORY,
                        DATA_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(UUID_FACTORY,
                        CHARACTER_EVENTS_FACTORY,
                        CHARACTER_EQUIPMENT_SLOT_FACTORY,
                        null,
                        VARIABLE_STATS_FACTORY,
                        ENTITIES_FACTORY,
                        CHARACTER_STATUS_EFFECTS_FACTORY,
                        DATA_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(UUID_FACTORY,
                        CHARACTER_EVENTS_FACTORY,
                        CHARACTER_EQUIPMENT_SLOT_FACTORY,
                        CHARACTER_INVENTORY_FACTORY,
                        null,
                        ENTITIES_FACTORY,
                        CHARACTER_STATUS_EFFECTS_FACTORY,
                        DATA_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(UUID_FACTORY,
                        CHARACTER_EVENTS_FACTORY,
                        CHARACTER_EQUIPMENT_SLOT_FACTORY,
                        CHARACTER_INVENTORY_FACTORY,
                        VARIABLE_STATS_FACTORY,
                        null,
                        CHARACTER_STATUS_EFFECTS_FACTORY,
                        DATA_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(UUID_FACTORY,
                        CHARACTER_EVENTS_FACTORY,
                        CHARACTER_EQUIPMENT_SLOT_FACTORY,
                        CHARACTER_INVENTORY_FACTORY,
                        VARIABLE_STATS_FACTORY,
                        ENTITIES_FACTORY,
                        null,
                        DATA_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(UUID_FACTORY,
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
        assertSame(UUID, character.uuid());
    }

    @Test
    void testMakeWithEntityUuid() {
        Character character = _characterFactory.make(CHARACTER_TYPE, UUID, DATA);

        assertNotNull(character);
        assertSame(UUID, character.uuid());
        assertSame(DATA, character.data());
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _characterFactory.make(null));
        assertThrows(IllegalArgumentException.class,
                () -> _characterFactory.make(null, UUID, DATA));
        assertThrows(IllegalArgumentException.class,
                () -> _characterFactory.make(CHARACTER_TYPE, null, DATA));
        assertThrows(IllegalArgumentException.class,
                () -> _characterFactory.make(CHARACTER_TYPE, UUID, null));
    }
}
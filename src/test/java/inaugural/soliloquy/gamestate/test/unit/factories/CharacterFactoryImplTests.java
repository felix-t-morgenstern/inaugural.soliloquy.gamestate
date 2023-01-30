package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.CharacterFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.factories.*;
import soliloquy.specs.ruleset.entities.character.CharacterType;

import java.util.UUID;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CharacterFactoryImplTests {
    private final UUID UUID = java.util.UUID.randomUUID();
    private final Supplier<UUID> UUID_FACTORY = () -> UUID;

    @Mock private CharacterEventsFactory mockEventsFactory;
    @Mock private CharacterEquipmentSlotsFactory mockEquipmentSlotsFactory;
    @Mock private CharacterInventoryFactory mockInventoryFactory;
    @Mock private CharacterStatusEffectsFactory mockStatusEffectsFactory;
    @Mock private VariableCacheFactory mockDataFactory;
    @Mock private VariableCache mockData;
    @Mock private CharacterType mockCharacterType;

    private CharacterFactory characterFactory;

    @BeforeEach
    void setUp() {
        mockEventsFactory = mock(CharacterEventsFactory.class);

        mockEquipmentSlotsFactory = mock(CharacterEquipmentSlotsFactory.class);

        mockInventoryFactory = mock(CharacterInventoryFactory.class);

        mockStatusEffectsFactory = mock(CharacterStatusEffectsFactory.class);

        mockData = mock(VariableCache.class);

        mockDataFactory = mock(VariableCacheFactory.class);
        when(mockDataFactory.make()).thenReturn(mockData);

        mockCharacterType = mock(CharacterType.class);

        characterFactory =
                new CharacterFactoryImpl(UUID_FACTORY, mockEventsFactory, mockEquipmentSlotsFactory,
                        mockInventoryFactory, mockStatusEffectsFactory, mockDataFactory);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(null, mockEventsFactory, mockEquipmentSlotsFactory,
                        mockInventoryFactory, mockStatusEffectsFactory, mockDataFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(UUID_FACTORY, null, mockEquipmentSlotsFactory,
                        mockInventoryFactory, mockStatusEffectsFactory, mockDataFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(UUID_FACTORY, mockEventsFactory, null,
                        mockInventoryFactory, mockStatusEffectsFactory, mockDataFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(UUID_FACTORY, mockEventsFactory,
                        mockEquipmentSlotsFactory, null, mockStatusEffectsFactory,
                        mockDataFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(UUID_FACTORY, mockEventsFactory,
                        mockEquipmentSlotsFactory, mockInventoryFactory, null, mockDataFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(UUID_FACTORY, mockEventsFactory,
                        mockEquipmentSlotsFactory, mockInventoryFactory, mockStatusEffectsFactory,
                        null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(CharacterFactory.class.getCanonicalName(),
                characterFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        var character = characterFactory.make(mockCharacterType);

        assertNotNull(character);
        assertSame(UUID, character.uuid());
    }

    @Test
    void testMakeWithEntityUuid() {
        var character = characterFactory.make(mockCharacterType, UUID, mockData);

        assertNotNull(character);
        assertSame(UUID, character.uuid());
        assertSame(mockData, character.data());
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> characterFactory.make(null));
        assertThrows(IllegalArgumentException.class,
                () -> characterFactory.make(null, UUID, mockData));
        assertThrows(IllegalArgumentException.class,
                () -> characterFactory.make(mockCharacterType, null, mockData));
        assertThrows(IllegalArgumentException.class,
                () -> characterFactory.make(mockCharacterType, UUID, null));
    }
}

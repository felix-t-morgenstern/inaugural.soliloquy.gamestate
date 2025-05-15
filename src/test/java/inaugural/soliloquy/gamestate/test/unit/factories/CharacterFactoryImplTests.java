package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.CharacterFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.factories.CharacterFactory;
import soliloquy.specs.ruleset.entities.character.CharacterType;

import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CharacterFactoryImplTests {
    private final UUID UUID = java.util.UUID.randomUUID();

    @Mock private Function<Character, CharacterEvents> mockEventsFactory;
    @Mock private Function<Character, CharacterEquipmentSlots> mockEquipmentSlotsFactory;
    @Mock private Function<Character, CharacterInventory> mockInventoryFactory;
    @Mock private Function<Character, CharacterStatusEffects> mockStatusEffectsFactory;
    @Mock private Map<String, Object> mockData;
    @Mock private CharacterType mockCharacterType;

    private CharacterFactory characterFactory;

    @BeforeEach
    public void setUp() {
        characterFactory =
                new CharacterFactoryImpl(mockEventsFactory, mockEquipmentSlotsFactory,
                        mockInventoryFactory, mockStatusEffectsFactory);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(null, mockEquipmentSlotsFactory,
                        mockInventoryFactory, mockStatusEffectsFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(mockEventsFactory, null, mockInventoryFactory,
                        mockStatusEffectsFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(mockEventsFactory, mockEquipmentSlotsFactory, null,
                        mockStatusEffectsFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactoryImpl(mockEventsFactory, mockEquipmentSlotsFactory,
                        mockInventoryFactory, null));
    }

    @Test
    public void testMake() {
        var character = characterFactory.make(mockCharacterType);

        assertNotNull(character);
        assertNotNull(character.uuid());
    }

    @Test
    public void testMakeWithEntityUuid() {
        var character = characterFactory.make(mockCharacterType, UUID, mockData);

        assertNotNull(character);
        assertSame(UUID, character.uuid());
        assertSame(mockData, character.data());
    }

    @Test
    public void testMakeWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> characterFactory.make(null));
        assertThrows(IllegalArgumentException.class,
                () -> characterFactory.make(null, UUID, mockData));
        assertThrows(IllegalArgumentException.class,
                () -> characterFactory.make(mockCharacterType, null, mockData));
        assertThrows(IllegalArgumentException.class,
                () -> characterFactory.make(mockCharacterType, UUID, null));
    }
}

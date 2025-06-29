package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.CharacterImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.io.graphics.assets.ImageAssetSet;
import soliloquy.specs.ruleset.entities.character.CharacterAIType;
import soliloquy.specs.ruleset.entities.character.CharacterType;
import soliloquy.specs.ruleset.entities.character.VariableStatisticType;

import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static soliloquy.specs.common.shared.Direction.SOUTHWEST;

@ExtendWith(MockitoExtension.class)
public class CharacterImplTests {
    private final UUID UUID = java.util.UUID.randomUUID();

    @Mock private CharacterType mockCharacterType;
    @Mock private TileEntities<Character> mockTileCharacters;
    @Mock private Tile mockTile;
    @Mock private CharacterEvents mockEvents;
    @Mock private Function<Character, CharacterEvents> mockEventsFactory;
    @Mock private CharacterStatusEffects mockStatusEffects;
    @Mock private Function<Character, CharacterStatusEffects> mockStatusEffectsFactory;
    @Mock private CharacterInventory mockInventory;
    @Mock private Function<Character, CharacterInventory> mockInventoryFactory;
    @Mock private CharacterEquipmentSlots mockEquipmentSlots;
    @Mock private Function<Character, CharacterEquipmentSlots> mockEquipmentSlotsFactory;
    @Mock private VariableStatisticType mockVariableStatType;
    @Mock private ImageAssetSet mockImageAssetSet;
    @Mock private Map<String, Object> mockData;
    
    private Character character;

    @BeforeEach
    public void setUp() {
        lenient().when(mockTileCharacters.contains(any())).thenReturn(true);

        lenient().when(mockTile.characters()).thenReturn(mockTileCharacters);
        
        lenient().when(mockEventsFactory.apply(any())).thenReturn(mockEvents);

        lenient().when(mockStatusEffectsFactory.apply(any())).thenReturn(mockStatusEffects);

        mockInventory = mock(CharacterInventory.class);

        lenient().when(mockInventoryFactory.apply(any())).thenReturn(mockInventory);

        //noinspection unchecked
        mockEquipmentSlotsFactory = mock(Function.class);
        lenient().when(mockEquipmentSlotsFactory.apply(any())).thenReturn(mockEquipmentSlots);

        character = new CharacterImpl(UUID, mockCharacterType, mockEventsFactory,
                mockEquipmentSlotsFactory, mockInventoryFactory, mockStatusEffectsFactory,
                mockData);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterImpl(null, mockCharacterType, mockEventsFactory,
                        mockEquipmentSlotsFactory, mockInventoryFactory, mockStatusEffectsFactory,
                        mockData));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterImpl(UUID, null, mockEventsFactory,
                        mockEquipmentSlotsFactory, mockInventoryFactory, mockStatusEffectsFactory,
                        mockData));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterImpl(UUID, mockCharacterType, null, mockEquipmentSlotsFactory,
                        mockInventoryFactory, mockStatusEffectsFactory, mockData));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterImpl(UUID, mockCharacterType, mockEventsFactory, null,
                        mockInventoryFactory, mockStatusEffectsFactory, mockData));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterImpl(UUID, mockCharacterType, mockEventsFactory,
                        mockEquipmentSlotsFactory, null, mockStatusEffectsFactory, mockData));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterImpl(UUID, mockCharacterType, mockEventsFactory,
                        mockEquipmentSlotsFactory, mockInventoryFactory, null, mockData));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterImpl(UUID, mockCharacterType, mockEventsFactory,
                        mockEquipmentSlotsFactory, mockInventoryFactory, mockStatusEffectsFactory,
                        null));
    }

    @Test
    public void testEquals() {
        var character2 = new CharacterImpl(UUID, mockCharacterType, mockEventsFactory,
                mockEquipmentSlotsFactory, mockInventoryFactory, mockStatusEffectsFactory,
                mockData);

        assertEquals(character, character2);
    }

    @Test
    public void testCharacterType() {
        assertSame(mockCharacterType, character.type());
    }

    @Test
    public void testClassifications() {
        assertNotNull(character.classifications());
    }

    @Test
    public void testPronouns() {
        assertNotNull(character.pronouns());
    }

    @Test
    public void testSetAndGetStance() {
        var stance = randomString();

        character.setStance(stance);

        assertEquals(stance, character.getStance());
    }

    @Test
    public void testSetAndGetDirection() {
        var direction = SOUTHWEST;

        character.setDirection(direction);

        assertEquals(direction, character.getDirection());
    }

    @Test
    public void testSetAndGetImageAssetSet() {
        character.setImageAssetSet(mockImageAssetSet);

        assertSame(mockImageAssetSet, character.getImageAssetSet());
    }

    @Test
    public void testSetImageAssetSetWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> character.setImageAssetSet(null));
    }

    @Test
    public void testSetAndGetAIType() {
        var mockCharacterAIType = mock(CharacterAIType.class);

        assertNull(character.getAIType());

        character.setAIType(mockCharacterAIType);

        assertSame(mockCharacterAIType, character.getAIType());
    }

    @Test
    public void testSetAITypeWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> character.setAIType(null));
    }

    @Test
    public void testCharacterEvents() {
        assertNotNull(character.events());
    }

    @Test
    public void testEquipment() {
        assertSame(mockEquipmentSlots, character.equipmentSlots());
    }

    @Test
    public void testInventory() {
        assertNotNull(character.inventory());
    }

    @Test
    public void testSetAndGetVariableStatisticCurrentValue() {
        assertEquals(0, character.getVariableStatisticCurrentValue(mockVariableStatType));

        var variableStatCurrentLevel = randomInt();

        character.setVariableStatisticCurrentValue(mockVariableStatType, variableStatCurrentLevel);

        assertEquals(variableStatCurrentLevel,
                character.getVariableStatisticCurrentValue(mockVariableStatType));
    }

    @Test
    public void testSetAndGetVariableStatisticCurrentValueWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> character.setVariableStatisticCurrentValue(null, randomInt()));
        assertThrows(IllegalArgumentException.class,
                () -> character.getVariableStatisticCurrentValue(null));
    }

    @Test
    public void testVariableStatisticCurrentValuesRepresentation() {
        Integer variableStatCurrentLevel = randomInt();

        assertNotNull(character.variableStatisticCurrentValuesRepresentation());
        assertTrue(character.variableStatisticCurrentValuesRepresentation().isEmpty());

        character.setVariableStatisticCurrentValue(mockVariableStatType, variableStatCurrentLevel);

        var variableStatCurrentLevels = character.variableStatisticCurrentValuesRepresentation();

        assertNotNull(variableStatCurrentLevels);
        assertEquals(1, variableStatCurrentLevels.size());
        assertEquals(variableStatCurrentLevel, variableStatCurrentLevels.get(mockVariableStatType));
        assertNotSame(variableStatCurrentLevels, character.variableStatisticCurrentValuesRepresentation());
    }

    @Test
    public void testStatusEffects() {
        assertNotNull(character.statusEffects());
    }

    @Test
    public void testPassiveAbilities() {
        assertNotNull(character.passiveAbilities());
    }

    @Test
    public void testActiveAbilities() {
        assertNotNull(character.activeAbilities());
    }

    @Test
    public void testReactiveAbilities() {
        assertNotNull(character.reactiveAbilities());
    }

    @Test
    public void testSetAndGetPlayerControlled() {
        character.setPlayerControlled(true);
        assertTrue(character.getPlayerControlled());

        character.setPlayerControlled(false);
        assertFalse(character.getPlayerControlled());
    }

    @Test
    public void testData() {
        assertSame(mockData, character.data());
    }

    @Test
    public void testSetAndGetName() {
        var name = randomString();

        character.setName(name);

        assertEquals(name, character.getName());
    }

    @Test
    public void testSetNameWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> character.setName(null));
        assertThrows(IllegalArgumentException.class, () -> character.setName(""));
    }

    @Test
    public void testUuid() {
        assertSame(UUID, character.uuid());
    }

    @Test
    public void testAssignTileAfterAddedToTileEntitiesOfTypeAndTile() {
        character.assignTileAfterAddedToTileEntitiesOfType(mockTile);

        assertSame(mockTile, character.tile());
    }

    @Test
    public void testDelete() {
        character.assignTileAfterAddedToTileEntitiesOfType(mockTile);

        character.delete();

        assertTrue(character.isDeleted());
        verify(mockTile, atLeast(1)).characters();
        verify(mockTileCharacters).remove(character);
        verify(mockEquipmentSlots).delete();
        verify(mockInventory).delete();
        verify(mockStatusEffects).delete();
    }

    @Test
    public void testThrowsIllegalStateExceptionWhenDeleted() {
        character.delete();

        assertThrows(EntityDeletedException.class, () -> character.type());
        assertThrows(EntityDeletedException.class, () -> character.classifications());
        assertThrows(EntityDeletedException.class, () -> character.pronouns());
        assertThrows(EntityDeletedException.class, () -> character.tile());
        assertThrows(EntityDeletedException.class, () -> character.getStance());
        assertThrows(EntityDeletedException.class, () -> character.setStance(""));
        assertThrows(EntityDeletedException.class, () -> character.getDirection());
        assertThrows(EntityDeletedException.class, () -> character.setDirection(SOUTHWEST));
        assertThrows(EntityDeletedException.class, () -> character.getImageAssetSet());
        assertThrows(EntityDeletedException.class,
                () -> character.setImageAssetSet(mockImageAssetSet));
        assertThrows(EntityDeletedException.class, () -> character.getAIType());
        assertThrows(EntityDeletedException.class, () -> character.setAIType(null));
        assertThrows(EntityDeletedException.class, () -> character.events());
        assertThrows(EntityDeletedException.class, () -> character.equipmentSlots());
        assertThrows(EntityDeletedException.class, () -> character.inventory());
        assertThrows(EntityDeletedException.class,
                () -> character.getVariableStatisticCurrentValue(mockVariableStatType));
        assertThrows(EntityDeletedException.class,
                () -> character.setVariableStatisticCurrentValue(mockVariableStatType, 0));
        assertThrows(EntityDeletedException.class,
                () -> character.variableStatisticCurrentValuesRepresentation());
        assertThrows(EntityDeletedException.class, () -> character.statusEffects());
        assertThrows(EntityDeletedException.class, () -> character.passiveAbilities());
        assertThrows(EntityDeletedException.class, () -> character.activeAbilities());
        assertThrows(EntityDeletedException.class, () -> character.reactiveAbilities());
        assertThrows(EntityDeletedException.class, () -> character.getPlayerControlled());
        assertThrows(EntityDeletedException.class, () -> character.setPlayerControlled(true));
        assertThrows(EntityDeletedException.class, () -> character.data());
        assertThrows(EntityDeletedException.class,
                () -> character.assignTileAfterAddedToTileEntitiesOfType(null));
        assertThrows(EntityDeletedException.class, () -> character.getName());
        assertThrows(EntityDeletedException.class, () -> character.setName(randomString()));
    }

    @Test
    public void testEnforceTileInvariant() {
        character.assignTileAfterAddedToTileEntitiesOfType(mockTile);
        when(mockTileCharacters.contains(any())).thenReturn(false);

        assertThrows(IllegalStateException.class, () -> character.type());
        assertThrows(IllegalStateException.class, () -> character.classifications());
        assertThrows(IllegalStateException.class, () -> character.pronouns());
        assertThrows(IllegalStateException.class, () -> character.tile());
        assertThrows(IllegalStateException.class, () -> character.getStance());
        assertThrows(IllegalStateException.class, () -> character.setStance(""));
        assertThrows(IllegalStateException.class, () -> character.getDirection());
        assertThrows(IllegalStateException.class, () -> character.setDirection(SOUTHWEST));
        assertThrows(IllegalStateException.class, () -> character.getImageAssetSet());
        assertThrows(IllegalStateException.class,
                () -> character.setImageAssetSet(mockImageAssetSet));
        assertThrows(IllegalStateException.class, () -> character.getAIType());
        assertThrows(IllegalStateException.class, () -> character.setAIType(null));
        assertThrows(IllegalStateException.class, () -> character.events());
        assertThrows(IllegalStateException.class, () -> character.equipmentSlots());
        assertThrows(IllegalStateException.class, () -> character.inventory());
        assertThrows(IllegalStateException.class,
                () -> character.getVariableStatisticCurrentValue(mockVariableStatType));
        assertThrows(IllegalStateException.class,
                () -> character.setVariableStatisticCurrentValue(mockVariableStatType, 0));
        assertThrows(IllegalStateException.class,
                () -> character.variableStatisticCurrentValuesRepresentation());
        assertThrows(IllegalStateException.class, () -> character.statusEffects());
        assertThrows(IllegalStateException.class, () -> character.passiveAbilities());
        assertThrows(IllegalStateException.class, () -> character.activeAbilities());
        assertThrows(IllegalStateException.class, () -> character.reactiveAbilities());
        assertThrows(IllegalStateException.class, () -> character.getPlayerControlled());
        assertThrows(IllegalStateException.class, () -> character.setPlayerControlled(true));
        assertThrows(IllegalStateException.class, () -> character.data());
        assertThrows(IllegalStateException.class, () -> character.delete());
        assertThrows(IllegalStateException.class, () -> character.isDeleted());
        assertThrows(IllegalStateException.class,
                () -> character.assignTileAfterAddedToTileEntitiesOfType(null));
        assertThrows(IllegalStateException.class, () -> character.getName());
        assertThrows(IllegalStateException.class, () -> character.setName(randomString()));
    }
}

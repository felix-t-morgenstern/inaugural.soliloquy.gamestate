package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.CharacterImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.gamestate.factories.CharacterEquipmentSlotsFactory;
import soliloquy.specs.gamestate.factories.CharacterEventsFactory;
import soliloquy.specs.gamestate.factories.CharacterInventoryFactory;
import soliloquy.specs.gamestate.factories.CharacterStatusEffectsFactory;
import soliloquy.specs.graphics.assets.ImageAssetSet;
import soliloquy.specs.ruleset.entities.character.CharacterAIType;
import soliloquy.specs.ruleset.entities.character.CharacterType;
import soliloquy.specs.ruleset.entities.character.CharacterVariableStatisticType;

import java.util.UUID;

import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static soliloquy.specs.common.shared.Direction.SOUTHWEST;

class CharacterImplTests {
    private final UUID UUID = java.util.UUID.randomUUID();

    @Mock private CharacterType mockCharacterType;
    @Mock private TileEntities<Character> mockTileCharacters;
    @Mock private Tile mockTile;
    @Mock private CharacterEvents mockEvents;
    @Mock private CharacterEventsFactory mockEventsFactory;
    @Mock private CharacterStatusEffects mockStatusEffects;
    @Mock private CharacterStatusEffectsFactory mockStatusEffectsFactory;
    @Mock private CharacterInventory mockInventory;
    @Mock private CharacterInventoryFactory mockInventoryFactory;
    @Mock private CharacterEquipmentSlots mockEquipmentSlots;
    @Mock private CharacterEquipmentSlotsFactory mockEquipmentSlotsFactory;
    @Mock private CharacterVariableStatisticType mockVariableStatType;
    @Mock private VariableCache mockData;
    @Mock private ImageAssetSet mockImageAssetSet;

    private Character character;

    @BeforeEach
    void setUp() {
        mockCharacterType = mock(CharacterType.class);

        //noinspection unchecked
        mockTileCharacters = (TileEntities<Character>) mock(TileEntities.class);
        when(mockTileCharacters.contains(any())).thenReturn(true);

        mockTile = mock(Tile.class);
        when(mockTile.characters()).thenReturn(mockTileCharacters);

        mockEvents = mock(CharacterEvents.class);

        mockEventsFactory = mock(CharacterEventsFactory.class);
        when(mockEventsFactory.make(any())).thenReturn(mockEvents);

        mockStatusEffects = mock(CharacterStatusEffects.class);

        mockStatusEffectsFactory = mock(CharacterStatusEffectsFactory.class);
        when(mockStatusEffectsFactory.make(any())).thenReturn(mockStatusEffects);

        mockInventory = mock(CharacterInventory.class);

        mockInventoryFactory = mock(CharacterInventoryFactory.class);
        when(mockInventoryFactory.make(any())).thenReturn(mockInventory);

        mockEquipmentSlots = mock(CharacterEquipmentSlots.class);

        mockEquipmentSlotsFactory = mock(CharacterEquipmentSlotsFactory.class);
        when(mockEquipmentSlotsFactory.make(any())).thenReturn(mockEquipmentSlots);

        mockVariableStatType = mock(CharacterVariableStatisticType.class);

        mockData = mock(VariableCache.class);

        mockImageAssetSet = mock(ImageAssetSet.class);

        character = new CharacterImpl(UUID, mockCharacterType, mockEventsFactory,
                mockEquipmentSlotsFactory, mockInventoryFactory, mockStatusEffectsFactory,
                mockData);
    }

    @Test
    void testConstructorWithInvalidParams() {
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
    void testGetInterfaceName() {
        assertEquals(Character.class.getCanonicalName(), character.getInterfaceName());
    }

    @Test
    void testEquals() {
        var character2 = new CharacterImpl(UUID, mockCharacterType, mockEventsFactory,
                mockEquipmentSlotsFactory, mockInventoryFactory, mockStatusEffectsFactory,
                mockData);

        assertEquals(character, character2);
    }

    @Test
    void testCharacterType() {
        assertSame(mockCharacterType, character.type());
    }

    @Test
    void testClassifications() {
        assertNotNull(character.classifications());
    }

    @Test
    void testPronouns() {
        assertNotNull(character.pronouns());
    }

    @Test
    void testSetAndGetStance() {
        var stance = randomString();

        character.setStance(stance);

        assertEquals(stance, character.getStance());
    }

    @Test
    void testSetAndGetDirection() {
        var direction = SOUTHWEST;

        character.setDirection(direction);

        assertEquals(direction, character.getDirection());
    }

    @Test
    void testSetAndGetImageAssetSet() {
        character.setImageAssetSet(mockImageAssetSet);

        assertSame(mockImageAssetSet, character.getImageAssetSet());
    }

    @Test
    void testSetImageAssetSetWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> character.setImageAssetSet(null));
    }

    @Test
    void testSetAndGetAIType() {
        var mockCharacterAIType = mock(CharacterAIType.class);

        assertNull(character.getAIType());

        character.setAIType(mockCharacterAIType);

        assertSame(mockCharacterAIType, character.getAIType());
    }

    @Test
    void testSetAITypeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> character.setAIType(null));
    }

    @Test
    void testCharacterEvents() {
        assertNotNull(character.events());
    }

    @Test
    void testEquipment() {
        assertSame(mockEquipmentSlots, character.equipmentSlots());
    }

    @Test
    void testInventory() {
        assertNotNull(character.inventory());
    }

    @Test
    void testSetAndGetVariableStatisticCurrentValue() {
        assertEquals(0, character.getVariableStatisticCurrentValue(mockVariableStatType));

        var variableStatCurrentLevel = randomInt();

        character.setVariableStatisticCurrentValue(mockVariableStatType, variableStatCurrentLevel);

        assertEquals(variableStatCurrentLevel,
                character.getVariableStatisticCurrentValue(mockVariableStatType));
    }

    @Test
    void testSetAndGetVariableStatisticCurrentValueWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> character.setVariableStatisticCurrentValue(null, randomInt()));
        assertThrows(IllegalArgumentException.class,
                () -> character.getVariableStatisticCurrentValue(null));
    }

    @Test
    void testVariableStatisticCurrentValuesRepresentation() {
        var variableStatCurrentLevel = randomInt();

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
    void testStatusEffects() {
        assertNotNull(character.statusEffects());
    }

    @Test
    void testPassiveAbilities() {
        assertNotNull(character.passiveAbilities());
    }

    @Test
    void testActiveAbilities() {
        assertNotNull(character.activeAbilities());
    }

    @Test
    void testReactiveAbilities() {
        assertNotNull(character.reactiveAbilities());
    }

    @Test
    void testSetAndGetPlayerControlled() {
        character.setPlayerControlled(true);
        assertTrue(character.getPlayerControlled());

        character.setPlayerControlled(false);
        assertFalse(character.getPlayerControlled());
    }

    @Test
    void testData() {
        assertSame(mockData, character.data());
    }

    @Test
    void testSetAndGetName() {
        var name = randomString();

        character.setName(name);

        assertEquals(name, character.getName());
    }

    @Test
    void testSetNameWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> character.setName(null));
        assertThrows(IllegalArgumentException.class, () -> character.setName(""));
    }

    @Test
    void testUuid() {
        assertSame(UUID, character.uuid());
    }

    @Test
    void testAssignTileAfterAddedToTileEntitiesOfTypeAndTile() {
        character.assignTileAfterAddedToTileEntitiesOfType(mockTile);

        assertSame(mockTile, character.tile());
    }

    @Test
    void testDelete() {
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
    void testThrowsIllegalStateExceptionWhenDeleted() {
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
        assertThrows(EntityDeletedException.class, () -> character.getInterfaceName());
    }

    @Test
    void testEnforceTileInvariant() {
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
        assertThrows(IllegalStateException.class, () -> character.getInterfaceName());
    }
}

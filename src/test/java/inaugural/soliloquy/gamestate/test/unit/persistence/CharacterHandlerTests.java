package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.CharacterHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.shared.Direction;
import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.factories.CharacterFactory;
import soliloquy.specs.graphics.assets.ImageAssetSet;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbility;
import soliloquy.specs.ruleset.entities.abilities.PassiveAbility;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbility;
import soliloquy.specs.ruleset.entities.character.CharacterAIType;
import soliloquy.specs.ruleset.entities.character.CharacterType;
import soliloquy.specs.ruleset.entities.character.VariableStatisticType;
import soliloquy.specs.ruleset.entities.character.StatusEffectType;
import soliloquy.specs.ruleset.valueobjects.CharacterClassification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import static inaugural.soliloquy.tools.collections.Collections.*;
import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static inaugural.soliloquy.tools.testing.Mock.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static soliloquy.specs.common.shared.Direction.SOUTHWEST;

class CharacterHandlerTests {
    private final UUID UUID = java.util.UUID.randomUUID();

    private final Map<String, CharacterType> CHARACTER_TYPES = new HashMap<>();

    private final String CHARACTER_TYPE_ID = randomString();

    private final String CASE = randomString();
    private final String ARTICLE = randomString();

    private final String STANCE = randomString();

    private final Direction DIRECTION = SOUTHWEST;

    private final Map<String, CharacterClassification> CLASSIFICATIONS = new HashMap<>();
    private final String CLASSIFICATION_ID = randomString();

    private final Map<String, ImageAssetSet> IMAGE_ASSET_SETS = new HashMap<>();
    private final String IMAGE_ASSET_SET_ID = randomString();

    private final Map<String, CharacterAIType> AI_TYPES = new HashMap<>();
    private final String AI_TYPE_ID = randomString();

    private final String EVENTS_WRITTEN = randomString();
    private final HandlerAndEntity<CharacterEvents> EVENTS_AND_HANDLER =
            generateMockEntityAndHandler(CharacterEvents.class, EVENTS_WRITTEN);
    private final CharacterEvents CHARACTER_EVENTS = EVENTS_AND_HANDLER.entity;
    private final TypeHandler<CharacterEvents> EVENTS_HANDLER = EVENTS_AND_HANDLER.handler;

    private final String EQUIPMENT_SLOT = randomString();

    private final String EQUIPMENT_SLOT_ITEM_WRITTEN_VALUE = randomString();
    private final String INVENTORY_ITEM_WRITTEN_VALUE = randomString();
    private final HandlerAndEntities<Item> ITEM_HANDLER_AND_ENTITIES =
            generateMockHandlerAndEntities(Item.class,
                    new String[]{EQUIPMENT_SLOT_ITEM_WRITTEN_VALUE,
                                 INVENTORY_ITEM_WRITTEN_VALUE});
    private final TypeHandler<Item> ITEM_HANDLER = ITEM_HANDLER_AND_ENTITIES.handler;
    private final Item EQUIPMENT_SLOT_ITEM =
            ITEM_HANDLER_AND_ENTITIES.entities.get(EQUIPMENT_SLOT_ITEM_WRITTEN_VALUE);
    private final Item INVENTORY_ITEM =
            ITEM_HANDLER_AND_ENTITIES.entities.get(INVENTORY_ITEM_WRITTEN_VALUE);

    private final String VARIABLE_STAT_TYPE_ID = randomString();
    private final int VARIABLE_STAT_CURRENT_LEVEL = randomInt();

    private final Map<String, StatusEffectType> STATUS_EFFECT_TYPES = new HashMap<>();
    private final String STATUS_EFFECT_TYPE_ID = randomString();
    private final int STAT_EFFECT_LEVEL = randomInt();

    private final Map<String, PassiveAbility> PASSIVE_ABILITIES = new HashMap<>();
    private final String PASSIVE_ABILITY_ID = randomString();

    private final Map<String, ActiveAbility> ACTIVE_ABILITIES = new HashMap<>();
    private final String ACTIVE_ABILITY_ID = randomString();

    private final Map<String, ReactiveAbility> REACTIVE_ABILITIES = new HashMap<>();
    private final String REACTIVE_ABILITY_ID = randomString();

    private final String DATA_WRITTEN = randomString();
    private final HandlerAndEntities<VariableCache> DATA_HANDLER_AND_ENTITIES =
            generateMockHandlerAndEntities(VariableCache.class, arrayOf(DATA_WRITTEN));
    private final TypeHandler<VariableCache> DATA_HANDLER = DATA_HANDLER_AND_ENTITIES.handler;
    private final VariableCache DATA = DATA_HANDLER_AND_ENTITIES.entities.get(DATA_WRITTEN);

    private final String NAME = randomString();

    @Mock private CharacterType mockCharacterType;
    @Mock private CharacterClassification mockClassification;
    @Mock private List<CharacterClassification> mockClassifications;
    @Mock private Map<String, String> mockPronouns;
    @Mock private ImageAssetSet mockImageAssetSet;
    @Mock private CharacterAIType mockCharacterAIType;
    @Mock private CharacterEquipmentSlots mockEquipmentSlots;
    @Mock private CharacterInventory mockInventory;
    @Mock private VariableStatisticType mockVariableStatType;
    @Mock private Function<String, VariableStatisticType> mockGetVariableStatType;
    @Mock private Map<VariableStatisticType, Integer> mockVariableStats;
    @Mock private PassiveAbility mockPassiveAbility;
    @Mock private ActiveAbility mockActiveAbility;
    @Mock private ReactiveAbility mockReactiveAbility;
    @Mock private List<PassiveAbility> mockPassiveAbilities;
    @Mock private List<ActiveAbility> mockActiveAbilities;
    @Mock private List<ReactiveAbility> mockReactiveAbilities;
    @Mock private StatusEffectType mockStatusEffectType;
    @Mock private CharacterStatusEffects mockStatEffects;
    @Mock private Character mockCharacter;
    @Mock private CharacterFactory mockCharacterFactory;

    private final String WRITTEN_VALUE = String.format(
            "{\"uuid\":\"%s\",\"characterTypeId\":\"%s\",\"classifications\":[\"%s\"]," +
                    "\"pronouns\":[{\"key\":\"%s\",\"val\":\"%s\"}],\"stance\":\"%s\"," +
                    "\"direction\":%s,\"assetSetId\":\"%s\",\"aiTypeId\":\"%s\"," +
                    "\"events\":\"%s\",\"equipmentSlots\":[{\"key\":\"%s\",\"val\":\"%s\"}]," +
                    "\"inventoryItems\":[\"%s\"],\"variableStats\":[{\"type\":\"%s\"," +
                    "\"current\":%d}],\"statusEffects\":[{\"type\":\"%s\",\"value\":%d}]," +
                    "\"passiveAbilityIds\":[\"%s\"],\"activeAbilityIds\":[\"%s\"]," +
                    "\"reactiveAbilityIds\":[\"%s\"],\"isPlayerControlled\":true,\"data\":\"%s\"," +
                    "\"name\":\"%s\"}",
            UUID, CHARACTER_TYPE_ID, CLASSIFICATION_ID, CASE, ARTICLE, STANCE, DIRECTION.getValue(),
            IMAGE_ASSET_SET_ID, AI_TYPE_ID, EVENTS_WRITTEN, EQUIPMENT_SLOT,
            EQUIPMENT_SLOT_ITEM_WRITTEN_VALUE, INVENTORY_ITEM_WRITTEN_VALUE, VARIABLE_STAT_TYPE_ID,
            VARIABLE_STAT_CURRENT_LEVEL, STATUS_EFFECT_TYPE_ID, STAT_EFFECT_LEVEL,
            PASSIVE_ABILITY_ID,
            ACTIVE_ABILITY_ID, REACTIVE_ABILITY_ID, DATA_WRITTEN, NAME);

    private TypeHandler<Character> characterHandler;

    @BeforeEach
    void setUp() {
        mockCharacterType = generateMockWithId(CharacterType.class, CHARACTER_TYPE_ID);

        mockClassification = generateMockWithId(CharacterClassification.class, CLASSIFICATION_ID);

        mockClassifications = generateMockList(mockClassification);

        mockPronouns = generateMockMap(Pair.of(CASE, ARTICLE));

        mockImageAssetSet = generateMockWithId(ImageAssetSet.class, IMAGE_ASSET_SET_ID);

        mockCharacterAIType = generateMockWithId(CharacterAIType.class, AI_TYPE_ID);

        mockEquipmentSlots = mock(CharacterEquipmentSlots.class);
        when(mockEquipmentSlots.representation()).thenReturn(
                mapOf(Pair.of(EQUIPMENT_SLOT, EQUIPMENT_SLOT_ITEM)));

        mockInventory = mock(CharacterInventory.class);
        when(mockInventory.representation()).thenReturn(listOf(INVENTORY_ITEM));

        mockVariableStatType = mock(VariableStatisticType.class);
        when(mockVariableStatType.id()).thenReturn(VARIABLE_STAT_TYPE_ID);

        //noinspection unchecked
        mockGetVariableStatType = (Function<String, VariableStatisticType>) mock(Function.class);
        when(mockGetVariableStatType.apply(anyString())).thenReturn(mockVariableStatType);

        mockVariableStats =
                generateMockMap(Pair.of(mockVariableStatType, VARIABLE_STAT_CURRENT_LEVEL));

        mockStatusEffectType = generateMockWithId(StatusEffectType.class, STATUS_EFFECT_TYPE_ID);

        mockStatEffects = mock(CharacterStatusEffects.class);
        when(mockStatEffects.representation()).thenReturn(
                mapOf(Pair.of(mockStatusEffectType, STAT_EFFECT_LEVEL)));

        mockPassiveAbility = generateMockWithId(PassiveAbility.class, PASSIVE_ABILITY_ID);

        mockActiveAbility = generateMockWithId(ActiveAbility.class, ACTIVE_ABILITY_ID);

        mockReactiveAbility = generateMockWithId(ReactiveAbility.class, REACTIVE_ABILITY_ID);

        mockPassiveAbilities = generateMockList(mockPassiveAbility);

        mockActiveAbilities = generateMockList(mockActiveAbility);

        mockReactiveAbilities = generateMockList(mockReactiveAbility);

        mockCharacter = mock(Character.class);
        when(mockCharacter.uuid()).thenReturn(UUID);
        when(mockCharacter.data()).thenReturn(DATA);
        when(mockCharacter.type()).thenReturn(mockCharacterType);
        when(mockCharacter.classifications()).thenReturn(mockClassifications);
        when(mockCharacter.pronouns()).thenReturn(mockPronouns);
        when(mockCharacter.getStance()).thenReturn(STANCE);
        when(mockCharacter.getDirection()).thenReturn(DIRECTION);
        when(mockCharacter.getImageAssetSet()).thenReturn(mockImageAssetSet);
        when(mockCharacter.getAIType()).thenReturn(mockCharacterAIType);
        when(mockCharacter.events()).thenReturn(CHARACTER_EVENTS);
        when(mockCharacter.equipmentSlots()).thenReturn(mockEquipmentSlots);
        when(mockCharacter.inventory()).thenReturn(mockInventory);
        when(mockCharacter.variableStatisticCurrentValuesRepresentation())
                .thenReturn(mockVariableStats);
        when(mockCharacter.statusEffects()).thenReturn(mockStatEffects);
        when(mockCharacter.passiveAbilities()).thenReturn(mockPassiveAbilities);
        when(mockCharacter.activeAbilities()).thenReturn(mockActiveAbilities);
        when(mockCharacter.reactiveAbilities()).thenReturn(mockReactiveAbilities);
        when(mockCharacter.getPlayerControlled()).thenReturn(true);
        when(mockCharacter.getName()).thenReturn(NAME);

        mockCharacterFactory = mock(CharacterFactory.class);
        when(mockCharacterFactory.make(any(), any(), any())).thenReturn(mockCharacter);

        CHARACTER_TYPES.put(CHARACTER_TYPE_ID, mockCharacterType);
        CLASSIFICATIONS.put(CLASSIFICATION_ID, mockClassification);
        IMAGE_ASSET_SETS.put(IMAGE_ASSET_SET_ID, mockImageAssetSet);
        AI_TYPES.put(AI_TYPE_ID, mockCharacterAIType);
        STATUS_EFFECT_TYPES.put(STATUS_EFFECT_TYPE_ID, mockStatusEffectType);
        PASSIVE_ABILITIES.put(PASSIVE_ABILITY_ID, mockPassiveAbility);
        ACTIVE_ABILITIES.put(ACTIVE_ABILITY_ID, mockActiveAbility);
        REACTIVE_ABILITIES.put(REACTIVE_ABILITY_ID, mockReactiveAbility);

        characterHandler = new CharacterHandler(mockCharacterFactory, CHARACTER_TYPES::get,
                CLASSIFICATIONS::get, IMAGE_ASSET_SETS::get, AI_TYPES::get, EVENTS_HANDLER,
                mockGetVariableStatType, STATUS_EFFECT_TYPES::get, PASSIVE_ABILITIES::get,
                ACTIVE_ABILITIES::get, REACTIVE_ABILITIES::get, DATA_HANDLER, ITEM_HANDLER);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterHandler(null, CHARACTER_TYPES::get, CLASSIFICATIONS::get,
                        IMAGE_ASSET_SETS::get, AI_TYPES::get, EVENTS_HANDLER,
                        mockGetVariableStatType, STATUS_EFFECT_TYPES::get, PASSIVE_ABILITIES::get,
                        ACTIVE_ABILITIES::get, REACTIVE_ABILITIES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterHandler(mockCharacterFactory, null, CLASSIFICATIONS::get,
                        IMAGE_ASSET_SETS::get, AI_TYPES::get, EVENTS_HANDLER,
                        mockGetVariableStatType, STATUS_EFFECT_TYPES::get, PASSIVE_ABILITIES::get,
                        ACTIVE_ABILITIES::get, REACTIVE_ABILITIES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterHandler(mockCharacterFactory, CHARACTER_TYPES::get, null,
                        IMAGE_ASSET_SETS::get, AI_TYPES::get, EVENTS_HANDLER,
                        mockGetVariableStatType, STATUS_EFFECT_TYPES::get, PASSIVE_ABILITIES::get,
                        ACTIVE_ABILITIES::get, REACTIVE_ABILITIES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterHandler(mockCharacterFactory, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, null, AI_TYPES::get, EVENTS_HANDLER,
                        mockGetVariableStatType, STATUS_EFFECT_TYPES::get, PASSIVE_ABILITIES::get,
                        ACTIVE_ABILITIES::get, REACTIVE_ABILITIES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterHandler(mockCharacterFactory, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, IMAGE_ASSET_SETS::get, null, EVENTS_HANDLER,
                        mockGetVariableStatType, STATUS_EFFECT_TYPES::get, PASSIVE_ABILITIES::get,
                        ACTIVE_ABILITIES::get, REACTIVE_ABILITIES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterHandler(mockCharacterFactory, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, IMAGE_ASSET_SETS::get, AI_TYPES::get, null,
                        mockGetVariableStatType, STATUS_EFFECT_TYPES::get, PASSIVE_ABILITIES::get,
                        ACTIVE_ABILITIES::get, REACTIVE_ABILITIES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterHandler(mockCharacterFactory, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, IMAGE_ASSET_SETS::get, AI_TYPES::get, EVENTS_HANDLER,
                        null, STATUS_EFFECT_TYPES::get, PASSIVE_ABILITIES::get,
                        ACTIVE_ABILITIES::get, REACTIVE_ABILITIES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterHandler(mockCharacterFactory, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, IMAGE_ASSET_SETS::get, AI_TYPES::get, EVENTS_HANDLER,
                        mockGetVariableStatType, null, PASSIVE_ABILITIES::get,
                        ACTIVE_ABILITIES::get, REACTIVE_ABILITIES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterHandler(mockCharacterFactory, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, IMAGE_ASSET_SETS::get, AI_TYPES::get, EVENTS_HANDLER,
                        mockGetVariableStatType, STATUS_EFFECT_TYPES::get, null,
                        ACTIVE_ABILITIES::get, REACTIVE_ABILITIES::get, DATA_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterHandler(mockCharacterFactory, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, IMAGE_ASSET_SETS::get, AI_TYPES::get, EVENTS_HANDLER,
                        mockGetVariableStatType, STATUS_EFFECT_TYPES::get, PASSIVE_ABILITIES::get,
                        null, REACTIVE_ABILITIES::get, DATA_HANDLER, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterHandler(mockCharacterFactory, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, IMAGE_ASSET_SETS::get, AI_TYPES::get, EVENTS_HANDLER,
                        mockGetVariableStatType, STATUS_EFFECT_TYPES::get, PASSIVE_ABILITIES::get,
                        ACTIVE_ABILITIES::get, null, DATA_HANDLER, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterHandler(mockCharacterFactory, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, IMAGE_ASSET_SETS::get, AI_TYPES::get, EVENTS_HANDLER,
                        mockGetVariableStatType, STATUS_EFFECT_TYPES::get, PASSIVE_ABILITIES::get,
                        ACTIVE_ABILITIES::get, REACTIVE_ABILITIES::get, null, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class, () ->
                new CharacterHandler(mockCharacterFactory, CHARACTER_TYPES::get,
                        CLASSIFICATIONS::get, IMAGE_ASSET_SETS::get, AI_TYPES::get, EVENTS_HANDLER,
                        mockGetVariableStatType, STATUS_EFFECT_TYPES::get, PASSIVE_ABILITIES::get,
                        ACTIVE_ABILITIES::get, REACTIVE_ABILITIES::get, DATA_HANDLER, null));
    }

    @Test
    void testWrite() {
        String output = characterHandler.write(mockCharacter);

        assertEquals(WRITTEN_VALUE, output);
        verify(mockCharacter, times(1)).uuid();
        verify(mockCharacter, times(1)).type();
        verify(mockCharacterType, times(1)).id();
        verify(mockClassifications, times(1)).size();
        verify(mockClassifications, times(1)).iterator();
        verify(mockClassification, times(1)).id();
        verify(mockCharacter, times(1)).getStance();
        verify(mockCharacter, times(1)).getDirection();
        verify(mockCharacter, times(1)).getImageAssetSet();
        verify(mockImageAssetSet, times(1)).id();
        verify(mockCharacter, times(1)).getAIType();
        verify(mockCharacterAIType, times(1)).id();
        verify(mockCharacter, times(1)).events();
        verify(EVENTS_HANDLER, times(1)).write(CHARACTER_EVENTS);
        verify(mockCharacter, times(1)).equipmentSlots();
        verify(mockEquipmentSlots, times(1)).representation();
        verify(ITEM_HANDLER, times(1)).write(EQUIPMENT_SLOT_ITEM);
        verify(mockCharacter, times(1)).inventory();
        verify(ITEM_HANDLER, times(1)).write(INVENTORY_ITEM);
        verify(mockCharacter, times(1)).variableStatisticCurrentValuesRepresentation();
        verify(mockVariableStats, times(1)).size();
        verify(mockVariableStatType, times(1)).id();
        verify(mockCharacter, times(1)).statusEffects();
        verify(mockStatEffects, times(1)).representation();
        verify(mockStatusEffectType, times(1)).id();
        verify(mockPassiveAbility, times(1)).id();
        verify(mockActiveAbility, times(1)).id();
        verify(mockReactiveAbility, times(1)).id();
        verify(mockCharacter, times(1)).getPlayerControlled();
        verify(mockCharacter, times(1)).data();
        verify(DATA_HANDLER, times(1)).write(DATA);
        verify(mockCharacter, times(1)).getName();
    }

    @Test
    void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> characterHandler.write(null));
    }

    @Test
    void testRead() {
        Character output = characterHandler.read(WRITTEN_VALUE);

        assertSame(mockCharacter, output);
        verify(DATA_HANDLER, times(1)).read(DATA_WRITTEN);
        verify(mockCharacterFactory, times(1)).make(mockCharacterType, UUID, DATA);
        verify(mockClassifications, times(1)).add(mockClassification);
        verify(mockPronouns, times(1)).put(CASE, ARTICLE);
        verify(mockCharacter, times(1)).setStance(STANCE);
        verify(mockCharacter, times(1)).setDirection(DIRECTION);
        verify(mockCharacter, times(1)).setImageAssetSet(mockImageAssetSet);
        verify(mockCharacter, times(1)).setAIType(mockCharacterAIType);
        verify(EVENTS_HANDLER, times(1)).read(EVENTS_WRITTEN);
        verify(CHARACTER_EVENTS, times(1)).copyAllTriggers(CHARACTER_EVENTS);
        verify(mockEquipmentSlots, times(1)).addCharacterEquipmentSlot(EQUIPMENT_SLOT);
        verify(ITEM_HANDLER, times(1)).read(EQUIPMENT_SLOT_ITEM_WRITTEN_VALUE);
        verify(mockEquipmentSlots, times(1)).equipItemToSlot(EQUIPMENT_SLOT, EQUIPMENT_SLOT_ITEM);
        verify(ITEM_HANDLER, times(1)).read(INVENTORY_ITEM_WRITTEN_VALUE);
        verify(mockInventory, times(1)).add(INVENTORY_ITEM);
        verify(mockGetVariableStatType, times(1)).apply(VARIABLE_STAT_TYPE_ID);
        verify(mockCharacter, times(1)).setVariableStatisticCurrentValue(mockVariableStatType,
                VARIABLE_STAT_CURRENT_LEVEL);
        verify(mockStatEffects, times(1)).setStatusEffectLevel(mockStatusEffectType,
                STAT_EFFECT_LEVEL);
        verify(mockPassiveAbilities, times(1)).add(mockPassiveAbility);
        verify(mockActiveAbilities, times(1)).add(mockActiveAbility);
        verify(mockReactiveAbilities, times(1)).add(mockReactiveAbility);
        verify(mockCharacter, times(1)).setPlayerControlled(true);
        verify(mockCharacter, times(1)).setName(NAME);
    }

    @Test
    void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> characterHandler.read(null));
        assertThrows(IllegalArgumentException.class, () -> characterHandler.read(""));
    }

    @Test
    void testGetArchetype() {
        assertNotNull(characterHandler.getArchetype());
        assertEquals(Character.class.getCanonicalName(),
                characterHandler.getArchetype().getInterfaceName());
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        Character.class.getCanonicalName() + ">",
                characterHandler.getInterfaceName());
    }
}

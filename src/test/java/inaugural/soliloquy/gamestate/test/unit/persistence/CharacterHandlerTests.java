package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.CharacterHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.shared.Direction;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.factories.CharacterFactory;
import soliloquy.specs.graphics.assets.ImageAssetSet;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbility;
import soliloquy.specs.ruleset.entities.abilities.PassiveAbility;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbility;
import soliloquy.specs.ruleset.entities.character.CharacterAIType;
import soliloquy.specs.ruleset.entities.character.CharacterType;
import soliloquy.specs.ruleset.entities.character.StatusEffectType;
import soliloquy.specs.ruleset.entities.character.VariableStatisticType;
import soliloquy.specs.ruleset.valueobjects.CharacterClassification;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import static inaugural.soliloquy.tools.collections.Collections.*;
import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static inaugural.soliloquy.tools.testing.Mock.*;
import static inaugural.soliloquy.tools.valueobjects.Pair.pairOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static soliloquy.specs.common.shared.Direction.SOUTHWEST;

@RunWith(MockitoJUnitRunner.class)
public class CharacterHandlerTests {
    private final UUID UUID = java.util.UUID.randomUUID();

    private final Map<String, CharacterType> CHARACTER_TYPES = mapOf();

    private final String CHARACTER_TYPE_ID = randomString();

    private final String CASE = randomString();
    private final String ARTICLE = randomString();

    private final String STANCE = randomString();

    private final Direction DIRECTION = SOUTHWEST;

    private final Map<String, CharacterClassification> CLASSIFICATIONS = mapOf();
    private final String CLASSIFICATION_ID = randomString();

    private final Map<String, ImageAssetSet> IMAGE_ASSET_SETS = mapOf();
    private final String IMAGE_ASSET_SET_ID = randomString();

    private final Map<String, CharacterAIType> AI_TYPES = mapOf();
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

    private final Map<String, StatusEffectType> STATUS_EFFECT_TYPES = mapOf();
    private final String STATUS_EFFECT_TYPE_ID = randomString();
    private final int STAT_EFFECT_LEVEL = randomInt();

    private final Map<String, PassiveAbility> PASSIVE_ABILITIES = mapOf();
    private final String PASSIVE_ABILITY_ID = randomString();

    private final Map<String, ActiveAbility> ACTIVE_ABILITIES = mapOf();
    private final String ACTIVE_ABILITY_ID = randomString();

    private final Map<String, ReactiveAbility> REACTIVE_ABILITIES = mapOf();
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

    private TypeHandler<Character> handler;

    @Before
    public void setUp() {
        mockCharacterType = generateMockWithId(CharacterType.class, CHARACTER_TYPE_ID);

        mockClassification = generateMockWithId(CharacterClassification.class, CLASSIFICATION_ID);

        mockClassifications = generateMockList(mockClassification);

        mockPronouns = generateMockMap(pairOf(CASE, ARTICLE));

        mockImageAssetSet = generateMockWithId(ImageAssetSet.class, IMAGE_ASSET_SET_ID);

        mockCharacterAIType = generateMockWithId(CharacterAIType.class, AI_TYPE_ID);

        when(mockEquipmentSlots.representation()).thenReturn(
                mapOf(pairOf(EQUIPMENT_SLOT, EQUIPMENT_SLOT_ITEM)));

        when(mockInventory.representation()).thenReturn(listOf(INVENTORY_ITEM));

        when(mockVariableStatType.id()).thenReturn(VARIABLE_STAT_TYPE_ID);

        //noinspection unchecked
        mockGetVariableStatType = (Function<String, VariableStatisticType>) mock(Function.class);
        when(mockGetVariableStatType.apply(anyString())).thenReturn(mockVariableStatType);

        mockVariableStats =
                generateMockMap(pairOf(mockVariableStatType, VARIABLE_STAT_CURRENT_LEVEL));

        mockStatusEffectType = generateMockWithId(StatusEffectType.class, STATUS_EFFECT_TYPE_ID);

        when(mockStatEffects.representation()).thenReturn(
                mapOf(pairOf(mockStatusEffectType, STAT_EFFECT_LEVEL)));

        mockPassiveAbility = generateMockWithId(PassiveAbility.class, PASSIVE_ABILITY_ID);

        mockActiveAbility = generateMockWithId(ActiveAbility.class, ACTIVE_ABILITY_ID);

        mockReactiveAbility = generateMockWithId(ReactiveAbility.class, REACTIVE_ABILITY_ID);

        mockPassiveAbilities = generateMockList(mockPassiveAbility);

        mockActiveAbilities = generateMockList(mockActiveAbility);

        mockReactiveAbilities = generateMockList(mockReactiveAbility);

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

        when(mockCharacterFactory.make(any(), any(), any())).thenReturn(mockCharacter);

        CHARACTER_TYPES.put(CHARACTER_TYPE_ID, mockCharacterType);
        CLASSIFICATIONS.put(CLASSIFICATION_ID, mockClassification);
        IMAGE_ASSET_SETS.put(IMAGE_ASSET_SET_ID, mockImageAssetSet);
        AI_TYPES.put(AI_TYPE_ID, mockCharacterAIType);
        STATUS_EFFECT_TYPES.put(STATUS_EFFECT_TYPE_ID, mockStatusEffectType);
        PASSIVE_ABILITIES.put(PASSIVE_ABILITY_ID, mockPassiveAbility);
        ACTIVE_ABILITIES.put(ACTIVE_ABILITY_ID, mockActiveAbility);
        REACTIVE_ABILITIES.put(REACTIVE_ABILITY_ID, mockReactiveAbility);

        handler = new CharacterHandler(mockCharacterFactory, CHARACTER_TYPES::get,
                CLASSIFICATIONS::get, IMAGE_ASSET_SETS::get, AI_TYPES::get, EVENTS_HANDLER,
                mockGetVariableStatType, STATUS_EFFECT_TYPES::get, PASSIVE_ABILITIES::get,
                ACTIVE_ABILITIES::get, REACTIVE_ABILITIES::get, DATA_HANDLER, ITEM_HANDLER);
    }

    @Test
    public void testConstructorWithInvalidParams() {
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
    public void testWrite() {
        var output = handler.write(mockCharacter);

        assertEquals(WRITTEN_VALUE, output);
        verify(mockCharacter).uuid();
        verify(mockCharacter).type();
        verify(mockCharacterType).id();
        verify(mockClassifications).size();
        verify(mockClassifications).iterator();
        verify(mockClassification).id();
        verify(mockCharacter).getStance();
        verify(mockCharacter).getDirection();
        verify(mockCharacter).getImageAssetSet();
        verify(mockImageAssetSet).id();
        verify(mockCharacter).getAIType();
        verify(mockCharacterAIType).id();
        verify(mockCharacter).events();
        verify(EVENTS_HANDLER).write(CHARACTER_EVENTS);
        verify(mockCharacter).equipmentSlots();
        verify(mockEquipmentSlots).representation();
        verify(ITEM_HANDLER).write(EQUIPMENT_SLOT_ITEM);
        verify(mockCharacter).inventory();
        verify(ITEM_HANDLER).write(INVENTORY_ITEM);
        verify(mockCharacter).variableStatisticCurrentValuesRepresentation();
        verify(mockVariableStats).size();
        verify(mockVariableStatType).id();
        verify(mockCharacter).statusEffects();
        verify(mockStatEffects).representation();
        verify(mockStatusEffectType).id();
        verify(mockPassiveAbility).id();
        verify(mockActiveAbility).id();
        verify(mockReactiveAbility).id();
        verify(mockCharacter).getPlayerControlled();
        verify(mockCharacter).data();
        verify(DATA_HANDLER).write(DATA);
        verify(mockCharacter).getName();
    }

    @Test
    public void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> handler.write(null));
    }

    @Test
    public void testRead() {
        var output = handler.read(WRITTEN_VALUE);

        assertSame(mockCharacter, output);
        verify(DATA_HANDLER).read(DATA_WRITTEN);
        verify(mockCharacterFactory).make(mockCharacterType, UUID, DATA);
        verify(mockClassifications).add(mockClassification);
        verify(mockPronouns).put(CASE, ARTICLE);
        verify(mockCharacter).setStance(STANCE);
        verify(mockCharacter).setDirection(DIRECTION);
        verify(mockCharacter).setImageAssetSet(mockImageAssetSet);
        verify(mockCharacter).setAIType(mockCharacterAIType);
        verify(EVENTS_HANDLER).read(EVENTS_WRITTEN);
        verify(CHARACTER_EVENTS).copyAllTriggers(CHARACTER_EVENTS);
        verify(mockEquipmentSlots).addCharacterEquipmentSlot(EQUIPMENT_SLOT);
        verify(ITEM_HANDLER).read(EQUIPMENT_SLOT_ITEM_WRITTEN_VALUE);
        verify(mockEquipmentSlots).equipItemToSlot(EQUIPMENT_SLOT, EQUIPMENT_SLOT_ITEM);
        verify(ITEM_HANDLER).read(INVENTORY_ITEM_WRITTEN_VALUE);
        verify(mockInventory).add(INVENTORY_ITEM);
        verify(mockGetVariableStatType).apply(VARIABLE_STAT_TYPE_ID);
        verify(mockCharacter).setVariableStatisticCurrentValue(mockVariableStatType,
                VARIABLE_STAT_CURRENT_LEVEL);
        verify(mockStatEffects).setStatusEffectLevel(mockStatusEffectType, STAT_EFFECT_LEVEL);
        verify(mockPassiveAbilities).add(mockPassiveAbility);
        verify(mockActiveAbilities).add(mockActiveAbility);
        verify(mockReactiveAbilities).add(mockReactiveAbility);
        verify(mockCharacter).setPlayerControlled(true);
        verify(mockCharacter).setName(NAME);
    }

    @Test
    public void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> handler.read(null));
        assertThrows(IllegalArgumentException.class, () -> handler.read(""));
    }

    @Test
    public void testArchetype() {
        assertNotNull(handler.archetype());
        assertEquals(Character.class.getCanonicalName(), handler.archetype().getInterfaceName());
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        Character.class.getCanonicalName() + ">",
                handler.getInterfaceName());
    }
}

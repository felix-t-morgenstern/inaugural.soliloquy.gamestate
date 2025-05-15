package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.entities.CharacterImpl;
import inaugural.soliloquy.gamestate.persistence.CharacterHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
import static inaugural.soliloquy.tools.random.Random.*;
import static inaugural.soliloquy.tools.testing.Mock.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static soliloquy.specs.common.valueobjects.Pair.pairOf;
import static soliloquy.specs.gamestate.entities.CharacterEvents.CharacterEvent;

@ExtendWith(MockitoExtension.class)
public class CharacterHandlerTests {
    private final UUID UUID = java.util.UUID.randomUUID();

    private final String CHARACTER_TYPE_ID = randomString();
    private final LookupAndEntitiesWithId<CharacterType> MOCK_TYPE_AND_LOOKUP =
            generateMockLookupFunctionWithId(CharacterType.class, CHARACTER_TYPE_ID);
    private final CharacterType MOCK_TYPE = MOCK_TYPE_AND_LOOKUP.entities.getFirst();
    private final Function<String, CharacterType> MOCK_GET_CHARACTER_TYPE =
            MOCK_TYPE_AND_LOOKUP.lookup;

    private final String CASE = randomString();
    private final String ARTICLE = randomString();

    private final String STANCE = randomString();

    private final Direction DIRECTION = randomDirection();

    private final String CLASSIFICATION_ID = randomString();
    private final LookupAndEntitiesWithId<CharacterClassification> MOCK_CLASSIFICATION_AND_LOOKUP =
            generateMockLookupFunctionWithId(CharacterClassification.class, CLASSIFICATION_ID);
    private final CharacterClassification MOCK_CLASSIFICATION =
            MOCK_CLASSIFICATION_AND_LOOKUP.entities.getFirst();
    private final Function<String, CharacterClassification> MOCK_GET_CLASSIFICATION =
            MOCK_CLASSIFICATION_AND_LOOKUP.lookup;

    private final String IMAGE_ASSET_SET_ID = randomString();
    private final LookupAndEntitiesWithId<ImageAssetSet> MOCK_IMAGE_ASSET_SET_AND_LOOKUP =
            generateMockLookupFunctionWithId(ImageAssetSet.class, IMAGE_ASSET_SET_ID);
    private final ImageAssetSet MOCK_IMAGE_ASSET_SET =
            MOCK_IMAGE_ASSET_SET_AND_LOOKUP.entities.getFirst();
    private final Function<String, ImageAssetSet> MOCK_GET_IMAGE_ASSET_SET =
            MOCK_IMAGE_ASSET_SET_AND_LOOKUP.lookup;

    private final String AI_TYPE_ID = randomString();
    private final LookupAndEntitiesWithId<CharacterAIType> MOCK_AI_TYPE_AND_LOOKUP =
            generateMockLookupFunctionWithId(CharacterAIType.class, AI_TYPE_ID);
    private final CharacterAIType MOCK_AI_TYPE = MOCK_AI_TYPE_AND_LOOKUP.entities.getFirst();
    private final Function<String, CharacterAIType> MOCK_GET_AI_TYPE =
            MOCK_AI_TYPE_AND_LOOKUP.lookup;

    private final String CHARACTER_EVENT_ID = randomString();
    private final String CHARACTER_EVENT_TRIGGER = randomString();
    private final LookupAndEntitiesWithId<CharacterEvent> MOCK_CHARACTER_EVENT_AND_LOOKUP =
            generateMockLookupFunctionWithId(CharacterEvent.class, CHARACTER_EVENT_ID);
    private final CharacterEvent MOCK_CHARACTER_EVENT =
            MOCK_CHARACTER_EVENT_AND_LOOKUP.entities.getFirst();
    private final Function<String, CharacterEvent> MOCK_GET_CHARACTER_EVENT =
            MOCK_CHARACTER_EVENT_AND_LOOKUP.lookup;

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
    private final LookupAndEntitiesWithId<VariableStatisticType>
            MOCK_VARIABLE_STAT_TYPE_AND_LOOKUP =
            generateMockLookupFunctionWithId(VariableStatisticType.class, VARIABLE_STAT_TYPE_ID);
    private final VariableStatisticType MOCK_VARIABLE_STAT_TYPE =
            MOCK_VARIABLE_STAT_TYPE_AND_LOOKUP.entities.getFirst();
    private final Function<String, VariableStatisticType> MOCK_GET_VARIABLE_STAT_TYPE =
            MOCK_VARIABLE_STAT_TYPE_AND_LOOKUP.lookup;
    private final int VARIABLE_STAT_CURRENT_LEVEL = randomInt();

    private final String STATUS_EFFECT_TYPE_ID = randomString();
    private final LookupAndEntitiesWithId<StatusEffectType> MOCK_STATUS_EFFECT_TYPE_AND_LOOKUP =
            generateMockLookupFunctionWithId(StatusEffectType.class, STATUS_EFFECT_TYPE_ID);
    private final StatusEffectType MOCK_STATUS_EFFECT_TYPE =
            MOCK_STATUS_EFFECT_TYPE_AND_LOOKUP.entities.getFirst();
    private final Function<String, StatusEffectType> MOCK_GET_STATUS_EFFECT_TYPE =
            MOCK_STATUS_EFFECT_TYPE_AND_LOOKUP.lookup;
    private final int STAT_EFFECT_LEVEL = randomInt();

    private final String PASSIVE_ABILITY_ID = randomString();
    private final LookupAndEntitiesWithId<PassiveAbility> MOCK_PASSIVE_ABILITY_AND_LOOKUP =
            generateMockLookupFunctionWithId(PassiveAbility.class, PASSIVE_ABILITY_ID);
    private final PassiveAbility MOCK_PASSIVE_ABILITY =
            MOCK_PASSIVE_ABILITY_AND_LOOKUP.entities.getFirst();
    private final Function<String, PassiveAbility> MOCK_GET_PASSIVE_ABILITY =
            MOCK_PASSIVE_ABILITY_AND_LOOKUP.lookup;

    private final String ACTIVE_ABILITY_ID = randomString();
    private final LookupAndEntitiesWithId<ActiveAbility> MOCK_ACTIVE_ABILITY_AND_LOOKUP =
            generateMockLookupFunctionWithId(ActiveAbility.class, ACTIVE_ABILITY_ID);
    private final ActiveAbility MOCK_ACTIVE_ABILITY =
            MOCK_ACTIVE_ABILITY_AND_LOOKUP.entities.getFirst();
    private final Function<String, ActiveAbility> MOCK_GET_ACTIVE_ABILITY =
            MOCK_ACTIVE_ABILITY_AND_LOOKUP.lookup;

    private final String REACTIVE_ABILITY_ID = randomString();
    private final LookupAndEntitiesWithId<ReactiveAbility> MOCK_REACTIVE_ABILITY_AND_LOOKUP =
            generateMockLookupFunctionWithId(ReactiveAbility.class, REACTIVE_ABILITY_ID);
    private final ReactiveAbility MOCK_REACTIVE_ABILITY =
            MOCK_REACTIVE_ABILITY_AND_LOOKUP.entities.getFirst();
    private final Function<String, ReactiveAbility> MOCK_GET_REACTIVE_ABILITY =
            MOCK_REACTIVE_ABILITY_AND_LOOKUP.lookup;

    private final String DATA_WRITTEN = randomString();
    @SuppressWarnings("rawtypes")
    private final HandlerAndEntities<Map> MAP_HANDLER_AND_ENTITIES =
            generateMockHandlerAndEntities(Map.class, arrayOf(DATA_WRITTEN));
    @SuppressWarnings("rawtypes")
    private final TypeHandler<Map> MAP_HANDLER = MAP_HANDLER_AND_ENTITIES.handler;
    @SuppressWarnings({"unchecked"})
    private final Map<String, Object> MOCK_DATA =
            MAP_HANDLER_AND_ENTITIES.entities.get(DATA_WRITTEN);

    private final String NAME = randomString();

    private List<CharacterClassification> mockClassifications;
    private Map<String, String> mockPronouns;
    private List<PassiveAbility> mockPassiveAbilities;
    private List<ActiveAbility> mockActiveAbilities;
    private List<ReactiveAbility> mockReactiveAbilities;

    @Mock private CharacterEvents mockCharacterEvents;
    @Mock private CharacterEquipmentSlots mockEquipmentSlots;
    @Mock private CharacterInventory mockInventory;
    @Mock private Map<VariableStatisticType, Integer> mockVariableStats;
    @Mock private CharacterStatusEffects mockStatEffects;
    @Mock private Character mockCharacter;
    @Mock private CharacterFactory mockCharacterFactory;

    private final String WRITTEN_VALUE = String.format(
            "{\"uuid\":\"%s\",\"characterTypeId\":\"%s\",\"classifications\":[\"%s\"]," +
                    "\"pronouns\":[{\"key\":\"%s\",\"val\":\"%s\"}],\"stance\":\"%s\"," +
                    "\"direction\":%s,\"assetSetId\":\"%s\",\"aiTypeId\":\"%s\"," +
                    "\"events\":[{\"id\":\"%s\",\"trigger\":\"%s\"}]," +
                    "\"equipmentSlots\":[{\"key\":\"%s\",\"val\":\"%s\"}]," +
                    "\"inventoryItems\":[\"%s\"],\"variableStats\":[{\"type\":\"%s\"," +
                    "\"current\":%d}],\"statusEffects\":[{\"type\":\"%s\",\"value\":%d}]," +
                    "\"passiveAbilityIds\":[\"%s\"],\"activeAbilityIds\":[\"%s\"]," +
                    "\"reactiveAbilityIds\":[\"%s\"],\"isPlayerControlled\":true,\"data\":\"%s\"," +
                    "\"name\":\"%s\"}",
            UUID, CHARACTER_TYPE_ID, CLASSIFICATION_ID, CASE, ARTICLE, STANCE, DIRECTION.getValue(),
            IMAGE_ASSET_SET_ID, AI_TYPE_ID, CHARACTER_EVENT_ID, CHARACTER_EVENT_TRIGGER,
            EQUIPMENT_SLOT, EQUIPMENT_SLOT_ITEM_WRITTEN_VALUE, INVENTORY_ITEM_WRITTEN_VALUE,
            VARIABLE_STAT_TYPE_ID, VARIABLE_STAT_CURRENT_LEVEL, STATUS_EFFECT_TYPE_ID,
            STAT_EFFECT_LEVEL, PASSIVE_ABILITY_ID, ACTIVE_ABILITY_ID, REACTIVE_ABILITY_ID,
            DATA_WRITTEN, NAME);

    private TypeHandler<Character> handler;

    @BeforeEach
    public void setUp() {
        mockClassifications = generateMockList(MOCK_CLASSIFICATION);
        mockPronouns = generateMockMap(pairOf(CASE, ARTICLE));
        mockPassiveAbilities = generateMockList(MOCK_PASSIVE_ABILITY);
        mockActiveAbilities = generateMockList(MOCK_ACTIVE_ABILITY);
        mockReactiveAbilities = generateMockList(MOCK_REACTIVE_ABILITY);

        lenient().when(mockCharacterEvents.representation()).thenReturn(
                mapOf(pairOf(CHARACTER_EVENT_TRIGGER, listOf(MOCK_CHARACTER_EVENT))));

        lenient().when(mockEquipmentSlots.representation()).thenReturn(
                mapOf(pairOf(EQUIPMENT_SLOT, EQUIPMENT_SLOT_ITEM)));

        lenient().when(mockInventory.representation()).thenReturn(listOf(INVENTORY_ITEM));

        mockVariableStats =
                generateMockMap(pairOf(MOCK_VARIABLE_STAT_TYPE, VARIABLE_STAT_CURRENT_LEVEL));

        lenient().when(mockStatEffects.representation()).thenReturn(
                mapOf(pairOf(MOCK_STATUS_EFFECT_TYPE, STAT_EFFECT_LEVEL)));

        lenient().when(mockCharacter.uuid()).thenReturn(UUID);
        lenient().when(mockCharacter.data()).thenReturn(MOCK_DATA);
        lenient().when(mockCharacter.type()).thenReturn(MOCK_TYPE);
        lenient().when(mockCharacter.classifications()).thenReturn(mockClassifications);
        lenient().when(mockCharacter.pronouns()).thenReturn(mockPronouns);
        lenient().when(mockCharacter.getStance()).thenReturn(STANCE);
        lenient().when(mockCharacter.getDirection()).thenReturn(DIRECTION);
        lenient().when(mockCharacter.getImageAssetSet()).thenReturn(MOCK_IMAGE_ASSET_SET);
        lenient().when(mockCharacter.getAIType()).thenReturn(MOCK_AI_TYPE);
        lenient().when(mockCharacter.events()).thenReturn(mockCharacterEvents);
        lenient().when(mockCharacter.equipmentSlots()).thenReturn(mockEquipmentSlots);
        lenient().when(mockCharacter.inventory()).thenReturn(mockInventory);
        lenient().when(mockCharacter.variableStatisticCurrentValuesRepresentation())
                .thenReturn(mockVariableStats);
        lenient().when(mockCharacter.statusEffects()).thenReturn(mockStatEffects);
        lenient().when(mockCharacter.passiveAbilities()).thenReturn(mockPassiveAbilities);
        lenient().when(mockCharacter.activeAbilities()).thenReturn(mockActiveAbilities);
        lenient().when(mockCharacter.reactiveAbilities()).thenReturn(mockReactiveAbilities);
        lenient().when(mockCharacter.getPlayerControlled()).thenReturn(true);
        lenient().when(mockCharacter.getName()).thenReturn(NAME);

        lenient().when(mockCharacterFactory.make(any(), any(), any())).thenReturn(mockCharacter);

        handler = new CharacterHandler(mockCharacterFactory, MOCK_GET_CHARACTER_TYPE,
                MOCK_GET_CLASSIFICATION,
                MOCK_GET_IMAGE_ASSET_SET, MOCK_GET_AI_TYPE, MOCK_GET_CHARACTER_EVENT,
                MOCK_GET_VARIABLE_STAT_TYPE, MOCK_GET_STATUS_EFFECT_TYPE, MOCK_GET_PASSIVE_ABILITY,
                MOCK_GET_ACTIVE_ABILITY, MOCK_GET_REACTIVE_ABILITY, MAP_HANDLER, ITEM_HANDLER);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterHandler(null, MOCK_GET_CHARACTER_TYPE, MOCK_GET_CLASSIFICATION,
                        MOCK_GET_IMAGE_ASSET_SET, MOCK_GET_AI_TYPE, MOCK_GET_CHARACTER_EVENT,
                        MOCK_GET_VARIABLE_STAT_TYPE, MOCK_GET_STATUS_EFFECT_TYPE,
                        MOCK_GET_PASSIVE_ABILITY, MOCK_GET_ACTIVE_ABILITY,
                        MOCK_GET_REACTIVE_ABILITY, MAP_HANDLER, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterHandler(mockCharacterFactory, null, MOCK_GET_CLASSIFICATION,
                        MOCK_GET_IMAGE_ASSET_SET, MOCK_GET_AI_TYPE, MOCK_GET_CHARACTER_EVENT,
                        MOCK_GET_VARIABLE_STAT_TYPE, MOCK_GET_STATUS_EFFECT_TYPE,
                        MOCK_GET_PASSIVE_ABILITY, MOCK_GET_ACTIVE_ABILITY,
                        MOCK_GET_REACTIVE_ABILITY, MAP_HANDLER, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterHandler(mockCharacterFactory, MOCK_GET_CHARACTER_TYPE, null,
                        MOCK_GET_IMAGE_ASSET_SET, MOCK_GET_AI_TYPE, MOCK_GET_CHARACTER_EVENT,
                        MOCK_GET_VARIABLE_STAT_TYPE, MOCK_GET_STATUS_EFFECT_TYPE,
                        MOCK_GET_PASSIVE_ABILITY, MOCK_GET_ACTIVE_ABILITY,
                        MOCK_GET_REACTIVE_ABILITY, MAP_HANDLER, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterHandler(mockCharacterFactory, MOCK_GET_CHARACTER_TYPE,
                        MOCK_GET_CLASSIFICATION, null, MOCK_GET_AI_TYPE, MOCK_GET_CHARACTER_EVENT,
                        MOCK_GET_VARIABLE_STAT_TYPE, MOCK_GET_STATUS_EFFECT_TYPE,
                        MOCK_GET_PASSIVE_ABILITY, MOCK_GET_ACTIVE_ABILITY,
                        MOCK_GET_REACTIVE_ABILITY, MAP_HANDLER, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterHandler(mockCharacterFactory, MOCK_GET_CHARACTER_TYPE,
                        MOCK_GET_CLASSIFICATION, MOCK_GET_IMAGE_ASSET_SET, null,
                        MOCK_GET_CHARACTER_EVENT, MOCK_GET_VARIABLE_STAT_TYPE,
                        MOCK_GET_STATUS_EFFECT_TYPE, MOCK_GET_PASSIVE_ABILITY,
                        MOCK_GET_ACTIVE_ABILITY, MOCK_GET_REACTIVE_ABILITY, MAP_HANDLER,
                        ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterHandler(mockCharacterFactory, MOCK_GET_CHARACTER_TYPE,
                        MOCK_GET_CLASSIFICATION, MOCK_GET_IMAGE_ASSET_SET, MOCK_GET_AI_TYPE, null,
                        MOCK_GET_VARIABLE_STAT_TYPE, MOCK_GET_STATUS_EFFECT_TYPE,
                        MOCK_GET_PASSIVE_ABILITY, MOCK_GET_ACTIVE_ABILITY,
                        MOCK_GET_REACTIVE_ABILITY, MAP_HANDLER, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterHandler(mockCharacterFactory, MOCK_GET_CHARACTER_TYPE,
                        MOCK_GET_CLASSIFICATION, MOCK_GET_IMAGE_ASSET_SET, MOCK_GET_AI_TYPE, null,
                        MOCK_GET_VARIABLE_STAT_TYPE, MOCK_GET_STATUS_EFFECT_TYPE,
                        MOCK_GET_PASSIVE_ABILITY, MOCK_GET_ACTIVE_ABILITY,
                        MOCK_GET_REACTIVE_ABILITY, MAP_HANDLER, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterHandler(mockCharacterFactory, MOCK_GET_CHARACTER_TYPE,
                        MOCK_GET_CLASSIFICATION, MOCK_GET_IMAGE_ASSET_SET, MOCK_GET_AI_TYPE,
                        MOCK_GET_CHARACTER_EVENT, null, MOCK_GET_STATUS_EFFECT_TYPE,
                        MOCK_GET_PASSIVE_ABILITY, MOCK_GET_ACTIVE_ABILITY,
                        MOCK_GET_REACTIVE_ABILITY, MAP_HANDLER, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterHandler(mockCharacterFactory, MOCK_GET_CHARACTER_TYPE,
                        MOCK_GET_CLASSIFICATION, MOCK_GET_IMAGE_ASSET_SET, MOCK_GET_AI_TYPE,
                        MOCK_GET_CHARACTER_EVENT, MOCK_GET_VARIABLE_STAT_TYPE, null,
                        MOCK_GET_PASSIVE_ABILITY, MOCK_GET_ACTIVE_ABILITY,
                        MOCK_GET_REACTIVE_ABILITY, MAP_HANDLER, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterHandler(mockCharacterFactory, MOCK_GET_CHARACTER_TYPE,
                        MOCK_GET_CLASSIFICATION, MOCK_GET_IMAGE_ASSET_SET, MOCK_GET_AI_TYPE,
                        MOCK_GET_CHARACTER_EVENT, MOCK_GET_VARIABLE_STAT_TYPE,
                        MOCK_GET_STATUS_EFFECT_TYPE, null, MOCK_GET_ACTIVE_ABILITY,
                        MOCK_GET_REACTIVE_ABILITY, MAP_HANDLER, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterHandler(mockCharacterFactory, MOCK_GET_CHARACTER_TYPE,
                        MOCK_GET_CLASSIFICATION, MOCK_GET_IMAGE_ASSET_SET, MOCK_GET_AI_TYPE,
                        MOCK_GET_CHARACTER_EVENT, MOCK_GET_VARIABLE_STAT_TYPE,
                        MOCK_GET_STATUS_EFFECT_TYPE, MOCK_GET_PASSIVE_ABILITY, null,
                        MOCK_GET_REACTIVE_ABILITY, MAP_HANDLER, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterHandler(mockCharacterFactory, MOCK_GET_CHARACTER_TYPE,
                        MOCK_GET_CLASSIFICATION, MOCK_GET_IMAGE_ASSET_SET, MOCK_GET_AI_TYPE,
                        MOCK_GET_CHARACTER_EVENT, MOCK_GET_VARIABLE_STAT_TYPE,
                        MOCK_GET_STATUS_EFFECT_TYPE, MOCK_GET_PASSIVE_ABILITY,
                        MOCK_GET_ACTIVE_ABILITY, null, MAP_HANDLER, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterHandler(mockCharacterFactory, MOCK_GET_CHARACTER_TYPE,
                        MOCK_GET_CLASSIFICATION, MOCK_GET_IMAGE_ASSET_SET, MOCK_GET_AI_TYPE,
                        MOCK_GET_CHARACTER_EVENT, MOCK_GET_VARIABLE_STAT_TYPE,
                        MOCK_GET_STATUS_EFFECT_TYPE, MOCK_GET_PASSIVE_ABILITY,
                        MOCK_GET_ACTIVE_ABILITY, MOCK_GET_REACTIVE_ABILITY, null, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterHandler(mockCharacterFactory, MOCK_GET_CHARACTER_TYPE,
                        MOCK_GET_CLASSIFICATION, MOCK_GET_IMAGE_ASSET_SET, MOCK_GET_AI_TYPE,
                        MOCK_GET_CHARACTER_EVENT, MOCK_GET_VARIABLE_STAT_TYPE,
                        MOCK_GET_STATUS_EFFECT_TYPE, MOCK_GET_PASSIVE_ABILITY,
                        MOCK_GET_ACTIVE_ABILITY, MOCK_GET_REACTIVE_ABILITY, MAP_HANDLER, null));
    }

    @Test
    public void testTypeHandled() {
        assertEquals(CharacterImpl.class.getCanonicalName(), handler.typeHandled());
    }

    @Test
    public void testWrite() {
        var output = handler.write(mockCharacter);

        assertEquals(WRITTEN_VALUE, output);
        verify(mockCharacter).uuid();
        verify(mockCharacter).type();
        verify(MOCK_TYPE).id();
        verify(mockClassifications).size();
        verify(mockClassifications).iterator();
        verify(MOCK_CLASSIFICATION).id();
        verify(mockCharacter).getStance();
        verify(mockCharacter).getDirection();
        verify(mockCharacter).getImageAssetSet();
        verify(MOCK_IMAGE_ASSET_SET).id();
        verify(mockCharacter).getAIType();
        verify(MOCK_AI_TYPE).id();
        verify(mockCharacter).events();
        verify(mockCharacterEvents).representation();
        verify(mockCharacter).equipmentSlots();
        verify(mockEquipmentSlots).representation();
        verify(ITEM_HANDLER).write(EQUIPMENT_SLOT_ITEM);
        verify(mockCharacter).inventory();
        verify(ITEM_HANDLER).write(INVENTORY_ITEM);
        verify(mockCharacter).variableStatisticCurrentValuesRepresentation();
        verify(mockVariableStats).size();
        verify(MOCK_VARIABLE_STAT_TYPE).id();
        verify(mockCharacter).statusEffects();
        verify(mockStatEffects).representation();
        verify(MOCK_STATUS_EFFECT_TYPE).id();
        verify(MOCK_PASSIVE_ABILITY).id();
        verify(MOCK_ACTIVE_ABILITY).id();
        verify(MOCK_REACTIVE_ABILITY).id();
        verify(mockCharacter).getPlayerControlled();
        verify(mockCharacter).data();
        verify(MAP_HANDLER).write(MOCK_DATA);
        verify(mockCharacter).getName();
    }

    @Test
    public void testWriteWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> handler.write(null));
    }

    @Test
    public void testRead() {
        var output = handler.read(WRITTEN_VALUE);

        assertSame(mockCharacter, output);
        verify(MAP_HANDLER).read(DATA_WRITTEN);
        verify(MOCK_GET_CHARACTER_TYPE).apply(CHARACTER_TYPE_ID);
        verify(mockCharacterFactory).make(MOCK_TYPE, UUID, MOCK_DATA);
        verify(MOCK_GET_CLASSIFICATION).apply(CLASSIFICATION_ID);
        verify(mockClassifications).add(MOCK_CLASSIFICATION);
        verify(mockPronouns).put(CASE, ARTICLE);
        verify(mockCharacter).setStance(STANCE);
        verify(mockCharacter).setDirection(DIRECTION);
        verify(mockCharacter).setImageAssetSet(MOCK_IMAGE_ASSET_SET);
        verify(mockCharacter).setAIType(MOCK_AI_TYPE);
        verify(MOCK_GET_CHARACTER_EVENT).apply(CHARACTER_EVENT_ID);
        verify(mockCharacterEvents).addEvent(same(MOCK_CHARACTER_EVENT),
                eq(arrayOf(CHARACTER_EVENT_TRIGGER)));
        verify(mockEquipmentSlots).addCharacterEquipmentSlot(EQUIPMENT_SLOT);
        verify(ITEM_HANDLER).read(EQUIPMENT_SLOT_ITEM_WRITTEN_VALUE);
        verify(mockEquipmentSlots).equipItemToSlot(EQUIPMENT_SLOT, EQUIPMENT_SLOT_ITEM);
        verify(ITEM_HANDLER).read(INVENTORY_ITEM_WRITTEN_VALUE);
        verify(mockInventory).add(INVENTORY_ITEM);
        verify(MOCK_GET_VARIABLE_STAT_TYPE).apply(VARIABLE_STAT_TYPE_ID);
        verify(mockCharacter).setVariableStatisticCurrentValue(MOCK_VARIABLE_STAT_TYPE,
                VARIABLE_STAT_CURRENT_LEVEL);
        verify(MOCK_GET_STATUS_EFFECT_TYPE).apply(STATUS_EFFECT_TYPE_ID);
        verify(mockStatEffects).setStatusEffectLevel(MOCK_STATUS_EFFECT_TYPE, STAT_EFFECT_LEVEL);
        verify(MOCK_GET_PASSIVE_ABILITY).apply(PASSIVE_ABILITY_ID);
        verify(mockPassiveAbilities).add(MOCK_PASSIVE_ABILITY);
        verify(MOCK_GET_ACTIVE_ABILITY).apply(ACTIVE_ABILITY_ID);
        verify(mockActiveAbilities).add(MOCK_ACTIVE_ABILITY);
        verify(MOCK_GET_REACTIVE_ABILITY).apply(REACTIVE_ABILITY_ID);
        verify(mockReactiveAbilities).add(MOCK_REACTIVE_ABILITY);
        verify(mockCharacter).setPlayerControlled(true);
        verify(mockCharacter).setName(NAME);
    }

    @Test
    public void testReadWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> handler.read(null));
        assertThrows(IllegalArgumentException.class, () -> handler.read(""));
    }
}

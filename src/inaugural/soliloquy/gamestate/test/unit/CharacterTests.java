package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.Character;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.IEntityUuid;
import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.common.specs.IMap;
import soliloquy.gamestate.specs.*;
import soliloquy.ruleset.gameentities.abilities.specs.IActiveAbilityType;
import soliloquy.ruleset.gameentities.abilities.specs.IReactiveAbilityType;
import soliloquy.ruleset.gameentities.specs.ICharacterAIType;
import soliloquy.ruleset.gameentities.specs.ICharacterType;
import soliloquy.sprites.specs.ISpriteSet;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTests {
    private ICharacter _character;

    private final IEntityUuid ID = new EntityUuidStub();
    private final ICharacterType CHARACTER_TYPE = new CharacterTypeStub();
    private final IGenericParamsSet TRAITS = new GenericParamsSetStub();
    private final ICharacterAIType AI_TYPE_1 = new CharacterAITypeStub();
    private final ICharacterAIType AI_TYPE_2 = new CharacterAITypeStub();
    private final IGenericParamsSet AI_PARAMS = new GenericParamsSetStub();
    private final IMap<String, ICollection<ICharacterEvent>> EVENTS = new MapStub<>();
    private final ICharacterEquipmentSlotsFactory EQUIPMENT_SLOTS_FACTORY = new CharacterEquipmentSlotsFactoryStub();
    private final ICharacterInventoryFactory INVENTORY_FACTORY = new CharacterInventoryFactoryStub();
    private final IMap<String, ICharacterVitalAttribute> VITAL_ATTRIBUTES = new MapStub<>();
    private final IMap<String, ICharacterAttribute> ATTRIBUTES = new MapStub<>();
    private final ICharacterStatusEffects STATUS_EFFECTS = new CharacterStatusEffectsStub();
    private final IMap<String, ICharacterAbility<IActiveAbilityType>> ACTIVE_ABILITIES = new MapStub<>();
    private final IMap<String, ICharacterAbility<IReactiveAbilityType>> REACTIVE_ABILITIES = new MapStub<>();
    private final IMap<String, ICharacterAptitude> APTITUDES = new MapStub<>();
    private final IGenericParamsSet DATA = new GenericParamsSetStub();

    @BeforeEach
    void setUp() {

        _character = new Character(
                ID,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                TRAITS,
                AI_TYPE_1,
                AI_PARAMS,
                EVENTS,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                VITAL_ATTRIBUTES,
                ATTRIBUTES,
                STATUS_EFFECTS,
                ACTIVE_ABILITIES,
                REACTIVE_ABILITIES,
                APTITUDES,
                DATA);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new Character(
                null,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                TRAITS,
                AI_TYPE_1,
                AI_PARAMS,
                EVENTS,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                VITAL_ATTRIBUTES,
                ATTRIBUTES,
                STATUS_EFFECTS,
                ACTIVE_ABILITIES,
                REACTIVE_ABILITIES,
                APTITUDES,
                DATA));
        assertThrows(IllegalArgumentException.class, () -> new Character(
                ID,
                null,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                TRAITS,
                AI_TYPE_1,
                AI_PARAMS,
                EVENTS,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                VITAL_ATTRIBUTES,
                ATTRIBUTES,
                STATUS_EFFECTS,
                ACTIVE_ABILITIES,
                REACTIVE_ABILITIES,
                APTITUDES,
                DATA));
        assertThrows(IllegalArgumentException.class, () -> new Character(
                ID,
                CHARACTER_TYPE,
                null,
                new MapFactoryStub(),
                TRAITS,
                AI_TYPE_1,
                AI_PARAMS,
                EVENTS,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                VITAL_ATTRIBUTES,
                ATTRIBUTES,
                STATUS_EFFECTS,
                ACTIVE_ABILITIES,
                REACTIVE_ABILITIES,
                APTITUDES,
                DATA));
        assertThrows(IllegalArgumentException.class, () -> new Character(
                ID,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                null,
                TRAITS,
                AI_TYPE_1,
                AI_PARAMS,
                EVENTS,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                VITAL_ATTRIBUTES,
                ATTRIBUTES,
                STATUS_EFFECTS,
                ACTIVE_ABILITIES,
                REACTIVE_ABILITIES,
                APTITUDES,
                DATA));
        assertThrows(IllegalArgumentException.class, () -> new Character(
                ID,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                null,
                AI_TYPE_1,
                AI_PARAMS,
                EVENTS,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                VITAL_ATTRIBUTES,
                ATTRIBUTES,
                STATUS_EFFECTS,
                ACTIVE_ABILITIES,
                REACTIVE_ABILITIES,
                APTITUDES,
                DATA));
        assertThrows(IllegalArgumentException.class, () -> new Character(
                ID,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                TRAITS,
                null,
                AI_PARAMS,
                EVENTS,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                VITAL_ATTRIBUTES,
                ATTRIBUTES,
                STATUS_EFFECTS,
                ACTIVE_ABILITIES,
                REACTIVE_ABILITIES,
                APTITUDES,
                DATA));
        assertThrows(IllegalArgumentException.class, () -> new Character(
                ID,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                TRAITS,
                AI_TYPE_1,
                null,
                EVENTS,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                VITAL_ATTRIBUTES,
                ATTRIBUTES,
                STATUS_EFFECTS,
                ACTIVE_ABILITIES,
                REACTIVE_ABILITIES,
                APTITUDES,
                DATA));
        assertThrows(IllegalArgumentException.class, () -> new Character(
                ID,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                TRAITS,
                AI_TYPE_1,
                AI_PARAMS,
                null,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                VITAL_ATTRIBUTES,
                ATTRIBUTES,
                STATUS_EFFECTS,
                ACTIVE_ABILITIES,
                REACTIVE_ABILITIES,
                APTITUDES,
                DATA));
        assertThrows(IllegalArgumentException.class, () -> new Character(
                ID,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                TRAITS,
                AI_TYPE_1,
                AI_PARAMS,
                EVENTS,
                null,
                INVENTORY_FACTORY,
                VITAL_ATTRIBUTES,
                ATTRIBUTES,
                STATUS_EFFECTS,
                ACTIVE_ABILITIES,
                REACTIVE_ABILITIES,
                APTITUDES,
                DATA));
        assertThrows(IllegalArgumentException.class, () -> new Character(
                ID,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                TRAITS,
                AI_TYPE_1,
                AI_PARAMS,
                EVENTS,
                EQUIPMENT_SLOTS_FACTORY,
                null,
                VITAL_ATTRIBUTES,
                ATTRIBUTES,
                STATUS_EFFECTS,
                ACTIVE_ABILITIES,
                REACTIVE_ABILITIES,
                APTITUDES,
                DATA));
        assertThrows(IllegalArgumentException.class, () -> new Character(
                ID,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                TRAITS,
                AI_TYPE_1,
                AI_PARAMS,
                EVENTS,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                null,
                ATTRIBUTES,
                STATUS_EFFECTS,
                ACTIVE_ABILITIES,
                REACTIVE_ABILITIES,
                APTITUDES,
                DATA));
        assertThrows(IllegalArgumentException.class, () -> new Character(
                ID,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                TRAITS,
                AI_TYPE_1,
                AI_PARAMS,
                EVENTS,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                VITAL_ATTRIBUTES,
                null,
                STATUS_EFFECTS,
                ACTIVE_ABILITIES,
                REACTIVE_ABILITIES,
                APTITUDES,
                DATA));
        assertThrows(IllegalArgumentException.class, () -> new Character(
                ID,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                TRAITS,
                AI_TYPE_1,
                AI_PARAMS,
                EVENTS,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                VITAL_ATTRIBUTES,
                ATTRIBUTES,
                null,
                ACTIVE_ABILITIES,
                REACTIVE_ABILITIES,
                APTITUDES,
                DATA));
        assertThrows(IllegalArgumentException.class, () -> new Character(
                ID,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                TRAITS,
                AI_TYPE_1,
                AI_PARAMS,
                EVENTS,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                VITAL_ATTRIBUTES,
                ATTRIBUTES,
                STATUS_EFFECTS,
                null,
                REACTIVE_ABILITIES,
                APTITUDES,
                DATA));
        assertThrows(IllegalArgumentException.class, () -> new Character(
                ID,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                TRAITS,
                AI_TYPE_1,
                AI_PARAMS,
                EVENTS,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                VITAL_ATTRIBUTES,
                ATTRIBUTES,
                STATUS_EFFECTS,
                ACTIVE_ABILITIES,
                null,
                APTITUDES,
                DATA));
        assertThrows(IllegalArgumentException.class, () -> new Character(
                ID,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                TRAITS,
                AI_TYPE_1,
                AI_PARAMS,
                EVENTS,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                VITAL_ATTRIBUTES,
                ATTRIBUTES,
                STATUS_EFFECTS,
                ACTIVE_ABILITIES,
                REACTIVE_ABILITIES,
                null,
                DATA));
        assertThrows(IllegalArgumentException.class, () -> new Character(
                ID,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                TRAITS,
                AI_TYPE_1,
                AI_PARAMS,
                EVENTS,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                VITAL_ATTRIBUTES,
                ATTRIBUTES,
                STATUS_EFFECTS,
                ACTIVE_ABILITIES,
                REACTIVE_ABILITIES,
                APTITUDES,
                null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(ICharacter.class.getCanonicalName(), _character.getInterfaceName());
    }

    @Test
    void testCharacterType() {
        assertSame(CHARACTER_TYPE, _character.characterType());
    }

    @Test
    void testClassifications() {
        assertNotNull(_character.classifications());
    }

    @Test
    void testPronouns() {
        assertNotNull(_character.pronouns());
    }

    @Test
    void testTraits() {
        assertNotNull(_character.traits());
    }

    @Test
    void testSetAndGetStance() {
        final String stance = "CharacterStance";
        _character.setStance(stance);

        assertEquals(stance, _character.getStance());
    }

    @Test
    void testSetAndGetDirection() {
        String direction = "CharacterDirection";
        _character.setDirection(direction);

        assertEquals(direction, _character.getDirection());
    }

    @Test
    void testSetAndGetSpriteSet() {
        ISpriteSet spriteSet = new SpriteSetStub();
        _character.setSpriteSet(spriteSet);

        assertEquals(spriteSet, _character.getSpriteSet());
    }

    @Test
    void testSetAndGetAIType() {
        assertSame(AI_TYPE_1, _character.getAIType());

        _character.setAIType(AI_TYPE_2);

        assertSame(AI_TYPE_2, _character.getAIType());
    }

    @Test
    void testSetNullAIType() {
        assertThrows(IllegalArgumentException.class, () -> _character.setAIType(null));
    }

    @Test
    void testCharacterAIParams() {
        assertEquals(AI_PARAMS, _character.characterAIParams());
    }

    @Test
    void testCharacterEvents() {
        assertEquals(EVENTS, _character.characterEvents());
    }

    @Test
    void testEquipment() {
        assertNotNull(_character.equipmentSlots());
        assertSame(_character,
                ((CharacterEquipmentSlotsStub) _character.equipmentSlots()).CHARACTER);
    }

    @Test
    void testInventory() {
        assertNotNull(_character.inventory());
        assertSame(_character, ((CharacterInventoryStub) _character.inventory()).CHARACTER);
    }

    @Test
    void testVitalAttributes() {
        assertEquals(VITAL_ATTRIBUTES, _character.vitalAttributes());
    }

    @Test
    void testAttributes() {
        assertEquals(ATTRIBUTES, _character.attributes());
    }

    @Test
    void testStatusEffects() {
        assertEquals(STATUS_EFFECTS, _character.statusEffects());
    }

    @Test
    void testActiveAbilities() {
        assertEquals(ACTIVE_ABILITIES, _character.activeAbilities());
    }

    @Test
    void testReactiveAbilities() {
        assertEquals(REACTIVE_ABILITIES, _character.reactiveAbilities());
    }

    @Test
    void testAptitudes() {
        assertEquals(APTITUDES, _character.aptitudes());
    }

    @Test
    void testSetAndGetPlayerControlled() {
        _character.setPlayerControlled(true);
        assertTrue(_character.getPlayerControlled());

        _character.setPlayerControlled(false);
        assertFalse(_character.getPlayerControlled());
    }

    @Test
    void testSetAndGetHidden() {
        _character.setHidden(true);
        assertTrue(_character.getHidden());

        _character.setHidden(false);
        assertFalse(_character.getHidden());
    }

    @Test
    void testSetAndGetDead() {
        _character.setDead(true);
        assertTrue(_character.getDead());

        _character.setDead(false);
        assertFalse(_character.getDead());
    }

    @Test
    void testData() {
        assertEquals(DATA, _character.data());
    }

    @Test
    void testSetAndGetName() {
        String CHARACTER_NAME = "CharacterName";
        _character.setName(CHARACTER_NAME);

        assertEquals(CHARACTER_NAME, _character.getName());
    }

    @Test
    void testId() {
        assertEquals(ID, _character.id());
    }

    @Test
    void testAssignCharacterToTile() {
        ITile tile = new TileStub();
        assertNull(_character.tile());

        // NB: Character.TILE should NOT be exposed, and calling Character.assignCharacterToTile
        // violates the invariant condition; therefore, TileCharactersStub calls
        // Character.assignCharacterToTile indirectly, as it should be in production code
        tile.characters().addCharacter(_character);

        assertSame(tile, _character.tile());
    }

    @Test
    void testDelete() {
        ITile tile = new TileStub();
        tile.characters().addCharacter(_character);
        // TODO: After defining ICharacterEvents, test its deletion here
        CharacterEquipmentSlotsStub equipmentSlots =
                (CharacterEquipmentSlotsStub) _character.equipmentSlots();
        CharacterInventoryStub inventory = (CharacterInventoryStub) _character.inventory();
        CharacterVitalAttributeStub vitalAttribute = new CharacterVitalAttributeStub();
        _character.vitalAttributes().put("vitalAttribute", vitalAttribute);
        CharacterAttributeStub attribute = new CharacterAttributeStub();
        _character.attributes().put("attribute", attribute);
        CharacterStatusEffectsStub statusEffects =
                (CharacterStatusEffectsStub) _character.statusEffects();
        CharacterAbilityStub<IActiveAbilityType> characterActiveAbility =
                new CharacterAbilityStub<>();
        _character.activeAbilities().put("characterActiveAbility", characterActiveAbility);
        CharacterAbilityStub<IReactiveAbilityType> characterReactiveAbility =
                new CharacterAbilityStub<>();
        _character.reactiveAbilities().put("characterReactiveAbility", characterReactiveAbility);
        CharacterAptitudeStub aptitude = new CharacterAptitudeStub();
        _character.aptitudes().put("aptitude", aptitude);
        assertFalse(_character.isDeleted());
        assertFalse(((TileCharactersStub)tile.characters()).REMOVED_CHARACTERS
                .contains(_character));

        _character.delete();

        assertTrue(_character.isDeleted());
        assertTrue(((TileCharactersStub)tile.characters()).REMOVED_CHARACTERS.contains(_character));
        assertTrue(equipmentSlots._isDeleted);
        assertTrue(inventory._isDeleted);
        assertTrue(vitalAttribute._isDeleted);
        assertTrue(attribute._isDeleted);
        assertTrue(statusEffects._isDeleted);
        assertTrue(characterActiveAbility._isDeleted);
        assertTrue(characterReactiveAbility._isDeleted);
        assertTrue(aptitude._isDeleted);
    }

    @Test
    void testThrowsIllegalStateExceptionWhenDeleted() {
        _character.delete();

        assertThrows(IllegalStateException.class, () -> _character.characterType());
        assertThrows(IllegalStateException.class, () -> _character.classifications());
        assertThrows(IllegalStateException.class, () -> _character.pronouns());
        assertThrows(IllegalStateException.class, () -> _character.traits());
        assertThrows(IllegalStateException.class, () -> _character.tile());
        assertThrows(IllegalStateException.class, () -> _character.getStance());
        assertThrows(IllegalStateException.class, () -> _character.setStance(""));
        assertThrows(IllegalStateException.class, () -> _character.getDirection());
        assertThrows(IllegalStateException.class, () -> _character.setDirection(""));
        assertThrows(IllegalStateException.class, () -> _character.getSpriteSet());
        assertThrows(IllegalStateException.class, () -> _character.setSpriteSet(new SpriteSetStub()));
        assertThrows(IllegalStateException.class, () -> _character.getAIType());
        assertThrows(IllegalStateException.class, () -> _character.setAIType(null));
        assertThrows(IllegalStateException.class, () -> _character.characterAIParams());
        assertThrows(IllegalStateException.class, () -> _character.characterEvents());
        assertThrows(IllegalStateException.class, () -> _character.equipmentSlots());
        assertThrows(IllegalStateException.class, () -> _character.inventory());
        assertThrows(IllegalStateException.class, () -> _character.vitalAttributes());
        assertThrows(IllegalStateException.class, () -> _character.attributes());
        assertThrows(IllegalStateException.class, () -> _character.statusEffects());
        assertThrows(IllegalStateException.class, () -> _character.activeAbilities());
        assertThrows(IllegalStateException.class, () -> _character.reactiveAbilities());
        assertThrows(IllegalStateException.class, () -> _character.aptitudes());
        assertThrows(IllegalStateException.class, () -> _character.getPlayerControlled());
        assertThrows(IllegalStateException.class, () -> _character.setPlayerControlled(true));
        assertThrows(IllegalStateException.class, () -> _character.getHidden());
        assertThrows(IllegalStateException.class, () -> _character.setHidden(true));
        assertThrows(IllegalStateException.class, () -> _character.getDead());
        assertThrows(IllegalStateException.class, () -> _character.setDead(true));
        assertThrows(IllegalStateException.class, () -> _character.data());
        assertThrows(IllegalStateException.class, () -> _character.assignCharacterToTile(null));
        assertThrows(IllegalStateException.class, () -> _character.getName());
        assertThrows(IllegalStateException.class, () -> _character.setName(""));
        assertThrows(IllegalStateException.class, () -> _character.id());
        assertThrows(IllegalStateException.class, () -> _character.getInterfaceName());
    }

    @Test
    void testEnforceTileInvariant() {
        TileStub tile = new TileStub();
        tile.characters().addCharacter(_character);
        ((TileCharactersStub)tile.characters()).CHARACTERS.remove(_character);

        assertThrows(IllegalStateException.class, () -> _character.characterType());
        assertThrows(IllegalStateException.class, () -> _character.classifications());
        assertThrows(IllegalStateException.class, () -> _character.pronouns());
        assertThrows(IllegalStateException.class, () -> _character.traits());
        assertThrows(IllegalStateException.class, () -> _character.tile());
        assertThrows(IllegalStateException.class, () -> _character.getStance());
        assertThrows(IllegalStateException.class, () -> _character.setStance(""));
        assertThrows(IllegalStateException.class, () -> _character.getDirection());
        assertThrows(IllegalStateException.class, () -> _character.setDirection(""));
        assertThrows(IllegalStateException.class, () -> _character.getSpriteSet());
        assertThrows(IllegalStateException.class, () -> _character.setSpriteSet(new SpriteSetStub()));
        assertThrows(IllegalStateException.class, () -> _character.getAIType());
        assertThrows(IllegalStateException.class, () -> _character.setAIType(null));
        assertThrows(IllegalStateException.class, () -> _character.characterAIParams());
        assertThrows(IllegalStateException.class, () -> _character.characterEvents());
        assertThrows(IllegalStateException.class, () -> _character.equipmentSlots());
        assertThrows(IllegalStateException.class, () -> _character.inventory());
        assertThrows(IllegalStateException.class, () -> _character.vitalAttributes());
        assertThrows(IllegalStateException.class, () -> _character.attributes());
        assertThrows(IllegalStateException.class, () -> _character.statusEffects());
        assertThrows(IllegalStateException.class, () -> _character.activeAbilities());
        assertThrows(IllegalStateException.class, () -> _character.reactiveAbilities());
        assertThrows(IllegalStateException.class, () -> _character.aptitudes());
        assertThrows(IllegalStateException.class, () -> _character.getPlayerControlled());
        assertThrows(IllegalStateException.class, () -> _character.setPlayerControlled(true));
        assertThrows(IllegalStateException.class, () -> _character.getHidden());
        assertThrows(IllegalStateException.class, () -> _character.setHidden(true));
        assertThrows(IllegalStateException.class, () -> _character.getDead());
        assertThrows(IllegalStateException.class, () -> _character.setDead(true));
        assertThrows(IllegalStateException.class, () -> _character.data());
        assertThrows(IllegalStateException.class, () -> _character.delete());
        assertThrows(IllegalStateException.class, () -> _character.isDeleted());
        assertThrows(IllegalStateException.class, () -> _character.assignCharacterToTile(null));
        assertThrows(IllegalStateException.class, () -> _character.getName());
        assertThrows(IllegalStateException.class, () -> _character.setName(""));
        assertThrows(IllegalStateException.class, () -> _character.id());
        assertThrows(IllegalStateException.class, () -> _character.getInterfaceName());
    }

    @Test
    void testEnforceGameZoneInvariant() {
        TileStub tile = new TileStub();
        tile.characters().addCharacter(_character);
        ((GameZoneStub)TileStub.GAME_ZONE)._characters.removeByKey(_character.id());

        assertThrows(IllegalStateException.class, () -> _character.characterType());
        assertThrows(IllegalStateException.class, () -> _character.classifications());
        assertThrows(IllegalStateException.class, () -> _character.pronouns());
        assertThrows(IllegalStateException.class, () -> _character.traits());
        assertThrows(IllegalStateException.class, () -> _character.tile());
        assertThrows(IllegalStateException.class, () -> _character.getStance());
        assertThrows(IllegalStateException.class, () -> _character.setStance(""));
        assertThrows(IllegalStateException.class, () -> _character.getDirection());
        assertThrows(IllegalStateException.class, () -> _character.setDirection(""));
        assertThrows(IllegalStateException.class, () -> _character.getSpriteSet());
        assertThrows(IllegalStateException.class, () -> _character.setSpriteSet(new SpriteSetStub()));
        assertThrows(IllegalStateException.class, () -> _character.getAIType());
        assertThrows(IllegalStateException.class, () -> _character.setAIType(null));
        assertThrows(IllegalStateException.class, () -> _character.characterAIParams());
        assertThrows(IllegalStateException.class, () -> _character.characterEvents());
        assertThrows(IllegalStateException.class, () -> _character.equipmentSlots());
        assertThrows(IllegalStateException.class, () -> _character.inventory());
        assertThrows(IllegalStateException.class, () -> _character.vitalAttributes());
        assertThrows(IllegalStateException.class, () -> _character.attributes());
        assertThrows(IllegalStateException.class, () -> _character.statusEffects());
        assertThrows(IllegalStateException.class, () -> _character.activeAbilities());
        assertThrows(IllegalStateException.class, () -> _character.reactiveAbilities());
        assertThrows(IllegalStateException.class, () -> _character.aptitudes());
        assertThrows(IllegalStateException.class, () -> _character.getPlayerControlled());
        assertThrows(IllegalStateException.class, () -> _character.setPlayerControlled(true));
        assertThrows(IllegalStateException.class, () -> _character.getHidden());
        assertThrows(IllegalStateException.class, () -> _character.setHidden(true));
        assertThrows(IllegalStateException.class, () -> _character.getDead());
        assertThrows(IllegalStateException.class, () -> _character.setDead(true));
        assertThrows(IllegalStateException.class, () -> _character.data());
        assertThrows(IllegalStateException.class, () -> _character.delete());
        assertThrows(IllegalStateException.class, () -> _character.isDeleted());
        assertThrows(IllegalStateException.class, () -> _character.assignCharacterToTile(null));
        assertThrows(IllegalStateException.class, () -> _character.getName());
        assertThrows(IllegalStateException.class, () -> _character.setName(""));
        assertThrows(IllegalStateException.class, () -> _character.id());
        assertThrows(IllegalStateException.class, () -> _character.getInterfaceName());
    }
}

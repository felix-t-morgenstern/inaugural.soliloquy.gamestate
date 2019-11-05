package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterImpl;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.GenericParamsSetFactory;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.CharacterEquipmentSlotsFactory;
import soliloquy.specs.gamestate.factories.CharacterInventoryFactory;
import soliloquy.specs.gamestate.factories.CharacterStatusEffectsFactory;
import soliloquy.specs.ruleset.entities.CharacterAIType;
import soliloquy.specs.ruleset.entities.CharacterType;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbilityType;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbilityType;
import soliloquy.specs.sprites.entities.SpriteSet;

import static org.junit.jupiter.api.Assertions.*;

class CharacterImplTests {
    private Character _character;

    private final EntityUuid ID = new EntityUuidStub();
    private final CharacterType CHARACTER_TYPE = new CharacterTypeStub();
    private final CharacterAIType AI_TYPE = new CharacterAITypeStub();
    private final CharacterEquipmentSlotsFactory EQUIPMENT_SLOTS_FACTORY = new CharacterEquipmentSlotsFactoryStub();
    private final CharacterInventoryFactory INVENTORY_FACTORY = new CharacterInventoryFactoryStub();
    private final CharacterStatusEffectsFactory STATUS_EFFECTS_FACTORY = new CharacterStatusEffectsFactoryStub();
    private final GenericParamsSetFactory GENERIC_PARAMS_SET_FACTORY = new GenericParamsSetFactoryStub();

    @BeforeEach
    void setUp() {

        _character = new CharacterImpl(
                ID,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                STATUS_EFFECTS_FACTORY,
                GENERIC_PARAMS_SET_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new CharacterImpl(
                null,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                STATUS_EFFECTS_FACTORY,
                GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new CharacterImpl(
                ID,
                null,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                STATUS_EFFECTS_FACTORY,
                GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new CharacterImpl(
                ID,
                CHARACTER_TYPE,
                null,
                new MapFactoryStub(),
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                STATUS_EFFECTS_FACTORY,
                GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new CharacterImpl(
                ID,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                null,
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                STATUS_EFFECTS_FACTORY,
                GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new CharacterImpl(
                ID,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                null,
                INVENTORY_FACTORY,
                STATUS_EFFECTS_FACTORY,
                GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new CharacterImpl(
                ID,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                EQUIPMENT_SLOTS_FACTORY,
                null,
                STATUS_EFFECTS_FACTORY,
                GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new CharacterImpl(
                ID,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                null,
                GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new CharacterImpl(
                ID,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                STATUS_EFFECTS_FACTORY,
                null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(Character.class.getCanonicalName(), _character.getInterfaceName());
    }

    @Test

    void testEquals() {
        Character character2 = new CharacterImpl(
                ID,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                EQUIPMENT_SLOTS_FACTORY,
                INVENTORY_FACTORY,
                STATUS_EFFECTS_FACTORY,
                GENERIC_PARAMS_SET_FACTORY);
        assertEquals(_character, character2);
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
        SpriteSet spriteSet = new SpriteSetStub();
        _character.setSpriteSet(spriteSet);

        assertEquals(spriteSet, _character.getSpriteSet());
    }

    @Test
    void testSetAndGetAIType() {
        assertNull(_character.getAIType());

        _character.setAIType(AI_TYPE);

        assertSame(AI_TYPE, _character.getAIType());
    }

    @Test
    void testSetNullAIType() {
        assertThrows(IllegalArgumentException.class, () -> _character.setAIType(null));
    }

    @Test
    void testCharacterEvents() {
        assertNotNull(_character.events());
    }

    @Test
    void testEquipment() {
        assertSame(CharacterEquipmentSlotsFactoryStub.CHARACTER_EQUIPMENT_SLOTS,
                _character.equipmentSlots());
    }

    @Test
    void testInventory() {
        assertNotNull(_character.inventory());
    }

    @Test
    void testDepletableStatistics() {
        assertNotNull(_character.depletableStatistics());
    }

    @Test
    void testStatistics() {
        assertNotNull(_character.statistics());
    }

    @Test
    void testStatusEffects() {
        assertNotNull(_character.statusEffects());
    }

    @Test
    void testActiveAbilities() {
        assertNotNull(_character.activeAbilities());
    }

    @Test
    void testReactiveAbilities() {
        assertNotNull(_character.reactiveAbilities());
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
        assertSame(GenericParamsSetFactoryStub.GENERIC_PARAMS_SET, _character.data());
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
        Tile tile = new TileStub();
        assertNull(_character.tile());

        // NB: Character.TILE should NOT be exposed, and calling Character.assignCharacterToTile
        // violates the invariant condition; therefore, TileCharactersStub calls
        // Character.assignCharacterToTile indirectly, as it should be in production code
        tile.characters().add(_character);

        assertSame(tile, _character.tile());
    }

    @SuppressWarnings("unchecked")
    @Test
    void testDelete() {
        Tile tile = new TileStub();
        tile.characters().add(_character);
        CharacterEquipmentSlotsStub equipmentSlots =
                (CharacterEquipmentSlotsStub) _character.equipmentSlots();
        CharacterInventoryStub inventory = (CharacterInventoryStub) _character.inventory();
        CharacterDepletableStatisticStub depletableStatistic =
                new CharacterDepletableStatisticStub();
        _character.depletableStatistics().put("depletableStatistic", depletableStatistic);
        CharacterStatisticStub statistic = new CharacterStatisticStub();
        _character.statistics().put("statistic", statistic);
        CharacterStatusEffectsStub statusEffects =
                (CharacterStatusEffectsStub) _character.statusEffects();
        CharacterAbilityStub<ActiveAbilityType> characterActiveAbility =
                new CharacterAbilityStub<>();
        _character.activeAbilities().put("characterActiveAbility", characterActiveAbility);
        CharacterAbilityStub<ReactiveAbilityType> characterReactiveAbility =
                new CharacterAbilityStub<>();
        _character.reactiveAbilities().put("characterReactiveAbility", characterReactiveAbility);
        assertFalse(_character.isDeleted());
        assertFalse(((TileCharactersStub)tile.characters()).REMOVED_CHARACTERS
                .contains(_character));

        _character.delete();

        assertTrue(_character.isDeleted());
        Character removedCharacter =
                ((TileCharactersStub)tile.characters()).REMOVED_CHARACTERS.get(0);
        assertSame(_character, removedCharacter);
        assertTrue(equipmentSlots._isDeleted);
        assertTrue(inventory._isDeleted);
        assertTrue(depletableStatistic._isDeleted);
        assertTrue(statistic._isDeleted);
        assertTrue(statusEffects._isDeleted);
        assertTrue(characterActiveAbility._isDeleted);
        assertTrue(characterReactiveAbility._isDeleted);
    }

    @Test
    void testThrowsIllegalStateExceptionWhenDeleted() {
        _character.delete();

        assertThrows(IllegalStateException.class, () -> _character.characterType());
        assertThrows(IllegalStateException.class, () -> _character.classifications());
        assertThrows(IllegalStateException.class, () -> _character.pronouns());
        assertThrows(IllegalStateException.class, () -> _character.tile());
        assertThrows(IllegalStateException.class, () -> _character.getStance());
        assertThrows(IllegalStateException.class, () -> _character.setStance(""));
        assertThrows(IllegalStateException.class, () -> _character.getDirection());
        assertThrows(IllegalStateException.class, () -> _character.setDirection(""));
        assertThrows(IllegalStateException.class, () -> _character.getSpriteSet());
        assertThrows(IllegalStateException.class, () -> _character.setSpriteSet(new SpriteSetStub()));
        assertThrows(IllegalStateException.class, () -> _character.getAIType());
        assertThrows(IllegalStateException.class, () -> _character.setAIType(null));
        assertThrows(IllegalStateException.class, () -> _character.events());
        assertThrows(IllegalStateException.class, () -> _character.equipmentSlots());
        assertThrows(IllegalStateException.class, () -> _character.inventory());
        assertThrows(IllegalStateException.class, () -> _character.depletableStatistics());
        assertThrows(IllegalStateException.class, () -> _character.statistics());
        assertThrows(IllegalStateException.class, () -> _character.statusEffects());
        assertThrows(IllegalStateException.class, () -> _character.activeAbilities());
        assertThrows(IllegalStateException.class, () -> _character.reactiveAbilities());
        assertThrows(IllegalStateException.class, () -> _character.getPlayerControlled());
        assertThrows(IllegalStateException.class, () -> _character.setPlayerControlled(true));
        assertThrows(IllegalStateException.class, () -> _character.getHidden());
        assertThrows(IllegalStateException.class, () -> _character.setHidden(true));
        assertThrows(IllegalStateException.class, () -> _character.getDead());
        assertThrows(IllegalStateException.class, () -> _character.setDead(true));
        assertThrows(IllegalStateException.class, () -> _character.data());
        assertThrows(IllegalStateException.class, () -> _character.assignToTileAfterAddingToTileCharacters(null));
        assertThrows(IllegalStateException.class, () -> _character.getName());
        assertThrows(IllegalStateException.class, () -> _character.setName(""));
        assertThrows(IllegalStateException.class, () -> _character.id());
        assertThrows(IllegalStateException.class, () -> _character.getInterfaceName());
    }

    @Test
    void testEnforceTileInvariant() {
        TileStub tile = new TileStub();
        tile.characters().add(_character);
        ((TileCharactersStub)tile.characters()).CHARACTERS.remove(_character);

        assertThrows(IllegalStateException.class, () -> _character.characterType());
        assertThrows(IllegalStateException.class, () -> _character.classifications());
        assertThrows(IllegalStateException.class, () -> _character.pronouns());
        assertThrows(IllegalStateException.class, () -> _character.tile());
        assertThrows(IllegalStateException.class, () -> _character.getStance());
        assertThrows(IllegalStateException.class, () -> _character.setStance(""));
        assertThrows(IllegalStateException.class, () -> _character.getDirection());
        assertThrows(IllegalStateException.class, () -> _character.setDirection(""));
        assertThrows(IllegalStateException.class, () -> _character.getSpriteSet());
        assertThrows(IllegalStateException.class, () -> _character.setSpriteSet(new SpriteSetStub()));
        assertThrows(IllegalStateException.class, () -> _character.getAIType());
        assertThrows(IllegalStateException.class, () -> _character.setAIType(null));
        assertThrows(IllegalStateException.class, () -> _character.events());
        assertThrows(IllegalStateException.class, () -> _character.equipmentSlots());
        assertThrows(IllegalStateException.class, () -> _character.inventory());
        assertThrows(IllegalStateException.class, () -> _character.depletableStatistics());
        assertThrows(IllegalStateException.class, () -> _character.statistics());
        assertThrows(IllegalStateException.class, () -> _character.statusEffects());
        assertThrows(IllegalStateException.class, () -> _character.activeAbilities());
        assertThrows(IllegalStateException.class, () -> _character.reactiveAbilities());
        assertThrows(IllegalStateException.class, () -> _character.getPlayerControlled());
        assertThrows(IllegalStateException.class, () -> _character.setPlayerControlled(true));
        assertThrows(IllegalStateException.class, () -> _character.getHidden());
        assertThrows(IllegalStateException.class, () -> _character.setHidden(true));
        assertThrows(IllegalStateException.class, () -> _character.getDead());
        assertThrows(IllegalStateException.class, () -> _character.setDead(true));
        assertThrows(IllegalStateException.class, () -> _character.data());
        assertThrows(IllegalStateException.class, () -> _character.delete());
        assertThrows(IllegalStateException.class, () -> _character.isDeleted());
        assertThrows(IllegalStateException.class, () -> _character.assignToTileAfterAddingToTileCharacters(null));
        assertThrows(IllegalStateException.class, () -> _character.getName());
        assertThrows(IllegalStateException.class, () -> _character.setName(""));
        assertThrows(IllegalStateException.class, () -> _character.id());
        assertThrows(IllegalStateException.class, () -> _character.getInterfaceName());
    }
}

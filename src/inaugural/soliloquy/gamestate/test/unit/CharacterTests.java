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
import soliloquy.ruleset.gameentities.specs.ICharacterAIType;
import soliloquy.ruleset.gameentities.specs.ICharacterEvent;
import soliloquy.ruleset.gameentities.specs.ICharacterType;
import soliloquy.sprites.specs.ISpriteSet;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTests {
    private ICharacter _character;

    private final IEntityUuid ID = new EntityUuidStub();
    private final ICharacterType CHARACTER_TYPE = new CharacterTypeStub();
    private final IGenericParamsSet CHARACTER_TRAITS = new GenericParamsSetStub();
    private final String CHARACTER_AI_TYPE_1_ID = "characterAIType1Id";
    private final String CHARACTER_AI_TYPE_2_ID = "characterAIType2Id";
    private final ICharacterAIType CHARACTER_AI_TYPE_1 = new CharacterAITypeStub();
    private final ICharacterAIType CHARACTER_AI_TYPE_2 = new CharacterAITypeStub();
    private final IMap<String, ICharacterAIType> CHARACTER_AI_TYPES = new MapStub<>();
    private final IGenericParamsSet CHARACTER_AI_PARAMS = new GenericParamsSetStub();
    private final IMap<String, ICollection<ICharacterEvent>> CHARACTER_EVENTS = new MapStub<>();
    private final IMap<String, ICharacterEquipmentSlot> CHARACTER_EQUIPMENT_SLOTS = new MapStub<>();
    private final IMap<String, ICharacterVitalAttribute> CHARACTER_VITAL_ATTRIBUTES = new MapStub<>();
    private final IMap<Integer, IItem> CHARACTER_INVENTORY = new MapStub<>();
    private final IMap<String, ICharacterAttribute> CHARACTER_ATTRIBUTES = new MapStub<>();
    private final ICharacterStatusEffects CHARACTER_STATUS_EFFECTS = new CharacterStatusEffectsStub();
    private final IMap<String, ICharacterAbility> CHARACTER_ACTIVE_ABILITIES = new MapStub<>();
    private final IMap<String, ICharacterAbility> CHARACTER_REACTIVE_ABILITIES = new MapStub<>();
    private final IMap<String, ICharacterAptitude> CHARACTER_APTITUDES = new MapStub<>();
    private final IGenericParamsSet DATA = new GenericParamsSetStub();
    private final String CHARACTER_NAME = "CharacterName";

    @BeforeEach
    void setUp() {
        CHARACTER_AI_TYPES.put(CHARACTER_AI_TYPE_1_ID, CHARACTER_AI_TYPE_1);
        CHARACTER_AI_TYPES.put(CHARACTER_AI_TYPE_2_ID, CHARACTER_AI_TYPE_2);

        _character = new Character(ID, CHARACTER_TYPE, new CollectionFactoryStub(),
                new MapFactoryStub(), CHARACTER_TRAITS, CHARACTER_AI_TYPES, CHARACTER_AI_PARAMS,
                CHARACTER_EVENTS, CHARACTER_EQUIPMENT_SLOTS, CHARACTER_INVENTORY,
                CHARACTER_VITAL_ATTRIBUTES, CHARACTER_ATTRIBUTES, CHARACTER_STATUS_EFFECTS,
                CHARACTER_ACTIVE_ABILITIES, CHARACTER_REACTIVE_ABILITIES, CHARACTER_APTITUDES,
                DATA);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals("soliloquy.gamestate.specs.ICharacter", _character.getInterfaceName());
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
    void testSetAndGetTile() {
        ITile tile = new TileStub();
        _character.setTile(tile);

        assertSame(tile, _character.getTile());
    }

    @Test
    void testSetAndGetTileWithZIndex() {
        ITile tile = new TileStub();
        _character.setTile(tile, 123);

        assertSame(tile, _character.getTile());
        assertEquals((Integer) 123, tile.getCharacters().get(_character));
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
        String stance = "CharacterStance";
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
    void testSetInvalidAITypeId() {
        assertThrows(IllegalArgumentException.class, () -> _character.setAITypeId("InvalidId"));
    }

    @Test
    void testSetAndGetAITypeId() {
        _character.setAITypeId(CHARACTER_AI_TYPE_2_ID);

        assertEquals(CHARACTER_AI_TYPE_2_ID, _character.getAITypeId());
    }

    @Test
    void testCharacterAIParams() {
        assertEquals(CHARACTER_AI_PARAMS, _character.characterAIParams());
    }

    @Test
    void testCharacterEvents() {
        assertEquals(CHARACTER_EVENTS, _character.characterEvents());
    }

    @Test
    void testEquipment() {
        assertEquals(CHARACTER_EQUIPMENT_SLOTS, _character.equipment());
    }

    @Test
    void testInventory() {
        assertEquals(CHARACTER_INVENTORY, _character.inventory());
    }

    @Test
    void testVitalAttributes() {
        assertEquals(CHARACTER_VITAL_ATTRIBUTES, _character.vitalAttributes());
    }

    @Test
    void testAttributes() {
        assertEquals(CHARACTER_ATTRIBUTES, _character.attributes());
    }

    @Test
    void testStatusEffects() {
        assertEquals(CHARACTER_STATUS_EFFECTS, _character.statusEffects());
    }

    @Test
    void testActiveAbilities() {
        assertEquals(CHARACTER_ACTIVE_ABILITIES, _character.activeAbilities());
    }

    @Test
    void testReactiveAbilities() {
        assertEquals(CHARACTER_REACTIVE_ABILITIES, _character.reactiveAbilities());
    }

    @Test
    void testAptitudes() {
        assertEquals(CHARACTER_APTITUDES, _character.aptitudes());
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
    void testGameZone() {
        _character.setTile(new TileStub());
        assertEquals(TileStub.GAME_ZONE, _character.gameZone());
    }

    @Test
    void testData() {
        assertEquals(DATA, _character.data());
    }

    @Test
    void testSetAndGetName() {
        _character.setName(CHARACTER_NAME);

        assertEquals(CHARACTER_NAME, _character.getName());
    }

    @Test
    void testId() {
        assertEquals(ID, _character.id());
    }

    @Test
    void testDelete() {
        ITile tile = new TileStub();
        IGameZone gameZone = TileStub.GAME_ZONE;
        IEntityUuid id = _character.id();
        _character.setTile(tile);
        assertFalse(_character.isDeleted());
        assertEquals(gameZone, _character.gameZone());
        assertEquals(_character, _character.gameZone().getCharacters().get(_character.id()));
        assertTrue(_character.getTile().getCharacters().containsKey(_character));

        _character.delete();

        assertTrue(_character.isDeleted());
        assertNull(gameZone.getCharacters().get(id));
        assertFalse(tile.getCharacters().containsKey(_character));
    }

    @Test
    void testThrowsIllegalStateExceptionWhenDeleted() {
        _character.delete();

        assertThrows(IllegalStateException.class, () -> _character.characterType());
        assertThrows(IllegalStateException.class, () -> _character.classifications());
        assertThrows(IllegalStateException.class, () -> _character.getTile());
        assertThrows(IllegalStateException.class, () -> _character.setTile(new TileStub()));
        assertThrows(IllegalStateException.class, () -> _character.setTile(new TileStub(), 0));
        assertThrows(IllegalStateException.class, () -> _character.pronouns());
        assertThrows(IllegalStateException.class, () -> _character.traits());
        assertThrows(IllegalStateException.class, () -> _character.getStance());
        assertThrows(IllegalStateException.class, () -> _character.setStance(""));
        assertThrows(IllegalStateException.class, () -> _character.getDirection());
        assertThrows(IllegalStateException.class, () -> _character.setDirection(""));
        assertThrows(IllegalStateException.class, () -> _character.getSpriteSet());
        assertThrows(IllegalStateException.class, () -> _character.setSpriteSet(new SpriteSetStub()));
        assertThrows(IllegalStateException.class, () -> _character.getAITypeId());
        assertThrows(IllegalStateException.class, () -> _character.setAITypeId(CHARACTER_AI_TYPE_1_ID));
        assertThrows(IllegalStateException.class, () -> _character.characterAIParams());
        assertThrows(IllegalStateException.class, () -> _character.characterEvents());
        assertThrows(IllegalStateException.class, () -> _character.equipment());
        assertThrows(IllegalStateException.class, () -> _character.inventory());
        assertThrows(IllegalStateException.class, () -> _character.vitalAttributes());
        assertThrows(IllegalStateException.class, () -> _character.attributes());
        assertThrows(IllegalStateException.class, () -> _character.statusEffects());
        assertThrows(IllegalStateException.class, () -> _character.activeAbilities());
        assertThrows(IllegalStateException.class, () -> _character.aptitudes());
        assertThrows(IllegalStateException.class, () -> _character.getPlayerControlled());
        assertThrows(IllegalStateException.class, () -> _character.setPlayerControlled(true));
        assertThrows(IllegalStateException.class, () -> _character.getHidden());
        assertThrows(IllegalStateException.class, () -> _character.setHidden(true));
        assertThrows(IllegalStateException.class, () -> _character.getDead());
        assertThrows(IllegalStateException.class, () -> _character.setDead(true));
        assertThrows(IllegalStateException.class, () -> _character.gameZone());
        assertThrows(IllegalStateException.class, () -> _character.data());
        assertThrows(IllegalStateException.class, () -> _character.getName());
        assertThrows(IllegalStateException.class, () -> _character.setName(""));
        assertThrows(IllegalStateException.class, () -> _character.id());
        assertThrows(IllegalStateException.class, () -> _character.read("", false));
        assertThrows(IllegalStateException.class, () -> _character.write());
        assertThrows(IllegalStateException.class, () -> _character.getInterfaceName());
    }

    @Test
    void testEnforceGameZoneInvariant() {
        TileStub tile = new TileStub();
        _character.setTile(tile);
        tile._characters.removeByKey(_character);

        assertThrows(IllegalStateException.class, () -> _character.characterType());
        assertThrows(IllegalStateException.class, () -> _character.classifications());
        assertThrows(IllegalStateException.class, () -> _character.getTile());
        assertThrows(IllegalStateException.class, () -> _character.setTile(new TileStub()));
        assertThrows(IllegalStateException.class, () -> _character.setTile(new TileStub(), 0));
        assertThrows(IllegalStateException.class, () -> _character.pronouns());
        assertThrows(IllegalStateException.class, () -> _character.traits());
        assertThrows(IllegalStateException.class, () -> _character.getStance());
        assertThrows(IllegalStateException.class, () -> _character.setStance(""));
        assertThrows(IllegalStateException.class, () -> _character.getDirection());
        assertThrows(IllegalStateException.class, () -> _character.setDirection(""));
        assertThrows(IllegalStateException.class, () -> _character.getSpriteSet());
        assertThrows(IllegalStateException.class, () -> _character.setSpriteSet(new SpriteSetStub()));
        assertThrows(IllegalStateException.class, () -> _character.getAITypeId());
        assertThrows(IllegalStateException.class, () -> _character.setAITypeId(CHARACTER_AI_TYPE_1_ID));
        assertThrows(IllegalStateException.class, () -> _character.characterAIParams());
        assertThrows(IllegalStateException.class, () -> _character.characterEvents());
        assertThrows(IllegalStateException.class, () -> _character.equipment());
        assertThrows(IllegalStateException.class, () -> _character.inventory());
        assertThrows(IllegalStateException.class, () -> _character.vitalAttributes());
        assertThrows(IllegalStateException.class, () -> _character.attributes());
        assertThrows(IllegalStateException.class, () -> _character.statusEffects());
        assertThrows(IllegalStateException.class, () -> _character.activeAbilities());
        assertThrows(IllegalStateException.class, () -> _character.aptitudes());
        assertThrows(IllegalStateException.class, () -> _character.getPlayerControlled());
        assertThrows(IllegalStateException.class, () -> _character.setPlayerControlled(true));
        assertThrows(IllegalStateException.class, () -> _character.getHidden());
        assertThrows(IllegalStateException.class, () -> _character.setHidden(true));
        assertThrows(IllegalStateException.class, () -> _character.getDead());
        assertThrows(IllegalStateException.class, () -> _character.setDead(true));
        assertThrows(IllegalStateException.class, () -> _character.gameZone());
        assertThrows(IllegalStateException.class, () -> _character.data());
        assertThrows(IllegalStateException.class, () -> _character.delete());
        assertThrows(IllegalStateException.class, () -> _character.isDeleted());
        assertThrows(IllegalStateException.class, () -> _character.getName());
        assertThrows(IllegalStateException.class, () -> _character.setName(""));
        assertThrows(IllegalStateException.class, () -> _character.id());
        assertThrows(IllegalStateException.class, () -> _character.read("", false));
        assertThrows(IllegalStateException.class, () -> _character.write());
        assertThrows(IllegalStateException.class, () -> _character.getInterfaceName());
    }
}

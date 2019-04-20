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
    private final ICharacterAIType CHARACTER_AI_TYPE_1 = new CharacterAITypeStub();
    private final ICharacterAIType CHARACTER_AI_TYPE_2 = new CharacterAITypeStub();
    private final IMap<String, ICharacterAIType> CHARACTER_AI_TYPES = new MapStub<>();
    private final IGenericParamsSet CHARACTER_AI_PARAMS = new GenericParamsSetStub();
    private final IMap<String, ICollection<ICharacterEvent>> CHARACTER_EVENTS = new MapStub<>();
    private final IMap<String, ICharacterVitalAttribute> CHARACTER_VITAL_ATTRIBUTES = new MapStub<>();
    private final IMap<String, ICharacterAttribute> CHARACTER_ATTRIBUTES = new MapStub<>();
    private final ICharacterStatusEffects CHARACTER_STATUS_EFFECTS = new CharacterStatusEffectsStub();
    private final IMap<String, ICharacterAbility> CHARACTER_ACTIVE_ABILITIES = new MapStub<>();
    private final IMap<String, ICharacterAbility> CHARACTER_REACTIVE_ABILITIES = new MapStub<>();
    private final IMap<String, ICharacterAptitude> CHARACTER_APTITUDES = new MapStub<>();
    private final IGenericParamsSet DATA = new GenericParamsSetStub();

    @BeforeEach
    void setUp() {
        String CHARACTER_AI_TYPE_1_ID = "characterAIType1Id";
        CHARACTER_AI_TYPES.put(CHARACTER_AI_TYPE_1_ID, CHARACTER_AI_TYPE_1);
        String CHARACTER_AI_TYPE_2_ID = "characterAIType2Id";
        CHARACTER_AI_TYPES.put(CHARACTER_AI_TYPE_2_ID, CHARACTER_AI_TYPE_2);

        _character = new Character(
                ID,
                CHARACTER_TYPE,
                new CollectionFactoryStub(),
                new MapFactoryStub(),
                CHARACTER_TRAITS,
                CHARACTER_AI_PARAMS,
                CHARACTER_EVENTS,
                CHARACTER_VITAL_ATTRIBUTES,
                CHARACTER_ATTRIBUTES,
                CHARACTER_STATUS_EFFECTS,
                CHARACTER_ACTIVE_ABILITIES,
                CHARACTER_REACTIVE_ABILITIES,
                CHARACTER_APTITUDES, DATA);
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
    void testSetInvalidAIType() {
        // TODO: Implement and test!
    }

    @Test
    void testSetAndGetAIType() {
        // TODO: Implement and test!
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
        // TODO: Implement and test!
    }

    @Test
    void testInventory() {
        // TODO: Implement and test!
    }

    @Test
    void testAddAndRemoveItemFromInventory() {
        // TODO: Write this test!!!
        // TODO: Write this test!!!
        // TODO: Write this test!!!
        // TODO: Write this test!!!
        // TODO: Write this test!!!
        // TODO: Write this test!!!
        // TODO: Write this test!!!
        // TODO: Write this test!!!
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
        // TODO: FIX THIS TEST!!!
        // TODO: FIX THIS TEST!!!
        // TODO: FIX THIS TEST!!!
        // TODO: FIX THIS TEST!!!
        // TODO: FIX THIS TEST!!!
        //new TileStub().characters().addCharacter(_character);
        //assertEquals(TileStub.GAME_ZONE, _character.gameZone());
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
    void testDelete() {
        // TODO: FIX THIS TEST!!!
        // TODO: FIX THIS TEST!!!
        // TODO: FIX THIS TEST!!!
        // TODO: FIX THIS TEST!!!
        // TODO: FIX THIS TEST!!!
//        ITile tile = new TileStub();
//        IGameZone gameZone = TileStub.GAME_ZONE;
//        IEntityUuid id = _character.id();
//        tile.characters().addCharacter(_character);
//        assertFalse(_character.isDeleted());
//        assertEquals(gameZone, _character.gameZone());
//        assertEquals(_character, _character.gameZone().getCharactersRepresentation()
//                .get(_character.id()));
//        assertTrue(_character.tile().characters().containsCharacter(_character));
//
//        _character.delete();
//
//        assertTrue(_character.isDeleted());
//        assertNull(gameZone.getCharactersRepresentation().get(id));
//        assertFalse(tile.characters().containsCharacter(_character));
    }

    @Test
    void testThrowsIllegalStateExceptionWhenDeleted() {
        // TODO: Add other methods here, and ensure that the invariant is called!
        _character.delete();

        assertThrows(IllegalStateException.class, () -> _character.characterType());
        assertThrows(IllegalStateException.class, () -> _character.classifications());
        assertThrows(IllegalStateException.class, () -> _character.pronouns());
        assertThrows(IllegalStateException.class, () -> _character.traits());
        assertThrows(IllegalStateException.class, () -> _character.getStance());
        assertThrows(IllegalStateException.class, () -> _character.setStance(""));
        assertThrows(IllegalStateException.class, () -> _character.getDirection());
        assertThrows(IllegalStateException.class, () -> _character.setDirection(""));
        assertThrows(IllegalStateException.class, () -> _character.getSpriteSet());
        assertThrows(IllegalStateException.class, () -> _character.setSpriteSet(new SpriteSetStub()));
        assertThrows(IllegalStateException.class, () -> _character.characterAIParams());
        assertThrows(IllegalStateException.class, () -> _character.characterEvents());
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
    void testEnforceTileInvariant() {
        // TODO: Add other methods here, and ensure that the invariant is called!
        TileStub tile = new TileStub();
        // TODO: FIX THIS TEST!!!
        // TODO: FIX THIS TEST!!!
        // TODO: FIX THIS TEST!!!
        // TODO: FIX THIS TEST!!!
        // TODO: FIX THIS TEST!!!
        return;
        //tile.characters().addCharacter(_character);
//        tile._characters.removeByKey(_character);
//
//        assertThrows(IllegalStateException.class, () -> _character.characterType());
//        assertThrows(IllegalStateException.class, () -> _character.classifications());
//        assertThrows(IllegalStateException.class, () -> _character.pronouns());
//        assertThrows(IllegalStateException.class, () -> _character.traits());
//        assertThrows(IllegalStateException.class, () -> _character.getStance());
//        assertThrows(IllegalStateException.class, () -> _character.setStance(""));
//        assertThrows(IllegalStateException.class, () -> _character.getDirection());
//        assertThrows(IllegalStateException.class, () -> _character.setDirection(""));
//        assertThrows(IllegalStateException.class, () -> _character.getSpriteSet());
//        assertThrows(IllegalStateException.class, () -> _character.setSpriteSet(new SpriteSetStub()));
//        assertThrows(IllegalStateException.class, () -> _character.characterAIParams());
//        assertThrows(IllegalStateException.class, () -> _character.characterEvents());
//        assertThrows(IllegalStateException.class, () -> _character.vitalAttributes());
//        assertThrows(IllegalStateException.class, () -> _character.attributes());
//        assertThrows(IllegalStateException.class, () -> _character.statusEffects());
//        assertThrows(IllegalStateException.class, () -> _character.activeAbilities());
//        assertThrows(IllegalStateException.class, () -> _character.aptitudes());
//        assertThrows(IllegalStateException.class, () -> _character.getPlayerControlled());
//        assertThrows(IllegalStateException.class, () -> _character.setPlayerControlled(true));
//        assertThrows(IllegalStateException.class, () -> _character.getHidden());
//        assertThrows(IllegalStateException.class, () -> _character.setHidden(true));
//        assertThrows(IllegalStateException.class, () -> _character.getDead());
//        assertThrows(IllegalStateException.class, () -> _character.setDead(true));
//        assertThrows(IllegalStateException.class, () -> _character.gameZone());
//        assertThrows(IllegalStateException.class, () -> _character.data());
//        assertThrows(IllegalStateException.class, () -> _character.delete());
//        assertThrows(IllegalStateException.class, () -> _character.isDeleted());
//        assertThrows(IllegalStateException.class, () -> _character.getName());
//        assertThrows(IllegalStateException.class, () -> _character.setName(""));
//        assertThrows(IllegalStateException.class, () -> _character.id());
//        assertThrows(IllegalStateException.class, () -> _character.read("", false));
//        assertThrows(IllegalStateException.class, () -> _character.write());
//        assertThrows(IllegalStateException.class, () -> _character.getInterfaceName());
    }

    @Test
    void testEnforceGameZoneInvariant() {
        // TODO: FIX THIS TEST!!!
        // TODO: FIX THIS TEST!!!
        // TODO: FIX THIS TEST!!!
        // TODO: FIX THIS TEST!!!
        // TODO: FIX THIS TEST!!!
        // TODO: Add other methods here, and ensure that the invariant is called!
//        TileStub tile = new TileStub();
//        tile.characters().addCharacter(_character);
//        ((GameZoneStub)TileStub.GAME_ZONE)._characters.removeByKey(_character.id());
//
//        assertThrows(IllegalStateException.class, () -> _character.characterType());
//        assertThrows(IllegalStateException.class, () -> _character.classifications());
//        assertThrows(IllegalStateException.class, () -> _character.pronouns());
//        assertThrows(IllegalStateException.class, () -> _character.traits());
//        assertThrows(IllegalStateException.class, () -> _character.getStance());
//        assertThrows(IllegalStateException.class, () -> _character.setStance(""));
//        assertThrows(IllegalStateException.class, () -> _character.getDirection());
//        assertThrows(IllegalStateException.class, () -> _character.setDirection(""));
//        assertThrows(IllegalStateException.class, () -> _character.getSpriteSet());
//        assertThrows(IllegalStateException.class, () -> _character.setSpriteSet(new SpriteSetStub()));
//        assertThrows(IllegalStateException.class, () -> _character.characterAIParams());
//        assertThrows(IllegalStateException.class, () -> _character.characterEvents());
//        assertThrows(IllegalStateException.class, () -> _character.vitalAttributes());
//        assertThrows(IllegalStateException.class, () -> _character.attributes());
//        assertThrows(IllegalStateException.class, () -> _character.statusEffects());
//        assertThrows(IllegalStateException.class, () -> _character.activeAbilities());
//        assertThrows(IllegalStateException.class, () -> _character.aptitudes());
//        assertThrows(IllegalStateException.class, () -> _character.getPlayerControlled());
//        assertThrows(IllegalStateException.class, () -> _character.setPlayerControlled(true));
//        assertThrows(IllegalStateException.class, () -> _character.getHidden());
//        assertThrows(IllegalStateException.class, () -> _character.setHidden(true));
//        assertThrows(IllegalStateException.class, () -> _character.getDead());
//        assertThrows(IllegalStateException.class, () -> _character.setDead(true));
//        assertThrows(IllegalStateException.class, () -> _character.gameZone());
//        assertThrows(IllegalStateException.class, () -> _character.data());
//        assertThrows(IllegalStateException.class, () -> _character.delete());
//        assertThrows(IllegalStateException.class, () -> _character.isDeleted());
//        assertThrows(IllegalStateException.class, () -> _character.getName());
//        assertThrows(IllegalStateException.class, () -> _character.setName(""));
//        assertThrows(IllegalStateException.class, () -> _character.id());
//        assertThrows(IllegalStateException.class, () -> _character.read("", false));
//        assertThrows(IllegalStateException.class, () -> _character.write());
//        assertThrows(IllegalStateException.class, () -> _character.getInterfaceName());
    }
}

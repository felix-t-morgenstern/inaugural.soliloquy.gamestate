package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterFactory;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.common.specs.*;
import soliloquy.gamestate.specs.*;
import soliloquy.ruleset.gameentities.specs.ICharacterType;

import static org.junit.jupiter.api.Assertions.*;

class CharacterFactoryTests {
    private final IEntityUuidFactory ENTITY_UUID_FACTORY = new EntityUuidFactoryStub();
    private final ICollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    private final IMapFactory MAP_FACTORY = new MapFactoryStub();
    private final ICharacterEquipmentSlotsFactory CHARACTER_EQUIPMENT_SLOT_FACTORY =
            new CharacterEquipmentSlotsFactoryStub();
    private final ICharacterInventoryFactory CHARACTER_INVENTORY_FACTORY =
            new CharacterInventoryFactoryStub();
    private final ICharacterStatusEffectsFactory CHARACTER_STATUS_EFFECTS_FACTORY =
            new CharacterStatusEffectsFactoryStub();
    private final IGenericParamsSetFactory GENERIC_PARAMS_SET_FACTORY =
            new GenericParamsSetFactoryStub();
    private final ICharacterType CHARACTER_TYPE = new CharacterTypeStub();
    private final IEntityUuid ENTITY_UUID = new EntityUuidStub();

    private ICharacterFactory _characterFactory;

    @BeforeEach
    void setUp() {
        _characterFactory = new CharacterFactory(ENTITY_UUID_FACTORY,
                COLLECTION_FACTORY,
                MAP_FACTORY,
                CHARACTER_EQUIPMENT_SLOT_FACTORY,
                CHARACTER_INVENTORY_FACTORY,
                CHARACTER_STATUS_EFFECTS_FACTORY,
                GENERIC_PARAMS_SET_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactory(null,
                        COLLECTION_FACTORY,
                        MAP_FACTORY,
                        CHARACTER_EQUIPMENT_SLOT_FACTORY,
                        CHARACTER_INVENTORY_FACTORY,
                        CHARACTER_STATUS_EFFECTS_FACTORY,
                        GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactory(ENTITY_UUID_FACTORY,
                        null,
                        MAP_FACTORY,
                        CHARACTER_EQUIPMENT_SLOT_FACTORY,
                        CHARACTER_INVENTORY_FACTORY,
                        CHARACTER_STATUS_EFFECTS_FACTORY,
                        GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactory(ENTITY_UUID_FACTORY,
                        COLLECTION_FACTORY,
                        null,
                        CHARACTER_EQUIPMENT_SLOT_FACTORY,
                        CHARACTER_INVENTORY_FACTORY,
                        CHARACTER_STATUS_EFFECTS_FACTORY,
                        GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactory(ENTITY_UUID_FACTORY,
                        COLLECTION_FACTORY,
                        MAP_FACTORY,
                        null,
                        CHARACTER_INVENTORY_FACTORY,
                        CHARACTER_STATUS_EFFECTS_FACTORY,
                        GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactory(ENTITY_UUID_FACTORY,
                        COLLECTION_FACTORY,
                        MAP_FACTORY,
                        CHARACTER_EQUIPMENT_SLOT_FACTORY,
                        null,
                        CHARACTER_STATUS_EFFECTS_FACTORY,
                        GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactory(ENTITY_UUID_FACTORY,
                        COLLECTION_FACTORY,
                        MAP_FACTORY,
                        CHARACTER_EQUIPMENT_SLOT_FACTORY,
                        CHARACTER_INVENTORY_FACTORY,
                        null,
                        GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterFactory(ENTITY_UUID_FACTORY,
                        COLLECTION_FACTORY,
                        MAP_FACTORY,
                        CHARACTER_EQUIPMENT_SLOT_FACTORY,
                        CHARACTER_INVENTORY_FACTORY,
                        CHARACTER_STATUS_EFFECTS_FACTORY,
                        null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(ICharacterFactory.class.getCanonicalName(),
                _characterFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        ICharacter character = _characterFactory.make(CHARACTER_TYPE);

        assertNotNull(character);
        assertSame(EntityUuidFactoryStub.RANDOM_ENTITY_UUID, character.id());
    }

    @Test
    void testMakeWithEntityUuid() {
        ICharacter character = _characterFactory.make(CHARACTER_TYPE, ENTITY_UUID);

        assertNotNull(character);
        assertSame(ENTITY_UUID, character.id());
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _characterFactory.make(null));
        assertThrows(IllegalArgumentException.class,
                () -> _characterFactory.make(null, ENTITY_UUID));
        assertThrows(IllegalArgumentException.class,
                () -> _characterFactory.make(CHARACTER_TYPE, null));
    }
}

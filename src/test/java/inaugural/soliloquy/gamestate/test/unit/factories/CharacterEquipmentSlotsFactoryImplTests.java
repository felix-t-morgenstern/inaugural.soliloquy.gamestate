package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.CharacterEquipmentSlotsFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeCharacter;
import org.junit.Before;
import org.junit.Test;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.factories.CharacterEquipmentSlotsFactory;

import static org.junit.Assert.*;

public class CharacterEquipmentSlotsFactoryImplTests {
    private final Character CHARACTER = new FakeCharacter();

    private CharacterEquipmentSlotsFactory factory;

    @Before
    public void setUp() {
        factory = new CharacterEquipmentSlotsFactoryImpl();
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(CharacterEquipmentSlotsFactory.class.getCanonicalName(),
                factory.getInterfaceName());
    }

    @Test
    public void testMake() {
        assertNotNull(factory.make(CHARACTER));
    }

    @Test
    public void testMakeWithNull() {
        assertThrows(IllegalArgumentException.class,
                () -> factory.make(null));
    }
}

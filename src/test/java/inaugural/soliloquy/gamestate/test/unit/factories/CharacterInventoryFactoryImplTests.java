package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.CharacterInventoryFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeCharacter;
import org.junit.Before;
import org.junit.Test;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.factories.CharacterInventoryFactory;

import static org.junit.Assert.*;

public class CharacterInventoryFactoryImplTests {
    private final Character CHARACTER = new FakeCharacter();

    private CharacterInventoryFactory factory;

    @Before
    public void setUp() {
        factory = new CharacterInventoryFactoryImpl();
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(CharacterInventoryFactory.class.getCanonicalName(),
                factory.getInterfaceName());
    }

    @Test
    public void testMake() {
        assertNotNull(factory.make(CHARACTER));
    }

    @Test
    public void testMakeWithNullInput() {
        assertThrows(IllegalArgumentException.class, () -> factory.make(null));
    }
}

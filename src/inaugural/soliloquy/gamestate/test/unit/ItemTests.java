package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.gamestate.specs.IItem;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ItemTests {
    private IItem _item;

    @BeforeEach
    public void setUp() {
        _item = new Item();
    }

    @Test
    public void testGetInterfaceName() {
        assertTrue(_item.getInterfaceName().equals("soliloquy.gamestate.specs.IItem"));
    }
}

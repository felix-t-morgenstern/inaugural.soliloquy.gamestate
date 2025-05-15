package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.SettingFactoryImpl;
import inaugural.soliloquy.tools.collections.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.factories.SettingFactory;

import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static soliloquy.specs.common.valueobjects.Pair.pairOf;

public class SettingFactoryImplTests {
    private SettingFactory settingFactory;

    @BeforeEach
    public void setUp() {
        settingFactory = new SettingFactoryImpl();
    }

    @Test
    public void testMake() {
        var settingId = randomString();
        var settingName = randomString();
        var settingValue = randomString();
        var settingControlParams =
                Collections.<String, Object>immutableMapOf(pairOf(randomString(), randomString()));

        var setting =
                settingFactory.make(settingId, settingName, settingValue, settingControlParams);

        assertEquals(setting.id(), settingId);
        assertEquals(setting.getName(), settingName);
        assertSame(setting.getValue(), settingValue);
        assertEquals(settingControlParams.size(), setting.controlParams().size());
        for(var key : settingControlParams.keySet()) {
            assertTrue(setting.controlParams().containsKey(key));
            assertEquals(settingControlParams.get(key), setting.controlParams().get(key));
        }
    }
}
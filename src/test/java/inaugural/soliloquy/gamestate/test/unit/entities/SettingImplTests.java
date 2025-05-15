package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.SettingImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.common.infrastructure.ImmutableMap;
import soliloquy.specs.gamestate.entities.Setting;

import java.util.Objects;

import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SettingImplTests {
    private final String SETTING_ID = randomString();
    private final String SETTING_NAME_1 = randomString();
    private final Integer SETTING_VALUE_1 = randomInt();

    @Mock private ImmutableMap<String, Object> mockSettingControlParams;

    private Setting<Integer> setting;

    @BeforeEach
    public void setUp() {
        setting = new SettingImpl<>(SETTING_ID, SETTING_NAME_1, SETTING_VALUE_1,
                mockSettingControlParams);
    }

    @Test
    public void testWithInvalidConstructorParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new SettingImpl<>(null,
                        SETTING_NAME_1,
                        SETTING_VALUE_1,
                        mockSettingControlParams));
        assertThrows(IllegalArgumentException.class,
                () -> new SettingImpl<>("",
                        SETTING_NAME_1,
                        SETTING_VALUE_1,
                        mockSettingControlParams));
        assertThrows(IllegalArgumentException.class,
                () -> new SettingImpl<>(SETTING_ID,
                        null,
                        SETTING_VALUE_1,
                        mockSettingControlParams));
        assertThrows(IllegalArgumentException.class,
                () -> new SettingImpl<>(SETTING_ID,
                        "",
                        SETTING_VALUE_1,
                        mockSettingControlParams));
        assertThrows(IllegalArgumentException.class,
                () -> new SettingImpl<>(SETTING_ID,
                        SETTING_NAME_1,
                        SETTING_VALUE_1,
                        null));
    }

    @Test
    public void testId() {
        assertEquals(SETTING_ID, setting.id());
    }

    @Test
    public void testName() {
        assertEquals(SETTING_NAME_1, setting.getName());
        var settingName2 = randomString();

        setting.setName(settingName2);

        assertEquals(settingName2, setting.getName());
    }

    @Test
    public void testSetNameWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> setting.setName(null));
        assertThrows(IllegalArgumentException.class, () -> setting.setName(""));
    }

    @Test
    public void testGetValue() {
        assertSame(setting.getValue(), SETTING_VALUE_1);
    }

    @Test
    public void testSetValue() {
        var settingValue2 = randomInt();

        setting.setValue(settingValue2);

        assertEquals(settingValue2, setting.getValue());
    }

    @Test
    public void testControlParams() {
        assertSame(setting.controlParams(), mockSettingControlParams);
    }

    @Test
    public void testHashCode() {
        assertEquals(
                Objects.hash(SETTING_ID, SETTING_NAME_1, SETTING_VALUE_1, mockSettingControlParams),
                setting.hashCode());
    }

    @Test
    public void testEquals() {
        var setting2 = new SettingImpl<>(SETTING_ID, SETTING_NAME_1, SETTING_VALUE_1,
                mockSettingControlParams);

        assertEquals(setting, setting2);
    }
}

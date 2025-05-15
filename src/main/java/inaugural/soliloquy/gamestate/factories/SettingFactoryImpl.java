package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.SettingImpl;
import soliloquy.specs.common.infrastructure.ImmutableMap;
import soliloquy.specs.gamestate.entities.Setting;
import soliloquy.specs.gamestate.factories.SettingFactory;

public class SettingFactoryImpl implements SettingFactory {
    @Override
    public <T> Setting<T> make(String id, String name, T defaultValue,
                               ImmutableMap<String, Object> controlParams) {
        return new SettingImpl<>(id, name, defaultValue, controlParams);
    }
}

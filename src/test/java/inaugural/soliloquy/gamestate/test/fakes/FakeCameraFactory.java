package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.gamestate.entities.Camera;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.factories.CameraFactory;

import java.util.function.Supplier;

import static inaugural.soliloquy.tools.generic.Archetypes.generateSimpleArchetype;

public class FakeCameraFactory implements CameraFactory {
    public static final Camera CAMERA = generateSimpleArchetype(Camera.class);
    public Supplier<GameZone> GET_CURRENT_GAME_ZONE;

    @Override
    public Camera make(Supplier<GameZone> getCurrentGameZone) throws IllegalArgumentException {
        GET_CURRENT_GAME_ZONE = getCurrentGameZone;
        return CAMERA;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

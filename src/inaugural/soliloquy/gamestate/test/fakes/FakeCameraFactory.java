package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.gamestate.entities.Camera;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.factories.CameraFactory;

import java.util.function.Supplier;

public class FakeCameraFactory implements CameraFactory {
    public final static Camera CAMERA = new FakeCamera();
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

package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.CameraImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.gamestate.entities.Camera;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.factories.CameraFactory;
import soliloquy.specs.ruleset.gameconcepts.TileVisibility;

import java.util.function.Supplier;

public class CameraFactoryImpl implements CameraFactory {
    private final TileVisibility TILE_VISIBILITY;

    public CameraFactoryImpl(TileVisibility tileVisibility) {
        TILE_VISIBILITY = Check.ifNull(tileVisibility, "tileVisibility");
    }

    @Override
    public Camera make(Supplier<GameZone> getCurrentGameZone) throws IllegalArgumentException {
        return new CameraImpl(TILE_VISIBILITY, getCurrentGameZone);
    }

    @Override
    public String getInterfaceName() {
        return CameraFactory.class.getCanonicalName();
    }
}

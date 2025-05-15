package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.GameZoneImpl;
import inaugural.soliloquy.tools.Check;
import org.apache.commons.lang3.function.TriConsumer;
import soliloquy.specs.common.valueobjects.Coordinate2d;
import soliloquy.specs.common.valueobjects.Coordinate3d;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.shared.GameZoneTerrain;
import soliloquy.specs.gamestate.factories.GameZoneFactory;

import java.util.Map;
import java.util.function.Consumer;

public class GameZoneFactoryImpl implements GameZoneFactory {
    private final Consumer<Character> ADD_TO_END_OF_ROUND_MANAGER;
    private final Consumer<Character> REMOVE_FROM_ROUND_MANAGER;
    private final TriConsumer<GameZoneTerrain, GameZone, Coordinate3d> ASSIGN_LOC_AFTER_PLACE;

    public GameZoneFactoryImpl(Consumer<Character> addToEndOfRoundManager,
                               Consumer<Character> removeFromRoundManager,
                               TriConsumer<GameZoneTerrain, GameZone, Coordinate3d> assignLocationAfterPlacement) {
        ADD_TO_END_OF_ROUND_MANAGER = Check.ifNull(addToEndOfRoundManager,
                "addToEndOfRoundManager");
        REMOVE_FROM_ROUND_MANAGER = Check.ifNull(removeFromRoundManager, "removeFromRoundManager");
        ASSIGN_LOC_AFTER_PLACE =
                Check.ifNull(assignLocationAfterPlacement, "assignLocationAfterPlacement");
    }

    @Override
    public GameZone make(String id, Coordinate2d maxCoordinates, Map<String, Object> data)
            throws IllegalArgumentException {
        Check.ifNullOrEmpty(id, "id");
        Check.ifNull(maxCoordinates, "maxCoordinates");
        Check.throwOnLteZero(maxCoordinates.X, "maxCoordinates.X");
        Check.throwOnLteZero(maxCoordinates.Y, "maxCoordinates.Y");
        return new GameZoneImpl(Check.ifNullOrEmpty(id, "id"), maxCoordinates, data,
                ADD_TO_END_OF_ROUND_MANAGER, REMOVE_FROM_ROUND_MANAGER, ASSIGN_LOC_AFTER_PLACE);
    }
}

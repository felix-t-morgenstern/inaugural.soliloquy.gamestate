package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.GameZoneImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Coordinate2d;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.GameZoneFactory;

import java.util.function.Consumer;

public class GameZoneFactoryImpl implements GameZoneFactory {
    private final Consumer<Character> ADD_TO_END_OF_ROUND_MANAGER;
    private final Consumer<Character> REMOVE_FROM_ROUND_MANAGER;

    public GameZoneFactoryImpl(Consumer<Character> addToEndOfRoundManager,
                               Consumer<Character> removeFromRoundManager) {
        ADD_TO_END_OF_ROUND_MANAGER = Check.ifNull(addToEndOfRoundManager,
                "addToEndOfRoundManager");
        REMOVE_FROM_ROUND_MANAGER = Check.ifNull(removeFromRoundManager, "removeFromRoundManager");
    }

    @Override
    public GameZone make(String id, Coordinate2d maxCoordinates, VariableCache data)
            throws IllegalArgumentException {
        Check.ifNullOrEmpty(id, "id");
        Check.ifNull(maxCoordinates, "maxCoordinates");
        Check.throwOnLteZero(maxCoordinates.X, "maxCoordinates.X");
        Check.throwOnLteZero(maxCoordinates.Y, "maxCoordinates.Y");
        return new GameZoneImpl(Check.ifNullOrEmpty(id, "id"), maxCoordinates, data,
                ADD_TO_END_OF_ROUND_MANAGER, REMOVE_FROM_ROUND_MANAGER);
    }

    @Override
    public String getInterfaceName() {
        return GameZoneFactory.class.getCanonicalName();
    }
}

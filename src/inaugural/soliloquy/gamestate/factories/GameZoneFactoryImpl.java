package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.GameZoneImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.GameZoneFactory;

import java.util.function.Consumer;

public class GameZoneFactoryImpl implements GameZoneFactory {
    private final CoordinateFactory COORDINATE_FACTORY;
    private final Consumer<Character> ADD_TO_END_OF_ROUND_MANAGER;
    private final Consumer<Character> REMOVE_FROM_ROUND_MANAGER;

    @SuppressWarnings("ConstantConditions")
    public GameZoneFactoryImpl(CoordinateFactory coordinateFactory,
                               Consumer<Character> addToEndOfRoundManager,
                               Consumer<Character> removeFromRoundManager) {
        COORDINATE_FACTORY = Check.ifNull(coordinateFactory, "coordinateFactory");
        ADD_TO_END_OF_ROUND_MANAGER = Check.ifNull(addToEndOfRoundManager,
                "addToEndOfRoundManager");
        REMOVE_FROM_ROUND_MANAGER = Check.ifNull(removeFromRoundManager, "removeFromRoundManager");
    }

    @Override
    public GameZone make(String id, String zoneType, Tile[][] tiles, VariableCache data)
            throws IllegalArgumentException {
        return new GameZoneImpl(Check.ifNullOrEmpty(id, "id"), zoneType, tiles, COORDINATE_FACTORY,
                data, ADD_TO_END_OF_ROUND_MANAGER, REMOVE_FROM_ROUND_MANAGER);
    }

    @Override
    public String getInterfaceName() {
        return GameZoneFactory.class.getCanonicalName();
    }
}

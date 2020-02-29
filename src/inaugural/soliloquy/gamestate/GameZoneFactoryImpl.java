package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.GameZoneFactory;

import java.util.function.Consumer;

public class GameZoneFactoryImpl implements GameZoneFactory {
    private final CoordinateFactory COORDINATE_FACTORY;
    private final CollectionFactory COLLECTION_FACTORY;
    private final Consumer<Character> ADD_TO_END_OF_ROUND_MANAGER;
    private final Consumer<Character> REMOVE_FROM_ROUND_MANAGER;

    @SuppressWarnings("ConstantConditions")
    public GameZoneFactoryImpl(CoordinateFactory coordinateFactory,
                               CollectionFactory collectionFactory,
                               Consumer<Character> addToEndOfRoundManager,
                               Consumer<Character> removeFromRoundManager) {
        if (coordinateFactory == null) {
            throw new IllegalArgumentException(
                    "GameZoneFactoryImpl: coordinateFactory cannot be null");
        }
        COORDINATE_FACTORY = coordinateFactory;
        if (collectionFactory == null) {
            throw new IllegalArgumentException(
                    "GameZoneFactoryImpl: collectionFactory cannot be null");
        }
        COLLECTION_FACTORY = collectionFactory;
        if (addToEndOfRoundManager == null) {
            throw new IllegalArgumentException(
                    "GameZoneFactoryImpl: addToEndOfRoundManager cannot be null");
        }
        ADD_TO_END_OF_ROUND_MANAGER = addToEndOfRoundManager;
        if (removeFromRoundManager == null) {
            throw new IllegalArgumentException(
                    "GameZoneFactoryImpl: removeFromRoundManager cannot be null");
        }
        REMOVE_FROM_ROUND_MANAGER = removeFromRoundManager;
    }

    @Override
    public GameZone make(String id, String zoneType, Tile[][] tiles, VariableCache data)
            throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("GameZoneFactoryImpl.make: id cannot be null");
        }
        if (id.equals("")) {
            throw new IllegalArgumentException("GameZoneFactoryImpl.make: id cannot be empty");
        }
        return new GameZoneImpl(id, zoneType, tiles, COORDINATE_FACTORY, COLLECTION_FACTORY, data,
                ADD_TO_END_OF_ROUND_MANAGER, REMOVE_FROM_ROUND_MANAGER);
    }

    @Override
    public String getInterfaceName() {
        return GameZoneFactory.class.getCanonicalName();
    }
}

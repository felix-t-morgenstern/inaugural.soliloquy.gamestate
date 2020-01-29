package inaugural.soliloquy.gamestate.persistentvaluetypehandlers;

import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentTypeHandler;
import inaugural.soliloquy.gamestate.archetypes.GameStateArchetype;
import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.GameState;
import soliloquy.specs.gamestate.factories.GameStateFactory;

public class PersistentGameStateHandler extends PersistentTypeHandler<GameState> {
    private final GameStateFactory GAME_STATE_FACTORY;
    private final PersistentValueTypeHandler<Character> CHARACTER_HANDLER;
    private final PersistentValueTypeHandler<EntityUuid> UUID_HANDLER;

    private static final GameState ARCHETYPE = new GameStateArchetype();

    @SuppressWarnings("ConstantConditions")
    public PersistentGameStateHandler(GameStateFactory gameStateFactory,
                                      PersistentValueTypeHandler<Character> characterHandler,
                                      PersistentValueTypeHandler<EntityUuid> uuidHandler) {
        if (gameStateFactory == null) {
            throw new IllegalArgumentException(
                    "PersistentGameStateHandler: gameStateFactory cannot be null");
        }
        GAME_STATE_FACTORY = gameStateFactory;
        if (characterHandler == null) {
            throw new IllegalArgumentException(
                    "PersistentGameStateHandler: characterHandler cannot be null");
        }
        CHARACTER_HANDLER = characterHandler;
        if (uuidHandler == null) {
            throw new IllegalArgumentException(
                    "PersistentGameStateHandler: uuidHandler cannot be null");
        }
        UUID_HANDLER = uuidHandler;
    }

    @Override
    public GameState getArchetype() {
        return null;
    }

    @Override
    public GameState read(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String write(GameState gameState) {
        return null;
    }

    private class GameStateDTO {
        String currentGameZone;
        String[] partyInCurrentGameZone;
        String[] partyNotInCurrentGameZone;
        String data;
    }

    private class PartyMemberInGameZoneDTO {
        int x;
        int y;
        String id;
    }
}

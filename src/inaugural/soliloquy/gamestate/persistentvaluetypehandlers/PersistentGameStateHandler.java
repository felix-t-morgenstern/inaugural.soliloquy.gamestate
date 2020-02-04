package inaugural.soliloquy.gamestate.persistentvaluetypehandlers;

import com.google.gson.Gson;
import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentTypeHandler;
import inaugural.soliloquy.gamestate.archetypes.GameStateArchetype;
import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;
import soliloquy.specs.common.infrastructure.ReadablePair;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.factories.GameStateFactory;
import soliloquy.specs.gamestate.factories.PartyFactory;

import java.util.ArrayList;

public class PersistentGameStateHandler extends PersistentTypeHandler<GameState> {
    private final GameStateFactory GAME_STATE_FACTORY;
    private final PartyFactory PARTY_FACTORY;
    private final GameZonesRepo GAME_ZONES_REPO;
    private final PersistentValueTypeHandler<VariableCache> VARIABLE_CACHE_HANDLER;
    private final PersistentValueTypeHandler<Character> CHARACTER_HANDLER;

    private static final GameState ARCHETYPE = new GameStateArchetype();

    @SuppressWarnings("ConstantConditions")
    public PersistentGameStateHandler(GameStateFactory gameStateFactory,
                                      PartyFactory partyFactory,
                                      GameZonesRepo gameZonesRepo,
                                      PersistentValueTypeHandler<VariableCache>
                                              variableCacheHandler,
                                      PersistentValueTypeHandler<Character> characterHandler) {
        if (gameStateFactory == null) {
            throw new IllegalArgumentException(
                    "PersistentGameStateHandler: gameStateFactory cannot be null");
        }
        GAME_STATE_FACTORY = gameStateFactory;
        if (partyFactory == null) {
            throw new IllegalArgumentException(
                    "PersistentGameStateHandler: partyFactory cannot be null");
        }
        PARTY_FACTORY = partyFactory;
        if (gameZonesRepo == null) {
            throw new IllegalArgumentException(
                    "PersistentGameStateHandler: gameZonesRepo cannot be null");
        }
        GAME_ZONES_REPO = gameZonesRepo;
        if (variableCacheHandler == null) {
            throw new IllegalArgumentException(
                    "PersistentGameStateHandler: variableCacheHandler cannot be null");
        }
        VARIABLE_CACHE_HANDLER = variableCacheHandler;
        if (characterHandler == null) {
            throw new IllegalArgumentException(
                    "PersistentGameStateHandler: characterHandler cannot be null");
        }
        CHARACTER_HANDLER = characterHandler;
    }

    @Override
    public GameState getArchetype() {
        return ARCHETYPE;
    }

    @Override
    public GameState read(String writtenData) throws IllegalArgumentException {
        if (writtenData == null) {
            throw new IllegalArgumentException(
                    "PersistentGameStateHandler.read: writtenData cannot be null");
        }
        if (writtenData.equals("")) {
            throw new IllegalArgumentException(
                    "PersistentGameStateHandler.read: writtenData cannot be empty");
        }
        GameStateDTO dto = new Gson().fromJson(writtenData, GameStateDTO.class);
        Party party = PARTY_FACTORY.make(VARIABLE_CACHE_HANDLER.read(dto.partyAttributes));
        GameState gameState = GAME_STATE_FACTORY.make(party,
                VARIABLE_CACHE_HANDLER.read(dto.data));
        GameZone currentGameZone = GAME_ZONES_REPO.getGameZone(dto.currentGameZoneId);
        gameState.setCurrentGameZone(currentGameZone);
        for (PcInGameZoneDTO pcDto : dto.pcsInCurrentGameZone) {
            boolean found = false;
            for (ReadablePair<Character, Integer> character :
                    currentGameZone.tile(pcDto.x, pcDto.y).characters()) {
                if (character.getItem1().id().toString().equals(pcDto.id)) {
                    party.characters().add(character.getItem1());
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new IllegalArgumentException("PersistentGameStateHandler: character " +
                        pcDto.id + " not found at (" + pcDto.x + "," + pcDto.y + ")");
            }
        }
        for (String pc : dto.pcsNotInCurrentGameZone) {
            party.characters().add(CHARACTER_HANDLER.read(pc));
        }
        return gameState;
    }

    @Override
    public String write(GameState gameState) {
        if (gameState == null) {
            throw new IllegalArgumentException(
                    "PersistentGameStateHandler.write: gameState cannot be null");
        }
        GameStateDTO dto = new GameStateDTO();
        dto.currentGameZoneId = gameState.getCurrentGameZone().id();
        dto.data = VARIABLE_CACHE_HANDLER.write(gameState.variableCache());
        dto.partyAttributes = VARIABLE_CACHE_HANDLER.write(gameState.party().attributes());
        ArrayList<Character> pcsInGameZone = new ArrayList<>();
        ArrayList<Character> pcsNotInGameZone = new ArrayList<>();
        for(Character pc : gameState.party().characters()) {
            if (pc.tile() == null) {
                pcsNotInGameZone.add(pc);
            } else {
                pcsInGameZone.add(pc);
            }
        }
        dto.pcsInCurrentGameZone = new PcInGameZoneDTO[pcsInGameZone.size()];
        dto.pcsNotInCurrentGameZone = new String[pcsNotInGameZone.size()];
        for (int i = 0; i < pcsInGameZone.size(); i++) {
            dto.pcsInCurrentGameZone[i] = new PcInGameZoneDTO();
            dto.pcsInCurrentGameZone[i].x = pcsInGameZone.get(i).tile().location().getX();
            dto.pcsInCurrentGameZone[i].y = pcsInGameZone.get(i).tile().location().getY();
            dto.pcsInCurrentGameZone[i].id = pcsInGameZone.get(i).id().toString();
        }
        for (int i = 0; i < pcsNotInGameZone.size(); i++) {
            dto.pcsNotInCurrentGameZone[i] = CHARACTER_HANDLER.write(pcsNotInGameZone.get(i));
        }
        return new Gson().toJson(dto);
    }

    private class GameStateDTO {
        String currentGameZoneId;
        PcInGameZoneDTO[] pcsInCurrentGameZone;
        String[] pcsNotInCurrentGameZone;
        String data;
        String partyAttributes;
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    private class PcInGameZoneDTO {
        int x;
        int y;
        String id;
    }
}

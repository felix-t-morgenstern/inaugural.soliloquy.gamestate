package inaugural.soliloquy.gamestate.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractSoliloquyTypeHandler;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Party;
import soliloquy.specs.gamestate.factories.PartyFactory;

import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public class PartyHandler extends AbstractSoliloquyTypeHandler<Party>
        implements TypeHandler<Party> {
    private final PartyFactory PARTY_FACTORY;
    private final Supplier<GameZone> GET_CURRENT_GAME_ZONE;
    private final TypeHandler<VariableCache> ATTRIBUTES_HANDLER;
    private final TypeHandler<Character> CHARACTER_HANDLER;

    public PartyHandler(PartyFactory partyFactory,
                        Supplier<GameZone> getCurrentGameZone,
                        TypeHandler<VariableCache> attributesHandler,
                        TypeHandler<Character> characterHandler) {
        super(Party.class);
        PARTY_FACTORY = Check.ifNull(partyFactory, "partyFactory");
        GET_CURRENT_GAME_ZONE = Check.ifNull(getCurrentGameZone, "getCurrentGameZone");
        ATTRIBUTES_HANDLER = Check.ifNull(attributesHandler, "attributesHandler");
        CHARACTER_HANDLER = Check.ifNull(characterHandler, "characterHandler");
    }

    @Override
    public Party read(String data) throws IllegalArgumentException {
        Check.ifNullOrEmpty(data, "data");

        PartyDTO partyDTO = JSON.fromJson(data, PartyDTO.class);

        Party party = PARTY_FACTORY.make(ATTRIBUTES_HANDLER.read(partyDTO.attributes));

        Map<UUID, Character> charactersInZone = GET_CURRENT_GAME_ZONE.get().charactersRepresentation();
        for(int i = 0; i < partyDTO.pcs.length; i++) {
            PcDTO pcDTO = partyDTO.pcs[i];
            UUID pcUuid = UUID.fromString(pcDTO.uuid);
            Character matchFromGameZone = charactersInZone.get(pcUuid);
            if (matchFromGameZone != null) {
                party.characters().add(matchFromGameZone);
            }
            else {
                party.characters().add(CHARACTER_HANDLER.read(pcDTO.character));
            }
        }

        return party;
    }

    @Override
    public String write(Party party) {
        Check.ifNull(party, "party");

        PartyDTO partyDTO = new PartyDTO();

        partyDTO.attributes = ATTRIBUTES_HANDLER.write(party.attributes());

        partyDTO.pcs = new PcDTO[party.characters().size()];

        for (int i = 0; i < party.characters().size(); i++) {
            PcDTO pcDTO = new PcDTO();

            Character pc = party.characters().get(i);

            pcDTO.uuid = pc.uuid().toString();

            if (pc.tile() == null) {
                pcDTO.character = CHARACTER_HANDLER.write(pc);
            }

            partyDTO.pcs[i] = pcDTO;
        }

        return JSON.toJson(partyDTO);
    }

    private static class PartyDTO {
        PcDTO[] pcs;
        String attributes;
    }

    private static class PcDTO {
        String uuid;
        String character;
    }
}

package inaugural.soliloquy.gamestate.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Party;

import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

public class PartyHandler extends AbstractTypeHandler<Party> implements TypeHandler<Party> {
    private final Function<Map<String, Object>, Party> PARTY_FACTORY;
    private final Supplier<GameZone> GET_CURRENT_GAME_ZONE;
    @SuppressWarnings("rawtypes") private final TypeHandler<Map> ATTRIBUTES_HANDLER;
    private final TypeHandler<Character> CHARACTER_HANDLER;

    public PartyHandler(Function<Map<String, Object>, Party> partyFactory,
                        Supplier<GameZone> getCurrentGameZone,
                        @SuppressWarnings("rawtypes") TypeHandler<Map> attributesHandler,
                        TypeHandler<Character> characterHandler) {
        PARTY_FACTORY = Check.ifNull(partyFactory, "partyFactory");
        GET_CURRENT_GAME_ZONE = Check.ifNull(getCurrentGameZone, "getCurrentGameZone");
        ATTRIBUTES_HANDLER = Check.ifNull(attributesHandler, "attributesHandler");
        CHARACTER_HANDLER = Check.ifNull(characterHandler, "characterHandler");
    }

    @SuppressWarnings("unchecked")
    @Override
    public Party read(String data) throws IllegalArgumentException {
        Check.ifNullOrEmpty(data, "data");

        var partyDTO = JSON.fromJson(data, DTO.class);

        //noinspection unchecked
        var party = PARTY_FACTORY.apply(
                (Map<String, Object>) ATTRIBUTES_HANDLER.read(partyDTO.attributes));

        var charactersInZone = GET_CURRENT_GAME_ZONE.get().charactersRepresentation();
        for (var i = 0; i < partyDTO.pcs.length; i++) {
            var pcDTO = partyDTO.pcs[i];
            var pcUuid = UUID.fromString(pcDTO.uuid);
            var matchFromGameZone = charactersInZone.get(pcUuid);
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

        var partyDTO = new DTO();

        partyDTO.attributes = ATTRIBUTES_HANDLER.write(party.attributes());

        partyDTO.pcs = new PcDTO[party.characters().size()];

        for (var i = 0; i < party.characters().size(); i++) {
            var pcDTO = new PcDTO();
            var pc = party.characters().get(i);

            pcDTO.uuid = pc.uuid().toString();

            if (pc.tile() == null) {
                pcDTO.character = CHARACTER_HANDLER.write(pc);
            }

            partyDTO.pcs[i] = pcDTO;
        }

        return JSON.toJson(partyDTO);
    }

    private static class DTO {
        PcDTO[] pcs;
        String attributes;
    }

    private static class PcDTO {
        String uuid;
        String character;
    }
}

package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Party;

import java.util.List;

import static inaugural.soliloquy.tools.collections.Collections.listOf;

public class PartyImpl implements Party {
    private final List<Character> CHARACTERS;
    private final VariableCache ATTRIBUTES;

    // TODO: Ensure that listFactory is not null
    public PartyImpl(VariableCache data) {
        CHARACTERS = listOf();
        ATTRIBUTES = Check.ifNull(data, "data");
    }

    @Override
    public List<Character> characters() {
        return CHARACTERS;
    }

    @Override
    public VariableCache attributes() {
        return ATTRIBUTES;
    }

    @Override
    public String getInterfaceName() {
        return Party.class.getCanonicalName();
    }
}

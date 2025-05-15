package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Party;

import java.util.List;
import java.util.Map;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.collections.Collections.mapOf;

public class PartyImpl implements Party {
    private final List<Character> CHARACTERS;
    private final Map<String, Object> ATTRIBUTES;

    // TODO: Ensure that listFactory is not null
    public PartyImpl(Map<String, Object> data) {
        CHARACTERS = listOf();
        ATTRIBUTES = mapOf(Check.ifNull(data, "data"));
    }

    @Override
    public List<Character> characters() {
        return CHARACTERS;
    }

    @Override
    public Map<String, Object> attributes() {
        return ATTRIBUTES;
    }
}

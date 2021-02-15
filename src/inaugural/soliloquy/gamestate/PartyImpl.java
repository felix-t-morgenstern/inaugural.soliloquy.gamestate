package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.CharacterArchetype;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Party;

public class PartyImpl implements Party {
    private final List<Character> CHARACTERS;
    private final VariableCache ATTRIBUTES;

    // TODO: Ensure that listFactory is not null
    public PartyImpl(ListFactory listFactory, VariableCache data) {
        CHARACTERS = listFactory.make(new CharacterArchetype());
        ATTRIBUTES = data;
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

package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Party;

public class PartyStub implements Party {
    private final Collection<Character> CHARACTERS = new CollectionStub<>();
    private final VariableCache ATTRIBUTES;

    public PartyStub() {
        ATTRIBUTES = new VariableCacheStub();
    }

    public PartyStub(VariableCache attributes) {
        ATTRIBUTES = attributes;
    }

    @Override
    public Collection<Character> characters() {
        return CHARACTERS;
    }

    @Override
    public VariableCache attributes() {
        return ATTRIBUTES;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

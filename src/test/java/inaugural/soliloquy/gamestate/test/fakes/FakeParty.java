package inaugural.soliloquy.gamestate.test.fakes;

import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Party;

public class FakeParty implements Party {
    private final List<Character> CHARACTERS = new FakeList<>();
    private final VariableCache ATTRIBUTES;

    public FakeParty() {
        ATTRIBUTES = new VariableCacheStub();
    }

    public FakeParty(VariableCache attributes) {
        ATTRIBUTES = attributes;
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
        return null;
    }
}

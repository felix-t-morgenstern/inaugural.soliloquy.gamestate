package inaugural.soliloquy.gamestate.test.fakes;

import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Party;

import java.util.List;

import static inaugural.soliloquy.tools.collections.Collections.listOf;

public class FakeParty implements Party {
    private final List<Character> CHARACTERS = listOf();
    private final VariableCache ATTRIBUTES;

    public FakeParty() {
        ATTRIBUTES = new VariableCacheStub();
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

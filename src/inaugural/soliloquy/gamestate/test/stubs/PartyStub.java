package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Party;

public class PartyStub implements Party {
    @Override
    public Collection<Character> playerCharacters() {
        return null;
    }

    @Override
    public GenericParamsSet partyAttributes() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

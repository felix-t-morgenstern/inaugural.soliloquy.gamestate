package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.gamestate.specs.ICharacter;
import soliloquy.gamestate.specs.IParty;

public class PartyStub implements IParty {
    @Override
    public ICollection<ICharacter> playerCharacters() {
        return null;
    }

    @Override
    public IGenericParamsSet partyAttributes() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

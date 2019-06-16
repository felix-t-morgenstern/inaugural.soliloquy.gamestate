package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.valueobjects.ICollection;
import soliloquy.specs.common.valueobjects.IGenericParamsSet;
import soliloquy.specs.gamestate.entities.ICharacter;
import soliloquy.specs.gamestate.entities.IParty;

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

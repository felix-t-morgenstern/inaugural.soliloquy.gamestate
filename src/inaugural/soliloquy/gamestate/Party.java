package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.CharacterArchetype;
import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.ICollectionFactory;
import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.common.specs.IGenericParamsSetFactory;
import soliloquy.gamestate.specs.ICharacter;
import soliloquy.gamestate.specs.IParty;

public class Party implements IParty {
    private final ICollection<ICharacter> PCs;
    private final IGenericParamsSet PARTY_ATTRIBUTES;

    public Party(ICollectionFactory collectionFactory, IGenericParamsSetFactory genericParamsSetFactory) {
        PCs = collectionFactory.make(new CharacterArchetype());
        PARTY_ATTRIBUTES = genericParamsSetFactory.make();
    }

    @Override
    public ICollection<ICharacter> playerCharacters() {
        return PCs;
    }

    @Override
    public IGenericParamsSet partyAttributes() {
        return PARTY_ATTRIBUTES;
    }

    @Override
    public void read(String s, boolean b) throws IllegalArgumentException {
        // TODO: Implement and test
    }

    @Override
    public String write() throws IllegalArgumentException {
        // TODO: Implement and test
        return null;
    }

    @Override
    public String getInterfaceName() {
        return "soliloquy.gamestate.specs.IParty";
    }
}

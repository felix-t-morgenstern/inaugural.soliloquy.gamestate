package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.CharacterArchetype;
import soliloquy.specs.common.factories.ICollectionFactory;
import soliloquy.specs.common.factories.IGenericParamsSetFactory;
import soliloquy.specs.common.valueobjects.ICollection;
import soliloquy.specs.common.valueobjects.IGenericParamsSet;
import soliloquy.specs.gamestate.entities.ICharacter;
import soliloquy.specs.gamestate.entities.IParty;

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
    public String getInterfaceName() {
        return IParty.class.getCanonicalName();
    }
}

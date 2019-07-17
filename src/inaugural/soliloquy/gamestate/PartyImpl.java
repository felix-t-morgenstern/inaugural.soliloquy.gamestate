package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.CharacterArchetype;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.GenericParamsSetFactory;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Party;

public class PartyImpl implements Party {
    private final Collection<Character> PCs;
    private final GenericParamsSet PARTY_ATTRIBUTES;

    public PartyImpl(CollectionFactory collectionFactory, GenericParamsSetFactory genericParamsSetFactory) {
        PCs = collectionFactory.make(new CharacterArchetype());
        PARTY_ATTRIBUTES = genericParamsSetFactory.make();
    }

    @Override
    public Collection<Character> playerCharacters() {
        return PCs;
    }

    @Override
    public GenericParamsSet partyAttributes() {
        return PARTY_ATTRIBUTES;
    }

    @Override
    public String getInterfaceName() {
        return Party.class.getCanonicalName();
    }
}

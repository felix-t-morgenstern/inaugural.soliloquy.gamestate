package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.CharacterArchetype;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Party;

public class PartyImpl implements Party {
    private final Collection<Character> PCs;
    private final VariableCache PARTY_ATTRIBUTES;

    public PartyImpl(CollectionFactory collectionFactory,
                     VariableCacheFactory variableCacheFactory) {
        PCs = collectionFactory.make(new CharacterArchetype());
        PARTY_ATTRIBUTES = variableCacheFactory.make();
    }

    @Override
    public Collection<Character> playerCharacters() {
        return PCs;
    }

    @Override
    public VariableCache partyAttributes() {
        return PARTY_ATTRIBUTES;
    }

    @Override
    public String getInterfaceName() {
        return Party.class.getCanonicalName();
    }
}

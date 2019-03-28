package inaugural.soliloquy.gamestate;

import soliloquy.common.specs.*;
import soliloquy.gamestate.specs.*;
import soliloquy.ruleset.gameentities.specs.ICharacterClassification;
import soliloquy.ruleset.gameentities.specs.ICharacterType;
import soliloquy.sprites.specs.ISpriteSet;

public class Party implements IParty {
    private final ICollection<ICharacter> PCs;
    private final IGenericParamsSet PARTY_ATTRIBUTES;

    public Party(ICollectionFactory collectionFactory, IGenericParamsSetFactory genericParamsSetFactory) {
        PCs = collectionFactory.make(new CharacterStub());
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

    private class CharacterStub implements ICharacter {

        @Override
        public void setGameZone(IGameZone iGameZone) throws IllegalArgumentException, IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public ICharacterType characterType() throws IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public ICollection<ICharacterClassification> classifications() throws IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public ITile getTile() throws IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setTile(ITile iTile) throws IllegalArgumentException, IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public IMap<String, String> pronouns() throws IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public IGenericParamsSet traits() throws IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getStance() throws IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setStance(String s) throws IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getDirection() throws IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setDirection(String s) throws IllegalArgumentException, IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public ISpriteSet getSpriteSet() throws IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setSpriteSet(ISpriteSet iSpriteSet) throws IllegalArgumentException, IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public ICharacterAI getAI() throws IllegalStateException, IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setAI(ICharacterAI iCharacterAI) throws IllegalArgumentException, IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public IMap<String, ICharacterEquipmentSlot> equipment() throws IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public IMap<Integer, IItem> inventory() throws IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public IMap<String, ICharacterVitalAttribute> vitalAttributes() throws IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public IMap<String, ICharacterValueFromModifiers> attributes() throws IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public ICharacterStatusEffects statusEffects() throws IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public IMap<String, ICharacterAbility> activeAbilities() throws IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public IMap<String, ICharacterAbility> reactiveAbilities() throws IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public IMap<String, ICharacterValueFromModifiers> aptitudes() throws IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isPC() throws IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isHidden() throws IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setHidden(boolean b) throws IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isDead() throws IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setIsDead(boolean b) throws IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void kill(ICharacter iCharacter) throws IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public IGameZone getGameZone() throws IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public IGenericParamsSet params() throws IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void delete() throws IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isDeleted() {
            throw new UnsupportedOperationException();
        }

        @Override
        public IEntityUuid id() {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getName() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setName(String s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void read(String s, boolean b) throws IllegalArgumentException {
            throw new UnsupportedOperationException();
        }

        @Override
        public String write() throws IllegalArgumentException {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getInterfaceName() {
            return "soliloquy.specs.gamestate.ICharacter";
        }
    }
}

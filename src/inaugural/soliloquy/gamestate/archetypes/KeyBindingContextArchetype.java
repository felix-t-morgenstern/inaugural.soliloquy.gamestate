package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.entities.IFunction;
import soliloquy.specs.common.infrastructure.ICollection;
import soliloquy.specs.common.infrastructure.IMap;
import soliloquy.specs.common.infrastructure.IPair;
import soliloquy.specs.common.infrastructure.IReadOnlyMap;
import soliloquy.specs.gamestate.entities.IKeyBinding;
import soliloquy.specs.gamestate.entities.IKeyBindingContext;

import java.util.Iterator;

public class KeyBindingContextArchetype implements IKeyBindingContext {

    @Override
    public String getInterfaceName() {
        return IKeyBindingContext.class.getCanonicalName();
    }

    @Override
    public boolean getBlocksAllLowerBindings() {
        return false;
    }

    @Override
    public void setBlocksAllLowerBindings(boolean b) {

    }

    @Override
    public void clear() {

    }

    @Override
    public void put(Character character, IKeyBinding iKeyBinding) throws IllegalArgumentException {

    }

    @Override
    public void putAll(ICollection<IPair<Character, IKeyBinding>> iCollection) throws IllegalArgumentException {

    }

    @Override
    public IKeyBinding removeByKey(Character character) {
        return null;
    }

    @Override
    public boolean removeByKeyAndValue(Character character, IKeyBinding iKeyBinding) {
        return false;
    }

    @Override
    public ICollection<IFunction<IPair<Character, IKeyBinding>, String>> validators() {
        return null;
    }

    @Override
    public IReadOnlyMap<Character, IKeyBinding> readOnlyRepresentation() {
        return null;
    }

    @Override
    public boolean containsKey(Character character) {
        return false;
    }

    @Override
    public boolean containsValue(IKeyBinding iKeyBinding) {
        return false;
    }

    @Override
    public boolean contains(IPair<Character, IKeyBinding> iPair) throws IllegalArgumentException {
        return false;
    }

    @Override
    public boolean equals(ICollection<IKeyBinding> iCollection) throws IllegalArgumentException {
        return false;
    }

    @Override
    public boolean equals(IReadOnlyMap<Character, IKeyBinding> iReadOnlyMap) throws IllegalArgumentException {
        return false;
    }

    @Override
    public IKeyBinding get(Character character) throws IllegalArgumentException, IllegalStateException {
        return null;
    }

    @Override
    public ICollection<Character> getKeys() {
        return null;
    }

    @Override
    public ICollection<IKeyBinding> getValues() {
        return null;
    }

    @Override
    public ICollection<Character> indicesOf(IKeyBinding iKeyBinding) {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean itemExists(Character character) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Iterator<IPair<Character, IKeyBinding>> iterator() {
        return null;
    }

    @Override
    public IMap<Character, IKeyBinding> makeClone() {
        return null;
    }

    @Override
    public Character getFirstArchetype() throws IllegalStateException {
        return null;
    }

    @Override
    public IKeyBinding getSecondArchetype() throws IllegalStateException {
        return null;
    }

    @Override
    public String getUnparameterizedInterfaceName() {
        return null;
    }
}

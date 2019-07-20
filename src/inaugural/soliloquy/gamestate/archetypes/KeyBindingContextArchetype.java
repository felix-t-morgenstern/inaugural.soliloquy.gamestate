package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.entities.Function;
import soliloquy.specs.common.infrastructure.*;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.KeyBinding;
import soliloquy.specs.gamestate.entities.KeyBindingContext;

import java.util.Iterator;

public class KeyBindingContextArchetype implements KeyBindingContext {
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
    public void put(Character character, KeyBinding keyBinding) throws IllegalArgumentException {

    }

    @Override
    public void putAll(ReadableCollection<Pair<Character, KeyBinding>> collection) throws IllegalArgumentException {

    }

    @Override
    public KeyBinding removeByKey(Character character) {
        return null;
    }

    @Override
    public boolean removeByKeyAndValue(Character character, KeyBinding keyBinding) {
        return false;
    }

    @Override
    public Collection<Function<Pair<Character, KeyBinding>, String>> validators() {
        return null;
    }

    @Override
    public ReadableMap<Character, KeyBinding> readOnlyRepresentation() {
        return null;
    }

    @Override
    public boolean containsKey(Character character) {
        return false;
    }

    @Override
    public boolean containsValue(KeyBinding keyBinding) {
        return false;
    }

    @Override
    public boolean contains(Pair<Character, KeyBinding> pair) throws IllegalArgumentException {
        return false;
    }

    @Override
    public boolean equals(ReadableCollection<KeyBinding> collection) throws IllegalArgumentException {
        return false;
    }

    @Override
    public boolean equals(ReadableMap<Character, KeyBinding> ReadableMap) throws IllegalArgumentException {
        return false;
    }

    @Override
    public KeyBinding get(Character character) throws IllegalArgumentException, IllegalStateException {
        return null;
    }

    @Override
    public Collection<Character> getKeys() {
        return null;
    }

    @Override
    public Collection<KeyBinding> getValues() {
        return null;
    }

    @Override
    public Collection<Character> indicesOf(KeyBinding keyBinding) {
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
    public Iterator<Pair<Character, KeyBinding>> iterator() {
        return null;
    }

    @Override
    public Map<Character, KeyBinding> makeClone() {
        return null;
    }

    @Override
    public Character getFirstArchetype() throws IllegalStateException {
        return null;
    }

    @Override
    public KeyBinding getSecondArchetype() throws IllegalStateException {
        return null;
    }

    @Override
    public String getUnparameterizedInterfaceName() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

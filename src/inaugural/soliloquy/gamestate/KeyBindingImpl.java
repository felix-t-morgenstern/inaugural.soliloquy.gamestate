package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.gamestate.entities.KeyBinding;

public class KeyBindingImpl implements KeyBinding {
    private final Collection<Character> BOUND_CHARACTERS;

    private Action _onPress;
    private Action _onRelease;
    private Action _onType;
    private boolean _blocksLowerBindings;

    @SuppressWarnings("ConstantConditions")
    public KeyBindingImpl(CollectionFactory collectionFactory) {
        if (collectionFactory == null) {
            throw new IllegalArgumentException("KeyBindingImpl: collectionFactory cannot be null");
        }
        BOUND_CHARACTERS = collectionFactory.make(' ');
    }

    @Override
    public Collection<Character> boundCharacters() {
        return BOUND_CHARACTERS;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Action getOnPress() {
        return _onPress;
    }

    @Override
    public void setOnPress(Action onPress) {
        _onPress = onPress;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Action getOnRelease() {
        return _onRelease;
    }

    @Override
    public void setOnRelease(Action onRelease) {
        _onRelease = onRelease;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Action getOnType() {
        return _onType;
    }

    @Override
    public void setOnType(Action onType) {
        _onType = onType;
    }

    @Override
    public boolean getBlocksLowerBindings() {
        return _blocksLowerBindings;
    }

    @Override
    public void setBlocksLowerBindings(boolean blocksLowerBindings) {
        _blocksLowerBindings = blocksLowerBindings;
    }

    @Override
    public String getInterfaceName() {
        return KeyBinding.class.getCanonicalName();
    }
}

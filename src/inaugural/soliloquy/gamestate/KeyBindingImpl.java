package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.gamestate.entities.KeyBinding;

public class KeyBindingImpl implements KeyBinding {
    private final List<Character> BOUND_CHARACTERS;

    @SuppressWarnings("rawtypes")
    private Action _onPress;
    @SuppressWarnings("rawtypes")
    private Action _onRelease;
    @SuppressWarnings("rawtypes")
    private Action _onType;
    private boolean _blocksLowerBindings;

    @SuppressWarnings("ConstantConditions")
    public KeyBindingImpl(ListFactory listFactory) {
        BOUND_CHARACTERS = Check.ifNull(listFactory, "listFactory").make(' ');
    }

    // TODO: Ensure that this is a clone
    @Override
    public List<Character> boundCharacters() {
        return BOUND_CHARACTERS.makeClone();
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

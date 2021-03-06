package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.gamestate.archetypes.KeyBindingContextArchetype;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.gamestate.entities.KeyBinding;
import soliloquy.specs.gamestate.entities.KeyBindingContext;
import soliloquy.specs.gamestate.entities.KeyPressListener;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Consumer;

public class KeyPressListenerImpl implements KeyPressListener {
    private final Map<Integer, KeyBindingContext> CONTEXTS;

    private static final KeyBindingContext ARCHETYPE = new KeyBindingContextArchetype();

    @SuppressWarnings("ConstantConditions")
    public KeyPressListenerImpl(MapFactory mapFactory) {
        if (mapFactory == null) {
            throw new IllegalArgumentException("KeyPressListenerImpl: mapFactory cannot be null");
        }
        CONTEXTS = mapFactory.make(0, ARCHETYPE);
    }

    @Override
    public Map<Integer, KeyBindingContext> contexts() {
        return CONTEXTS;
    }

    @Override
    public String getInterfaceName() {
        return KeyPressListener.class.getCanonicalName();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        handleKeyEvent(e, binding -> binding.getOnType().run(null));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        handleKeyEvent(e, binding -> binding.getOnPress().run(null));
    }

    @Override
    public void keyReleased(KeyEvent e) {
        handleKeyEvent(e, binding -> binding.getOnRelease().run(null));
    }

    private void handleKeyEvent(KeyEvent e, Consumer<KeyBinding> onEvent) {
        ArrayList<Integer> indicesArr = new ArrayList<>(CONTEXTS.keySet());
        Collections.sort(indicesArr);
        for(Integer index : indicesArr) {
            KeyBindingContext context = CONTEXTS.get(index);
            List<KeyBinding> bindings = context.bindings();
            for(KeyBinding binding : bindings) {
                if (binding.boundCharacters().contains(e.getKeyChar())) {
                    onEvent.accept(binding);
                    if (binding.getBlocksLowerBindings()) {
                        return;
                    }
                }
            }
            if (context.getBlocksAllLowerBindings()) {
                return;
            }
        }
    }
}

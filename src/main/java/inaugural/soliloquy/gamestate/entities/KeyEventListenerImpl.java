package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.timing.TimestampValidator;
import soliloquy.specs.gamestate.entities.KeyBinding;
import soliloquy.specs.gamestate.entities.KeyBindingContext;
import soliloquy.specs.gamestate.entities.KeyEventListener;

import java.util.*;
import java.util.function.Consumer;

public class KeyEventListenerImpl implements KeyEventListener {
    private final TreeMap<Integer, List<KeyBindingContext>> CONTEXTS;
    private final TimestampValidator TIMESTAMP_VALIDATOR;
    private final HashMap<KeyBindingContext, Integer> PRIORITIES_BY_CONTEXTS;

    public KeyEventListenerImpl(Long mostRecentTimestamp) {
        CONTEXTS = new TreeMap<>();
        TIMESTAMP_VALIDATOR = new TimestampValidator(mostRecentTimestamp);
        PRIORITIES_BY_CONTEXTS = new HashMap<>();
    }

    @Override
    public String getInterfaceName() {
        return KeyEventListener.class.getCanonicalName();
    }

    @Override
    public void addContext(KeyBindingContext keyBindingContext, int priority)
            throws IllegalArgumentException {
        Check.ifNull(keyBindingContext, "keyBindingContext");
        removeContext(keyBindingContext);
        if (!CONTEXTS.containsKey(priority)) {
            ArrayList<KeyBindingContext> contextsAtPriority = new ArrayList<>();
            contextsAtPriority.add(keyBindingContext);
            CONTEXTS.put(priority, contextsAtPriority);
        }
        else {
            CONTEXTS.get(priority).add(keyBindingContext);
        }
        PRIORITIES_BY_CONTEXTS.put(keyBindingContext, priority);
    }

    @Override
    public void removeContext(KeyBindingContext keyBindingContext)
            throws IllegalArgumentException {
        Check.ifNull(keyBindingContext, "keyBindingContext");
        if (PRIORITIES_BY_CONTEXTS.containsKey(keyBindingContext)) {
            int priority = PRIORITIES_BY_CONTEXTS.get(keyBindingContext);
            List<KeyBindingContext> contextsAtPriority = CONTEXTS.get(priority);
            contextsAtPriority.remove(keyBindingContext);
            if (contextsAtPriority.isEmpty()) {
                CONTEXTS.remove(priority);
            }
            PRIORITIES_BY_CONTEXTS.remove(keyBindingContext);
        }
    }

    @Override
    public java.util.List<Character> activeKeysRepresentation() {
        ArrayList<Character> representation = new ArrayList<>();
        loopOverBindings(null, null, c -> {
            if (!representation.contains(c)) {
                representation.add(c);
            }
        });
        return representation;
    }

    @Override
    public void press(char c, long timestamp) {
        TIMESTAMP_VALIDATOR.validateTimestamp(timestamp);
        handleKeyEvent(c, binding -> binding.press(timestamp));
    }

    @Override
    public void release(char c, long timestamp) {
        TIMESTAMP_VALIDATOR.validateTimestamp(timestamp);
        handleKeyEvent(c, binding -> binding.release(timestamp));
    }

    // TODO: Ensure that the values in CONTEXT are also deeply cloned in KeyEventListenerImpl.contextsRepresentation
    @Override
    public Map<Integer, List<KeyBindingContext>> contextsRepresentation() {
        return new HashMap<>(CONTEXTS);
    }

    private void handleKeyEvent(char c, Consumer<KeyBinding> onEvent) {
        loopOverBindings(c, onEvent, null);
    }

    private void loopOverBindings(Character c, Consumer<KeyBinding> handleEvent,
                                  Consumer<Character> handleCharacter) {
        for (List<KeyBindingContext> contexts : CONTEXTS.values()) {
            for (KeyBindingContext context : contexts) {
                List<KeyBinding> bindings = context.bindings();
                for (KeyBinding binding : bindings) {
                    if (handleCharacter != null) {
                        binding.boundCharacters().forEach(handleCharacter);
                    }
                    if (handleEvent != null) {
                        if (binding.boundCharacters().contains(c)) {
                            handleEvent.accept(binding);
                        }
                    }
                    if (binding.getBlocksLowerBindings()) {
                        break;
                    }
                }
                if (context.getBlocksAllLowerBindings()) {
                    return;
                }
            }
        }
    }
}

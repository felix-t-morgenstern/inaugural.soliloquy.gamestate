package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.Map;

public class FakeMapFactory implements MapFactory {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public <K, V> Map<K, V> make(K archetype1, V archetype2) {
        return new FakeMap(archetype1, archetype2);
    }

    @Override
    public String getInterfaceName() {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }
}

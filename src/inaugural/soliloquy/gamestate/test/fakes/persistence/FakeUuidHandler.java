package inaugural.soliloquy.gamestate.test.fakes.persistence;

import java.util.UUID;

public class FakeUuidHandler extends FakeTypeHandler<UUID> {
    @Override
    public String typeName() {
        return "UUID";
    }

    @Override
    protected UUID generateInstance() {
        return UUID.randomUUID();
    }
}

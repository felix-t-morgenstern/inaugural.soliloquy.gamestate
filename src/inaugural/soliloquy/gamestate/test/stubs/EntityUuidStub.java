package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.valueobjects.EntityUuid;

import java.util.UUID;

public class EntityUuidStub implements EntityUuid {
    private final UUID UUID;

    public EntityUuidStub() {
        UUID = null;
    }

    public EntityUuidStub(String uuid) {
        UUID = java.util.UUID.fromString(uuid);
    }

    @Override
    public long getMostSignificantBits() {
        if (UUID == null) {
            return 0;
        }
        else {
            return UUID.getMostSignificantBits();
        }
    }

    @Override
    public long getLeastSignificantBits() {
        if (UUID == null) {
            return 0;
        }
        else {
            return UUID.getLeastSignificantBits();
        }
    }

    public String toString() {
        return UUID.toString();
    }

    @Override
    public boolean equals(Object comparand) {
        return comparand instanceof EntityUuid
                && UUID.getMostSignificantBits() == ((EntityUuid) comparand).getMostSignificantBits()
                && UUID.getLeastSignificantBits() == ((EntityUuid) comparand).getLeastSignificantBits();
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

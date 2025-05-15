package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.gamestate.entities.Deletable;

public class FakeDeletable implements Deletable {
    public boolean Deleted;

    @Override
    public void delete() throws IllegalStateException {
        Deleted = true;
    }

    @Override
    public boolean isDeleted() {
        return Deleted;
    }
}

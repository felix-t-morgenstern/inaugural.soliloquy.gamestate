package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.shared.HasId;
import soliloquy.specs.common.shared.HasName;

public class FakeHasIdAndName implements HasId, HasName {
    public String Id;
    public String Name;

    public FakeHasIdAndName(String id, String name) {
        Id = id;
        Name = name;
    }

    @Override
    public String id() throws IllegalStateException {
        return Id;
    }

    @Override
    public String getName() {
        return Name;
    }

    @Override
    public void setName(String name) {
        Name = name;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

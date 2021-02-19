package inaugural.soliloquy.gamestate.test.fakes.persistence;

import inaugural.soliloquy.gamestate.test.fakes.FakeCharacter;
import soliloquy.specs.gamestate.entities.Character;

public class FakePersistentCharacterHandler extends FakePersistentValueTypeHandler<Character> {
    @Override
    public String typeName() {
        return "Character";
    }

    @Override
    protected Character generateInstance() {
        return new FakeCharacter();
    }
}

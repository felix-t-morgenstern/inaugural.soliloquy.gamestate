package inaugural.soliloquy.gamestate.test.fakes.persistence;

import inaugural.soliloquy.gamestate.test.fakes.FakeCharacter;
import soliloquy.specs.gamestate.entities.Character;

public class FakeCharacterHandler extends FakeTypeHandler<Character> {
    @Override
    public String typeName() {
        return "Character";
    }

    @Override
    protected Character generateInstance() {
        return new FakeCharacter();
    }
}

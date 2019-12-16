package inaugural.soliloquy.gamestate.test.stubs.persistenttypehandlers;

import inaugural.soliloquy.gamestate.test.stubs.CharacterStub;
import soliloquy.specs.gamestate.entities.Character;

public class PersistentCharacterHandlerStub extends PersistentValueTypeHandlerStub<Character> {
    @Override
    public String typeName() {
        return "Character";
    }

    @Override
    protected Character generateInstance() {
        return new CharacterStub();
    }
}

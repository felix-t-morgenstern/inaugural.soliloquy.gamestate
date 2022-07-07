package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.factories.CharacterFactory;
import soliloquy.specs.ruleset.entities.CharacterType;

import java.util.UUID;

public class FakeCharacterFactory implements CharacterFactory {
    @Override
    public Character make(CharacterType characterType) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Character make(CharacterType characterType, UUID uuid, VariableCache data)
            throws IllegalArgumentException {
        return new FakeCharacter(uuid, characterType, data);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

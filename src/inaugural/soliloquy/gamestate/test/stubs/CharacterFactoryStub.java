package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.factories.CharacterFactory;
import soliloquy.specs.ruleset.entities.CharacterType;

public class CharacterFactoryStub implements CharacterFactory {
    @Override
    public Character make(CharacterType characterType) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Character make(CharacterType characterType, EntityUuid entityUuid, GenericParamsSet data) throws IllegalArgumentException {
        return new CharacterStub(entityUuid, characterType, data);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

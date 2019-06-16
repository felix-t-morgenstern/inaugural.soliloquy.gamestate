package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.valueobjects.ICoordinate;
import soliloquy.specs.common.valueobjects.IMap;
import soliloquy.specs.game.IGame;
import soliloquy.specs.gamestate.entities.ICharacter;
import soliloquy.specs.logger.ILogger;
import soliloquy.specs.ruleset.entities.IFixtureType;
import soliloquy.specs.ruleset.entities.abilities.IActiveAbility;
import soliloquy.specs.ruleset.entities.abilities.IReactiveAbility;
import soliloquy.specs.sprites.entities.ISprite;

public class FixtureTypeStub implements IFixtureType {
    @Override
    public boolean isContainer() throws IllegalStateException {
        return false;
    }

    @Override
    public ICoordinate defaultOffset() throws IllegalStateException {
        return null;
    }

    @Override
    public String id() throws IllegalStateException {
        return null;
    }

    @Override
    public ISprite sprite() {
        return null;
    }

    @Override
    public boolean onStep(ICharacter iCharacter) {
        return false;
    }

    @Override
    public boolean canStep(ICharacter iCharacter) {
        return false;
    }

    @Override
    public IMap<String, IActiveAbility> activeAbilities() throws IllegalStateException {
        return null;
    }

    @Override
    public IMap<String, IReactiveAbility> reactiveAbilities() throws IllegalStateException {
        return null;
    }

    @Override
    public IGame game() {
        return null;
    }

    @Override
    public ILogger logger() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

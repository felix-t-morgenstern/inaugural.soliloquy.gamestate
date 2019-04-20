package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.common.specs.ICoordinate;
import soliloquy.common.specs.IMap;
import soliloquy.game.primary.specs.IGame;
import soliloquy.gamestate.specs.ICharacter;
import soliloquy.logger.specs.ILogger;
import soliloquy.ruleset.gameentities.abilities.specs.IActiveAbility;
import soliloquy.ruleset.gameentities.abilities.specs.IReactiveAbility;
import soliloquy.ruleset.gameentities.specs.IFixtureType;
import soliloquy.sprites.specs.ISprite;

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

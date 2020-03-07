package inaugural.soliloquy.gamestate.test.fakes;

import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.ruleset.gameconcepts.ActiveCharactersProvider;

public class FakeActiveCharactersProvider implements ActiveCharactersProvider {
    public final Collection<Pair<Character,VariableCache>> ActiveCharacters;

    private static final Character CHARACTER_1 = new FakeCharacter();
    private static final VariableCache DATA_1 = new VariableCacheStub();
    private static final Character CHARACTER_2 = new FakeCharacter();
    private static final VariableCache DATA_2 = new VariableCacheStub();
    private static final Character CHARACTER_3 = new FakeCharacter();
    private static final VariableCache DATA_3 = new VariableCacheStub();

    public FakeActiveCharactersProvider() {
        ActiveCharacters = new FakeCollection<>();

        ActiveCharacters.add(new FakePair<>(CHARACTER_1, DATA_1));
        ActiveCharacters.add(new FakePair<>(CHARACTER_2, DATA_2));
        ActiveCharacters.add(new FakePair<>(CHARACTER_3, DATA_3));
    }

    @Override
    public ReadableCollection<Pair<Character, VariableCache>> activeCharacters() {
        return ActiveCharacters.representation();
    }
}

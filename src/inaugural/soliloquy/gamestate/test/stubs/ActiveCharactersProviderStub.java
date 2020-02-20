package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.ruleset.gameconcepts.ActiveCharactersProvider;

public class ActiveCharactersProviderStub implements ActiveCharactersProvider {
    public final Collection<Pair<Character,VariableCache>> ActiveCharacters;

    private static final Character CHARACTER_1 = new CharacterStub();
    private static final VariableCache DATA_1 = new VariableCacheStub();
    private static final Character CHARACTER_2 = new CharacterStub();
    private static final VariableCache DATA_2 = new VariableCacheStub();
    private static final Character CHARACTER_3 = new CharacterStub();
    private static final VariableCache DATA_3 = new VariableCacheStub();

    public int _numberOfTimesToProvideEmptyCollection;

    public ActiveCharactersProviderStub() {
        ActiveCharacters = new CollectionStub<>();

        ActiveCharacters.add(new PairStub<>(CHARACTER_1, DATA_1));
        ActiveCharacters.add(new PairStub<>(CHARACTER_2, DATA_2));
        ActiveCharacters.add(new PairStub<>(CHARACTER_3, DATA_3));
    }

    @Override
    public ReadableCollection<Pair<Character, VariableCache>> activeCharacters() {
        if (_numberOfTimesToProvideEmptyCollection > 0) {
            _numberOfTimesToProvideEmptyCollection--;
            return new ReadableCollectionStub<>();
        }
        return ActiveCharacters.representation();
    }
}

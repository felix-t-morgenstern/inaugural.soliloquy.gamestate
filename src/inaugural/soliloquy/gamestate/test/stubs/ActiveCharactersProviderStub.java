package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.ruleset.gameconcepts.ActiveCharactersProvider;

public class ActiveCharactersProviderStub implements ActiveCharactersProvider {
    public static ReadableCollection<Pair<Character,VariableCache>> ACTIVE_CHARACTERS;
    public static final Character CHARACTER_1 = new CharacterStub();
    public static final VariableCache DATA_1 = new VariableCacheStub();
    public static final Character CHARACTER_2 = new CharacterStub();
    public static final VariableCache DATA_2 = new VariableCacheStub();
    public static final Character CHARACTER_3 = new CharacterStub();
    public static final VariableCache DATA_3 = new VariableCacheStub();

    public int _numberOfTimesToProvideEmptyCollection;

    public ActiveCharactersProviderStub() {
        Collection<Pair<Character,VariableCache>> activeCharacters = new CollectionStub<>();

        activeCharacters.add(new PairStub<>(CHARACTER_1, DATA_1));
        activeCharacters.add(new PairStub<>(CHARACTER_2, DATA_2));
        activeCharacters.add(new PairStub<>(CHARACTER_3, DATA_3));

        ACTIVE_CHARACTERS = activeCharacters.readOnlyRepresentation();
    }

    @Override
    public ReadableCollection<Pair<Character, VariableCache>> activeCharacters() {
        if (_numberOfTimesToProvideEmptyCollection > 0) {
            _numberOfTimesToProvideEmptyCollection--;
            return new ReadableCollectionStub<>();
        }
        return ACTIVE_CHARACTERS;
    }
}

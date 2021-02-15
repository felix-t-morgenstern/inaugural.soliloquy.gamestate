package inaugural.soliloquy.gamestate.test.spydoubles;

import inaugural.soliloquy.gamestate.test.fakes.FakePair;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.ruleset.gameconcepts.TurnHandling;

import java.util.ArrayList;

public class TurnHandlingSpyDouble implements TurnHandling {
    public final ArrayList<Pair<Character, Integer>> TurnStarts = new ArrayList<>();
    public final ArrayList<Pair<Character, Integer>> TurnEnds = new ArrayList<>();

    @Override
    public void onTurnStart(Character character, int numberOfTurns)
            throws IllegalArgumentException {
        TurnStarts.add(new FakePair<>(character, numberOfTurns));
    }

    @Override
    public void onTurnEnd(Character character, int numberOfTurns) throws IllegalArgumentException {
        TurnEnds.add(new FakePair<>(character, numberOfTurns));
    }
}

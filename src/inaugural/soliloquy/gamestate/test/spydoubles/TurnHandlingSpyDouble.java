package inaugural.soliloquy.gamestate.test.spydoubles;

import inaugural.soliloquy.gamestate.test.fakes.FakeReadablePair;
import soliloquy.specs.common.infrastructure.ReadablePair;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.ruleset.gameconcepts.TurnHandling;

import java.util.ArrayList;

public class TurnHandlingSpyDouble implements TurnHandling {
    public final ArrayList<ReadablePair<Character, Integer>> TurnStarts = new ArrayList<>();
    public final ArrayList<ReadablePair<Character, Integer>> TurnEnds = new ArrayList<>();

    @Override
    public void onTurnStart(Character character, int numberOfTurns)
            throws IllegalArgumentException {
        TurnStarts.add(new FakeReadablePair<>(character, numberOfTurns));
    }

    @Override
    public void onTurnEnd(Character character, int numberOfTurns) throws IllegalArgumentException {
        TurnEnds.add(new FakeReadablePair<>(character, numberOfTurns));
    }
}

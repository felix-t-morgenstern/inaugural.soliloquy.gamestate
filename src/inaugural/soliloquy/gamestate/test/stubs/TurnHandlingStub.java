package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.ReadablePair;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.ruleset.gameconcepts.TurnHandling;

import java.util.ArrayList;

public class TurnHandlingStub implements TurnHandling {
    public final ArrayList<ReadablePair<Character, Integer>> TurnStarts = new ArrayList<>();
    public final ArrayList<ReadablePair<Character, Integer>> TurnEnds = new ArrayList<>();

    @Override
    public void onTurnStart(Character character, int numberOfTurns)
            throws IllegalArgumentException {
        TurnStarts.add(new ReadablePairStub<>(character, numberOfTurns));
    }

    @Override
    public void onTurnEnd(Character character, int numberOfTurns) throws IllegalArgumentException {
        TurnEnds.add(new ReadablePairStub<>(character, numberOfTurns));
    }
}

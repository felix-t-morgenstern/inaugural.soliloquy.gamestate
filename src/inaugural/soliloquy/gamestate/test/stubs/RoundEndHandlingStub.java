package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Timer;
import soliloquy.specs.ruleset.gameconcepts.RoundEndHandling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoundEndHandlingStub implements RoundEndHandling {
    public Map<Character, Integer> TurnStarts;
    public Map<Character, Integer> TurnEnds;
    public Map<Character, Integer> RoundEnds;
    public List<Timer> Timers;
    public Character PrevActiveCharacter;
    public Character NewActiveCharacter;

    @Override
    public void runEndOfRoundEvents(Map<Character, Integer> turnStarts,
                                    Map<Character, Integer> turnEnds,
                                    Map<Character, Integer> roundEnds,
                                    List<Timer> timers,
                                    Character prevActiveCharacter,
                                    Character newActiveCharacter)
            throws IllegalArgumentException {
        TurnStarts = new HashMap<>(turnStarts);
        TurnEnds = new HashMap<>(turnEnds);
        RoundEnds = new HashMap<>(roundEnds);
        Timers = new ArrayList<>(timers);
        PrevActiveCharacter = prevActiveCharacter;
        NewActiveCharacter = newActiveCharacter;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

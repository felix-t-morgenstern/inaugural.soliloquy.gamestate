package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.CharacterStatusEffectsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterStatusEffects;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.ruleset.entities.character.StatusEffectType;

import static inaugural.soliloquy.tools.random.Random.randomIntWithInclusiveFloor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CharacterStatusEffectsImplTests {
    private final int EFFECT_1_LEVEL = randomIntWithInclusiveFloor(1);
    private final int EFFECT_2_LEVEL = randomIntWithInclusiveFloor(1);

    @Mock private StatusEffectType mockStatusEffectType2;
    @Mock private StatusEffectType mockStatusEffectType1;
    @Mock private Character mockCharacter;

    private CharacterStatusEffects characterStatusEffects;

    @BeforeEach
    public void setUp() {
        mockStatusEffectType1 = mock(StatusEffectType.class);
        mockStatusEffectType2 = mock(StatusEffectType.class);
        mockCharacter = mock(Character.class);
        lenient().when(mockCharacter.isDeleted()).thenReturn(false);

        characterStatusEffects = new CharacterStatusEffectsImpl(mockCharacter);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> new CharacterStatusEffectsImpl(null));
    }

    @Test
    public void testGetAndSetStatusEffectLevel() {
        var statusEffectLevel = randomIntWithInclusiveFloor(1);

        assertEquals(0, (int) characterStatusEffects.getStatusEffectLevel(mockStatusEffectType2));

        characterStatusEffects.setStatusEffectLevel(mockStatusEffectType2, statusEffectLevel);

        assertEquals(statusEffectLevel, (int) characterStatusEffects.getStatusEffectLevel(mockStatusEffectType2));
    }

    @Test
    public void testGetAllStatusEffects() {
        characterStatusEffects.setStatusEffectLevel(mockStatusEffectType1, EFFECT_1_LEVEL);
        characterStatusEffects.setStatusEffectLevel(mockStatusEffectType2, EFFECT_2_LEVEL);

        var statusEffectLevels = characterStatusEffects.representation();

        assertEquals(2, statusEffectLevels.size());
        assertEquals(EFFECT_1_LEVEL, (int) statusEffectLevels.get(mockStatusEffectType1));
        assertEquals(EFFECT_2_LEVEL, (int) statusEffectLevels.get(mockStatusEffectType2));
    }

    @Test
    public void testClearStatusEffects() {
        characterStatusEffects.setStatusEffectLevel(mockStatusEffectType1, EFFECT_1_LEVEL);
        characterStatusEffects.setStatusEffectLevel(mockStatusEffectType2, EFFECT_2_LEVEL);
        var statusEffectLevels = characterStatusEffects.representation();
        assertEquals(2, statusEffectLevels.size());

        characterStatusEffects.clearStatusEffects();
        statusEffectLevels = characterStatusEffects.representation();

        assertEquals(0, statusEffectLevels.size());
    }

    @Test
    public void testGetAndSetInvalidStatusEffectType() {
        assertThrows(IllegalArgumentException.class,
                () -> characterStatusEffects.getStatusEffectLevel(null));
        assertThrows(IllegalArgumentException.class,
                () -> characterStatusEffects.setStatusEffectLevel(null, 0));
    }

    @Test
    public void testStatusEffectIsRemovedWhenSetToZero() {
        characterStatusEffects.setStatusEffectLevel(mockStatusEffectType2, 1);

        assertEquals(1, characterStatusEffects.representation().size());

        characterStatusEffects.setStatusEffectLevel(mockStatusEffectType2, 0);

        assertEquals(0, characterStatusEffects.representation().size());
    }

    @Test
    public void testEnforceDeletionInvariant() {
        characterStatusEffects.delete();

        assertThrows(EntityDeletedException.class,
                () -> characterStatusEffects.getStatusEffectLevel(mockStatusEffectType2));
        assertThrows(EntityDeletedException.class,
                () -> characterStatusEffects.representation());
        assertThrows(EntityDeletedException.class,
                () -> characterStatusEffects.setStatusEffectLevel(mockStatusEffectType2, 0));
        assertThrows(EntityDeletedException.class,
                () -> characterStatusEffects.clearStatusEffects());
    }
}

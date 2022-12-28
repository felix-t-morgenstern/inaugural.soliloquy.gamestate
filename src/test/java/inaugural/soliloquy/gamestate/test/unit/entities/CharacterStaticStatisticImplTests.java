package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.CharacterStaticStatisticImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeCharacter;
import inaugural.soliloquy.gamestate.test.spydoubles.CharacterStatisticCalculationSpyDouble;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterStatistic;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.ruleset.entities.CharacterStaticStatisticType;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CharacterStaticStatisticImplTests {
    private final Character CHARACTER = new FakeCharacter();
    private final VariableCache DATA = new VariableCacheStub();
    private final CharacterStatisticCalculationSpyDouble CHARACTER_ATTRIBUTE_CALCULATION =
            new CharacterStatisticCalculationSpyDouble();

    @Mock private CharacterStaticStatisticType mockStatType;

    private CharacterStatistic<CharacterStaticStatisticType> characterStatistic;

    @BeforeEach
    void setUp() {
        mockStatType = mock(CharacterStaticStatisticType.class);

        characterStatistic = new CharacterStaticStatisticImpl(CHARACTER, mockStatType, DATA,
                CHARACTER_ATTRIBUTE_CALCULATION);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new CharacterStaticStatisticImpl(null,
                mockStatType, DATA, CHARACTER_ATTRIBUTE_CALCULATION));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterStaticStatisticImpl(CHARACTER,
                        null, DATA, CHARACTER_ATTRIBUTE_CALCULATION));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterStaticStatisticImpl(CHARACTER,
                        mockStatType, DATA, null));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterStaticStatisticImpl(CHARACTER,
                        mockStatType, null, CHARACTER_ATTRIBUTE_CALCULATION));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(CharacterStatistic.class.getCanonicalName() + "<" +
                        CharacterStaticStatisticType.class.getCanonicalName() + ">",
                characterStatistic.getInterfaceName());
    }

    @Test
    void testType() {
        assertEquals(mockStatType, characterStatistic.type());
    }

    @Test
    void testData() {
        assertSame(DATA, characterStatistic.data());
    }

    @Test
    void testCalculateValue() {
        characterStatistic.calculate();

        assertSame(CHARACTER, CHARACTER_ATTRIBUTE_CALCULATION._character);
        assertSame(mockStatType, CHARACTER_ATTRIBUTE_CALCULATION._statisticType);
        assertEquals(CharacterStatisticCalculationSpyDouble.VALUE,
                characterStatistic.totalValue());
        Map<String, Integer> representation = characterStatistic.representation();
        assertEquals(CharacterStatisticCalculationSpyDouble.MODIFIERS, representation);
        assertEquals(CharacterStatisticCalculationSpyDouble.MODIFIERS.size(),
                representation.size());
        CharacterStatisticCalculationSpyDouble.MODIFIERS.forEach((modifierType, value) ->
                assertEquals(value, representation.get(modifierType)));
    }

    @Test
    void testThrowsIllegalStateExceptionForDeadCharacter() {
        CHARACTER.delete();

        assertThrows(IllegalStateException.class, () -> characterStatistic.type());
        assertThrows(IllegalStateException.class, () -> characterStatistic.data());
        assertThrows(IllegalStateException.class, () -> characterStatistic.getInterfaceName());
        assertThrows(IllegalStateException.class, () -> characterStatistic.calculate());
        assertThrows(IllegalStateException.class, () -> characterStatistic.representation());
        assertThrows(IllegalStateException.class, () -> characterStatistic.totalValue());
    }

    @Test
    void testEnforceDeletionInvariant() {
        characterStatistic.delete();

        assertThrows(EntityDeletedException.class, () -> characterStatistic.type());
        assertThrows(EntityDeletedException.class, () -> characterStatistic.data());
        assertThrows(EntityDeletedException.class, () -> characterStatistic.getInterfaceName());
        assertThrows(EntityDeletedException.class, () -> characterStatistic.calculate());
        assertThrows(EntityDeletedException.class, () -> characterStatistic.representation());
        assertThrows(EntityDeletedException.class, () -> characterStatistic.totalValue());
    }
}

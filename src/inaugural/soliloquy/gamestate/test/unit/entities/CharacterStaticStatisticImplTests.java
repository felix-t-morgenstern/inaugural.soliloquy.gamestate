package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.CharacterStaticStatisticImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeCharacter;
import inaugural.soliloquy.gamestate.test.fakes.FakeCharacterStaticStatisticType;
import inaugural.soliloquy.gamestate.test.spydoubles.CharacterStatisticCalculationSpyDouble;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterStatistic;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.ruleset.entities.CharacterStaticStatisticType;

import static org.junit.jupiter.api.Assertions.*;

class CharacterStaticStatisticImplTests {
    private final Character CHARACTER = new FakeCharacter();
    private final CharacterStaticStatisticType TYPE =
            new FakeCharacterStaticStatisticType();
    private final VariableCache DATA = new VariableCacheStub();
    private final CharacterStatisticCalculationSpyDouble CHARACTER_ATTRIBUTE_CALCULATION =
            new CharacterStatisticCalculationSpyDouble();

    private CharacterStatistic<CharacterStaticStatisticType> _characterStatistic;

    @BeforeEach
    void setUp() {
        _characterStatistic = new CharacterStaticStatisticImpl(CHARACTER, TYPE, DATA,
                CHARACTER_ATTRIBUTE_CALCULATION);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new CharacterStaticStatisticImpl(null,
                TYPE, DATA, CHARACTER_ATTRIBUTE_CALCULATION));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterStaticStatisticImpl(CHARACTER,
                        null, DATA, CHARACTER_ATTRIBUTE_CALCULATION));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterStaticStatisticImpl(CHARACTER,
                        TYPE, DATA, null));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterStaticStatisticImpl(CHARACTER,
                        TYPE, null, CHARACTER_ATTRIBUTE_CALCULATION));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(CharacterStatistic.class.getCanonicalName() + "<" +
                        CharacterStaticStatisticType.class.getCanonicalName() + ">",
                _characterStatistic.getInterfaceName());
    }

    @Test
    void testType() {
        assertEquals(TYPE, _characterStatistic.type());
    }

    @Test
    void testData() {
        assertSame(DATA, _characterStatistic.data());
    }

    @Test
    void testCalculateValue() {
        _characterStatistic.calculate();

        assertSame(CHARACTER, CHARACTER_ATTRIBUTE_CALCULATION._character);
        assertSame(TYPE, CHARACTER_ATTRIBUTE_CALCULATION._statisticType);
        assertEquals(CharacterStatisticCalculationSpyDouble.VALUE,
                _characterStatistic.totalValue());
        Map<String, Integer> representation = _characterStatistic.representation();
        assertEquals(CharacterStatisticCalculationSpyDouble.MODIFIERS, representation);
        assertEquals(CharacterStatisticCalculationSpyDouble.MODIFIERS.size(),
                representation.size());
        CharacterStatisticCalculationSpyDouble.MODIFIERS.forEach((modifierType, value) ->
                assertEquals(value, representation.get(modifierType)));
    }

    @Test
    void testThrowsIllegalStateExceptionForDeadCharacter() {
        CHARACTER.delete();

        assertThrows(IllegalStateException.class, () -> _characterStatistic.type());
        assertThrows(IllegalStateException.class, () -> _characterStatistic.data());
        assertThrows(IllegalStateException.class, () -> _characterStatistic.getInterfaceName());
        assertThrows(IllegalStateException.class, () -> _characterStatistic.calculate());
        assertThrows(IllegalStateException.class, () -> _characterStatistic.representation());
        assertThrows(IllegalStateException.class, () -> _characterStatistic.totalValue());
    }

    @Test
    void testEnforceDeletionInvariant() {
        _characterStatistic.delete();

        assertThrows(EntityDeletedException.class, () -> _characterStatistic.type());
        assertThrows(EntityDeletedException.class, () -> _characterStatistic.data());
        assertThrows(EntityDeletedException.class, () -> _characterStatistic.getInterfaceName());
        assertThrows(EntityDeletedException.class, () -> _characterStatistic.calculate());
        assertThrows(EntityDeletedException.class, () -> _characterStatistic.representation());
        assertThrows(EntityDeletedException.class, () -> _characterStatistic.totalValue());
    }
}

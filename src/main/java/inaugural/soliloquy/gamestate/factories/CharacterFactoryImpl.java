package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.CharacterImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.factories.*;
import soliloquy.specs.ruleset.entities.character.CharacterType;

import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import static inaugural.soliloquy.tools.collections.Collections.mapOf;

public class CharacterFactoryImpl implements CharacterFactory {
    private final Function<Character, CharacterEvents> CHARACTER_EVENTS_FACTORY;
    private final Function<Character, CharacterEquipmentSlots> CHARACTER_EQUIPMENT_SLOTS_FACTORY;
    private final Function<Character, CharacterInventory> CHARACTER_INVENTORY_FACTORY;
    private final Function<Character, CharacterStatusEffects> CHARACTER_STATUS_EFFECTS_FACTORY;

    public CharacterFactoryImpl(Function<Character, CharacterEvents> characterEventsFactory,
                                Function<Character, CharacterEquipmentSlots> characterEquipmentSlotsFactory,
                                Function<Character, CharacterInventory> characterInventoryFactory,
                                Function<Character, CharacterStatusEffects> characterStatusEffectsFactory) {
        CHARACTER_EVENTS_FACTORY = Check.ifNull(characterEventsFactory, "characterEventsFactory");
        CHARACTER_EQUIPMENT_SLOTS_FACTORY = Check.ifNull(characterEquipmentSlotsFactory,
                "characterEquipmentSlotsFactory");
        CHARACTER_INVENTORY_FACTORY = Check.ifNull(characterInventoryFactory,
                "characterInventoryFactory");
        CHARACTER_STATUS_EFFECTS_FACTORY = Check.ifNull(characterStatusEffectsFactory,
                "characterStatusEffectsFactory");
    }

    @Override
    public Character make(CharacterType characterType) throws IllegalArgumentException {
        return make(characterType, UUID.randomUUID(), mapOf());
    }

    @Override
    public Character make(CharacterType characterType, UUID uuid, Map<String, Object> data)
            throws IllegalArgumentException {
        Check.ifNull(characterType, "characterType");
        Check.ifNull(uuid, "uuid");
        return new CharacterImpl(
                uuid,
                characterType,
                CHARACTER_EVENTS_FACTORY,
                CHARACTER_EQUIPMENT_SLOTS_FACTORY,
                CHARACTER_INVENTORY_FACTORY,
                CHARACTER_STATUS_EFFECTS_FACTORY,
                data);
    }
}

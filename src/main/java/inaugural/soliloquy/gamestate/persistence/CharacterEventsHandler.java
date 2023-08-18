package inaugural.soliloquy.gamestate.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.shared.HasId;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEvents;
import soliloquy.specs.gamestate.entities.CharacterEvents.CharacterEvent;
import soliloquy.specs.gamestate.factories.CharacterEventsFactory;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static inaugural.soliloquy.tools.collections.Collections.arrayOf;
import static inaugural.soliloquy.tools.generic.Archetypes.generateSimpleArchetype;

public class CharacterEventsHandler extends AbstractTypeHandler<CharacterEvents> {
    private final Function<String, CharacterEvent> GET_CHARACTER_EVENT;
    private final CharacterEventsFactory CHARACTER_EVENTS_FACTORY;
    private final Character CHARACTER_ARCHETYPE = generateSimpleArchetype(Character.class);

    public CharacterEventsHandler(Function<String, CharacterEvent> getCharacterEvent,
                                  CharacterEventsFactory characterEventsFactory) {
        super(generateSimpleArchetype(CharacterEvents.class));
        GET_CHARACTER_EVENT = Check.ifNull(getCharacterEvent, "getCharacterEvent");
        CHARACTER_EVENTS_FACTORY = Check.ifNull(characterEventsFactory, "characterEventsFactory");
    }

    @Override
    public CharacterEvents read(String data) throws IllegalArgumentException {
        Check.ifNullOrEmpty(data, "data");

        var characterEvents = CHARACTER_EVENTS_FACTORY.make(CHARACTER_ARCHETYPE);

        var triggerWithEventsDtos = JSON.fromJson(data, DTO[].class);
        for (var triggerWithEventsDto : triggerWithEventsDtos) {
            for (var eventId : triggerWithEventsDto.eventIds) {
                characterEvents.addEvent(arrayOf(triggerWithEventsDto.trigger),
                        GET_CHARACTER_EVENT.apply(eventId));
            }
        }

        return characterEvents;
    }

    @Override
    public String write(CharacterEvents characterEvents) {
        Check.ifNull(characterEvents, "characterEvents");

        var representation = characterEvents.representation();
        var triggersWithEvents = new DTO[representation.size()];
        var index = new AtomicInteger(0);
        representation.forEach((key, value) -> {
            var triggerWithEventsDto = new DTO();
            triggerWithEventsDto.trigger = key;
            triggerWithEventsDto.eventIds = value.stream().map(HasId::id).toArray(String[]::new);
            triggersWithEvents[index.getAndIncrement()] = triggerWithEventsDto;
        });

        return JSON.toJson(triggersWithEvents);
    }

    private static class DTO {
        String trigger;
        String[] eventIds;
    }
}

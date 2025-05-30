package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.WallSegmentImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.WallSegment;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.gamestate.factories.WallSegmentFactory;
import soliloquy.specs.ruleset.entities.WallSegmentType;

import java.util.Map;
import java.util.function.Function;

public class WallSegmentFactoryImpl implements WallSegmentFactory {
    @SuppressWarnings("rawtypes") private final TypeHandler<Map> MAP_HANDLER;
    private final Function<String, WallSegmentType> GET_SEGMENT_TYPE;
    private final Function<String, GameMovementEvent> GET_MOVEMENT_EVENT;
    private final Function<String, GameAbilityEvent> GET_ABILITY_EVENT;

    @SuppressWarnings("ConstantConditions")
    public WallSegmentFactoryImpl(@SuppressWarnings("rawtypes") TypeHandler<Map> mapHandler,
                                  Function<String, WallSegmentType> getSegmentType,
                                  Function<String, GameMovementEvent> getMovementEvent,
                                  Function<String, GameAbilityEvent> getAbilityEvent) {
        MAP_HANDLER = Check.ifNull(mapHandler, "mapHandler");
        GET_SEGMENT_TYPE = Check.ifNull(getSegmentType, "getSegmentType");
        GET_MOVEMENT_EVENT = Check.ifNull(getMovementEvent, "getMovementEvent");
        GET_ABILITY_EVENT = Check.ifNull(getAbilityEvent, "getAbilityEvent");
    }

    @Override
    public WallSegment make(Definition definition) throws IllegalArgumentException {
        Check.ifNull(definition, "definition");

        Check.ifNullOrEmpty(definition.typeId, "definition.typeId");
        var segmentType = GET_SEGMENT_TYPE.apply(definition.typeId);
        if (segmentType == null) {
            throw new IllegalArgumentException(
                    "WallSegmentFactoryImpl.make: definition.typeId (" + definition.typeId +
                            ") does not correspond to a valid WallSegmentType");
        }

        //noinspection unchecked
        var data = (Map<String, Object>) MAP_HANDLER.read(definition.data);

        var output = new WallSegmentImpl(data);
        output.setType(segmentType);

        Check.ifNull(definition.movementEventIds, "definition.movementEventIds");
        for (var id : definition.movementEventIds) {
            Check.ifNullOrEmpty(id, "id within definition.movementEventIds");
            var movementEvent = GET_MOVEMENT_EVENT.apply(id);
            if (movementEvent == null) {
                throw new IllegalArgumentException(
                        "WallSegmentFactoryImpl.make: id within definition.movementEventIds");
            }
            output.movementEvents().add(movementEvent);
        }

        Check.ifNull(definition.abilityEventIds, "definition.abilityEventIds");
        for (var id : definition.abilityEventIds) {
            Check.ifNullOrEmpty(id, "id within definition.abilityEventIds");
            var abilityEvent = GET_ABILITY_EVENT.apply(id);
            if (abilityEvent == null) {
                throw new IllegalArgumentException(
                        "WallSegmentFactoryImpl.make: id within definition.abilityEventIds");
            }
            output.abilityEvents().add(abilityEvent);
        }

        return output;
    }
}

package inaugural.soliloquy.gamestate.entities;

import soliloquy.specs.gamestate.entities.GameEventTargetEntity;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;

import java.util.ArrayList;
import java.util.List;

abstract class AbstractGameEventTargetEntity
        extends HasDeletionInvariants
        implements GameEventTargetEntity {
    private final List<GameMovementEvent> MOVEMENT_EVENTS;
    private final List<GameAbilityEvent> ABILITY_EVENTS;

    // NB: This constructor is to ONLY be used by AbstractTileEntity
    AbstractGameEventTargetEntity() {
        MOVEMENT_EVENTS = new ArrayList<>();
        ABILITY_EVENTS = new ArrayList<>();
    }

    @Override
    public List<GameMovementEvent> movementEvents() throws IllegalStateException {
        enforceInvariants("movementEvents");
        return MOVEMENT_EVENTS;
    }

    @Override
    public List<GameAbilityEvent> abilityEvents() throws IllegalStateException {
        enforceInvariants("abilityEvents");
        return ABILITY_EVENTS;
    }

    abstract void enforceInvariants(String methodName);
}

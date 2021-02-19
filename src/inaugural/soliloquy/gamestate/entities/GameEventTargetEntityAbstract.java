package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.gamestate.archetypes.GameAbilityEventArchetype;
import inaugural.soliloquy.gamestate.archetypes.GameMovementEventArchetype;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.gamestate.entities.GameEventTargetEntity;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;

abstract class GameEventTargetEntityAbstract extends HasDeletionInvariants implements GameEventTargetEntity {
    private final List<GameMovementEvent> MOVEMENT_EVENTS;
    private final List<GameAbilityEvent> ABILITY_EVENTS;

    private final static GameMovementEvent GAME_MOVEMENT_EVENT_ARCHETYPE =
            new GameMovementEventArchetype();
    private final static GameAbilityEvent GAME_ABILITY_EVENT_ARCHETYPE =
            new GameAbilityEventArchetype();

    // NB: This constructor is to ONLY be used by TileEntityAbstract
    GameEventTargetEntityAbstract() {
        MOVEMENT_EVENTS = null;
        ABILITY_EVENTS = null;
    }

    GameEventTargetEntityAbstract(ListFactory listFactory) {
        Check.ifNull(listFactory, "listFactory");
        MOVEMENT_EVENTS = listFactory.make(GAME_MOVEMENT_EVENT_ARCHETYPE);
        ABILITY_EVENTS = listFactory.make(GAME_ABILITY_EVENT_ARCHETYPE);
    }

    // TODO: Test to ensure that clones are produced
    @Override
    public List<GameMovementEvent> movementEvents() throws IllegalStateException {
        enforceInvariants("movementEvents");
        return MOVEMENT_EVENTS.makeClone();
    }

    // TODO: Test to ensure that clones are produced
    @Override
    public List<GameAbilityEvent> abilityEvents() throws IllegalStateException {
        enforceInvariants("abilityEvents");
        return ABILITY_EVENTS.makeClone();
    }

    abstract void enforceInvariants(String methodName);
}

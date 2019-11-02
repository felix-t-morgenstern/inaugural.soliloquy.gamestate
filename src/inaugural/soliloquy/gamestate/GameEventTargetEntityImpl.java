package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.GameAbilityEventArchetype;
import inaugural.soliloquy.gamestate.archetypes.GameMovementEventArchetype;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.gamestate.entities.GameEventTargetEntity;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;

abstract class GameEventTargetEntityImpl extends HasDeletionInvariants implements GameEventTargetEntity {
    private final Collection<GameMovementEvent> MOVEMENT_EVENTS;
    private final Collection<GameAbilityEvent> ABILITY_EVENTS;

    private final static GameMovementEvent GAME_MOVEMENT_EVENT_ARCHETYPE =
            new GameMovementEventArchetype();
    private final static GameAbilityEvent GAME_ABILITY_EVENT_ARCHETYPE =
            new GameAbilityEventArchetype();

    GameEventTargetEntityImpl(CollectionFactory collectionFactory) {
        if (collectionFactory == null) {
            throw new IllegalArgumentException("TileImpl: collectionFactory cannot be null");
        }
        MOVEMENT_EVENTS = collectionFactory.make(GAME_MOVEMENT_EVENT_ARCHETYPE);
        ABILITY_EVENTS = collectionFactory.make(GAME_ABILITY_EVENT_ARCHETYPE);
    }

    @Override
    public Collection<GameMovementEvent> movementEvents() throws IllegalStateException {
        enforceInvariantsForEventsCollections("movementEvents");
        return MOVEMENT_EVENTS;
    }

    @Override
    public Collection<GameAbilityEvent> abilityEvents() throws IllegalStateException {
        enforceInvariantsForEventsCollections("abilityEvents");
        return ABILITY_EVENTS;
    }

    abstract void enforceInvariantsForEventsCollections(String methodName);
}

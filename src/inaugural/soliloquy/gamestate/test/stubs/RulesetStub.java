package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.entities.Function;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.infrastructure.SettingsRepo;
import soliloquy.specs.common.shared.EntityGroup;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.ruleset.Ruleset;
import soliloquy.specs.ruleset.entities.*;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbilityType;
import soliloquy.specs.ruleset.entities.abilities.PassiveAbilityType;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbilityType;
import soliloquy.specs.ruleset.valueobjects.CharacterClassification;

public class RulesetStub implements Ruleset {
    @Override
    public EntityGroup<ActiveAbilityType> activeAbilityTypes() {
        return null;
    }

    @Override
    public EntityGroup<ReactiveAbilityType> reactiveAbilityTypes() {
        return null;
    }

    @Override
    public EntityGroup<PassiveAbilityType> passiveAbilityTypes() {
        return null;
    }

    @Override
    public EntityGroup<CharacterVariableStatisticType> variableStatisticTypesGrouped() {
        return null;
    }

    @Override
    public EntityGroup<CharacterStaticStatisticType> characterStaticStatisticTypes() {
        return null;
    }

    @Override
    public Registry<CharacterClassification> characterClassifications() {
        return null;
    }

    @Override
    public Registry<CharacterType> characterTypes() {
        return null;
    }

    @Override
    public Registry<CharacterAIType> characterAITypes() {
        return null;
    }

    @Override
    public Registry<Element> elements() {
        return null;
    }

    @Override
    public Registry<EquipmentType> equipmentTypes() {
        return null;
    }

    @Override
    public Registry<FixtureType> fixtureTypes() {
        return null;
    }

    @Override
    public Registry<GameMovementEvent> gameMovementEvents() {
        return null;
    }

    @Override
    public Registry<GameAbilityEvent> gameAbilityEvents() {
        return null;
    }

    @Override
    public Registry<GroundType> groundTypes() {
        return null;
    }

    @Override
    public Registry<ItemType> itemTypes() {
        return null;
    }

    @Override
    public Registry<StatusEffectType> statusEffectTypes() {
        return null;
    }

    @Override
    public Registry<CharacterVariableStatisticType> variableStatisticTypes() {
        return null;
    }

    @Override
    public Registry<WallSegmentType> wallSegmentTypes() {
        return null;
    }

    @Override
    public Registry<Action> actions() {
        return null;
    }

    @Override
    public Registry<Function> functions() {
        return null;
    }

    @Override
    public SettingsRepo rulesetSettings() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

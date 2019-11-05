package inaugural.soliloquy.gamestate.test.stubs;

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
    public EntityGroup<ActiveAbilityType> activeAbilitiyTypes() {
        return null;
    }

    @Override
    public EntityGroup<ReactiveAbilityType> reactiveAbilitiyTypes() {
        return null;
    }

    @Override
    public EntityGroup<PassiveAbilityType> passiveAbilitiyTypes() {
        return null;
    }

    @Override
    public EntityGroup<CharacterStatisticType> characterStatisticTypes() {
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
    public Registry<CharacterDepletableStatisticType> depletableStatisticTypes() {
        return null;
    }

    @Override
    public Registry<WallSegmentType> wallSegmentTypes() {
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

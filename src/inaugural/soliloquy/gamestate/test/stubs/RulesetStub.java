package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.infrastructure.SettingsRepo;
import soliloquy.specs.common.shared.EntityGroup;
import soliloquy.specs.gamestate.entities.gameevents.GameEvent;
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
    public Collection<String> aptitudes() {
        return null;
    }

    @Override
    public EntityGroup<AttributeType> attributes() {
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
    public Registry<GameEvent> gameEvents() {
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
    public Registry<VitalAttributeType> vitalAttributes() {
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

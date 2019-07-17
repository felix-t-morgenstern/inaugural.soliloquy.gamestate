package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.SettingsRepo;
import soliloquy.specs.common.shared.EntityGroup;
import soliloquy.specs.ruleset.Ruleset;
import soliloquy.specs.ruleset.entities.*;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbilityType;
import soliloquy.specs.ruleset.entities.abilities.PassiveAbilityType;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbilityType;
import soliloquy.specs.ruleset.valueobjects.CharacterClassification;
import soliloquy.specs.ruleset.valueobjects.TypesRegistry;

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
    public TypesRegistry<CharacterClassification> characterClassifications() {
        return null;
    }

    @Override
    public TypesRegistry<CharacterType> characterTypes() {
        return null;
    }

    @Override
    public TypesRegistry<CharacterAIType> characterAITypes() {
        return null;
    }

    @Override
    public TypesRegistry<CharacterEventType> characterEventTypes() {
        return null;
    }

    @Override
    public TypesRegistry<Element> elements() {
        return null;
    }

    @Override
    public TypesRegistry<EquipmentType> equipmentTypes() {
        return null;
    }

    @Override
    public TypesRegistry<FixtureType> fixtureTypes() {
        return null;
    }

    @Override
    public TypesRegistry<GroundType> groundTypes() {
        return null;
    }

    @Override
    public TypesRegistry<ItemType> itemTypes() {
        return null;
    }

    @Override
    public TypesRegistry<StatusEffectType> statusEffectTypes() {
        return null;
    }

    @Override
    public TypesRegistry<VitalAttributeType> vitalAttributes() {
        return null;
    }

    @Override
    public TypesRegistry<WallSegmentType> wallSegmentTypes() {
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

package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.ICollection;
import soliloquy.specs.common.infrastructure.ISettingsRepo;
import soliloquy.specs.common.shared.IEntityGroup;
import soliloquy.specs.ruleset.IRuleset;
import soliloquy.specs.ruleset.entities.*;
import soliloquy.specs.ruleset.entities.abilities.IActiveAbilityType;
import soliloquy.specs.ruleset.entities.abilities.IPassiveAbilityType;
import soliloquy.specs.ruleset.entities.abilities.IReactiveAbilityType;
import soliloquy.specs.ruleset.valueobjects.ICharacterClassification;
import soliloquy.specs.ruleset.valueobjects.ITypesRegistry;

public class RulesetStub implements IRuleset {
    @Override
    public IEntityGroup<IActiveAbilityType> activeAbilitiyTypes() {
        return null;
    }

    @Override
    public IEntityGroup<IReactiveAbilityType> reactiveAbilitiyTypes() {
        return null;
    }

    @Override
    public IEntityGroup<IPassiveAbilityType> passiveAbilitiyTypes() {
        return null;
    }

    @Override
    public ICollection<String> aptitudes() {
        return null;
    }

    @Override
    public IEntityGroup<IAttributeType> attributes() {
        return null;
    }

    @Override
    public ITypesRegistry<ICharacterClassification> characterClassifications() {
        return null;
    }

    @Override
    public ITypesRegistry<ICharacterType> characterTypes() {
        return null;
    }

    @Override
    public ITypesRegistry<ICharacterAIType> characterAITypes() {
        return null;
    }

    @Override
    public ITypesRegistry<ICharacterEventType> characterEventTypes() {
        return null;
    }

    @Override
    public ITypesRegistry<IElement> elements() {
        return null;
    }

    @Override
    public ITypesRegistry<IEquipmentType> equipmentTypes() {
        return null;
    }

    @Override
    public ITypesRegistry<IFixtureType> fixtureTypes() {
        return null;
    }

    @Override
    public ITypesRegistry<IGroundType> groundTypes() {
        return null;
    }

    @Override
    public ITypesRegistry<IItemType> itemTypes() {
        return null;
    }

    @Override
    public ITypesRegistry<IStatusEffectType> statusEffectTypes() {
        return null;
    }

    @Override
    public ITypesRegistry<IVitalAttributeType> vitalAttributes() {
        return null;
    }

    @Override
    public ITypesRegistry<IWallSegmentType> wallSegmentTypes() {
        return null;
    }

    @Override
    public ISettingsRepo rulesetSettings() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.IEntityGroup;
import soliloquy.common.specs.ISettingsRepo;
import soliloquy.ruleset.gameentities.abilities.specs.IActiveAbilityType;
import soliloquy.ruleset.gameentities.abilities.specs.IPassiveAbilityType;
import soliloquy.ruleset.gameentities.abilities.specs.IReactiveAbilityType;
import soliloquy.ruleset.gameentities.specs.*;
import soliloquy.ruleset.primary.specs.IRuleset;
import soliloquy.sprites.specs.ISpriteFactory;
import soliloquy.sprites.specs.ISpriteSetFactory;

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
    public IEntityGroup<IAttribute> attributes() {
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
    public ITypesRegistry<ITimerAction> timerActions() {
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
    public ISpriteFactory spriteFactory() {
        return null;
    }

    @Override
    public ISpriteSetFactory spriteSetFactory() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

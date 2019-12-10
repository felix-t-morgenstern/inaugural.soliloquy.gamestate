package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.ruleset.entities.abilities.AbilitySource;
import soliloquy.specs.ruleset.entities.abilities.IncomingAbilityDescription;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbilityType;

public class ReactiveAbilityTypeArchetype implements ReactiveAbilityType {
    @Override
    public Integer priority() {
        return null;
    }

    @Override
    public String eventHook() {
        return null;
    }

    @Override
    public boolean willReact(IncomingAbilityDescription incomingAbilityDescription) {
        return false;
    }

    @Override
    public boolean react(boolean b, AbilitySource abilitySource, IncomingAbilityDescription incomingAbilityDescription) {
        return false;
    }

    @Override
    public String description(GenericParamsSet genericParamsSet) throws IllegalStateException {
        return null;
    }

    @Override
    public String id() throws IllegalStateException {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String s) {

    }

    @Override
    public void delete() throws IllegalStateException {

    }

    @Override
    public boolean isDeleted() {
        return false;
    }

    @Override
    public GenericParamsSet data() throws IllegalStateException {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return ReactiveAbilityType.class.getCanonicalName();
    }
}

package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.ruleset.gameentities.specs.IEquipmentType;

import java.util.ArrayList;
import java.util.List;

public class EquipmentTypeStub implements IEquipmentType {
    public final static String ID = "EquipmentTypeStub";

    public final static List<String> VALID_EQUIPMENT_SLOTS = new ArrayList<>();

    @Override
    public boolean canEquipToSlotType(String equipmentSlotType) throws IllegalArgumentException {
        return VALID_EQUIPMENT_SLOTS.contains(equipmentSlotType);
    }

    @Override
    public String id() throws IllegalStateException {
        return null;
    }

    @Override
    public String getPluralName() {
        return null;
    }

    @Override
    public void setPluralName(String s) throws IllegalArgumentException {

    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}

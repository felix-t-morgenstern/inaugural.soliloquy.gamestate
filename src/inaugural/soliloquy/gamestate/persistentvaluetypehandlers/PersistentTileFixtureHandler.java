package inaugural.soliloquy.gamestate.persistentvaluetypehandlers;

import com.google.gson.Gson;
import inaugural.soliloquy.gamestate.archetypes.TileFixtureArchetype;
import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.PersistentTypeHandler;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.PersistentValueTypeHandler;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.factories.TileFixtureFactory;
import soliloquy.specs.ruleset.entities.FixtureType;

import java.util.function.Function;

public class PersistentTileFixtureHandler extends PersistentTypeHandler<TileFixture>
        implements PersistentValueTypeHandler<TileFixture> {
    private final Function<String, FixtureType> GET_FIXTURE_TYPE;
    private final TileFixtureFactory TILE_FIXTURE_FACTORY;
    private final PersistentValueTypeHandler<EntityUuid> ID_HANDLER;
    private final PersistentValueTypeHandler<VariableCache> DATA_HANDLER;
    private final PersistentValueTypeHandler<Item> ITEMS_HANDLER;

    private final TileFixture ARCHETYPE = new TileFixtureArchetype();

    public PersistentTileFixtureHandler(Function<String, FixtureType> getFixtureType,
                                        TileFixtureFactory tileFixtureFactory,
                                        PersistentValueTypeHandler<EntityUuid> idHandler,
                                        PersistentValueTypeHandler<VariableCache> dataHandler,
                                        PersistentValueTypeHandler<Item> itemsHandler) {
        GET_FIXTURE_TYPE = Check.ifNull(getFixtureType, "getFixtureType");
        TILE_FIXTURE_FACTORY = Check.ifNull(tileFixtureFactory, "tileFixtureFactory");
        ID_HANDLER = Check.ifNull(idHandler, "idHandler");
        DATA_HANDLER = Check.ifNull(dataHandler, "dataHandler");
        ITEMS_HANDLER = Check.ifNull(itemsHandler, "itemsHandler");
    }

    @Override
    public TileFixture read(String data) throws IllegalArgumentException {
        if (data == null) {
            throw new IllegalArgumentException(
                    "PersistentTileFixtureHandler.read: data cannot be null");
        }
        if (data.equals("")) {
            throw new IllegalArgumentException(
                    "PersistentTileFixtureHandler.read: data cannot be empty");
        }
        TileFixtureDTO dto = new Gson().fromJson(data, TileFixtureDTO.class);
        TileFixture tileFixture = TILE_FIXTURE_FACTORY.make(
                GET_FIXTURE_TYPE.apply(dto.fixtureTypeId),
                DATA_HANDLER.read(dto.data), ID_HANDLER.read(dto.id));
        tileFixture.setXTileWidthOffset(dto.tileWidthOffset);
        tileFixture.setYTileHeightOffset(dto.tileHeightOffset);
        for(int i = 0; i < dto.items.length; i++) {
            tileFixture.items().add(ITEMS_HANDLER.read(dto.items[i]));
        }
        tileFixture.setName(dto.name);
        return tileFixture;
    }

    @Override
    public String write(TileFixture tileFixture) {
        if (tileFixture == null) {
            throw new IllegalArgumentException(
                    "PersistentTileFixtureHandler.write: tileFixture cannot be null");
        }
        TileFixtureDTO dto = new TileFixtureDTO();
        dto.id = ID_HANDLER.write(tileFixture.id());
        dto.fixtureTypeId = tileFixture.type().id();
        dto.tileWidthOffset = tileFixture.getXTileWidthOffset();
        dto.tileHeightOffset = tileFixture.getYTileHeightOffset();
        List<Item> items = tileFixture.items().representation();
        dto.items = new String[items.size()];
        for(int i = 0; i < items.size(); i++) {
            dto.items[i] = ITEMS_HANDLER.write(items.get(i));
        }
        dto.data = DATA_HANDLER.write(tileFixture.data());
        dto.name = tileFixture.getName();
        return new Gson().toJson(dto);
    }

    @Override
    public TileFixture getArchetype() {
        return ARCHETYPE;
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    private class TileFixtureDTO {
        String id;
        String fixtureTypeId;
        float tileWidthOffset;
        float tileHeightOffset;
        String[] items;
        String data;
        String name;
    }
}

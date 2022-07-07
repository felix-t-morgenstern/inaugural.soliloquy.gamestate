package inaugural.soliloquy.gamestate.persistence;

import com.google.gson.Gson;
import inaugural.soliloquy.gamestate.archetypes.TileFixtureArchetype;
import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.factories.TileFixtureFactory;
import soliloquy.specs.ruleset.entities.FixtureType;

import java.util.UUID;
import java.util.function.Function;

public class TileFixtureHandler extends AbstractTypeHandler<TileFixture> {
    private final Function<String, FixtureType> GET_FIXTURE_TYPE;
    private final TileFixtureFactory TILE_FIXTURE_FACTORY;
    private final TypeHandler<UUID> UUID_HANDLER;
    private final TypeHandler<VariableCache> DATA_HANDLER;
    private final TypeHandler<Item> ITEMS_HANDLER;

    private static final TileFixture ARCHETYPE = new TileFixtureArchetype();

    public TileFixtureHandler(Function<String, FixtureType> getFixtureType,
                              TileFixtureFactory tileFixtureFactory,
                              TypeHandler<UUID> uuidHandler,
                              TypeHandler<VariableCache> dataHandler,
                              TypeHandler<Item> itemsHandler) {
        super(ARCHETYPE);
        GET_FIXTURE_TYPE = Check.ifNull(getFixtureType, "getFixtureType");
        TILE_FIXTURE_FACTORY = Check.ifNull(tileFixtureFactory, "tileFixtureFactory");
        UUID_HANDLER = Check.ifNull(uuidHandler, "uuidHandler");
        DATA_HANDLER = Check.ifNull(dataHandler, "dataHandler");
        ITEMS_HANDLER = Check.ifNull(itemsHandler, "itemsHandler");
    }

    @Override
    public TileFixture read(String data) throws IllegalArgumentException {
        if (data == null) {
            throw new IllegalArgumentException(
                    "TileFixtureHandler.read: data cannot be null");
        }
        if (data.equals("")) {
            throw new IllegalArgumentException(
                    "TileFixtureHandler.read: data cannot be empty");
        }
        TileFixtureDTO dto = new Gson().fromJson(data, TileFixtureDTO.class);
        TileFixture tileFixture = TILE_FIXTURE_FACTORY.make(
                GET_FIXTURE_TYPE.apply(dto.fixtureTypeId),
                DATA_HANDLER.read(dto.data), UUID_HANDLER.read(dto.uuid));
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
                    "TileFixtureHandler.write: tileFixture cannot be null");
        }
        TileFixtureDTO dto = new TileFixtureDTO();
        dto.uuid = UUID_HANDLER.write(tileFixture.uuid());
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

    private static class TileFixtureDTO {
        String uuid;
        String fixtureTypeId;
        float tileWidthOffset;
        float tileHeightOffset;
        String[] items;
        String data;
        String name;
    }
}

package inaugural.soliloquy.gamestate.persistence;

import inaugural.soliloquy.gamestate.entities.TileFixtureImpl;
import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.factories.TileFixtureFactory;
import soliloquy.specs.ruleset.entities.FixtureType;

import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import static soliloquy.specs.common.valueobjects.Vertex.vertexOf;

public class TileFixtureHandler extends AbstractTypeHandler<TileFixture> {
    private final Function<String, FixtureType> GET_FIXTURE_TYPE;
    private final TileFixtureFactory TILE_FIXTURE_FACTORY;
    @SuppressWarnings("rawtypes") private final TypeHandler<Map> MAP_HANDLER;
    private final TypeHandler<Item> ITEMS_HANDLER;

    public TileFixtureHandler(Function<String, FixtureType> getFixtureType,
                              TileFixtureFactory tileFixtureFactory,
                              @SuppressWarnings("rawtypes") TypeHandler<Map> mapHandler,
                              TypeHandler<Item> itemsHandler) {
        GET_FIXTURE_TYPE = Check.ifNull(getFixtureType, "getFixtureType");
        TILE_FIXTURE_FACTORY = Check.ifNull(tileFixtureFactory, "tileFixtureFactory");
        MAP_HANDLER = Check.ifNull(mapHandler, "mapHandler");
        ITEMS_HANDLER = Check.ifNull(itemsHandler, "itemsHandler");
    }

    @Override
    public String typeHandled() {
        return TileFixtureImpl.class.getCanonicalName();
    }

    @SuppressWarnings("unchecked")
    @Override
    public TileFixture read(String data) throws IllegalArgumentException {
        Check.ifNullOrEmpty(data, "data");
        var dto = JSON.fromJson(data, DTO.class);
        //noinspection unchecked
        var tileFixture = TILE_FIXTURE_FACTORY.make(GET_FIXTURE_TYPE.apply(dto.typeId),
                (java.util.Map<String, Object>) MAP_HANDLER.read(dto.data),
                UUID.fromString(dto.uuid));
        tileFixture.setTileOffset(vertexOf(dto.widthOffset, dto.heightOffset));
        for (var i = 0; i < dto.items.length; i++) {
            tileFixture.items().add(ITEMS_HANDLER.read(dto.items[i]));
        }
        tileFixture.setName(dto.name);
        return tileFixture;
    }

    @Override
    public String write(TileFixture tileFixture) {
        Check.ifNull(tileFixture, "tileFixture");
        var dto = new DTO();
        dto.uuid = tileFixture.uuid().toString();
        dto.typeId = tileFixture.type().id();
        dto.widthOffset = tileFixture.getTileOffset().X;
        dto.heightOffset = tileFixture.getTileOffset().Y;
        var items = tileFixture.items().representation();
        dto.items = new String[items.size()];
        for (var i = 0; i < items.size(); i++) {
            dto.items[i] = ITEMS_HANDLER.write(items.get(i));
        }
        dto.data = MAP_HANDLER.write(tileFixture.data());
        dto.name = tileFixture.getName();
        return JSON.toJson(dto);
    }

    private static class DTO {
        String uuid;
        String typeId;
        float widthOffset;
        float heightOffset;
        String[] items;
        String data;
        String name;
    }
}

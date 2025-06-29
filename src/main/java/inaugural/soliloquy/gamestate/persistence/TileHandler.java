package inaugural.soliloquy.gamestate.persistence;

import inaugural.soliloquy.gamestate.entities.TileImpl;
import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.io.graphics.assets.Sprite;
import soliloquy.specs.ruleset.entities.GroundType;

import java.util.Map;
import java.util.function.Function;

public class TileHandler extends AbstractTypeHandler<Tile> {
    private final Function<Map<String, Object>, Tile> TILE_FACTORY;

    private final TypeHandler<Character> CHARACTERS_HANDLER;
    private final TypeHandler<Item> ITEMS_HANDLER;
    private final TypeHandler<TileFixture> FIXTURES_HANDLER;
    private final TypeHandler<Sprite> SPRITE_HANDLER;
    @SuppressWarnings("rawtypes") private final TypeHandler<Map> MAP_HANDLER;

    private final Function<String, GameMovementEvent> GET_MOVEMENT_EVENT;
    private final Function<String, GameAbilityEvent> GET_ABILITY_EVENT;
    private final Function<String, GroundType> GET_GROUND_TYPE;

    public TileHandler(Function<Map<String, Object>, Tile> tileFactory,
                       TypeHandler<Character> charactersHandler,
                       TypeHandler<Item> itemsHandler,
                       TypeHandler<TileFixture> fixturesHandler,
                       TypeHandler<Sprite> spriteHandler,
                       @SuppressWarnings("rawtypes") TypeHandler<Map> mapHandler,
                       Function<String, GameMovementEvent> getMovementEvent,
                       Function<String, GameAbilityEvent> getAbilityEvent,
                       Function<String, GroundType> getGroundType) {
        TILE_FACTORY = Check.ifNull(tileFactory, "tileFactory");
        CHARACTERS_HANDLER = Check.ifNull(charactersHandler, "charactersHandler");
        ITEMS_HANDLER = Check.ifNull(itemsHandler, "itemsHandler");
        FIXTURES_HANDLER = Check.ifNull(fixturesHandler, "fixturesHandler");
        SPRITE_HANDLER = Check.ifNull(spriteHandler, "spriteHandler");
        MAP_HANDLER = Check.ifNull(mapHandler, "mapHandler");
        GET_MOVEMENT_EVENT = Check.ifNull(getMovementEvent, "getMovementEvent");
        GET_ABILITY_EVENT = Check.ifNull(getAbilityEvent, "getAbilityEvent");
        GET_GROUND_TYPE = Check.ifNull(getGroundType, "getGroundType");
    }

    @Override
    public String typeHandled() {
        return TileImpl.class.getCanonicalName();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Tile read(String data) throws IllegalArgumentException {
        Check.ifNullOrEmpty(data, "data");

        var dto = JSON.fromJson(data, TileDTO.class);

        //noinspection unchecked
        var tile = TILE_FACTORY.apply((Map<String, Object>) MAP_HANDLER.read(dto.data));

        tile.setGroundType(GET_GROUND_TYPE.apply(dto.groundTypeId));

        for (var i = 0; i < dto.characters.length; i++) {
            tile.characters().add(CHARACTERS_HANDLER.read(dto.characters[i].entity),
                    dto.characters[i].z);
        }

        for (var i = 0; i < dto.items.length; i++) {
            tile.items().add(ITEMS_HANDLER.read(dto.items[i].entity),
                    dto.items[i].z);
        }

        for (var i = 0; i < dto.fixtures.length; i++) {
            tile.fixtures().add(FIXTURES_HANDLER.read(dto.fixtures[i].entity),
                    dto.fixtures[i].z);
        }

        for (var i = 0; i < dto.movementEvents.length; i++) {
            tile.movementEvents().add(GET_MOVEMENT_EVENT.apply(dto.movementEvents[i]));
        }

        for (var i = 0; i < dto.abilityEvents.length; i++) {
            tile.abilityEvents().add(GET_ABILITY_EVENT.apply(dto.abilityEvents[i]));
        }

        for (var i = 0; i < dto.sprites.length; i++) {
            tile.sprites().put(SPRITE_HANDLER.read(dto.sprites[i].entity),
                    dto.sprites[i].z);
        }

        return tile;
    }

    @Override
    public String write(Tile tile) {
        Check.ifNull(tile, "tile");

        var dto = new TileDTO();

        dto.groundTypeId = tile.getGroundType().id();

        int index;

        dto.characters = new TileEntityDTO[tile.characters().size()];
        index = 0;
        for (var pair : tile.characters()) {
            dto.characters[index++] = new TileEntityDTO(pair.SECOND,
                    CHARACTERS_HANDLER.write(pair.FIRST));
        }

        dto.items = new TileEntityDTO[tile.items().size()];
        index = 0;
        for (var pair : tile.items()) {
            dto.items[index++] = new TileEntityDTO(pair.SECOND,
                    ITEMS_HANDLER.write(pair.FIRST));
        }

        dto.fixtures = new TileEntityDTO[tile.fixtures().size()];
        index = 0;
        for (var pair : tile.fixtures()) {
            dto.fixtures[index++] = new TileEntityDTO(pair.SECOND,
                    FIXTURES_HANDLER.write(pair.FIRST));
        }

        dto.movementEvents = new String[tile.movementEvents().size()];
        index = 0;
        for (var movementEvent : tile.movementEvents()) {
            dto.movementEvents[index++] = movementEvent.id();
        }

        dto.abilityEvents = new String[tile.abilityEvents().size()];
        index = 0;
        for (var abilityEvent : tile.abilityEvents()) {
            dto.abilityEvents[index++] = abilityEvent.id();
        }

        dto.sprites = new TileEntityDTO[tile.sprites().size()];
        index = 0;
        for (var z : tile.sprites().keySet()) {
            dto.sprites[index++] =
                    new TileEntityDTO(tile.sprites().get(z), SPRITE_HANDLER.write(z));
        }

        dto.data = MAP_HANDLER.write(tile.data());

        return JSON.toJson(dto, TileDTO.class);
    }

    private static class TileDTO {
        String groundTypeId;
        TileEntityDTO[] characters;
        TileEntityDTO[] items;
        TileEntityDTO[] fixtures;
        String[] movementEvents;
        String[] abilityEvents;
        TileEntityDTO[] sprites;
        String data;
    }

    private static class TileEntityDTO {
        TileEntityDTO(int z, String entity) {
            this.z = z;
            this.entity = entity;
        }

        int z;
        String entity;
    }
}

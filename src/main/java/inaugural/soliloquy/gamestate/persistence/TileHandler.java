package inaugural.soliloquy.gamestate.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractSoliloquyTypeHandler;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.gamestate.factories.TileFactory;
import soliloquy.specs.gamestate.factories.TileWallSegmentFactory;
import soliloquy.specs.graphics.assets.Sprite;
import soliloquy.specs.ruleset.entities.GroundType;
import soliloquy.specs.ruleset.entities.WallSegmentType;

import java.util.function.Function;

public class TileHandler extends AbstractSoliloquyTypeHandler<Tile> {
    private final TileFactory TILE_FACTORY;
    private final TileWallSegmentFactory TILE_WALL_SEGMENT_FACTORY;

    private final TypeHandler<Character> CHARACTERS_HANDLER;
    private final TypeHandler<Item> ITEMS_HANDLER;
    private final TypeHandler<TileFixture> FIXTURES_HANDLER;
    private final TypeHandler<Sprite> SPRITE_HANDLER;
    private final TypeHandler<VariableCache> DATA_HANDLER;

    private final Function<String, WallSegmentType> GET_SEGMENT_TYPE;
    private final Function<String, GameMovementEvent> GET_MOVEMENT_EVENT;
    private final Function<String, GameAbilityEvent> GET_ABILITY_EVENT;
    private final Function<String, GroundType> GET_GROUND_TYPE;

    public TileHandler(TileFactory tileFactory,
                       TileWallSegmentFactory tileWallSegmentFactory,
                       TypeHandler<Character> charactersHandler,
                       TypeHandler<Item> itemsHandler,
                       TypeHandler<TileFixture> fixturesHandler,
                       TypeHandler<Sprite> spriteHandler,
                       TypeHandler<VariableCache> dataHandler,
                       Function<String, WallSegmentType> getSegmentType,
                       Function<String, GameMovementEvent> getMovementEvent,
                       Function<String, GameAbilityEvent> getAbilityEvent,
                       Function<String, GroundType> getGroundType) {
        super(Tile.class);
        TILE_FACTORY = Check.ifNull(tileFactory, "tileFactory");
        TILE_WALL_SEGMENT_FACTORY = Check.ifNull(tileWallSegmentFactory, "tileWallSegmentFactory");
        CHARACTERS_HANDLER = Check.ifNull(charactersHandler, "charactersHandler");
        ITEMS_HANDLER = Check.ifNull(itemsHandler, "itemsHandler");
        FIXTURES_HANDLER = Check.ifNull(fixturesHandler, "fixturesHandler");
        SPRITE_HANDLER = Check.ifNull(spriteHandler, "spriteHandler");
        DATA_HANDLER = Check.ifNull(dataHandler, "dataHandler");
        GET_SEGMENT_TYPE = Check.ifNull(getSegmentType, "getSegmentType");
        GET_MOVEMENT_EVENT = Check.ifNull(getMovementEvent, "getMovementEvent");
        GET_ABILITY_EVENT = Check.ifNull(getAbilityEvent, "getAbilityEvent");
        GET_GROUND_TYPE = Check.ifNull(getGroundType, "getGroundType");
    }

    @Override
    public Tile read(String data) throws IllegalArgumentException {
        Check.ifNullOrEmpty(data, "data");

        TileDTO dto = JSON.fromJson(data, TileDTO.class);

        Tile tile = TILE_FACTORY.make(dto.x, dto.y, DATA_HANDLER.read(dto.data));
        tile.setHeight(dto.height);
        tile.setGroundType(GET_GROUND_TYPE.apply(dto.groundTypeId));

        for (int i = 0; i < dto.characters.length; i++) {
            tile.characters().add(CHARACTERS_HANDLER.read(dto.characters[i].entity),
                    dto.characters[i].z);
        }

        for (int i = 0; i < dto.items.length; i++) {
            tile.items().add(ITEMS_HANDLER.read(dto.items[i].entity),
                    dto.items[i].z);
        }

        for (int i = 0; i < dto.fixtures.length; i++) {
            tile.fixtures().add(FIXTURES_HANDLER.read(dto.fixtures[i].entity),
                    dto.fixtures[i].z);
        }

        for (int i = 0; i < dto.wallSegments.length; i++) {
            TileWallSegment segment =
                    TILE_WALL_SEGMENT_FACTORY.make(DATA_HANDLER.read(dto.wallSegments[i].data));
            segment.setType(GET_SEGMENT_TYPE.apply(dto.wallSegments[i].type));
            tile.wallSegments().add(
                    TileWallSegmentDirection.fromValue(dto.wallSegments[i].direction), segment,
                    dto.wallSegments[i].height, dto.wallSegments[i].z);
        }

        for (int i = 0; i < dto.movementEvents.length; i++) {
            tile.movementEvents().add(GET_MOVEMENT_EVENT.apply(dto.movementEvents[i]));
        }

        for (int i = 0; i < dto.abilityEvents.length; i++) {
            tile.abilityEvents().add(GET_ABILITY_EVENT.apply(dto.abilityEvents[i]));
        }

        for (int i = 0; i < dto.sprites.length; i++) {
            tile.sprites().put(SPRITE_HANDLER.read(dto.sprites[i].entity),
                    dto.sprites[i].z);
        }

        return tile;
    }

    @Override
    public String write(Tile tile) {
        if (tile == null) {
            throw new IllegalArgumentException("TileHandler.write: tile cannot be null");
        }

        TileDTO dto = new TileDTO();
        dto.x = tile.location().x();
        dto.y = tile.location().y();
        dto.height = tile.getHeight();
        dto.groundTypeId = tile.getGroundType().id();

        int index;

        dto.characters = new TileEntityDTO[tile.characters().size()];
        index = 0;
        for (Pair<Character, Integer> pair : tile.characters()) {
            dto.characters[index++] = new TileEntityDTO(pair.getItem2(),
                    CHARACTERS_HANDLER.write(pair.getItem1()));
        }

        dto.items = new TileEntityDTO[tile.items().size()];
        index = 0;
        for (Pair<Item, Integer> pair : tile.items()) {
            dto.items[index++] = new TileEntityDTO(pair.getItem2(),
                    ITEMS_HANDLER.write(pair.getItem1()));
        }

        dto.fixtures = new TileEntityDTO[tile.fixtures().size()];
        index = 0;
        for (Pair<TileFixture, Integer> pair : tile.fixtures()) {
            dto.fixtures[index++] = new TileEntityDTO(pair.getItem2(),
                    FIXTURES_HANDLER.write(pair.getItem1()));
        }

        dto.wallSegments = new TileWallSegmentDTO[tile.wallSegments().size()];
        index = 0;
        for (Pair<TileWallSegmentDirection, Pair<TileWallSegment,
                TileWallSegmentDimensions>> pair : tile.wallSegments()) {
            TileWallSegmentDimensions dimens = pair.getItem2().getItem2();
            dto.wallSegments[index++] =
                    new TileWallSegmentDTO(pair.getItem2().getItem1().getType().id(),
                            pair.getItem1().getValue(), dimens.getHeight(), dimens.getZIndex(),
                            DATA_HANDLER.write(tile.data()));
        }

        dto.movementEvents = new String[tile.movementEvents().size()];
        index = 0;
        for (GameMovementEvent movementEvent : tile.movementEvents()) {
            dto.movementEvents[index++] = movementEvent.id();
        }

        dto.abilityEvents = new String[tile.abilityEvents().size()];
        index = 0;
        for (GameAbilityEvent abilityEvent : tile.abilityEvents()) {
            dto.abilityEvents[index++] = abilityEvent.id();
        }

        dto.sprites = new TileEntityDTO[tile.sprites().size()];
        index = 0;
        for (Sprite sprite : tile.sprites().keySet()) {
            dto.sprites[index++] =
                    new TileEntityDTO(tile.sprites().get(sprite), SPRITE_HANDLER.write(sprite));
        }

        dto.data = DATA_HANDLER.write(tile.data());

        return JSON.toJson(dto, TileDTO.class);
    }

    private static class TileDTO {
        int x;
        int y;
        int height;
        String groundTypeId;
        TileEntityDTO[] characters;
        TileEntityDTO[] items;
        TileEntityDTO[] fixtures;
        TileWallSegmentDTO[] wallSegments;
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

    private static class TileWallSegmentDTO {
        TileWallSegmentDTO(String type, int direction, int height, int z,
                           String data) {
            this.type = type;
            this.direction = direction;
            this.height = height;
            this.z = z;
            this.data = data;
        }

        String type;
        int direction;
        int height;
        int z;
        String data;
    }
}

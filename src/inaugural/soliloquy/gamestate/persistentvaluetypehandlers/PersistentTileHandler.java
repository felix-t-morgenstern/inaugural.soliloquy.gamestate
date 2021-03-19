package inaugural.soliloquy.gamestate.persistentvaluetypehandlers;

import com.google.gson.Gson;
import inaugural.soliloquy.gamestate.archetypes.TileArchetype;
import inaugural.soliloquy.tools.generic.HasOneGenericParam;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.PersistentValueTypeHandler;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.gamestate.factories.TileFactory;
import soliloquy.specs.gamestate.factories.TileWallSegmentFactory;
import soliloquy.specs.graphics.assets.Sprite;
import soliloquy.specs.ruleset.entities.GroundType;
import soliloquy.specs.ruleset.entities.WallSegmentType;

import java.util.function.Function;

public class PersistentTileHandler extends HasOneGenericParam<Tile>
        implements PersistentValueTypeHandler<Tile> {
    private final TileFactory TILE_FACTORY;
    private final TileWallSegmentFactory TILE_WALL_SEGMENT_FACTORY;

    private final PersistentValueTypeHandler<Character> CHARACTERS_HANDLER;
    private final PersistentValueTypeHandler<Item> ITEMS_HANDLER;
    private final PersistentValueTypeHandler<TileFixture> FIXTURES_HANDLER;
    private final PersistentValueTypeHandler<Sprite> SPRITE_HANDLER;
    private final PersistentValueTypeHandler<VariableCache> DATA_HANDLER;

    private final Function<String, WallSegmentType> GET_SEGMENT_TYPE;
    private final Function<String, GameMovementEvent> GET_MOVEMENT_EVENT;
    private final Function<String, GameAbilityEvent> GET_ABILITY_EVENT;
    private final Function<String, GroundType> GET_GROUND_TYPE;

    private static final Tile ARCHETYPE = new TileArchetype();

    @SuppressWarnings("ConstantConditions")
    public PersistentTileHandler(TileFactory tileFactory,
                                 TileWallSegmentFactory tileWallSegmentFactory,
                                 PersistentValueTypeHandler<Character> charactersHandler,
                                 PersistentValueTypeHandler<Item> itemsHandler,
                                 PersistentValueTypeHandler<TileFixture> fixturesHandler,
                                 PersistentValueTypeHandler<Sprite> spriteHandler,
                                 PersistentValueTypeHandler<VariableCache> dataHandler,
                                 Function<String, WallSegmentType> getSegmentType,
                                 Function<String, GameMovementEvent> getMovementEvent,
                                 Function<String, GameAbilityEvent> getAbilityEvent,
                                 Function<String, GroundType> getGroundType) {
        super(ARCHETYPE);
        if (tileFactory == null) {
            throw new IllegalArgumentException(
                    "PersistentTileHandler: tileFactory cannot be null");
        }
        TILE_FACTORY = tileFactory;
        if (tileWallSegmentFactory == null) {
            throw new IllegalArgumentException(
                    "PersistentTileHandler: tileWallSegmentFactory cannot be null");
        }
        TILE_WALL_SEGMENT_FACTORY = tileWallSegmentFactory;
        if (charactersHandler == null) {
            throw new IllegalArgumentException(
                    "PersistentTileHandler: charactersHandler cannot be null");
        }
        CHARACTERS_HANDLER = charactersHandler;
        if (itemsHandler == null) {
            throw new IllegalArgumentException(
                    "PersistentTileHandler: itemsHandler cannot be null");
        }
        ITEMS_HANDLER = itemsHandler;
        if (fixturesHandler == null) {
            throw new IllegalArgumentException(
                    "PersistentTileHandler: fixturesHandler cannot be null");
        }
        FIXTURES_HANDLER = fixturesHandler;
        if (spriteHandler == null) {
            throw new IllegalArgumentException(
                    "PersistentTileHandler: spriteHandler cannot be null");
        }
        SPRITE_HANDLER = spriteHandler;
        if (dataHandler == null) {
            throw new IllegalArgumentException(
                    "PersistentTileHandler: dataHandler cannot be null");
        }
        DATA_HANDLER = dataHandler;
        if (getSegmentType == null) {
            throw new IllegalArgumentException(
                    "PersistentTileHandler: getSegmentType cannot be null");
        }
        GET_SEGMENT_TYPE = getSegmentType;
        if (getMovementEvent == null) {
            throw new IllegalArgumentException(
                    "PersistentTileHandler: getMovementEvent cannot be null");
        }
        GET_MOVEMENT_EVENT = getMovementEvent;
        if (getAbilityEvent == null) {
            throw new IllegalArgumentException(
                    "PersistentTileHandler: getAbilityEvent cannot be null");
        }
        GET_ABILITY_EVENT = getAbilityEvent;
        if (getGroundType == null) {
            throw new IllegalArgumentException(
                    "PersistentTileHandler: getGroundType cannot be null");
        }
        GET_GROUND_TYPE = getGroundType;
    }

    @Override
    public Tile read(String data) throws IllegalArgumentException {
        if (data == null) {
            throw new IllegalArgumentException("PersistentTileHandler.read: data cannot be null");
        }
        if (data.equals("")) {
            throw new IllegalArgumentException("PersistentTileHandler.read: data cannot be empty");
        }

        TileDTO dto = new Gson().fromJson(data, TileDTO.class);

        Tile tile = TILE_FACTORY.make(dto.x, dto.y, DATA_HANDLER.read(dto.data));
        tile.setHeight(dto.height);
        tile.setGroundType(GET_GROUND_TYPE.apply(dto.groundTypeId));

        for(int i = 0; i < dto.characters.length; i++) {
            tile.characters().add(CHARACTERS_HANDLER.read(dto.characters[i].entity),
                    dto.characters[i].z);
        }

        for(int i = 0; i < dto.items.length; i++) {
            tile.items().add(ITEMS_HANDLER.read(dto.items[i].entity),
                    dto.items[i].z);
        }

        for(int i = 0; i < dto.fixtures.length; i++) {
            tile.fixtures().add(FIXTURES_HANDLER.read(dto.fixtures[i].entity),
                    dto.fixtures[i].z);
        }

        for(int i = 0; i < dto.wallSegments.length; i++) {
            TileWallSegment segment =
                    TILE_WALL_SEGMENT_FACTORY.make(DATA_HANDLER.read(dto.wallSegments[i].data));
            segment.setType(GET_SEGMENT_TYPE.apply(dto.wallSegments[i].type));
            tile.wallSegments().add(
                    TileWallSegmentDirection.fromValue(dto.wallSegments[i].direction), segment,
                    dto.wallSegments[i].height, dto.wallSegments[i].z);
        }

        for(int i = 0; i < dto.movementEvents.length; i++) {
            tile.movementEvents().add(GET_MOVEMENT_EVENT.apply(dto.movementEvents[i]));
        }

        for(int i = 0; i < dto.abilityEvents.length; i++) {
            tile.abilityEvents().add(GET_ABILITY_EVENT.apply(dto.abilityEvents[i]));
        }

        for(int i = 0; i < dto.sprites.length; i++) {
            tile.sprites().put(SPRITE_HANDLER.read(dto.sprites[i].entity),
                    dto.sprites[i].z);
        }

        return tile;
    }

    @Override
    public String write(Tile tile) {
        if (tile == null) {
            throw new IllegalArgumentException("PersistentTileHandler.write: tile cannot be null");
        }

        TileDTO dto = new TileDTO();
        dto.x = tile.location().getX();
        dto.y = tile.location().getY();
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
        for(GameMovementEvent movementEvent : tile.movementEvents()) {
            dto.movementEvents[index++] = movementEvent.id();
        }

        dto.abilityEvents = new String[tile.abilityEvents().size()];
        index = 0;
        for(GameAbilityEvent abilityEvent : tile.abilityEvents()) {
            dto.abilityEvents[index++] = abilityEvent.id();
        }

        dto.sprites = new TileEntityDTO[tile.sprites().size()];
        index = 0;
        for(Sprite sprite : tile.sprites().keySet()) {
            dto.sprites[index++] =
                    new TileEntityDTO(tile.sprites().get(sprite), SPRITE_HANDLER.write(sprite));
        }

        dto.data = DATA_HANDLER.write(tile.data());

        return new Gson().toJson(dto, TileDTO.class);
    }

    @Override
    public String getUnparameterizedInterfaceName() {
        return PersistentValueTypeHandler.class.getCanonicalName();
    }

    private class TileDTO {
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

    @SuppressWarnings("InnerClassMayBeStatic")
    private class TileEntityDTO {
        TileEntityDTO(int z, String entity) {
            this.z = z;
            this.entity = entity;
        }

        int z;
        String entity;
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    private class TileWallSegmentDTO {
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

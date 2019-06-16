package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.TileFixtureArchetype;
import soliloquy.specs.common.factories.IMapFactory;
import soliloquy.specs.gamestate.entities.ITile;
import soliloquy.specs.gamestate.entities.ITileFixture;
import soliloquy.specs.gamestate.entities.ITileFixtures;

public class TileFixtures extends GameEntityMediatorWithZIndex<ITileFixture> implements ITileFixtures {
    private final ITile TILE;
    private final static ITileFixture TILE_FIXTURE_ARCHETYPE = new TileFixtureArchetype();

    @SuppressWarnings("ConstantConditions")
    public TileFixtures(ITile tile, IMapFactory mapFactory) {
        super(mapFactory);
        if (tile == null) {
            throw new IllegalArgumentException("TileFixtures: tile must be non-null");
        }
        if (mapFactory == null) {
            throw new IllegalArgumentException("TileFixtures: mapFactory must be non-null");
        }
        TILE = tile;
    }

    @Override
    public String getInterfaceName() {
        enforceDeletionInvariants("getInterfaceName");
        return ITileFixtures.class.getCanonicalName();
    }

    @Override
    protected String className() {
        return "TileFixtures";
    }

    @Override
    protected ITileFixture getArchetype() {
        return TILE_FIXTURE_ARCHETYPE;
    }

    @Override
    protected String containingClassName() {
        return "tile";
    }

    @Override
    protected boolean containingObjectIsDeleted() {
        return TILE.isDeleted();
    }
}

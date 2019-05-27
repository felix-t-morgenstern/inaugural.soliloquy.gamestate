package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.TileFixtureArchetype;
import soliloquy.common.specs.IMapFactory;
import soliloquy.gamestate.specs.ITile;
import soliloquy.gamestate.specs.ITileFixture;
import soliloquy.gamestate.specs.ITileFixtures;

public class TileFixtures extends GameEntityMediatorWithZIndex<ITileFixture> implements ITileFixtures {
    private final ITile TILE;
    private final static ITileFixture TILE_FIXTURE_ARCHETYPE = new TileFixtureArchetype();

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

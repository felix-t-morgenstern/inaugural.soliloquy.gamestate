package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.TileFixtureArchetype;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtures;

public class TileFixturesImpl extends GameEntityMediatorWithZIndex<TileFixture> implements TileFixtures {
    private final Tile TILE;
    private final static TileFixture TILE_FIXTURE_ARCHETYPE = new TileFixtureArchetype();

    @SuppressWarnings("ConstantConditions")
    public TileFixturesImpl(Tile tile, MapFactory mapFactory) {
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
        return TileFixtures.class.getCanonicalName();
    }

    @Override
    protected String className() {
        return "TileFixtures";
    }

    @Override
    protected TileFixture getArchetype() {
        return TILE_FIXTURE_ARCHETYPE;
    }

    @Override
    protected String containingClassName() {
        return "tile";
    }

    @Override
    protected Deletable getContainingObject() {
        return TILE;
    }

    @Override
    protected void enforceAssignmentInvariant(TileFixture tileFixture, String methodName) {
        if (tileFixture != null && ENTITIES.containsKey(tileFixture) &&
                tileFixture.tile() != TILE) {
            throw new IllegalStateException("TileFixturesImpl." + methodName + ": tileFixture " +
                    "is present in this class, but this class is not assigned to tileFixture");
        }
    }

    @Override
    protected void assignEntityToAggregate(TileFixture tileFixture) {
        tileFixture.assignTileFixtureToTileAfterAddingToTileFixtures(TILE);
    }

    @Override
    protected void removeEntityFromAggregate(TileFixture tileFixture) {
        tileFixture.assignTileFixtureToTileAfterAddingToTileFixtures(null);
    }

    @Override
    boolean entityIsPresentElsewhere(TileFixture tileFixture) {
        return tileFixture.tile() != null;
    }
}

package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.TileFixtureArchetype;
import soliloquy.common.specs.IMap;
import soliloquy.common.specs.IMapFactory;
import soliloquy.gamestate.specs.ITile;
import soliloquy.gamestate.specs.ITileFixture;
import soliloquy.gamestate.specs.ITileFixtures;

import java.util.HashMap;
import java.util.Map;

public class TileFixtures implements ITileFixtures {
    private final ITile TILE;
    private final IMapFactory MAP_FACTORY;
    private final HashMap<ITileFixture, Integer> TILE_FIXTURES;

    private final static ITileFixture TILE_FIXTURE_ARCHETYPE = new TileFixtureArchetype();

    private boolean _isDeleted;

    public TileFixtures(ITile tile, IMapFactory mapFactory) {
        if (tile == null) {
            throw new IllegalArgumentException("TileFixtures: tile must be non-null");
        }
        if (mapFactory == null) {
            throw new IllegalArgumentException("TileFixtures: mapFactory must be non-null");
        }
        TILE = tile;
        MAP_FACTORY = mapFactory;
        TILE_FIXTURES = new HashMap<>();
    }

    @Override
    public void delete() throws IllegalStateException {
        _isDeleted = true;

        for(Map.Entry<ITileFixture,Integer> entry : TILE_FIXTURES.entrySet()) {
            entry.getKey().delete();
        }
    }

    @Override
    public boolean isDeleted() {
        return _isDeleted;
    }

    @Override
    public String getInterfaceName() {
        enforceDeletionInvariants("getInterfaceName");
        return ITileFixtures.class.getCanonicalName();
    }

    @Override
    public IMap<ITileFixture, Integer> getRepresentation() throws IllegalStateException {
        enforceDeletionInvariants("getRepresentation");
        IMap<ITileFixture, Integer> representation = MAP_FACTORY.make(TILE_FIXTURE_ARCHETYPE,0);
        for(Map.Entry<ITileFixture,Integer> entry : TILE_FIXTURES.entrySet()) {
            representation.put(entry.getKey(), entry.getValue());
        }
        return representation;
    }

    @Override
    public void add(ITileFixture tileFixture) throws IllegalArgumentException {
        add(tileFixture, 0);
    }

    @Override
    public void add(ITileFixture tileFixture, int zIndex) throws IllegalArgumentException {
        enforceDeletionInvariants("add");
        if (tileFixture == null) {
            throw new IllegalArgumentException("TileFixtures.add: tileFixture must be non-null");
        }
        TILE_FIXTURES.put(tileFixture, zIndex);
    }

    @Override
    public boolean remove(ITileFixture tileFixture) {
        enforceDeletionInvariants("remove");
        if (tileFixture == null) {
            throw new IllegalArgumentException("TileFixtures.remove: tileFixture must be non-null");
        }
        return TILE_FIXTURES.remove(tileFixture) != null;
    }

    @Override
    public boolean contains(ITileFixture tileFixture) throws IllegalArgumentException {
        enforceDeletionInvariants("contain");
        if (tileFixture == null) {
            throw new IllegalArgumentException(
                    "TileFixtures.contains: tileFixture must be non-null");
        }
        return TILE_FIXTURES.containsKey(tileFixture);
    }

    private void enforceDeletionInvariants(String methodName) {
        if (_isDeleted) {
            throw new IllegalStateException("TileFixtures." + methodName + ": object is deleted");
        }
        if (TILE.isDeleted()) {
            throw new IllegalStateException("TileFixtures." + methodName + ": tile is deleted");
        }
    }
}

package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.sprites.entities.Sprite;

public class SpriteArchetype implements Sprite {
    @Override
    public String getInterfaceName() {
        return Sprite.class.getCanonicalName();
    }

    @Override
    public int getHeight() {
        return 0;
    }
}

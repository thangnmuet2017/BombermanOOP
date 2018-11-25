package uet.oop.bomberman.entities.tile;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

/**
 * Entity co dinh, khong di chuyen
 */
public abstract class Tile extends Entity {
	
    public Tile(int x, int y, Sprite sprite) {
        _x = x;
        _y = y;
        // Tile la lop cac doi tuong co dinh 
        // nen cac toa do _x va _y coi la so nguyen
        _sprite = sprite;
    }

    /**
     * Mac dinh khong cho cac doi tuong khac di qua
     * @param e
     * @return
     */
    @Override
    public boolean collide(Entity e) {
        return false;
    }

    @Override
    public void render(Screen screen) {
        screen.renderEntity( Coordinates.tileToPixel(_x), Coordinates.tileToPixel(_y), this);
    }

    @Override
    public void update() {}
}

package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class SpeedItem extends Item {

    public SpeedItem(int x, int y, Sprite sprite) {
            super(x, y, sprite);
    }

    @Override
    public boolean collide(Entity e) {
        // TODO: xu ly Bomber an Item
        if (e instanceof Bomber) {
            Game.addBomberSpeed(0.2); // + 0.2 toc do
            remove(); // an xong Item thi xoa khoi ban do man choi
//            File item = new File("item.WAV");
//            Sound.playSound(item);
            return true;
        }
        return false;
    }
}

package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class FlameItem extends Item {

    public FlameItem(int x, int y, Sprite sprite) {
            super(x, y, sprite);
    }

    @Override
    public boolean collide(Entity e) {
        // TODO: xu ly Bomb an Item
        if (e instanceof Bomber) {
            Game.addBombRadius(1); // + 1 ban kinh sat thuong
            remove(); // an xong Item thi xoa khoi ban do man choi
//            File item = new File("item.WAV");
//            Sound.playSound(item);
            return true;
        }
        return false;
    }

}

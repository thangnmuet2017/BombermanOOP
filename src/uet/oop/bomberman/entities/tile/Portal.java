package uet.oop.bomberman.entities.tile;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class Portal extends Tile {
    private Board _board;

    public Portal(int x, int y, Sprite sprite, Board board) {
        super(x, y, sprite);
        _board = board;    
    }

    @Override
    public boolean collide(Entity e) {
        // TODO: xu ly va cham voi Bomber
        if (e instanceof Bomber) {
            if (!_board.detectNoEnemies()) {
                // chua het Enemy thi Bomber khong the di vao vi tri Portal
                return false;
            }
            // neu nhu da het enemy, ta xu ly khi Bomber di vao Portal
            if (e.getXTile() == getX() && e.getYTile() == getY()) {
                _board.nextLevel();
//                File win = new File("win.WAV");
//                Sound.playSound(win);
            }
            return true;
        }
        // cac truong hop khac deu khong cho di qua portal
        return false;
    }

}

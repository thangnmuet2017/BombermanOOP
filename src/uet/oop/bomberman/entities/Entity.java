package uet.oop.bomberman.entities;

import uet.oop.bomberman.graphics.IRender;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

/**
 * Lop dai dien cho tat ca cac thuc the trong game (Bomber, Enemy, Wall, Brick,...)
 */
public abstract class Entity implements IRender {

	protected double _x, _y;
	protected boolean _removed = false;
	protected Sprite _sprite;

	/**
	 * Phuong thuc nay duoc goi lien tuc trong vong lap game,
	 * muc dich de xu ly su kien va trang thai cho Entity
	 */
	@Override
	public abstract void update();

	/**
	 * Phuong thuc nay duoc goi lien tuc trong vong lap game
	 * muc dich de xu ly hinh anh cua Entity theo trang thai
	 */
	@Override
	public abstract void render(Screen screen);
	
	public void remove() {
		_removed = true;
	}
	
	public boolean isRemoved() {
		return _removed;
	}
	
	public Sprite getSprite() {
		return _sprite;
	}

	/**
	 * Phuong thuc nay duoc goi de xu ly khi hai Entity va cham nhau
	 * @param e
	 * @return
	 * co cho Entity e di qua doi tuong Entity hien tai hay khong, true la cho di qua, false la khong the di qua
	 */
	public abstract boolean collide(Entity e);
	
	public double getX() {
		return _x;
	}
	
	public double getY() {
		return _y;
	}
	
	public int getXTile() {
		return Coordinates.pixelToTile(_x + _sprite.SIZE / 2);
	}
	
	public int getYTile() {
		return Coordinates.pixelToTile(_y - _sprite.SIZE / 2);
	}
}

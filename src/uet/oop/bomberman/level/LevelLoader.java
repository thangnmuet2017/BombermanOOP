package uet.oop.bomberman.level;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.exceptions.LoadLevelException;

/**
 * Load va luu tru thong tin ban do cac man choi
 */
public abstract class LevelLoader {

	protected int _width;
	protected int _height;
	protected int _level;
	protected Board _board;
	
	public LevelLoader(Board board, int level) throws LoadLevelException {
		_board = board;
		loadLevel(level);
	}

	public abstract void loadLevel(int level) throws LoadLevelException;
	public abstract void createEntities();

	public int getWidth() {
		return _width;
	}

	public int getHeight() {
		return _height;
	}

	public int getLevel() {
		return _level;
	}

}

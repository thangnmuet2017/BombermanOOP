package uet.oop.bomberman.level;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.StringTokenizer;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Balloon;
import uet.oop.bomberman.entities.character.enemy.Oneal;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

public class FileLevelLoader extends LevelLoader {

	/**
	 * Ma tran chua thong tin cua ban do, moi phan tu luu gia tri ky tu doc duoc
	 * tu ma tran ban do trong tep cau hinh
	 */
	private static char[][] _map;
	
	public FileLevelLoader(Board board, int level) throws LoadLevelException {
		super(board, level);
	}
	
	@Override
	public void loadLevel(int level) throws LoadLevelException {
            // TODO: do du lieu trong tep cau hinh /levels/Level{level}.txt
            // TODO: cap nhat cac gia tri doc duoc vao _width, _height, _level, _map
            try {
                URL absPath = FileLevelLoader.class.getResource("/levels/Level" + level +".txt");
                BufferedReader bfr = new BufferedReader(
                new InputStreamReader(absPath.openStream()));

                String basicData = bfr.readLine();
                StringTokenizer token = new StringTokenizer(basicData);

                _level = Integer.parseInt(token.nextToken());
                _height = Integer.parseInt(token.nextToken());
                _width = Integer.parseInt(token.nextToken());
                
                _map = new char[_height][_width];
                String[] line = new String[_height];
                // char[][] copyMap = new char[_height][_width];
                // la mang cac String chua tung hang tren ban do
                for (int i = 0; i < _height; i++) {
                        line[i] = bfr.readLine().substring(0, _width);
                        for (int j = 0 ; j < _width; j++) {
                                _map[i][j] = line[i].charAt(j);
                                
                        }
                }
				bfr.close();
            } catch (Exception e) {
                throw new LoadLevelException("Error loading level " + level, e);
            }   
	}

	@Override
	public void createEntities() {
            // TODO: tao cac Entity cho man choi
            // TODO: sau khi tao xong, goi _board.addEntity() de them Entity vao game

            // TODO: phan code mau o duoi huong dan them cac loai entity vao game
            // TODO: hay xoa no di khi hoan thanh load man choi tu tep cau hinh
            // them Wall
            for (int i = 0; i < getHeight(); i++) {
                for (int j = 0; j < getWidth(); j++) {
                    //int pos = x + y * _width;
                    //Sprite sprite = y == 0 || x == 0 || x == 10 || y == 10 ? Sprite.wall : Sprite.grass;
                    //_board.addEntity(pos, new Grass(x, y, sprite));
                    addEntityToMap(_map[i][j], j, i);

                }
            }

            /*
            // them Bomber
            int xBomber = 1, yBomber = 1;
            _board.addCharacter( new Bomber(Coordinates.tileToPixel(xBomber), Coordinates.tileToPixel(yBomber) + Game.TILES_SIZE, _board) );
            Screen.setOffset(0, 0);
            _board.addEntity(xBomber + yBomber * _width, new Grass(xBomber, yBomber, Sprite.grass));

            // them Enemy
            int xE = 2, yE = 1;
            _board.addCharacter( new Balloon(Coordinates.tileToPixel(xE), Coordinates.tileToPixel(yE) + Game.TILES_SIZE, _board));
            _board.addEntity(xE + yE * _width, new Grass(xE, yE, Sprite.grass));

            // them Brick
            int xB = 3, yB = 1;
            _board.addEntity(xB + yB * _width,
                            new LayeredEntity(xB, yB,
                                    new Grass(xB, yB, Sprite.grass),
                                    new Brick(xB, yB, Sprite.brick)
                            )
            );

            // them Item kem Brick che phu o tren
            int xI = 1, yI = 2;
            _board.addEntity(xI + yI * _width,
                            new LayeredEntity(xI, yI,
                                    new Grass(xI ,yI, Sprite.grass),
                                    new SpeedItem(xI, yI, Sprite.powerup_flames),
                                    new Brick(xI, yI, Sprite.brick)
                            )
            );
            */
	}
	
	public void addEntityToMap(char c, int x, int y) {
            int pos = x + y*getWidth();

            switch(c) {
                case '#': // wall
                    _board.addEntity(pos, new Wall(x, y, Sprite.wall));
                    break;
                case '*': // brick
                    _board.addEntity( pos, new LayeredEntity( x, y, new Grass(x, y, Sprite.grass), new Brick(x, y, Sprite.brick) ) );
                    break;
                case ' ': // grass
                    _board.addEntity(pos, new Grass(x, y, Sprite.grass) );
                    break;
                case 'p': // bomberman
                    _board.addCharacter( new Bomber(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board) );
                    Screen.setOffset(0, 0);
                    _board.addEntity(pos, new Grass(x, y, Sprite.grass));
                    break;
                case '1': // balloon
                    _board.addCharacter( new Balloon(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
                    _board.addEntity(pos, new Grass(x, y, Sprite.grass));
                    break;
                case '2': // Oneal
                    _board.addCharacter( new Oneal(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
                    _board.addEntity(pos, new Grass(x, y, Sprite.grass));
                    break;
                case 'b': // BombItem
                    _board.addEntity(pos, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), 
                        new BombItem(x, y, Sprite.powerup_bombs), new Brick(x, y, Sprite.brick) ) );
                    break;
                case 'f': // FlameItem
                    _board.addEntity(pos, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), 
                        new FlameItem(x, y, Sprite.powerup_flames), new Brick(x, y, Sprite.brick) ) );
                    break;
                case 's': // SpeedItem
                    _board.addEntity(pos, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), 
                        new SpeedItem(x, y, Sprite.powerup_speed), new Brick(x, y, Sprite.brick) ) );
                    break;
                case 'x': // portal
                    _board.addEntity(pos, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new Portal(x, y, Sprite.portal), 
                                                                    new Brick(x, y, Sprite.brick) ) );
                    break;
                default: 
                    _board.addEntity(pos, new Grass(x, y, Sprite.grass) );
                    break;
            }
	}
}
			
package uet.oop.bomberman.entities.character;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;

import java.util.Iterator;
import java.util.List;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.level.Coordinates;

public class Bomber extends Character {

    private List<Bomb> _bombs;
    protected Keyboard _input;

    /**
     * neu gia tri nay < 0 thi cho phep dat doi tuong bomb tiep theo
     * cu moi lan dat bomb, gia tri nay duoc reset ve 30 va giam dan trong moi lan update()
     */
    protected int _timeBetweenPutBombs = 0;

    public Bomber(int x, int y, Board board) {
        super(x, y, board);
        _bombs = _board.getBombs();
        _input = _board.getInput();
        _sprite = Sprite.player_right; // _sprite trong Entity
    }

    @Override
    public void update() {
        clearBombs();
        if (!_alive) {
            afterKill();
            return;
        }

        if (_timeBetweenPutBombs < -7500) _timeBetweenPutBombs = 0;
        else _timeBetweenPutBombs--;

        animate();

        calculateMove();

        detectPlaceBomb();
    }

    @Override
    public void render(Screen screen) {
        calculateXOffset();

        if (_alive)
            chooseSprite();
        else
            _sprite = Sprite.player_dead1;

        screen.renderEntity((int) _x, (int) _y - _sprite.SIZE, this);
    }

    public void calculateXOffset() {
        int xScroll = Screen.calculateXOffset(_board, this);
        Screen.setOffset(xScroll, 0);
    }

    /**
     * Kiem tra xem co dat duoc bomb hay khong
     * Neu co thi dat tai vi tri hien tai cua bomber
     */
    private void detectPlaceBomb() {
        // TODO: kiem tra xem phim dat bom co duoc go va gia tri _timeBetweenPutBombs, Game.getBombRate() co thoa man hay khong
        // TODO: Game.getBombRate() tra ve so luong bomb co the dat lien tiep o thoi diem hien tai
        // TODO: _timeBetweenPutBombs dung de ngan chan Bomber dat 2 bomb lien tiep trong 1 khoang thoi gian qua ngan
        // TODO: neu 3 dieu kien tren thoa man thi ta dat bomb bang cach goi placeBomb()
        // TODO: sau khi dat xong, nho giam so luong BombRate va reset _timeBetweenPutBombs
        
        if(_input.space && Game.getBombRate() > 0 && _timeBetweenPutBombs < 0) {
			
        // tinh tam vi tri hien tai theo toa do tile va dat bomb tai vi tri do
            int xCur = Coordinates.pixelToTile(_x + _sprite.getSize()/2); 
            int yCur = Coordinates.pixelToTile(_y - _sprite.getSize()/2); 
            placeBomb(xCur, yCur);
            // Trong Sprite, tat ca cac doi tuong deu co SIZE = 16, getSize() tra ve SIZE nen _sprite.getSize() = 16
            Game.addBombRate(-1);
            _timeBetweenPutBombs = 30;
            
        // test chuong trinh cho thay
        // neu de _timeBetweenPutBombs <= 0 hoac cac gia tri duong < 10 se khong dat duoc nhieu bom lien tiep ???
        // neu de _timeBetweenPutBombs tu 10 den gan 30 thi co the dat nhieu bom vao cung vi tri trong khoang thoi gian ngan ???
        // neu de _timeBetweenPutBombs > 36 thi kho khan trong viec dat hai bom lien tiep o hai vi tri canh nhau trong mot khoang thoi gian ngan
        // ---> ta nen chon cac gia tri lan can 30 cho _timeBetweenPutBombs
        }

    }

    protected void placeBomb(int x, int y) {
        // TODO: thuc hien thao tac dat bomb vao toa do (x, y)
        Bomb b = new Bomb(x, y, _board);
        _board.addBomb(b);
    }

    private void clearBombs() {
        Iterator<Bomb> bs = _bombs.iterator();

        Bomb b;
        while (bs.hasNext()) {
            b = bs.next();
            if (b.isRemoved()) {
                bs.remove();
                Game.addBombRate(1);
            }
        }

    }

    @Override
    public void kill() {
        if (!_alive) return;
        _alive = false;
//        File bomber_die = new File("bomber_die.WAV");
//        Sound.playSound(bomber_die);
    }

    @Override
    protected void afterKill() {
        if (_timeAfter > 0) --_timeAfter;
        else {
            _board.endGame();
        }
    }

    @Override
    protected void calculateMove() {
        // TODO: xu ly nhan tin hieu dieu khien huong di tu _input va goi move() de thuc hien di chuyen
        // TODO: nho cap nhat lai gia tri co _moving khi thay doi trang thai di chuyen
        
        // code dua theo calculateMove() cua Enemy
        int xa = 0, ya = 0;
        if(_input.up) ya--;
        else if(_input.down) ya++;
        else if(_input.left) xa--;
        else if(_input.right) xa++;

        if(xa != 0 || ya != 0){
            move(xa*Game.getBomberSpeed(), ya*Game.getBomberSpeed());
            _moving =true;
        }
        else _moving = false;
    }

    @Override
    public boolean canMove(double x, double y) {
        // TODO: kiem tra co doi tuong tai vi tri dinh di chuyen den hay khong va co the den do khong
        
        int tileX = Coordinates.pixelToTile(x);
        int tileY = Coordinates.pixelToTile(y);
        Entity checkedEntity = _board.getEntity(tileX, tileY, this);
        return collide(checkedEntity);
    }

    public void moveCenterX() {
        int pixelOfEntity = Coordinates.tileToPixel(1); 
        // vi tri Center dua vao vi tri dat bom trong ham detectPlaceBomb() 
        double centerX = _x + _sprite.getRealWidth() / 2;
        int tileCenterX = Coordinates.pixelToTile(centerX);
        _x = Coordinates.tileToPixel(tileCenterX) + pixelOfEntity / 2 - _sprite.getRealWidth() / 2;
    }

    public void moveCenterY() {
        int pixelOfEntity = Coordinates.tileToPixel(1);
        // vi tri Center dua vao vi tri dat bom trong ham detectPlaceBomb() 
        double centerY = _y - _sprite.getRealHeight() / 2;
        int tileCenterY = Coordinates.pixelToTile(centerY);
        _y = Coordinates.tileToPixel(tileCenterY) + pixelOfEntity / 2 + _sprite.getRealHeight() / 2;
    }

    public void autoMoveCenter() {
        int pixelOfEntity = Coordinates.tileToPixel(1);
        double centerX = _x + _sprite.getRealWidth() / 2;
        double centerY = _y - _sprite.getRealHeight() / 2;
        boolean contactTop = !canMove(centerX, centerY - pixelOfEntity / 2);
        boolean contactDown = !canMove(centerX, centerY + pixelOfEntity / 2);
        boolean contactLeft = !canMove(centerX - pixelOfEntity / 2, centerY);
        boolean contactRight = !canMove(centerX + pixelOfEntity / 2, centerY);

        // Tu dong can giua khi di chuyen bi vuong nua nguoi
        if (_direction != 0 && contactDown) moveCenterY();
        if(_direction != 1 && contactLeft) moveCenterX();
        if (_direction != 2 && contactTop) moveCenterY();
        if (_direction != 3 && contactRight) moveCenterX();
    }
	
    @Override
    public void move(double x, double y) {
        // TODO: su dung canMove() de kiem tra xem co the di chuyen toi diem da tinh toan hay khong va thay doi _x, _y
        // TODO: nho cap nhat lai _direction sau kh di chuyen: up, right, down, left -> 0, 1, 2, 3
        // TODO: Di chuyen nhan vat ra giua
		
        // Tinh toa do tam bomber
        double centerX = _x + _sprite.getRealWidth() / 2;
        double centerY = _y - _sprite.getRealHeight() / 2;
        // cap nhat gia tri _direction khi di chuyen
        if (x > 0) _direction = 1;
        if (x < 0) _direction = 3;
        if (y > 0) _direction = 2;
        if (y < 0) _direction = 0;
        // kiem tra xem co the di chuyen den vi tri da tinh toan hay khong
        // va thuc hien thay doi toa do _x, _y
        if (canMove(centerX + x, centerY + y)) {
            _x += x;
            _y += y;
        }
        // di chuyen nhan vat ra giua
        autoMoveCenter();
    }

    @Override
    public boolean collide(Entity e) {
        // TODO: xu ly va cham voi Flame
        // TODO: xu ly va cham voi Enemy
		
        if (e instanceof Flame) {
            this.kill();
            return false;
        }

        if (e instanceof Enemy) {
            this.kill();
            // bomber chet, enemy co the di qua vi tri cuoi cung cua bomber
            return true;
        } //*
		
        if (e instanceof Wall) return false;
        if (e instanceof LayeredEntity) return e.collide(this); // brick
        if (e instanceof Bomb) return e.collide(this); 
        return true; // grass
        
        // return e.collide(this);
        // neu ta chi dung lenh nay sau //* thi bomber khong di chuyen duoc sau khi dat bomb ???
    }

    private void chooseSprite() {
        switch (_direction) {
            case 0:
                _sprite = Sprite.player_up;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, _animate, 20);
                }
                break;
            case 1:
                _sprite = Sprite.player_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
            case 2:
                _sprite = Sprite.player_down;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, _animate, 20);
                }
                break;
            case 3:
                _sprite = Sprite.player_left;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, _animate, 20);
                }
                break;
            default:
                _sprite = Sprite.player_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
        }
    }
}

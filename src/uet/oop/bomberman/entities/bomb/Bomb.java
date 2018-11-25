package uet.oop.bomberman.entities.bomb;

import java.io.File;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.Sound;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.AnimatedEntitiy;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

public class Bomb extends AnimatedEntitiy {

    protected double _timeToExplode = 120; //2 seconds
    public int _timeAfter = 20;

    protected Board _board;
    protected Flame[] _flames;
    protected boolean _exploded = false;
    protected boolean _allowedToPassThru = true;

    public Bomb(int x, int y, Board board) {
        _x = x;
        _y = y;
        _board = board;
        _sprite = Sprite.bomb;
    }

    @Override
    public void update() {
        if(_timeToExplode > 0) 
                _timeToExplode--;
        else {
            if(!_exploded) 
                explode();
            else
                updateFlames();

            if(_timeAfter > 0) 
                _timeAfter--;
            else
                remove();
        }

        animate();
    }

    @Override
    public void render(Screen screen) {
        if(_exploded) {
            _sprite =  Sprite.bomb_exploded2;
            renderFlames(screen);
        } else
            _sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, _animate, 60);

        int xt = (int)_x << 4;
        int yt = (int)_y << 4;

        screen.renderEntity(xt, yt , this);
    }

    public void renderFlames(Screen screen) {
        for (int i = 0; i < _flames.length; i++) {
            _flames[i].render(screen);
        }
    }

    public void updateFlames() {
        for (int i = 0; i < _flames.length; i++) {
            _flames[i].update();
        }
    }

/**
 * Xu ly bomb no
 */
    protected void explode() {
        _exploded = true;
        _allowedToPassThru = true;
//        File bomb_bang = new File("bomb_bang.WAV");
//        Sound.playSound(bomb_bang);
//        am thanh lam giat man hinh choi
        // TODO: xu ly khi character dung tai vi tri bomb
        Character character = _board.getCharacterAt(_x, _y);
        if(character != null)  {
            character.kill();
        }
        // TODO: tao cac flame
        _flames = new Flame[4]; // 4 directions
        for (int i = 0; i < _flames.length; i++) {
            _flames[i] = new Flame((int)_x, (int)_y, i, Game.getBombRadius(), _board);
        }
    }

    public FlameSegment flameAt(int x, int y) {
        if(!_exploded) return null;

        for (int i = 0; i < _flames.length; i++) {
            if(_flames[i] == null) return null;
            FlameSegment e = _flames[i].flameSegmentAt(x, y);
            if(e != null) return e;
        }

        return null;
    }

    @Override
    public boolean collide(Entity e) {
    // TODO: xu li khi bomber di ra ngay sau khi dat bomb (_allowedToPassThru)

    // xem lai phuong thuc detectPlaceBomb, placeBomb cua Bomber va constructor cua Bomb de tim ra lien he
    // y tuong: tinh theo toa do pixel (so thuc)
    // neu toa do pixel cua Bomber va Bomb chenh nhau mot luong du nho
    // thi coi nhu Bomb dung cung cho Bomber
    // va khi do ta cho phep Bomber thoat ra ngoai Bomb
        /*
        // thu in ra cac toa do pixel
        System.out.print("Bomber: " + e.getXTile() + " " + e.getYTile());
        System.out.println(" " + e.getX() + " " + e.getY());
        System.out.print("Bomb: " + getX() + " " + getY());
        System.out.println(" " + Coordinates.tileToPixel(getX()) + " " + Coordinates.tileToPixel(getY()));
        System.out.print("Bomber - Bomb = " + (e.getX() - Coordinates.tileToPixel(getX())));
        System.out.println(" " + (e.getY() - Coordinates.tileToPixel(getY())));
         */
        if(e instanceof Bomber) {
            double diffX = e.getX() - Coordinates.tileToPixel(getX());
            double diffY = e.getY() - Coordinates.tileToPixel(getY());

            if( !( (diffX >= -11 && diffX < 15) && (diffY >= 0 && diffY < 25) ) ) { 
                _allowedToPassThru = false;
            }
            return _allowedToPassThru;
        }

        // neu ta dung doan code sau
        /*
        if(e instanceof Bomber) {
            if (e.getXTile() == this.getX() && e.getYTile() == this.getY()) {
                _allowedToPassThru = true;
            }
            else _allowedToPassThru = false;
            System.out.println("\t" + _allowedToPassThru);
            return _allowedToPassThru;
        }
        //---> bomber co the sang trai, len xuong, nhung bi ket lai khi sang phai va ko chet??
         */
        
    // TODO: xu ly va cham voi flame cua bomb khac
        if (e instanceof FlameSegment) {
            explode();
        }
        
        return false;
    }
}
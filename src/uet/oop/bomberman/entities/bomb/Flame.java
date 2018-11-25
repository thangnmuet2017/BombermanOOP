package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Screen;

public class Flame extends Entity {

	protected Board _board;
	protected int _direction;
	private int _radius;
	protected int xOrigin, yOrigin;
	protected FlameSegment[] _flameSegments = new FlameSegment[0];

	public Flame(int x, int y, int direction, int radius, Board board) {
            xOrigin = x;
            yOrigin = y;
            _x = x;
            _y = y;
            _direction = direction;
            _radius = radius;
            _board = board;
            createFlameSegments();
	}

	/**
	 * Tao cac FlameSegment, moi Segment ung voi mot don vi do dai
	 */
	private void createFlameSegments() {
            /**
             * tao ra cac Segment
             */
            _flameSegments = new FlameSegment[calculatePermitedDistance()];

            /**
             * last dung de danh dau segment cuoi cung
             */
            boolean last = false;

            // TODO: tao cac Segment
            int xSegment = (int)_x;
            int ySegment = (int)_y;
            int _flameSegmentsLength = _flameSegments.length;
            for (int i = 0; i < _flameSegmentsLength; i++) {
                if (i == _flameSegmentsLength - 1) last = true;
                else last = false;
                switch (_direction) {
                    case 0: 
                        ySegment--;
                        break;
                    case 1: 
                        xSegment++;
                        break;
                    case 2: 
                        ySegment++;
                        break;
                    case 3: 
                        xSegment--;
                        break;
                }
                _flameSegments[i] = new FlameSegment(xSegment, ySegment, _direction, last);
            }
	}

	/**
	 * Tinh toan do dai flame, xet cac truong hop gap vat can
	 */
	private int calculatePermitedDistance() {
            // TODO: thuc hien tinh toan do dai flame

            // thuc hien gan tuong tu phuong thuc createFlameSegments()
            int radius = 0;
            int xSegment = (int)_x;
            int ySegment = (int)_y;
            while(radius < _radius) {
                switch (_direction) {
                    case 0: 
                        ySegment--;
                        break;
                    case 1: 
                        xSegment++;
                        break;
                    case 2: 
                        ySegment++;
                        break;
                    case 3: 
                        xSegment--;
                        break;
                }
                // kiem tra xem co Entity nao dang dung o toa do (xSegment, ySegment) hay khong
                Entity entity = _board.getEntity(xSegment, ySegment, null);
                
                // getEntity co tinh bao quat rong hon getEntityAt
                // doi so null duoc truyen vao de tim Character bat ky (neu co) tai (xSegment, ySegment)
                /*
                neu dung Entity entity = _board.getEntityAt(xSegment, ySegment);
                thi khi dinh bomb, Balloon chet, nhung Oneal van song???
                 */
                
                // neu la bomb, tang do dai flame
                if (entity instanceof Bomb) {
                    radius++;
                }
                // neu la brick, wall thi khong can tang _radius len nua
                else if( !(entity.collide(this)) ) break;
                // neu gap cac doi tuong khac thi tang _radius len
                else radius++;
            }
            return radius;
	}
	
	public FlameSegment flameSegmentAt(int x, int y) {
            for (int i = 0; i < _flameSegments.length; i++) {
                    if(_flameSegments[i].getX() == x && _flameSegments[i].getY() == y)
                            return _flameSegments[i];
            }
            return null;
	}

	@Override
	public void update() {}
	
	@Override
	public void render(Screen screen) {
            for (int i = 0; i < _flameSegments.length; i++) {
                    _flameSegments[i].render(screen);
            }
	}

	@Override
	public boolean collide(Entity e) {
            // TODO: xu ly va cham cua Flame voi Bomber va Enemy
            // Chu y doi tuong nay co vi tri chinh la vi tri ma bomb da no
            if (e instanceof Bomb) {
                ((Bomb) e).explode();
            }
            return false;
	}
}

package uet.oop.bomberman.entities.character.enemy.ai;

import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;

public class AIMedium extends AI {
//    Bomber _bomber; // code goc
//    Enemy _enemy; // code goc
    // thu thay bang hai dong code sau
    private Bomber _bomber;
    private Enemy _enemy;
    
    public AIMedium(Bomber bomber, Enemy enemy) {
        _bomber = bomber;
        _enemy = enemy;
    }

    @Override
    public int calculateDirection() {
        // TODO: cai dat thuat toan tim duong di cho AI

        // doan code sau co can thiet phai them vao khong?
        /*
        if (_bomber == null) {
                return random.nextInt(4); // di chuyen tu do khi bomber chet???
        }
        */

        int randomMovement = random.nextInt(2);
        // Cac Enemy co kha nang duoi theo Bomber co the tuy chon di chuyen doc hay ngang
        // randomMovement = 0 --> doc
        // randomMovement = 1 --> ngang
        if (randomMovement == 0) {
            int direction = predictVerticalDirection();
            if (direction != -1) return direction;
            else return predictHorizontalDirection(); 
        }
        else {
            int direction = predictHorizontalDirection();
            if (direction != -1) return direction;
            else return predictVerticalDirection(); 
        }
    }

    private int predictVerticalDirection() {
        if(_bomber.getYTile() < _enemy.getYTile())
            return 0; // move up
        else if(_bomber.getYTile() > _enemy.getYTile())
            return 2; // move down
        return -1;
    }

    private int predictHorizontalDirection() {
        if(_bomber.getXTile() < _enemy.getXTile())
            return 3; // move left
        else if(_bomber.getXTile() > _enemy.getXTile())
            return 1; // move right
        return -1;
    }
}

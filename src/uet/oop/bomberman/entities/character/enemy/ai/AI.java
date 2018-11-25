package uet.oop.bomberman.entities.character.enemy.ai;

import java.util.Random;

public abstract class AI {

    protected Random random = new Random();

    /*
     * Thuat toan tim duong di cho AI
     */
    public abstract int calculateDirection();
}

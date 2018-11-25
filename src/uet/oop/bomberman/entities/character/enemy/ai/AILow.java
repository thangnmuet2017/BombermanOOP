package uet.oop.bomberman.entities.character.enemy.ai;

public class AILow extends AI {

	@Override
	public int calculateDirection() {
            // TODO: cai dat thuat toan tim duong di
            // up, right, down, left -> 0, 1, 2, 3
            // --> tra ve direction tu 0 den truoc 4
            return random.nextInt(4);
	}

}

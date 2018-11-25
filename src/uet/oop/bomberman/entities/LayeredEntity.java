package uet.oop.bomberman.entities;

import uet.oop.bomberman.entities.tile.destroyable.DestroyableTile;
import uet.oop.bomberman.graphics.Screen;

import java.util.LinkedList;

/**
 * Chua va quan ly nhieu Entity tai mot vi tri
 * VD: tai vi tri dau Item co ca 3 Entity la Grass, Item va Brick
 */
public class LayeredEntity extends Entity {
	
	protected LinkedList<Entity> _entities = new LinkedList<>();
	
	public LayeredEntity(int x, int y, Entity ... entities) {
            _x = x;
            _y = y;

            for (int i = 0; i < entities.length; i++) {
                _entities.add(entities[i]); 

                if(i > 1) {
                    if(entities[i] instanceof DestroyableTile)
                            ((DestroyableTile)entities[i]).addBelowSprite(entities[i-1].getSprite());
                }
            }
	}
	
	@Override
	public void update() {
		clearRemoved();
		getTopEntity().update();
	}
	
	@Override
	public void render(Screen screen) {
		getTopEntity().render(screen);
	}
	
	public Entity getTopEntity() {
		
		return _entities.getLast();
	}
	
	private void clearRemoved() {
		Entity top  = getTopEntity();
		
		if(top.isRemoved())  {
			_entities.removeLast();
		}
	}
	
	public void addBeforeTop(Entity e) {
		_entities.add(_entities.size() - 1, e);
	}
	
	@Override
	public boolean collide(Entity e) {
		// TODO: lay Entity tren cung ra de xu ly va cham
		return getTopEntity().collide(e);
	}

}
